/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util.annotion;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

import cn.evake.auth.annotation.Authority;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: AuthAnnotationUtil.java, v 0.1 2018年8月2日 下午3:31:54 wang yi Exp $
 */
public class AuthAnnotationUtil {

    /**
     * 获取注解内容
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param handler
     * @return
     */
    public static Authority getAnthAnnotation(HandlerMethod handler) {
        Authority authority = null;
        if (handler == null) {
        }
        HandlerMethod hm = handler;
        Class<?> clazz = hm.getBeanType();
        Method m = hm.getMethod();
        if (clazz != null && m != null) {
            boolean isClzAnnotation = clazz.isAnnotationPresent(Authority.class);
            boolean isMethondAnnotation = m.isAnnotationPresent(Authority.class);
            if (isMethondAnnotation) {
                authority = m.getAnnotation(Authority.class);
            } else if (isClzAnnotation) {
                authority = clazz.getAnnotation(Authority.class);
            }
        }
        return authority;
    }
}
