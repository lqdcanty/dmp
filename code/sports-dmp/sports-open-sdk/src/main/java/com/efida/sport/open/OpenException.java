package com.efida.sport.open;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

/**
 * 异常情况
 * @author zhiyang
 * @version $Id: OpenException.java, v 0.1 2016年5月19日 下午1:55:33 zhiyang Exp $
 */
public class OpenException extends Exception {

    // 错误码
    private int               code;
    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 构造异常
     * 
     * @param code 异常状态码
     * @param msg 异常讯息
     */
    public OpenException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 构造异常
     * 
     * @param code 异常状态码
     * @param ex 异常来源
     */
    public OpenException(int code, Exception ex) {
        super(ex);
        this.code = code;
    }

    /**
     * 
     * @return 异常状态码。
     */
    public int getErrorCode() {
        return code;
    }

}
