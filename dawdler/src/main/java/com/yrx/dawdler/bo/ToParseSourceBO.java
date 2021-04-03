package com.yrx.dawdler.bo;

import lombok.Builder;
import lombok.Data;
import net.sf.jsqlparser.statement.Statement;

import java.util.Map;

/**
 * Created by r.x on 2021/4/3.
 */
@Data
@Builder
public class ToParseSourceBO {
    private String source;
    private Statement statement;
    private Map<String, String> remarkMapping;
    private String ddlUrl;
    private String dbUsername;
    private String dbPassword;
}
