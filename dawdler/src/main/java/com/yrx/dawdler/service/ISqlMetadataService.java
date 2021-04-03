package com.yrx.dawdler.service;

import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.vo.SqlSourceVO;

import java.util.List;

/**
 * Created by r.x on 2021/3/21.
 */
public interface ISqlMetadataService {

    MetadataDTO buildDocs(SqlSourceVO sqlSourceVO) throws Exception;

    MetadataDTO batchUpload(SqlSourceVO vo, List<String> lines) throws Exception;
}
