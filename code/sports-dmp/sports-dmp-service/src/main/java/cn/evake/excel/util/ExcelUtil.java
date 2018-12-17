package cn.evake.excel.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.evake.excel.exception.ExcelRuntimeException;

/**
 * 
 * @author wangyi
 * @desc 
 * @version $Id: ExcleUtil.java, v 0.1 2017年9月20日 下午1:08:55 lijiaqi Exp $
 */
public class ExcelUtil {

    /** 
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3 
     *  
     * @param col 
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。  
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 将EXCEL中0123映射成ABCD
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param columnIndex
     * @return
     */
    public static String getExcelACol(int columnIndex) {
        columnIndex = columnIndex + 1;
        if (columnIndex < 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

    /**
     * 获取Excel
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param bytes
     * @param sheetName
     * @return
     */
    public static List<Map<String, Object>> readByteXlsxWithSheet(byte[] bytes, String sheetName) {
        InputStream input = new ByteArrayInputStream(bytes);
        return readInputStreamXlsx(input, sheetName);
    }

    /**
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param inputStream
     * @param sheetName
     * @return
     */
    public static List<Map<String, Object>> readInputStreamXlsx(InputStream inputStream,
                                                                String sheetName) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // IO流读取文件  
        XSSFWorkbook wb = null;
        Map<String, Object> rowMap = null;
        int totalRows = 0;
        int totalCells = 0;
        try {
            // 创建文档  
            wb = new XSSFWorkbook(inputStream);
            //读取sheet(页)  
            XSSFSheet xssfSheet = wb.getSheet(sheetName);
            if (xssfSheet == null) {
                throw new ExcelRuntimeException("sheet:" + sheetName + "不存在");
            }
            totalRows = xssfSheet.getLastRowNum();
            //读取Row,从第二行开始  
            for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    rowMap = new HashMap<String, Object>();
                    totalCells = xssfRow.getLastCellNum();
                    //读取列，从第一列开始  
                    for (int c = 0; c <= totalCells + 1; c++) {
                        XSSFCell cell = xssfRow.getCell(c);
                        String result = "";
                        if (cell == null) {
                            continue;
                        }
                        String columName = CellReference
                            .convertNumToColString(cell.getColumnIndex());
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型  
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                                    SimpleDateFormat sdf = null;
                                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                                        .getBuiltinFormat("h:mm:ss")) {
                                        sdf = new SimpleDateFormat("HH:mm:ss");
                                    } else {// 日期  
                                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    }
                                    Date date = cell.getDateCellValue();
                                    result = sdf.format(date);
                                } else if (cell.getCellStyle().getDataFormat() == 58) {
                                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    double value = cell.getNumericCellValue();
                                    Date date = org.apache.poi.ss.usermodel.DateUtil
                                        .getJavaDate(value);
                                    result = sdf.format(date);
                                } else {
                                    //处理数据
                                    if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
                                        double value = cell.getNumericCellValue();
                                        result = value + "";
                                    }
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:// String类型  
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                result = cell.getRichStringCellValue().toString();
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                //空行跳出
                                break;
                            default:
                                result = "";
                                break;
                        }
                        //  判断是否为科学计数法（包含E、e、.等符号）
                        if (result != null && result.contains(".")
                            && (result.contains("E10") || result.contains("+"))) {
                            BigDecimal bd = new BigDecimal(result);
                            result = bd.toString();
                        }
                        rowMap.put(columName, result);
                    }
                    list.add(rowMap);
                }
            }
            return list;
        } catch (ExcelRuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /** 
     * read the Excel 2010 .xlsx 
     * @param file 
     * @param beanclazz 
     * @param titleExist 
     * @return 
     * @throws Exception 
     * @throws IOException  
     */
    public static List<Map<String, Object>> readXlsxWithSheet(File file, String sheetName) {
        InputStream input = null;
        try {
            input = FileUtils.openInputStream(file);
            return readInputStreamXlsx(input, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 读取文本xlsx 2010
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param input
     * @return
     * @throws IOException
     */
    public static Map<String, List<Map<String, Object>>> readXlsx(File file) throws IOException {
        Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
        InputStream input = FileUtils.openInputStream(file);
        // 创建文档  
        XSSFWorkbook wb = new XSSFWorkbook(input);
        //读取sheet(页)  
        int numberOfSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = wb.getSheetName(i);
            result.put(sheetName, readXlsxWithSheet(file, sheetName));
        }
        input.close();
        return result;
    }
}
