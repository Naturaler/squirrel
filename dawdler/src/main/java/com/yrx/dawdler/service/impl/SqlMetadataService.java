package com.yrx.dawdler.service.impl;

import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.DdlParserFactory;
import com.yrx.dawdler.parser.DmlParserFactory;
import com.yrx.dawdler.parser.IDdlParser;
import com.yrx.dawdler.parser.IDmlParser;
import com.yrx.dawdler.service.ISqlMetadataService;
import com.yrx.dawdler.vo.SqlSourceVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yrx.dawdler.constant.SqlScriptConstant.SQL_ANNOTATION_PREFIX;

@Service
@Slf4j
public class SqlMetadataService implements ISqlMetadataService {
    @Autowired
    private DdlParserFactory ddlParserFactory;
    @Autowired
    private DmlParserFactory dmlParserFactory;

    @Override
    public MetadataDTO buildDocs(SqlSourceVO sqlSourceVO) {
        List<String> lines = breakUpInput(sqlSourceVO.getInputSql());
        List<DdlDTO> ddlList = new ArrayList<>();
        List<DmlDTO> dmlList = new ArrayList<>();
        String remark = "";
        for (String line : lines) {
            if (line.startsWith(SQL_ANNOTATION_PREFIX)) {
                remark = extractRemark(line);
                continue;
            }
            try {
                Statement statement = CCJSqlParserUtil.parse(line);
                boolean isDml = isDml(statement);
                if (isDml) {
                    dmlList.add(parseDml(line, statement, remark));
                } else {
                    ddlList.add(parseDdl(line, statement, remark));
                }
            } catch (JSQLParserException e) {
                e.printStackTrace();
                log.error("数据行解析异常: line: {}", line, e);
            }
        }
        MetadataDTO dto = new MetadataDTO();
        dto.setDdlList(ddlList);
        dto.setDmlList(dmlList);
        return dto;
    }

    private DdlDTO parseDdl(String source, Statement statement, String remark) {
        IDdlParser ddlParser = ddlParserFactory.getParser(statement.getClass());
        return ddlParser.parse(source, statement, remark);
    }

    public static void main(String[] args) throws JSQLParserException {
        String sql = "drop table table_name;";
        Statement statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

//        sql = "alter table table_name rename to table_name_new;";
//        statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

//        sql = "alter table table_name drop column column_name;";
//        statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));
//
//        sql = "alter table table_name rename column column_name to column_name_new";
//        statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

//        sql = "alter table table_name drop primary key;";
//        statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

//        sql = "alter table table_name rename constraint pk_name to \"pk_name_new\";";
//        statement = CCJSqlParserUtil.parse(sql);
//        System.out.println("sql = " + sql);
//        System.out.println("statement.getClass() = " + statement.getClass());
//        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

        sql = "alter table table_name add constraint pk_name unique(column1, column2);";
        statement = CCJSqlParserUtil.parse(sql);
        System.out.println("sql = " + sql);
        System.out.println("statement.getClass() = " + statement.getClass());
        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));

        sql = "create index index_name on table_name(column1, column2);";
        statement = CCJSqlParserUtil.parse(sql);
        System.out.println("sql = " + sql);
        System.out.println("statement.getClass() = " + statement.getClass());
        System.out.println("DmlTypeEnum.getByClass(statement.getClass()) = " + DmlTypeEnum.getByClass(statement.getClass()));
    }

    private DmlDTO parseDml(String source, Statement statement, String remark) {
        IDmlParser dmlParser = dmlParserFactory.getParser(statement.getClass());
        return dmlParser.parse(source, statement, remark);
    }

    private List<String> breakUpInput(String inputSql) {
        String[] split = inputSql.split(System.lineSeparator());
        return Stream.of(split).filter(StringUtils::isNoneBlank).collect(Collectors.toList());
    }

    private String extractRemark(String line) {
        return line.substring(SQL_ANNOTATION_PREFIX.length());
    }

    /**
     * 是否dml操作
     *
     * @return <ul>
     * <li>true：增删查改操作：insert、delete、select、update、merge(Oracle独有)</li>
     * <li>false：除上述语句外的操作，默认值</li>
     * </ul>
     */
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


}
