/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.AccessAnalysisModel;
import com.efida.sport.facade.model.ChannelTrendAnalysisModel;
import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sport.facade.model.RegisterStatisticsModel;
import com.efida.sport.facade.model.RegisterTrendAnalysisModel;
import com.efida.sport.facade.model.TerminaStatisticsModel;
import com.efida.sport.facade.service.RegisterAnalysisFacadeService;
import com.efida.sports.dmp.service.dubbo.intergration.RegisterAnalysisFacadeClient;

/**
 * 
 * @author zoutao
 * @version $Id: RegisterAnalysisFacadeClientImpl.java, v 0.1 2018年8月30日 下午4:08:20 zoutao Exp $
 */
@Service
public class RegisterAnalysisFacadeClientImpl implements RegisterAnalysisFacadeClient {

    @Reference
    private RegisterAnalysisFacadeService registerAnalysisFacade;

    private static Logger                 log = LoggerFactory
        .getLogger(RegisterAnalysisFacadeClientImpl.class);

    @Override
    public RegisterStatisticsModel getRegisterStatistics() {
        DefaultResult<RegisterStatisticsModel> dr = registerAnalysisFacade.getRegisterStatistics();

        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();

    }

    @Override
    public List<RegisterTrendAnalysisModel> getRegisterGrowthTrend(Date startTime, Date endTime) {

        DefaultResult<List<RegisterTrendAnalysisModel>> dr = registerAnalysisFacade
            .getRegisterGrowthTrend(startTime, endTime);

        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();

    }

    @Override
    public PagingResult<RegisterTrendAnalysisModel> getRegisterGrowthTrend(int currentPage,
                                                                           int pageSize,
                                                                           Date startTime,
                                                                           Date endTime) {

        DefaultResult<PagingResult<RegisterTrendAnalysisModel>> dr = registerAnalysisFacade
            .getRegisterGrowthTrend(currentPage, pageSize, startTime, endTime);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();

    }

    @Override
    public List<String> getTopNumChannel(Date startTime, Date endTime, int topNum) {
        DefaultResult<List<String>> dr = registerAnalysisFacade.getTopNumChannel(startTime, endTime,
            topNum);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();

    }

    @Override
    public List<RegisterTrendAnalysisModel> channelRegitserAnalysis(Date startTime, Date endTime,
                                                                    List<String> channelCodes) {

        if (channelCodes == null || channelCodes.size() < 1) {
            List<RegisterTrendAnalysisModel> Data = new ArrayList<RegisterTrendAnalysisModel>();
            return Data;
        }

        DefaultResult<List<RegisterTrendAnalysisModel>> dr = registerAnalysisFacade
            .channelRegitserAnalysis(startTime, endTime, channelCodes);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();

    }

    @Override
    public List<ChannelTrendAnalysisModel> channelTrendAnalysis(Date startTime, Date endTime,
                                                                List<String> channelCodes) {
        if (channelCodes == null || channelCodes.size() < 1) {
            List<ChannelTrendAnalysisModel> Data = new ArrayList<ChannelTrendAnalysisModel>();
            return Data;
        }
        DefaultResult<List<ChannelTrendAnalysisModel>> dr = registerAnalysisFacade
            .channelTrendAnalysis(startTime, endTime, channelCodes);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public List<TerminaStatisticsModel> getTerMinaStatisticsAnalysis(Date statiscsTime) {
        DefaultResult<List<TerminaStatisticsModel>> dr = registerAnalysisFacade
            .getTerMinaStatisticsAnalysis(statiscsTime);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public List<AccessAnalysisModel> getPCAccessAnalysis(Date startTime, Date endTime) {
        DefaultResult<List<AccessAnalysisModel>> dr = registerAnalysisFacade
            .getPCAccessAnalysis(startTime, endTime);
        if (!dr.isSuccess()) {
            throw new BusinessException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

}
