/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration;

import java.util.Date;
import java.util.List;

import com.efida.sport.facade.model.AccessAnalysisModel;
import com.efida.sport.facade.model.ChannelTrendAnalysisModel;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sport.facade.model.RegisterStatisticsModel;
import com.efida.sport.facade.model.RegisterTrendAnalysisModel;
import com.efida.sport.facade.model.TerminaStatisticsModel;

/**
 * 
 * @author zoutao
 * @version $Id: RegisterAnalysisFacadeClient.java, v 0.1 2018年8月30日 下午4:06:13 zoutao Exp $
 */
public interface RegisterAnalysisFacadeClient {

    /**
     * 获取用户统计数据
     * @return
     */
    public RegisterStatisticsModel getRegisterStatistics();

    /**
     * 获取用户增长数据
     * 
     * @return
     */
    List<RegisterTrendAnalysisModel> getRegisterGrowthTrend(Date startTime, Date endTime);

    /**
    * 分页获取用户增长数据
    * 
    * @param currentPage
    * @param PageSize
    * @param startTime
    * @param endTime
    * @return
    */
    PagingResult<RegisterTrendAnalysisModel> getRegisterGrowthTrend(int currentPage, int pageSize,
                                                                    Date startTime, Date endTime);

    /**
     * 按渠道分组 ，统计总用户数，按总用户数倒序查询渠道编号
    
     * 
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    List<String> getTopNumChannel(Date startTime, Date endTime, int topNum);

    /**
     * 
     * 渠道用户分析 一段时间段类 各个渠道的新增用户数，总用户数，新增占比数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    List<RegisterTrendAnalysisModel> channelRegitserAnalysis(Date startTime, Date endTime,
                                                             List<String> channelCodes);

    /**
     * 
     *  渠道用户趋势分析 一段时间段了各个渠道 用户新增数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    List<ChannelTrendAnalysisModel> channelTrendAnalysis(Date startTime, Date endTime,
                                                         List<String> channelCodes);

    /**
     * 获取终端统计数据
     * 
     * @param statiscsTime
     * @return
     */
    List<TerminaStatisticsModel> getTerMinaStatisticsAnalysis(Date statiscsTime);

    /**
     * 获取官网访问数据
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<AccessAnalysisModel> getPCAccessAnalysis(Date startTime, Date endTime);

}
