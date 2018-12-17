/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;
import java.util.List;

/**
 * 赛事信息
 * @author zhiyang
 * @version $Id: OpenMatchModel.java, v 0.1 2018年5月24日 下午5:09:10 zhiyang Exp $
 */
public class OpenMatchModel implements Serializable {

    /**  */
    private static final long          serialVersionUID = 1L;
    //项目编号， projectCode;
    private String                     gameCode;
    //赛事编号
    private String                     matchCode;

    //赛事名称
    private String                     matchName;

    //赛事客服电话
    private String                     matchService;
    //赛事图片地址
    private String                     imgUrl;

    //赛事开始时间
    private String                     beginTime;

    //赛事结束时间
    private String                     endTime;

    //赛事参赛人数
    private int                        matchSize;

    //报名截止时间
    private String                     regTime;

    //赛事介绍
    private String                     matchIntro;

    //分赛场信息
    private List<OpenPlayingAreaModel> fieldType;

    //赛事组别信息
    private List<OpenGroupTypeModel>   groupType;

    //赛事细类信息
    private List<OpenEventTypeModel>   eventType;

    //赛事进行状态 @see MatchStatusEnum
    private int                        matchStatus;

    //赛事类型。 
    private String                     matchType;

    //赛事奖项
    private List<OpenMatchAwardModel>  matchAward;

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
     * Getter method for property <tt>imgUrl</tt>.
     * 
     * @return property value of imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Setter method for property <tt>imgUrl</tt>.
     * 
     * @param imgUrl value to be assigned to property imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Getter method for property <tt>matchSize</tt>.
     * 
     * @return property value of matchSize
     */
    public int getMatchSize() {
        return matchSize;
    }

    /**
     * Setter method for property <tt>matchSize</tt>.
     * 
     * @param matchSize value to be assigned to property matchSize
     */
    public void setMatchSize(int matchSize) {
        this.matchSize = matchSize;
    }

    /**
     * Getter method for property <tt>beginTime</tt>.
     * 
     * @return property value of beginTime
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * Setter method for property <tt>beginTime</tt>.
     * 
     * @param beginTime value to be assigned to property beginTime
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     * 
     * @return property value of endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     * 
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter method for property <tt>regTime</tt>.
     * 
     * @return property value of regTime
     */
    public String getRegTime() {
        return regTime;
    }

    /**
     * Setter method for property <tt>regTime</tt>.
     * 
     * @param regTime value to be assigned to property regTime
     */
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /**
     * Getter method for property <tt>matchIntro</tt>.
     * 
     * @return property value of matchIntro
     */
    public String getMatchIntro() {
        return matchIntro;
    }

    /**
     * Setter method for property <tt>matchIntro</tt>.
     * 
     * @param matchIntro value to be assigned to property matchIntro
     */
    public void setMatchIntro(String matchIntro) {
        this.matchIntro = matchIntro;
    }

    /**
     * Getter method for property <tt>fieldType</tt>.
     * 
     * @return property value of fieldType
     */
    public List<OpenPlayingAreaModel> getFieldType() {
        return fieldType;
    }

    /**
     * Setter method for property <tt>fieldType</tt>.
     * 
     * @param fieldType value to be assigned to property fieldType
     */
    public void setFieldType(List<OpenPlayingAreaModel> fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Getter method for property <tt>groupType</tt>.
     * 
     * @return property value of groupType
     */
    public List<OpenGroupTypeModel> getGroupType() {
        return groupType;
    }

    /**
     * Setter method for property <tt>groupType</tt>.
     * 
     * @param groupType value to be assigned to property groupType
     */
    public void setGroupType(List<OpenGroupTypeModel> groupType) {
        this.groupType = groupType;
    }

    /**
     * Getter method for property <tt>eventType</tt>.
     * 
     * @return property value of eventType
     */
    public List<OpenEventTypeModel> getEventType() {
        return eventType;
    }

    /**
     * Setter method for property <tt>eventType</tt>.
     * 
     * @param eventType value to be assigned to property eventType
     */
    public void setEventType(List<OpenEventTypeModel> eventType) {
        this.eventType = eventType;
    }

    /**
     * Getter method for property <tt>matchType</tt>.
     * 
     * @return property value of matchType
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * Setter method for property <tt>matchType</tt>.
     * 
     * @param matchType value to be assigned to property matchType
     */
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    /**
     * Getter method for property <tt>matchAward</tt>.
     * 
     * @return property value of matchAward
     */
    public List<OpenMatchAwardModel> getMatchAward() {
        return matchAward;
    }

    /**
     * Setter method for property <tt>matchAward</tt>.
     * 
     * @param matchAward value to be assigned to property matchAward
     */
    public void setMatchAward(List<OpenMatchAwardModel> matchAward) {
        this.matchAward = matchAward;
    }

    /**
     * Getter method for property <tt>matchStatus</tt>.
     * 
     * @return property value of matchStatus
     */
    public int getMatchStatus() {
        return matchStatus;
    }

    /**
     * Setter method for property <tt>matchStatus</tt>.
     * 
     * @param matchStatus value to be assigned to property matchStatus
     */
    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
    }

    /**
     * Getter method for property <tt>matchService</tt>.
     * 
     * @return property value of matchService
     */
    public String getMatchService() {
        return matchService;
    }

    /**
     * Setter method for property <tt>matchService</tt>.
     * 
     * @param matchService value to be assigned to property matchService
     */
    public void setMatchService(String matchService) {
        this.matchService = matchService;
    }

}
