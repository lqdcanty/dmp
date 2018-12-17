package com.efida.sport.open.util;

/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.ICommonModel;
import com.efida.sport.open.result.ErrorCode;

/**
 * 比较工具类
 * @author zhiyang
 * @version $Id: CompareUtils.java, v 0.1 2016年5月24日 下午12:57:21 zhiyang Exp $
 */
public class CompareUtils {

    /**
     * 断言st1 与str2取值相同
     * 
     * @param str1
     * @param str2
     * @throws OpenException 
     */
    public static void assertStringEqual(String filedName, String str1,
                                         String str2) throws OpenException {

        if (StringUtils.equals(str1, str2)) {
            return;
        }

        throw new OpenException(ErrorCode.UNKNOW_ERROR,
            "字段:" + filedName + "不相同. old :" + str1 + ",new:" + str2);
    }

    /**
     * 断言日期是否相同
     * 
     * @param string
     * @param completeDate
     * @param completeDate2
     * @throws OpenException 
     */
    public static void assertDateEqual(String filedName, Date date1,
                                       Date date2) throws OpenException {

        if (date1 == null || date2 == null) {

            if (date1 != null || date2 != null) {
                throw new OpenException(ErrorCode.UNKNOW_ERROR, "字段:" + filedName + "不相同.");
            }
        }

        if (date1.getTime() / 1000 != date2.getTime() / 1000) {

            throw new OpenException(ErrorCode.UNKNOW_ERROR,
                "字段:" + filedName + "不相同. old :" + date1 + ",new:" + date2);
        }

        return;
    }

    /**
     * 断言list不能为空
     * @param <ICommonModel>
     * 
     * @param tempObligeelist
     * @param string
     * @throws OpenException 
     */
    public static void asserNotNull(List<? extends ICommonModel> items,
                                    String desc) throws OpenException {

        if (items == null || items.size() < 1) {
            throw new OpenException(ErrorCode.UNKNOW_ERROR, desc);
        }
    }

    /**
     * 断言是否相同
     * 
     * @param fieldName
     * @param isTrue
     * @param tipInfo
     * @throws OpenException 
     */
    public static void assertTrue(boolean isTrue, String tipInfo) throws OpenException {

        if (!isTrue) {
            throw new OpenException(ErrorCode.UNKNOW_ERROR, tipInfo);
        }
    }

}
