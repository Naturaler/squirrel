package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.alter.AlterOperation;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class AlterTableParser implements IDdlParser {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "alter table table_name modify column_name varchar2(32);";
//        String sql = "alter table table_name rename column column_name to column_name_new";
//        String sql = "alter table table_name add is_valid char;";
//        String sql = "alter table table_name add constraint pk_name unique(column1, column2);";
        Statement statement = CCJSqlParserUtil.parse(sql);
        Alter alter = (Alter) statement;
        System.out.println("alter = " + alter);
    }

    @Override
    public DdlDTO parse(String source, Statement statement, String remark) {
        Alter alter = (Alter) statement;
        DdlTypeEnum ddlType = identifyDdlType(alter);
        String table = extractTable(alter);
        String metadataBefore = "";
        String metadataAfter = "";
        String column = "";
        String columnType = "";
        String primaryKey = "";
        if (ddlType != null) {
            switch (ddlType) {
                case ADD_COLUMN:
                    column = alter.getAlterExpressions().get(0).getColumnName();
                    columnType = alter.getAlterExpressions().get(0).getColDataTypeList().get(0).getColDataType().getDataType();
                    break;
                case RENAME_COLUMN_NAME:
                case MODIFY_COLUMN_TYPE:
                    // todo
                    break;
                case CREATE_INDEX:
                    StringJoiner joiner = new StringJoiner(",");
                    column = String.join(",", alter.getAlterExpressions().get(0).getIndex().getColumnsNames());
                    primaryKey = alter.getAlterExpressions().get(0).getIndex().getName();
                    break;
                case DROP_COLUMN:
                    break;
            }
        }
        DdlDTO dto = new DdlDTO();
        dto.setSource(source);
        dto.setDdlType(ddlType);
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setMetadataBefore(metadataBefore);
        dto.setMetadataAfter(metadataAfter);
        dto.setColumn(column);
        dto.setColumnType(columnType);
        dto.setPrimaryKey(primaryKey);
        dto.setRemark(remark);
        return dto;
    }

    private DdlTypeEnum identifyDdlType(Alter statement) {
        AlterExpression expression = statement.getAlterExpressions().get(0);
        AlterOperation operation = expression.getOperation();
        switch (operation) {
            case ADD:
                if (expression.getIndex() != null) {
                    return DdlTypeEnum.CREATE_INDEX;
                }
                return DdlTypeEnum.ADD_COLUMN;
            case RENAME:
                return DdlTypeEnum.RENAME_COLUMN_NAME;
            case MODIFY:
                return DdlTypeEnum.MODIFY_COLUMN_TYPE;
            case DROP:
                return DdlTypeEnum.DROP_COLUMN;
            default:
                return null;
        }
    }

    private String extractPrimaryKey(Alter createIndex) {
//        List<String> columnsNames = createIndex.getIndex().getColumnsNames();
//        return String.join(",", columnsNames);
        return "";
    }

    private String extractTable(Alter statement) {
        return statement.getTable().getName();
    }
}