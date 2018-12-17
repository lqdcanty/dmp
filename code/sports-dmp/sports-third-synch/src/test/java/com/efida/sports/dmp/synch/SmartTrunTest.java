/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActAndCustomerExt;
import com.efida.sports.dmp.synch.data.smartrun.service.SmartrunEnrollService;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: SmartTrunTest.java, v 0.1 2018年9月6日 下午6:24:32 wang yi Exp $
 */
public class SmartTrunTest extends BaseTest {

    @Autowired
    public SmartrunEnrollService smartrunEnrollService;

    @Test
    public void smartrunReadActTest() throws Exception {
    }

    @Test
    public void smartrunReadActEnTest() throws Exception {
        List<TActAndCustomerExt> readActEventMatch = smartrunEnrollService.readCactEnrollInfos(25);
        System.err.println(JSON.toJSONString(readActEventMatch));
    }
}
