/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollxResultModel.java, v 0.1 2018年7月26日 下午11:52:09 zhiyang Exp $
 */
public class OpenEnrollxResultModel {

    //报名编号 
    private String applayCode;

    //报名提交唯一标识
    private String idempotentId;

    //赛事编号  （必选）
    private String matchCode;
    //赛场编号
    private String fieldCode;

    //项目细类编号（必选）
    private String eventCode;

    //是否成功 1成功 2失败
    private int    success;
    //描述
    private String message;
    public String getApplayCode() {
        return applayCode;
    }
    public void setApplayCode(String applayCode) {
        this.applayCode = applayCode;
    }
    public String getIdempotentId() {
        return idempotentId;
    }
    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }
    public String getMatchCode() {
        return matchCode;
    }
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }
    public String getFieldCode() {
        return fieldCode;
    }
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
    public String getEventCode() {
        return eventCode;
    }
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    public int getSuccess() {
        return success;
    }
    public void setSuccess(int success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    
}
