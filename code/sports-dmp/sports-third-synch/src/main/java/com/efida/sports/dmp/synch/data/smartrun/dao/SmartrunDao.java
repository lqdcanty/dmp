/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.smartrun.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.efida.sports.dmp.synch.data.smartrun.dao.entity.TAdminActLevel;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActAndCustomerExt;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActLevelExt;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: SmartrunDao.java, v 0.1 2018年9月6日 下午5:12:35 wang yi Exp $
 */
@Component
public class SmartrunDao {

    @Autowired
    @Qualifier("smartrunJdbc")
    protected JdbcTemplate smartrunJdbcTemplate;

    /**
     * 获取赛事以及赛事项信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param i
     * @return 赛事以及赛事项信息
     */
    public List<TActLevelExt> getActLevelExt(int actId) {
        List<TActLevelExt> queryForObject = smartrunJdbcTemplate.query(String.format(
            "select * from t_admin_act join t_admin_act_level on t_admin_act.id=t_admin_act_level.act_id where t_admin_act.id=%s",
            actId), new Object[] {}, new BeanPropertyRowMapper<>(TActLevelExt.class));
        return queryForObject;
    }

    /**
     * 获取赛事下的报名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param i
     * @return
     */
    public List<TActAndCustomerExt> getActAndCustomerExt(int actId) {
        List<TActAndCustomerExt> queryForObject = smartrunJdbcTemplate.query(String.format(
            "select *,ta.id as cu_id from t_customer_act ta join t_customer tc join t_apply_card td on (ta.customer_id=tc.id and td.customer_id=tc.id) where ta.state=1 and ta.act_id=%s",
            actId), new Object[] {}, new BeanPropertyRowMapper<>(TActAndCustomerExt.class));
        return queryForObject;
    }

    /**
     * 获取赛事分组
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param actLevelId
     * @return
     */
    public TAdminActLevel getActLevel(Integer actLevelId) {
        List<TAdminActLevel> actLevel = smartrunJdbcTemplate.query(
            String.format("select * from t_admin_act_level where id=%s", actLevelId),
            new Object[] {}, new BeanPropertyRowMapper<>(TAdminActLevel.class));
        if (CollectionUtils.isEmpty(actLevel)) {
            return null;
        }
        return actLevel.get(0);
    }

}
