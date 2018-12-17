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
import com.efida.sport.open.model.OpenProjectModel;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.CallLogService;
import com.efida.sports.dmp.biz.open.ProjectService;
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
 * 开放接口 ：项目服务接口
 * @author zhiyang
 * @version $Id: OpenProjectController.java, v 0.1 2018年5月24日 下午7:04:35 zhiyang Exp $
 */

@RestController()
@RequestMapping(value = "/open/projects", produces = "application/json")
@Api(value = "/open/projects", tags = "项目管理接口")
public class OpenProjectController {
    private Logger         logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CallLogService callLogService;

    /**
     * 查询赛事信息
     * 
     * @param unitCode
     * @param timestamp
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "获取项目信息列表", notes = "用于返回项目信息列表。")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方编码(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "timestamp", value = "时间戳(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sign", value = "签名(不可为空)", dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "unitCode", required = true) String unitCode,
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
            callLog.setInterfaceCode("project/list");
            callLog.setInterfaceName("获取项目");
            String queryStr = "unitCode=" + unitCode + "&timestamp=" + timestamp + "&sign=" + sign;
            callLog.setRequestParam(StringUtil.trimMaxStr(queryStr, 128));
            logger.info(
                "查询项目信息列表， unitCode:" + unitCode + ",timestamp:" + timestamp + ",sign:" + sign);
            List<OpenProjectModel> projects = projectService.queryProjectList(unitCode, timestamp,
                sign);

            callLog.setSuccess((byte) 1);
            callLog.setStatus("获取项目列表成功。");
            callLog.setTotal(projects == null ? 0 : projects.size());
            Map<String, Object> map = JsonResultUtil.getSuccessOpenApiResult("获取项目列表成功。", projects);
            content = JSON.toJSONString(map);
            return map;
        } catch (OpenException ex1) {
            logger.error("获取项目信息失败， unitCode:" + unitCode + "," + ex1.getMessage());
            callLog.setSuccess((byte) 0);
            callLog.setStatus(StringUtil.trimMaxStr(ex1.getMessage(), 32));
            callLog.setTotal(0);
            return JsonResultUtil.getFailResult(500, ex1.getMessage());
        } catch (Exception ex2) {
            logger.error("获取项目列表失败!" + unitCode, ex2);
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

}
