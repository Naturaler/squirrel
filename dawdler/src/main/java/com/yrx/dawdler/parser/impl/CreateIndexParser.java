package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DdlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/4.
 */
@Component
@DdlParserFlag(target = CreateIndex.class)
public class CreateIndexParser implements IDdlParser {
    private final DdlTypeEnum DDL_TYPE = DdlTypeEnum.CREATE_INDEX;

    @Override
    public List<DdlDTO> parse(ToParseSourceBO bo) {
        CreateIndex createIndex = (CreateIndex) bo.getStatement();
        String table = extractTable(createIndex);
        String primaryKey = extractPrimaryKey(createIndex);

        DdlDTO dto = new DdlDTO();
        dto.setSource(bo.getSource());
        dto.setDdlType(DDL_TYPE.getDesc());
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setPrimaryKey(primaryKey);
        dto.setRemark(bo.getRemarkMapping().get(table.toUpperCase()));
        return Collections.singletonList(dto);
    }

    private String extractPrimaryKey(CreateIndex createIndex) {
        List<String> columnsNames = createIndex.getIndex().getColumnsNames();
        return String.join(",", columnsNames);
    }

    private String extractTable(CreateIndex createIndex) {
        return createIndex.getTable().getName();
    }
}
