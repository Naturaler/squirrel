package com.yrx.dawdler.dto;

import lombok.Data;

import java.util.Objects;

/**
 * Created by r.x on 2021/3/21.
 */
@Data
public class DmlDTO {
    /**
     * 原始sql
     */
    private String source;
    /**
     * 变更类型
     */
    private String dmlType;
    /**
     * schema名称
     */
    private String schema;
    /**
     * 表英文名
     */
    private String table;
    /**
     * 涉及字段
     */
    private String column;
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
        DmlDTO dmlDTO = (DmlDTO) o;
        return Objects.equals(dmlType, dmlDTO.dmlType) &&
                Objects.equals(schema, dmlDTO.schema) &&
                Objects.equals(table, dmlDTO.table) &&
                Objects.equals(column, dmlDTO.column) &&
                Objects.equals(remark, dmlDTO.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dmlType, schema, table, column, remark);
    }
}
