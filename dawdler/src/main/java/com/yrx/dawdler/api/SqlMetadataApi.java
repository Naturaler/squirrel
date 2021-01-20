package com.yrx.dawdler.api;

import com.yrx.dawdler.component.IDownloadHelper;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.service.ISqlMetadataService;
import com.yrx.dawdler.service.impl.BookService;
import com.yrx.dawdler.vo.SqlSourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping("/sql/metadata")
@Controller
public class SqlMetadataApi {
    @Autowired
    private ISqlMetadataService sqlMetadataService;
    @Autowired
    private IDownloadHelper downloadHelper;

    @GetMapping("")
    public String getAll(Map<String, Object> map) {
        return "sql";
    }

    /**
     * 根据sql生成元数据变更文档
     */
    @PostMapping("/build/docs")
    public String buildDocs(Map<String, Object> map, SqlSourceVO sqlSourceVO) {
        MetadataDTO dto = sqlMetadataService.buildDocs(sqlSourceVO);
        map.put("dto", dto);
        return "sqlMetadata";
    }

    @PostMapping("/docs/download")
    public void downloadDocs(MetadataDTO dto,
                             HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ddl = " + dto);
        SqlSourceVO sqlSourceVO = new SqlSourceVO();
        sqlSourceVO.setInputSql("alter table temp_batch_score_31d add constraint pk_temp_batch_score_31d primary key(cust_no);");
        MetadataDTO dto02 = sqlMetadataService.buildDocs(sqlSourceVO);
        downloadHelper.download(request, response, dto02);

//        return "sql";
    }
}
