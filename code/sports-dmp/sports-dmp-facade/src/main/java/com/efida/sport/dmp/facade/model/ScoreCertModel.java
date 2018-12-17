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
 * @version $Id: ScoreCertModel.java, v 0.1 2018年8月6日 下午3:31:55 zoutao Exp $
 */
public class ScoreCertModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 证书唯一编号（内部系统唯一编号）
     */
    private String            scoreCertCode;
    /**
     * 正式编号（对外公示）
     */
    private String            scoreCertSn;
    /**
     * 证书图片地址
     */
    private String            certPicUrl;
    /**
     * 证书缩略图
     */
    private String            certShortPicuUrl;

    /**
     * 赛事名称
     */
    private String            matchName;
    /**
     * 比赛名称
     */
    private String            comtitionName;
    /**
     * 项目名称
     */
    private String            eventName;
    /**
     * 比赛时间
     */
    private Date              partTime;

    /**
     * 比赛时间
     */
    private String            partTimeStr;

    public String getScoreCertCode() {
        return scoreCertCode;
    }

    public void setScoreCertCode(String scoreCertCode) {
        this.scoreCertCode = scoreCertCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
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

    public String getScoreCertSn() {
        return scoreCertSn;
    }

    public void setScoreCertSn(String scoreCertSn) {
        this.scoreCertSn = scoreCertSn;
    }

    public String getCertPicUrl() {
        return certPicUrl;
    }

    public void setCertPicUrl(String certPicUrl) {
        this.certPicUrl = certPicUrl;
    }

    public String getCertShortPicuUrl() {
        return certShortPicuUrl;
    }

    public void setCertShortPicuUrl(String certShortPicuUrl) {
        this.certShortPicuUrl = certShortPicuUrl;
    }

    public String getComtitionName() {
        return comtitionName;
    }

    public void setComtitionName(String comtitionName) {
        this.comtitionName = comtitionName;
    }
}
