/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

/**
 * 
 * @author zoutao
 * @version $Id: ScoreHearder.java, v 0.1 2018年8月5日 下午4:50:58 zoutao Exp $
 */
public class ScoreHeader implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 列索引（与data关联）
     */
    private String            dataInex;
    /**
     * 列名
     */
    private String            header;
    /**
     * pc是否展示
     */
    private boolean           pcIsShow         = true;
    /**
     * 移动端是否展示
     */
    private boolean           h5IsShow         = true;
    /**
     * 
     */
    private String            width;

    private String            h5Width;

    public String getDataInex() {
        return dataInex;
    }

    public void setDataInex(String dataInex) {
        this.dataInex = dataInex;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isPcIsShow() {
        return pcIsShow;
    }

    public void setPcIsShow(boolean pcIsShow) {
        this.pcIsShow = pcIsShow;
    }

    public boolean isH5IsShow() {
        return h5IsShow;
    }

    public void setH5IsShow(boolean h5IsShow) {
        this.h5IsShow = h5IsShow;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getH5Width() {
        return h5Width;
    }

    public void setH5Width(String h5Width) {
        this.h5Width = h5Width;
    }

}
