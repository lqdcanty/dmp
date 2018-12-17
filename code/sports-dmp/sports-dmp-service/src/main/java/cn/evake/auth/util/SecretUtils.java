/**

* evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 加密具类
 * @author Evance
 * @version $Id: PasswordUtil.java, v 0.1 2018年2月25日 下午10:58:33 Evance Exp $
 */
public class SecretUtils {

    /**
      * @param source    原字符串
      */
    public static String getMD5(String source) {
        String result = null;
        try {
            if (source == null) {
                return result;
            }
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(source.getBytes());
            // messageDigest.digest()获得16位长度
            result = bytesToHex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 
     * 二进制转十六进制 
     *  
     * @param bytes 
     * @return 
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串  
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /** 
     * Base64编码 
     *  
     * @param data 待编码数据 
     * @return String 编码数据 
     * @throws UnsupportedEncodingException 
     * @throws Exception 
     */
    public static String encode(String data) {
        // 执行编码  
        byte[] b = Base64.getEncoder().encode(data.getBytes());
        return new String(b);
    }

}
