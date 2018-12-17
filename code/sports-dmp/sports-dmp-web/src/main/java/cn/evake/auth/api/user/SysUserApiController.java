/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import cn.evake.auth.constants.UserConstants;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.enmus.MenuTypeEnmu;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.DiskUserVo;
import cn.evake.auth.usermodel.MenuExt;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.CStringUtil;
import cn.evake.auth.util.SecretUtil;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.UucodeUtil;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 用戶信息操作controller
 * 
 * @author Evance
 * @version $Id: UserApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "api/user", produces = "application/json")
@Api(value = "用户管理接口", tags = "系统用户管理")
@Authority(value = AuthorityTypeEnum.NoValidate)
public class SysUserApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户信息获取", notes = "用于查詢用户信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "uuid", value = "用户唯一标识", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "get", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> getMeUserInfo(String uuid, HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            DiskUserVo diskUserVo = DiskUserVo.getDiskUserVo(userService.getUserByUuid(uuid));
            diskUserVo.setRoles(userService.getSysUserRoleByUuid(uuid));
            resultWrapper.setData(diskUserVo);
        } catch (Exception e) {
            logger.error("用户信息获取失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户列表", notes = "用于用户列表查询")
    @ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字(支持 用户名 用户真实名 手机号 邮箱 等查询)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "role", value = "角色", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "size", value = "数量", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<DiskUserVo>> sysUserAdd(@RequestParam(name = "keyword", required = false) String keyword,
                                                              @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                                              @RequestParam(name = "role", required = false) String 角色,
                                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                              HttpServletRequest request) {
        ResultWrapper<PagingResult<DiskUserVo>> resultWrapper = new ResultWrapper<PagingResult<DiskUserVo>>();
        try {
            PagingResult<SysUser> pageUsers = userService.getAllUserInofs(keyword, pageNumber,
                size);
            resultWrapper
                .setData(new PagingResult<DiskUserVo>(
                    DiskUserVo.getDiskUserVo(pageUsers.getList(),
                        userService
                            .getSysUserRoleByUuids(DiskUserVo.getUUids(pageUsers.getList()))),
                    pageUsers.getAllRow(), pageUsers.getCurrentPage(), pageUsers.getPageSize()));
        } catch (Exception e) {
            logger.error("查询失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户添加", notes = "用于用户添加")
    @ApiImplicitParams({ @ApiImplicitParam(name = "userName", value = "用户名(不可为中文 用于登录 校验唯一)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "realName", value = "真实名称", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "nickName", value = "昵称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "password", value = "密码(不小于6位数)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "email", value = "email(校验)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "avatar", value = "头像(不上传则后台默认)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "phone", value = "电话(不能为中文)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "gender", value = "性别(男,女,未知)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态(1 正常 0 已禁用)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "roles", value = "角色ID(用,分割)", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "remark", value = "备注(60字符以内)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "identity", value = "身份", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> sysUserAdd(@RequestParam(required = true) String userName,
                                                @RequestParam(required = true) String password,
                                                @RequestParam(required = true) String realName,
                                                @RequestParam(required = false) String nickName,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) String avatar,
                                                @RequestParam(required = true) String phone,
                                                @RequestParam(required = true) String gender,
                                                @RequestParam(required = false, defaultValue = "1") String status,
                                                @RequestParam(required = true) Long[] roles,
                                                @RequestParam(required = false) String remark,
                                                @RequestParam(required = false) String identity,
                                                HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(userName);
            sysUser.setNickName(nickName);
            sysUser.setRealName(realName);
            sysUser.setUuid(UucodeUtil.getUucode(UserConstants.USER_PREFIX));
            sysUser.setPassword(SecretUtil.getMD5(password));
            sysUser.setEmail(email);
            sysUser.setAvatar(StringUtils.isEmpty(avatar) ? UserConstants.defaultAvatar : avatar);
            sysUser.setPhone(phone);
            sysUser.setGender(gender);
            sysUser.setStatus(status);
            sysUser.setIdentity(identity);
            sysUser.setRemark(remark);
            sysUser.setGmtCreate(new Date());
            userService.assertUser(sysUser);
            DiskUserVo diskUserVo = DiskUserVo
                .getDiskUserVo(userService.createUser(sysUser, roles));
            diskUserVo.setRoles(userService.getSysUserRoleByUuid(diskUserVo.getUuid()));
            resultWrapper.setData(diskUserVo);
        } catch (Exception e) {
            logger.error("用户创建失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户修改", notes = "用于用户修改")
    @ApiImplicitParams({ @ApiImplicitParam(name = "uuid", value = "用户唯一标识", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "realName", value = "真实名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "nickName", value = "昵称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "email", value = "email(校验)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "avatar", value = "头像(不上传则后台默认)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "phone", value = "电话(不能为中文)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "gender", value = "性别(男,女,未知)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "roles", value = "角色ID(用,分割)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态(1 正常 0 已禁用)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "remark", value = "备注(60字符以内)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "identity", value = "身份", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "update", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> sysUserUpdate(@RequestParam(required = true) String uuid,
                                                   @RequestParam(required = false) String realName,
                                                   @RequestParam(required = false) String nickName,
                                                   @RequestParam(required = false) String email,
                                                   @RequestParam(required = false) String avatar,
                                                   @RequestParam(required = false) String phone,
                                                   @RequestParam(required = false) String gender,
                                                   @RequestParam(required = false) Long[] roles,
                                                   @RequestParam(required = false) String status,
                                                   @RequestParam(required = false) String remark,
                                                   @RequestParam(required = false) String identity,
                                                   HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setEmail(CStringUtil.emptyToNull(email));
            sysUser.setNickName(CStringUtil.emptyToNull(nickName));
            sysUser.setRealName(CStringUtil.emptyToNull(realName));
            sysUser.setAvatar(CStringUtil.emptyToNull(avatar));
            sysUser.setPhone(CStringUtil.emptyToNull(phone));
            sysUser.setGender(CStringUtil.emptyToNull(gender));
            sysUser.setStatus(CStringUtil.emptyToNull(status));
            sysUser.setIdentity(CStringUtil.emptyToNull(identity));
            sysUser.setRemark(CStringUtil.emptyToNull(remark));
            sysUser.setGmtModified(new Date());
            sysUser = userService.updateUserByUUid(uuid, sysUser);
            userService.checkAndupUserRoleByUuid(uuid, roles);
            if (sysUser != null) {
                DiskUserVo diskUserVo = new DiskUserVo();
                BeanUtils.copyProperties(sysUser, diskUserVo);
                resultWrapper.setData(diskUserVo);
            }
        } catch (Exception e) {
            logger.error("用户修改失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户锁定", notes = "用于用户锁定")
    @ApiImplicitParams({ @ApiImplicitParam(name = "uuid", value = "用户唯一标识", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态(1 正常 0 禁用)", required = false, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "lock", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DiskUserVo> sysUserUpdate(@RequestParam(required = true) String uuid,
                                                   @RequestParam(required = true) String status,
                                                   HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            SysUser sysUser = new SysUser();
            sysUser.setStatus(status);
            sysUser.setGmtModified(new Date());
            sysUser = userService.updateUserByUUid(uuid, sysUser);
            if (sysUser != null) {
                DiskUserVo diskUserVo = new DiskUserVo();
                BeanUtils.copyProperties(sysUser, diskUserVo);
                resultWrapper.setData(diskUserVo);
            }
        } catch (Exception e) {
            logger.error("用户锁定失败 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "用户修改密码", notes = "用于修改密码")
    @ApiImplicitParams({ @ApiImplicitParam(name = "uuid", value = "用户唯一标识", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "update/pass", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<DiskUserVo> upUserPass(@RequestParam(required = true) String uuid,
                                                @RequestParam(required = true) String password,
                                                HttpServletRequest request) {
        ResultWrapper<DiskUserVo> resultWrapper = new ResultWrapper<DiskUserVo>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            logger.info("用户:{} 修改uuid:{} 的密码为：{}", loginUser.getUserName(), uuid, password);
            userService.updateUserPass(uuid, password);
        } catch (Exception e) {
            logger.error("用户修改密码失败 ", e);
            resultWrapper.setErrorMsg("修改密码失败");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "查詢用户所有具备权限的菜单", notes = "用于查詢用户所有具备权限的菜单")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/all", method = { RequestMethod.GET })
    @ResponseBody
    @Authority(permissionCode = "14")
    public ResultWrapper<Map<String, Object>> sysMenuAll(HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            Map<String, Object> result = new HashMap<>();
            result.put("sysMenuTree", loginUser.getSysMenuTree());
            result.put("sys", loginUser.getSys());
            resultWrapper.setData(result);
        } catch (Exception e) {
            logger.error("菜单查询信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "查询系统系统菜单", notes = "用于查询系统系统菜单")
    @ApiImplicitParams({ @ApiImplicitParam(name = "sysId", value = "系统ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<MenuExt>> getSysMenu(@RequestParam(value = "sysId", required = true) Long sysId,
                                                   HttpServletRequest request) {
        ResultWrapper<List<MenuExt>> resultWrapper = new ResultWrapper<List<MenuExt>>();
        try {
            UserInfoModel loginUser = userService.getUserModelChecked(request);
            List<SysMenu> menus = UserInfoModel.getFormatURl(ServletUtil.getCtx(request),
                UserInfoModel.getSysMenu(sysId, loginUser.getSysMenu()));
            List<MenuExt> sortMenus = UserInfoModel
                .getSortMenus(UserInfoModel.getSysMenuTree(menus));
            //处理权限数据
            ArrayList<MenuExt> newMenus = new ArrayList<MenuExt>();
            for (MenuExt menuExt : sortMenus) {
                if (!menuExt.getType().equals(MenuTypeEnmu.permission.getCode())) {
                    newMenus.add(menuExt);
                }
            }
            resultWrapper.setData(newMenus);
        } catch (Exception e) {
            logger.error("查询系统菜单错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

}
