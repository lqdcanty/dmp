/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreRankModel.java, v 0.1 2018年7月9日 下午10:59:59 zhiyang Exp $
 */
public class OpenScoreRankModel {

    //成绩名次编号
    private String     scoreRankCode;

    //运动员编号 
    private String     playerCode;

    //姓名
    private String     playerName;

    //手机号(必填）
    private String     playerPhone;

    //参赛编号
    private String     partCode;

    //参赛时间 （必填）
    private String     partTime;

    //赛事项目编号
    private String     gameCode;

    //赛事项目名称
    private String     gameName;

    //赛事编号  （必选）
    private String     matchCode;

    //赛事名称
    private String     matchName;

    //分赛场编号 (可选)
    private String     fieldCode;

    //分赛场名称 （可选）
    private String     fieldName;

    //组别编号(可选)
    private String     matchGroupCode;

    //组别名称（可选）
    private String     matchGroupName;

    //项目细类编号（必选）
    private String     eventCode;

    //项目细类名称（必选）
    private String     eventName;

    //考试科目
    private String     scoreName;
    /**
     * 成绩数值描述
     */
    private double     score;

    //成绩单位
    private String     scoreUnit;

    //成绩文字描述 “1分28秒”
    private String     scoreDesc;

    //排名
    private Integer    ranking;

    //晋级情况
    private String     promotion;

    //成绩对应属性
    private JSONObject extProp;

    //成绩接口中唯一性标识
    private String     idempotentId;

    // 1.已经存在则修改属性，但是只能在一段时间内可修改，具体时间同业务有关系； 0或其他取值：不支持修改，记录存在则返回已经存在记录。
    private String     modifyFlag = "0";

    /**
     * Getter method for property <tt>scoreRankCode</tt>.
     * 
     * @return property value of scoreRankCode
     */
    public String getScoreRankCode() {
        return scoreRankCode;
    }

    /**
     * Setter method for property <tt>scoreRankCode</tt>.
     * 
     * @param scoreRankCode value to be assigned to property scoreRankCode
     */
    public void setScoreRankCode(String scoreRankCode) {
        this.scoreRankCode = scoreRankCode;
    }

    /**
     * Getter method for property <tt>playerCode</tt>.
     * 
     * @return property value of playerCode
     */
    public String getPlayerCode() {
        return playerCode;
    }

    /**
     * Setter method for property <tt>playerCode</tt>.
     * 
     * @param playerCode value to be assigned to property playerCode
     */
    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    /**
     * Getter method for property <tt>playerName</tt>.
     * 
     * @return property value of playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter method for property <tt>playerName</tt>.
     * 
     * @param playerName value to be assigned to property playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
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

    /**
     * Getter method for property <tt>playerPhone</tt>.
     * 
     * @return property value of playerPhone
     */
    public String getPlayerPhone() {
        return playerPhone;
    }

    /**
     * Setter method for property <tt>playerPhone</tt>.
     * 
     * @param playerPhone value to be assigned to property playerPhone
     */
    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    /**
     * Getter method for property <tt>partCode</tt>.
     * 
     * @return property value of partCode
     */
    public String getPartCode() {
        return partCode;
    }

    /**
     * Setter method for property <tt>partCode</tt>.
     * 
     * @param partCode value to be assigned to property partCode
     */
    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    /**
     * Getter method for property <tt>partTime</tt>.
     * 
     * @return property value of partTime
     */
    public String getPartTime() {
        return partTime;
    }

    /**
     * Setter method for property <tt>partTime</tt>.
     * 
     * @param partTime value to be assigned to property partTime
     */
    public void setPartTime(String partTime) {
        this.partTime = partTime;
    }

    /**
     * Getter method for property <tt>fieldCode</tt>.
     * 
     * @return property value of fieldCode
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * Setter method for property <tt>fieldCode</tt>.
     * 
     * @param fieldCode value to be assigned to property fieldCode
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * Getter method for property <tt>fieldName</tt>.
     * 
     * @return property value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter method for property <tt>fieldName</tt>.
     * 
     * @param fieldName value to be assigned to property fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter method for property <tt>matchGroupCode</tt>.
     * 
     * @return property value of matchGroupCode
     */
    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    /**
     * Setter method for property <tt>matchGroupCode</tt>.
     * 
     * @param matchGroupCode value to be assigned to property matchGroupCode
     */
    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode;
    }

    /**
     * Getter method for property <tt>matchGroupName</tt>.
     * 
     * @return property value of matchGroupName
     */
    public String getMatchGroupName() {
        return matchGroupName;
    }

    /**
     * Setter method for property <tt>matchGroupName</tt>.
     * 
     * @param matchGroupName value to be assigned to property matchGroupName
     */
    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
    }

    /**
     * Getter method for property <tt>matchCode</tt>.
     * 
     * @return property value of matchCode
     */
    public String getMatchCode() {
        return matchCode;
    }

    /**
     * Setter method for property <tt>matchCode</tt>.
     * 
     * @param matchCode value to be assigned to property matchCode
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    /**
     * Getter method for property <tt>eventCode</tt>.
     * 
     * @return property value of eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Setter method for property <tt>eventCode</tt>.
     * 
     * @param eventCode value to be assigned to property eventCode
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Getter method for property <tt>eventName</tt>.
     * 
     * @return property value of eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Setter method for property <tt>eventName</tt>.
     * 
     * @param eventName value to be assigned to property eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Getter method for property <tt>scoreName</tt>.
     * 
     * @return property value of scoreName
     */
    public String getScoreName() {
        return scoreName;
    }

    /**
     * Setter method for property <tt>scoreName</tt>.
     * 
     * @param scoreName value to be assigned to property scoreName
     */
    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    /**
     * Getter method for property <tt>score</tt>.
     * 
     * @return property value of score
     */
    public double getScore() {
        return score;
    }

    /**
     * Setter method for property <tt>score</tt>.
     * 
     * @param score value to be assigned to property score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Getter method for property <tt>scoreUnit</tt>.
     * 
     * @return property value of scoreUnit
     */
    public String getScoreUnit() {
        return scoreUnit;
    }

    /**
     * Setter method for property <tt>scoreUnit</tt>.
     * 
     * @param scoreUnit value to be assigned to property scoreUnit
     */
    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    /**
     * Getter method for property <tt>scoreDesc</tt>.
     * 
     * @return property value of scoreDesc
     */
    public String getScoreDesc() {
        return scoreDesc;
    }

    /**
     * Setter method for property <tt>scoreDesc</tt>.
     * 
     * @param scoreDesc value to be assigned to property scoreDesc
     */
    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    /**
     * Getter method for property <tt>ranking</tt>.
     * 
     * @return property value of ranking
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * Setter method for property <tt>ranking</tt>.
     * 
     * @param ranking value to be assigned to property ranking
     */
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    /**
     * Getter method for property <tt>promotion</tt>.
     * 
     * @return property value of promotion
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * Setter method for property <tt>promotion</tt>.
     * 
     * @param promotion value to be assigned to property promotion
     */
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    /**
     * Getter method for property <tt>extProp</tt>.
     * 
     * @return property value of extProp
     */
    public JSONObject getExtProp() {
        return extProp;
    }

    /**
     * Setter method for property <tt>extProp</tt>.
     * 
     * @param extProp value to be assigned to property extProp
     */
    public void setExtProp(JSONObject extProp) {
        this.extProp = extProp;
    }

    /**
     * Getter method for property <tt>idempotentId</tt>.
     * 
     * @return property value of idempotentId
     */
    public String getIdempotentId() {
        return idempotentId;
    }

    /**
     * Setter method for property <tt>idempotentId</tt>.
     * 
     * @param idempotentId value to be assigned to property idempotentId
     */
    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    /**
     * Getter method for property <tt>modifyFlag</tt>.
     * 
     * @return property value of modifyFlag
     */
    public String getModifyFlag() {
        return modifyFlag;
    }

    /**
     * Setter method for property <tt>modifyFlag</tt>.
     * 
     * @param modifyFlag value to be assigned to property modifyFlag
     */
    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OpenScoreRankModel [scoreRankCode=" + scoreRankCode + ", playerCode=" + playerCode
               + ", playerName=" + playerName + ", playerPhone=" + playerPhone + ", partCode="
               + partCode + ", partTime=" + partTime + ", matchCode=" + matchCode + ", fieldCode="
               + fieldCode + ", fieldName=" + fieldName + ", matchGroupCode=" + matchGroupCode
               + ", matchGroupName=" + matchGroupName + ", eventCode=" + eventCode + ", eventName="
               + eventName + ", scoreName=" + scoreName + ", score=" + score + ", scoreUnit="
               + scoreUnit + ", scoreDesc=" + scoreDesc + ", ranking=" + ranking + ", promotion="
               + promotion + ", extProp=" + extProp + ", idempotentId=" + idempotentId
               + ", modifyFlag=" + modifyFlag + "]";
    }

}
