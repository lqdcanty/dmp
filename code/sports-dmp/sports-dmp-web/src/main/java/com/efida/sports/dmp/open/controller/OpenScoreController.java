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

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.model.OpenScoreResultModel;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.CallLogService;
import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.biz.open.request.ScoreQueryRequest;
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
 * 
 * @author zhiyang
 * @version $Id: OpenScoreController.java, v 0.1 2018年6月21日 下午5:49:24 zhiyang Exp $
 */
@RestController()
@RequestMapping(value = "/open/score", produces = "application/json")
@Api(value = "open/score", tags = "赛事成绩接口")
public class OpenScoreController {
    private Logger           logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenScoreService openScoreService;

    @Autowired
    private CallLogService   callLogService;

    @ApiOperation(value = "提交比赛成绩信息接口", notes = "提交比赛成绩信息接口")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方编码(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "count", value = "提交数据数(不可为空)", dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "data", value = "提交报名列表(JSON数组）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "timestamp", value = "时间戳(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sign", value = "签名(不可为空)", dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "submit", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> submit(@RequestParam(value = "unitCode", required = true) String unitCode,
                                      @RequestParam(value = "count", required = true) int count,
                                      @RequestParam(value = "data", required = true) String data,
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
            callLog.setInterfaceCode("score/submit");
            callLog.setInterfaceName("提交比赛成绩信息");
            String queryStr = "unitCode=" + unitCode + "&count=" + count + "&data=" + data
                              + "&timestamp=" + timestamp + "&sign=" + sign;
            callLog.setRequestParam(StringUtil.trimMaxStr(queryStr, 128));
            logger.info("提交比赛成绩信息," + queryStr);
            List<OpenScoreResultModel> items = openScoreService.submitScoreInfo(unitCode, count,
                data, timestamp, sign, ipAddress);
            String status = "成功提交比赛成绩信息。" + items.size();
            Map<String, Object> rs = JsonResultUtil.getSuccessOpenApiResult(status, items);
            rs.put("count", items.size());
            int success = 0;
            int failed = 0;
            for (OpenScoreResultModel item : items) {
                if (item.getSuccess() == 1) {
                    success++;
                } else {
                    failed++;
                }
            }
            if (failed > 0) {
                status = "存在提交失败成绩信息,成功条数:" + success + ",失败条数:" + failed;
                rs.put("message", status);
            }
            callLog.setSuccess((byte) 1);
            callLog.setStatus(StringUtil.trimMaxStr(status, 32));
            callLog.setTotal(count);
            return rs;
        } catch (OpenException ex1) {
            logger.error("提交比赛成绩信息失败!" + unitCode, ex1);
            callLog.setSuccess((byte) 0);
            callLog.setStatus(StringUtil.trimMaxStr(ex1.getMessage(), 32));
            callLog.setTotal(0);
            return JsonResultUtil.getFailResult(500, ex1.getMessage());
        } catch (Exception ex2) {
            logger.error("提交比赛成绩信息失败!" + unitCode, ex2);
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

    @ApiOperation(value = "查询比赛成绩信息", notes = "查询比赛成绩信息。")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方编码(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号(可为空）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "eventCode", value = "具体参赛项目编号(可为空）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerCode", value = "运动员编号(可为空）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "scoreName", value = "科目名称(可为空）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "运动员手机号码(可为空）", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页码(不可为空)", dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "单页大小(不可为空)", dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "timestamp", value = "时间戳(不可为空)", dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sign", value = "签名(不可为空)", dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "unitCode", required = true) String unitCode,
                                    @RequestParam(value = "matchCode", required = false) String matchCode,
                                    @RequestParam(value = "eventCode", required = false) String eventCode,
                                    @RequestParam(value = "playerCode", required = false) String playerCode,
                                    @RequestParam(value = "scoreName", required = false) String scoreName,
                                    @RequestParam(value = "playerPhone", required = false) String playerPhone,
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
            callLog.setInterfaceCode("score/list");
            callLog.setInterfaceName("查询比赛成绩信息");
            String queryStr = "unitCode=" + unitCode + "&playerCode=" + playerCode + "&matchCode="
                              + matchCode + "&eventCode=" + eventCode + "&scoreName=" + scoreName
                              + "&playerPhone=" + playerPhone + "&timestamp=" + timestamp + "&sign="
                              + sign;
            callLog.setRequestParam(StringUtil.trimMaxStr(queryStr, 128));
            logger.info("查询比赛成绩信息," + queryStr);

            matchCode = StringUtils.emptyStr(matchCode);
            eventCode = StringUtils.emptyStr(eventCode);
            playerCode = StringUtils.emptyStr(playerCode);
            scoreName = StringUtils.emptyStr(scoreName);
            playerPhone = StringUtils.emptyStr(playerPhone);
            ScoreQueryRequest qr = new ScoreQueryRequest();
            qr.setUnitCode(unitCode);
            qr.setIpAddress(ipAddress);
            qr.setMatchCode(matchCode);
            qr.setEventCode(eventCode);
            qr.setPlayerCode(playerCode);
            qr.setScoreName(scoreName);
            qr.setPlayerPhone(playerPhone);
            qr.setPageNumber(pageNumber);
            qr.setPageSize(pageSize);
            qr.setTimestamp(timestamp);
            qr.setSign(sign);

            List<OpenScoreModel> items = openScoreService.queryScoreInfo(qr);
            String status = "查询比赛成绩信息成功。数量：" + items.size();
            Map<String, Object> rs = JsonResultUtil.getSuccessOpenApiResult(status, items);

            callLog.setSuccess((byte) 1);
            callLog.setStatus(StringUtil.trimMaxStr(status, 32));
            callLog.setTotal(items.size());

            return rs;
        } catch (OpenException ex1) {
            logger.error("查询比赛成绩信息失败!" + unitCode, ex1);
            callLog.setSuccess((byte) 0);
            callLog.setStatus(StringUtil.trimMaxStr(ex1.getMessage(), 32));
            callLog.setTotal(0);
            return JsonResultUtil.getFailResult(500, ex1.getMessage());
        } catch (Exception ex2) {
            logger.error("查询比赛成绩信息失败!" + unitCode, ex2);
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
