/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.quartz;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.efida.sports.dmp.synch.data.smartrun.service.SmartrunRunSynComp;

/**
 * dmp同步承办方调度任务
 * @author wang yi
 * @desc 
 * @version $Id: DmpSynDataSchedu.java, v 0.1 2018年9月3日 下午6:36:25 wang yi Exp $
 */
@Component
public class DmpSynDataSchedu {

    @Autowired
    private SmartrunRunSynComp synComp;

    private Logger             logger = LoggerFactory.getLogger(this.getClass());

    /**
     *定时同步趣定向数据
     *每天2点10分30秒触发任务
     */
    @Scheduled(cron = "30 10 2 * * ?")
    //@PostConstruct
    //@Scheduled(cron = "*/5 * * * * ?")
    public void synSmartRun() {
        logger.info("start syn smartrun data");
        synComp.synSmartRunApplyInfo();
        logger.info("end syn smartrun data");
    }

    /**
     * 定时同步九镖数据
     * 每天1点10分30秒触发任务
     */
    @Scheduled(cron = "30 10 1 * * ?")
    @PostConstruct
    public void synJiuBiaoRun() {
        logger.info("start syn jiubiao data");
        synComp.synJiuBiaoInfo();
        logger.info("end syn jiubiao data");
    }

}
