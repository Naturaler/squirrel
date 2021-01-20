package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.stereotype.Component;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class DropTableParser implements IDdlParser {
    private final DdlTypeEnum DDL_TYPE = DdlTypeEnum.DROP_TABLE;

    @Override
    public DdlDTO parse(String source, Statement statement, String remark) {
        Drop drop = (Drop) statement;
        String table = extractTable(drop);
        String metadataBefore = "";
        String metadataAfter = "";
        String column = "";
        String columnType = "";
        String primaryKey = "";

        DdlDTO dto = new DdlDTO();
        dto.setSource(source);
        dto.setDdlType(DDL_TYPE);
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

    private String extractTable(Drop statement) {
        return statement.getName().getName();
    }
}
