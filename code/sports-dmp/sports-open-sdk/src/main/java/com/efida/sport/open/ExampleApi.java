/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenPlayerModel;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.model.OpenScoreRankModel;
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

/**
 * 测试用例
 * @author zhiyang
 * @version $Id: ExampleApi.java, v 0.1 2018年6月6日 下午12:38:45 zhiyang Exp $
 */
public class ExampleApi {

    //上线后正式域名：api.zntyydh.com
    //localhost:18081
    //目前测试环境域名：zntydmp.efida.com.cn
    public static String host = "zntydmp.efida.com.cn";

    //测试获取项目接口
    public void testQueryProject() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);
        OpenProjectQueryResult result = api.queryProjects();
        System.out.println(JSON.toJSON(result).toString());
    }

    //测试获取赛事信息接口
    public void testQueryMatchList() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        String beginTimeMax = null;
        String matchCode = "";
        String beginTimeMin = null;
        String matchStatus = null;
        String endTimeMin = null;
        int pageNumber = 1;
        int pageSize = 20;
        String endTimeMax = null;

        OpenMatchQueryResult result = api.queryMatchList(matchCode, matchStatus, beginTimeMin,
            beginTimeMax, endTimeMin, endTimeMax, pageNumber, pageSize);
        System.out.println(JSON.toJSON(result).toString());
    }

    //测试提交运动员报名信息接口
    public void testSubmitEnroll() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);
        List<OpenEnrollModel> items = new ArrayList<OpenEnrollModel>();
        OpenEnrollModel item = new OpenEnrollModel();
        item.setApplyTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode("project201806141710317965");
        item.setGameName("game name");
        item.setMatchCode("match201807161550557254");
        item.setMatchName("赛事名称" + System.currentTimeMillis());
        item.setPlayerName("姓名");
        item.setEventCode("item201807251524459494");
        item.setMatchGroupCode("group201807251525084113");
        item.setMatchGroupName("团体组");
        item.setPlayerPhone("18628380221");
        item.setEventName("比赛项名称");
        item.setFieldCode("field201807161553552360");
        item.setFieldName("成都赛");
        item.setEntryFee(Math.random() * 100 + 0.1);
        item.setOpenId("openid" + System.currentTimeMillis());
        item.setOpenPlatType("qq");
        item.setPlayerCategory("square");

        JSONObject extProp = new JSONObject();
        extProp.put("兴趣爱好", "看电影");
        JSONArray array = new JSONArray();
        array.add("跑步");
        array.add("唱歌");
        extProp.put("特长", array);
        item.setExtProp(extProp);
        item.setModifyFlag("1");
        String uuid = java.util.UUID.randomUUID().toString();
        uuid = "testmodify001";
        item.setIdempotentId(uuid);
        items.add(item);
        OpenEnrollSubmitResult result = api.submitEnroll(items);
        System.out.println(JSON.toJSONString(result));
    }

    //测试提交运动员报名信息接口
    public void testSubmitEnrollx() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);
        List<OpenEnrollxModel> items = new ArrayList<OpenEnrollxModel>();
        OpenEnrollxModel item = new OpenEnrollxModel();
        item.setApplyTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode("project201806141710317965");
        item.setGameName("game name");
        item.setMatchCode("match201807161550557254");
        item.setMatchName("赛事名称" + System.currentTimeMillis());
        item.setEventCode("item201807251524459494");
        item.setMatchGroupCode("group201807251525084113");
        item.setMatchGroupName("团体组");
        item.setEventName("比赛项名称");
        item.setFieldCode("field201807161553552360");
        item.setFieldName("成都赛");
        item.setEntryFee(Math.random() * 100 + 0.1);

        List<OpenPlayerModel> playerData = new ArrayList<OpenPlayerModel>();

        OpenPlayerModel player = new OpenPlayerModel();
        player.setPlayerPhone("18628380221");
        player.setPlayerName("姓名");
        player.setOpenId("openid" + System.currentTimeMillis());
        player.setOpenPlatType("qq");
        player.setPlayerCategory("square");

        JSONObject extProp = new JSONObject();
        extProp.put("兴趣爱好", "看电影");
        JSONArray array = new JSONArray();
        array.add("跑步");
        array.add("唱歌");
        extProp.put("特长", array);
        player.setExtProp(extProp);
        playerData.add(player);

        item.setModifyFlag("1");
        String uuid = java.util.UUID.randomUUID().toString();
        uuid = "testmodify001";
        item.setIdempotentId(uuid);
        item.setPlayerData(playerData);

        items.add(item);
        OpenEnrollxSubmitResult result = api.submitEnrollx(items);
        System.out.println(JSON.toJSONString(result));
    }

    //测试提交运动员报名信息接口
    public void testSubmitEnrollx_tuanti() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);
        List<OpenEnrollxModel> items = new ArrayList<OpenEnrollxModel>();
        OpenEnrollxModel item = new OpenEnrollxModel();
        item.setApplyTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode("project201806141710317965");
        item.setGameName("game name");
        item.setMatchCode("match201807161550557254");
        item.setMatchName("赛事名称" + System.currentTimeMillis());
        item.setEventCode("item201807251524459494");
        item.setMatchGroupCode("group201807251525084113");
        item.setMatchGroupName("团体组");
        item.setEventName("比赛项名称");
        item.setFieldCode("field201807161553552360");
        item.setFieldName("成都赛");
        item.setEntryFee(Math.random() * 100 + 0.1);
        item.setEventType("tuanti");
        item.setRegistrationNum(3);
        item.setLeaderName("领队姓名");
        item.setLeaderPhone("18732323233");

        List<OpenPlayerModel> playerData = new ArrayList<OpenPlayerModel>();
        for (int i = 0; i < item.getRegistrationNum(); i++) {
            OpenPlayerModel player = new OpenPlayerModel();
            player.setPlayerPhone("18628380221");
            player.setPlayerName("姓名" + i);
            player.setOpenId("openid" + System.currentTimeMillis());
            player.setOpenPlatType("qq");
            player.setPlayerCategory("square");

            JSONObject extProp = new JSONObject();
            extProp.put("兴趣爱好", "看电影");
            JSONArray array = new JSONArray();
            array.add("跑步");
            array.add("唱歌");
            extProp.put("特长", array);
            player.setExtProp(extProp);
            player.setPlayerNo(i + 1);
            playerData.add(player);

        }

        item.setModifyFlag("1");
        String uuid = java.util.UUID.randomUUID().toString();
        uuid = "testmodify001";
        item.setIdempotentId(uuid);
        item.setPlayerData(playerData);

        items.add(item);
        OpenEnrollxSubmitResult result = api.submitEnrollx(items);
        System.out.println(JSON.toJSONString(result));
    }

    //测试推送（查询）运动员报名信息接口
    public void testEnrollQuery() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        String playerCode = null;
        String matchCode = "match201807161550557254";
        String fieldCode = null;

        String playerPhone = "";
        OpenEnrollQueryResult result = api.queryEnrollList(playerCode, matchCode, fieldCode, "", "",
            playerPhone, 1, 20);
        System.out.println(JSON.toJSON(result).toString());
    }

    //测试推送（查询）运动员报名信息接口
    public void testEnrollxQuery() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        String leaderPhone = "18732323233";
        String matchCode = "match201807161550557254";
        String fieldCode = null;

        String playerPhone = "18628380221";
        OpenEnrollxQueryResult result = api.queryEnrollxList(null, matchCode, fieldCode, null, null,
            leaderPhone, playerPhone, 1, 20);

        System.out.println(JSON.toJSON(result).toString());
    }

    //测试提交成绩接口
    public void testSubmitScore() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);
        List<OpenScoreModel> items = new ArrayList<OpenScoreModel>();
        OpenScoreModel item = new OpenScoreModel();
        item.setPlayerCode("playerCode");
        item.setPlayerName("李小刚");
        item.setPlayerPhone("18628380221");
        item.setSex("m");
        item.setPartTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode("gamecode");
        item.setGameName("game name");
        item.setMatchCode("test-match-code");
        item.setMatchName("跳绳大赛");
        item.setFieldCode("fieldCode");
        item.setFieldName("赛场成都");
        item.setMatchGroupCode("boy");
        item.setMatchGroupName("男子跳远");
        item.setEventCode("test-event-code");
        item.setEventName("立定跳远");
        item.setScore(3.32);
        item.setScoreName("男子跳远");
        item.setScoreUnit("m");
        item.setScoreType("big");
        item.setScoreDesc("2.32米");
        item.setIsValid((byte) 1);
        JSONObject scoreProp = new JSONObject();
        scoreProp.put("projectName", "男子跳远");
        item.setScoreProp(scoreProp);
        item.setOpenId("openid" + System.currentTimeMillis());
        item.setOpenPlatType("qq");
        JSONObject playerProp = new JSONObject();
        playerProp.put("兴趣爱好", "看电影");
        JSONArray array = new JSONArray();
        array.add("跑步");
        array.add("唱歌");
        playerProp.put("特长", array);
        playerProp.put("playerHeigh", 178.2);
        //item.setIdempotentId("idempotent" + System.currentTimeMillis());
        String uuid = "testmodify003";
        item.setIdempotentId(uuid);
        item.setModifyFlag("1");
        item.setPlayerProp(playerProp);
        items.add(item);
        OpenScoreSubmitResult result = api.submitScore(items);
        System.out.println(JSON.toJSON(result).toString());
    }

    //测试查询成绩接口
    public void testScoreQuery() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        String playerCode = null;
        String matchCode = "test-match-code";
        String fieldCode = null;
        String playerPhone = "";

        OpenScoreQueryResult result = api.queryScoreList(matchCode, null, playerCode, null,
            playerPhone, 1, 20);
        System.out.println(result.toString());
    }

    //测试提交成绩接口
    public void testSubmitScoreRank() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        List<OpenScoreRankModel> items = new ArrayList<OpenScoreRankModel>();
        OpenScoreRankModel item = new OpenScoreRankModel();
        item.setPlayerCode("pc" + System.currentTimeMillis());
        item.setPlayerName("测试运动员");
        item.setPlayerPhone("18628380221");
        item.setPartCode(item.getPlayerCode());
        item.setPartTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        item.setMatchCode("test-match-code");
        item.setFieldCode("fieldCode");
        item.setFieldName("赛场成都");
        item.setMatchGroupCode("boy");
        item.setMatchGroupName("男子跳远");
        item.setEventCode("test-event-code");
        item.setEventName("立定跳远");
        item.setScore(3.32);
        item.setScoreName("男子跳远");
        item.setScoreUnit("m");
        item.setRanking((int) (10 * Math.random()));
        item.setScoreDesc("2.32米");
        item.setPromotion("晋级决赛");
        JSONObject scoreProp = new JSONObject();
        scoreProp.put("projectName", "男子跳远");
        item.setExtProp(scoreProp);
        //item.setIdempotentId("idempotent" + System.currentTimeMillis());
        String uuid = "testmodify001";
        item.setIdempotentId(uuid);
        items.add(item);
        OpenScoreRankSubmitResult result = api.submitScoreRank(item.getMatchCode(), items);
        System.out.println(JSON.toJSON(result).toString());
    }

    //测试查询成绩接口
    public void testScoreRankQuery() throws OpenException {
        OpenApi api = new OpenApi();
        api.setOpenHost(host);
        api.setPrivateKey("test");
        String unitCode = "test";
        api.setUnitCode(unitCode);

        String playerCode = null;
        String matchCode = "test-match-code";
        String fieldCode = null;
        String playerPhone = "";

        OpenScoreRankQueryResult result = api.queryScoreRankList(matchCode, null, playerCode, null,
            playerPhone, 1, 20);

        System.out.println(result.toString());
    }

    public static void main(String[] args) throws OpenException {
        ExampleApi ep = new ExampleApi();
        /*     ep.testQueryProject();
        ep.testQueryMatchList();
        ep.testQueryProject();
        ep.testQueryMatchList();*/
        ep.testSubmitEnroll();
        ep.testSubmitScore();
        ep.testEnrollQuery();
        ep.testScoreQuery();
        // ep.testSubmitScoreRank();
        // ep.testScoreRankQuery();

        ep.testSubmitEnrollx();
        ep.testSubmitEnrollx_tuanti();
        ep.testEnrollxQuery();
    }
}
