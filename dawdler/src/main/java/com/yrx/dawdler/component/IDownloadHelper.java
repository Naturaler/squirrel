package com.yrx.dawdler.component;

import com.yrx.dawdler.dto.MetadataDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDownloadHelper {

    void download(HttpServletRequest request, HttpServletResponse response,
                  MetadataDTO dto);
}
