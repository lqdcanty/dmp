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
 * @version $Id: ScoreDetail.java, v 0.1 2018年8月5日 下午4:22:03 zoutao Exp $
 */
public class ScoreDetail implements Serializable {
    /**  */
    private static final long  serialVersionUID = 1L;
    //总记录数
    private int                total;
    private int                curentPage       = 1;
    private int                pageSize         = 50;

    private List<List<String>> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurentPage() {
        return curentPage;
    }

    public void setCurentPage(int curentPage) {
        this.curentPage = curentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
