/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

/**
 * 
 * @author zhiyang
 * @version $Id: SaveCertStatus.java, v 0.1 2018年8月7日 下午9:17:48 zhiyang Exp $
 */
public class SaveCertStatus {

    private boolean success;
    private String  tipInfo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTipInfo() {
        return tipInfo;
    }

    public void setTipInfo(String tipInfo) {
        this.tipInfo = tipInfo;
    }

}
