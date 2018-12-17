/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.result;

import java.util.List;

import com.efida.sport.open.model.OpenProjectModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenProjectQueryResult.java, v 0.1 2018年6月6日 下午1:49:34 zhiyang Exp $
 */
public class OpenProjectQueryResult {

    /**
     * 返回json相关
     */
    public String                 resultCode = null;

    public String                 resultMsg  = null;

    private Integer               count      = 0;
    public List<OpenProjectModel> data       = null;

    /**
     * 
     */
    public OpenProjectQueryResult() {
        super();
    }

    /**
     * @param resultCode
     * @param resultMsg
     * @param count
     * @param data
     */
    public OpenProjectQueryResult(String resultCode, String resultMsg, Integer count,
                                  List<OpenProjectModel> data) {
        super();
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.count = count;
        this.data = data;
    }

}
