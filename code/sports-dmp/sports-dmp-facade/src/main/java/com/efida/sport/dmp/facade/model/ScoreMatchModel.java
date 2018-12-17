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
 * @version $Id: ScoreMatchModel.java, v 0.1 2018年7月28日 上午10:47:00 zoutao Exp $
 */
public class ScoreMatchModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 赛事编号
     */
    private String            matchCode;
    /**
     * 赛事名称
     */
    private String            matchName;
    /**
     * 赛事图片
     */
    private String            matchImg;
    /**
     * 开始时间
     */
    private Date              startTime;
    /**
     * 结束时间
     */
    private Date              endTime;

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchImg() {
        return matchImg;
    }

    public void setMatchImg(String matchImg) {
        this.matchImg = matchImg;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
