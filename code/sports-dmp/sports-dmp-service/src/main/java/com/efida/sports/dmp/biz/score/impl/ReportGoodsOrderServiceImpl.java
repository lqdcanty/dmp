package com.efida.sports.dmp.biz.score.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.biz.score.ReportGoodsOrderService;
import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.dao.mapper.ReportGoodsOrderMapper;
import com.efida.sports.dmp.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 奖章service实现
 * 
 * @author zengbo
 * @version $Id: ReportGoodsOrderServiceImpl.java, v 0.1 2018年10月10日 上午11:03:40 zengbo Exp $
 */
@Service("reportGoodsOrderService")
public class ReportGoodsOrderServiceImpl implements ReportGoodsOrderService {

    private static Logger          logger = LoggerFactory
        .getLogger(ReportGoodsOrderServiceImpl.class);

    @Autowired
    private ReportGoodsOrderMapper reportGoodsOrderMapper;

    @Override
    public boolean saveGoodsOrder(ReportGoodsOrder order) {
        try {
            order.setGmtCreate(new Date());
            order.setGmtModified(new Date());
            String date = DateUtil.format(order.getDate(), DateUtil.WEB_FORMAT);
            ReportGoodsOrder report = queryReportGoodsByParams(date);
            if (report != null) {
                if (order.getFinishCount() != report.getFinishCount()
                    || order.getMedalReceive() != report.getMedalReceive()
                    || order.getEntityReceive() != report.getEntityReceive()
                    || order.getOnlineOrder() != report.getOnlineOrder()) {
                    order.setId(report.getId());
                    order.setGmtCreate(report.getGmtCreate());
                    int result = reportGoodsOrderMapper.updateByPrimaryKeySelective(order);
                    if (result == 1) {
                        return true;
                    }
                }
                return false;
            }
            int result = reportGoodsOrderMapper.insertSelective(order);
            if (result == 1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    @Override
    public boolean saveGoodsOrderList(List<ReportGoodsOrder> orders) {
        if (orders != null && orders.size() > 0) {
            for (ReportGoodsOrder order : orders) {
                saveGoodsOrder(order);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> countMedalReceive() {
        return reportGoodsOrderMapper.countMedalReceive();
    }

    @Override
    public List<Map<String, Object>> countFinishEnrollPerson() {
        return reportGoodsOrderMapper.countFinishEnrollPerson();
    }

    @Override
    public ReportGoodsOrder queryReportGoodsByParams(String date) {
        return reportGoodsOrderMapper.queryReportGoodsByParams(date);
    }

    @Override
    public String queryScoreStartTime() {
        return reportGoodsOrderMapper.queryScoreStartTime();
    }

    @Override
    public PagingResult<ReportGoodsOrder> countGoodsOrderPersonByPage(String startDate,
                                                                      String endDate,
                                                                      int pageNumber,
                                                                      int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<ReportGoodsOrder> orders = reportGoodsOrderMapper.queryGoodsOrderByParams(startDate,
            endDate);
        PageInfo<ReportGoodsOrder> pageInfo = new PageInfo<ReportGoodsOrder>(orders);
        return new PagingResult<ReportGoodsOrder>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    @Override
    public List<ReportGoodsOrder> countGoodsOrderPersonByExport(String startDate, String endDate) {
        return reportGoodsOrderMapper.queryGoodsOrderByParams(startDate, endDate);
    }

}
