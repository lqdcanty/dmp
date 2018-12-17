/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;

/**
 * 分赛场信息
 * @author zhiyang
 * @version $Id: OpenPlayingAreaModel.java, v 0.1 2018年5月24日 下午5:56:54 zhiyang Exp $
 */
public class OpenPlayingAreaModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    //分赛场编号
    private String            fieldCode;
    //分赛场名称
    private String            fieldName;
    //举办地点
    private String            fieldAddress;
    //比赛时间
    private String            fieldTime;

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
     * Getter method for property <tt>fieldAddress</tt>.
     * 
     * @return property value of fieldAddress
     */
    public String getFieldAddress() {
        return fieldAddress;
    }

    /**
     * Setter method for property <tt>fieldAddress</tt>.
     * 
     * @param fieldAddress value to be assigned to property fieldAddress
     */
    public void setFieldAddress(String fieldAddress) {
        this.fieldAddress = fieldAddress;
    }

    /**
     * Getter method for property <tt>fieldTime</tt>.
     * 
     * @return property value of fieldTime
     */
    public String getFieldTime() {
        return fieldTime;
    }

    /**
     * Setter method for property <tt>fieldTime</tt>.
     * 
     * @param fieldTime value to be assigned to property fieldTime
     */
    public void setFieldTime(String fieldTime) {
        this.fieldTime = fieldTime;
    }

}
