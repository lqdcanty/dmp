/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreResultModel.java, v 0.1 2018年6月21日 下午6:35:39 zhiyang Exp $
 */
public class OpenScoreResultModel {
    //成绩编号
    private String scoreCode;
    //运动员编号 
    private String playerCode;
    //姓名
    private String playerName;
    //手机号(必填）
    private String playerPhone;
    //报名提交唯一标识
    private String idempotentId;

    //赛事编号  （必选）
    private String matchCode;

    //赛场编号（选填）
    private String fieldCode;

    //项目细类编号（必选）
    private String eventCode;

    //是否成功 1成功 2失败
    private int    success;
    //描述
    private String message;

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
     * Getter method for property <tt>success</tt>.
     * 
     * @return property value of success
     */
    public int getSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     * 
     * @param success value to be assigned to property success
     */
    public void setSuccess(int success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
