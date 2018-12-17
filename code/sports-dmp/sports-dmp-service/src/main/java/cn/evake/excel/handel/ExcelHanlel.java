package cn.evake.excel.handel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import cn.evake.auth.util.DateUtil;
import cn.evake.excel.annotation.ExcelAttribute;
import cn.evake.excel.exception.ExcelRuntimeException;
import cn.evake.excel.util.ExcelUtil;

/**
 * Excel处理类
 * 注:仅支持2010后的excel版本
 * @author wang yi
 * @desc @param <T>
 * @version $Id: ExcelUtil.java, v 0.1 2018年8月16日 下午1:14:12 wang yi Exp $
 */
public class ExcelHanlel<T> implements Serializable {

    private static final long serialVersionUID = 551970754610248636L;

    private Class<T>          clazz;

    public ExcelHanlel(Class<T> clazz) {
        this.clazz = clazz;
    }

    /** 
     * 将excel表单数据源的数据导入到List 
     * @param sheetName 
     *            工作表的名称 
     * @param output 
     *            java输入流 
     */
    public List<T> getExcelToList(String sheetName, InputStream input) {
        List<T> list = new ArrayList<T>();
        try {
            Workbook book = new XSSFWorkbook(input);
            Sheet sheet = null;
            // 如果指定sheet名,则取指定sheet中的内容.  
            if (StringUtils.isNotBlank(sheetName)) {
                sheet = book.getSheet(sheetName);
            }
            // 如果传入的sheet名不存在则默认指向第1个sheet.  
            if (sheet == null) {
                sheet = book.getSheetAt(0);
            }
            // 得到数据的行数  
            int rows = sheet.getLastRowNum();
            // 有数据时才处理  
            if (rows > 0) {
                // 得到类的所有field  
                Field[] allFields = clazz.getDeclaredFields();
                // 定义一个map用于存放列的序号和field  
                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
                for (int i = 0, index = 0; i < allFields.length; i++) {
                    Field field = allFields[i];
                    // 将有注解的field存放到map中  
                    if (field.isAnnotationPresent(ExcelAttribute.class)) {
                        // 设置类的私有字段属性可访问  
                        field.setAccessible(true);
                        fieldsMap.put(index, field);
                        index++;
                    }
                }
                // 从第2行开始取数据,默认第一行是表头  
                for (int i = 1, len = rows; i < len; i++) {
                    // 得到一行中的所有单元格对象.  
                    Row row = sheet.getRow(i);
                    Iterator<Cell> cells = row.cellIterator();
                    T entity = null;
                    int index = 0;
                    while (cells.hasNext()) {
                        // 单元格中的内容.  
                        Cell cell = cells.next();
                        String c = cell.getStringCellValue();
                        if (!StringUtils.isNotBlank(c)) {
                            continue;
                        }
                        if (c.indexOf("合计：") != -1) {
                            continue;
                        }
                        // 如果不存在实例则新建  
                        entity = (entity == null ? clazz.newInstance() : entity);
                        // 从map中得到对应列的field  
                        Field field = fieldsMap.get(cell.getColumnIndex());
                        if (field == null) {
                            continue;
                        }
                        // 取得类型,并根据对象类型设置值.  
                        Class<?> fieldType = field.getType();
                        if (fieldType == null) {
                            continue;
                        }
                        if (String.class == fieldType) {
                            field.set(entity, String.valueOf(c));
                        } else if (BigDecimal.class == fieldType) {
                            c = c.indexOf("%") != -1 ? c.replace("%", "") : c;
                            field.set(entity, BigDecimal.valueOf(Double.valueOf(c)));
                        } else if (Date.class == fieldType) {
                            field.set(entity, DateUtils.parseDate(c));
                        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                            field.set(entity, Integer.parseInt(c));
                        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                            field.set(entity, Long.valueOf(c));
                        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                            field.set(entity, Float.valueOf(c));
                        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                            field.set(entity, Short.valueOf(c));
                        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                            field.set(entity, Double.valueOf(c));
                        } else if (Character.TYPE == fieldType) {
                            if ((c != null) && (c.length() > 0)) {
                                field.set(entity, Character.valueOf(c.charAt(0)));
                            }
                        }

                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }
        } catch (Exception e) {
            throw new ExcelRuntimeException("将excel表单数据源的数据导入到List异常!", e);
        }
        return list;
    }

    /** 
     * Excel读取数据到List对象
     * @param sheetName 
     *            工作表的名称 
     * @param excelFile 
     *            excel文件
     * @throws IOException 
     */
    public List<T> getExcelToList(String sheetName, int startRow,
                                  byte[] excelFile) throws Exception {
        List<T> list = new ArrayList<T>();
        Workbook book = new XSSFWorkbook(new ByteArrayInputStream(excelFile));
        Sheet sheet = null;
        // 如果指定sheet名,则取指定sheet中的内容.  
        if (StringUtils.isNotBlank(sheetName)) {
            sheet = book.getSheet(sheetName);
        }
        // 如果传入的sheet名不存在则默认指向第1个sheet.  
        if (sheet == null) {
            sheet = book.getSheetAt(0);
        }
        // 得到数据的行数  
        int allRows = sheet.getLastRowNum();
        // 有数据时才处理  
        if (allRows > 0) {
            // 得到类的所有field  
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field  
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            for (int i = 0; i < allFields.length; i++) {
                Field field = allFields[i];
                // 将有注解的field存放到map中  
                if (field.isAnnotationPresent(ExcelAttribute.class)) {
                    // 设置类的私有字段属性可访问  
                    field.setAccessible(true);
                    //获取注解
                    ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                    fieldsMap.put(ExcelUtil.getExcelCol(attr.column()), field);
                }
            }
            // 从第startRow行开始取数据,默认第一行是表头
            startRow = startRow - 1;
            for (int i = startRow; i <= allRows; i++) {
                // 得到一行中的所有单元格对象.  
                Row row = sheet.getRow(i);
                Iterator<Cell> cells = row.cellIterator();
                T entity = null;
                while (cells.hasNext()) {
                    // 单元格中的内容.  
                    Cell cell = cells.next();
                    String result = "";
                    //处理数值问题
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
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                double value = cell.getNumericCellValue();
                                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                                result = sdf.format(date);
                            } else {
                                //处理数据
                                if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
                                    double value = cell.getNumericCellValue();
                                    result = value + "";
                                }
                            }
                            break;
                        default:
                            result = cell.getRichStringCellValue().toString() + "";
                            break;
                    }
                    //  判断是否为科学计数法（包含E、e、.等符号）
                    if (result != null && result.contains(".")
                        && (result.contains("E10") || result.contains("+"))) {
                        BigDecimal bd = new BigDecimal(result);
                        result = bd.toString();
                    }
                    // 如果不存在实例则新建  
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field  
                    Field field = fieldsMap.get(cell.getColumnIndex());
                    if (field == null) {
                        continue;
                    }
                    // 取得类型,并根据对象类型设置值.  
                    Class<?> fieldType = field.getType();
                    if (fieldType == null) {
                        continue;
                    }
                    //处理每个字段数据并提示信息
                    try {
                        if (String.class == fieldType) {
                            field.set(entity, String.valueOf(result));
                        } else if (BigDecimal.class == fieldType) {
                            result = result.indexOf("%") != -1 ? result.replace("%", "") : result;
                            field.set(entity, BigDecimal.valueOf(Double.valueOf(result)));
                        } else if (Date.class == fieldType) {
                            field.set(entity, DateUtil.parseStr(result, DateUtil.LONG_WEB_FORMAT));
                        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                            System.err.println(result);
                            field.set(entity, Double.valueOf(result).intValue());
                        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                            field.set(entity, Double.valueOf(result).longValue());
                        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                            field.set(entity, Double.valueOf(result).floatValue());
                        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                            field.set(entity, Double.valueOf(result).shortValue());
                        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                            field.set(entity, Double.valueOf(result).doubleValue());
                        } else if (Character.TYPE == fieldType) {
                            if ((result != null) && (result.length() > 0)) {
                                field.set(entity, Character.valueOf(result.charAt(0)));
                            }
                        }
                    } catch (Exception e) {
                        throw new ExcelRuntimeException(
                            String.format("sheet:%s中第%s行,第%s列,数据格式错误!", sheet.getSheetName(), i + 1,
                                ExcelUtil.getExcelACol(cell.getColumnIndex())),
                            e);
                    }
                }
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 获取通过模板生成的文件
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param list 源数据
     * @param sheetName sheet名称
     * @param startRow 开始行数
     * @param templateFile 模板文件
     * @return
     */
    public byte[] generTemplateExcel(List<T> list, String sheetName, int startRow,
                                     byte[] templateFile) {
        if (CollectionUtils.isEmpty(list)) {
            return templateFile;
        }
        // IO流读取文件  
        Workbook wb = null;
        try {
            // 创建文档 
            InputStream input = new ByteArrayInputStream(templateFile);
            wb = new XSSFWorkbook(input);
            //读取sheet(页)  
            Sheet rsheet = wb.getSheet(sheetName);
            if (rsheet == null) {
                throw new ExcelRuntimeException("sheet:" + sheetName + "不存在");
            }
            // 得到所有定义字段  
            Field[] allFields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();
            // 得到所有field并存放到一个list中  
            for (Field field : allFields) {
                if (field.isAnnotationPresent(ExcelAttribute.class)) {
                    fields.add(field);
                }
            }
            // 产生工作薄对象  
            Workbook workbook = wb;
            // 取出一共有多少个sheet  
            int sheetTotalRow = 0;
            if (list.size() >= 65536) {
                sheetTotalRow = 65536;
            } else {
                sheetTotalRow = list.size();
            }
            // 使用模板工作表对象  
            Sheet sheet = rsheet;
            for (int i = 0; i < sheetTotalRow; i++) {
                //导入数据列
                Cell cell; // 产生单元格  
                Row row;// 产生一行  
                /* *********普通列样式********* */
                Font font = workbook.createFont();
                CellStyle cellStyle = workbook.createCellStyle();
                font.setFontName("Arail narrow"); // 字体  
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *********标红列样式********* */
                Font newFont = workbook.createFont();
                CellStyle newCellStyle = workbook.createCellStyle();
                newFont.setFontName("Arail narrow"); // 字体  
                newFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *************创建内容列*************** */
                font = workbook.createFont();
                cellStyle = workbook.createCellStyle();
                // 写入各条记录,每条记录对应excel表中的一行  
                row = sheet.createRow(i + startRow);
                T vo = (T) list.get(i); // 得到导出对象.  
                for (int j = 0; j < fields.size(); j++) {
                    // 获得field  
                    Field field = fields.get(j);
                    // 设置实体类私有属性可访问  
                    field.setAccessible(true);
                    ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                    int col = j;
                    // 根据指定的顺序获得列号  
                    if (StringUtils.isNotBlank(attr.column())) {
                        col = ExcelUtil.getExcelCol(attr.column());
                    }
                    // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空
                    if (attr.isExport()) {
                        // 创建cell  
                        cell = row.createCell(col);
                        if (attr.isMark()) {
                            newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色  
                            newCellStyle.setFont(newFont);
                            cell.setCellStyle(newCellStyle);
                        } else {
                            font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色  
                            cellStyle.setFont(font);
                            cell.setCellStyle(cellStyle);
                        }
                        // 如果数据存在就填入,不存在填入空格  
                        Class<?> classType = (Class<?>) field.getType();
                        String value = null;
                        if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
                            //处理时间
                            value = DateUtil.formatDate((Date) field.get(vo));
                        }
                        cell.setCellValue(field.get(vo) == null ? ""
                            : value == null ? String.valueOf(field.get(vo)) : value);
                    }
                }
            }
            /* *************创建合计列*************** */
            Row lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                if (attr.isSum()) {
                    int col = i;
                    // 根据指定的顺序获得列号  
                    if (StringUtils.isNotBlank(attr.column())) {
                        col = ExcelUtil.getExcelCol(attr.column());
                    }
                    BigDecimal totalNumber = BigDecimal.ZERO;
                    Cell sumCell = lastRow.createCell(col);
                    sumCell.setCellValue(new HSSFRichTextString("合计：" + totalNumber));
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ExcelRuntimeException("将List数据源的数据导入到excel表单异常!", e);
        }
    }

    /**
     * 生成excle文件
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param list
     * @param sheetName
     * @param startRow
     * @return
     */
    public byte[] generExcel(List<T> list, String sheetName, int startRow) {
        // IO流读取文件  
        Workbook wb = null;
        try {
            // 创建文档 
            wb = new XSSFWorkbook();
            //读取sheet(页)  
            Sheet rsheet = wb.getSheet(sheetName);
            if (rsheet == null) {
                throw new ExcelRuntimeException("sheet:" + sheetName + "不存在");
            }
            // excel中每个sheet中最多有65536行  
            int sheetSize = 65536;
            // 得到所有定义字段  
            Field[] allFields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();
            // 得到所有field并存放到一个list中  
            for (Field field : allFields) {
                if (field.isAnnotationPresent(ExcelAttribute.class)) {
                    fields.add(field);
                }
            }
            // 产生工作薄对象  
            Workbook workbook = wb;
            // 取出一共有多少个sheet  
            int listSize = 0;
            if (list != null && list.size() >= 0) {
                listSize = list.size();
            }
            double sheetNo = Math.ceil(listSize / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                // 使用模板工作表对象  
                Sheet sheet = rsheet;
                // 设置工作表的名称.  
                Cell cell; // 产生单元格  
                Row row;// 产生一行  
                /* *********普通列样式********* */
                Font font = workbook.createFont();
                CellStyle cellStyle = workbook.createCellStyle();
                font.setFontName("Arail narrow"); // 字体  
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *********标红列样式********* */
                Font newFont = workbook.createFont();
                CellStyle newCellStyle = workbook.createCellStyle();
                newFont.setFontName("Arail narrow"); // 字体  
                newFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *************创建内容列*************** */
                font = workbook.createFont();
                cellStyle = workbook.createCellStyle();
                int startNo = index * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                // 写入各条记录,每条记录对应excel表中的一行  
                for (int i = startRow; i < endNo; i++) {
                    row = sheet.createRow(i - startNo);
                    T vo = (T) list.get(i); // 得到导出对象.  
                    for (int j = 0; j < fields.size(); j++) {
                        // 获得field  
                        Field field = fields.get(j);
                        // 设置实体类私有属性可访问  
                        field.setAccessible(true);
                        ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                        int col = j;
                        // 根据指定的顺序获得列号  
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = ExcelUtil.getExcelCol(attr.column());
                        }
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空
                        if (attr.isExport()) {
                            // 创建cell  
                            cell = row.createCell(col);
                            if (attr.isMark()) {
                                newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色  
                                newCellStyle.setFont(newFont);
                                cell.setCellStyle(newCellStyle);
                            } else {
                                font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色  
                                cellStyle.setFont(font);
                                cell.setCellStyle(cellStyle);
                            }
                            // 如果数据存在就填入,不存在填入空格  
                            Class<?> classType = (Class<?>) field.getType();
                            String value = null;
                            if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
                                //处理时间
                                value = DateUtil.formatDate((Date) field.get(vo));
                            }
                            cell.setCellValue(field.get(vo) == null ? ""
                                : value == null ? String.valueOf(field.get(vo)) : value);
                        }
                    }
                }
                /* *************创建合计列*************** */
                Row lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                    if (attr.isSum()) {
                        int col = i;
                        // 根据指定的顺序获得列号  
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = ExcelUtil.getExcelCol(attr.column());
                        }
                        BigDecimal totalNumber = BigDecimal.ZERO;
                        Cell sumCell = lastRow.createCell(col);
                        sumCell.setCellValue(new HSSFRichTextString("合计：" + totalNumber));
                    }
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ExcelRuntimeException("将List数据源的数据导入到excel表单异常!", e);
        }
    }

    /** 
     * 将list数据源的数据导入到excel表单 
     * @param list 
     *            数据源 
     * @param sheetName 
     *            工作表的名称 
     * @param output 
     *            java输出流 
     */
    public boolean getListToExcel(List<T> list, String sheetName, OutputStream output) {
        try {
            // excel中每个sheet中最多有65536行  
            int sheetSize = 65536;
            // 得到所有定义字段  
            Field[] allFields = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();
            // 得到所有field并存放到一个list中  
            for (Field field : allFields) {
                if (field.isAnnotationPresent(ExcelAttribute.class)) {
                    fields.add(field);
                }
            }
            // 产生工作薄对象  
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 取出一共有多少个sheet  
            int listSize = 0;
            if (list != null && list.size() >= 0) {
                listSize = list.size();
            }
            double sheetNo = Math.ceil(listSize / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                // 产生工作表对象  
                HSSFSheet sheet = workbook.createSheet();
                // 设置工作表的名称.  
                workbook.setSheetName(index, sheetName + index);
                HSSFRow row;
                HSSFCell cell;// 产生单元格  
                row = sheet.createRow(0);// 产生一行  
                /* *********普通列样式********* */
                HSSFFont font = workbook.createFont();
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                font.setFontName("Arail narrow"); // 字体  
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *********标红列样式********* */
                HSSFFont newFont = workbook.createFont();
                HSSFCellStyle newCellStyle = workbook.createCellStyle();
                newFont.setFontName("Arail narrow"); // 字体  
                newFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体宽度  
                /* *************创建列头名称*************** */
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                    int col = i;
                    // 根据指定的顺序获得列号  
                    if (StringUtils.isNotBlank(attr.column())) {
                        col = ExcelUtil.getExcelCol(attr.column());
                    }
                    // 创建列  
                    cell = row.createCell(col);
                    if (attr.isMark()) {
                        newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色  
                        newCellStyle.setFont(newFont);
                        cell.setCellStyle(newCellStyle);
                    } else {
                        font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色  
                        cellStyle.setFont(font);
                        cell.setCellStyle(cellStyle);
                    }
                    sheet.setColumnWidth(i, (int) ((attr.name().getBytes().length <= 4 ? 6
                        : attr.name().getBytes().length) * 1.5 * 256));
                    // 设置列中写入内容为String类型  
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    // 写入列名  
                    cell.setCellValue(attr.name());
                    // 如果设置了提示信息则鼠标放上去提示.  
                    if (StringUtils.isNotBlank(attr.prompt())) {
                        setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
                    }
                    // 如果设置了combo属性则本列只能选择不能输入  
                    if (attr.combo().length > 0) {
                        setValidation(sheet, attr.combo(), 1, 100, col, col);
                    }
                }
                /* *************创建内容列*************** */
                font = workbook.createFont();
                cellStyle = workbook.createCellStyle();
                int startNo = index * sheetSize;
                int endNo = Math.min(startNo + sheetSize, listSize);
                // 写入各条记录,每条记录对应excel表中的一行  
                for (int i = startNo; i < endNo; i++) {
                    row = sheet.createRow(i + 1 - startNo);
                    T vo = (T) list.get(i); // 得到导出对象.  
                    for (int j = 0; j < fields.size(); j++) {
                        // 获得field  
                        Field field = fields.get(j);
                        // 设置实体类私有属性可访问  
                        field.setAccessible(true);
                        ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                        int col = j;
                        // 根据指定的顺序获得列号  
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = ExcelUtil.getExcelCol(attr.column());
                        }
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.  
                        if (attr.isExport()) {
                            // 创建cell  
                            cell = row.createCell(col);
                            if (attr.isMark()) {
                                newFont.setColor(HSSFFont.COLOR_RED); // 字体颜色  
                                newCellStyle.setFont(newFont);
                                cell.setCellStyle(newCellStyle);
                            } else {
                                font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色  
                                cellStyle.setFont(font);
                                cell.setCellStyle(cellStyle);
                            }
                            // 如果数据存在就填入,不存在填入空格  
                            Class<?> classType = (Class<?>) field.getType();
                            String value = null;
                            if (field.get(vo) != null && classType.isAssignableFrom(Date.class)) {
                                /*  SimpleDateFormat sdf = new SimpleDateFormat(
                                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);*/
                                value = String.valueOf(field.get(vo));
                            }
                            cell.setCellValue(field.get(vo) == null ? ""
                                : value == null ? String.valueOf(field.get(vo)) : value);
                        }
                    }
                }
                /* *************创建合计列*************** */
                HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
                    if (attr.isSum()) {
                        int col = i;
                        // 根据指定的顺序获得列号  
                        if (StringUtils.isNotBlank(attr.column())) {
                            col = ExcelUtil.getExcelCol(attr.column());
                        }
                        BigDecimal totalNumber = BigDecimal.ZERO;
                        for (int j = 1, len = (sheet.getLastRowNum() - 1); j < len; j++) {
                            HSSFRow hssfRow = sheet.getRow(j);
                            if (hssfRow != null) {
                                /*    HSSFCell hssfCell = hssfRow.getCell(col);*/
                            }
                        }
                        HSSFCell sumCell = lastRow.createCell(col);
                        sumCell.setCellValue(new HSSFRichTextString("合计：" + totalNumber));
                    }
                }
            }
            output.flush();
            workbook.write(output);
            output.close();
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new ExcelRuntimeException("将list数据源的数据导入到excel表单异常!", e);
        }

    }

    /** 
     * 设置单元格上提示 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param promptTitle 
     *            标题 
     * @param promptContent 
     *            内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */
    public static Sheet setHSSFPrompt(Sheet sheet, String promptTitle, String promptContent,
                                      int firstRow, int endRow, int firstCol, int endCol) {
        // 构造constraint对象  
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象  
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /** 
     * 设置某些列的值只能输入预制的数据,显示下拉框. 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param textlist 
     *            下拉框显示的内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */
    public static Sheet setValidation(Sheet sheet, String[] textlist, int firstRow, int endRow,
                                      int firstCol, int endCol) {
        // 加载下拉列表内容  
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象  
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

}