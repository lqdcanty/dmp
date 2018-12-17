/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package cn.evake.auth.util;

/**
 * 业务参数断言工具
 * 
 * @author zhiyang
 * @version $Id: AssertUtils.java, v 0.1 2016年7月8日 下午4:15:53 zhiyang Exp $
 */
public class AssertUtils {

    /**
     * 取值不能为null
     * 
     * @param value
     * @param tipInfo
     * @throws DmpBusException
     */
    public static void assertNotNull(Object value, String tipInfo) throws Exception {

        if (value == null) {
            throw new Exception();
        }

        return;
    }

    /**
     * 取值不能为null or 空字符串
     * 
     * @param value
     * @param tipInfo
     * @throws Exception 
     * 
     */
    public static void assertNotEmpty(String value, String tipInfo) throws Exception {

        if (value == null || value.length() < 1) {
            throw new Exception(tipInfo);
        }

        return;
    }

    /**
     * 
     * 
     * @param value
     * @param fieldName
     * @param minLength
     *            最小长度
     * @param maxLength
     *            最大长度
     * @throws DmpBusException
     */
    public static void assertLengthRange(String value, String fieldName, int minLength,
                                         int maxLength) throws Exception {

        if (minLength > maxLength) {
            throw new RuntimeException(
                "编码错误!minLength:" + minLength + " 大于 maxLength: " + maxLength);
        }
        int len = value.length();
        if (len < minLength) {
            throw new Exception(fieldName + "长度必须大于等于" + minLength);
        }

        if (len > maxLength) {
            throw new Exception(fieldName + "长度必须小于等于" + minLength);
        }

        return;
    }

    /**
     * 
     * @param equals
     * @param string
     * @throws Exception 
     */
    public static void assertEqual(boolean equals, String string) throws Exception {
        if (!equals) {
            throw new Exception(string);
        }

        return;
    }
}
