/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.permission;

import java.util.ArrayList;
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
import cn.evake.auth.dao.model.SysRolePermission;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.SysRoleMenuExt;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 角色controller
 * @author wang yi
 * @desc 
 * @version $Id: SysPermApiController.java, v 0.1 2018年7月3日 下午7:44:52 wang yi Exp $
 */
@RestController
@RequestMapping(value = "api/sys/permission", produces = "application/json")
@Api(value = "用户角色接口", tags = "系统角色管理")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class SysPermApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
    *
    * @return
    */
    @ApiOperation(value = "角色查询", notes = "用于角色所拥有的角色查询")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<List<SysRoleMenuExt>> getRolePermission(Long roleId,
                                                                 HttpServletRequest request) {
        ResultWrapper<List<SysRoleMenuExt>> resultWrapper = new ResultWrapper<List<SysRoleMenuExt>>();
        try {
            List<SysRoleMenuExt> pers = userService.getSysRoleMenuByRoleId(roleId);
            resultWrapper.setData(pers);
        } catch (Exception e) {
            logger.error("角色角色查询错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     *角色添加 
     * @return
     */
    @ApiOperation(value = "角色添加", notes = "用于角色添加")
    @ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "menuIds", value = " 目录(菜单)ID", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<Boolean> rolePermissionAdd(@RequestParam(required = true) Long roleId,
                                                    @RequestParam(required = true) String roleName,
                                                    @RequestParam(required = true) Long[] menuIds,
                                                    HttpServletRequest request) {
        UserInfoModel loginUser = userService.getUserModelChecked(request);
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            List<SysRolePermission> datas = new ArrayList<SysRolePermission>();
            for (Long menuId : menuIds) {
                SysRolePermission per = new SysRolePermission();
                per.setRoleId(roleId);
                per.setRoleName(roleName);
                per.setMenuId(menuId);
                per.setCreateUserName(loginUser.getUserName());
                per.setUuid(loginUser.getUuid());
                per.setGmtCreate(new Date());
                datas.add(per);
            }
            Boolean role = userService.addRolePermissions(datas);
            resultWrapper.setData(role);
        } catch (Exception e) {
            logger.error("角色添加错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "角色删除", notes = "用于角色删除")
    @ApiImplicitParams({ @ApiImplicitParam(name = "menuId", value = "菜单ID", required = true, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<String> rolePermissionDelete(@RequestParam(required = true) Long menuId,
                                                      @RequestParam(required = true) Long roleId,
                                                      HttpServletRequest request) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
        try {
            boolean fla = userService.removeRolePermission(roleId, menuId);
            if (!fla) {
                resultWrapper.setErrorMsg("删除失败");
            }
        } catch (Exception e) {
            logger.error("角色删除错误信息 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }
}
