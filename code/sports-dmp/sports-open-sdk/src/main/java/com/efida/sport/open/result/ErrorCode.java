package com.efida.sport.open.result;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

/**
 * 
 * 定义错误码. 
 * 
 * @author zhiyang
 * @version $Id: ErrorCode.java, v 0.1 2016年5月19日 下午2:03:53 zhiyang Exp $
 */
public class ErrorCode {

    /**
     * 执行正常
     */
    public final static int OK                     = 0;
    /**
     * 未分类错误
     */
    public final static int UNKNOW_ERROR           = 1;

    /**
     * 重复提交，曾经成功提交了该订单;客户端收到该错误码应该当作成功处理。
     */
    public final static int ALREADY_SUCCESS_SUBMIT = 2;

    /**
     * 必填参数为空。
     */
    public final static int PARAMETER_EMPTY        = 4;

    /**
     * 必填参数无效。
     */
    public final static int PARAMETER_INVALID      = 5;

    /**
     * 验证签名失败
     */
    public final static int VALID_SIGNATURE_ERROR  = 8;

}
