/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.excel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.evake.auth.util.underline.Underline2Camel;

/**
 * 
 * @author wang yi
 * @desc
 * @version $Id: ExcelHandelVo.java, v 0.1 2018年8月3日 下午8:07:29 wang yi Exp $
 */
public class ExcelAnalyUtils {

    private byte[] excleFile;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 解析Excel模板文件
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sheetName
     * @return 对应的对象数据映射的数据
     */
    public List<Map<String, Object>> analyExcelTemplate(String sheetName) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> templateJson = ExcelUtil.readByteXlsxWithSheet(excleFile,
            sheetName);
        long endTime = System.currentTimeMillis();
        logger.info("ExcelHandel analysis excel sheet_name:{} spends:{}ms", sheetName,
            endTime - startTime);
        List<Map<String, Object>> resultVos = new ArrayList<Map<String, Object>>();
        // 转换新表头
        Map<String, Object> newTitle = parseCamelTitle(templateJson.get(0));
        // 删除表头
        templateJson.remove(0);
        for (Map<String, Object> map : templateJson) {
            // 将原始表头映射成数据模型字段
            Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
            for (Entry<String, Object> titleMap : newTitle.entrySet()) {
                for (Entry<String, Object> enrollInfoExcelVo : map.entrySet()) {
                    if (titleMap.getKey().equals(enrollInfoExcelVo.getKey())) {
                        // 值为空时跳过数据
                        if (enrollInfoExcelVo.getValue() == null) {
                            continue;
                        }
                        newEnrollInfoMap.put(titleMap.getValue().toString(),
                            enrollInfoExcelVo.getValue());
                    }
                }
            }
            if (!newEnrollInfoMap.isEmpty()) {
                resultVos.add(newEnrollInfoMap);
            }
        }
        return resultVos;
    }

    /**
     * 解析Excel模板文件
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sheetName
     * @return 对应的对象数据映射的数据
     */
    public List<Map<String, Object>> analyExcelTemplate(String sheetName, String extPrefix) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> templateJson = ExcelUtil.readByteXlsxWithSheet(excleFile,
            sheetName);
        long endTime = System.currentTimeMillis();
        logger.info("ExcelHandel analysis excel sheet_name:{} spends:{}ms", sheetName,
            endTime - startTime);
        List<Map<String, Object>> resultVos = new ArrayList<Map<String, Object>>();
        // 转换新表头
        Map<String, Object> newTitle = parseCamelTitle(templateJson.get(0));
        // 删除表头
        templateJson.remove(0);
        for (Map<String, Object> map : templateJson) {
            // 将原始表头映射成数据模型字段
            Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
            for (Entry<String, Object> titleMap : newTitle.entrySet()) {
                for (Entry<String, Object> enrollInfoExcelVo : map.entrySet()) {
                    if (titleMap.getKey().equals(enrollInfoExcelVo.getKey())) {
                        // 值为空时跳过数据
                        if (enrollInfoExcelVo.getValue() == null) {
                            continue;
                        }
                        newEnrollInfoMap.put(titleMap.getValue().toString(),
                            enrollInfoExcelVo.getValue());
                    }
                }
            }
            //当列表数据不为空可添加拓展数据
            if (newEnrollInfoMap.size() > 1) {
                if (extPrefix.equals("score_prop_")) {
                    Map<String, Object> extProp = getExtProp(newTitle, map, "score_prop_");
                    if (!extProp.isEmpty()) {
                        newEnrollInfoMap.put("scoreProp", JSON.toJSONString(extProp));
                    }
                    Map<String, Object> extPropp = getExtProp(newTitle, map, "player_prop_");
                    if (!extPropp.isEmpty()) {
                        newEnrollInfoMap.put("playerProp", JSON.toJSONString(extPropp));
                    }
                }
                Map<String, Object> extProp = getExtProp(newTitle, map, extPrefix);
                if (!extProp.isEmpty()) {
                    newEnrollInfoMap.put("extProp", extProp);
                }
            }
            if (!newEnrollInfoMap.isEmpty()) {
                resultVos.add(newEnrollInfoMap);
            }
        }
        return resultVos;
    }

    /**
     *获取拓展属性
     *修改解析模式
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param map
     * @param extPrefix
     * @return
     */
    private Map<String, Object> getExtProp(Map<String, Object> newTitle, Map<String, Object> map,
                                           String extPrefix) {
        // 将原始表头映射成数据模型字段
        Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
        for (Entry<String, Object> titleMap : newTitle.entrySet()) {
            if (titleMap.getValue().toString().contains(extPrefix)) {
                newEnrollInfoMap.put(titleMap.getValue().toString(),
                    map.get(titleMap.getKey()) == null ? "" : map.get(titleMap.getKey()));
            }
        }
        return newEnrollInfoMap;
    }

    /**
     * 转换excel表头
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param map
     * @return
     */
    private Map<String, Object> parseCamelTitle(Map<String, Object> map) {
        // 转换表头
        Map<String, Object> newTitle = new HashMap<String, Object>();
        for (Entry<String, Object> titleVo : map.entrySet()) {
            newTitle.put(titleVo.getKey(),
                Underline2Camel.underline2Camel(titleVo.getValue().toString(), true));
        }
        return newTitle;
    }

    public ExcelAnalyUtils(byte[] excleFile) {
        super();
        this.excleFile = excleFile;
    }

    public byte[] getExcleFile() {
        return excleFile;
    }

    public void setExcleFile(byte[] excleFile) {
        this.excleFile = excleFile;
    }

}
