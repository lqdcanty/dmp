/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.util.devices.DeviceUtils;

/**
 * 
 * 通用
 * @author wang yi
 * @desc 
 * @version $Id: CommonPageController.java, v 0.1 2018年6月21日 下午2:18:53 wang yi Exp $
 */
@Controller
@RequestMapping(value = "/pages/open")
public class CommonPageController {

    private Logger       logger       = LoggerFactory.getLogger(getClass());

    private final String common_pages = "/view/pages";

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserService  userService;

    @RequestMapping(value = "{modules}/{view}", method = RequestMethod.GET)
    public String commonPages(@PathVariable(required = true) String modules,
                              @PathVariable(required = true) String view,
                              HttpServletRequest request) {
        String viewUrl = String.format("%s/%s/%s", common_pages, modules, view);
        if (DeviceUtils.isMobileDevice(request)) {
            viewUrl = String.format("%s/%s/%s", common_pages, modules, view + "_mobile");
        }
        return viewUrl;
    }
}
