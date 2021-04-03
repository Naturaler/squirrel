package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DdlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/4.
 */
@Component
@DdlParserFlag(target = CreateTable.class)
public class CreateTableParser implements IDdlParser {
    private final DdlTypeEnum DDL_TYPE = DdlTypeEnum.CREATE_TABLE;

    @Override
    public List<DdlDTO> parse(ToParseSourceBO bo) {
        CreateTable createTable = (CreateTable) bo.getStatement();
        String table = extractTable(createTable);

        DdlDTO dto = new DdlDTO();
        dto.setSource(bo.getSource());
        dto.setDdlType(DDL_TYPE.getDesc());
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setRemark(bo.getRemarkMapping().get(table.toUpperCase()));
        return Collections.singletonList(dto);
    }

    private String extractTable(CreateTable createTable) {
        return createTable.getTable().getName();
    }
}
