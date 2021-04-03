package com.yrx.dawdler.component.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import com.yrx.dawdler.component.IDownloadHelper;
import com.yrx.dawdler.component.MetadataStyle;
import com.yrx.dawdler.dto.DdlDTO;
import com.yrx.dawdler.dto.DmlDTO;
import com.yrx.dawdler.dto.MetadataDTO;
import com.yrx.dawdler.enumeration.DdlTypeEnum;
import com.yrx.dawdler.enumeration.DmlTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by r.x on 2021/4/4.
 */
@Component
@Slf4j
public class ExcelDownloadHelper implements IDownloadHelper {
    private static final String SHEET_NAME_DDL = "表字段级元数据变更";
    private static final String SHEET_NAME_DML = "数据变更";

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, MetadataDTO dto) {
        try (Workbook workbook = buildWorkBook(dto)) {
            FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");
            String fileName = "元数据变更-" + dateFormat.format(new Date()) + "-" + System.currentTimeMillis();

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");

            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (userAgent.contains("firefox")) {
                response.setHeader("content-disposition", "attachment;filename=deml.xlsx");
            } else {
                response.setHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode(fileName, "UTF-8")
                        + ".xlsx");
            }

            workbook.write(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Workbook buildWorkBook(MetadataDTO dto) {
        Workbook workbook = new XSSFWorkbook();
        ExcelExportService excelExportService = new ExcelExportService();

        ExportParams ddlParams = new ExportParams(null, SHEET_NAME_DDL, ExcelType.XSSF);
        ddlParams.setMaxNum(1_000_000);
        ddlParams.setStyle(MetadataStyle.class);
        List<ExcelExportEntity> ddlHeader = createDdlHeader();
        List<DdlDTO> ddlDataSet = filterEmptyObj(dto.getDdlList(), (ddl) -> ddl.getDdlType() != null);
        // 防止只导出表头报错
        if (CollectionUtils.isEmpty(ddlDataSet)) {
            ddlDataSet.add(new DdlDTO());
        }
        excelExportService.createSheetForMap(workbook, ddlParams, ddlHeader, ddlDataSet);

        ExportParams dmlParams = new ExportParams(null, SHEET_NAME_DML, ExcelType.XSSF);
        dmlParams.setMaxNum(1_000_000);
        dmlParams.setStyle(MetadataStyle.class);
        List<ExcelExportEntity> dmlHeader = createDmlHeader();
        List<DmlDTO> dmlDataSet = filterEmptyObj(dto.getDmlList(), (dml) -> dml.getDmlType() != null);
        // 防止只导出表头报错
        if (CollectionUtils.isEmpty(dmlDataSet)) {
            dmlDataSet.add(new DmlDTO());
        }
        excelExportService.createSheetForMap(workbook, dmlParams, dmlHeader, dmlDataSet);

        // 设置表头样式
        headerStyle(workbook, SHEET_NAME_DDL, DdlTypeEnum.list());
        headerStyle(workbook, SHEET_NAME_DML, DmlTypeEnum.list());

        return workbook;
    }

    private List<ExcelExportEntity> createDmlHeader() {
        List<ExcelExportEntity> header = new ArrayList<>(5);
        header.add(new ExcelExportEntity("变更类型", "dmlType"));
        header.add(new ExcelExportEntity("Schema名称", "schema"));
        header.add(new ExcelExportEntity("表英文名", "table"));
        header.add(new ExcelExportEntity("涉及字段", "column"));
        header.add(new ExcelExportEntity("变更说明/备注", "remark"));

        return header;

    }

    private List<ExcelExportEntity> createDdlHeader() {
        List<ExcelExportEntity> header = new ArrayList<>(9);
        header.add(new ExcelExportEntity("变更类型", "ddlType"));
        header.add(new ExcelExportEntity("Schema名称", "schema"));
        header.add(new ExcelExportEntity("表/视图名", "table"));
        header.add(new ExcelExportEntity("变更前元数据", "metadataBefore"));
        header.add(new ExcelExportEntity("变更后元数据", "metadataAfter"));
        header.add(new ExcelExportEntity("字段名称", "column"));
        header.add(new ExcelExportEntity("字段格式", "columnType"));
        header.add(new ExcelExportEntity("主键名称", "primaryKey"));

        return header;
    }

    private void headerStyle(Workbook workbook, String sheetName, String[] options) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (options != null && options.length > 0) {
            DataValidationHelper validationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(options);
            CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 100, 0, 0);
            DataValidation validation = validationHelper.createValidation(constraint, cellRangeAddressList);
            sheet.addValidationData(validation);
        }

        // 自适应宽度
        short lastCellNum = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 3 / 2);

            // 最大列宽限制
            final int maxWidth = 15_000;
            if (sheet.getColumnWidth(i) > maxWidth) {
                sheet.setColumnWidth(i, maxWidth);
            }
        }
    }

    private <T> List<T> filterEmptyObj(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
