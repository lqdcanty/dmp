package com.efida.sports.dmp.count;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sport.facade.model.GoodsOrderCountModel;
import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.biz.score.OpenApplyinfoCountService;
import com.efida.sports.dmp.biz.score.OpenScoreCountService;
import com.efida.sports.dmp.biz.score.ReportGoodsOrderService;
import com.efida.sports.dmp.biz.score.ReportMatchFinishService;
import com.efida.sports.dmp.biz.score.ReportMatchSourceService;
import com.efida.sports.dmp.biz.score.ReportUnitEnrollService;
import com.efida.sports.dmp.biz.score.ReportUnitFinishService;
import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.service.dubbo.intergration.GoodsOrderFacade;
import com.efida.sports.dmp.utils.DateUtil;

/** 
 * 测试导出Excel数据 
 *  
 * @author jiaxingHuang 
 * @version 1.0 2014年8月20日 
 *  
 */
public class CountTest extends BaseTest {

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
    private GoodsOrderFacade          goodsOrderFacade;

    @Autowired
    private ReportGoodsOrderService   reportGoodsOrderService;

    //    /**
    //     * 承办方报名数据
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void unittest() throws Exception {
    //        List<String> dates = openApplyinfoCountService.queryUnitEnrollDate();
    //        System.out.println(JSON.toJSONString(dates));
    //        for (String str : dates) {
    //            List<ReportUnitEnrollEntity> list = openApplyinfoCountService.queryUnitEnrollCount(str);
    //            System.out.println(JSON.toJSONString(list));
    //            //                reportUnitEnrollService.saveReportUnitList(list);
    //        }
    //    }
    //
    //    @Test
    //    public void countUnitEnrollByCode() throws Exception {
    //        int s = reportUnitEnrollService.countUnitEnrollByCode("test", "2018-08-30");
    //        System.out.println(s);
    //    }
    //
    //    /**
    //     * 赛事报名数据
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void matchtest() throws Exception {
    //        List<String> dates = openApplyinfoCountService.queryUnitEnrollDate();
    //        for (String str : dates) {
    //            List<ReportMatchSourceEntity> lists = openApplyinfoCountService
    //                .queryMatchEnrollCount(str);
    //            System.out.println(JSON.toJSONString(lists));
    //            //            reportMatchSourceService.saveReportMatchSourceList(lists);
    //
    //        }
    //    }
    //
    //    /**
    //     * 承办方完善人数统计
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void unitScoreTest() throws Exception {
    //        List<String> dates = openScoreCountService.queryUnitScoreDate();
    //        for (String str : dates) {
    //            List<ReportUnitFinishEntity> records = openScoreCountService.queryUnitScoreCount(str);
    //            System.out.println(JSON.toJSONString(records));
    //            //            reportUnitFinishService.saveReportUnitFinishList(records);
    //        }
    //    }
    //
    //    /**
    //     * 赛事完善人数统计
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void matchScoreTest() throws Exception {
    //        List<String> dates = openScoreCountService.queryUnitScoreDate();
    //        for (String str : dates) {
    //            List<ReportMatchFinishEntity> records = openScoreCountService.queryMatchScoreCount(str);
    //            System.out.println(JSON.toJSONString(records));
    //            //            reportMatchFinishService.saveReportMatchFinishList(records);
    //        }
    //    }

    //    @Test
    //    public void test() {
    //        System.out
    //            .println("报名表开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //        List<String> applydates = openApplyinfoCountService.queryUnitEnrollDate();
    //        for (String applydate : applydates) {
    //            System.out.println(
    //                "承办方报名表开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            List<ReportUnitEnrollEntity> list = openApplyinfoCountService
    //                .queryUnitEnrollCount(applydate);
    //            System.out.println(
    //                "承办方报名表结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            System.out.println(
    //                "赛事报名表开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            List<ReportMatchSourceEntity> lists = openApplyinfoCountService
    //                .queryMatchEnrollCount(applydate);
    //            System.out.println(
    //                "赛事报名表结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //        }
    //        List<String> scoredates = openScoreCountService.queryUnitScoreDate();
    //        for (String scoredate : scoredates) {
    //            System.out.println(
    //                "承办方完赛开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            List<ReportUnitFinishEntity> unitList = openScoreCountService
    //                .queryUnitScoreCount(scoredate);
    //            System.out.println(
    //                "承办方完赛结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            System.out.println(
    //                "赛事完赛开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //            List<ReportMatchFinishEntity> matchList = openScoreCountService
    //                .queryMatchScoreCount(scoredate);
    //            System.out.println(
    //                "赛事完赛结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //        }
    //        System.out
    //            .println("结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    //    }

    //    /**
    //     * 总报名人数 百分比
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void testPercent() throws Exception {
    //        //        int enroll = reportUnitEnrollService.countTotalUnitEnroll(null, null);
    //        //        int finish = reportUnitFinishService.countTotalUnitFinish(null, null);
    //
    //        int enroll = reportUnitEnrollService.countTotalUnitEnroll("2018-07-01", "2018-08-30");
    //        int finish = reportUnitFinishService.countTotalUnitFinish("2018-07-01", "2018-08-30");
    //
    //        System.out.println(NumberUtil.percentageConvert(finish, enroll));
    //    }
    //
    //    /**
    //     * 承办方报名人次 百分比
    //     * 
    //     * @throws Exception
    //     */
    //    @Test
    //    public void test() throws Exception {
    //        //        int enroll = reportUnitEnrollService.countTotalUnitEnroll(null, null);
    //        //        int finish = reportUnitFinishService.countTotalUnitFinish(null, null);
    //
    //        int enroll = reportUnitEnrollService.countTotalUnitEnroll("2018-07-01", "2018-08-30");
    //        int finish = reportUnitFinishService.countTotalUnitFinish("2018-07-01", "2018-08-30");
    //
    //        System.out.println(NumberUtil.percentageConvert(finish, enroll));
    //    }

    @Test
    public void goodsCount() throws Exception {
        long start = System.currentTimeMillis();
        try {
            String startTime = reportGoodsOrderService.queryScoreStartTime();//赛事开始时间
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
            System.out.println(e);
        } finally {
            System.out.println("timingGoodsOrderTask excute end , spent time :"
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
    public int finishConut(String date, List<Map<String, Object>> finishs) {
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
    public int medalConut(String date, List<Map<String, Object>> medals) {
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
    public int entityConut(String date, List<GoodsOrderCountModel> entitys) {
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

}