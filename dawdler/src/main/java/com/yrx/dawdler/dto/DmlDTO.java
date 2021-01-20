package com.yrx.dawdler.dto;

import com.yrx.dawdler.enumeration.DmlTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class DmlDTO {
    public DmlDTO() {
    }

    /**
     * 原始sql
     */
    private String source;
    /**
     * 变更类型
     */
    private DmlTypeEnum dmlType;
    /**
     * Schema名称
     */
    private String schema = "collectsm";
    /**
     * 表英文名
     */
    private String table;
    /**
     * 涉及字段
     */
    private List<String> columns;
    /**
     * 变更说明/备注
     */
    private String remark;
}
