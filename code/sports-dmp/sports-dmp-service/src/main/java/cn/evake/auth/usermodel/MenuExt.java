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
 * 菜单树
 * @author wang yi
 * @desc 
 * @version $Id: MenuExt.java, v 0.1 2018年3月8日 下午1:12:09 wang yi Exp $
 */
public class MenuExt extends SysMenu {
    /**  */
    private static final long serialVersionUID = 3927059363824302850L;
    /**
          * 子菜单
          */
    private List<MenuExt>     children         = new ArrayList<MenuExt>();

    /**
     * 递归查找菜单树
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param menus
     * @return
     */
    public static List<MenuExt> getTreeMenus(List<SysMenu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<MenuExt>();
        }
        // 最后的结果
        List<MenuExt> menuList = new ArrayList<MenuExt>();
        // 先找到所有的一级菜单
        for (SysMenu sysMenu : menus) {
            // 一级菜单没有parentId
            if (sysMenu.getParentId() == null) {
                MenuExt menuExt = new MenuExt();
                BeanUtils.copyProperties(sysMenu, menuExt);
                menuList.add(menuExt);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (MenuExt menu : menuList) {
            List<MenuExt> child = getChild(menu.getId(), menus);
            menu.setChildren(child);
        }
        return menuList;
    }

    private static List<MenuExt> getChild(Long id, List<SysMenu> menus) {
        // 子菜单
        List<MenuExt> childList = new ArrayList<>();
        for (SysMenu menu : menus) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId() != null) {
                if (menu.getParentId().equals(id)) {
                    MenuExt menuExt = new MenuExt();
                    BeanUtils.copyProperties(menu, menuExt);
                    childList.add(menuExt);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (MenuExt menu : childList) {
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

    public void setChildren(List<MenuExt> children) {
        this.children = children;
    }

    public List<MenuExt> getChildren() {
        return children;
    }

}
