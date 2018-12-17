/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.dubbo.open.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.evake.auth.dubbo.open.constants.OpenUserConstants;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: UserInfoUtils.java, v 0.1 2018年2月26日 下午6:47:54 wang yi Exp $
 */
public class OpenUserInfoUtil {

    /**
     * 获取当前用户登录的token
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    public static String getUserAuthToken(HttpServletRequest request) {
        //校验头参数
        String authToken = request.getParameter(OpenUserConstants.USER_TOKEN_KEY);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(OpenUserConstants.USER_TOKEN_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return authToken;
    }

    /**
     * 获取当前用户登录的token
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    public static String getUserUid(HttpServletRequest request) {
        //校验头参数
        String uid = request.getParameter(OpenUserConstants.USER_UUID);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(OpenUserConstants.USER_UUID)) {
                    return cookie.getValue();
                }
            }
        }
        return uid;
    }

}
