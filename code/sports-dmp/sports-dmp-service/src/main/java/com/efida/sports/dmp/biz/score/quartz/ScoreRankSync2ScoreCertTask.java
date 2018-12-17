/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.efida.sports.dmp.biz.open.quartz.OpenEnrollSycTask;
import com.efida.sports.dmp.biz.score.ScoreCertService;
import com.efida.sports.dmp.biz.score.SyncCertService;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 将open_score数据同步到 score_cert中
 * 
 * @author zhiyang
 * @version $Id: ScoreRankSync2ScoreCertTask.java, v 0.1 2018年8月5日 下午9:33:49 zhiyang Exp $
 */
@Component
public class ScoreRankSync2ScoreCertTask {

    //
    private Logger                logger              = LoggerFactory
        .getLogger(OpenEnrollSycTask.class);

    private final String          LOCK_SCORE_TASK_KEY = "dmp_lock_for_ScoreRankSync2ScoreCertTask";

    @Autowired
    private CacheService          cacheSevcice;

    @Autowired
    private SyncCertService       syncCertService;

    @Autowired
    private SpMatchFacadeClient   spMatchFacadeClient;

    @Autowired
    private OpenScoreEntityMapper openScoreEntityMapper;

    @Autowired
    private ScoreCertService      scoreCertService;

    private Boolean               isAuto              = false;

    /**
     * 定时将成功报名信息同步到dmp系统
     */
    //@Scheduled(cron = "*/30 * * * * ?")
    public void timingSycEnrollInfo2Dmp() {
        if (!isAuto) {
            return;
        }
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        String status = cacheSevcice.get(LOCK_SCORE_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("start timingSycEnrollInfo2Dmp !");
            cacheSevcice.put(LOCK_SCORE_TASK_KEY, SeqWorkerUtil.buildSingleId(), 900);
            int pageSize = 200;
            long start = System.currentTimeMillis();
            while (true) {

                List<OpenScoreEntity> items = this.selectUnSyncRecord(1, pageSize);
                if (CollectionUtils.isEmpty(items)) {
                    break;
                }
                boolean flag = false;
                for (OpenScoreEntity item : items) {
                    this.syncCertService.syncBestScore2Player(item);
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
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_SCORE_TASK_KEY);

    }

    private List<OpenScoreEntity> selectUnSyncRecord(int pageNumber, int pageSize) {

        int start = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("start", start);
        map.put("limit", pageSize);
        List<OpenScoreEntity> items = this.openScoreEntityMapper.selectUnSyncRecord(map);

        return items;
    }
}
