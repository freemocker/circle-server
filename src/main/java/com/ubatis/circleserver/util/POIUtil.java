package com.ubatis.circleserver.util;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * POI 工具
 * http://poi.apache.org/apidocs/index.html
 */
public class POIUtil {

    private final static Logger logger = LoggerFactory.getLogger(POIUtil.class);

    public static void simpleGetExcelDataList(InputStream excelFileInputStream){
        logger.info("excelFileInputStream:{}", excelFileInputStream);
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(excelFileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("workbook:{}", workbook);
        Sheet sheet = workbook.getSheetAt(0);
        logger.info("sheet.getLastRowNum():{}", sheet.getLastRowNum());
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                logger.info("cell:{}", getCellValue(cell));
            }
        }
    }

    public static Object getCellValue(Cell cell){
        String ret = null;
        switch (cell.getCellType().name()) {
            case "STRING":
                ret = cell.getStringCellValue();
                break;
            case "NUMERIC":
                ret = String.valueOf(cell.getNumericCellValue());
                break;
        }
        return ret;
    }

}
