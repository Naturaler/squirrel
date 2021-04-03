package com.yrx.dawdler.parser;

import com.yrx.dawdler.bo.ToParseSourceBO;
import com.yrx.dawdler.dto.DdlDTO;

import java.util.List;

/**
 * Created by r.x on 2021/4/3.
 */
public interface IDdlParser {

    List<DdlDTO> parse(ToParseSourceBO bo);
}
