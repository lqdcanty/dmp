/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;

/**
 * 
 * @author lizhiyang
 * @version $Id: DeleteRankTask.java, v 0.1 2018年8月31日 下午2:42:15 lizhiyang Exp $
 */
public class DeleteRankTask implements Runnable {

    private Long                     data = null;
    private ScoreRankCaculateService scoreRankCaculateService;

    public DeleteRankTask(Long item) {

        this.data = item;
    }

    @Override
    public void run() {

        this.scoreRankCaculateService.deleteAutoRankRecord(this.data);
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public ScoreRankCaculateService getScoreRankCaculateService() {
        return scoreRankCaculateService;
    }

    public void setScoreRankCaculateService(ScoreRankCaculateService scoreRankCaculateService) {
        this.scoreRankCaculateService = scoreRankCaculateService;
    }

}
