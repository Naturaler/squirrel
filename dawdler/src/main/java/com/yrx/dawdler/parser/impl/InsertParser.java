package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class InsertParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.ADD_DATA;

    @Override
    public DmlDTO parse(String source, Statement statement, String remark) {
        Insert insert = (Insert) statement;
        String table = extractTable(insert);
        List<String> columns = extractColumn(insert);

        DmlDTO dto = new DmlDTO();
        dto.setSource(source);
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE);
        dto.setColumns(columns);
        dto.setRemark(remark);
        return dto;
    }

    private List<String> extractColumn(Insert statement) {
        return statement.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
    }

    private String extractTable(Insert statement) {
        return statement.getTable().getName();
    }


}
