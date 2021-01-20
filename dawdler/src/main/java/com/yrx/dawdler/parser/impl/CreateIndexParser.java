package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class CreateIndexParser implements IDdlParser {
    private final DdlTypeEnum DDL_TYPE = DdlTypeEnum.CREATE_INDEX;

    @Override
    public DdlDTO parse(String source, Statement statement, String remark) {
        CreateIndex createIndex = (CreateIndex) statement;
        String table = extractTable(createIndex);
        String metadataBefore = "";
        String metadataAfter = "";
        String column = "";
        String columnType = "";
        String primaryKey = extractPrimaryKey(createIndex);

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

    private String extractPrimaryKey(CreateIndex createIndex) {
        List<String> columnsNames = createIndex.getIndex().getColumnsNames();
        return String.join(",", columnsNames);
    }

    private String extractTable(CreateIndex statement) {
        return statement.getTable().getName();
    }
}