/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util.user;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.evake.auth.constants.UserConstants;
import cn.evake.auth.util.SecretUtil;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: UserInfoUtils.java, v 0.1 2018年2月26日 下午6:47:54 wang yi Exp $
 */
public class UserInfoUtil {

    final static String UID_PREFIX = "uid_";

    /**
     * 获取当前用户登录的token
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    public static String getAuthTokenKey(HttpServletRequest request) {
        //校验头参数
        String authToken = request.getParameter(UserConstants.USER_TOKEN_KEY);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(UserConstants.USER_TOKEN_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return authToken;
    }

    /**
     * 
     * 生成token
     * @param uuid
     * @return
     */
    public static String generateAutoken(String uuid) {
        String orToken = uuid + new Date().getTime();
        return SecretUtil.encode(orToken);
    }

    /**
     * 获取用户缓存标识
     * @param uuid 用户唯一标识
     * @return
     */
    public static String generateCacheUserKey(String uuid) {
        return UID_PREFIX + uuid;
    }

    /**
     * 获取用户缓存标识
     * @param request
     * @return
     */
    public static String getCacheUserKey(HttpServletRequest request) {
        String uuid = request.getParameter(UserConstants.USER_UUID);
        if (StringUtils.isBlank(uuid)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(UserConstants.USER_UUID)) {
                        uuid = cookie.getValue();
                    }
                }
            }
        }
        return generateCacheUserKey(uuid);
    }

    /**
     * 获取访问用户名
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        String userName = request.getParameter(UserConstants.USER_NAME);
        if (StringUtils.isBlank(userName)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(UserConstants.USER_NAME)) {
                        userName = cookie.getValue();
                    }
                }
            }
        }
        return userName;
    }

    /**
     * 获取用户缓存标识
     * @param uuid 用户唯一标识
     * @return
     */
    public static String getCacheUserUUid(String cacheKay) {
        return cacheKay.replace(UID_PREFIX, "");
    }

}
