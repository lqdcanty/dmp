/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.usermodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import cn.evake.auth.dao.model.SysMenu;

/**
 * 权限菜单树
 * @author wang yi
 * @desc 
 * @version $Id: MenuExt.java, v 0.1 2018年3月8日 下午1:12:09 wang yi Exp $
 */
public class RoleMenuExt extends SysMenu {
    private static final long serialVersionUID = 3466424744369172066L;

    private Boolean           hasPermis        = false;

    /**
     * 子菜单
     */
    private List<RoleMenuExt> children         = new ArrayList<RoleMenuExt>();

    /**
     * 获取系统下的菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param id
     * @param allMenus
     * @return
     */
    public static List<RoleMenuExt> getRoleMenuExtTree(Long sysId, List<RoleMenuExt> allMenus) {
        List<RoleMenuExt> sysMenus = getSysMenu(sysId, allMenus);
        return getTreeMenus(sysMenus);
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
    private static List<RoleMenuExt> getSysMenu(Long sysId, List<RoleMenuExt> sysMenus) {
        List<RoleMenuExt> newSysMenus = new ArrayList<RoleMenuExt>();
        if (sysId == null || sysMenus == null || sysMenus.size() == 0) {
            return newSysMenus;
        }
        for (RoleMenuExt sysMenu : sysMenus) {
            if (sysId.equals(sysMenu.getSysId())) {
                newSysMenus.add(sysMenu);
            }
        }
        return newSysMenus;
    }

    /**
    * 递归查找菜单树
    * @title
    * @methodName
    * @author wangyi
    * @description
    * @param menus
    * @return
    */
    public static List<RoleMenuExt> getTreeMenus(List<RoleMenuExt> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<RoleMenuExt>();
        }
        // 最后的结果
        List<RoleMenuExt> menuList = new ArrayList<RoleMenuExt>();
        // 先找到所有的一级菜单
        for (SysMenu sysMenu : menus) {
            // 一级菜单没有parentId
            if (sysMenu.getParentId() == null) {
                RoleMenuExt menuExt = new RoleMenuExt();
                BeanUtils.copyProperties(sysMenu, menuExt);
                menuList.add(menuExt);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (RoleMenuExt menu : menuList) {
            List<RoleMenuExt> child = getChild(menu.getId(), menus);
            menu.setChildren(child);
        }
        return menuList;
    }

    private static List<RoleMenuExt> getChild(Long id, List<RoleMenuExt> menus) {
        // 子菜单
        List<RoleMenuExt> childList = new ArrayList<>();
        for (SysMenu menu : menus) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId() != null) {
                if (menu.getParentId().equals(id)) {
                    RoleMenuExt menuExt = new RoleMenuExt();
                    BeanUtils.copyProperties(menu, menuExt);
                    childList.add(menuExt);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (RoleMenuExt menu : childList) {
            if (menu.getParentId() != null) {
                // 递归
                menu.setChildren(getChild(menu.getId(), menus));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public void setChildren(List<RoleMenuExt> children) {
        this.children = children;
    }

    public List<RoleMenuExt> getChildren() {
        return children;
    }

    public Boolean getHasPermis() {
        return hasPermis;
    }

    public Long getMenuId() {
        return super.getId();
    }

    public void setHasPermis(Boolean hasPermis) {
        this.hasPermis = hasPermis;
    }

}
