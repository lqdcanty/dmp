/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.util.List;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollxModel.java, v 0.1 2018年7月26日 下午11:26:36 zhiyang Exp $
 */
public class OpenEnrollxModel {
    //报名编号
    private String applyCode;



    //personal:个人赛, group:团体赛
    private String eventType;
    //比赛报名人数
    private Integer registrationNum;
    //团队名称
    private String teamName;
    //领队电话
    private String leaderPhone;
    //领队姓名
    private String leaderName;

    //项目编号（必选）
    private String            gameCode;
    //项目名称（ 必选）
    private String            gameName;
    //赛事编号  （必选）
    private String            matchCode;
    //赛事名称 （必选）
    private String            matchName;
    //分赛场编号 (可选)
    private String            fieldCode;
    //分赛场名称 （可选）
    private String            fieldName;

    //组别编号(可选)
    private String            matchGroupCode;
    //组别名称（可选）
    private String            matchGroupName;
    //项目细类编号（必选）
    private String            eventCode;
    //项目细类名称（必选）
    private String            eventName;
    //报名时间 （必填）
    private String            applyTime;

    //报名费（元）（ 必填）
    private Double            entryFee;



    //记录唯一性标识
    private String            idempotentId;

    // 1.已经存在则修改属性，但是只能在一段时间内可修改，具体时间同业务有关系； 0或其他取值：不支持修改，记录存在则返回已经存在记录。
    private String            modifyFlag       = "0";

    //参赛运动员信息
    private List<OpenPlayerModel> playerData;

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(Integer registrationNum) {
        this.registrationNum = registrationNum;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode;
    }

    public String getMatchGroupName() {
        return matchGroupName;
    }

    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public List<OpenPlayerModel> getPlayerData() {
        return playerData;
    }

    public void setPlayerData(List<OpenPlayerModel> playerData) {
        this.playerData = playerData;
    }

  
}
