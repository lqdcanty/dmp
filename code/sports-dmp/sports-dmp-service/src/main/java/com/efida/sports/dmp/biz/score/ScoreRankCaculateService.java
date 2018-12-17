/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score;

import com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

/**
 * 
 * @author Lenovo
 * @version $Id: ScoreRankCaculateService.java, v 0.1 2018年8月13日 下午5:45:31 Lenovo Exp $
 */
public interface ScoreRankCaculateService {
    /**
     * 查询出该承办方下面的赛事，并按赛事排名
     * 
     * @param unitCode
     * @return 
     */
    public long sortScoreByUnitCode(String unitCode);

    /**
     * 通过比赛编号排序
     * 
     * @param competitionCode
     * @return
     */
    public int sortScoreByCompetition(String competitionCode);

    /**
     * 
     * 
     * @param rank
     */
    public void saveOrUpdate(OpenScoreRankEntity rank);

    /**
     * 通过搜索条件对满足条件数据进行排名。
     * 
     * @param qs
     * @param competitionCode
     * @return
     */
    public int sortScoreByCompetition(SearchCompetionScoreRequest qs, String competitionCode);

    /**
     * 通过比赛配置信息自动排序
     */
    public void autoRankingByConfig();

    public int deleteAutoRankRecord(Long id);
}
