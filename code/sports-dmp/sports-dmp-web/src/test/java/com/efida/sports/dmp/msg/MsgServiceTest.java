/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.msg;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.efida.sports.dmp.base.BaseTest;
import com.efida.sports.dmp.service.msg.IMsgService;

public class MsgServiceTest extends BaseTest {

    @Autowired
    private IMsgService ismgService;

    @Test
    public void downMsg() {
        ismgService.sendDownMsg("18482190199");
    }

}
