package com.yrx.dawdler.parser;

import com.yrx.dawdler.dto.DdlDTO;
import net.sf.jsqlparser.statement.Statement;

public interface IDdlParser {
    DdlDTO parse(String source, Statement statement, String remark);
}
