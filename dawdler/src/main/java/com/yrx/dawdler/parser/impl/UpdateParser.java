package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

@Component
public class UpdateParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.MODIFY_DATA;

    @Override
    public DmlDTO parse(String source, Statement statement, String remark) {
        Update update = (Update) statement;
        String table = extractTable(update);
        List<String> columns = extractColumn(update);

        DmlDTO dto = new DmlDTO();
        dto.setSource(source);
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE);
        dto.setColumns(columns);
        dto.setRemark(remark);
        return dto;
    }

    private List<String> extractColumn(Update statement) {
        return statement.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
    }

    private String extractTable(Update statement) {
        return statement.getTable().getName();
    }
}
