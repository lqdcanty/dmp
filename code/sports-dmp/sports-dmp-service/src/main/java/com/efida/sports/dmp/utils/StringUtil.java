package com.efida.sports.dmp.utils;

/**
 * @author wang yi
 * @desc 
 * @version $Id: StringUtil.java, v 0.1 2018年6月19日 上午11:11:34 wang yi Exp $
 */
public class StringUtil {

    /**
     * 截断字符串
     * 
     * @param str
     * @param maxLen
     * @return
     */
    public static String trimMaxStr(String str, int maxLen) {

        if (str == null) {
            return null;
        }

        if (str.length() <= maxLen) {
            return str;
        }

        return str.substring(0, maxLen);

    }

    public static String getStringFromStringArray(String[] strArrays, String separator) {
        if (strArrays == null || strArrays.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArrays.length; i++) {
            if (i != 0) {
                sb.append(separator);
            }
            sb.append(strArrays[i]);

        }
        return sb.toString();
    }

    public static String[] converIntArrayToStringArray(Integer[] intArray) {
        if (intArray == null) {
            return null;

        }
        String[] strs = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            strs[i] = String.valueOf(intArray[i]);
        }
        return strs;

    }

    /**
     * 数组值比较
      * arrayEqualsValue(这里用一句话描述这个方法的作用)
      * @author zhenyong.Guan
      * @param @param array
      * @param @param target
      * @param @return    参数
      * @return boolean    返回类型
     */
    public static <T> boolean arrayEqualsValue(T[] array, T target) {
        for (T t : array) {
            if (t.equals(target)) {
                return true;
            }
        }
        return false;
    }

}
