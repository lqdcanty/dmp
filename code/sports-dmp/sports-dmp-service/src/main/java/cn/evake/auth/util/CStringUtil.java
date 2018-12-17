/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Evance
 * @version $Id: UStringUtils.java, v 0.1 2018年4月23日 上午1:01:36 Evance Exp $
 */
public class CStringUtil {

    public static String emptyToNull(String originStr) {
        if (StringUtils.isBlank(originStr)) {
            return null;
        } else {
            return originStr;
        }
    }
}
