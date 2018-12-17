package com.efida.sports.dmp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * Hptp操作工具类
 * 
 * @author wangyi
 * @desc 
 * @version $Id: HttpUtils.java, v 0.1 2017年10月30日 上午11:36:55 wangyi Exp $
 */
public class HttpUtils {

    public static final String POST                 = "POST";

    public static final String GET                  = "GET";

    public static final int    CONNECTTIMEOUT       = 10 * 60 * 1000;

    public static final int    READTIMEOUT          = 10 * 60 * 1000;

    public static final String charset              = "utf-8";

    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String JSON_CONTENT_TYPE    = "application/json";

    public static final String XML_CONTENT_TYPE     = "application/xml";

    public static String executePost(String urlStr, Map<String, Object> params,
                                     Map<String, String> headerOpitions) {
        return executeHttpMethod(POST, urlStr, params, headerOpitions);
    }

    /**
     * post请求
     * @param urlStr
     * @param params
     * @return
     */
    public static String executePost(String urlStr, Map<String, Object> params) {
        return executeHttpMethod(POST, urlStr, params);
    }

    /**
     * @Title http POST json数据
     * @author lijiaqi
     * @description
     * @param urlStr
     * @param jsonStr
     * @return
     */
    public static String executePostJSON(String urlStr, String jsonStr) {
        return executePost(urlStr, jsonStr, JSON_CONTENT_TYPE);
    }

    /**
     * 
     * @Title http post xml数据
     * @author lijiaqi
     * @description
     * @param urlStr
     * @param xmlStr
     * @return
     */
    public static String executePostXML(String urlStr, String xmlStr) {
        return executePost(urlStr, xmlStr, XML_CONTENT_TYPE);
    }

    public static String executePost(String urlStr, String postStr, String contenType) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        String method = "POST";
        InputStreamReader in = null;
        try {

            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", contenType);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);

            if (method.equals(POST)) {
                DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                dop.write(postStr.getBytes("UTF-8"));
                dop.flush();
                dop.close();
            }
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
                throw new RuntimeException("code " + code);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("不合法的http地址", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取数据失败", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭流失败", e);
                }
            }
        }
        return result;

    }

    /**
     * post请求
     * @param urlStr
     * @param params
     * @return
     */
    public static String executePost(String urlStr, String postStr) {
        return executePost(urlStr, postStr, "application/x-www-form-urlencoded");
    }

    /**
     * get请求
     * @param urlStr
     * @param params
     * @return
     */
    public static String executeGet(String urlStr, Map<String, Object> params) {
        return executeHttpMethod(GET, urlStr, params);
    }

    /**
     * get请求
     * @param urlStr
     * @param params
     * @return
     */
    public static String executeGet(String urlStr, Map<String, Object> params,
                                    Map<String, String> headerOpitions) {
        return executeHttpMethod(GET, urlStr, params, headerOpitions);
    }

    public static byte[] getByteArrayFromUrl(String urlStr) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        byte[] content = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);
            // 连接
            connection.connect();
            int code = connection.getResponseCode();

            if (code > 400) {
                throw new RuntimeException("code " + code);
            }
            // 如果返回状态码200
            if (200 == code) {
                // 读取返回数据
                in = connection.getInputStream();
                content = IOUtils.toByteArray(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("不合法的http地址", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取数据失败", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭流失败", e);
                }
            }
        }
        return content;
    }

    public static String executeHttpMethod(String method, String urlStr, Map<String, Object> params) {
        return executeHttpMethod(method, urlStr, params, null);
    }

    public static String executeHttpMethod(String method, String urlStr,
                                           Map<String, Object> params,
                                           Map<String, String> headerOptions, String proxyHost,
                                           int proxyPort) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            if (method.equals(GET)) {
                urlStr = String.format("%s?%s", urlStr, buildParamsString(params));
            }
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Charset", "UTF-8");

            if (headerOptions != null && headerOptions.size() > 0) {
                for (String key : headerOptions.keySet()) {
                    connection.setRequestProperty(key, headerOptions.get(key));
                }
            }

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            if (method.equals(POST)) {
                DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                // 构建传递参数
                String paramStr = buildParamsString(params);
                dop.write(paramStr.getBytes());
                dop.flush();
                dop.close();
            }
            // 连接
            connection.connect();
            int code = connection.getResponseCode();

            if (code > 400) {
                throw new RuntimeException("code " + code);
            }
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
        } catch (MalformedURLException e) {
            throw new RuntimeException("不合法的http地址", e);
        } catch (IOException e) {
            throw new RuntimeException("读取数据失败", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭流失败", e);
                }
            }
        }
        return result;
    }

    public static String executeHttpMethod(String method, String urlStr,
                                           Map<String, Object> params,
                                           Map<String, String> headerOptions) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            if (method.equals(GET)) {
                urlStr = String.format("%s?%s", urlStr, buildParamsString(params));
            }
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Charset", "UTF-8");

            if (headerOptions != null && headerOptions.size() > 0) {
                for (String key : headerOptions.keySet()) {
                    connection.setRequestProperty(key, headerOptions.get(key));
                }
            }

            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);
            if (method.equals(POST)) {
                DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                // 构建传递参数
                String paramStr = buildParamsString(params);
                dop.write(paramStr.getBytes());
                dop.flush();
                dop.close();
            }
            // 连接
            connection.connect();
            int code = connection.getResponseCode();

            if (code > 400) {
                throw new RuntimeException("code " + code);
            }
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
        } catch (MalformedURLException e) {
            throw new RuntimeException("不合法的http地址", e);
        } catch (IOException e) {
            throw new RuntimeException("读取数据失败", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭流失败", e);
                }
            }
        }
        return result;
    }

    /**
     * 是否是存在的文件
     * @param ulrStr
     * @return
     */
    public static boolean isNetFileAvailable(String ulrStr) {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(ulrStr).openConnection();
            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 构建参数字符串
     * 
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String buildParamsString(Map<String, Object> params)
                                                                       throws UnsupportedEncodingException {
        StringBuffer paramsBuffer = new StringBuffer();
        int flag = 0;
        if (params != null) {
            for (String key : params.keySet()) {
                if (flag != 0) {
                    paramsBuffer.append("&");
                }
                String value = URLEncoder.encode(String.valueOf(params.get(key)), "utf-8");
                paramsBuffer.append(key).append("=").append(value);
                flag = 1;
            }
        }
        return paramsBuffer.toString();
    }

}
