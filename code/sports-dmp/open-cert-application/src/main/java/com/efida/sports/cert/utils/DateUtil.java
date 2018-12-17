/**
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间格式化
 * @author wang yi
 * @desc 
 * @version $Id: DateUtil.java, v 0.1 2018年3月8日 下午1:47:10 wang yi Exp $
 */
public class DateUtil {
    private final static String    FORMAT_NORMAL          = "yyyy-MM-dd HH:ss:mm";

    public static final long       MINUTETIMESTAMP        = 60 * 1000l;

    public static final long       HOURTIMESTAMP          = 60 * 60 * 1000l;

    public static final long       DAYTIMESTAMP           = 24 * 60 * 60 * 1000l;

    public static final long       MONTHTIMESTAMP         = 30 * 24 * 60 * 60 * 1000l;

    public static final long       YEARTIMESTAMP          = 365 * 24 * 60 * 60 * 1000l;

    public static SimpleDateFormat sdf_chinese_date       = new SimpleDateFormat(
        "yyyy年MM月dd日 HH:mm:ss");
    public static SimpleDateFormat sdf_chinese            = new SimpleDateFormat("yyyy年MM月dd日");

    public static SimpleDateFormat sdf_simple             = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat sdf                    = new SimpleDateFormat("yyyy-MM-dd");

    /** yyyyMMdd */
    public final static String     SHORT_FORMAT           = "yyyyMMdd";

    /** yyyyMMddHHmmss */
    public final static String     LONG_FORMAT            = "yyyyMMddHHmmss";

    /** yyyy-MM-dd */
    public final static String     WEB_FORMAT             = "yyyy-MM-dd";

    /** HHmmss */
    public final static String     TIME_FORMAT            = "HHmmss";

    /** yyyyMM */
    public final static String     MONTH_FORMAT           = "yyyyMM";

    /** yyyy年MM月dd日 */
    public final static String     CHINA_FORMAT           = "yyyy年MM月dd日";

    /** yyyy-MM-dd HH:mm:ss */
    public final static String     LONG_WEB_FORMAT        = "yyyy-MM-dd HH:mm:ss";

    /** yyyy-MM-dd HH:mm */
    public final static String     LONG_WEB_FORMAT_NO_SEC = "yyyy-MM-dd HH:mm";

    /** yyyy/MM/dd*/
    public final static String     NORMAL_FORMAT          = "yyyy/MM/dd";

    public static SimpleDateFormat sdf1                   = new SimpleDateFormat("yyyy/MM/dd");

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getDateWithAddYears(long nowTime, int increaseYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowTime);
        calendar.add(Calendar.YEAR, increaseYear);
        return calendar.getTime();
    }

    /**
     * 格式化聊天时间
     * 
     * @param timestamp
     * @return
     */
    public static String formateShowDate(long timestamp) {
        long nowTime = System.currentTimeMillis();
        // 如果小于一分钟
        final long differenceTime = nowTime - timestamp;
        if (differenceTime < MINUTETIMESTAMP) {
            long differnce = Math.abs((differenceTime) / 1000);
            if (differnce == 0) {
                differnce = 1;
            }
            return differnce + "秒前";
        }

        if (differenceTime < HOURTIMESTAMP) {
            long differnce = (differenceTime) / MINUTETIMESTAMP;
            if (differnce == 0) {
                differnce = 1;
            }
            return differnce + "分钟前";
        }

        if (differenceTime < DAYTIMESTAMP) {
            long differnce = (nowTime - timestamp) / HOURTIMESTAMP;
            if (differnce == 0) {
                differnce = 1;
            }
            return differnce + "小时前";
        }

        int differenceDay = (int) (differenceTime / DAYTIMESTAMP);

        if (differenceDay < 15) {
            return differenceDay + "天前";
        }

        if (differenceDay < 30) {
            return "半个月前";
        }
        // 小于半年
        if (differenceDay > 30 && differenceDay < 365) {
            int differenceMonth = differenceDay / 30;
            if (differenceMonth == 6) {
                return "半年前";
            }
            return differenceMonth + "个月前";
        }

        int differenceYear = differenceDay / 365;
        return differenceYear + "年前";

    }

    /**
     * 格式化聊天时间
     * 
     * @param timestamp
     * @return
     */
    public static String formateStockDate(long timestamp) {
        long nowTime = System.currentTimeMillis();

        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.set(Calendar.MILLISECOND, 0);
        todayEnd.add(Calendar.DAY_OF_MONTH, 1);

        if (timestamp >= todayStart.getTimeInMillis() && timestamp <= todayEnd.getTimeInMillis()) {
            return "今天 " + formateDate(timestamp, "HH:mm");
        }

        Calendar yesterdayStart = Calendar.getInstance();
        yesterdayStart.set(Calendar.HOUR, 0);
        yesterdayStart.set(Calendar.MINUTE, 0);
        yesterdayStart.set(Calendar.SECOND, 0);
        yesterdayStart.set(Calendar.MILLISECOND, 0);
        yesterdayStart.add(Calendar.DAY_OF_MONTH, -1);

        if (timestamp >= yesterdayStart.getTimeInMillis()
            && timestamp < todayStart.getTimeInMillis()) {
            return "昨天 " + formateDate(timestamp, "HH:mm");
        }

        Calendar theDayBeforeYesterdayStart = Calendar.getInstance();
        theDayBeforeYesterdayStart.set(Calendar.HOUR, 0);
        theDayBeforeYesterdayStart.set(Calendar.MINUTE, 0);
        theDayBeforeYesterdayStart.set(Calendar.SECOND, 0);
        theDayBeforeYesterdayStart.set(Calendar.MILLISECOND, 0);
        theDayBeforeYesterdayStart.add(Calendar.DAY_OF_MONTH, -2);

        if (timestamp >= theDayBeforeYesterdayStart.getTimeInMillis()
            && timestamp < yesterdayStart.getTimeInMillis()) {
            return "前天 " + formateDate(timestamp, "HH:mm");
        }

        Calendar yearStart = Calendar.getInstance();
        yearStart.set(Calendar.HOUR, 0);
        yearStart.set(Calendar.MINUTE, 0);
        yearStart.set(Calendar.SECOND, 0);
        yearStart.set(Calendar.MILLISECOND, 0);
        yearStart.set(Calendar.MONTH, 0);
        yearStart.set(Calendar.DAY_OF_MONTH, 1);
        if (timestamp >= yearStart.getTimeInMillis()) {
            return formateDate(timestamp, "MM-dd HH:mm");
        }

        return formateDate(timestamp, "yyyy-MM-dd");

    }

    public static String formateDate(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timestamp));
    }

    public static String formateDate(long timestamp, String pattern, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(new Date(timestamp));
    }

    public static String formateChineseDate(long timestamp) {
        return sdf_chinese_date.format(timestamp);
    }

    public static String formateChineseDate(Date date) {
        return sdf_chinese_date.format(date);
    }

    public static String formateChinese(long timestamp) {
        return sdf_chinese.format(timestamp);
    }

    public static String formateChinese(Date date) {
        return sdf_chinese.format(date);
    }

    public static String formateSimpleDate(long timestamp) {
        return sdf_simple.format(new Date(timestamp));
    }

    public static String formateSimpleDate(Date date) {
        return sdf_simple.format(date);
    }

    public static Integer getDateYear(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取到
     * 
     * @param nowTime
     * @param increaseYear
     * @return
     */
    public static long getAddDateWithIncreaseYear(long nowTime, int increaseYear) {
        int nowyear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, increaseYear + nowyear);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取时间差
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String getTimeDiffernce(long beginTime, long endTime) {

        long timeDifference = endTime - beginTime;
        if (timeDifference <= 0) {
            return "";
        }
        int dayDiffernce = (int) (timeDifference / DAYTIMESTAMP);

        if (dayDiffernce < 30) {
            return dayDiffernce + "天";
        } else if (dayDiffernce == 30) {
            return "1个月";
        }

        int monthDiffernce = (int) (timeDifference / MONTHTIMESTAMP);

        if (monthDiffernce < 12) {
            return monthDiffernce + "个月";
        } else if (monthDiffernce == 12) {
            return "1年";
        }

        int yearDiffernce = (int) (timeDifference / YEARTIMESTAMP);

        if (yearDiffernce >= 1) {
            return yearDiffernce + "年";
        }

        return "";

    }

    public static boolean isDate(String str) {
        boolean convertSuccess = false;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
            convertSuccess = true;
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 将时间字符串转换成时间戳
     * 
     * @param dateStr
     * @return
     */
    public static long parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        long date = 0l;
        try {
            date = Date.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 将时间字符串转换成时间戳
     * @param dateStr
     * @return
     */
    public static long parseDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date.getTime();
    }

    /**
     * 将时间字符串转换成Date<br/>
     * example Thu Jun 04 17:18:17 +0800 2015
     * 
     * @param dateStr
     * @return
     */
    public static long parseDate(String dateStr, String pattern, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date.getTime();
    }

    public static Date parseStr(String dateStr) {
        Date date = null;
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (Exception e) {

            }
        }
        return date;
    }

    /**
     * 根据时间，格式，将String时间类型处理为Date型，精确到秒
     * 
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseStr(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                date = simpleDateFormat.parse(dateStr);
            } catch (Exception e) {
                date = DateUtil.getCurrentDate();
            }
        }
        return date;
    }

    /**
     * 重置小时为12点
     * 
     * @param date
     * @param hour
     * @return
     * @throws ParseException
     */
    public static Date resetHour(Date date, int hour) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String newDate = sdf.format(date);
        Calendar ca = Calendar.getInstance();
        ca.setTime(sdf.parse(newDate));
        ca.add(Calendar.HOUR_OF_DAY, hour);
        return ca.getTime();
    }

    /**
     * 查询当前日期前(后)x天的日期
     *
     * @param millis
     *            当前日期毫秒数
     * @param day
     *            天数（如果day数为负数,说明是此日期前的天数）
     * @param pattern
     * @return long 毫秒数只显示到天，时间全为0
     */
    public static long beforDateNum(long millis, int day, String pattern) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(Calendar.DAY_OF_YEAR, day);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date(c.getTimeInMillis());
        Date newDate;
        try {
            newDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            newDate = DateUtil.getCurrentDate();
        }
        return newDate.getTime();
    }

    /**
     * 查询当前日期前(后)x天的日期
     *
     * @param millis
     *            当前日期毫秒数
     * @param day
     *            天数（如果day数为负数,说明是此日期前的天数）
     * @return yyyy-MM-dd
     */
    public static String beforLongDate(long millis, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(Calendar.DAY_OF_YEAR, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(c.getTimeInMillis());
        return sdf.format(date);
    }

    /**
     * 日期对象解析成日期字符串基础方法，可以据此封装出多种便捷的方法直接使用
     * 
     * @param date
     *            待格式化的日期对象
     * @param format
     *            输出的格式
     * @return 格式化的字符串
     */
    public static String format(Date date, String format) {
        if (date == null || StringUtils.isEmpty(format)) {
            return "";
        }

        return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).format(date);
    }

    /**
     * 把日期对象按照<code>yyyyMMdd</code>格式解析成字符串
     * 
     * @param date
     *            待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatShort(Date date) {
        return format(date, SHORT_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyyMMddHHmmss</code>格式解析成字符串
     * 
     * @param date
     *            待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatLong(Date date) {
        return format(date, LONG_FORMAT);
    }

    /**
     * 在日志字符串上进行秒的增加操作
     * 
     * @param dateStr
     *            日期字符串
     * @param format
     *            格式
     * @param second
     *            秒
     * @return
     */
    public static String add(String dateStr, String format, int second) {
        Date time = null;
        time = parseStr(dateStr, format);
        if (null == time) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.SECOND, second);
        return format(cal.getTime(), format);
    }

    /**
     * 原来日期基础上增加 制定 秒数
     * @param date
     * @param second
     * @return
     */
    public static Date add(Date date, int second) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * 原来日期基础上增加 制定 秒数
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static String formatDate(Date gmtCreate) {
        if (gmtCreate == null) {
            return null;
        }
        return formatDate(gmtCreate, FORMAT_NORMAL);
    }

    public static String formatDate(Date gmtCreate, String formatNormal) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatNormal);
        return simpleDateFormat.format(gmtCreate);
    }

    /**
     * 
     * 格式化时间
     * @param buildTime
     * @return
     */
    public static Date formatDate(Long buildTime) {
        if (buildTime == null) {
            return null;
        }
        Date date = new Date(buildTime);
        return date;
    }

    /**
     * 断言该数据是否是时间
     * @param publishTime
     * @return 
     */
    public static boolean checkIsDate(String time) {
        try {
            parseDate(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是指定格式时间
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param time
     * @return
     */
    public static boolean checkIsDate(String time, String pattern) {
        try {
            parseDate(time, pattern);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
