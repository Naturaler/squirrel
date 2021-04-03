package com.yrx.dawdler.api;

import com.yrx.dawdler.component.IDownloadHelper;
import com.yrx.dawdler.config.DbConnectionConfig;
import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.service.ISqlMetadataService;
import com.yrx.dawdler.vo.SqlSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by r.x on 2021/3/21.
 */
@RequestMapping("/sql/metadata")
@Controller
@Slf4j
public class SqlMetadataApi {
    @Autowired
    private ISqlMetadataService sqlMetadataService;
    @Autowired
    private IDownloadHelper downloadHelper;
    @Autowired
    private DbConnectionConfig dbConnectionConfig;

    @GetMapping("")
    public String getAll(Map<String, Object> map) {
        map.put("dbConnectionConfig", dbConnectionConfig.getAllConnectionItems());
        return "sql";
    }

    @PostMapping("/build/docs")
    public String buildDocs(Map<String, Object> map, SqlSourceVO sqlSourceVO) throws Exception {
        MetadataDTO dto = sqlMetadataService.buildDocs(sqlSourceVO);

        map.put("dto", dto);

        return "sqlMetadata";
    }

    @GetMapping("/file/multi")
    public String multiFile() {
        return "multiFile";
    }

    @PostMapping("/docs/download")
    public void downloadDocs(MetadataDTO dto, HttpServletRequest request, HttpServletResponse response) {
        downloadHelper.download(request, response, dto);
    }

    @PostMapping("/batch/upload")
    public String batchUpload(Map<String, Object> map, HttpServletRequest request) throws Exception {
        String profile = request.getParameter("profile");
        String url = request.getParameter("dbUrl");
        String username = request.getParameter("dbUsername");
        String password = request.getParameter("dbPassword");

        if (StringUtils.isNotBlank(profile)&&!"manual".equals(profile)) {
            DbConnectionConfig.DbConnectionItem dbConfig = dbConnectionConfig.getByProfile(profile);
            url = dbConfig.getUrl();
            username = dbConfig.getUsername();
            password = dbConfig.getPassword();
        }

        SqlSourceVO vo = new SqlSourceVO();
        vo.setDbUrl(url);
        vo.setDbUsername(username);
        vo.setDbPassword(password);

        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("file");

        List<String> lines = new ArrayList<>();

        for (MultipartFile file : fileList) {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            lines.addAll(reader.lines().collect(Collectors.toList()));
        }

        MetadataDTO dto = sqlMetadataService.batchUpload(vo, lines);

        map.put("dto", dto);

        return "sqlMetadata";
    }
}
