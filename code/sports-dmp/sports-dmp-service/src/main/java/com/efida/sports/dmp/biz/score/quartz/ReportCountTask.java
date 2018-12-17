/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.efida.sport.facade.model.GoodsOrderCountModel;
import com.efida.sports.dmp.biz.score.OpenApplyinfoCountService;
import com.efida.sports.dmp.biz.score.OpenScoreCountService;
import com.efida.sports.dmp.biz.score.ReportGoodsOrderService;
import com.efida.sports.dmp.biz.score.ReportMatchFinishService;
import com.efida.sports.dmp.biz.score.ReportMatchSourceService;
import com.efida.sports.dmp.biz.score.ReportUnitEnrollService;
import com.efida.sports.dmp.biz.score.ReportUnitFinishService;
import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.dao.entity.ReportMatchFinishEntity;
import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;
import com.efida.sports.dmp.service.dubbo.intergration.GoodsOrderFacade;
import com.efida.sports.dmp.utils.DateUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 报表统计定时任务
 * 
 * @author zengbo
 * @version $Id: ReportCountTask.java, v 0.1 2018年8月29日 下午3:15:17 zengbo Exp $
 */
@Component
public class ReportCountTask {
    //
    private Logger                    logger                  = LoggerFactory
        .getLogger(ReportCountTask.class);
    private final String              REPORT_ENROLL_TASK_LOCK = "report_enroll_task_lock";
    private final String              REPORT_UNIT_TASK_LOCK   = "report_unit_task_lock";
    private final String              DAY_UNIT_TASK_LOCK      = "day_unit_task_lock";
    private final String              DAY_GOODS_ORDER_LOCK    = "day_goods_order_lock";
    @Autowired
    private OpenApplyinfoCountService openApplyinfoCountService;
    @Autowired
    private OpenScoreCountService     openScoreCountService;
    @Autowired
    private ReportUnitEnrollService   reportUnitEnrollService;
    @Autowired
    private ReportUnitFinishService   reportUnitFinishService;
    @Autowired
    private ReportMatchSourceService  reportMatchSourceService;
    @Autowired
    private ReportMatchFinishService  reportMatchFinishService;
    @Autowired
    private CacheService              cacheSevcice;
    @Autowired
    private GoodsOrderFacade          goodsOrderFacade;
    @Autowired
    private ReportGoodsOrderService   reportGoodsOrderService;

    /**
     * 定时每隔2小时生成报名统计数据
     */
    @Scheduled(cron = "* * */2 * * ?")
    //    @Scheduled(cron = "0 0 1 * * ? *")
    public void timingCreateApplyinfoDataTask() {
        logger.info("timingCreateApplyinfoDataTask start excute");
        long start = System.currentTimeMillis();
        try {
            String status = cacheSevcice.get(REPORT_ENROLL_TASK_LOCK);
            if (StringUtils.isNotBlank(status)) {
                return;
            }
            cacheSevcice.put(REPORT_ENROLL_TASK_LOCK, UUID.randomUUID().toString(), 1000);
            List<String> applydates = openApplyinfoCountService.queryUnitEnrollDate();
            List<ReportMatchSourceEntity> matchList = openApplyinfoCountService
                .queryMatchEnrollCount("");
            List<ReportUnitEnrollEntity> unitList = openApplyinfoCountService
                .queryUnitEnrollCount("");
            List<ReportMatchSourceEntity> olds = reportMatchSourceService.queryReportMatch(null,
                null);
            for (String applydate : applydates) {
                List<ReportUnitEnrollEntity> units = getUnitList(unitList, applydate);
                reportUnitEnrollService.saveReportUnitList(units);
                List<ReportMatchSourceEntity> matchs = getMatchList(matchList, applydate);
                List<ReportMatchSourceEntity> reportMatch = getOldMatch(olds, applydate);
                if ((matchs == null || matchs.size() <= 0) && reportMatch != null
                    && reportMatch.size() > 0) {
                    for (ReportMatchSourceEntity entity : reportMatch) {
                        reportMatchSourceService.delReportMatch(entity.getId());
                    }
                }
                reportMatchSourceService.saveReportMatchSourceList(matchs);
            }
        } catch (Exception e) {
            logger.error("成报名统计数据异常", e);
        } finally {
            cacheSevcice.remove(REPORT_ENROLL_TASK_LOCK);
            logger.info("timingCreateApplyinfoDataTask excute end , spent time :"
                        + (System.currentTimeMillis() - start) + "ms");
        }
    }

    public static List<ReportMatchSourceEntity> getOldMatch(List<ReportMatchSourceEntity> olds,
                                                            String date) {
        List<ReportMatchSourceEntity> matchs = new ArrayList<ReportMatchSourceEntity>();
        if (olds == null || olds.size() <= 0) {
            return olds;
        }
        for (ReportMatchSourceEntity match : olds) {
            String dated = DateUtil.format(match.getApplyTimeDay(), DateUtil.WEB_FORMAT);
            if (date.equals(dated)) {
                matchs.add(match);
            }
        }
        return matchs;
    }

    /**
     * 定时每隔2小时生成成绩统计数据
     */
    @Scheduled(cron = "* * */2 * * ?")
    //    @Scheduled(cron = "0 0 1 * * ? *")
    public void timingCreateScoreDataTask() {
        logger.info("timingCreateScoreDataTask start excute");
        long start = System.currentTimeMillis();
        try {
            String status = cacheSevcice.get(REPORT_UNIT_TASK_LOCK);
            if (StringUtils.isNotBlank(status)) {
                return;
            }
            cacheSevcice.put(REPORT_UNIT_TASK_LOCK, UUID.randomUUID().toString(), 1000);
            List<String> partdates = openScoreCountService.queryUnitScoreDate();
            List<ReportUnitFinishEntity> unitRecords = openScoreCountService
                .queryUnitScoreCount("");
            List<ReportMatchFinishEntity> matchRecords = openScoreCountService
                .queryMatchScoreCount("");
            for (String partdate : partdates) {
                List<ReportUnitFinishEntity> units = getUnitFinsh(unitRecords, partdate);
                List<ReportMatchFinishEntity> matchs = getMatchFinsh(matchRecords, partdate);
                reportUnitFinishService.saveReportUnitFinishList(units);
                reportMatchFinishService.saveReportMatchFinishList(matchs);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            cacheSevcice.remove(REPORT_UNIT_TASK_LOCK);
            logger.info("timingCreateScoreDataTask excute end , spent time :"
                        + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 每5分钟更新一次当日数据
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    //    @Scheduled(cron = "0 0 1 * * ? *")
    public void timingUpdateDayDataTask() {
        logger.info("timingUpdateDayDataTask start excute");
        long start = System.currentTimeMillis();
        try {
            String status = cacheSevcice.get(DAY_UNIT_TASK_LOCK);
            if (StringUtils.isNotBlank(status)) {
                return;
            }
            cacheSevcice.put(DAY_UNIT_TASK_LOCK, UUID.randomUUID().toString(), 300);
            String date = DateUtil.formatDate(new Date());
            List<ReportUnitEnrollEntity> unitList = openApplyinfoCountService
                .queryUnitEnrollCount(date);
            reportUnitEnrollService.saveReportUnitList(unitList);
            List<ReportMatchSourceEntity> matchList = openApplyinfoCountService
                .queryMatchEnrollCount(date);
            reportMatchSourceService.saveReportMatchSourceList(matchList);
            List<ReportUnitFinishEntity> unitRecords = openScoreCountService
                .queryUnitScoreCount(date);
            reportUnitFinishService.saveReportUnitFinishList(unitRecords);
            List<ReportMatchFinishEntity> matchRecords = openScoreCountService
                .queryMatchScoreCount(date);
            reportMatchFinishService.saveReportMatchFinishList(matchRecords);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            cacheSevcice.remove(DAY_UNIT_TASK_LOCK);
            logger.info("timingUpdateDayDataTask excute end , spent time :"
                        + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 每隔1个小时更新一次奖章统计数据
     */
    @Scheduled(cron = "* * */1 * * ?")
    public void timingGoodsOrderTask() {
        logger.info("timingGoodsOrderTask start excute");
        long start = System.currentTimeMillis();
        try {
            String status = cacheSevcice.get(DAY_GOODS_ORDER_LOCK);
            if (StringUtils.isNotBlank(status)) {
                return;
            }
            cacheSevcice.put(DAY_GOODS_ORDER_LOCK, UUID.randomUUID().toString(), 1000);
            String startTime = reportGoodsOrderService.queryScoreStartTime();//成绩开始时间
            int day = DateUtil.getDateDifference(startTime,
                DateUtil.format(new Date(), DateUtil.WEB_FORMAT));
            /**获取从赛事开始到当前日期所有的时间*/
            List<String> dates = DateUtil.getAvgDateList(DateUtil.strToDate(startTime), new Date(),
                day);
            if (dates != null && dates.size() > 0) {
                List<Map<String, Object>> finishs = reportGoodsOrderService
                    .countFinishEnrollPerson();//每日完赛人数统计
                List<Map<String, Object>> medals = reportGoodsOrderService.countMedalReceive();//电子奖章每日人数统计
                List<GoodsOrderCountModel> entitys = goodsOrderFacade.queryGoodsOrderByDay(null,
                    null, "1");//实物奖章领取人数
                List<GoodsOrderCountModel> onlines = goodsOrderFacade.queryGoodsOrderByDay(null,
                    null, "0");//实物奖章在线订购数
                for (String date : dates) {
                    int finish = finishConut(date, finishs);
                    int medal = medalConut(date, medals);
                    int entity = entityConut(date, entitys);
                    int online = entityConut(date, onlines);
                    ReportGoodsOrder order = new ReportGoodsOrder();
                    order.setDate(DateUtil.strToDate(date));
                    order.setFinishCount(finish);
                    order.setMedalReceive(medal);
                    order.setEntityReceive(entity);
                    order.setOnlineOrder(online);
                    reportGoodsOrderService.saveGoodsOrder(order);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            cacheSevcice.remove(DAY_GOODS_ORDER_LOCK);
            logger.info("timingGoodsOrderTask excute end , spent time :"
                        + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 获取当日完赛人次
     * 
     * @param date
     * @param finishs
     * @return
     */
    public static int finishConut(String date, List<Map<String, Object>> finishs) {
        int total = 0;
        if (finishs != null && finishs.size() > 0) {
            for (Map<String, Object> map : finishs) {
                Date partTime = (Date) map.get("partTime");
                String partDate = DateUtil.format(partTime, DateUtil.WEB_FORMAT);
                if (date.equals(partDate)) {
                    Long conut = (Long) map.get("count");
                    return conut.intValue();
                }
            }
        }
        return total;
    }

    /**
     * 获取电子奖章每日人数
     * 
     * @param date
     * @param medals
     * @return
     */
    public static int medalConut(String date, List<Map<String, Object>> medals) {
        int total = 0;
        if (medals != null && medals.size() > 0) {
            for (Map<String, Object> map : medals) {
                Date partTime = (Date) map.get("viewTime");
                String partDate = DateUtil.format(partTime, DateUtil.WEB_FORMAT);
                if (date.equals(partDate)) {
                    Long conut = (Long) map.get("count");
                    return conut.intValue();
                }
            }
        }
        return total;
    }

    /**
     *  获取实物奖章领取人数
     * 
     * @param date
     * @param entitys
     * @return
     */
    public static int entityConut(String date, List<GoodsOrderCountModel> entitys) {
        int total = 0;
        if (entitys != null && entitys.size() > 0) {
            for (GoodsOrderCountModel entity : entitys) {
                Date partTime = entity.getDate();
                String partDate = DateUtil.format(partTime, DateUtil.WEB_FORMAT);
                if (date.equals(partDate)) {
                    Long conut = entity.getTotal();
                    return conut.intValue();
                }
            }
        }
        return total;
    }

    public static List<ReportMatchSourceEntity> getMatchList(List<ReportMatchSourceEntity> matchList,
                                                             String date) {
        List<ReportMatchSourceEntity> matchs = new ArrayList<ReportMatchSourceEntity>();
        if (matchList == null || matchList.size() <= 0) {
            return matchs;
        }
        for (ReportMatchSourceEntity entity : matchList) {
            String dated = DateUtil.format(entity.getApplyTimeDay(), DateUtil.WEB_FORMAT);
            if (date.equals(dated)) {
                matchs.add(entity);
            }
        }
        return matchs;
    }

    public static List<ReportUnitEnrollEntity> getUnitList(List<ReportUnitEnrollEntity> unitList,
                                                           String date) {
        List<ReportUnitEnrollEntity> units = new ArrayList<ReportUnitEnrollEntity>();
        if (unitList == null || unitList.size() <= 0) {
            return units;
        }
        for (ReportUnitEnrollEntity unit : unitList) {
            String dated = DateUtil.format(unit.getApplyTimeDay(), DateUtil.WEB_FORMAT);
            if (date.equals(dated)) {
                units.add(unit);
            }
        }
        return units;
    }

    public static List<ReportUnitFinishEntity> getUnitFinsh(List<ReportUnitFinishEntity> unitList,
                                                            String date) {
        List<ReportUnitFinishEntity> units = new ArrayList<ReportUnitFinishEntity>();
        if (unitList == null || unitList.size() <= 0) {
            return units;
        }
        for (ReportUnitFinishEntity unit : unitList) {
            String dated = DateUtil.format(unit.getPartTimeDay(), DateUtil.WEB_FORMAT);
            if (date.equals(dated)) {
                units.add(unit);
            }
        }
        return units;
    }

    public static List<ReportMatchFinishEntity> getMatchFinsh(List<ReportMatchFinishEntity> matchList,
                                                              String date) {
        List<ReportMatchFinishEntity> matchs = new ArrayList<ReportMatchFinishEntity>();
        if (matchList == null || matchList.size() <= 0) {
            return matchs;
        }
        for (ReportMatchFinishEntity match : matchList) {
            String dated = DateUtil.format(match.getPartTimeDay(), DateUtil.WEB_FORMAT);
            if (date.equals(dated)) {
                matchs.add(match);
            }
        }
        return matchs;
    }
}