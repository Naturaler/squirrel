package com.yrx.dawdler.parser.impl;

import com.yrx.dawdler.annotation.DmlParserFlag;
import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import com.yrx.dawdler.parser.IDmlParser;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.yrx.dawdler.constant.CommonConstant.SCHEMA;

/**
 * Created by r.x on 2021/4/3.
 */
@Component
@DmlParserFlag(target = Insert.class)
public class InsertParser implements IDmlParser {
    private final DmlTypeEnum DML_TYPE = DmlTypeEnum.ADD_DATA;

    @Override
    public DmlDTO parse(ToParseSourceBO bo) {
        Insert insert = (Insert) bo.getStatement();
        String table = extractTable(insert);
        List<String> columns = extractColumn(insert);

        DmlDTO dto = new DmlDTO();
        dto.setSource(bo.getSource());
        dto.setSchema(SCHEMA);
        dto.setTable(table);
        dto.setDmlType(DML_TYPE.getDesc());
        dto.setColumn(String.join(",", columns));
        dto.setRemark("");
        return dto;
    }

    private List<String> extractColumn(Insert insert) {
        return insert.getColumns().stream().map(Column::getColumnName).collect(Collectors.toList());
    }

    private String extractTable(Insert insert) {
        return insert.getTable().getName();
    }
}
