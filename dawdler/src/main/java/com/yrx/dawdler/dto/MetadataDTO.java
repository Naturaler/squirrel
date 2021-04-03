package com.yrx.dawdler.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by r.x on 2021/3/21.
 */
@Data
public class MetadataDTO {
    private List<DdlDTO> ddlList = new ArrayList<>(0);
    private List<DmlDTO> dmlList = new ArrayList<>(0);

    /**
     * 总sql条数
     */
    private Integer size;
    private Integer successSize;
    private Integer failSize;
    /**
     * 处理失败的sql
     */
    private List<String> failList;
    /**
     * 忽略的sql数
     */
    private Integer ignoreSize;
    private Map<String, Integer> ignoreList;
}
