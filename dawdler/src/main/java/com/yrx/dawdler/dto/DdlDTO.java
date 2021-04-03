package com.yrx.dawdler.dto;

import lombok.Data;

import java.util.Objects;

/**
 * Created by r.x on 2021/3/21.
 */
@Data
public class DdlDTO {
    /**
     * 原始sql
     */
    private String source;
    /**
     * 变更类型
     */
    private String ddlType;
    /**
     * schema名称
     */
    private String schema;
    /**
     * 表/视图名
     */
    private String table;
    /**
     * 变更前元数据
     */
    private String metadataBefore;
    /**
     * 变更后元数据
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DdlDTO ddlDTO = (DdlDTO) o;
        return Objects.equals(ddlType, ddlDTO.ddlType) &&
                Objects.equals(schema, ddlDTO.schema) &&
                Objects.equals(table, ddlDTO.table) &&
                Objects.equals(metadataBefore, ddlDTO.metadataBefore) &&
                Objects.equals(metadataAfter, ddlDTO.metadataAfter) &&
                Objects.equals(column, ddlDTO.column) &&
                Objects.equals(columnType, ddlDTO.columnType) &&
                Objects.equals(primaryKey, ddlDTO.primaryKey) &&
                Objects.equals(remark, ddlDTO.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ddlType, schema, table, metadataBefore, metadataAfter, column, columnType, primaryKey, remark);
    }
}
