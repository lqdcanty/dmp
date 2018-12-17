/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.RegisterModel;
import com.efida.sport.facade.service.RegisterFacadeService;
import com.efida.sports.dmp.service.dubbo.intergration.RegisterFacadeClient;

/**
 * 
 * @author zhiyang
 * @version $Id: RegisterFacadeClientImpl.java, v 0.1 2018年8月5日 下午11:02:38 zhiyang Exp $
 */
@Service("registerFacadeClient")
public class RegisterFacadeClientImpl implements RegisterFacadeClient {

    private static Logger         logger = LoggerFactory.getLogger(RegisterFacadeClientImpl.class);

    @Reference
    private RegisterFacadeService registerFacadeService;

    /**
     * 
     * @see com.efida.sports.dmp.service.dubbo.intergration.RegisterFacadeClient#getRegisterModelByPhone(java.lang.String)
     */
    @Override
    public RegisterModel getRegisterModelByPhone(String phoneNum) {

        DefaultResult<RegisterModel> rs = this.registerFacadeService
            .getOrInsertRegisterByPhone(phoneNum);
        if (!rs.isSuccess()) {
            logger.error("create register failed. phone:" + phoneNum + "," + rs.getErrorInfo());
            throw new BusinessException("创建账号失败,phone:" + phoneNum);
        }
        return rs.getResultObj();
    }

}
