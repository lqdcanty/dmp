/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.request;

import java.util.List;

/**
 * 
 * @author lizhiyang
 * @version $Id: SearchCompetionScoreRequest.java, v 0.1 2018年8月29日 下午7:48:34 lizhiyang Exp $
 */
public class SearchCompetionScoreRequest {

    private String       matchCode;
    private List<String> fieldCodes;
    private List<String> groupCodes;
    private List<String> eventCodes;
    private String       playerPhone;

    private Integer      pageNumber;
    private Integer      pageSize;

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public List<String> getFieldCodes() {
        return fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public List<String> getGroupCodes() {
        return groupCodes;
    }

    public void setGroupCodes(List<String> groupCodes) {
        this.groupCodes = groupCodes;
    }

    public List<String> getEventCodes() {
        return eventCodes;
    }

    public void setEventCodes(List<String> eventCodes) {
        this.eventCodes = eventCodes;
    }

    public String getPlayerPhone() {
        return playerPhone;
    }

    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SearchCompetionScoreRequest [matchCode=" + matchCode + ", fieldCodes=" + fieldCodes
               + ", groupCodes=" + groupCodes + ", eventCodes=" + eventCodes + ", playerPhone="
               + playerPhone + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + "]";
    }

}
