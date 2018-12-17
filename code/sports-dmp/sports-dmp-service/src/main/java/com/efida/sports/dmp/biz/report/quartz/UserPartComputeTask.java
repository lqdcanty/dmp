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

import com.efida.sports.dmp.biz.report.UserPartAnalysisService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * @author lizhiyang
 * @version $Id: UserPartComputeTask.java, v 0.1 2018年8月31日 下午5:27:26 lizhiyang Exp $
 */
@Component
public class UserPartComputeTask {

    //
    private Logger                  logger        = LoggerFactory.getLogger(this.getClass());

    private final String            LOCK_TASK_KEY = "dmp_lock_for_UserPartComputeTask_task";

    @Autowired
    private CacheService            cacheSevcice;

    @Autowired
    private UserPartAnalysisService userPartAnalysisService;

    /**
     * 定时将成功报名信息同步到dmp系统
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void timingComputeInfo() {
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
            logger.info("start timingComputeInfo !");
            cacheSevcice.put(LOCK_TASK_KEY, SeqWorkerUtil.buildSingleId(), 1800);
            // singleTreadSync();
            this.userPartAnalysisService.computeUsertPartSoruce();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_TASK_KEY);
        logger.info("end timingComputeInfo !");

    }

}
