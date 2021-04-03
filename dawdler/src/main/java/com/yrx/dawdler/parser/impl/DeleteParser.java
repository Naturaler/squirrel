package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DmlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/4.
 */
@Component
@DmlParserFlag(target = Delete.class)
public class DeleteParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.DELETE_DATA;

    @Override
    public DmlDTO parse(ToParseSourceBO bo) {
        Delete delete = (Delete) bo.getStatement();
        String table = extractTable(delete);
        List<String> columns = Collections.emptyList();

        DmlDTO dto = new DmlDTO();
        dto.setSource(bo.getSource());
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE.getDesc());
        dto.setColumn(String.join(",", columns));
        dto.setRemark("");
        return dto;
    }

    private String extractTable(Delete delete) {
        return delete.getTable().getName();
    }
}
