/**
 * evake.cn Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.efida.sports.dmp.open;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sports.dmp.base.BaseTest;

import cn.evake.auth.dubbo.open.facade.OpenAuthFacade;
import cn.evake.auth.dubbo.open.facade.OpenUserFacade;
import cn.evake.auth.dubbo.open.model.OpenUserModel;
import cn.evake.auth.dubbo.open.result.OpenAuthResult;

/**
 * 
 * @author Evance
 * @version $Id: FileServiceTest.java, v 0.1 2017年12月26日 下午11:46:16 Evance Exp $
 */
public class OpenAuth extends BaseTest {

    @Reference
    private OpenUserFacade openUserFacade;

    @Reference
    private OpenAuthFacade openAuthFacade;

    @Test
    public void getUserInfo() {
        OpenAuthResult<OpenUserModel> userInfo = openUserFacade.getUserInfo("US20180620153725");
        System.err.println(JSON.toJSONString(userInfo));
    }

    @Test
    public void getLoginUserInfo() {
        OpenAuthResult<OpenUserModel> loginUserInfo = openAuthFacade.getLoginUserInfo("admindefult",
            "YWRtaW5kZWZ1bHQxNTMzMTgwMjM1OTM2");
        System.err.println(JSON.toJSONString(loginUserInfo));
    }

    @Test
    public void checkIsLogin() {
        OpenAuthResult<Boolean> checkIsLogin = openAuthFacade.checkIsLogin("admindefult",
            "YWRtaW5kZWZ1bHQxNTMzMTgwMjM1OTM2");
        System.err.println(JSON.toJSONString(checkIsLogin));
    }
}
