package com.efida.sports.cert.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字转换工具
 * 
 * @author zengbo
 * @version $Id: NumberUtil.java, v 0.1 2018年8月30日 下午3:18:20 zengbo Exp $
 */
public class NumberUtil {

    /**
     * 
     * 保留2位小数
     * @param molecule　
     * @param denominator　
     * @return
     */
    public static String percentageConvertDecimal(int molecule, int denominator) {
        if (molecule == 0 || denominator == 0) {
            return "0%";
        }
        float res = (float) molecule / denominator;
        float b = (float) (Math.round(res * 10000)) / 10000;
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String percent = fnum.format(b * 100);
        return percent + "%";
    }

    /**
     * 
     * 获取整数百分比
     * @param molecule　
     * @param denominator　
     * @return
     */
    public static String percentageConvert(int molecule, int denominator) {
        if (molecule == 0 || denominator == 0) {
            return "0%";
        }
        double f1 = molecule * 1.0 / denominator * 1.0 * 100;

        //        double f1 = new BigDecimal((float) molecule / denominator)
        //            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String percent = "";
        long reten = (long) f1;
        if (reten == f1) {
            percent = reten + "%";
        } else {
            percent = new BigDecimal(f1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
        }
        return percent;
    }

    /**
     * 数字转字符串
     * 
     * @param number
     * @return
     */
    public static String intFormatStr(int number) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(number);
    }

    /**
     * 数字转字符串
     * 
     * @param number
     * @return
     */
    public static String longFormatStr(long number) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(number);
    }

    public static Double trimZeroNum(Double num) {

        String str = trimZero(num);
        if (str == null) {
            return null;
        }
        return Double.parseDouble(str);
    }

    /**
     * 去掉double后面的0
     * 
     * @param num
     * @return
     */
    public static String trimZero(Double num) {
        if (num == null) {
            return null;
        }
        String str = num + "";
        String nstr = "";
        boolean find = str.indexOf(".") >= 0;
        if (!find) {
            return str;
        }
        int endIndex = str.length() - 1;
        for (int i = endIndex; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '0') {
                continue;
            }
            if (c == '.') {
                endIndex = i;
            } else {
                endIndex = i + 1;
            }
            break;
        }
        return str.substring(0, endIndex);

    }

    public static void main(String[] args) {

        Double num = 0.0000;

        System.out.println(NumberUtil.trimZero(num));
        num = 0.03320000;
        System.out.println(NumberUtil.trimZero(num));
        num = 1.0;
        System.out.println(NumberUtil.trimZero(num));
        num = 1.0323;
        System.out.println(NumberUtil.trimZero(num));

    }
}
