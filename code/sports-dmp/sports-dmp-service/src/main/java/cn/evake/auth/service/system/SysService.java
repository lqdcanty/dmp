/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.system;

import java.util.List;

import cn.evake.auth.dao.model.SysBase;
import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;

/**
 * 
 * @author wang yi
 * @desc
 * @version $Id: SysService.java, v 0.1 2018年2月26日 下午6:37:40 wang yi Exp $
 */
public interface SysService {

	/**
	 * 更系统基本信息
	 * 
	 * @param sysBase
	 */
	void upInsertSysBase(SysBase sysBase);

	/**
	 * 获取系统基本信息
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @return
	 */
	SysBase getSysBase();

	/**
	 * 添加子系統
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysInfo
	 *            系统信息
	 * @return
	 */
	SysInfo addSys(SysInfo sysInfo);

	/**
	 * 删除一个子系统通过系统名
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysname
	 * @return
	 */
	boolean deleteSysWithName(String sysname);

	/**
	 * 添加一个系统菜单
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysInfo
	 * @return
	 */
	SysMenu addSysMenu(SysMenu sysInfo);

	/**
	 * 通过菜单ID删除数据
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param menuId
	 * @return
	 */
	boolean deleteSysMenu(Long menuId);

	/**
	 * 获取所有的子系统
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @return
	 */
	List<SysInfo> getAllSys();

	/**
	 * 获取所有菜单
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param loginUser
	 * @return
	 */
	List<SysMenu> getAllSysMenu();

	/**
	 * 更新系统数据
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysInfo
	 * @return
	 */
	SysInfo updateSysById(SysInfo sysInfo);

	/**
	 * 更新系统菜单数据
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysMenu
	 * @return
	 */
	SysMenu updateSysMenu(SysMenu sysMenu);

	/**
	 * 查询菜单通过系统ID
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param ids
	 *            系统IDs
	 * @return
	 */
	List<SysMenu> getSysMenuBySysIds(List<Long> ids);

	/**
	 * 
	 * 查询菜单通过系统ID
	 * 
	 * @param sysId
	 *            系统ID
	 * @return
	 */
	List<SysMenu> getSysMenuBySysId(Long sysId);

	/**
	 * 通过菜单ID查询系统菜单
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param menuIds
	 * @return
	 */
	List<SysMenu> getSysMenuByMenuIds(List<Long> menuIds);

	/**
	 * 通过菜单ID查询系统菜单
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param menuId
	 *            菜单ID
	 * @return
	 */
	SysMenu getSysMenuByMenuId(Long menuId);

	/**
	 * 获取系统通过ID
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysIds
	 * @return
	 */
	List<SysInfo> getSysBySysId(List<Long> sysIds);

	/**
	 * 查询菜单
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param menuIds
	 *            菜单IDs
	 * @param status
	 *            状态
	 * @return
	 */
	List<SysMenu> getSysMenuByMenuIdsWithStatus(List<Long> menuIds, Boolean status);

}
