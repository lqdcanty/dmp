/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.result;

import java.util.List;

import com.efida.sport.open.model.OpenEnrollxResultModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollxSubmitResult.java, v 0.1 2018年7月27日 下午3:33:18 zhiyang Exp $
 */
public class OpenEnrollxSubmitResult {

    /**
     * 返回json相关
     */
    public String                       resultCode = null;
    public String                       message    = null;
    public Integer                      count      = 0;
    public List<OpenEnrollxResultModel> data       = null;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<OpenEnrollxResultModel> getData() {
        return data;
    }

    public void setData(List<OpenEnrollxResultModel> data) {
        this.data = data;
    }

}
