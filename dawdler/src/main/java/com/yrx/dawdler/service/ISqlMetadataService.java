package com.yrx.dawdler.service;

import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.vo.SqlSourceVO;

public interface ISqlMetadataService {

    /**
     * 根据sql生成元数据变更文档
     */
    MetadataDTO buildDocs(SqlSourceVO sqlSourceVO);
}
