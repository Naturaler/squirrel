package com.yrx.dawdler.vo;

import lombok.Data;

/**
 * Created by r.x on 2021/3/21.
 */
@Data
public class SqlSourceVO {
    /**
     * 入参sql，一或多条
     */
    private String inputSql;
    /**
     * 环境配置
     */
    private String profile;
    /**
     * 数据库 url
     */
    private String dbUrl;
    /**
     * 数据库 username
     */
    private String dbUsername;
    /**
     * 数据库 password
     */
    private String dbPassword;
}
