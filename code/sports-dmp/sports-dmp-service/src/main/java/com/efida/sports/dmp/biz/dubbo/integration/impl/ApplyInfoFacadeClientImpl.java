/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.dubbo.integration.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.exception.BusinessException;
import com.efida.sport.facade.model.ApplyInfoModel;
import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.service.ApplyInfoFacadeService;
import com.efida.sports.dmp.biz.dubbo.integration.ApplyInfoFacadeClient;

/**
 * 
 * @author zhiyang
 * @version $Id: ApplyInfoFacadeClientImpl.java, v 0.1 2018年7月20日 下午11:21:56 zhiyang Exp $
 */
@Service("applyInfoFacadeClient")
public class ApplyInfoFacadeClientImpl implements ApplyInfoFacadeClient {

    /**
     * 
     */
    private static Logger          logger = LoggerFactory
        .getLogger(ApplyInfoFacadeClientImpl.class);

    @Reference
    private ApplyInfoFacadeService applyInfoFacadeService;

    /**
     * 
     * @see com.efida.sports.dmp.biz.dubbo.integration.ApplyInfoFacadeClient#syncApplyInfo(com.efida.sport.facade.model.ApplyInfoModel)
     */
    @Override
    public Boolean syncApplyInfo(ApplyInfoModel enrollInfo) {
        DefaultResult<Boolean> rs = applyInfoFacadeService.syncApplyInfo(enrollInfo);
        if (!rs.isSuccess()) {
            logger.error("syncApplyInfo applyInfoModel failed,data:{}:",
                JSON.toJSONString(enrollInfo));
            throw new BusinessException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

}
