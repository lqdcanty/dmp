/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.efida.sports.cert.utils.NumberUtil;

/**
 * 证书图片信息
 * @author wang yi
 * @desc 
 * @version $Id: CerpicInfoModel.java, v 0.1 2018年8月5日 下午8:27:24 wang yi Exp $
 */
public class CerpicInfoModel {

    /**
     * 赛事名称
     */
    private String     certMatchName;

    /**
     * 赛事描述
     */
    private String     certEventName;

    /**
     * 颁发姓名
     */
    private String     certName;

    /**
     * 证书序号(对应证书编号)
     */
    private String     certSn;

    /**
     * 成绩描述
     */
    private String     scoreDesc;

    /**
     * 成绩
     */
    private BigDecimal score;

    /**
     * 单位
     */
    private String     scoreUnit;

    /**
     * 证书颁发时间
     */
    private Date       certTime;

    /**
     * 完赛时间
     */
    private Date       partTime;

    /**
     *获取证书成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param certModel
     * @return
     */
    public static String getCerPicScore(CerpicInfoModel certModel) {
        String scoreDesc = "";
        if (certModel == null) {
            return scoreDesc;
        }
        if (StringUtils.isNotBlank(certModel.getScoreDesc())) {
            scoreDesc = certModel.getScoreDesc();
            return scoreDesc;
        }
        scoreDesc = NumberUtil.trimZero(certModel.getScore().doubleValue())
                    + certModel.getScoreUnit();
        return scoreDesc;
    }

    public String getCertMatchName() {
        return certMatchName;
    }

    public void setCertMatchName(String certMatchName) {
        this.certMatchName = certMatchName;
    }

    public String getCertEventName() {
        return certEventName;
    }

    public void setCertEventName(String certEventName) {
        this.certEventName = certEventName;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertSn() {
        return certSn;
    }

    public void setCertSn(String certSn) {
        this.certSn = certSn;
    }

    public Date getCertTime() {
        return certTime;
    }

    public void setCertTime(Date certTime) {
        this.certTime = certTime;
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

}
