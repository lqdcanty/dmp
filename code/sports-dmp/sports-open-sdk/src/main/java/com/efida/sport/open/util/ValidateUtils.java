package com.efida.sport.open.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.result.ErrorCode;

/**
 * 验证工具类
 * @author zhiyang
 * @version $Id: ValidateUtils.java, v 0.1 2016年5月25日 下午10:18:45 zhiyang Exp $
 */
public class ValidateUtils {

    /**
     * 取值不能为null
     * 
     * @param value
     * @param tipInfo
     * @throws OpenException 
     */
    public static void assertNotNull(Object value, String tipInfo) throws OpenException {

        if (value == null) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, tipInfo);
        }

        return;
    }

    /**
     * 断言为true
     * 
     * @param isTrue
     * @param tipInfo
     * @throws OpenException
     */
    public static void assertTrue(Boolean isTrue, String tipInfo) throws OpenException {

        if (!isTrue) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, tipInfo);
        }

        return;
    }

    /**
     * 取值不能为null or  空字符串
     * 
     * @param value
     * @param tipInfo
     * @throws OpenException 
     */
    public static void assertNotEmpty(String value, String tipInfo) throws OpenException {

        if (value == null || value.length() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, tipInfo);
        }

        return;
    }

    /**
     * 
     * 
     * @param value
     * @param fieldName
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @throws OpenException
     */
    public static void assertLengthRange(String value, String fieldName, int minLength,
                                         int maxLength) throws OpenException {

        if (minLength > maxLength) {
            throw new RuntimeException(
                "编码错误!minLength:" + minLength + " 大于 maxLength: " + maxLength);
        }
        int len = value.length();
        if (len < minLength) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                fieldName + "长度必须大于等于" + minLength);
        }

        if (len > maxLength) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                fieldName + "长度必须小于等于" + minLength);
        }

        return;
    }

    public static void validatePhone(String value, String tipInfo) throws OpenException {

        String regExPhone = "^\\d{3}|d{4}-?\\d{8}|\\d{4}-?\\d{8}|\\d{4}-?\\d{7}$";

        Pattern pattern = Pattern.compile(regExPhone, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, tipInfo);
        }
    }
}
