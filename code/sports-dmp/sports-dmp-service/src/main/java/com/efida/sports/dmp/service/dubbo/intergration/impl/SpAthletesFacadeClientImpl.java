/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.SpAthletesFacade;
import com.efida.sport.admin.facade.exception.BusinessException;
import com.efida.sport.admin.facade.model.SpAthletesEnrollModel;
import com.efida.sport.admin.facade.result.SportAdminResult;
import com.efida.sports.dmp.service.dubbo.intergration.SpAthletesFacadeClient;

/**
 * 
 * @author zoutao
 * @version $Id: SpAthletesFacadeClient.java, v 0.1 2018年5月28日 下午3:22:08 zoutao Exp $
 */
@Service
public class SpAthletesFacadeClientImpl implements SpAthletesFacadeClient {
    @Reference
    private SpAthletesFacade spAthletesFacade;
    private static Logger    log = LoggerFactory.getLogger(SpAthletesFacadeClientImpl.class);

    @Override
    public SpAthletesEnrollModel getAthietes(String matchCode) {
        SportAdminResult<SpAthletesEnrollModel> rs = spAthletesFacade.getAthietes(matchCode);
        if (log.isDebugEnabled()) {
            log.info("获取运动员属性：matchCode：" + matchCode + " ,返回结果：" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new BusinessException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

}
