/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.api.vo.SysInfoVo;
import cn.evake.auth.dao.model.SysBase;
import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.service.system.SysService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.CStringUtil;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author wang yi
 * @desc
 * @version $Id: SysManagerController.java, v 0.1 2018年2月26日 上午11:21:33 wang yi
 *          Exp $
 */
@RestController
@RequestMapping(value = "api/sys", produces = "application/json")
@Api(value = "系统配置理接口", tags = "系统配置管理")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class SysApiController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysService sysService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "系统基础信息查询", notes = "用于系统基础信息查询")
	@ApiImplicitParams({})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "info", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<SysInfoVo> getSystemInfo(Model model, HttpServletRequest request) {
		//
		ResultWrapper<SysInfoVo> resultWrapper = new ResultWrapper<SysInfoVo>();
		try {
			SysInfoVo sysInfoM = new SysInfoVo();
			SysBase sysBase = sysService.getSysBase();
			BeanUtils.copyProperties(sysBase, sysInfoM);
			resultWrapper.setData(sysInfoM);
		} catch (Exception e) {
			logger.error("信息错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		return resultWrapper;
	}

	@ApiOperation(value = "系统基础信息修改", notes = "用于系统基础信息修改")
	@ApiImplicitParams({})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "up", method = { RequestMethod.POST })
	@ResponseBody
	public ResultWrapper<SysInfoVo> upSystemInfo(@ModelAttribute SysInfoVo sysInfo, Model model,
			HttpServletRequest request) {
		ResultWrapper<SysInfoVo> resultWrapper = new ResultWrapper<SysInfoVo>();
		try {
			SysBase sysBase = new SysBase();
			BeanUtils.copyProperties(sysInfo, sysBase);
			UserInfoModel infoModel = userService.getUserModelChecked(request);
			sysBase.setLastUpUid(infoModel.getUuid());
			sysBase.setLastUname(infoModel.getRealName());
			sysService.upInsertSysBase(sysBase);
			resultWrapper.setData(sysInfo);
		} catch (Exception e) {
			logger.error("信息错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		return resultWrapper;
	}

	/**
	 * 子系统添加
	 * 
	 * @return
	 */
	@ApiOperation(value = "子系统添加", notes = "用于子系统添加")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysname", value = "系统名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "icon", value = "图标URL", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "host", value = "系统URL", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dispaly", value = "显示在主页(显示:1 不显示:0)", required = true, dataType = "int", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "add", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<SysInfo> sysAdd(@RequestParam(required = true) String sysname,
			@RequestParam(required = true) String icon, @RequestParam(required = true) String host,
			@RequestParam(required = true) Integer dispaly, Model model, HttpServletRequest request) {
		//
		SysInfo sysInfo = new SysInfo();
		sysInfo.setName(sysname);
		sysInfo.setIcon(icon);
		sysInfo.setHost(host);
		sysInfo.setDispaly(dispaly == 1 ? true : false);
		sysInfo.setGmtCreate(new Date());
		ResultWrapper<SysInfo> resultWrapper = new ResultWrapper<SysInfo>();
		try {
			UserInfoModel loginUser = userService.getUserModelChecked(request);
			sysInfo.setCreateUserName(loginUser.getUserName());
			SysInfo sys = sysService.addSys(sysInfo);
			if (sys != null) {
				resultWrapper.setData(sys);
			} else {
				resultWrapper.setErrorMsg("添加失败");
			}
		} catch (Exception e) {
			logger.error("子系统添加信息错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		logger.info("子系统详细信息--->:{}", JSON.toJSONString(sysInfo));
		return resultWrapper;
	}

	/**
	 * 子系统更新
	 * 
	 * @return
	 */
	@ApiOperation(value = "子系统更新", notes = "用于子系统更新")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysId", value = "系统ID", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "sysname", value = "系统名称", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "icon", value = "图标URL", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "host", value = "系统URL", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "dispaly", value = "显示在主页(显示:1 不显示:0)", required = false, dataType = "int", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "update", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<SysInfo> sysUpdate(@RequestParam(required = true) Long sysId, String sysname, String icon,
			String host, Integer dispaly, Model model, HttpServletRequest request) {
		SysInfo sysInfo = new SysInfo();
		sysInfo.setId(sysId);
		sysInfo.setName(CStringUtil.emptyToNull(sysname));
		sysInfo.setIcon(CStringUtil.emptyToNull(icon));
		sysInfo.setHost(CStringUtil.emptyToNull(host));
		if (dispaly != null) {
			sysInfo.setDispaly(dispaly == 1 ? true : false);
		}
		sysInfo.setGmtModifed(new Date());
		ResultWrapper<SysInfo> resultWrapper = new ResultWrapper<SysInfo>();
		try {
			sysInfo = sysService.updateSysById(sysInfo);
			if (sysInfo != null) {
				resultWrapper.setData(sysInfo);
			} else {
				resultWrapper.setErrorMsg("更新失败");
			}
		} catch (Exception e) {
			logger.error("子系统添加信息错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		logger.info("子系统详细信息--->:{}", JSON.toJSONString(sysInfo));
		return resultWrapper;
	}

	/**
	 * 子系统查询
	 * 
	 * @return
	 */
	@ApiOperation(value = "子系统查询", notes = "用于子系统查询")
	@ApiImplicitParams({})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "all", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<List<SysInfo>> sysAll(HttpServletRequest request) {
		ResultWrapper<List<SysInfo>> resultWrapper = new ResultWrapper<List<SysInfo>>();
		try {
			List<SysInfo> syss = sysService.getAllSys();
			if (syss != null) {
				resultWrapper.setData(syss);
			} else {
				resultWrapper.setErrorMsg("子系统信息获取失败");
			}
		} catch (Exception e) {
			logger.error("子系统信息获取错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		return resultWrapper;
	}

	/**
	 * 子系统添加
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "子系统删除", notes = "用于子系统删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysname", value = "系统名称", required = true, dataType = "String", paramType = "query"), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"), @ApiResponse(code = 500, message = "接口系统异常") })
	@RequestMapping(value = "delete", method = { RequestMethod.GET })
	@ResponseBody
	public ResultWrapper<String> sysDelete(@RequestParam(required = true) String sysname, HttpServletRequest request) {
		ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
		try {
			boolean fla = sysService.deleteSysWithName(sysname);
			if (fla) {
				resultWrapper.setData("删除成功");
			} else {
				resultWrapper.setErrorMsg("删除失败");
			}
		} catch (Exception e) {
			logger.error("子系统删除失败错误 ", e);
			resultWrapper.setErrorMsg(e.getMessage());
		}
		return resultWrapper;
	}

}
