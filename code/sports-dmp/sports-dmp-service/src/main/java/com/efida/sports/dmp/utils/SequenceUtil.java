/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.efida.sports.dmp.utils;

/**
 * 
 * @author wangyi
 * @version $Id: SequenceUtil.java, v 0.1 2017年11月28日 上午10:37:56 chenyao Exp $
 */
public class SequenceUtil {

    /** 商品前缀 */
    private static final String PRODUCT  = "PR";
    /** 报价 */
    public static String        QUOTE    = "QU";
    /** 合同 */
    public static String        CONTRACT = "HT";

    private static String       base     = "0123456789";

    /**
     * 
     * @param type
     * @param timestamp
     * @return
     */
    public static String generateSequence(String type, long timestamp) {

        return type + getCurrentTime(timestamp) + getRandomString(4);
    }

    /**
     * 获取报价
     * @return
     */
    public static String generateQuoteSequence() {

        return QUOTE + getCurrentTime() /*+ getRandomString(4)*/;
    }

    public static String generateHTSequence() {

        return CONTRACT + getCurrentTime();
    }

    public static String generateProductImportSequence() {
        return PRODUCT + "00000000000000";
    }

    /**
     * 返回当前时间，按字符串返回 
     * 格式为:yyyyMMddHHmmss
     * @return 
     */
    private static String getCurrentTime() {

        return DateUtil.formateDate(System.currentTimeMillis(), "yyyyMMddHHmmss");
    }

    /**
     * 返回当前时间，按字符串返回 
     * 格式为:yyyyMMddHHmmss
     * @return 
     */
    private static String getCurrentTime(long timestamp) {

        return DateUtil.formateDate(timestamp, "yyyyMMddHHmmss");
    }

    /**
     * 返回指定长度随机字符串
     * 
     * 这里 随机种子采用第一次初始化为准； 而不是每次都初始化种子 ，
     * （调用者序列号前部已经按时间设置）
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度  

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (base.length() * Math.random());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String generateProductSequence() {
        return PRODUCT + getCurrentTime() /*+ getRandomString(8)*/;
    }
}
