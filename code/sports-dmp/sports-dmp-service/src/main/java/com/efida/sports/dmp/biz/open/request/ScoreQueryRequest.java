/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.request;

/**
 * 
 * @author zhiyang
 * @version $Id: ScoreQueryRequest.java, v 0.1 2018年6月21日 下午7:50:36 zhiyang Exp $
 */
public class ScoreQueryRequest {

    private String  unitCode;
    private String  matchCode;
    private String  eventCode;

    private String  playerCode;
    private String  scoreName;
    private String  playerPhone;
    private String  timestamp;
    private String  sign;

    private String  ipAddress;
    private Integer pageNumber;
    private Integer pageSize;

    /**
     * Getter method for property <tt>unitCode</tt>.
     * 
     * @return property value of unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * Setter method for property <tt>unitCode</tt>.
     * 
     * @param unitCode value to be assigned to property unitCode
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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
     * Getter method for property <tt>timestamp</tt>.
     * 
     * @return property value of timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Setter method for property <tt>timestamp</tt>.
     * 
     * @param timestamp value to be assigned to property timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter method for property <tt>sign</tt>.
     * 
     * @return property value of sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setter method for property <tt>sign</tt>.
     * 
     * @param sign value to be assigned to property sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Getter method for property <tt>ipAddress</tt>.
     * 
     * @return property value of ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Setter method for property <tt>ipAddress</tt>.
     * 
     * @param ipAddress value to be assigned to property ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Getter method for property <tt>pageNumber</tt>.
     * 
     * @return property value of pageNumber
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Setter method for property <tt>pageNumber</tt>.
     * 
     * @param pageNumber value to be assigned to property pageNumber
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     * 
     * @return property value of pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     * 
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
