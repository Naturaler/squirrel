package com.yrx.dawdler.parser;

import com.yrx.dawdler.dto.DmlDTO;
import net.sf.jsqlparser.statement.Statement;

public interface IDmlParser {
    DmlDTO parse(String source, Statement statement, String remark);
}
