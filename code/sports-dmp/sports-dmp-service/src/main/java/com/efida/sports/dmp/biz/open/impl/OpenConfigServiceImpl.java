/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.mapper.OpenUnitEntityMapper;

import cn.evake.auth.service.common.CacheService;

/**
 * 开放接口相关配置 服务
 * 
 * @author zhiyang
 * @version $Id: OpenConfigServiceImpl.java, v 0.1 2018年5月28日 下午5:04:03 zhiyang
 *          Exp $
 */
@Service("openConfigService")
public class OpenConfigServiceImpl implements OpenConfigService {

	@Autowired
	private CacheService cacheService;
	/**
	 * 开放接口账号
	 */
	@Autowired
	private OpenUnitEntityMapper openUnitEntityMapper;

	@Override
	public String getPrivateKey(String unitCode) throws OpenException {
		OpenUnitEntity entity = openUnitEntityMapper.selectByUnitCode(unitCode);
		if (entity == null) {
			throw new OpenException(ErrorCode.PARAMETER_INVALID, "未找到承办方账号" + unitCode + "配置信息！");
		}
		if (entity.getIsLock() > 0) {
			throw new OpenException(ErrorCode.PARAMETER_INVALID, "承办方账号:" + unitCode + "已锁定，请联系管理员！");
		}
		return entity.getClientSecrect();
	}

	@Override
	public void checkUnitCodeFreq(String unitCode) {
		//卡雷拉超级轨道赛车   放行
		if (unitCode.equals("carrera")) {
			return;
		}
		String lastMinute = DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT_NO_SEC);
		String key = "dmcodeFreq" + unitCode + lastMinute;
		Integer total = (Integer) cacheService.getObj(key);
		if (total == null) {
			total = 1;
		} else {
			total++;
		}
		if (total > 60) {
			throw new BusinessException("账号1分钟内接口调用超过60次，本次暂不提供服务，请放缓你的访问频率！");
		}
		cacheService.putObj(key, total, 300);

	}
}
