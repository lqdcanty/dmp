/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zoutao
 * @version $Id: SoreExtPro.java, v 0.1 2018年7月28日 上午11:29:01 zoutao Exp $
 */
public class SoreExtPro implements Serializable {

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
     * 成绩
     */
    private String            scoreDesc;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getScoreDesc() {
        return scoreDesc;
    }

    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public String getPartTimeStr() {
        return partTimeStr;
    }

    public void setPartTimeStr(String partTimeStr) {
        this.partTimeStr = partTimeStr;
    }

}
