/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 比赛成绩模型
 * @author zhiyang
 * @version $Id: OpenScoreModel.java, v 0.1 2018年6月21日 下午4:40:25 zhiyang Exp $
 */
public class OpenScoreModel {
    /**  */
    private static final long serialVersionUID = 1L;

    //成绩编号
    private String            scoreCode;
    //运动员编号 
    private String            playerCode;

    //手机号(必填）
    private String            playerPhone;
    //姓名
    private String            playerName;
    // M:男  F：女  
    private String            sex;
    /**:
     * 上方平台类型
     */
    private String            openPlatType;

    /**
     * 上方账号Id
     */
    private String            openId;
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
    //参赛时间 （必填）
    private String            partTime;

    //考试科目
    private String            scoreName;
    /**
     * 成绩数值描述
     */
    private double            score;
    //成绩单位
    private String            scoreUnit;
    //成绩文字描述 “1分28秒”
    private String            scoreDesc;
    //1有效 0 无效
    private Byte              isValid;
    //成绩类型
    private String            scoreType;
    //成绩对应属性
    private JSONObject        scoreProp;
    /**
     * 运动员其他属性，参看运动员报名属性 简称。 
     */
    private JSONObject        playerProp;

    //成绩接口中唯一性标识
    private String            idempotentId;
    // 1.已经存在则修改属性，但是只能在一段时间内可修改，具体时间同业务有关系； 0或其他取值：不支持修改，记录存在则返回已经存在记录。
    private String            modifyFlag       = "0";

    /**
     * Getter method for property <tt>scoreCode</tt>.
     * 
     * @return property value of scoreCode
     */
    public String getScoreCode() {
        return scoreCode;
    }

    /**
     * Setter method for property <tt>scoreCode</tt>.
     * 
     * @param scoreCode value to be assigned to property scoreCode
     */
    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
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

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>openPlatType</tt>.
     * 
     * @return property value of openPlatType
     */
    public String getOpenPlatType() {
        return openPlatType;
    }

    /**
     * Setter method for property <tt>openPlatType</tt>.
     * 
     * @param openPlatType value to be assigned to property openPlatType
     */
    public void setOpenPlatType(String openPlatType) {
        this.openPlatType = openPlatType;
    }

    /**
     * Getter method for property <tt>openId</tt>.
     * 
     * @return property value of openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * Setter method for property <tt>openId</tt>.
     * 
     * @param openId value to be assigned to property openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * Getter method for property <tt>gameCode</tt>.
     * 
     * @return property value of gameCode
     */
    public String getGameCode() {
        return gameCode;
    }

    /**
     * Setter method for property <tt>gameCode</tt>.
     * 
     * @param gameCode value to be assigned to property gameCode
     */
    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    /**
     * Getter method for property <tt>gameName</tt>.
     * 
     * @return property value of gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Setter method for property <tt>gameName</tt>.
     * 
     * @param gameName value to be assigned to property gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
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
     * Getter method for property <tt>matchName</tt>.
     * 
     * @return property value of matchName
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * Setter method for property <tt>matchName</tt>.
     * 
     * @param matchName value to be assigned to property matchName
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName;
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
     * Getter method for property <tt>isValid</tt>.
     * 
     * @return property value of isValid
     */
    public Byte getIsValid() {
        return isValid;
    }

    /**
     * Setter method for property <tt>isValid</tt>.
     * 
     * @param isValid value to be assigned to property isValid
     */
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    /**
     * Getter method for property <tt>scoreType</tt>.
     * 
     * @return property value of scoreType
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * Setter method for property <tt>scoreType</tt>.
     * 
     * @param scoreType value to be assigned to property scoreType
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * Getter method for property <tt>scoreProp</tt>.
     * 
     * @return property value of scoreProp
     */
    public JSONObject getScoreProp() {
        return scoreProp;
    }

    /**
     * Setter method for property <tt>scoreProp</tt>.
     * 
     * @param scoreProp value to be assigned to property scoreProp
     */
    public void setScoreProp(JSONObject scoreProp) {
        this.scoreProp = scoreProp;
    }

    /**
     * Getter method for property <tt>playerProp</tt>.
     * 
     * @return property value of playerProp
     */
    public JSONObject getPlayerProp() {
        return playerProp;
    }

    /**
     * Setter method for property <tt>playerProp</tt>.
     * 
     * @param playerProp value to be assigned to property playerProp
     */
    public void setPlayerProp(JSONObject playerProp) {
        this.playerProp = playerProp;
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
        return "OpenScoreModel [scoreCode=" + scoreCode + ", playerCode=" + playerCode
               + ", playerPhone=" + playerPhone + ", playerName=" + playerName + ", sex=" + sex
               + ", openPlatType=" + openPlatType + ", openId=" + openId + ", gameCode=" + gameCode
               + ", gameName=" + gameName + ", matchCode=" + matchCode + ", matchName=" + matchName
               + ", fieldCode=" + fieldCode + ", fieldName=" + fieldName + ", matchGroupCode="
               + matchGroupCode + ", matchGroupName=" + matchGroupName + ", eventCode=" + eventCode
               + ", eventName=" + eventName + ", partTime=" + partTime + ", scoreName=" + scoreName
               + ", score=" + score + ", scoreUnit=" + scoreUnit + ", scoreDesc=" + scoreDesc
               + ", isValid=" + isValid + ", scoreType=" + scoreType + ", scoreProp=" + scoreProp
               + ", playerProp=" + playerProp + ", idempotentId=" + idempotentId + ", modifyFlag="
               + modifyFlag + "]";
    }

}
