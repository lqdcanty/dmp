/**
 * evake.cn Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.efida.sports.dmp.unit;

import java.util.List;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.dmp.facade.OpenUnitFacade;
import com.efida.sport.dmp.facade.model.UserUnitModel;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sports.dmp.base.BaseTest;

/**
 * 
 * @author Evance
 * @version $Id: FileServiceTest.java, v 0.1 2017年12月26日 下午11:46:16 Evance Exp $
 */
public class OpenUnit extends BaseTest {

    @Reference
    private OpenUnitFacade userUnit;

    @Test
    public void getuserUnits() {
        DefaultResult<List<UserUnitModel>> userUnit2 = userUnit.getUserUnit("aaaaa");
        System.err.println(JSON.toJSONString(userUnit2));
    }

}
