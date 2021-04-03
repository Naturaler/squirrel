package com.yrx.dawdler.component;

import com.yrx.dawdler.dto.MetadataDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by r.x on 2021/3/21.
 */
public interface IDownloadHelper {
    void download(HttpServletRequest request, HttpServletResponse response, MetadataDTO dto);
}
