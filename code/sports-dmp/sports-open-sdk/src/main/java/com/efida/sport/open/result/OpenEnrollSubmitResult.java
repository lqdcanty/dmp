/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.result;

import java.util.List;

import com.efida.sport.open.model.OpenEnrollResultModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollSubmitResult.java, v 0.1 2018年5月30日 下午12:46:18 zhiyang Exp $
 */
public class OpenEnrollSubmitResult {

    /**
     * 返回json相关
     */
    public String                      resultCode = null;
    public String                      message    = null;
    public Integer                     count      = 0;
    public List<OpenEnrollResultModel> data       = null;

    /**
     * Getter method for property <tt>resultCode</tt>.
     * 
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     * 
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>count</tt>.
     * 
     * @return property value of count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Setter method for property <tt>count</tt>.
     * 
     * @param count value to be assigned to property count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Getter method for property <tt>data</tt>.
     * 
     * @return property value of data
     */
    public List<OpenEnrollResultModel> getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     * 
     * @param data value to be assigned to property data
     */
    public void setData(List<OpenEnrollResultModel> data) {
        this.data = data;
    }

}
