package com.efida.sports.dmp.biz.score;

import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchFinishEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: OpenScoreCountService.java, v 0.1 2018年8月29日 下午8:57:45 zengbo Exp $
 */
public interface OpenScoreCountService {

    /**
     * 
     * 
     * @return
     */
    List<String> queryUnitScoreDate();

    /**
     * 承办方完赛人数统计
     * 
     * @param map
     * @return
     */
    List<ReportUnitFinishEntity> queryUnitScoreCount(String date);

    /**
     * 赛事完赛人数统计
     * 
     * @param map
     * @return
     */
    List<ReportMatchFinishEntity> queryMatchScoreCount(String date);

}
