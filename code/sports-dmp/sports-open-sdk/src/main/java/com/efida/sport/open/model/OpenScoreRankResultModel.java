/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreRankResultModel.java, v 0.1 2018年7月9日 下午10:58:58 zhiyang Exp $
 */
public class OpenScoreRankResultModel {

    //报名提交唯一标识
    private String idempotentId;
    //运动员编号 
    private String playerCode;

    //成绩名次编号
    private String scoreRankCode;
    //姓名
    private String playerName;

    //手机号(必填）
    private String playerPhone;

    //是否成功 1成功 2失败
    private int    success;
    //描述
    private String message;

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

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OpenScoreRankResultModel [idempotentId=" + idempotentId + ", playerCode="
               + playerCode + ", scoreRankCode=" + scoreRankCode + ", playerName=" + playerName
               + ", playerPhone=" + playerPhone + ", success=" + success + ", message=" + message
               + "]";
    }

}
