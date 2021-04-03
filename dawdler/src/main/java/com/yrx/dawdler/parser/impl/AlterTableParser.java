package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DdlParserFlag;
import com.yrx.dawdler.bo.DbMetadataBO;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.component.IDbMetadataHelper;
import com.yrx.dawdler.component.impl.DbMetadataHelper;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.alter.AlterOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/4.
 */
@Component
@DdlParserFlag(target = Alter.class)
public class AlterTableParser implements IDdlParser {

    @Override
    public List<DdlDTO> parse(ToParseSourceBO bo) {
        List<DdlDTO> result = new ArrayList<>();

        Alter alter = (Alter) bo.getStatement();
        DdlTypeEnum ddlType = identifyDdlType(alter);
        if (ddlType == null) {
            return Collections.emptyList();
        }
        String table = extractTable(alter);
        String metadataBefore = "";
        String metadataAfter = "";
        String column = "";
        String columnType = "";
        String primaryKey = "";
        List<String> columnTypeArg;

        switch (ddlType) {
            case ADD_COLUMN:
                List<AlterExpression.ColumnDataType> colData = alter.getAlterExpressions().get(0).getColDataTypeList();
                for (AlterExpression.ColumnDataType columnDataType : colData) {
                    column = columnDataType.getColumnName();
                    columnType = columnDataType.getColDataType().getDataType();
                    columnTypeArg = columnDataType.getColDataType().getArgumentsStringList();
                    if (CollectionUtils.isNotEmpty(columnTypeArg)) {
                        columnType += "(" + String.join(",", columnTypeArg) + ")";
                    }
                    DdlDTO dto = new DdlDTO();
                    dto.setSource(bo.getSource());
                    dto.setDdlType(ddlType.getDesc());
                    dto.setSchema(SCHEMA);
                    dto.setTable(table);
                    dto.setMetadataBefore(metadataBefore);
                    dto.setMetadataAfter(metadataAfter);
                    dto.setColumn(column);
                    dto.setColumnType(columnType);
                    dto.setPrimaryKey(primaryKey);
                    dto.setRemark(bo.getRemarkMapping().get((table + "." + column).toUpperCase()));

                    result.add(dto);
                }
                break;
            case RENAME_COLUMN_NAME:
            case MODIFY_COLUMN_TYPE:
                for (AlterExpression.ColumnDataType columnDataType : alter.getAlterExpressions().get(0).getColDataTypeList()) {
                    column = columnDataType.getColumnName();
                    columnType = columnDataType.getColDataType().getDataType();
                    columnTypeArg = columnDataType.getColDataType().getArgumentsStringList();
                    if (CollectionUtils.isNotEmpty(columnTypeArg)) {
                        columnType += "(" + String.join(",", columnTypeArg) + ")";
                    }
                    metadataBefore = getCurrMetadata(bo, table, column);
                    metadataAfter = columnType;

                    DdlDTO dto = new DdlDTO();
                    dto.setSource(bo.getSource());
                    dto.setDdlType(ddlType.getDesc());
                    dto.setSchema(SCHEMA);
                    dto.setTable(table);
                    dto.setMetadataBefore(metadataBefore);
                    dto.setMetadataAfter(metadataAfter);
                    dto.setColumn(column);
                    dto.setColumnType(columnType);
                    dto.setPrimaryKey(primaryKey);
                    dto.setRemark(bo.getRemarkMapping().get(table + "." + column).toUpperCase());

                    result.add(dto);
                }
                break;
            case CREATE_INDEX:
                column = String.join(",", alter.getAlterExpressions().get(0).getIndex().getColumnsNames());
                primaryKey = alter.getAlterExpressions().get(0).getIndex().getName();

                DdlDTO dto = new DdlDTO();
                dto.setSource(bo.getSource());
                dto.setDdlType(ddlType.getDesc());
                dto.setSchema(SCHEMA);
                dto.setTable(table);
                dto.setMetadataBefore(metadataBefore);
                dto.setMetadataAfter(metadataAfter);
                dto.setColumn(column);
                dto.setColumnType(columnType);
                dto.setPrimaryKey(primaryKey);
                dto.setRemark(bo.getRemarkMapping().get(table + "." + column).toUpperCase());

                result.add(dto);
                break;
            case DROP_COLUMN:
                break;
        }

        return result;
    }

    private String getCurrMetadata(ToParseSourceBO bo, String table, String column) {
        IDbMetadataHelper IDbMetadataHelper = new DbMetadataHelper(bo.getDdlUrl(), bo.getDbUsername(), bo.getDbPassword());
        DbMetadataBO tableMetadata = IDbMetadataHelper.getTableMetadata(table);

        Optional<String> result = tableMetadata.getColumns().stream().filter(original -> original.getName().equals(column))
                .map(DbMetadataBO.DbColumn::getType)
                .findFirst();
        return result.orElse("");
    }

    private DdlTypeEnum identifyDdlType(Alter alter) {
        AlterExpression expression = alter.getAlterExpressions().get(0);
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

    private String extractTable(Alter alter) {
        return alter.getTable().getName();
    }
}
