/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.result;

import java.util.List;

import com.efida.sport.open.model.OpenScoreRankResultModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreRankSubmitResult.java, v 0.1 2018年7月10日 上午12:07:56 zhiyang Exp $
 */
public class OpenScoreRankSubmitResult {

    /**
     * 返回json相关
     */
    public String                         resultCode = null;

    public String                         message    = null;
    public Integer                        count      = 0;
    public List<OpenScoreRankResultModel> data       = null;

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
    public List<OpenScoreRankResultModel> getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     * 
     * @param data value to be assigned to property data
     */
    public void setData(List<OpenScoreRankResultModel> data) {
        this.data = data;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OpenScoreSubmitResult [resultCode=" + resultCode + ", message=" + message
               + ", count=" + count + ", data=" + data + "]";
    }

}
