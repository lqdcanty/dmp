/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

/**
 * 
 * @author lizhiyang
 * @version $Id: SaveRankTask.java, v 0.1 2018年8月30日 下午9:53:48 lizhiyang Exp $
 */
public class SaveRankTask implements Runnable {

    private OpenScoreRankEntity      data = null;
    private ScoreRankCaculateService scoreRankCaculateService;

    public SaveRankTask(OpenScoreRankEntity item) {

        this.data = item;
    }

    @Override
    public void run() {

        this.scoreRankCaculateService.saveOrUpdate(this.data);
    }

    public OpenScoreRankEntity getData() {
        return data;
    }

    public void setData(OpenScoreRankEntity data) {
        this.data = data;
    }

    public ScoreRankCaculateService getScoreRankCaculateService() {
        return scoreRankCaculateService;
    }

    public void setScoreRankCaculateService(ScoreRankCaculateService scoreRankCaculateService) {
        this.scoreRankCaculateService = scoreRankCaculateService;
    }

}
