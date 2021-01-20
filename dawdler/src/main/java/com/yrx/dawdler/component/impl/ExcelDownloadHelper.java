package com.yrx.dawdler.component.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.yrx.dawdler.component.IDownloadHelper;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.MetadataDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ExcelDownloadHelper implements IDownloadHelper {

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, MetadataDTO dto) {
        Workbook workbook = buildWorkBook(dto);
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            String fileName = "demo" + "-" + System.currentTimeMillis();
            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (userAgent.contains("firefox")) {
                response.setHeader("content-disposition", "attachment;filename=demo.xlsx");
            } else {
                response.setHeader("Content-Disposition", "attachment;filename=" +
                        URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            }

            workbook.write(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Workbook buildWorkBook(MetadataDTO dto) {
        // excel标题和sheet名称
        ExportParams exportParams = new ExportParams("demo", "表字段级元数据变更");
        exportParams.setMaxNum(1_000_000);
        IExcelDataHandler dataHandler = null;
        if (dataHandler != null) {
            exportParams.setDataHandler(dataHandler);
        }

        // 表头
        List<ExcelExportEntity> header = createHeader();

        Collection<DdlDTO> dataSet = dto.getDdlList();
        return ExcelExportUtil.exportExcel(exportParams, header, dataSet);
    }

    private List<ExcelExportEntity> createHeader() {
        List<ExcelExportEntity> header = new ArrayList<>();
        header.add(new ExcelExportEntity("变更类型", "ddlType"));
        header.add(new ExcelExportEntity("Schema名称", "schema"));
        header.add(new ExcelExportEntity("表/视图名", "table"));
        header.add(new ExcelExportEntity("变更前元数据", "metadataBefore"));
        header.add(new ExcelExportEntity("变更前后数据", "metadataAfter"));
        header.add(new ExcelExportEntity("字段名称", "column"));
        header.add(new ExcelExportEntity("字段格式", "columnType"));
        header.add(new ExcelExportEntity("主键名称", "primaryKey"));
        header.add(new ExcelExportEntity("变更说明/备注", "remark"));
        return header;
    }
}
