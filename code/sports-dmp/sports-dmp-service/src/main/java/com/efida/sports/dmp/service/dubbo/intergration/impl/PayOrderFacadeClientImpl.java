/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import java.util.List;
import java.util.Map;

import com.efida.sport.facade.service.FinancialReportFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.facade.model.SmtPayOrderDetailModel;
import com.efida.sport.facade.model.SmtPayOrderModel;
import com.efida.sport.facade.service.PayOrderFacadeService;
import com.efida.sports.dmp.service.dubbo.cover.PagingResultCover;
import com.efida.sports.dmp.service.dubbo.intergration.PayOrderFacadeClient;

import cn.evake.auth.usermodel.PagingResult;

/**
 * @author antony
 * @version $Id: PayOrderFacadeClient.java, v 0.1 2018年7月21日 下午6:16:50 antony Exp $
 */
@Service("payOrderFacadeClient")
public class PayOrderFacadeClientImpl implements PayOrderFacadeClient {

    @Reference
    private FinancialReportFacadeService reportFacadeService;

    private static Logger         log = LoggerFactory.getLogger(PayOrderFacadeClientImpl.class);

    @Override
    public PagingResult<SmtPayOrderModel> getSmtPayOrderList(Map<String, Object> params) {
        PagingResult<SmtPayOrderModel> pagingResult = PagingResultCover.convertVO(reportFacadeService.getFinancialReportListByPage(params));
        return pagingResult;
    }

    @Override
    public PagingResult<SmtPayOrderDetailModel> getSmtPayOrderDetail(Map<String, Object> params) {
        PagingResult<SmtPayOrderDetailModel> pagingResult = PagingResultCover.convertVO(reportFacadeService.getFinancialReportDetail(params));
        return pagingResult;
    }

    @Override
    public List<SmtPayOrderModel> getAllSmtPayOrderModel(Map<String, Object> params) {
        return reportFacadeService.getFinancialReportList(params);

    }

    @Override
    public SmtPayOrderModel getSettlementPayOrderCount(Map<String, Object> params) {
        return reportFacadeService.getFinancialReportCount(params);

    }

}
