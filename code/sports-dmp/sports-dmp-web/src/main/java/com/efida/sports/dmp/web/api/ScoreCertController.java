/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sports.dmp.biz.score.SyncCertService;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.utils.JsonResultUtil;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author zhiyang
 * @version $Id: ScoreCertController.java, v 0.1 2018年8月7日 下午8:08:48 zhiyang Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/scoreCert", produces = "application/json")
@Api(value = "成绩证书接口", tags = "成绩系统-证书管理")
@Authority(value = AuthorityTypeEnum.NoValidate)
public class ScoreCertController {

    private Logger          logger        = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService     userService;

    @Autowired
    private SyncCertService syncCertService;

    private String          generPicPermt = "dmp-cert-ganerge-pic";

    @ApiOperation(value = "生成成绩证书信息", notes = "生成成绩证书")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "成绩ID", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "generate", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> generateCertByID(@RequestParam(required = false) Long id,
                                                HttpServletRequest request) {
        try {
            Boolean checkUserLimit = userService.checkUserLimit(request, generPicPermt);
            if (checkUserLimit == false) {
                return JsonResultUtil.getFailResult("500", "您暂无该权限!");
            }
            String str = syncCertService.syncOneScoreById(id);
            return JsonResultUtil.getSuccessResult(str);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @ApiOperation(value = "生成成绩证书信息(批量)", notes = "生成成绩证书(批量)")
    @ApiImplicitParams({ @ApiImplicitParam(name = "genpicType", value = "证书类型(day:天 page:当前页 all:所有数据)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "day", value = "日期", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "scoreIds", value = "成绩ID", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "generate/bat", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> generateCertBtt(@RequestParam(required = true) String genpicType,
                                               @RequestParam(required = false) String day,
                                               @RequestParam(required = false) List<Long> scoreIds,
                                               HttpServletRequest request) {
        try {
            Boolean checkUserLimit = userService.checkUserLimit(request, generPicPermt);
            if (checkUserLimit == false) {
                return JsonResultUtil.getFailResult("500", "您暂无该权限!");
            }

            if (genpicType.equals("day")) {
                if (day == null) {
                    return JsonResultUtil.getFailResult("500", "时间不允许为空");
                } else {
                    syncCertService.generateCertByScoreCreateDay(new Date());
                }
            }

            if (genpicType.equals("all")) {
                syncCertService.generateCertByAllScore();
            }

            if (genpicType.equals("page")) {
                syncCertService.generateCertByScoreIds(scoreIds);
            }
            return JsonResultUtil.getSuccessResult();
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @ApiOperation(value = "获取成绩证书信息", notes = "获取成绩证书")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "成绩ID", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "pic", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Map<String, Object>> getCertPicByID(@RequestParam(required = false) Long id,
                                                             HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            ScoreCertEntity scorePic = syncCertService.getCertPicBySoreId(id);
            if (scorePic == null) {
                throw new DmpBusException("证书未生成!");
            }
            if (!scorePic.getIsPicOk() || StringUtils.isBlank(scorePic.getCertPicUrl())) {
                throw new DmpBusException("证书正在生成中,请耐心等待!");
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("pic", scorePic.getCertPicUrl());
            resultWrapper.setData(result);
        } catch (BusinessException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (DmpBusException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "发送短信信息", notes = "发送短信信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "成绩ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "ims", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> sendMsgByID(@RequestParam(required = true) Long id,
                                              HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        boolean sendMsgScoreCert = false;
        try {
            ScoreCertEntity scoreCert = syncCertService.getCertEntityById(id);
            sendMsgScoreCert = syncCertService.sendMsgScoreCert(scoreCert);
            resultWrapper.setData(sendMsgScoreCert);
        } catch (BusinessException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (DmpBusException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "重复发送短信信息", notes = "重复发送短信信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "成绩ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "ims/replay", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> replaySendMsgByID(@RequestParam(required = true) Long id,
                                                    HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        boolean sendMsgScoreCert = false;
        try {
            ScoreCertEntity scoreCert = syncCertService.getCertEntityById(id);
            sendMsgScoreCert = syncCertService.sendReplayMsgScoreCert(scoreCert);
            resultWrapper.setData(sendMsgScoreCert);
        } catch (BusinessException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (DmpBusException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "证书列表信息", notes = "获取成绩证书列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "playerPhone", value = "运动员电话号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "certSn", value = "证书编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "certName", value = "姓名", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "isSmsSend", value = "是否已经发短信。1.已下发。 0.未下发", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "wait_check:需要人工介入修正；waitPic:等待证书, normal:证书正常", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "cert/pic", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<ScoreCertEntity>> getCertPics(@RequestParam(required = false) String playerPhone,
                                                                    @RequestParam(required = false) String certSn,
                                                                    @RequestParam(required = false) String certName,
                                                                    @RequestParam(required = false) Integer isSmsSend,
                                                                    @RequestParam(required = false) String status,
                                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                    HttpServletRequest request) {
        ResultWrapper<PagingResult<ScoreCertEntity>> resultWrapper = new ResultWrapper<PagingResult<ScoreCertEntity>>();
        try {
            PagingResult<ScoreCertEntity> scorePic = syncCertService.getPageCertEntity(playerPhone,
                certSn, certName, isSmsSend, status, pageNumber, pageSize);
            resultWrapper.setData(scorePic);
        } catch (BusinessException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (DmpBusException ex) {
            logger.error("业务异常", ex);
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
