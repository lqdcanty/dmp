/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.dubbo.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.efida.sport.dmp.facade.OpenEnrollFacade;
import com.efida.sport.dmp.facade.model.EnrollInfoDto;
import com.efida.sport.dmp.facade.model.EnrollxInfoDto;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollResultModel;
import com.efida.sport.open.model.OpenEnrollxResultModel;
import com.efida.sports.dmp.biz.open.OpenEnrollService;
import com.efida.sports.dmp.biz.open.OpenEnrollxService;

/**
 * 
 * @author zhiyang
 * @version $Id: EnrollFacadeImpl.java, v 0.1 2018年7月5日 下午4:20:35 zhiyang Exp $
 */
@Service
public class OpenEnrollFacadeImpl implements OpenEnrollFacade {

    private Logger             logger = LoggerFactory.getLogger(OpenEnrollFacadeImpl.class);
    @Autowired
    private OpenEnrollService  openEnrollService;
    @Autowired
    private OpenEnrollxService openEnrollxService;

    /**
     * 
     * @see com.efida.sport.dmp.facade.OpenEnrollFacade#submitEnrollInfo(java.lang.String, com.efida.sport.dmp.facade.model.EnrollInfoDto)
     */
    @Override
    public DefaultResult<Boolean> submitEnrollInfo(String unitCode, EnrollInfoDto enrollInfo) {

        DefaultResult<Boolean> result = new DefaultResult<Boolean>();
        try {
            //OpenEnrollModel em = conver2Model(enrollInfo);
            enrollInfo.setUnitCode(unitCode);
            enrollInfo.setSource((byte) 0);
            OpenEnrollResultModel orm = openEnrollService.addOpenEnrollInfo(enrollInfo);
            result.setResultObj(orm.getSuccess() == 1 ? true : false);
        } catch (OpenException e) {
            result.setErrorCode(e.getMessage());
            logger.error("", e);
        } catch (Exception ex) {
            result.setErrorCode(ex.getMessage());
            logger.error("", ex);
        }
        return result;
    }

    @Override
    public DefaultResult<Boolean> submitEnrollInfo(String unitCode, EnrollxInfoDto enrollInfo) {
        DefaultResult<Boolean> result = new DefaultResult<Boolean>();
        try {
            enrollInfo.setUnitCode(unitCode);
            enrollInfo.setSource((byte) 0);
            OpenEnrollxResultModel orm = openEnrollxService.addOpenEnrollInfo(enrollInfo);
            result.setResultObj(orm.getSuccess() == 1 ? true : false);
        } catch (OpenException e) {
            result.setErrorCode(e.getMessage());
            logger.error("", e);
        } catch (Exception ex) {
            result.setErrorCode(ex.getMessage());
            logger.error("", ex);
        }
        return result;
    }

    private OpenEnrollModel conver2Model(EnrollInfoDto enrollInfo) {

        OpenEnrollModel oem = new OpenEnrollModel();
        BeanUtils.copyProperties(enrollInfo, oem);

        return oem;
    }

}
