/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;

/**
 * 赛事细类（赛事项，比赛项）
 * @author zhiyang
 * @version $Id: OpenEventTypeModel.java, v 0.1 2018年5月24日 下午5:44:25 zhiyang Exp $
 */
public class OpenEventTypeModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    //细类编号
    private String            eventCode;
    //细类名称
    private String            eventName;
    //细类顺序  可选
    private int               eventNumber;
    //比赛时间
    private String            eventTime;
    //报名名额  可选
    private Integer           personLimit;
    //报名费 可选
    private Double            entryFee;
    //分赛场编号 可选
    private String            fieldCode;
    //分组编号 可选
    private String            groupCode;

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
     * Getter method for property <tt>eventNumber</tt>.
     * 
     * @return property value of eventNumber
     */
    public int getEventNumber() {
        return eventNumber;
    }

    /**
     * Setter method for property <tt>eventNumber</tt>.
     * 
     * @param eventNumber value to be assigned to property eventNumber
     */
    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }

    /**
     * Getter method for property <tt>eventTime</tt>.
     * 
     * @return property value of eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * Setter method for property <tt>eventTime</tt>.
     * 
     * @param eventTime value to be assigned to property eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Getter method for property <tt>personLimit</tt>.
     * 
     * @return property value of personLimit
     */
    public Integer getPersonLimit() {
        return personLimit;
    }

    /**
     * Setter method for property <tt>personLimit</tt>.
     * 
     * @param personLimit value to be assigned to property personLimit
     */
    public void setPersonLimit(Integer personLimit) {
        this.personLimit = personLimit;
    }

    /**
     * Getter method for property <tt>entryFee</tt>.
     * 
     * @return property value of entryFee
     */
    public Double getEntryFee() {
        return entryFee;
    }

    /**
     * Setter method for property <tt>entryFee</tt>.
     * 
     * @param entryFee value to be assigned to property entryFee
     */
    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
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

}
