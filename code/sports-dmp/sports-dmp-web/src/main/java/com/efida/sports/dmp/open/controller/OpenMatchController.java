/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.open.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenMatchModel;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.CallLogService;
import com.efida.sports.dmp.biz.open.EnrollSyncService;
import com.efida.sports.dmp.biz.open.OpenMatchService;
import com.efida.sports.dmp.dao.entity.OpenCallLogEntity;
import com.efida.sports.dmp.utils.JsonResultUtil;
import com.efida.sports.dmp.utils.SeqWorkerUtil;
import com.efida.sports.dmp.utils.ServletUtil;
import com.efida.sports.dmp.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 开放接口 ：赛事服务接口
 * @author zhiyang
 * @version $Id: OpenMatchController.java, v 0.1 2018年5月25日 下午2:41:43 zhiyang Exp $
 */

@RestController
@RequestMapping(value = "/open/match", produces = "application/json")
@Api(value = "open/match", tags = "赛事管理接口")
public class OpenMatchController {

    private Logger           logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenMatchService openMatchService;

    @Autowired
    private CallLogService   callLogService;
    
    @Autowired
    private EnrollSyncService enrollSyncService;

    @ApiOperation(value = "获取赛事信息", notes = "获取赛事信息。")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方编码(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编码", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchStatus", value = "赛事进行状态", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "beginTimeMin", value = "赛事开始时间范围最小值（闭区间）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "beginTimeMax", value = "赛事开始时间范围最大值（开区间）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTimeMin", value = "赛事结束时间范围最小值（闭区间）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTimeMax", value = "赛事结束时间范围最大值（开区间）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页码(不可为空)", dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "单页大小(不可为空)", dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "timestamp", value = "时间戳(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sign", value = "签名(不可为空)", dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "get", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> get(@RequestParam(value = "unitCode", required = true) String unitCode,
                                   @RequestParam(value = "matchCode", required = false) String matchCode,
                                   @RequestParam(value = "matchStatus", required = false) String matchStatus,
                                   @RequestParam(value = "beginTimeMin", required = false) String beginTimeMin,
                                   @RequestParam(value = "beginTimeMax", required = false) String beginTimeMax,
                                   @RequestParam(value = "endTimeMin", required = false) String endTimeMin,
                                   @RequestParam(value = "endTimeMax", required = false) String endTimeMax,
                                   @RequestParam(value = "pageNumber", required = false) int pageNumber,
                                   @RequestParam(value = "pageSize", required = false) int pageSize,
                                   @RequestParam(value = "timestamp", required = false) String timestamp,
                                   @RequestParam(value = "sign", required = false) String sign,
                                   HttpServletRequest request) {
        long start = System.currentTimeMillis();
        OpenCallLogEntity callLog = new OpenCallLogEntity();
        String content = null;
        try {

            String ipAddress = ServletUtil.getIpAddress(request);
            String requestTime = DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT);
            String seqNo = SeqWorkerUtil.generateTimeSequence();
            callLog.setRequestIp(ipAddress);
            callLog.setRequestTime(requestTime);
            callLog.setSeqNo(seqNo);
            callLog.setUnitCode(unitCode);
            callLog.setInterfaceCode("match/get");
            callLog.setInterfaceName("获取赛事信息");
            String queryStr = "unitCode=" + unitCode + "&matchCode=" + matchCode + "&matchStatus="
                              + matchStatus + "&beginTimeMin=" + beginTimeMin + "&beginTimeMax="
                              + beginTimeMax + "&endTimeMin=" + endTimeMin + "&endTimeMax="
                              + endTimeMax + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize
                              + "&timestamp=" + timestamp + "&sign=" + sign;
            callLog.setRequestParam(StringUtil.trimMaxStr(queryStr, 128));
            logger.info("获取赛事信息," + queryStr);
            matchCode = StringUtils.emptyStr(matchCode);
            matchStatus = StringUtils.emptyStr(matchStatus);
            beginTimeMin = StringUtils.emptyStr(beginTimeMin);
            beginTimeMax = StringUtils.emptyStr(beginTimeMax);
            endTimeMin = StringUtils.emptyStr(endTimeMin);
            endTimeMax = StringUtils.emptyStr(endTimeMax);

            List<OpenMatchModel> matchList = openMatchService.queryMatchList(unitCode, matchCode,
                matchStatus, beginTimeMin, beginTimeMax, endTimeMin, endTimeMax, pageNumber,
                pageSize, timestamp, sign);

            callLog.setSuccess((byte) 1);
            callLog.setStatus("获取赛事信息成功。");
            callLog.setTotal(matchList == null ? 0 : matchList.size());
            Map<String, Object> map = JsonResultUtil.getSuccessOpenApiResult("获取赛事信息成功。",
                matchList);
            content = JSON.toJSONString(map);
            return map;
        } catch (OpenException ex1) {
            logger.error("获取赛事信息失败!" + unitCode, ex1);
            callLog.setSuccess((byte) 0);
            callLog.setStatus(StringUtil.trimMaxStr(ex1.getMessage(), 32));
            callLog.setTotal(0);
            return JsonResultUtil.getFailResult(500, ex1.getMessage());
        } catch (Exception ex2) {
            logger.error("获取赛事信息失败!" + unitCode, ex2);
            callLog.setSuccess((byte) 0);
            callLog.setStatus(StringUtil.trimMaxStr(ex2.getMessage(), 32));
            callLog.setTotal(0);
            return JsonResultUtil.getFailResult(500, "系统错误");
        } finally {
            long spent = System.currentTimeMillis() - start;
            callLog.setSpent((int) spent);
            callLogService.saveLog(callLog);
            if (content != null) {
                callLogService.saveLogContent(callLog.getSeqNo(), content);
            }
        }
    }
    
 
    @RequestMapping(value = "sync", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> get(@RequestParam(value = "unitCode", required = true) String unitCode,
                                   @RequestParam(value = "pageNumber", required = false) int pageNumber,
                                   @RequestParam(value = "pageSize", required = false) int pageSize,
                                   HttpServletRequest request) {
     
 
        try {

            long spent =  this.enrollSyncService.multTreadSync2(unitCode, pageNumber, pageSize);
            return JsonResultUtil.getFailResult(200, "花费："+spent+"ms");

        } catch (Exception ex2) {
            logger.error("sync 失败" + unitCode, ex2);
           
            return JsonResultUtil.getFailResult(500, "系统错误");
        }  
 
    }
}
