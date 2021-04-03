package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DdlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.parser.IDdlParser;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by r.x on 2021/4/3.
 */
@Component
@DdlParserFlag(target = Drop.class)
public class DropTableParser implements IDdlParser {
    private final DdlTypeEnum DDL_TYPE = DdlTypeEnum.DROP_TABLE;

    @Override
    public List<DdlDTO> parse(ToParseSourceBO bo) {
        Drop drop = (Drop) bo.getStatement();
        String table = extractTable(drop);

        DdlDTO dto = new DdlDTO();
        dto.setSource(bo.getSource());
        dto.setDdlType(DDL_TYPE.getDesc());
        dto.setTable(table);
        dto.setRemark(bo.getRemarkMapping().get(table.toUpperCase()));
        return Collections.singletonList(dto);
    }

    private String extractTable(Drop drop) {
        return drop.getName().getName();
    }
}
