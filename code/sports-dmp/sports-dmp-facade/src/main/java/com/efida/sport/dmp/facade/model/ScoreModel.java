/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author zoutao
 * @version $Id: ScoreModel.java, v 0.1 2018年7月28日 上午11:40:11 zoutao Exp $
 */
public class ScoreModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 性别 
     */
    private String            gender;
    /**
     * 转义后性别
     */
    private String            genderStr;
    /**
     * 赛事名称
     */
    private String            matchName;
    /**
     * 比赛编号
     */
    private String            matchCode;
    /**
     * 赛场名称
     */
    private String            siteName;
    /**
     * 分组名称
     */
    private String            groupName;
    /**
     * 比赛项名称
     */
    private String            eventName;
    /**
     * 参赛时间
     */
    private Date              partTime;

    /**
     * 参赛时间格式化后数据
     */
    private String            partTimeStr;
    /**
    * 成绩数值表示
    */
    private BigDecimal        score;
    /**
     * 成绩单位
     */
    private String            scoreUnit;

    /**
     * big:score取值越大，成绩越好；small:score取值越小，成绩越好
     */
    private String            scoreType;

    /**
     * 成绩描述
     */
    private String            scoreDesc;
    /**
     * 运动员编号
     */
    private String            palyerCode;
    /**
     * 运动员昵称
     */
    private String            palyerName;

    /**
     * 比赛编号
     */
    private String            competitionCode;

    /**
     * 我的成绩排名，只有最好成绩才有排名
     */
    private String            ranking          = "";

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getPartTime() {
        return partTime;
    }

    public void setPartTime(Date partTime) {
        this.partTime = partTime;
    }

    public String getPartTimeStr() {
        return partTimeStr;
    }

    public void setPartTimeStr(String partTimeStr) {
        this.partTimeStr = partTimeStr;
    }

    public String getScoreDesc() {
        return scoreDesc;
    }

    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getPalyerCode() {
        return palyerCode;
    }

    public void setPalyerCode(String palyerCode) {
        this.palyerCode = palyerCode;
    }

    public String getPalyerName() {
        return palyerName;
    }

    public void setPalyerName(String palyerName) {
        this.palyerName = palyerName;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

}
