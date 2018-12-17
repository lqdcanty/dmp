/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import cn.evake.auth.usermodel.PagingResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.admin.facade.SpAppVersionFacade;
import com.efida.sport.admin.facade.model.SpAppVersionModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sport.admin.facade.result.SportAdminResult;
import com.efida.sport.facade.model.SmtPayOrderDetailModel;
import com.efida.sport.facade.model.SmtPayOrderModel;
import com.efida.sport.facade.service.PayOrderFacadeService;
import com.efida.sports.dmp.service.dubbo.cover.PagingResultCover;
import com.efida.sports.dmp.service.dubbo.intergration.AppVersionFacadeClient;
import com.efida.sports.dmp.service.dubbo.intergration.PayOrderFacadeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author antony
 * @version $Id: PayOrderFacadeClient.java, v 0.1 2018年7月21日 下午6:16:50 antony Exp $
 */
@Service("appVersionFacadeClient")
public class AppVersionFacadeClientImpl implements AppVersionFacadeClient {

    @Reference
    private SpAppVersionFacade spAppVersionFacade;

    private static Logger         log = LoggerFactory.getLogger(AppVersionFacadeClientImpl.class);

    @Override
    public PagingResult<SpAppVersionModel> getAppVersionPageList(Map<String, Object> params) {
        SportAdminResult<SportsAdminPageResult<SpAppVersionModel>> sp = spAppVersionFacade.getAppVersionPageList(params);
        PagingResult<SpAppVersionModel> pagingResult = PagingResultCover.convertVO2(sp.getResultObj());
        return pagingResult;
    }

    @Override
    public boolean saveAppVersion(SpAppVersionModel spAppVersionModel) {
        return spAppVersionFacade.saveAppVersion(spAppVersionModel);
    }

    @Override
    public SpAppVersionModel queryAppVersionById(Long id) {
        return spAppVersionFacade.queryAppVersionById(id);
    }

    @Override
    public boolean delAppVersion(Long id) {
        return spAppVersionFacade.delAppVersion(id);
    }


}
