/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

/**
 * 生成的图片Model
 * @author wang yi
 * @desc 
 * @version $Id: CertPicModel.java, v 0.1 2018年8月7日 下午9:22:39 wang yi Exp $
 */
public class CertPicModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /**
     *证书图片编号
     */
    private String            scoreCertno;
    /**
     * 证书编号
     */
    private String            scoreCertSn;
    /**
     * 证书图片地址
     */
    private String            certPicUrl;

    public String getScoreCertno() {
        return scoreCertno;
    }

    public void setScoreCertno(String scoreCertno) {
        this.scoreCertno = scoreCertno;
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

}
