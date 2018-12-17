/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.efida.sports.dmp.synch.data.common.dao.entity.SynRelation;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: ZntyConfigDao.java, v 0.1 2018年9月10日 下午4:20:19 wang yi Exp $
 */
@Component
public class ZntyConfigDao {
    @Autowired
    @Qualifier("zntyJdbc")
    protected JdbcTemplate zntyJdbcTemplate;

    /**
     * 获取赛事关联关系
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param actId
     * @param actLevelId
     * @return
     */
    public SynRelation getActRelation(String unitCode, String reMatchId, String reEventId) {
        List<SynRelation> synRelation = zntyJdbcTemplate.query(String.format(
            "select * from syn_relation where re_match_id='%s' and re_event_id='%s' and unit_code='%s'",
            reMatchId, reEventId, unitCode), new Object[] {},
            new BeanPropertyRowMapper<>(SynRelation.class));
        if (CollectionUtils.isEmpty(synRelation)) {
            return null;
        }
        return synRelation.get(0);
    }

    public List<SynRelation> getNeedSynRelation(String unitCode) {
        List<SynRelation> synRelation = zntyJdbcTemplate.query(String.format(
            "select * from syn_relation where unit_code='%s' and is_syn=1 and syn_ok=0", unitCode),
            new Object[] {}, new BeanPropertyRowMapper<>(SynRelation.class));
        return synRelation;
    }

}
