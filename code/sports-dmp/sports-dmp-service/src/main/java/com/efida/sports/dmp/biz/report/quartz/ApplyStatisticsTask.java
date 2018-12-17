/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.report.quartz;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.efida.sports.dmp.service.player.ApplyAreaStatisticsService;
import com.efida.sports.dmp.service.player.OpenPlayerCleanService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * @author zoutao
 * @version $Id: ApplyStatisticsTask.java, v 0.1 2018年9月14日 下午5:42:43 zoutao Exp $
 */
@Component
public class ApplyStatisticsTask {

    //
    private Logger                     logger                               = LoggerFactory
        .getLogger(this.getClass());

    private final String               LOCK_CLEAN_PLAYER_TASK_KEY           = "dmp_lock_for_clean_open_player_task";
    private final String               LOCK_CLEAN_APPLY_STATISTICS_TASK_KEY = "dmp_lock_for_clean_apply_statistics_task";
    private final String               LOCK_CLEAN_AREA_STATISTICS_TASK_KEY  = "dmp_lock_for_clean_area_statistics_task";

    @Autowired
    private CacheService               cacheSevcice;
    @Autowired
    private OpenPlayerCleanService     playerCleanService;
    @Autowired
    private ApplyAreaStatisticsService applyAreaStatisticsService;

    /**
     * 定时清洗运动员数据
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void cleanOpenPlayer() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }

        String status = cacheSevcice.get(LOCK_CLEAN_PLAYER_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            cacheSevcice.put(LOCK_CLEAN_PLAYER_TASK_KEY, SeqWorkerUtil.buildSingleId(), 500);
            playerCleanService.cleanOpenPlayer();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_CLEAN_PLAYER_TASK_KEY);
    }

    /**
     * 定时统计运动员报名信息
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    public void createApplyStatistics() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }

        String status = cacheSevcice.get(LOCK_CLEAN_APPLY_STATISTICS_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            cacheSevcice.put(LOCK_CLEAN_APPLY_STATISTICS_TASK_KEY, SeqWorkerUtil.buildSingleId(),
                3600);
            playerCleanService.createApplyStatistics();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_CLEAN_APPLY_STATISTICS_TASK_KEY);
    }

    /**
     * 定时清洗运动员数据
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    public void createAreaStatistics() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }

        String status = cacheSevcice.get(LOCK_CLEAN_AREA_STATISTICS_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("start timingComputeInfo !");
            cacheSevcice.put(LOCK_CLEAN_AREA_STATISTICS_TASK_KEY, SeqWorkerUtil.buildSingleId(),
                600);
            applyAreaStatisticsService.createAreaStatistics();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_CLEAN_AREA_STATISTICS_TASK_KEY);
        logger.info("end timingComputeInfo !");
    }

}
