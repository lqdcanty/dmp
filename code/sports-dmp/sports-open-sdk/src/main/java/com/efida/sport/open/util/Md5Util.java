package com.efida.sport.open.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Md5工具
 * @author zhiyang
 * @version $Id: Md5Util.java, v 0.1 2016年5月23日 下午7:21:25 zhiyang Exp $
 */
public class Md5Util {

    /**
     * 
     * 通过内容生成md5.
     * 按utf-8编码格式解析 
     * @param base64Content
     * @return
     */
    public static String computeMd5(String content) {
        return signature(content);
    }

    /**
     * 生成有效签名
     * 
     * @param params
     * @param secret
     * @return
     */
    public static String signature(String orgin) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 生成有效签名
     * @param args
     * @return
     */
    public static String signature(String... args) {
        String arr = "";
        for (String arg : args) {
            if (arg != null) {
                arr += arg;
            }
        }
        return signature(arr);
    }

    /**
     * 二行制转字符串
     * 
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 获取单个文件的MD5值！ 
     * @param filePath
     * @return
     */
    public static String getFileMD5(String filePath) {
        return getFileMD5(new File(filePath));
    }

    /**
     * 获取单个文件的MD5值！
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {

        if (!file.isFile()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Sign file error!");
        }
    }

    /**
     * 获取单个文件的MD5值！
     * @param fileBytes
     * @return
     */
    public static String getFileMD5(byte[] fileBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(fileBytes, 0, fileBytes.length);
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Sign file error!");
        }
    }

}
