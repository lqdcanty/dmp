/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.score.cert;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.biz.score.SyncCertService;

/**
 * 
 * @author zhiyang
 * @version $Id: syncCertServiceTest.java, v 0.1 2018年8月6日 下午8:51:03 zhiyang Exp $
 */
public class syncCertServiceTest extends BaseTest {

    @Autowired
    private SyncCertService syncCertService;

    @Test
    public void syncRecord() {

        long id = 17;
        int count = 0;
        while (count++ < 40) {
            id++;
            String result = this.syncCertService.syncOneScoreById(id);
            Assert.assertTrue(result.length() > 0);
        }

    }
    
    @Test
    public void certDate() {

      
        Date date = new Date();
            int total = this.syncCertService.generateCertByScoreCreateDay(date);
            Assert.assertTrue(total>=0);
    }
    
}
