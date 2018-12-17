/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.dao.model.SysRole;
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
 * 权限操作
 * @author Evance
 * @version $Id: SysUserApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "api/sys/role", produces = "application/json")
@Api(value = "用户角色接口", tags = "系统角色管理")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class SysUserRoleApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     *用户角色添加 
     * @return
     */
    @ApiOperation(value = "角色添加", notes = "用于角色添加")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleName", value = "角色名", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态 正常：normal 锁定：lock", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SysRole> roleAdd(@RequestParam(required = true) String roleName,
                                          @RequestParam(required = true) String status,
                                          HttpServletRequest request) {
        UserInfoModel loginUser = userService.getUserModelChecked(request);
        ResultWrapper<SysRole> resultWrapper = new ResultWrapper<SysRole>();
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setUuid(loginUser.getUuid());
        sysRole.setStatus(status);
        sysRole.setCreateUserName(loginUser.getUserName());
        sysRole.setGmtCreate(new Date());
        try {
            SysRole role = userService.addRole(sysRole);
            if (role == null) {
                resultWrapper.setData(role);
            }
        } catch (Exception e) {
            logger.error("角色添加错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     *用户角色添加 
     * @return
     */
    @ApiOperation(value = "角色修改", notes = "用于角色修改")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "roleName", value = "角色名", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态 正常：normal 锁定：lock", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "update", method = { RequestMethod.GET, })
    @ResponseBody
    public ResultWrapper<SysRole> roleUpdate(@RequestParam(required = true) Long roleId,
                                             @RequestParam(required = true) String roleName,
                                             @RequestParam(required = true) String status,
                                             HttpServletRequest request) {
        ResultWrapper<SysRole> resultWrapper = new ResultWrapper<SysRole>();
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setRoleName(CStringUtil.emptyToNull(roleName));
        sysRole.setStatus(CStringUtil.emptyToNull(status));
        sysRole.setGmtModified(new Date());
        try {
            SysRole role = userService.updateRole(sysRole);
            if (role == null) {
                resultWrapper.setData(role);
            }
        } catch (Exception e) {
            logger.error("角色更新错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     *用户角色锁定 
     * @return
     */
    @ApiOperation(value = "角色锁定", notes = "用于角色锁定")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "status", value = "状态 正常：normal 锁定：lock", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "lock", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SysRole> roleUpdate(@RequestParam(required = true) Long roleId,
                                             @RequestParam(required = true) String status,
                                             HttpServletRequest request) {
        ResultWrapper<SysRole> resultWrapper = new ResultWrapper<SysRole>();
        SysRole sysRole = new SysRole();
        sysRole.setId(roleId);
        sysRole.setStatus(status);
        sysRole.setGmtModified(new Date());
        try {
            SysRole role = userService.updateRole(sysRole);
            if (role == null) {
                resultWrapper.setData(role);
            }
        } catch (Exception e) {
            logger.error("角色更新错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     *用户角色删除
     * @return
     */
    @ApiOperation(value = "角色删除", notes = "用于角色删除")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<String> roleDelete(Long roleId, HttpServletRequest request) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
        try {
            boolean fla = userService.removeRoleById(roleId);
            if (!fla) {
                resultWrapper.setErrorMsg("删除失败");
            }
        } catch (Exception e) {
            logger.error("角色删除错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "角色查询", notes = "用于角色查询")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<List<SysRole>> roleAll(HttpServletRequest request) {
        ResultWrapper<List<SysRole>> resultWrapper = new ResultWrapper<List<SysRole>>();
        try {
            List<SysRole> roles = userService.getAllRole();
            if (roles != null) {
                resultWrapper.setData(roles);
            } else {
                resultWrapper.setErrorMsg("查询失败");
            }
        } catch (Exception e) {
            logger.error("查询所有角色信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

}
