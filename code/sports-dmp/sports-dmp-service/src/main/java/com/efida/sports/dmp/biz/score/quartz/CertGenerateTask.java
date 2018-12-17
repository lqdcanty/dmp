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

import com.efida.sports.dmp.biz.score.SyncCertService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: CertGenerateTask.java, v 0.1 2018年10月25日 下午2:11:45 wang yi Exp $
 */
@Component
public class CertGenerateTask {
    private Logger          logger        = LoggerFactory.getLogger(this.getClass());

    private final String    LOCK_TASK_KEY = "dmp_lock_for_all_gertGenerateTask";

    private final int       LOCK_TIME     = 3 * 60 * 60;

    @Autowired
    private CacheService    cacheSevcice;

    @Autowired
    private SyncCertService syncCertService;

    /**
     * 定时生成证书
     * 8小时执行一次
     */
    @Scheduled(cron = "* * */8 * * ?")
    public void timingAutoRank() {
        String status = cacheSevcice.get(LOCK_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("start generateCertByAllScore.....");
            cacheSevcice.put(LOCK_TASK_KEY, SeqWorkerUtil.buildSingleId(), LOCK_TIME);
            syncCertService.generateCertByAllScore();
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            cacheSevcice.remove(LOCK_TASK_KEY);
        }
    }

}
