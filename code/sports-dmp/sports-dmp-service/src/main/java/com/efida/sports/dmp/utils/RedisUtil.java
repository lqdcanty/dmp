/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.efida.sports.dmp.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author lijiaqi
 * @desc 
 * @version $Id: RedisUtil.java, v 0.1 2016年11月9日 下午5:52:25 lijiaqi Exp $
 */
public class RedisUtil {

    private static final String charset = "UTF-8";

    public static byte[] convert(String val) {
        try {
            if (val == null) {
                return null;
            }
            return val.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列号对象
     * 
     * @param object
     * @return
     * @throws IOException 
     */
    public static byte[] serialize(Object object) {
        byte[] bytes = null;
        try {
            ObjectOutputStream objectOutputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;

    }

    /**
     * 反序列化对象 
     * 
     * @param bytes
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object deSeialize(byte[] bytes) {
        Object result = null;
        try {
            ByteArrayInputStream byteArrayOutputStream = null;
            byteArrayOutputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            result = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
