/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.dao.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author zoutao
 * @version $Id: ApplyStatisticsModel.java, v 0.1 2018年9月14日 上午11:53:27 zoutao Exp $
 */
public class ApplyStatisticsModel {
    /**
     * 报名次数
     */
    private long        applyCount;

    private Set<String> applyEventCode = new HashSet<String>();

    private long        applyEventCount;

    public long getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(long applyCount) {
        this.applyCount = applyCount;
    }

    public Set<String> getApplyEventCode() {
        return applyEventCode;
    }

    public void setApplyEventCode(Set<String> applyEventCode) {
        this.applyEventCode = applyEventCode;
        this.setApplyEventCount(applyEventCode.size());
    }

    public long getApplyEventCount() {
        return applyEventCount;
    }

    public void setApplyEventCount(long applyEventCount) {
        this.applyEventCount = applyEventCount;
    }

}
