package com.efida.sports.dmp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 
 * 签名工具类
 * @author wang yi
 * @desc 
 * @version $Id: MD5Utils.java, v 0.1 2017年12月27日 下午2:54:19 wang yi Exp $
 */
public class MD5Utils {

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"   };

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String result = null;
        try {
            result = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteArrayToHexString(md.digest(result.getBytes("UTF-8")));
        } catch (Exception exception) {
        }
        return result;
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
            result = byte2hex(md.digest(orgin.toString().getBytes("UTF-8")));
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

    public static String getFileMD5(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            byte[] fileBytes = bos.toByteArray();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(fileBytes, 0, fileBytes.length);
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Sign file error!");
        }
    }
}
