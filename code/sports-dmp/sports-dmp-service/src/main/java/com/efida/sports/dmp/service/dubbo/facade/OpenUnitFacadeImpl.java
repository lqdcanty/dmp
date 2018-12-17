/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.facade;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sport.admin.facade.exception.BusinessException;
import com.efida.sport.dmp.facade.OpenUnitFacade;
import com.efida.sport.dmp.facade.model.UnitAccountDto;
import com.efida.sport.dmp.facade.model.UserUnitModel;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.dmp.facade.result.DmpPageResult;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.service.dubbo.cover.CommonCover;
import com.efida.sports.dmp.service.unit.UnitService;
import com.efida.sports.dmp.service.user.UserUnitService;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 承办方账号对外接口
 * @author wang yi
 * @desc 
 * @version $Id: OpenUnitFacadeImpl.java, v 0.1 2018年7月12日 下午4:38:12 wang yi Exp $
 */
@SuppressWarnings("all")
@com.alibaba.dubbo.config.annotation.Service
public class OpenUnitFacadeImpl implements OpenUnitFacade {

    private Logger          logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitService     openUnitService;

    @Autowired
    private UserUnitService userUnitService;

    @Override
    public DefaultResult<DmpPageResult<UnitAccountDto>> getUnitAccounts(String keyword, int page,
                                                                        int size) {
        DefaultResult<DmpPageResult<UnitAccountDto>> defaultResult = new DefaultResult<DmpPageResult<UnitAccountDto>>();
        try {
            PagingResult<OpenUnitEntity> unitPages = openUnitService.getUnitAccounts(keyword, page,
                size);
            List<UnitAccountDto> unitDtos = CommonCover.coverToUnitAccountDtos(unitPages.getList());
            defaultResult
                .setResultObj(new DmpPageResult(unitDtos, unitPages.getAllRow(), page, size));
        } catch (BusinessException e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg("查询失败");
        }
        return defaultResult;
    }

    @Override
    public DefaultResult<List<UserUnitModel>> getUserUnit(String userName) {
        DefaultResult<List<UserUnitModel>> defaultResult = new DefaultResult<List<UserUnitModel>>();
        try {
            if (StringUtils.isBlank(userName)) {
                throw new BusinessException("用户名不允许为空");
            }
            List<UserUnit> userUnits = userUnitService.getUserUnitByUserName(userName);
            defaultResult.setResultObj(CommonCover.coverToUserUnitModes(userUnits));
        } catch (BusinessException e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg("查询失败");
        }
        return defaultResult;
    }

}
