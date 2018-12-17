package com.efida.sport.open.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常用日期函数
 * @author zhiyang
 * @version $Id: DateUtil.java, v 0.1 2016年5月19日 下午7:29:13 zhiyang Exp $
 */
public class DateUtil {

    /**logger*/
    private static Logger      logger                 = LoggerFactory.getLogger(DateUtil.class);

    /** yyyyMMdd */
    public final static String SHORT_FORMAT           = "yyyyMMdd";

    /** yyyyMMddHHmmss */
    public final static String LONG_FORMAT            = "yyyyMMddHHmmss";

    /** yyyy-MM-dd */
    public final static String WEB_FORMAT             = "yyyy-MM-dd";

    /** HHmmss */
    public final static String TIME_FORMAT            = "HHmmss";

    /** yyyyMM */
    public final static String MONTH_FORMAT           = "yyyyMM";

    /** yyyy年MM月dd日 */
    public final static String CHINA_FORMAT           = "yyyy年MM月dd日";

    /** yyyy-MM-dd HH:mm:ss */
    public final static String LONG_WEB_FORMAT        = "yyyy-MM-dd HH:mm:ss";

    /** yyyy-MM-dd HH:mm */
    public final static String LONG_WEB_FORMAT_NO_SEC = "yyyy-MM-dd HH:mm";

    /**
     * 判定是否为空或者null
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {

        if (str == null || str.length() < 1) {
            return true;
        }

        return false;
    }

    /**
     * 判定字符串是否为null
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {

        return str == null;
    }

    /**
     * 日期对象解析成日期字符串基础方法，可以据此封装出多种便捷的方法直接使用
     * 
     * @param date 待格式化的日期对象
     * @param format 输出的格式
     * @return 格式化的字符串
     */
    public static String format(Date date, String format) {
        if (date == null || format.length() < 1) {
            return "";
        }

        return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).format(date);
    }

    /**
     * 格式化当前时间
     * 
     * @param format 输出的格式
     * @return
     */
    public static String formatCurrent(String format) {
        if (isBlank(format)) {
            return "";
        }

        return format(new Date(), format);
    }

    /**
     * 日期字符串解析成日期对象基础方法，可以在此封装出多种便捷的方法直接使用
     * 
     * @param dateStr 日期字符串
     * @param format 输入的格式
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date parseWithNoThrow(String dateStr, String format) {
        if (isBlank(format)) {
            return null;
        }

        if (dateStr == null || dateStr.length() < format.length()) {
            return null;
        }

        try {
            return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String dateStr, String format) throws ParseException {
        if (isBlank(format)) {
            throw new ParseException("format can not be null.", 0);
        }

        if (dateStr == null || dateStr.length() < format.length()) {
            throw new ParseException("date string's length is too small.", 0);
        }

        return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).parse(dateStr);
    }

    /**
     * 日期字符串格式化基础方法，可以在此封装出多种便捷的方法直接使用
     * 
     * @param dateStr 日期字符串
     * @param formatIn 输入的日期字符串的格式
     * @param formatOut 输出日期字符串的格式
     * @return 已经格式化的字符串
     * @throws ParseException
     */
    public static String format(String dateStr, String formatIn, String formatOut)
                                                                                  throws ParseException {

        Date date = parse(dateStr, formatIn);
        return format(date, formatOut);
    }

    /**
     * 把日期对象按照<code>yyyyMMdd</code>格式解析成字符串
     * 
     * @param date 待格式化的日期对象 
     * @return 格式化的字符串
     */
    public static String formatShort(Date date) {
        return format(date, SHORT_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyyMMddHHmmss</code>格式解析成字符串
     * 
     * @param date 待格式化的日期对象 
     * @return 格式化的字符串
     */
    public static String formatLong(Date date) {
        return format(date, LONG_FORMAT);
    }

    /**
     * 把日期字符串按照<code>yyyyMMdd</code>格式，进行格式化
     * 
     * @param dateStr 待格式化的日期字符串
     * @param formatIn 输入的日期字符串的格式 
     * @return 格式化的字符串
     */
    public static String formatShort(String dateStr, String formatIn) throws ParseException {
        return format(dateStr, formatIn, SHORT_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyy-MM-dd</code>格式解析成字符串
     * 
     * @param date 待格式化的日期对象 
     * @return 格式化的字符串
     */
    public static String formatWeb(Date date) {
        return format(date, WEB_FORMAT);
    }

    /**
     * 把日期字符串按照<code>yyyy-MM-dd</code>格式，进行格式化
     * 
     * @param dateStr 待格式化的日期字符串
     * @param formatIn 输入的日期字符串的格式 
     * @return 格式化的字符串
     * @throws ParseException 
     */
    public static String formatWeb(String dateStr, String formatIn) throws ParseException {
        return format(dateStr, formatIn, WEB_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyyMM</code>格式解析成字符串
     * 
     * @param date 待格式化的日期对象 
     * @return 格式化的字符串
     */
    public static String formatMonth(Date date) {

        return format(date, MONTH_FORMAT);
    }

    /**
     * 把日期对象按照<code>HHmmss</code>格式解析成字符串
     * 
     * @param date 待格式化的日期对象 
     * @return 格式化的字符串
     */
    public static String formatTime(Date date) {
        return format(date, TIME_FORMAT);
    }

    /**
     * 将字符串转换为data
     * @param sDate yyyyMMddHHmmss
     * @return
     */
    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(LONG_FORMAT);
        Date d = null;

        if ((sDate != null) && (sDate.length() == LONG_FORMAT.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }

        return d;
    }

    /**
     * 在日志字符串上进行秒的增加操作
     * @param dateStr    日期字符串
     * @param format     格式
     * @param second     秒
     * @return
     */
    public static String add(String dateStr, String format, int second) {
        Date time = null;
        try {
            time = parse(dateStr, format);
        } catch (ParseException e) {
            // 什么也不做
            logger.warn("DateUtil解析错误" + e);
        }

        if (null == time) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.SECOND, second);

        return format(cal.getTime(), format);
    }

    /**
     * 计算时间差(s)
     * 
     * @param timeStr1
     * @param timeStr2
     * @param format
     * @return
     */
    public static int sub(String timeStr1, String timeStr2, String format) {
        Date time1 = null, time2 = null;
        try {
            time1 = parse(timeStr1, format);
            time2 = parse(timeStr2, format);
        } catch (ParseException e) {
            // 什么也不做
            logger.warn("DateUtil解析错误" + e);
        }
        if (time1 == null && time2 == null) {
            return 0;
        }
        Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
        c1.setTime(time1);
        c2.setTime(time2);
        long gap = (c1.getTimeInMillis() - c2.getTimeInMillis()) / 1000;
        return (int) gap;
    }

    /**
     * 两个日期相差多少秒 
     *  
     * @param date1 
     * @param date2 
     * @return 
     */
    public static int getTimeDelta(Date date1, Date date2) {
        long timeDelta = (date1.getTime() - date2.getTime()) / 1000;//单位是秒  
        int secondsDelta = timeDelta > 0 ? (int) timeDelta : (int) Math.abs(timeDelta);
        return secondsDelta;
    }

}
