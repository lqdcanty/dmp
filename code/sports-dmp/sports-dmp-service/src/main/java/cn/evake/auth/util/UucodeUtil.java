/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util;

import java.util.Date;

/**
 * 随机编号工具类
 * @author Evance
 * @version $Id: UucodeUtil.java, v 0.1 2018年3月24日 下午7:45:55 Evance Exp $
 */
public class UucodeUtil {

    /**
     * 
     * 获取随机ID
     * @return
     */
    public static String getUuid() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * 获取时间随机
     * @return
     */
    public static String getUUdate(String fomat) {
        String formatDate = DateUtil.formatDate(new Date(), fomat);
        return formatDate;
    }

    public static String getUdate() {
        String fomat = "yyyyMMddHHmmss";
        return getUUdate(fomat);
    }

    /**
     * 获取时间随机
     * @param prefix
     * @return
     */
    public static String getUucode(String prefix) {
        return prefix + getUdate();
    }

    /**
     * 获取时间随机
     * @param prefix
     * @return
     */
    public static String getUucode() {
        return getUdate();
    }

    /**
     * 获取随机编码
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public static String getUxCode() {
        return SecretUtil.encode(getUucode());
    }

    /**
     * 
     * 获取时间随机
     * @param prefix
     * @return
     */
    public static String getUdate(String prefix) {
        return prefix + getUdate();
    }

}
