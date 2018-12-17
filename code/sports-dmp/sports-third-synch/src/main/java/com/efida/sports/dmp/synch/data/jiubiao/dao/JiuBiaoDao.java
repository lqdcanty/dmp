/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.jiubiao.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.efida.sports.dmp.synch.data.jiubiao.dao.entity.ShotLastmatch;
import com.efida.sports.dmp.synch.data.jiubiao.dao.entity.ShotMatch;
import com.efida.sports.dmp.synch.data.jiubiao.dao.entity.ShotMember;

/**
 * 九镖dao
 * @author wang yi
 * @desc 
 * @version $Id: JiuBiaoDao.java, v 0.1 2018年9月13日 下午5:12:35 wang yi Exp $
 */
@Component
public class JiuBiaoDao {

    @Autowired
    @Qualifier("jiubiaoJdbc")
    protected JdbcTemplate jiubiaoJdbcTemplate;

    /**
     * 根据城市查询报名
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param cityId
     * @return
     */
    public List<ShotMember> getShotApplyMember(String cityId) {
        List<ShotMember> queryForObject = jiubiaoJdbcTemplate.query(String.format(
            "select  *,sm.id as id from shot_member as sm join shot_city as sc  on (sm.city_name=sc.name) where sc.id='%s' order by sm.id desc ",
            cityId), new Object[] {}, new BeanPropertyRowMapper<>(ShotMember.class));
        return queryForObject;
    }

    /**
     * 根据城市查询成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param reFileId
     * @return
     */
    public List<ShotMatch> getMatchScore(String cityId) {
        List<ShotMatch> queryForObject = jiubiaoJdbcTemplate.query(String.format(
            "select *,sa.id from  shot_city as sc join shot_match as sm join shot_member as sa on (sm.city_name=sc.name and sm.userid=sa.id) where   sm.status='0' and (sm.best_score is not null and sm.best_score !='') and sc.id='%s' ",
            cityId), new Object[] {}, new BeanPropertyRowMapper<>(ShotMatch.class));
        return queryForObject;
    }

    /**
     * 查询决赛成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param cityId
     * @return
     */
    public List<ShotLastmatch> getLastMatchScore(String cityId) {
        List<ShotLastmatch> queryForObject = jiubiaoJdbcTemplate.query(String.format(
            "select *,sa.id from  shot_member as sa join shot_city as sc join shot_lastmatch as sm   on (sm.city_name=sc.name and sm.userid=sa.id) where   sm.status='0' and (sm.best_score is not null and sm.best_score !='') and sc.id='%s' ORDER BY sa.id desc",
            cityId), new Object[] {}, new BeanPropertyRowMapper<>(ShotLastmatch.class));
        return queryForObject;
    }

}
