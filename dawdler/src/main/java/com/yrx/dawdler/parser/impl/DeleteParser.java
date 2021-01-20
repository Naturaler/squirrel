package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class DeleteParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.DELETE_DATA;

    @Override
    public DmlDTO parse(String source,Statement statement, String remark) {
        Delete delete = (Delete) statement;
        String table = extractTable(delete);
        List<String> columns = extractColumn(delete);

        DmlDTO dto = new DmlDTO();
        dto.setSource(source);
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE);
        dto.setColumns(columns);
        dto.setRemark(remark);
        return dto;
    }

    private List<String> extractColumn(Delete statement) {
        return Collections.emptyList();
    }

    private String extractTable(Delete statement) {
        return statement.getTable().getName();
    }
}
