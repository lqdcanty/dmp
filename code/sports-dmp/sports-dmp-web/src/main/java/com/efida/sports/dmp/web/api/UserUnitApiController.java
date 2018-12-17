/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.user.UserUnitService;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 数据账户API
 * 
 * @author Evance
 * @version $Id: UnitApiController2.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp
 *          $
 */
@RestController
@RequestMapping(value = "dmp/api/user", produces = "application/json")
@Api(value = "用户承办方接口", tags = "数据中心-用户承办方管理")
@Authority(value = AuthorityTypeEnum.NoValidate)
public class UserUnitApiController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserUnitService userUnitService;

	@ApiOperation(value = "账户承办方数据", notes = "用于账户承办方数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "uid", value = "用户uid", required = false, dataType = "String", paramType = "query"), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "/unit", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<List<UserUnit>> getUnitList(@RequestParam(required = false) String uid,
			HttpServletRequest request) {
		ResultWrapper<List<UserUnit>> resultWrapper = new ResultWrapper<List<UserUnit>>();
		try {
			List<UserUnit> userUnit = userUnitService.getUserUnit(uid);
			resultWrapper.setData(userUnit);
		} catch (DmpBusException ex) {
			resultWrapper.setErrorMsg(ex.getMessage());
			logger.error("业务错误 ", ex);
		} catch (Exception ex) {
			logger.error("系统错误 ", ex);
			resultWrapper.setErrorMsg("系统错误");
		}
		return resultWrapper;
	}

	@ApiOperation(value = "用户关联承办方数据更新", notes = "用于用户关联承办方数据更新")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "uid", value = "用户uid", required = true, dataType = "String", paramType = "query"), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "/up", method = { RequestMethod.POST })
	@ResponseBody
	public ResultWrapper<List<UserUnit>> lockUnit(@RequestParam(required = true) String uid,
			@RequestBody List<UserUnit> unitCodes, HttpServletRequest request) {
		ResultWrapper<List<UserUnit>> resultWrapper = new ResultWrapper<List<UserUnit>>();
		try {
			List<UserUnit> userUnit = userUnitService.upAndInsertUserUnit(uid, unitCodes);
			resultWrapper.setData(userUnit);
		} catch (DmpBusException ex) {
			resultWrapper.setErrorMsg(ex.getMessage());
			logger.error("业务错误 ", ex);
		} catch (Exception ex) {
			logger.error("系统错误 ", ex);
			resultWrapper.setErrorMsg("系统错误");
		}
		return resultWrapper;
	}

}
