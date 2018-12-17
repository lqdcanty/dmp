/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zoutao
 * @version $Id: ScoreExtPro.java, v 0.1 2018年8月5日 下午4:14:00 zoutao Exp $
 */
public class ScoreGridData implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private List<ScoreHeader> header;

    private ScoreDetail       detail;

    public List<ScoreHeader> getHeader() {
        return header;
    }

    public void setHeader(List<ScoreHeader> headers) {
        this.header = headers;
    }

    public ScoreDetail getDetail() {
        return detail;
    }

    public void setDetail(ScoreDetail detail) {
        this.detail = detail;
    }

}
