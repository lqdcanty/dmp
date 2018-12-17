/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.annotation.LogActionTypeEnum;
import cn.evake.auth.constants.UserConstants;
import cn.evake.auth.dao.model.SysUserLog;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.user.UserInfoUtil;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 用户登陆controller
 * @author Evance
 * @version $Id: UserLoginController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "api/user", produces = "application/json")
@Api(value = "用户登录接口", tags = "系统用户登录")
public class UserLoginApiController {

    private Logger       logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService  userService;

    @Autowired
    private CacheService redisService;

    @Autowired
    private LogService   logService;

    /**
     * Ajax登录请求 
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登陆异步接口", notes = "用于用户登陆异步接口")
    @ApiImplicitParams({ @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "authCode", value = "验证码", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<Map<String, Object>> ajaxLogin(@RequestParam(required = true) String username,
                                                        @RequestParam(required = true) String password,
                                                        @RequestParam(required = true) String authCode,
                                                        Model model, HttpServletRequest req,
                                                        HttpServletResponse resp) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        UserInfoModel userModel = new UserInfoModel();
        String logAction = null;
        try {
            String sessionCode = (String) req.getSession().getAttribute("code");
            boolean fla = userService.checkAuthCode(sessionCode, authCode);
            if (!fla) {
                logger.info("用户:{} 输入验证码:{} 正确验证码:{} 比对结果不一致", username, authCode, sessionCode);
                throw new AuthBusException("验证码错误");
            }
            userModel = userService.userLogin(username, password);
            //用户认证成功用户信息放在缓存redis
            redisService.putObj(UserInfoUtil.generateCacheUserKey(userModel.getUuid()), userModel,
                UserConstants.REDIS_TIMEOUT);
            //更新用户最后登录时间
            userService.updateLastLogin(userModel.getUuid());
            //设置cookies
            List<Cookie> cookies = forMartUserCookies(req, userModel);
            for (Cookie cookie : cookies) {
                resp.addCookie(cookie);
            }
            //设置返回信息
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put(UserConstants.USER_TOKEN_KEY, userModel.getAuthToken());
            resultMap.put(UserConstants.USER_UUID, userModel.getAuthToken());
            resultMap.put(UserConstants.USER_NAME, userModel.getUserName());
            resultWrapper.setData(resultMap);
            logAction = String.format("用户:%s 登录系统成功", username);
        } catch (Exception e) {
            logger.error("登陆错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
            logAction = String.format("用户:%s 登录系统失败 原因:%s", username, e.getMessage());
        } finally {
            //记录登录日志
            SysUserLog userLog = new SysUserLog();
            userLog.setUuid(userModel.getUuid());
            userLog.setAction(logAction);
            userLog.setIp(req.getRemoteAddr());
            userLog.setBrowser(req.getHeader("User-Agent"));
            userLog.setUserName(username);
            userLog.setViewUrl(req.getRequestURI());
            userLog.setActionType(LogActionTypeEnum.LOGIN.getType());
            userLog.setGmtCreate(new Date());
            logService.addNewUserLog(userLog);
        }
        return resultWrapper;
    }

    /**
     * 批量写入系统Cookies
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param req
     * @param userModel 
     * @param resp
     * @return 
     */
    private List<Cookie> forMartUserCookies(HttpServletRequest req, UserInfoModel userModel) {
        List<Cookie> cookies = new ArrayList<Cookie>();
        //设置Cookis 
        String domainUrl = ServletUtil.getDomain(req);
        Cookie tokenCookie = new Cookie(UserConstants.USER_TOKEN_KEY, userModel.getAuthToken());
        tokenCookie.setPath("/");
        tokenCookie.setDomain(domainUrl);
        tokenCookie.setMaxAge(UserConstants.COOKIES_TIMEOUT);
        Cookie uidCookie = new Cookie(UserConstants.USER_UUID, userModel.getUuid());
        uidCookie.setPath("/");
        uidCookie.setDomain(domainUrl);
        uidCookie.setMaxAge(UserConstants.COOKIES_TIMEOUT);
        Cookie userName = new Cookie(UserConstants.USER_NAME, userModel.getUserName());
        userName.setPath("/");
        userName.setDomain(domainUrl);
        userName.setMaxAge(UserConstants.COOKIES_TIMEOUT);
        cookies.add(tokenCookie);
        cookies.add(uidCookie);
        cookies.add(userName);
        return cookies;
    }

    /**
     *  Ajax用户状态获取
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    @ApiOperation(value = "用户当前状态获取", notes = "用于查詢当前用户登录状态")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "status", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ResultWrapper<Boolean> getUserStatus(HttpServletRequest request,
                                                HttpServletResponse resp) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            if (userModel == null) {
                resultWrapper.setData(false);
                return resultWrapper;
            }
            resultWrapper.setData(true);
            return resultWrapper;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            resultWrapper.setData(false);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     *  Ajax退出登陆请求 
     * @param uuid
     * @return
     */
    @ApiOperation(value = "用户退出登陆异步接口", notes = "用于用户退出登陆异步")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<String> ajaxLoginOut(HttpServletRequest request,
                                              HttpServletResponse resp) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            logger.info("--用户:{} 异步调用异步退出接口---> 正在用户清除数据...", userModel.getUserName());
            userService.userLoginOut(request);
            //清除浏览器用户信息
            Cookie authToken = new Cookie(UserConstants.USER_TOKEN_KEY, userModel.getAuthToken());
            authToken.setMaxAge(0);
            resp.addCookie(authToken);
        } catch (Exception e) {
            logger.error("", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

}
