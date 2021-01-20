package com.yrx.dawdler.dto;

import com.yrx.dawdler.enumeration.DdlTypeEnum;
import lombok.Data;

@Data
public class DdlDTO {
    public DdlDTO() {
    }

    /**
     * 原始sql
     */
    private String source;
    /**
     * 变更类型
     */
    private DdlTypeEnum ddlType;
    /**
     * Schema名称
     */
    private String schema = "collectsm";
    /**
     * 表/视图名
     */
    private String table;
    /**
     * 变更后元数据
     */
    private String metadataBefore;
    /**
     * 变更前元数据
     */
    private String metadataAfter;
    /**
     * 字段名称
     */
    private String column;
    /**
     * 字段格式
     */
    private String columnType;
    /**
     * 主键名称
     */
    private String primaryKey;
    /**
     * 变更说明/备注
     */
    private String remark;
}
