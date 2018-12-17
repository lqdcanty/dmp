/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.app.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.evake.app.dao.model.AppInfo;
import cn.evake.app.service.AppEmServcie;
import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 应用系统管理controller
 * @author wang yi
 * @desc 
 * @version $Id: AppManagerController.java, v 0.1 2018年9月21日 上午11:19:26 wang yi Exp $
 */
@RestController
@RequestMapping(value = "api/eapp", produces = "application/json")
@Api(value = "应用系统管理接口", tags = "应用系统系统-应用系统管理")
public class AppManagerController {

    private Logger       logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppEmServcie appEService;

    @Autowired
    private UserService  userService;

    @Autowired
    private LogService   logerService;

    /**
     * 应用系统获取
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    @ApiOperation(value = "应用系统获取", notes = "用于查询应用系统获取")
    @ApiImplicitParams({ @ApiImplicitParam(name = "app", value = "(系统)系统名/系统编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "user", value = "(负责人)账号/名称", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<List<AppInfo>> getEapps(HttpServletRequest request,
                                                 HttpServletResponse resp,
                                                 @RequestParam(value = "app", required = false) String app,
                                                 @RequestParam(value = "user", required = false) String user) {
        ResultWrapper<List<AppInfo>> resultWrapper = new ResultWrapper<List<AppInfo>>();
        try {
            List<AppInfo> apps = appEService.getAppsKeyword(app, user);
            resultWrapper.setData(apps);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "系统编号检查", notes = "用于系统编号是否被使用")
    @ApiImplicitParams({ @ApiImplicitParam(name = "appCode", value = "系统编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "check/appCode", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<Boolean> checkAppCode(HttpServletRequest request, HttpServletResponse resp,
                                               @RequestParam(name = "appCode") String appCode) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            boolean isUseed = appEService.checkAppCodeUsed(appCode);
            resultWrapper.setData(isUseed);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "应用系统获取", notes = "用于查询应用系统获取")
    @ApiImplicitParams({ @ApiImplicitParam(name = "appCode", value = "系统编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "get", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<AppInfo> getEapp(HttpServletRequest request, HttpServletResponse resp,
                                          @RequestParam(name = "appCode") String appCode) {
        ResultWrapper<AppInfo> resultWrapper = new ResultWrapper<AppInfo>();
        try {
            AppInfo appInfo = appEService.getAppInfo(appCode);
            resultWrapper.setData(appInfo);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "应用系统添加", notes = "用于应用系统添加")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = { RequestMethod.POST })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<AppInfo> addEapp(HttpServletRequest request, HttpServletResponse resp,
                                          @RequestBody AppInfo appInfo) {
        ResultWrapper<AppInfo> resultWrapper = new ResultWrapper<AppInfo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            appInfo.setLastModifyTime(new Date());
            appInfo.setGmtModified(new Date());
            appInfo.setGmtCreate(new Date());
            appInfo.setLastModifyUser(userModel.getUserName());
            appInfo.setCreateUser(userModel.getUserName());
            AppInfo app = appEService.addAppInfo(appInfo);
            resultWrapper.setData(app);
            String userLog = String.format("用户:%s 添加系统应用  应用编号:%s   应用名:%s 应用信息:%s",
                userModel.getUserName(), appInfo.getAppCode(), appInfo.getAppName(),
                JSON.toJSONString(appInfo));
            logger.info(userLog);
            logerService.addUserOperateLog(request, userLog);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "应用系统修改", notes = "用于应用系统修改(通过数据标识)")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "up", method = { RequestMethod.POST })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<AppInfo> upEapp(HttpServletRequest request, HttpServletResponse resp,
                                         @RequestBody AppInfo appInfo) {
        ResultWrapper<AppInfo> resultWrapper = new ResultWrapper<AppInfo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            appInfo.setLastModifyTime(new Date());
            appInfo.setGmtModified(new Date());
            appInfo.setLastModifyUser(userModel.getUserName());
            AppInfo nApp = appEService.upAppInfo(appInfo);
            resultWrapper.setData(nApp);
            String userLog = String.format("用户:%s 修改系统应用  应用编号:%s   应用名:%s  应用信息:%s",
                userModel.getUserName(), appInfo.getAppCode(), appInfo.getAppName(),
                JSON.toJSONString(appInfo));
            logger.info(userLog);
            logerService.addUserOperateLog(request, userLog);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "应用系统删除", notes = "用于应用删除")
    @ApiImplicitParams({ @ApiImplicitParam(name = "appCode", value = "系统编号", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "del", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<Boolean> delEapp(HttpServletRequest request, HttpServletResponse resp,
                                          @RequestParam(name = "appCode") String appCode) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            boolean fla = appEService.delAppInfo(appCode);
            resultWrapper.setData(fla);
            String userLog = String.format("用户:%s  删除系统应用  应用编号:%s", userModel.getUserName(),
                appCode);
            logger.info(userLog);
            logerService.addUserOperateLog(request, userLog);
            return resultWrapper;
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }
}
