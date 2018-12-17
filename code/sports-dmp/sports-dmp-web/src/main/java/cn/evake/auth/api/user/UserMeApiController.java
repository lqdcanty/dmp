/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.DiskUserVo;
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
 * 用戶信息操作controller
 * @author Evance
 * @version $Id: UserApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "api/user", produces = "application/json")
@Api(value = "系统个人信息管理", tags = "系统个人管理")
@Authority(value = AuthorityTypeEnum.NoValidate)
public class UserMeApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    //////////////////////////////////个人信息操作/////////////////////////////////////////
    @ApiOperation(value = "个人信息查询", notes = "用于查询自己信息")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "me/get", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> getMeUserInfo(HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            DiskUserVo user = new DiskUserVo();
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            BeanUtils.copyProperties(loginUser, user);
            resultWrapper.setData(user);
        } catch (Exception e) {
            logger.error("菜单查询信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "个人信息修改", notes = "用于修改自己基本信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "realName", value = "真实名称", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "nickName", value = "昵称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "email", value = "email(校验)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "avatar", value = "头像", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "phone", value = "电话(不能为中文)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "gender", value = "性别(男,女,未知)", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "me/update", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SysUser> upMeUserInfo(@RequestParam(required = false) String realName,
                                               @RequestParam(required = false) String nickName,
                                               @RequestParam(required = false) String email,
                                               @RequestParam(required = false) String avatar,
                                               @RequestParam(required = false) String phone,
                                               @RequestParam(required = false) String gender,
                                               HttpServletRequest request) {
        ResultWrapper<SysUser> resultWrapper = new ResultWrapper<SysUser>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            String uuid = loginUser.getUuid();
            SysUser sysUser = new SysUser();
            sysUser.setRealName(CStringUtil.emptyToNull(realName));
            sysUser.setNickName(CStringUtil.emptyToNull(nickName));
            sysUser.setEmail(CStringUtil.emptyToNull(email));
            sysUser.setAvatar(CStringUtil.emptyToNull(avatar));
            sysUser.setPhone(CStringUtil.emptyToNull(phone));
            sysUser.setGender(CStringUtil.emptyToNull(gender));
            /*  userService.assertUser(sysUser);*/
            SysUser newUser = userService.updateUserByUUid(uuid, sysUser);
            resultWrapper.setData(newUser);
        } catch (Exception e) {
            logger.error("更新用户信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "个人信息修改密码", notes = "用于自己密码")
    @ApiImplicitParams({ @ApiImplicitParam(name = "oldPass", value = "旧的密码", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "newPass", value = "新密码", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "me/update/pass", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> upMePass(@RequestParam(required = true) String oldPass,
                                           @RequestParam(required = true) String newPass,
                                           HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            String uuid = loginUser.getUuid();
            userService.updateUserPass(uuid, oldPass, newPass);
            resultWrapper.setData(true);
        } catch (Exception e) {
            logger.error("更新密码信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "个人信息检查密码", notes = "用于检查自己密码是否正确")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pass", value = "密码", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "me/pass/check", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> ckMePass(@RequestParam(required = true) String pass,
                                           HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            String uuid = loginUser.getUuid();
            resultWrapper.setData(userService.checkPassIsEnble(uuid, pass));
        } catch (Exception e) {
            logger.error("检查密码失败,信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

}
