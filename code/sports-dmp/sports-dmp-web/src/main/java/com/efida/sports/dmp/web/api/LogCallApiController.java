/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

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

import com.efida.sports.dmp.biz.open.CallLogService;
import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.dao.entity.OpenCallLogEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 数据中心日志信息
 * @author Evance
 * @version $Id: PlayerApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/call", produces = "application/json")
@Api(value = "日志信息接口", tags = "数据中心-日志信息管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class LogCallApiController {

    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService    userService;

    @Autowired
    private CallLogService callLogSerivcie;

    @Authority(permissionCode = "MjAxODA2MjIxNjUxMTc=")
    @ApiOperation(value = "调用日志信息", notes = "用于调用查询调用日志信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "success", value = "状态", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "请求开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "请求结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/log/list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<OpenCallLogEntity> getCallLogList(@RequestParam(required = false) String unitCode,
                                                         @RequestParam(required = false) Integer success,
                                                         @RequestParam(required = false) String startTime,
                                                         @RequestParam(required = false) String endTime,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int limit,
                                                         HttpServletRequest request) {
        LayerPagevo<OpenCallLogEntity> resultWrapper = new LayerPagevo<OpenCallLogEntity>();
        try {
            PagingResult<OpenCallLogEntity> playerModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            if (!allowData && StringUtils.isNotBlank(unitCode)
                && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }
            if (!allowData) {
                unitCode = userInfo.getUserName();
                resultWrapper.setUnitCode(unitCode);
            }
            playerModels = callLogSerivcie.selectPage(unitCode, success, startTime, endTime, page,
                limit);
            resultWrapper.setData(playerModels.getList());
            resultWrapper.setCount(playerModels.getAllRow());
        } catch (DmpBusException ex) {
            resultWrapper.setMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA2MjIxNjUxMTc=")
    @ApiOperation(value = "调用日志详细", notes = "用于调用查询调用日志详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "callLogId", value = "日志ID", required = false, dataType = "long", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/log/detail", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<OpenCallLogEntity> getCallLogDetail(@RequestParam(required = true) Long callLogId,
                                                             HttpServletRequest request) {
        ResultWrapper<OpenCallLogEntity> resultWrapper = new ResultWrapper<OpenCallLogEntity>();
        try {
            String unitCode = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //查询详情权限
            Boolean isAllowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            if (!isAllowData) {
                unitCode = userInfo.getUserName();
            }
            resultWrapper.setData(callLogSerivcie.selectByIdAndUnitCode(callLogId, unitCode));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }
}
