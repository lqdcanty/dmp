/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;

/**
 * 赛事组别信息
 * @author zhiyang
 * @version $Id: OpenGroupTypeModel.java, v 0.1 2018年5月24日 下午5:43:20 zhiyang Exp $
 */
public class OpenGroupTypeModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    //分组编号
    private String            groupCode;
    //分组名称
    private String            groupName;
    //分组序号
    private String            groupNumber;

    //分赛场编号 （关联分赛场） 可选
    private String            fieldCode;

    /**
     * Getter method for property <tt>groupCode</tt>.
     * 
     * @return property value of groupCode
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * Setter method for property <tt>groupCode</tt>.
     * 
     * @param groupCode value to be assigned to property groupCode
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * Getter method for property <tt>groupName</tt>.
     * 
     * @return property value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Setter method for property <tt>groupName</tt>.
     * 
     * @param groupName value to be assigned to property groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Getter method for property <tt>groupNumber</tt>.
     * 
     * @return property value of groupNumber
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Setter method for property <tt>groupNumber</tt>.
     * 
     * @param groupNumber value to be assigned to property groupNumber
     */
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
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

}
