/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import com.efida.sport.open.OpenException;

/**
 * 开放接口服务配置
 * @author zhiyang
 * @version $Id: OpenConfigService.java, v 0.1 2018年5月28日 下午5:00:58 zhiyang Exp $
 */
public interface OpenConfigService {

    /**
     * 通过承办方code获取的承办方的私钥。 
     * 
     * @param unitCode
     * @return
     * @throws OpenException 
     */
    String getPrivateKey(String unitCode) throws OpenException;

    /**
     * 检查调用频率
     * 
     * @param unitCode
     */
    void checkUnitCodeFreq(String unitCode);
}
