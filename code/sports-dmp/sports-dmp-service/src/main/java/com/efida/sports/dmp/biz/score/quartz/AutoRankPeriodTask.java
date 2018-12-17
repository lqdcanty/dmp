/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.quartz;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * @author lizhiyang
 * @version $Id: AutoRankPeriodTask.java, v 0.1 2018年8月30日 下午2:12:30 lizhiyang Exp $
 */
@Component
public class AutoRankPeriodTask {

    //
    private Logger                   logger        = LoggerFactory
        .getLogger(AutoRankPeriodTask.class);

    private final String             LOCK_TASK_KEY = "dmp_lock_for_autoRankPeriodTask";

    @Autowired
    private CacheService             cacheSevcice;

    @Autowired
    private ScoreRankCaculateService scoreRankCaculateService;

    /**
         * 定时将成功报名信息同步到dmp系统
         */
    @Scheduled(cron = "0 0 1,12,19 * * ?")
    //@Scheduled(cron = "*/30 * * * * ?")
    public void timingAutoRank() {

        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        String status = cacheSevcice.get(LOCK_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("start timingAutoRank !");
            cacheSevcice.put(LOCK_TASK_KEY, SeqWorkerUtil.buildSingleId(), 600);
            scoreRankCaculateService.autoRankingByConfig();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_TASK_KEY);
    }
}
