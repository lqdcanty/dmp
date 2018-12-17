/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSON;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.service.system.SysService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.MenuExt;
import cn.evake.auth.usermodel.SysMenuExt;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.UucodeUtil;
import cn.evake.auth.web.vo.DisSysMenuVo;
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
 * @version $Id: SysManagerController.java, v 0.1 2018年2月26日 上午11:21:33 wang yi Exp $
 */
@RestController
@RequestMapping(value = "api/sys", produces = "application/json")
@Api(value = "系统菜单权限接口", tags = "系统菜单权限")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class SysMenuPermController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysService  sysService;

    @Autowired
    private UserService userService;

    /**
     * 子系统菜单添加
     * @return
     */
    @ApiOperation(value = "系统菜单添加", notes = "用于系统菜单添")
    @ApiImplicitParams({ @ApiImplicitParam(name = "sysId", value = "系统id", required = true, dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "menuName", value = "菜单名称", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "menuUrl", value = "菜单URl", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "type", value = "类型(''(目录)url(链接);button(按钮))", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "permissionCode", value = "权限码", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "isSelfSys", value = "是否内部系统：1.是，0.否", required = true, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "newWindow", value = "新打开窗口(1:新窗口打开0当前框架打开)", required = true, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "isOpen", value = "是否展开1:展开 0:不展开", required = true, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "parentId", value = "父级ID", required = false, dataType = "Long", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/add", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SysMenu> sysMenuAdd(@RequestParam(required = true) Long sysId,
                                             @RequestParam(required = true) String menuName,
                                             @RequestParam(required = true) String menuUrl,
                                             @RequestParam(required = true) String type,
                                             @RequestParam(required = false) String permissionCode,
                                             @RequestParam(required = true) Integer isSelfSys,
                                             @RequestParam(required = true) Integer newWindow,
                                             @RequestParam(required = true) Integer isOpen,
                                             Long parentId, HttpServletRequest request) {
        ResultWrapper<SysMenu> resultWrapper = new ResultWrapper<SysMenu>();
        try {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setSysId(sysId);
            sysMenu.setMenuName(menuName);
            sysMenu.setMenuUrl(menuUrl);
            sysMenu.setType(type);
            sysMenu.setIsSelfSys(isSelfSys == 1);
            sysMenu.setNewWindow(newWindow == 1);
            sysMenu.setIsOpen(isOpen == 1);
            sysMenu.setStatus(true);
            sysMenu.setParentId(parentId);
            sysMenu.setPermissionCode(
                StringUtils.isBlank(permissionCode) ? UucodeUtil.getUxCode() : permissionCode);
            sysMenu.setSupportMobile(false);
            sysMenu.setGmtCreate(new Date());
            SysMenu sys = sysService.addSysMenu(sysMenu);
            logger.info("子系统菜单详细信息--->:{}", JSON.toJSONString(sysMenu));
            if (sys != null) {
                resultWrapper.setData(sys);
            } else {
                resultWrapper.setErrorMsg("添加失败");
            }
        } catch (Exception e) {
            logger.error("子系统菜单添加信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 子系统菜单更新
     * @return
     */
    @ApiOperation(value = "系统菜单更新", notes = "用于系统菜单更新")
    @ApiImplicitParams({ @ApiImplicitParam(name = "sysMenuId", value = "系统菜单id", required = true, dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "sysId", value = "系统id", required = false, dataType = "Long", paramType = "query"),
                         @ApiImplicitParam(name = "menuName", value = "菜单名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "menuUrl", value = "菜单URl", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "type", value = "类型(''(目录)url(链接);button(按钮))", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "permissionCode", value = "权限码", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "isSelfSys", value = "是否内部系统：1.是，0.否", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "newWindow", value = "新打开窗口(1:新窗口打开0当前框架打开)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "isOpen", value = "是否展开1:展开 0:不展开", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "parentId", value = "父级ID", required = false, dataType = "Long", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/update", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SysMenu> sysMenuUpdate(@RequestParam(required = true) Long sysMenuId,
                                                Long sysId, String menuName, String menuUrl,
                                                String type, String permissionCode,
                                                Integer isSelfSys, Integer newWindow,
                                                Integer isOpen, Long parentId,
                                                HttpServletRequest request) {
        ResultWrapper<SysMenu> resultWrapper = new ResultWrapper<SysMenu>();
        try {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(sysMenuId);
            sysMenu.setSysId(sysId);
            sysMenu.setMenuName(menuName);
            sysMenu.setMenuUrl(menuUrl);
            sysMenu.setType(type);
            sysMenu.setPermissionCode(permissionCode);
            sysMenu.setIsSelfSys(isSelfSys == 1 ? true : false);
            sysMenu.setNewWindow(newWindow == 1 ? true : false);
            sysMenu.setIsOpen(isOpen == 1 ? true : false);
            sysMenu.setParentId(parentId);
            sysMenu.setSupportMobile(false);
            SysMenu sys = sysService.updateSysMenu(sysMenu);
            if (sys != null) {
                resultWrapper.setData(sys);
            } else {
                resultWrapper.setErrorMsg("更新失败");
            }
        } catch (Exception e) {
            logger.error("子系统菜单添加信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 系统菜单删除
     * @return
     */
    @ApiOperation(value = "系统菜单删除", notes = "用于系统菜单删除")
    @ApiImplicitParams({ @ApiImplicitParam(name = "menuId", value = "系统id", required = true, dataType = "Long", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/delete", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<String> sysMenuDelete(@RequestParam(required = true) Long menuId,
                                               HttpServletRequest request) {
        ResultWrapper<String> resultWrapper = new ResultWrapper<String>();
        try {
            boolean fla = sysService.deleteSysMenu(menuId);
            if (fla) {
                resultWrapper.setData("删除成功");
            } else {
                resultWrapper.setErrorMsg("删除失败");
            }
        } catch (Exception e) {
            logger.error("子系统菜单删除信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 系统菜单信息
     * @return
     */
    @ApiOperation(value = "系统菜单信息", notes = "用于系统菜单信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "menuId", value = "系统id", required = true, dataType = "Long", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/info", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<DisSysMenuVo> getSysMenuInfote(@RequestParam(required = true) Long menuId,
                                                        HttpServletRequest request) {
        ResultWrapper<DisSysMenuVo> resultWrapper = new ResultWrapper<DisSysMenuVo>();
        try {
            SysMenu thisMenu = sysService.getSysMenuByMenuId(menuId);
            SysMenu parent = sysService.getSysMenuByMenuId(thisMenu.getParentId());
            DisSysMenuVo data = DisSysMenuVo.getDisSysMenuVo(parent, thisMenu);
            resultWrapper.setData(data);
        } catch (Exception e) {
            logger.error("子系统菜单信息获取错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 系统菜单查詢
     * @return
     */
    @ApiOperation(value = "系统菜单查詢", notes = "用于系统菜单查詢")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/all", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<SysMenu>> sysMenuAll(HttpServletRequest request) {
        ResultWrapper<List<SysMenu>> resultWrapper = new ResultWrapper<List<SysMenu>>();
        try {
            List<SysMenu> sys = sysService.getAllSysMenu();
            if (sys != null) {
                resultWrapper.setData(sys);
            } else {
                resultWrapper.setErrorMsg("查询失败");
            }
        } catch (Exception e) {
            logger.error("子系统菜单查询信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    @ApiOperation(value = "我的系统菜单(移动端)查詢", notes = "用于获取我的系统菜单(移动端)查詢")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/moblie/menu/all", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<SysMenu>> sysMobileMenuAll(HttpServletRequest request) {
        ResultWrapper<List<SysMenu>> resultWrapper = new ResultWrapper<List<SysMenu>>();
        try {
            List<SysMenu> resultMenus = new ArrayList<SysMenu>();
            UserInfoModel userModel = userService.getUserModelChecked(request);
            List<SysMenu> sys = userModel.getSysMenu();
            if (sys != null) {
                for (SysMenu sysMenu : sys) {
                    if (sysMenu.getSupportMobile() != null && sysMenu.getSupportMobile()) {
                        resultMenus.add(sysMenu);
                    }
                }
                resultWrapper.setData(resultMenus);
            } else {
                resultWrapper.setErrorMsg("查询失败");
            }
        } catch (Exception e) {
            logger.error("子系统(移动端)菜单查询信息错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 系统查询系统菜单
     * @return
     */
    @ApiOperation(value = "系统菜单跟根据系统查询", notes = "用于根据系统查询系统菜单")
    @ApiImplicitParams({ @ApiImplicitParam(name = "sysId", value = "系统ID", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/get", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<MenuExt>> getSysMenu(@RequestParam(value = "sysId", required = true) Long sysId,
                                                   HttpServletRequest request) {
        ResultWrapper<List<MenuExt>> resultWrapper = new ResultWrapper<List<MenuExt>>();
        try {
            List<SysMenu> menus = sysService.getSysMenuBySysId(sysId);
            resultWrapper.setData(MenuExt.getTreeMenus(menus));
        } catch (Exception e) {
            logger.error("查询系统菜单错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }

    /**
     * 查询全部系统菜单
     * @return
     */
    @ApiOperation(value = "系统菜单查询全部", notes = "系统菜单查询全部")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/menu/sys", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<SysMenuExt>> getSysMenu(HttpServletRequest request) {
        ResultWrapper<List<SysMenuExt>> resultWrapper = new ResultWrapper<List<SysMenuExt>>();
        try {
            List<SysMenuExt> datas = new ArrayList<SysMenuExt>();
            List<SysInfo> allSys = sysService.getAllSys();
            for (SysInfo sysInfo : allSys) {
                SysMenuExt sysMenuExt = new SysMenuExt();
                BeanUtils.copyProperties(sysInfo, sysMenuExt);
                sysMenuExt.setMenuExt(
                    MenuExt.getTreeMenus(sysService.getSysMenuBySysId(sysInfo.getId())));
                datas.add(sysMenuExt);
            }
            resultWrapper.setData(datas);
        } catch (Exception e) {
            logger.error("查询系统菜单错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        }
        return resultWrapper;
    }
}
