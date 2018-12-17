/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.quartz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.efida.sports.dmp.biz.open.EnrollSyncService;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 报名数据同步任务 
 * @author zhiyang
 * @version $Id: OpenEnrollSycTask.java, v 0.1 2018年7月5日 下午8:11:55 zhiyang Exp $
 */
@Component
public class OpenEnrollSycTask {

    //
    private Logger            logger               = LoggerFactory.getLogger(this.getClass());

    private final String      LOCK_ENROLL_TASK_KEY = "dmp_lock_for_enrollsyc2sports_task";

    @Autowired
    private CacheService      cacheSevcice;

    @Autowired
    private EnrollSyncService enrollSyncService;

    /**
     * 定时将成功报名信息同步到dmp系统
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void timingSycEnrollInfo2Dmp() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        String status = cacheSevcice.get(LOCK_ENROLL_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("start timingSycEnrollInfo2Dmp !");
            cacheSevcice.put(LOCK_ENROLL_TASK_KEY, SeqWorkerUtil.buildSingleId(), 900);
            // singleTreadSync();
            this.enrollSyncService.multTreadSync(2000);
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_ENROLL_TASK_KEY);
    }

    private void singleTreadSync() {

        int pageSize = 200;
        long start = System.currentTimeMillis();
        while (true) {

            List<OpenApplyInfoEntity> items = this.enrollSyncService.selectUnSyncRecord(1,
                pageSize);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            boolean flag = false;
            for (OpenApplyInfoEntity item : items) {
                enrollSyncService.syncOneEnrollInfo(item);
                if (System.currentTimeMillis() - start >= 600000) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
            if (items.size() < pageSize) {
                break;
            }
        }

    }

}
