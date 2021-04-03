package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DmlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/3.
 */
@Component
@DmlParserFlag(target = Update.class)
public class UpdateParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.MODIFY_DATA;

    @Override
    public DmlDTO parse(ToParseSourceBO bo) {
        Update update = (Update) bo.getStatement();
        String table = extractTable(update);
        List<String> columns = extractColumn(update);

        DmlDTO dto = new DmlDTO();
        dto.setSource(bo.getSource());
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE.getDesc());
        dto.setColumn(String.join(",", columns));
        dto.setRemark("");
        return dto;
    }

    private List<String> extractColumn(Update update) {
        return update.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
    }

    private String extractTable(Update update) {
        return update.getTable().getName();
    }
}
