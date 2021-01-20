package com.yrx.dawdler.dto;

import lombok.Data;

import java.util.List;

@Data
public class MetadataDTO {
    List<DdlDTO> ddlList;
    List<DmlDTO> dmlList;

    public MetadataDTO() {
    }
}
