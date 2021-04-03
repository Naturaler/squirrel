package com.yrx.dawdler.parser;

import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DmlDTO;

/**
 * Created by r.x on 2021/4/3.
 */
public interface IDmlParser {

    DmlDTO parse(ToParseSourceBO bo);
}
