/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.open.controller;

/**
 * 
 * @author zhiyang
 * @version $Id: StringUtils.java, v 0.1 2018年6月25日 下午5:02:30 zhiyang Exp $
 */
public class StringUtils {

    /**
     * 清空null为""
     * 
     * @param machCode
     * @return
     */
    public static String emptyStr(String machCode) {

        if (machCode == null) {
            return "";
        }

        return machCode;
    }
}
