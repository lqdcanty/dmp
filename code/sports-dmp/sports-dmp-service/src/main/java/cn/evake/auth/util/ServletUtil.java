package cn.evake.auth.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * ServletUtil 工具类
 * @author wangyi
 * @desc 
 * @version $Id: ServletUtil.java, v 0.1 2017年10月29日 下午5:23:48 wangyi Exp $
 */
public class ServletUtil {

    // 定义正则表达式，域名的根需要自定义，这里不全  
    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";

    public static String getWebRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    public static String getWebRealPath() {
        File file = new File(ServletUtil.class.getResource("/").getPath());
        return file.getParentFile().getParentFile().getAbsolutePath();
    }

    /**
     * 获取web的项目部署root路径
     * 
     * @param request
     * @return
     */
    public static String getWebRootPath(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取Session的值
     * 
     * @param session
     * @param key
     * @return
     */
    public static Object getSessionValue(HttpSession session, String key) {
        if (session == null) {
            return null;
        }
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Object val = session.getAttribute(key);
        return val;

    }

    /**
     * 保存sesion的值
     * 
     * @param session
     * @param key
     * @param value
     * @return
     */
    public static boolean saveSessionValue(HttpSession session, String key, Object value) {
        if (session == null) {
            return false;
        }
        if (StringUtils.isBlank(key)) {
            return false;
        }
        session.setAttribute(key, value);
        return true;
    }

    /**
     * 清空seesion的值
     * 
     * @param session
     * @param key
     */
    public static void removeSessionValue(HttpSession session, String key) {
        if (!StringUtils.isBlank(key)) {
            session.removeAttribute(key);
        }
    }

    /**
     * 获取Ctx
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    public static String getCtx(HttpServletRequest request) {
        StringBuffer ctx = new StringBuffer();
        ctx.append(request.getScheme());
        ctx.append("://" + request.getServerName());
        ctx.append(":" + request.getServerPort());
        ctx.append(request.getContextPath());
        return ctx.toString();
    }

    /** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     * 用户真实IP为： 192.168.1.110 
     * @param request 
     * @return 
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取根域名
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String result = "";
        String httpUrl = getCtx(request);
        if (StringUtils.isBlank(httpUrl)) {
            return result;
        }
        try {
            Pattern pa = Pattern.compile(RE_TOP, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pa.matcher(httpUrl);
            matcher.find();
            result = matcher.group();
        } catch (Exception e) {
            return result;
        }
        System.err.println("解析后根域名-->" + result);
        return result;
    }

    /**
     * 获取Http的referer 
     * 
     * @param request
     * @return
     */
    public static String getHttpReferer(HttpServletRequest request) {
        String refer = request.getHeader("Referer");
        return refer;
    }

    /**
     * 
     * @Title 获取Http的referer的Host
     * @author lijiaqi
     * @description
     * @param request
     * @return
     */
    public static String getHttpRefererHost(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (StringUtils.isBlank(referer)) {
            return "";
        }
        String referReqUrl = referer.substring(referer.indexOf("//") + "//".length());
        int index = referReqUrl.indexOf("/");
        if (index == -1) {
            return referReqUrl;
        }
        referReqUrl = referReqUrl.substring(0, referReqUrl.indexOf("/"));
        return referReqUrl;
    }

    /**
     * 获取http的referer 去掉参数
     * @param request
     * @return
     */
    public static String getHttpRefererWithoutQueryString(HttpServletRequest request) {
        String refer = request.getHeader("Referer");
        if (StringUtils.isNotBlank(refer)) {
            if (refer.indexOf("?") != -1) {
                refer = refer.substring(0, refer.indexOf("?"));
            }
        }
        return refer;
    }

    public static String getServerName(HttpServletRequest request) {
        return request.getServerName();
    }

    public static String getHttpHost(HttpServletRequest request) {
        String host = request.getHeader("Host");
        return host;
    }

    /**
     * 判断IP是否是本机地址或者外部地址
     * 
     * @param request
     * @return
     */
    public static boolean isInternalIp(HttpServletRequest request) {
        String ip = getIpAddress(request);
        String[] parts = ip.split("\\.");
        // 如果是a类内部地址
        if (ip.startsWith("10.")) {
            return true;
        }
        Integer ipPart2 = Integer.parseInt(parts[1]);
        // 如果是b类内部地址
        if (ip.startsWith("172") && ipPart2 >= 16 && ipPart2 <= 31) {
            return true;
        }
        // 如果是C类地址
        if (ip.startsWith("192")) {
            return true;
        }
        // 如果是本机地址
        if (ip.startsWith("127")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是外部链接
     * 
     * @param fromUrl
     * @return
     */
    public static boolean isExtralLinks(String fromUrl) {
        if (StringUtils.isBlank(fromUrl)) {
            return false;
        }
        String protocal = fromUrl.substring(0, fromUrl.indexOf("://") + "://".length());
        String urlWithOutProtocal = fromUrl.substring(protocal.length());
        if (urlWithOutProtocal.startsWith("www.taobiaoren.com")
            || urlWithOutProtocal.startsWith("taobiaoren.com")) {
            return false;
        }
        return true;
    }

    /**
     * 获取回调的地址
     * 
     * @param request
     * @param callbackurl
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getLoginCallbackurl(HttpServletRequest request, String callbackurl)
                                                                                            throws UnsupportedEncodingException {
        if (!StringUtils.isBlank(callbackurl)) {
            // 获取回调
            callbackurl = URLDecoder.decode(callbackurl, "utf-8");
            String ctx = ServletUtil.getCtx(request);
            if (callbackurl.indexOf(ctx) == -1) {
                return "";
            } else {
                callbackurl = callbackurl.substring(callbackurl.indexOf(ctx) + ctx.length());
            }
        }
        return callbackurl;
    }

    /**
     * 获取user-agent
     * 
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader("User-Agent");
    }

    /**
     * 获取请求的完整url（包含参数）
     * @param request
     * @return
     */
    public static String getRequestURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url;
    }

    /**
     * @title 去掉context
     * @methodName
     * @author lijiaqi
     * @description
     * @param request
     * @return
     */
    public static String getRequestURIWithOutContextPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestURI = uri.substring(contextPath.length());
        if (requestURI.contains("//")) {
            requestURI = requestURI.replace("//", "/");
        }
        return requestURI;

    }

    /**
     * 获取请求的完整url（包含参数）
     * @param request
     * @return
     */
    public static String getFullRequestURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        if (!StringUtils.isBlank(request.getQueryString())) {
            url = url + "?" + request.getQueryString();
        }
        return url;
    }

    /**
     * 
     * 获取所有的请求参数
     * @Title 
     * @author lijiaqi
     * @description
     * @param request
     * @return
     */
    public static Map<String, String> getReqParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            params.put(paraName, request.getParameter(paraName));
        }
        return params;
    }

    public static String getRequestURLAndParam(HttpServletRequest request) {
        StringBuffer url = new StringBuffer(request.getRequestURL().toString());
        Enumeration<String> enu = request.getParameterNames();
        boolean first = true;
        while (enu.hasMoreElements()) {
            if (first == true) {
                url.append("?");
            }
            if (first == false) {
                url.append("&");
            }
            String paraName = (String) enu.nextElement();
            url.append(paraName).append("=").append(request.getParameter(paraName));
            first = false;
        }
        return url.toString();
    }

    /**
     * 判断是否本地请求调试的
     * @param request
     * @return
     */
    public static boolean isLocalRequest(HttpServletRequest request) {
        String ctx = getCtx(request);
        if (ctx.startsWith("https://localhost") || ctx.startsWith("http://localhost")) {
            return true;
        }
        if (ctx.startsWith("https://192.168") || ctx.startsWith("http://192.168")) {
            return true;
        }
        if (ctx.startsWith("https://127") || ctx.startsWith("http://127")) {
            return true;
        }

        return false;
    }

    /**
     * 去除queryString
     * @param url
     * @return
     */
    public static String removeQeueryString(String url) {
        //去掉queryString
        if (url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        return url;
    }

}
