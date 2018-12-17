/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.evake.auth.dao.mapper.SysBaseMapper;
import cn.evake.auth.dao.mapper.SysInfoMapper;
import cn.evake.auth.dao.mapper.SysMenuMapper;
import cn.evake.auth.dao.model.SysBase;
import cn.evake.auth.dao.model.SysBaseExample;
import cn.evake.auth.dao.model.SysBaseExample.Criteria;
import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.system.SysService;

/**
 * 
 * @author wang yi
 * @desc
 * @version $Id: SysServiceImpl.java, v 0.1 2018年2月26日 下午6:38:09 wang yi Exp $
 */
@Service
public class SysServiceImpl implements SysService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysInfoMapper sysinfoDao;

	@Autowired
	private SysMenuMapper sysMenuDao;

	@Autowired
	private SysBaseMapper sysBaseMapper;

	@Override
	public SysInfo addSys(SysInfo sysInfo) {
		// 用户
		if (sysInfo == null) {
			throw new AuthBusException("添加系统数据不能为空");
		}
		sysInfo.setGmtCreate(new Date());
		SysInfo sys = sysinfoDao.selectByName(sysInfo.getName());
		if (sys != null) {
			throw new AuthBusException("系统已存在!无需重复添加");
		}
		sysinfoDao.insert(sysInfo);
		return sysInfo;
	}

	@Override
	public List<SysInfo> getAllSys() {
		return sysinfoDao.selectAll();
	}

	@Override
	public boolean deleteSysWithName(String sysname) {
		SysInfo sys = sysinfoDao.selectByName(sysname);
		if (sys == null) {
			throw new AuthBusException("系统不已存在");
		}
		sysinfoDao.deleteByPrimaryKey(sys.getId());
		return true;
	}

	@Override
	public SysMenu addSysMenu(SysMenu sysInfo) {
		assertMenuIsLimit(sysInfo);
		SysMenu sys = sysMenuDao.selectByMenuNameAndSysId(sysInfo.getSysId(), sysInfo.getMenuName());
		if (sys != null) {
			throw new AuthBusException("同一系统下,菜单名已存在!");
		}
		// 添加一个菜单
		sysMenuDao.insert(sysInfo);
		return sysInfo;
	}

	/**
	 * 通过异常信息判断 菜单数据是否合法
	 * 
	 * @title
	 * @methodName
	 * @author wangyi
	 * @description
	 * @param sysInfo
	 */
	public void assertMenuIsLimit(SysMenu sysInfo) {
		if (sysInfo == null) {
			throw new AuthBusException("菜单不能为空");
		}
		if (sysInfo.getSysId() == null) {
			throw new AuthBusException("上级菜单必填");
		}
		if (StringUtils.isBlank(sysInfo.getType())) {
			throw new AuthBusException("类型不能为空");
		}
		if (StringUtils.isBlank(sysInfo.getMenuName())) {
			throw new AuthBusException("菜单名称不能为空");
		}
		if (sysInfo.getMenuName().length() > 16) {
			throw new AuthBusException("菜单名称不能大于16个字符");
		}
		if (StringUtils.isBlank(sysInfo.getPermissionCode())) {
			throw new AuthBusException("授权码不能为空");
		}
		if (sysInfo.getPermissionCode().length() > 24) {
			throw new AuthBusException("授权码不能大于24个字符");
		}
	}

	@Override
	public boolean deleteSysMenu(Long menuId) {
		SysMenu sysMenu = sysMenuDao.selectByPrimaryKey(menuId);
		if (sysMenu == null) {
			throw new AuthBusException("系统菜单不存在");
		}
		sysMenuDao.deleteByPrimaryKey(menuId);
		return true;
	}

	@Override
	public List<SysMenu> getAllSysMenu() {
		List<SysMenu> sysMenus = sysMenuDao.selectAll();
		return sysMenus;
	}

	@Override
	public SysInfo updateSysById(SysInfo sysInfo) {
		sysinfoDao.updateByPrimaryKeySelective(sysInfo);
		return sysinfoDao.selectByPrimaryKey(sysInfo.getId());
	}

	@Override
	public SysMenu updateSysMenu(SysMenu sysMenu) {
		assertMenuIsLimit(sysMenu);
		sysMenuDao.updateByPrimaryKeySelective(sysMenu);
		return sysMenuDao.selectByPrimaryKey(sysMenu.getId());
	}

	@Override
	public List<SysMenu> getSysMenuBySysIds(List<Long> sysIds) {
		if (CollectionUtils.isNotEmpty(sysIds)) {
			return sysMenuDao.selectBySysIds(sysIds);
		}
		return new ArrayList<SysMenu>();
	}

	@Override
	public List<SysMenu> getSysMenuByMenuIds(List<Long> menuIds) {
		if (CollectionUtils.isNotEmpty(menuIds)) {
			return sysMenuDao.selectByIds(menuIds);
		}
		return new ArrayList<SysMenu>();
	}

	@Override
	public List<SysInfo> getSysBySysId(List<Long> sysIds) {
		if (CollectionUtils.isNotEmpty(sysIds)) {
			return sysinfoDao.selectSysIds(sysIds);
		}
		return new ArrayList<SysInfo>();
	}

	@Override
	public List<SysMenu> getSysMenuBySysId(Long sysId) {
		List<Long> sysIds = new ArrayList<Long>();
		sysIds.add(sysId);
		return sysMenuDao.selectBySysIds(sysIds);
	}

	@Override
	public SysMenu getSysMenuByMenuId(Long menuId) {
		return sysMenuDao.selectByPrimaryKey(menuId);
	}

	@Override
	public List<SysMenu> getSysMenuByMenuIdsWithStatus(List<Long> menuIds, Boolean status) {
		if (CollectionUtils.isNotEmpty(menuIds)) {
			return sysMenuDao.selectByIdsWithStatus(menuIds, status);
		}
		return new ArrayList<SysMenu>();
	}

	@Override
	public SysBase getSysBase() {
		// 获取系统基本信息
		List<SysBase> bases = sysBaseMapper.selectByExample(null);
		if (CollectionUtils.isNotEmpty(bases)) {
			return bases.get(0);
		}
		return null;
	}

	@Override
	public void upInsertSysBase(SysBase sysBase) {
		SysBase base = getSysBase();
		if (base == null) {
			sysBase.setGmtModified(new Date());
			sysBase.setGmtCreate(new Date());
			sysBaseMapper.insert(sysBase);
		} else {
			sysBase.setGmtModified(new Date());
			sysBaseMapper.updateSelective(sysBase);
		}
	}

}
