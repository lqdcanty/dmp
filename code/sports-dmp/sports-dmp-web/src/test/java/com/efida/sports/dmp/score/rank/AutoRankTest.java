/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.score.rank;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest;
import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;
import com.efida.sports.dmp.service.score.ScoreRankService;

/**
 * 
 * @author Lenovo
 * @version $Id: AutoRankTest.java, v 0.1 2018年8月14日 上午9:58:30 Lenovo Exp $
 */
public class AutoRankTest extends BaseTest {

    @Autowired
    private ScoreRankCaculateService scoreRankCaculateService;

    @Autowired
    private ScoreRankService         scoreRankService;

    @Test
    public void testSortByCompetition() {

        long total = scoreRankCaculateService.sortScoreByCompetition("competition1535640382732");
        Assert.assertTrue(total >= 0);
    }

    @Test
    public void syncRecord() {

        //match201806141321181634
        // long total = scoreRankCaculateService.sortScoreByUnitCode("leizhansheji");
        SearchCompetionScoreRequest qs = new SearchCompetionScoreRequest();
        qs.setMatchCode("match201807021735587881");
        long total = scoreRankCaculateService.sortScoreByCompetition(qs, "test-xxx");
        //long total = scoreRankCaculateService.sortScoreByMatchCode("match201806141321181634");

        Assert.assertTrue(total > 0);
        total = scoreRankService.deleteAutoRankInfoByCompetitionCode("test-xxx");
        Assert.assertTrue(total > 0);
    }

}
