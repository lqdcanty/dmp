package com.efida.sport.open.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.result.ErrorCode;

/**
 * 字符串
 * @author zhiyang
 * @version $Id: StringUtils.java, v 0.1 2016年5月24日 上午11:31:14 zhiyang Exp $
 */
public class StringUtils {

    /**
     * 日志工具类
     */
    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 判定两个字符串是否相同. 
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {

        if (str1 == null || str2 == null) {
            return str1 == str2;
        }

        return str1.equals(str2);
    }

    /**
     * 字符串是否为null or 空字符串
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() < 1) {
            return true;
        }

        return false;
    }

    /**
     * 获取utf-8编码数据
     * 
     * @param str
     * @return
     * @throws OpenException 
     */
    public static String getUrlEncodeStr(String str) throws OpenException {

        if (str == null || str.length() < 1) {
            return str;
        }

        try {
            return URLEncoder.encode(str, ServiceEnvConstants.CHARSET);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "编码错误!");
        }

    }

    /**
     * 获取utf-8解码数据
     * 
     * @param str
     * @return
     * @throws OpenException 
     */
    public static String getUrlDecodeStr(String str) throws OpenException {

        if (str == null || str.length() < 1) {
            return str;
        }

        try {
            return URLDecoder.decode(str, ServiceEnvConstants.CHARSET);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "编码错误!");
        }

    }
}
