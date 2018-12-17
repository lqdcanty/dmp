package com.efida.sports.dmp.biz.score;

import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: OpenApplyinfoService.java, v 0.1 2018年8月29日 下午4:56:40 zengbo Exp $
 */
public interface OpenApplyinfoCountService {

    /**
     * 
     * 
     * @return
     */
    List<String> queryUnitEnrollDate();

    /**
     * 承办方报名人数统计
     * 
     * @param map
     * @return
     */
    List<ReportUnitEnrollEntity> queryUnitEnrollCount(String date);

    /**
     * 赛事报名人数统计
     * 
     * @param map
     * @return
     */
    List<ReportMatchSourceEntity> queryMatchEnrollCount(String date);

}
