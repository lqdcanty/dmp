/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.model.OpenScoreRankModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.result.OpenEnrollQueryResult;
import com.efida.sport.open.result.OpenEnrollSubmitResult;
import com.efida.sport.open.result.OpenEnrollxQueryResult;
import com.efida.sport.open.result.OpenEnrollxSubmitResult;
import com.efida.sport.open.result.OpenMatchQueryResult;
import com.efida.sport.open.result.OpenProjectQueryResult;
import com.efida.sport.open.result.OpenScoreQueryResult;
import com.efida.sport.open.result.OpenScoreRankQueryResult;
import com.efida.sport.open.result.OpenScoreRankSubmitResult;
import com.efida.sport.open.result.OpenScoreSubmitResult;
import com.efida.sport.open.util.DateUtil;
import com.efida.sport.open.util.HttpUtils;
import com.efida.sport.open.util.Md5Util;
import com.efida.sport.open.util.StringUtils;

/**
 * 开放接口api工具。
 * 
 * 
 * @author zhiyang
 * @version $Id: OpenApi.java, v 0.1 2018年6月5日 下午6:47:00 zhiyang Exp $
 */
public class OpenApi {
    /**
     * 日志
     */
    private final static Logger logger   = LoggerFactory.getLogger(OpenApi.class);

    /**
     * 版本号
     */
    private final String        version  = "1.6";
    /**
     * unitCode，承办方
     */
    private String              unitCode;
    /**
     * 私钥
     */
    private String              privateKey;

    /**
     * 开放服务主机地址
     */
    private String              openHost = "api.hzsport.com";

    /**
     * Getter method for property <tt>openHost</tt>.
     * 
     * @return property value of openHost
     */
    public String getOpenHost() {
        return openHost;
    }

    /**
     * Setter method for property <tt>openHost</tt>.
     * 
     * @param openHost value to be assigned to property openHost
     */
    public void setOpenHost(String openHost) {
        this.openHost = openHost;
    }

    /**
     * 查询 project 信息
     * 
     * /open/projects/list
     * 
     * @param unitCode
     * @return
     * @throws OpenException 
     */
    public OpenProjectQueryResult queryProjects() throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/projects/list?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;
        String signData = unitCode + privateKey + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;

        String resdata = HttpUtils.executePost(postUrl, null);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenProjectQueryResult result = JSON.toJavaObject(json, OpenProjectQueryResult.class);

        return result;
    }

    /**
     * 查询赛事列表
     * 
     * @param matchCode 赛事编号 可以传空
     * @param matchStatus @see MatchStatusEnum  传空则代表查询所有
     * @param beginTimeMin  赛事开始时间最小值
     * @param beginTimeMax  赛事开始时间最大值
     * @param endTimeMin 赛事结束时间范围最小值
     * @param endTimeMax 赛事结束时间范围最大值
     * @param pageNumber  页码
     * @param pageSize   单页大小
     * @return
     * @throws OpenException
     */
    public OpenMatchQueryResult queryMatchList(String matchCode, String matchStatus,
                                               String beginTimeMin, String beginTimeMax,
                                               String endTimeMin, String endTimeMax, int pageNumber,
                                               int pageSize) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/match/get?v=" + version + "&unitCode="
                         + unitCode + "&timestamp=" + timestamp;
        matchCode = emptyStr(matchCode);
        matchStatus = emptyStr(matchStatus);

        beginTimeMin = emptyStr(beginTimeMin);
        beginTimeMax = emptyStr(beginTimeMax);
        endTimeMin = emptyStr(endTimeMin);
        endTimeMax = emptyStr(endTimeMax);
        Map<String, String> map = new HashMap<String, String>();
        map.put("matchCode", matchCode);
        map.put("matchStatus", matchStatus);
        map.put("beginTimeMin", beginTimeMin);
        map.put("beginTimeMax", beginTimeMax);
        map.put("endTimeMin", endTimeMin);
        map.put("endTimeMax", endTimeMax);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");
        String signData = unitCode + privateKey + matchCode + matchStatus + beginTimeMin
                          + beginTimeMax + endTimeMin + endTimeMax + pageNumber + pageSize
                          + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenMatchQueryResult result = JSON.toJavaObject(json, OpenMatchQueryResult.class);

        return result;
    }

    /**
     * 查询报名信息
     * (过时,建议不使用)
     * 
     * @param playerCode  运动员编号， 可以为空
     * @param matchCode   赛事编号， 可以为空
     * @param fieldCode  赛场编号， 可以为空
     * @param matchGroupCode 分组编号， 可以为空
     * @param eventCode  比赛项编号， 可以为空
     * @param playerPhone 运动员手机号 可以为空
     * @param pageNumber  页码。 不能为空
     * @param pageSize  单页返回大小。 不能为空
     * @return
     * @throws OpenException
     */
    public OpenEnrollQueryResult queryEnrollList(String playerCode, String matchCode,
                                                 String fieldCode, String matchGroupCode,
                                                 String eventCode, String playerPhone,
                                                 int pageNumber,
                                                 int pageSize) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/enroll/list?v=" + version + "&unitCode="
                         + unitCode + "&timestamp=" + timestamp;

        playerCode = emptyStr(playerCode);
        matchCode = emptyStr(matchCode);
        fieldCode = emptyStr(fieldCode);
        matchGroupCode = emptyStr(matchGroupCode);
        eventCode = emptyStr(eventCode);

        playerPhone = emptyStr(playerPhone);

        Map<String, String> map = new HashMap<String, String>();
        map.put("playerCode", playerCode);
        map.put("matchCode", matchCode);
        map.put("fieldCode", fieldCode);
        map.put("matchGroupCode", matchGroupCode);
        map.put("eventCode", eventCode);
        map.put("playerPhone", playerPhone);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");

        String signData = unitCode + privateKey + playerCode + matchCode + fieldCode
                          + matchGroupCode + eventCode + playerPhone + pageNumber + pageSize
                          + timestamp;

        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        System.out.println(resdata);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenEnrollQueryResult result = JSON.toJavaObject(json, OpenEnrollQueryResult.class);

        return result;
    }

    /**
     * 提交报名信息
     * (过时，建议不使用)
     * @param enrollItems
     * @return
     * @throws OpenException
     */
    public OpenEnrollSubmitResult submitEnroll(List<OpenEnrollModel> items) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/enroll/submit?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;

        if (items == null || items.size() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "报名记录不能为空，至少需要一条报名信息！ ");
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(items);
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", jsonArray.toJSONString());
        map.put("count", items.size() + "");

        String signData = unitCode + privateKey + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        System.out.println(resdata);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenEnrollSubmitResult result = JSON.toJavaObject(json, OpenEnrollSubmitResult.class);
        return result;
    }

    /**
     * 提交成绩信息接口
     * 
     * @param items
     * @return
     * @throws OpenException
     */
    public OpenScoreSubmitResult submitScore(List<OpenScoreModel> items) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/score/submit?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;

        if (items == null || items.size() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "成绩记录不能为空，至少需要一条成绩信息！ ");
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(items);
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", jsonArray.toJSONString());
        map.put("count", items.size() + "");
        String signData = unitCode + privateKey + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        System.out.println(resdata);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenScoreSubmitResult result = JSON.toJavaObject(json, OpenScoreSubmitResult.class);
        return result;
    }

    /**
    * 查询成绩接口
    * 
    * @param matchCode
    * @param eventCode
    * @param playerCode
    * @param scoreName
    * @param playerPhone 运动员手机号
    * @param pageNumber
    * @param pageSize
    * @return
    * @throws OpenException
    */
    public OpenScoreQueryResult queryScoreList(String matchCode, String eventCode,
                                               String playerCode, String scoreName,
                                               String playerPhone, int pageNumber,
                                               int pageSize) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/score/list?v=" + version + "&unitCode="
                         + unitCode + "&timestamp=" + timestamp;

        matchCode = emptyStr(matchCode);
        eventCode = emptyStr(eventCode);
        playerCode = emptyStr(playerCode);
        scoreName = emptyStr(scoreName);
        playerPhone = emptyStr(playerPhone);

        Map<String, String> map = new HashMap<String, String>();
        map.put("matchCode", matchCode);
        map.put("eventCode", eventCode);
        map.put("playerCode", playerCode);
        map.put("scoreName", scoreName);
        map.put("playerPhone", playerPhone);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");

        String signData = unitCode + privateKey + matchCode + eventCode + playerCode + playerPhone
                          + pageNumber + pageSize + timestamp;

        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenScoreQueryResult result = JSON.toJavaObject(json, OpenScoreQueryResult.class);

        return result;
    }

    /**
     * 提交赛事成绩名次接口
     * 
     * @param matchCode
     * @param items
     * @return
     * @throws OpenException
     */
    public OpenScoreRankSubmitResult submitScoreRank(String matchCode,
                                                     List<OpenScoreRankModel> items) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/score/rank/submit?v=" + version
                         + "&unitCode=" + unitCode + "&matchCode=" + matchCode + "&timestamp="
                         + timestamp;

        if (matchCode == null || matchCode.length() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "赛事编号不能为空！");
        }
        if (items == null || items.size() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "成绩名次信息不能为空，至少需要一条成绩名次信息！ ");
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(items);
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", jsonArray.toJSONString());
        map.put("count", items.size() + "");

        String signData = unitCode + matchCode + privateKey + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenScoreRankSubmitResult result = JSON.toJavaObject(json, OpenScoreRankSubmitResult.class);

        return result;
    }

    /**
     * 查询 成绩名次信息
     * 
     * @param matchCode
     * @param eventCode
     * @param playerCode
     * @param scoreName
     * @param playerPhone 运动员手机号
     * @param pageNumber
     * @param pageSize
     * @return
     * @throws OpenException
     */
    public OpenScoreRankQueryResult queryScoreRankList(String matchCode, String eventCode,
                                                       String playerCode, String scoreName,
                                                       String playerPhone, int pageNumber,
                                                       int pageSize) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/score/rank/list?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;

        matchCode = emptyStr(matchCode);
        eventCode = emptyStr(eventCode);
        playerCode = emptyStr(playerCode);
        scoreName = emptyStr(scoreName);
        playerPhone = emptyStr(playerPhone);

        Map<String, String> map = new HashMap<String, String>();
        map.put("matchCode", matchCode);
        map.put("eventCode", eventCode);
        map.put("playerCode", playerCode);
        map.put("scoreName", scoreName);
        map.put("playerPhone", playerPhone);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");

        String signData = unitCode + privateKey + matchCode + eventCode + playerCode + scoreName
                          + playerPhone + pageNumber + pageSize + timestamp;

        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenScoreRankQueryResult result = JSON.toJavaObject(json, OpenScoreRankQueryResult.class);

        return result;
    }

    /**
     * 提交报名信息(新接口)
     *  支持团体报名
     * 路径：/open/enrollx/submit
    @param enrollItems
     * @return
     * @throws OpenException
     */
    public OpenEnrollxSubmitResult submitEnrollx(List<OpenEnrollxModel> items) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/enrollx/submit?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;

        if (items == null || items.size() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "报名记录不能为空，至少需要一条报名信息！ ");
        }
        JSONArray jsonArray = (JSONArray) JSON.toJSON(items);
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", jsonArray.toJSONString());
        map.put("count", items.size() + "");

        String signData = unitCode + privateKey + timestamp;
        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        System.out.println(resdata);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenEnrollxSubmitResult result = JSON.toJavaObject(json, OpenEnrollxSubmitResult.class);

        return result;
    }

    /**
     * 报名信息查询（新接口）
     *  支持团体报名查询。
     *  路径：/open/enrollx/list
     * @param applyCode
     * @param matchCode
     * @param fieldCode
     * @param matchGroupCode
     * @param eventCode
     * @param leaderPhone
     * @param playerPhone
     * @param pageNumber
     * @param pageSize
     * @return
     * @throws OpenException
     */
    public OpenEnrollxQueryResult queryEnrollxList(String applyCode, String matchCode,
                                                   String fieldCode, String matchGroupCode,
                                                   String eventCode, String leaderPhone,
                                                   String playerPhone, int pageNumber,
                                                   int pageSize) throws OpenException {

        checkOpenParam();
        String timestamp = DateUtil.format(new Date(), DateUtil.LONG_FORMAT);
        String postUrl = "http://" + this.openHost + "/open/enrollx/list?v=" + version
                         + "&unitCode=" + unitCode + "&timestamp=" + timestamp;

        applyCode = emptyStr(applyCode);
        matchCode = emptyStr(matchCode);
        fieldCode = emptyStr(fieldCode);
        matchGroupCode = emptyStr(matchGroupCode);
        eventCode = emptyStr(eventCode);
        leaderPhone = emptyStr(leaderPhone);
        playerPhone = emptyStr(playerPhone);

        Map<String, String> map = new HashMap<String, String>();
        map.put("applyCode", applyCode);
        map.put("matchCode", matchCode);
        map.put("fieldCode", fieldCode);
        map.put("matchGroupCode", matchGroupCode);
        map.put("eventCode", eventCode);
        map.put("leaderPhone", leaderPhone);
        map.put("playerPhone", playerPhone);
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");

        String signData = unitCode + privateKey + applyCode + matchCode + fieldCode + matchGroupCode
                          + eventCode + leaderPhone + playerPhone + pageNumber + pageSize
                          + timestamp;

        String sign = Md5Util.signature(signData);
        postUrl += "&sign=" + sign;
        String resdata = HttpUtils.executePost(postUrl, map);
        System.out.println(resdata);
        logger.info("return data:\n" + resdata);
        JSONObject json = JSONObject.parseObject(resdata);
        OpenEnrollxQueryResult result = JSON.toJavaObject(json, OpenEnrollxQueryResult.class);

        return result;
    }

    private String emptyStr(String machCode) {

        if (machCode == null) {
            return "";
        }

        return machCode;
    }

    private void checkOpenParam() throws OpenException {
        if (StringUtils.isEmpty(this.unitCode)) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "unitCode为空,请初始化unitCode! ");
        }
        if (StringUtils.isEmpty(this.privateKey)) {
            throw new OpenException(ErrorCode.PARAMETER_EMPTY, "privateKey为空,请初始化privateKey! ");
        }
    }

    /**
     * Getter method for property <tt>unitCode</tt>.
     * 
     * @return property value of unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * Setter method for property <tt>unitCode</tt>.
     * 
     * @param unitCode value to be assigned to property unitCode
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * Getter method for property <tt>version</tt>.
     * 
     * @return property value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Getter method for property <tt>privateKey</tt>.
     * 
     * @return property value of privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {

        this.privateKey = privateKey;
    }

}
