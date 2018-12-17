/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.quartz;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.efida.sports.cert.dao.entity.ScoreCertEntity;
import com.efida.sports.cert.model.PagingResult;
import com.efida.sports.cert.service.cert.ScoreCertServcie;
import com.efida.sports.cert.tread.CertPicHanderTeard;

/**
 * 图片生成任务
 * @author wang yi
 * @desc 
 * @version $Id: CertPicGenerateTask.java, v 0.1 2018年8月5日 下午7:54:28 wang yi Exp $
 */
@Component
public class CertPicGenerateTask {

    private Logger           logger    = LoggerFactory.getLogger(CertPicGenerateTask.class);

    @Autowired
    private ScoreCertServcie scoreCetService;

    /**
     * 线程数
     */
    private int              treadSize = 2;

    /**
     * 
     */
    @Scheduled(cron = "*/3 * * * * ?")
    public void generatePicTask() {
        int pageSize = 10;
        PagingResult<ScoreCertEntity> needGenPic = scoreCetService.getNeedGenPic(1, pageSize);
        if (CollectionUtils.isEmpty(needGenPic.getList())) {
            return;
        }
        logger.info("start gennera certpic.... totalSize:{}", needGenPic.getAllRow());
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(treadSize);
        for (int page = 1; page <= needGenPic.getPageSize(); page++) {
            List<ScoreCertEntity> list = scoreCetService.getNeedGenPic(page, pageSize).getList();
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            //启动数据
            for (int s = 0; s < list.size(); s++) {
                CertPicHanderTeard hander = new CertPicHanderTeard(list.get(s));
                pool.execute(hander);
            }
        }
        pool.shutdown();
        while (!pool.isTerminated()) {
        }
    }

}
