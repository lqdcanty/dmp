package com.efida.sports.dmp.biz.score;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 奖章service
 * 
 * @author zengbo
 * @version $Id: ReportGoodsOrderService.java, v 0.1 2018年10月10日 上午11:00:38 zengbo Exp $
 */
public interface ReportGoodsOrderService {

    /**
     * 保存奖章
     * 
     * @param order
     * @return
     */
    boolean saveGoodsOrder(ReportGoodsOrder order);

    /**
     * 保存奖章集合
     * 
     * @param orders
     * @return
     */
    boolean saveGoodsOrderList(List<ReportGoodsOrder> orders);

    /**
     * 电子奖章领取人数
     * 
     * @return
     */
    List<Map<String, Object>> countMedalReceive();

    /**
     * 完赛人数统计
     * 
     * @return
     */
    List<Map<String, Object>> countFinishEnrollPerson();

    /**
     * 根据日期查询奖章统计
     * 
     * @param date
     * @return
     */
    ReportGoodsOrder queryReportGoodsByParams(String date);

    /**
     * 根据排序获取完赛开始日期 asc
     * 
     * @param sortParam
     * @return
     */
    String queryScoreStartTime();

    /**
     * 分页查询奖章统计数据
     * 
     * @param startDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PagingResult<ReportGoodsOrder> countGoodsOrderPersonByPage(String startDate,
                                                                      String endDate,
                                                                      int pageNumber, int pageSize);

    /**
     * 查询导出奖章数据
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public List<ReportGoodsOrder> countGoodsOrderPersonByExport(String startDate, String endDate);

}
