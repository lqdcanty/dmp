/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zoutao
 * @version $Id: PlaterStatisticalAnalysisModel.java, v 0.1 2018年9月13日 下午3:02:01 zoutao Exp $
 * 运动员统计分析
 */

@ApiModel(description = "运动员统计分析对象")
public class PlayerStatisticalAnalysisModel {

    @ApiModelProperty(value = "成年人数量")
    private long adultTotal;

    @ApiModelProperty(value = "未成年人数量")
    private long unAdultTotal;

    @ApiModelProperty(value = "男性数量")
    private long maleTotal;

    @ApiModelProperty(value = "女性数量")
    private long femaleTotal;

    @ApiModelProperty(value = "ios报名数量")
    private long iosTotal;

    @ApiModelProperty(value = "pc报名数量")
    private long pcTotal;

    @ApiModelProperty(value = "android报名数量")
    private long androidTotal;

    @ApiModelProperty(value = "dmp报名数量")
    private long dmpTotal;

    @ApiModelProperty(value = "weichat报名数量")
    private long weichatTotal;

    public long getAdultTotal() {
        return adultTotal;
    }

    public void setAdultTotal(long adultTotal) {
        this.adultTotal = adultTotal;
    }

    public long getUnAdultTotal() {
        return unAdultTotal;
    }

    public void setUnAdultTotal(long unAdultTotal) {
        this.unAdultTotal = unAdultTotal;
    }

    public long getMaleTotal() {
        return maleTotal;
    }

    public void setMaleTotal(long maleTotal) {
        this.maleTotal = maleTotal;
    }

    public long getFemaleTotal() {
        return femaleTotal;
    }

    public void setFemaleTotal(long femaleTotal) {
        this.femaleTotal = femaleTotal;
    }

    public long getIosTotal() {
        return iosTotal;
    }

    public void setIosTotal(long iosTotal) {
        this.iosTotal = iosTotal;
    }

    public long getPcTotal() {
        return pcTotal;
    }

    public void setPcTotal(long pcTotal) {
        this.pcTotal = pcTotal;
    }

    public long getAndroidTotal() {
        return androidTotal;
    }

    public void setAndroidTotal(long androidTotal) {
        this.androidTotal = androidTotal;
    }

    public long getDmpTotal() {
        return dmpTotal;
    }

    public void setDmpTotal(long dmpTotal) {
        this.dmpTotal = dmpTotal;
    }

    public long getWeichatTotal() {
        return weichatTotal;
    }

    public void setWeichatTotal(long weichatTotal) {
        this.weichatTotal = weichatTotal;
    }

}
