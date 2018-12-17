/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.open.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sports.dmp.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 签名辅助工具，用于测试时生成签名值
 * @author zhiyang
 * @version $Id: SignController.java, v 0.1 2018年5月30日 下午8:58:50 zhiyang Exp $
 */
@RestController()
@RequestMapping(value = "/open/sign", produces = "application/json")
@Api(value = "open/sign", tags = "开放接口签名工具")
public class OpenSignController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "获取MD5签名信息", notes = "传入需要签名字符串，返回md5签名字符串。")
    @ApiImplicitParams({ @ApiImplicitParam(name = "signdata", value = "待签名字符串", dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "md5", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "signdata", required = true) String signdata,
                                    HttpServletRequest request) {
        logger.info("获取MD5签名信息， signdata:" + signdata);
        String md5 = MD5Utils.MD5Encode(signdata);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("md5", md5);
        return map;

    }

}
