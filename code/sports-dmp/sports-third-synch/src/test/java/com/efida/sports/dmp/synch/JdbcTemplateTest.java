package com.efida.sports.dmp.synch;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.TCustomer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcTemplateTest {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate secondaryJdbcTemplate;

    @Test
    public void test() throws Exception {
        // 往第一个数据源中插入两条数据
        List<Map<String, Object>> queryForList = primaryJdbcTemplate
            .queryForList("select * from t_customer_act limit 0,10");
        System.err.println(JSON.toJSONString(queryForList));
        List<TCustomer> queryForObject = primaryJdbcTemplate.query(
            "select * from t_customer_act limit 0,10", new Object[] {},
            new BeanPropertyRowMapper<>(TCustomer.class));
        System.err.println(JSON.toJSONString(queryForObject));
    }
}