/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.utils.MD5Utils;

/**
 * 
 * @author zhiyang
 * @version $Id: SignUtils.java, v 0.1 2018年5月29日 下午9:11:03 zhiyang Exp $
 */
public class SignUtils {
    private static Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 
     * 
     * @param signData
     * @param sign
     * @throws OpenException
     */
    public static void assertSignTrue(String signData, String sign) throws OpenException {
        if (StringUtils.isEmpty(sign)) {
            throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "签名参数sign不能为空!");
        }

        String md5 = MD5Utils.MD5Encode(signData);
        if (md5.equalsIgnoreCase(sign)) {
            return;
        }
        logger.error("签名验证失败。 正确签名应该为:" + md5 + ",客户端传输签名为：" + sign);

        throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "验证签名失败!");
    }

    /**
     * 检查时间戳参数
     * 
     * @param timestamp
     * @throws OpenException 
     * @throws ParseException 
     */
    public static void checkTimestamp(String timestamp) throws OpenException, ParseException {

        if (StringUtils.isEmpty(timestamp)) {
            throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "时间戳参数timestamp不能为空!");
        }

        if (timestamp.length() != DateUtil.LONG_FORMAT.length()) {
            throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "时间戳timestamp:" + timestamp
                                                                     + "格式错误，格式应为："
                                                                     + DateUtil.LONG_FORMAT);
        }

        Date ts = DateUtil.parse(timestamp, DateUtil.LONG_FORMAT);
        long diff = System.currentTimeMillis() - ts.getTime();

        if (diff > 3600000) {
            throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "时间戳timestamp:" + timestamp
                                                                     + "过期超过1小时,距离当前时间：" + diff
                                                                     / 1000 + "s");
        } else if (diff < -3600000) {
            throw new OpenException(ErrorCode.VALID_SIGNATURE_ERROR, "时间戳timestamp:" + timestamp
                                                                     + "无效, 超过当前时间：" + diff / 1000
                                                                     + "s");
        }
    }
}
