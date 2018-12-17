/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.usermodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.dao.model.SysRolePermission;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.dao.model.SysUserRole;

/**
 * 用户信息类
 * @author wang yi
 * @desc 
 * @version $Id: UserInfoModel.java, v 0.1 2018年2月23日 下午2:48:21 wang yi Exp $
 */
public class UserInfoModel extends SysUser implements Serializable {

    /**  */
    private static final long       serialVersionUID = 989832419981771442L;

    /**
     *用户token 
     */
    private String                  authToken;

    /**
     * 用户所拥有的系统
     */
    private List<SysInfo>           sys;

    /**
     * 系统目录
     */
    private List<SysMenu>           sysMenu;

    /**
     * 用户角色
     */
    private List<SysUserRole>       userRoles;

    /**
     * 用户权限
     */
    private List<SysRolePermission> userPermisssions;

    //拓展显示数据//
    /**
     * 角色名称
     */
    private String                  userRoleStr;

    /**
     * 格式化所有URl
     * @param ctx 本系统根路径
     * @param menus 菜单
     * @return
     */
    public static List<SysMenu> getFormatURl(Long sysId, String ctx, List<SysMenu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return menus;
        }
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getIsSelfSys()) {
                sysMenu.setMenuUrl(ctx + sysMenu.getMenuUrl());
            }
        }
        return menus;
    }

    /**
     * 格式化所有URl
     * @param ctx 本系统根路径
     * @param menus 菜单
     * @return
     */
    public static List<SysMenu> getFormatURl(String ctx, List<SysMenu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return menus;
        }
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getIsSelfSys()) {
                sysMenu.setMenuUrl(ctx + sysMenu.getMenuUrl());
            }
        }
        return menus;
    }

    /**
     * 通过授权码获取菜单
     * @param sysMenus 菜单
     * @param permissionCode 授权码
     * @return
     */
    public static SysMenu getMenuByPermissionCode(List<SysMenu> sysMenus, String permissionCode) {
        if (CollectionUtils.isEmpty(sysMenus) || StringUtils.isBlank(permissionCode)) {
            return null;
        }
        for (SysMenu sysMenu : sysMenus) {
            if (permissionCode.equals(sysMenu.getPermissionCode())) {
                return sysMenu;
            }
        }
        return null;
    }

    /**
     * 
     * 通过URL获取菜单
     * @param sysMenus 系统菜单
     * @param menuUrl 菜单URl
     * @return
     */
    public static SysMenu getMenuByMenuUrl(List<SysMenu> sysMenus, String menuUrl) {
        if (CollectionUtils.isEmpty(sysMenus)) {
            return null;
        }
        for (SysMenu sysMenu : sysMenus) {
            if (StringUtils.isNotBlank(sysMenu.getMenuUrl())
                && sysMenu.getMenuUrl().equals(menuUrl)) {
                return sysMenu;
            }
        }
        return null;
    }

    /**
     * 获取所有允许的code
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userModel
     * @return
     */
    public static List<Long> getAllPermissonId(List<SysRolePermission> permisssions) {
        List<Long> result = new ArrayList<Long>();
        if (permisssions == null) {
            return result;
        }
        //根据权限ID获取菜单
        for (SysRolePermission sysRolePermission : permisssions) {
            result.add(sysRolePermission.getMenuId());
        }
        return result;
    }

    /**
     * 获取所有的角色ID
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userModel
     * @return
     */
    public static List<Long> getRoleIds(List<SysUserRole> roles) {
        List<Long> result = new ArrayList<Long>();
        if (roles == null) {
            return result;
        }
        for (SysUserRole sysUserRole : roles) {
            Long roleId = sysUserRole.getRoleId();
            result.add(roleId);
        }
        return result;
    }

    /**
     * 获取菜单ID
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userModel
     * @return
     */
    public static List<Long> getMenuIds(List<SysRolePermission> roles) {
        List<Long> result = new ArrayList<Long>();
        if (roles == null) {
            return result;
        }
        for (SysRolePermission sysRole : roles) {
            Long menuId = sysRole.getMenuId();
            result.add(menuId);
        }
        return result;
    }

    /**
     * 菜单树排序
     * @param indexMenus
     * @return menus
     */
    public static List<MenuExt> getSortMenus(List<MenuExt> menus) {
        Collections.sort(menus, new Comparator<MenuExt>() {
            @Override
            public int compare(MenuExt o1, MenuExt o2) {
                if (o1.getChildren() != null) {
                    return -1;
                }
                return 0;
            }
        });
        return menus;
    }

    /**
     * 获取菜单树
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysMenus
     * @return
     */
    public static List<MenuExt> getSysMenuTree(List<SysMenu> sysMenus) {
        if (sysMenus != null) {
            return MenuExt.getTreeMenus(sysMenus);
        }
        return new ArrayList<MenuExt>();
    }

    /**
     * 获取某系统下的菜单树
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysId
     * @param sysMenus
     * @return
     */
    public static List<MenuExt> getSysMenuTree(Long sysId, List<SysMenu> sysMenus) {
        if (sysMenus != null) {
            return MenuExt.getTreeMenus(getSysMenu(sysId, sysMenus));
        }
        return new ArrayList<MenuExt>();
    }

    /**
     * 获取某系统下的菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysId
     * @param sysMenus
     * @return
     */
    public static List<SysMenu> getSysMenu(Long sysId, List<SysMenu> sysMenus) {
        List<SysMenu> newSysMenus = new ArrayList<SysMenu>();
        if (sysId == null || sysMenus == null || sysMenus.size() == 0) {
            return newSysMenus;
        }
        for (SysMenu sysMenu : sysMenus) {
            if (sysId.equals(sysMenu.getSysId())) {
                newSysMenus.add(sysMenu);
            }
        }
        return newSysMenus;
    }

    /**
     * 获取系统ID
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userInfoModel
     * @return
     */
    public static List<Long> getSysIds(List<SysMenu> sysMenus) {
        List<Long> result = new ArrayList<Long>();
        if (sysMenus == null) {
            return result;
        }
        for (SysMenu sys : sysMenus) {
            Long sysId = sys.getSysId();
            result.add(sysId);
        }
        return result;
    }

    public List<MenuExt> getSysMenuTree() {
        return UserInfoModel.getSysMenuTree(sysMenu);
    }

    public String getUserRoleStr() {
        if (!CollectionUtils.isEmpty(this.userRoles)) {
            String resultRoles = "";
            int start = 0;
            for (SysUserRole role : userRoles) {
                start = start + 1;
                if (start == 1) {
                    resultRoles = role.getRoleName();
                } else {
                    resultRoles = resultRoles + "," + role.getRoleName();
                }
            }
            return resultRoles;
        }
        return userRoleStr;
    }

    public void setUserRoleStr(String userRoleStr) {
        this.userRoleStr = userRoleStr;
    }

    public List<SysUserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<SysUserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<SysRolePermission> getUserPermisssions() {
        return userPermisssions;
    }

    public void setUserPermisssions(List<SysRolePermission> userPermisssions) {
        this.userPermisssions = userPermisssions;
    }

    public List<SysInfo> getSys() {
        return sys;
    }

    public void setSys(List<SysInfo> sys) {
        this.sys = sys;
    }

    public List<SysMenu> getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(List<SysMenu> sysMenu) {
        this.sysMenu = sysMenu;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
