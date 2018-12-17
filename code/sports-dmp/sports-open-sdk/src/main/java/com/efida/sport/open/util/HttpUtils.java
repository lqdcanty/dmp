/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.efida.sport.open.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.result.ErrorCode;

/**
 * http工具
 * @author zhiyang
 * @version $Id: HttpUtils.java, v 0.1 2016年5月24日 下午4:25:26 zhiyang Exp $
 */
public class HttpUtils {

    private static Logger      logger            = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 连接发送数据超时时间
     */
    public static final int    CONNECTTIMEOUT    = 10 * 60 * 1000;

    /**
     * 读取数据超时时间
     */
    public static final int    READTIMEOUT       = 10 * 60 * 1000;

    public static final String charset           = "UTF-8";

    /**
     * http 头
     */
    public static final String JSON_CONTENT_TYPE = "application/json";

    /**
     * 
     * 
     * @param url
     * @param map
     * @return
     */
    public static String executePost(String url, Map<String, String> map) {
        logger.info("start executePost, url:" + url);
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (map != null) {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> elem = (Entry<String, String>) iterator.next();
                    String value = elem.getValue();
                    if (value == null) {
                        value = "";
                    }
                    list.add(new BasicNameValuePair(elem.getKey(), value));
                }
            }

            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            logger.info("", ex);
        }
        return result;
    }

    /**
     * 
     *  开放接口客户端http get 查询通用方法
     * @param queryUrl
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @throws OpenException 
     */
    public static String executeGet(String queryUrl, int connectionTimeout, int readTimeout)
                                                                                            throws OpenException {
        if (logger.isInfoEnabled()) {
            logger.info("start post data, queryUrl:" + queryUrl);
        }
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {

            url = new URL(queryUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("User-Agent", "znty-open-api");

            // 连接
            connection.connect();
            int code = connection.getResponseCode();
            // 如果返回状态码200
            if (200 == code) {
                // 读取返回数据
                in = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(in);
                StringBuffer strBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    strBuffer.append(line);
                }
                result = strBuffer.toString();
            }
            if (code > 400) {
                throw new OpenException(ErrorCode.UNKNOW_ERROR, "服务器响应错误, code " + code);
            }
        } catch (MalformedURLException e) {
            logger.error("url error, url:" + url, e);
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "不合法的http地址, url:" + url);
        } catch (IOException e) {
            logger.error("io exception , url:" + url, e);
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "读取数据失败");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new OpenException(ErrorCode.UNKNOW_ERROR, "关闭流失败");
                }
            }
        }
        return result;

    }

    /**
     * 给url地址添加参数
     * @throws UnsupportedEncodingException 
     */
    public static String urlAddParams(String url, String paramName, String pararmValue)
                                                                                       throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        String[] urls = url.split("[?]");
        pararmValue = URLEncoder.encode(String.valueOf(pararmValue), "utf-8");
        if (urls.length > 1) {
            return url + "&" + paramName + "=" + pararmValue;
        } else {
            return url + "?" + paramName + "=" + pararmValue;
        }
    }
}
