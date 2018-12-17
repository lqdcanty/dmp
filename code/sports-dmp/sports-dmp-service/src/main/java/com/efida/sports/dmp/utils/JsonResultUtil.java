package com.efida.sports.dmp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efida.sports.dmp.constants.Constants;
import com.efida.sports.dmp.enums.ErrorCodeEnum;
import com.efida.sports.dmp.enums.ResultCodeEnum;

/**
 * 
 * @author zoutao
 * @version $Id: OperationType.java, v 0.1 2017年5月5日 下午2:01:22 zoutao Exp $
 */
public class JsonResultUtil {

    public static Map<String, Object> getFailResult(int code, String resultMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, code);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        result.put(Constants.RESULT, new HashMap<String, Object>());
        return result;
    }

    public static Map<String, Object> getFailResult(String code, String resultMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, code);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        result.put(Constants.RESULT, new HashMap<String, Object>());
        return result;
    }

    public static Map<String, Object> getFailResult(String resultMsg,
                                                    Map<String, Object> resultObject) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, Constants.FAIL);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        result.put(Constants.RESULT, resultObject);
        return result;
    }

    /**
     * 获取服务器错误的jsonresult map
     * 
     * @param resultMsg
     * @return
     */
    public static Map<String, Object> getErroCodeResult(int code, String resultMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, code);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        return result;
    }

    /**
     * 获取服务器错误的jsonresult map
     * 
     * @param resultMsg
     * @return
     */
    public static Map<String, Object> getServerErrorResult(String resultMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, ErrorCodeEnum.SERVER_ERROR.getCode());
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        return result;
    }

    /**
     * 获取请求参数错误的jsonresult map
     * 
     * @param resultMsg
     * @return
     */
    public static Map<String, Object> getParamErrorExceptionResult(String resultMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, ErrorCodeEnum.ILLEGAL_PARAMAS.getCode());
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        return result;
    }

    /**
     *   获取请求操作成功的jsonresult map
     * 
     * @param resultMsg
     * @param resultObject
     * @return
     */
    public static Map<String, Object> getSuccessResult(String resultMsg,
                                                       Map<String, Object> resultObject) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, ResultCodeEnum.SUCCESS);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (resultObject == null) {
            resultObject = new HashMap<String, Object>();
        }
        result.put(Constants.RESULT, resultObject);
        return result;
    }

    /**
     *   获取请求操作成功的jsonresult map
     * 
     * @param resultMsg
     * @param resultObject
     * @return
     */
    public static Map<String, Object> getSuccessOpenApiResult(String resultMsg, List resultObject) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, ResultCodeEnum.SUCCESS);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (resultObject == null) {
            resultObject = new ArrayList<Object>();
        }
        result.put("data", resultObject);
        result.put("count", resultObject.size());
        return result;
    }

    /**
     *   获取请求操作成功的jsonresult map
     * 
     * @param resultMsg
     * @param resultObject
     * @return
     */
    public static Map<String, Object> getSuccessResult(String resultMsg, JSONObject object) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, Constants.SUCCESS);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (object == null) {
            object = new JSONObject();
        }
        result.put(Constants.RESULT, object);
        return result;
    }

    /**
     *   获取请求操作成功的jsonresult map
     * 
     * @param resultMsg
     * @param resultObject
     * @return
     */
    public static Map<String, Object> getSuccessResult(String resultMsg, JSONArray object) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, Constants.SUCCESS);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (object == null) {
            object = new JSONArray();
        }
        result.put(Constants.RESULT, object);
        return result;
    }

    /**
     * 获取服务器错误的jsonresult map
     * 
     * @param code
     * @param resultMsg
     * @param maps
     * @return
     */
    public static Map<String, Object> getErroCodeResult(int code, String resultMsg,
                                                        Map<String, Object> maps) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, code);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (maps == null) {
            maps = new HashMap<String, Object>();
        }
        result.put(Constants.RESULT, maps);
        return result;
    }

    /**
     * 获取请求操作成功的jsonresult map
     * 
     * @param resultObject
     * @return
     */
    public static Map<String, Object> getSuccessResult(Map<String, Object> resultObject) {
        return getSuccessResult("请求操作成功", resultObject);
    }

    /**
     * @param object
     * @return
     */
    public static String toJSONString(Map<String, Object> object) {
        return JSON.toJSONString(object);
    }

    /**
     * 获取请求操作成功的jsonresult map
     * 
     * @return
     */
    public static Map<String, Object> getSuccessResult() {
        return getSuccessResult("请求操作成功", new HashMap<String, Object>());
    }

    /**
     * 获取请求操作成功的jsonresult map
     * 
     * @return
     */
    public static Map<String, Object> getSuccessResult(String resultMsg) {
        return getSuccessResult(resultMsg, new JSONObject());
    }

    /**
     * 获取请求操作成功的jsonresult map
     * 
     * @return
     */
    public static Map<String, Object> getSuccessResult(String code, String resultMsg,
                                                       JSONObject object) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constants.RESULTCODE, code);
        result.put(Constants.RESULTMSG, resultMsg == null ? "" : resultMsg);
        if (object == null) {
            object = new JSONObject();
        }
        result.put(Constants.RESULT, object);
        return result;
    }

}
