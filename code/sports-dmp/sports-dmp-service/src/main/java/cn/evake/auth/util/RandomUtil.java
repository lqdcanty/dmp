/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util;

/**
 * 随机数
 * @author Evance
 * @version $Id: RandomUtil.java, v 0.1 2018年1月1日 下午5:00:35 Evance Exp $
 */
public class RandomUtil {

    /**
     * 
     * @title 获取随机数
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public static Integer getIntRandomId() {
        return (int) Math.round(Math.random() * 1000000000);
    }
}
