package cn.evake.auth.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author lijiaqi
 * @date 2014年10月22日
 * @version 1.0.0
 * @description desc
 */
public class HttpUtils {

    public static final String POST                 = "POST";

    public static final String GET                  = "GET";

    public static final int    CONNECTTIMEOUT       = 1 * 60 * 1000;

    public static final int    READTIMEOUT          = 1 * 60 * 1000;

    public static final String charset              = "utf-8";

    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String JSON_CONTENT_TYPE    = "application/json";

    public static final String XML_CONTENT_TYPE     = "application/xml";

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

    public static String executeGBKGet(String urlStr, Map<String, Object> params) {
        return executeHttpMethodGBK(GET, urlStr, params);
    }

    private static String executeHttpMethodGBK(String method, String urlStr,
                                               Map<String, Object> params) {

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
            connection.setConnectTimeout(CONNECTTIMEOUT);
            connection.setReadTimeout(READTIMEOUT);
            if (method.equals(POST)) {
                DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                // 构建传递参数
                String paramStr = buildParamsString(params);
                dop.write(paramStr.getBytes("GBK"));
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

    public static String executeHttpMethod(String method, String urlStr,
                                           Map<String, Object> params) {
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
     * 构建参数字符串
     * 
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String buildParamsString(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuffer paramsBuffer = new StringBuffer();
        int flag = 0;
        if (params != null) {
            for (String key : params.keySet()) {
                if (flag != 0) {
                    paramsBuffer.append("&");
                }
                String value = URLEncoder.encode(String.valueOf(params.get(key)), "GBK");
                paramsBuffer.append(key).append("=").append(value);
                flag = 1;
            }
        }
        return paramsBuffer.toString();
    }

    public static void downLoadFileFromUrl(String urlStr, String tempPath) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        OutputStream os = null;
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
                os = new FileOutputStream(tempPath);
                byte[] fileData = new byte[1024];// 从输入流中获取数据

                int size = in.read(fileData);
                //当读取到流末尾时，返回-1
                while (size != -1) {
                    //将读取内容输出,注意这里第二个和第三个参数，指定从字节数组里读取指定位置的字节,该参数关系到最后读取内容的正确性
                    os.write(fileData, 0, size);
                    size = in.read(fileData);
                }
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
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭流失败", e);
                }
            }
        }
    }

    /**
     * 地址请求转发
     * @param url
     * @param request
     * @param response
     */
    public static void DispatcherUrl(String url, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            throw new RuntimeException("不合法的url地址", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO流处理失败", e);
        }
    }
}
