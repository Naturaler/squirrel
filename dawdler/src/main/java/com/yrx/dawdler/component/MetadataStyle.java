package com.yrx.dawdler.component;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;

/**
 * Created by r.x on 2021/4/4.
 */
public class MetadataStyle implements IExcelExportStyler {
    private Workbook workbook;

    public MetadataStyle(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public CellStyle getHeaderStyle(short i) {
        // 设置单元格背景色
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        CTColor ctColor = CTColor.Factory.newInstance();
        ctColor.setRgb(new byte[]{(byte) 146, (byte) 208, 80});
        cellStyle.setFillForegroundColor(XSSFColor.from(ctColor, null));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 设置单元格边框
        cellStyle.setBottomBorderColor((short) 79);
        cellStyle.setTopBorderColor((short) 79);
        cellStyle.setLeftBorderColor((short) 79);
        cellStyle.setRightBorderColor((short) 79);

        cellStyle.setBorderBottom(BorderStyle.HAIR);
        cellStyle.setBorderTop(BorderStyle.HAIR);
        cellStyle.setBorderLeft(BorderStyle.HAIR);
        cellStyle.setBorderRight(BorderStyle.HAIR);

        // 设置对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }

    @Override
    public CellStyle getTitleStyle(short i) {
        return getHeaderStyle(i);
    }

    @Override
    public CellStyle getStyles(boolean b, ExcelExportEntity excelExportEntity) {
        return defaultStyle();
    }

    @Override
    public CellStyle getStyles(Cell cell, int i, ExcelExportEntity excelExportEntity, Object o, Object o1) {
        return defaultStyle();
    }

    @Override
    public CellStyle getTemplateStyles(boolean b, ExcelForEachParams excelForEachParams) {
        return defaultStyle();
    }

    private CellStyle defaultStyle() {
        CellStyle cellStyle = workbook.createCellStyle();

        // 设置对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }
}
