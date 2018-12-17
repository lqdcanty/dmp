/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.web.vo;

import org.springframework.beans.BeanUtils;

import cn.evake.auth.dao.model.SysMenu;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: DisSysMenuVo.java, v 0.1 2018年4月23日 下午4:57:46 wang yi Exp $
 */
public class DisSysMenuVo extends SysMenu {
    /**  */
    private static final long serialVersionUID = 6167033523078780506L;

    private DisSysMenuVo      parentMenuInfo;

    public DisSysMenuVo getParentMenuInfo() {
        return parentMenuInfo;
    }

    public void setParentMenuInfo(DisSysMenuVo parentMenuInfo) {
        this.parentMenuInfo = parentMenuInfo;
    }

    /**
     * 获取菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param parent
     * @param thisMenu
     * @return
     */
    public static DisSysMenuVo getDisSysMenuVo(SysMenu parent, SysMenu thisMenu) {
        DisSysMenuVo disSysMenuVo = new DisSysMenuVo();
        if (thisMenu != null) {
            disSysMenuVo = getDisSysMenuVo(thisMenu);
        }
        if (parent != null) {
            disSysMenuVo.setParentMenuInfo(getDisSysMenuVo(parent));
        }
        return disSysMenuVo;
    }

    /**
     * 获取菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param thisMenu
     * @return
     */
    private static DisSysMenuVo getDisSysMenuVo(SysMenu thisMenu) {
        DisSysMenuVo disSysMenuVo = new DisSysMenuVo();
        if (thisMenu != null) {
            BeanUtils.copyProperties(thisMenu, disSysMenuVo);
        }
        return disSysMenuVo;
    }

}
