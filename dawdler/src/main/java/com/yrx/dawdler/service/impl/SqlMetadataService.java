package com.yrx.dawdler.service.impl;

import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.parser.DdlParserFactory;
import com.yrx.dawdler.parser.DmlParserFactory;
import com.yrx.dawdler.parser.IDdlParser;
import com.yrx.dawdler.parser.IDmlParser;
import com.yrx.dawdler.service.ISqlMetadataService;
import com.yrx.dawdler.vo.SqlSourceVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.SqlScriptConstant.SQL_ANNOTATION_PREFIX;

/**
 * Created by r.x on 2021/3/27.
 */
@Service
@Slf4j
public class SqlMetadataService implements ISqlMetadataService {
    @Autowired
    private DdlParserFactory ddlParserFactory;
    @Autowired
    private DmlParserFactory dmlParserFactory;

    @Override
    public MetadataDTO buildDocs(SqlSourceVO sqlSourceVO) throws Exception {
        List<String> lines = breakUpInput(sqlSourceVO.getInputSql());
        return buildDocs(sqlSourceVO, lines);
    }

    private MetadataDTO buildDocs(SqlSourceVO sqlSourceVO, List<String> lines) throws IllegalAccessException {
        List<DdlDTO> ddlList = new ArrayList<>();
        List<DmlDTO> dmlList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        Map<String, Integer> ignoreList = new HashMap<>();
        Map<String, String> remarkMapping = new HashMap<>();

        for (String line : lines) {
            Statement statement = null;
            try {
                statement = CCJSqlParserUtil.parse(line);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
            boolean isComment = isComment(statement);
            if (isComment) {
                saveRemark(statement, remarkMapping);
            }
        }

        for (String line : lines) {
            //过滤注释信息
            if (line.startsWith(SQL_ANNOTATION_PREFIX)) {
                continue;
            }

            try {
                Statement statement = CCJSqlParserUtil.parse(line);
                if (isComment(statement)) {
                    continue;
                }

                ToParseSourceBO bo = ToParseSourceBO.builder()
                        .source(line).statement(statement).remarkMapping(remarkMapping)
                        .ddlUrl(sqlSourceVO.getDbUrl()).dbUsername(sqlSourceVO.getDbUsername()).dbPassword(sqlSourceVO.getDbPassword())
                        .build();

                boolean isDml = isDml(statement);
                if (isDml) {
                    DmlDTO dto = parseDml(bo);
                    if (dto != null && StringUtils.isNotBlank(dto.getDmlType())) {
                        dmlList.add(dto);
                    } else {
                        Integer size = ignoreList.get(line);
                        if (size == null) {
                            size = 0;
                        }
                        size++;
                        ignoreList.put(line, size);
                    }
                } else {
                    List<DdlDTO> dtoList = parseDdl(bo);
                    if (CollectionUtils.isEmpty(dtoList)) {
                        Integer size = ignoreList.get(line);
                        if (size == null) {
                            size = 0;
                        }
                        size++;
                        ignoreList.put(line, size);
                    } else {
                        for (DdlDTO dto : dtoList) {
                            if (dto != null && StringUtils.isNotBlank(dto.getDdlType())) {
                                ddlList.add(dto);
                            } else {
                                Integer size = ignoreList.get(line);
                                if (size == null) {
                                    size = 0;
                                }
                                size++;
                                ignoreList.put(line, size);
                            }
                        }
                    }
                }
            } catch (JSQLParserException e) {
                log.error("数据行解析异常: line: {}", line, e);
                failList.add(line);
            }
        }

        // 转大写
        upperCaseAll(ddlList);
        upperCaseAll(dmlList);

        // 去重
        ddlList = deDuplication(ddlList);
        dmlList = deDuplication(dmlList);

        MetadataDTO dto = new MetadataDTO();
        dto.setDdlList(ddlList);
        dto.setDmlList(dmlList);
        dto.setSize(lines.size());
        dto.setSuccessSize(lines.size() - failList.size() - ignoreList.size());
        dto.setFailSize(failList.size());
        dto.setFailList(failList);
        dto.setIgnoreSize(ignoreList.values().stream().mapToInt(Integer::intValue).sum());
        dto.setIgnoreList(ignoreList);
        return dto;
    }

    private <T> List<T> deDuplication(List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    private <T> void upperCaseAll(List<T> list) throws IllegalAccessException {
        for (T t : list) {
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object source = field.get(t);
                if (source != null) {
                    field.set(t, source.toString().toUpperCase());
                }
            }
        }
    }

    private List<DdlDTO> parseDdl(ToParseSourceBO bo) {
        IDdlParser ddlParser = ddlParserFactory.getParser(bo.getStatement().getClass());
        if (ddlParser == null) {
            log.warn("找不到对应的sql parser: {}", bo);
            return Collections.emptyList();
        }
        return ddlParser.parse(bo);
    }

    private DmlDTO parseDml(ToParseSourceBO bo) {
        IDmlParser dmlParser = dmlParserFactory.getParser(bo.getStatement().getClass());
        if (dmlParser == null) {
            log.warn("找不到对应的sql parser: {}", bo);
            return null;
        }
        return dmlParser.parse(bo);
    }


    private boolean isDml(Statement statement) {
        if (statement instanceof Insert) {
            return true;
        }
        if (statement instanceof Delete) {
            return true;
        }
        if (statement instanceof Select) {
            return true;
        }
        if (statement instanceof Update) {
            return true;
        }
        if (statement instanceof Merge) {
            return true;
        }
        return false;
    }

    private void saveRemark(Statement statement, Map<String, String> remarkMapping) {
        Comment comment = (Comment) statement;
        String remark = comment.getComment().getValue();
        Table table = comment.getTable();
        if (table == null) {
            String column = comment.getColumn().getTable().getName() + "." + comment.getColumn().getColumnName();
            column = column.toUpperCase();
            remarkMapping.put(column, remark);
        } else {
            remarkMapping.put(table.getName(), remark);
        }
    }

    private boolean isComment(Statement statement) {
        return statement instanceof Comment;
    }

    private List<String> breakUpInput(String inputSql) {
        String[] lines = inputSql.split(System.lineSeparator());
        return restoreSql(Arrays.asList(lines));
    }

    private List<String> restoreSql(List<String> lines) {
        List<String> result = new ArrayList<>(lines.size());
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                continue;
            }
            if (line.trim().startsWith(SQL_ANNOTATION_PREFIX)) {
                continue;
            }
            if (line.trim().endsWith(";")) {
                builder.append(line);
                result.add(builder.toString());
                builder = new StringBuilder();
            } else {
                builder.append(line).append(" ");
            }
        }
        return result;
    }

    @Override
    public MetadataDTO batchUpload(SqlSourceVO vo, List<String> lines) throws Exception {
        return null;
    }
}
