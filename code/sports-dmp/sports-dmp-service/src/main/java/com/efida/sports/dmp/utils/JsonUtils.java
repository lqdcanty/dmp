package com.efida.sports.dmp.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * JSON工具辅助类
 * @author wang yi
 * @desc 
 * @version $Id: JsonUtils.java, v 0.1 2018年6月26日 下午7:39:52 wang yi Exp $
 */
public class JsonUtils {

    /**
     * 判断是否是JOSNArry数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param Str
     * @return
     */
    public static boolean isJSONArray(String str) {
        try {
            JSONArray array = JSONObject.parseArray(str);
            if (array == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
