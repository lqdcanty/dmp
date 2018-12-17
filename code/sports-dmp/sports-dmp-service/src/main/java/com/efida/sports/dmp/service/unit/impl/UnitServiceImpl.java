/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.unit.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.mapper.OpenUnitEntityMapper;
import com.efida.sports.dmp.enums.UnitLockEnmu;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.unit.UnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.constants.UserConstants;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.enmus.UserStatusEnmu;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.util.RegexUtils;
import cn.evake.auth.util.SecretUtil;
import cn.evake.auth.util.UucodeUtil;

/**
 * 开放账户服务
 * @author wang yi
 * @desc 
 * @version $Id: UnitServiceImpl.java, v 0.1 2018年7月4日 上午10:55:53 wang yi Exp $
 */
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private OpenUnitEntityMapper unitMapper;

    @Autowired
    private UserService          userService;

    private String               defaultAvatar = "http://img.zcool.cn/community/0177b355ed01bc6ac7251df8f6be5a.png@1280w_1l_2o_100sh.webp";

    @Override
    public PagingResult<OpenUnitEntity> getPageUnitByKeywords(String unitCode, String unitName,
                                                              String email, String phone,
                                                              String lock, int page, int limit) {
        PageHelper.startPage(page, limit);
        Map<String, Object> params = new HashMap<>();
        params.put("unitCode", unitCode);
        params.put("unitName", unitName);
        params.put("email", email);
        params.put("phone", phone);
        params.put("lock", lock);
        List<OpenUnitEntity> units = unitMapper.selectByKeyWords(params);
        PageInfo<OpenUnitEntity> pageInfo = new PageInfo<OpenUnitEntity>(units);
        return new PagingResult<OpenUnitEntity>(pageInfo.getList(), pageInfo.getTotal(), page,
            limit);
    }

    @Transactional
    @Override
    public void lockUnitByCode(String unitCode, boolean lock) {
        unitMapper.lockUnitByCode(unitCode, lock == true ? 1 : 0);
        //userService.lockUserByUserName(unitCode, lock);
    }

    @Override
    public OpenUnitEntity getUnitByCode(String unitCode) {
        return unitMapper.selectByUnitCode(unitCode);
    }

    @Transactional
    @Override
    public OpenUnitEntity addNewOpenUnitEntity(OpenUnitEntity openUnit) {
        assertUnit(openUnit);
        Boolean enable = userService.checkUserNameIsEnable(openUnit.getUnitCode());
        if (!enable) {
            throw new DmpBusException(openUnit.getUnitCode() + "账户已被占用");
        }
        SysUser sysUser = new SysUser();
        sysUser.setAvatar(defaultAvatar);
        sysUser.setRealName(openUnit.getUnitName());
        sysUser.setUserName(openUnit.getUnitCode());
        sysUser.setUuid(UucodeUtil.getUucode(UserConstants.USER_PREFIX));
        sysUser.setPassword(SecretUtil.getMD5(openUnit.getManagerPass()));
        sysUser.setEmail(openUnit.getEmail());
        sysUser.setAvatar(defaultAvatar);
        sysUser.setPhone(openUnit.getPhone());
        sysUser.setGender("未知");
        sysUser.setStatus(UserStatusEnmu.normal.getCode());
        sysUser.setRemark(openUnit.getRemark());
        sysUser.setGmtCreate(new Date());
        openUnit.setIsLock(new Byte(UnitLockEnmu.unlock.getCode()));
        unitMapper.insert(openUnit);
        userService.addUser(sysUser);
        return openUnit;
    }

    /**
     * 断言承办方
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param openUnit
     */
    private void assertUnit(OpenUnitEntity openUnit) {
        if (openUnit == null) {
            throw new DmpBusException("承办方不能为空");
        }
        if (StringUtils.isBlank(openUnit.getUnitCode()) || openUnit.getUnitCode().length() > 24) {
            throw new DmpBusException("承办方账号不能为空且需小于24个字符");
        }
        if (RegexUtils.checkChinese(openUnit.getUnitCode())) {
            throw new DmpBusException("承办方账号不能为中文");
        }
        if (StringUtils.isBlank(openUnit.getUnitName()) || openUnit.getUnitName().length() > 24) {
            throw new DmpBusException("承办方名称不能为空且需小于24个字符");
        }
        /*    if (StringUtils.isBlank(openUnit.getManagerPass())
            || openUnit.getManagerPass().length() > 32) {
            throw new BusinessException("管理密码不能为空且需小于32个字符");
        }*/
        if (StringUtils.isBlank(openUnit.getClientSecrect())
            || openUnit.getClientSecrect().length() > 64) {
            throw new DmpBusException("客户端私钥不能为空且需小于24个字符");
        }
        if (StringUtils.isBlank(openUnit.getClientSecrect())
            || openUnit.getClientSecrect().length() > 64) {
            throw new DmpBusException("客户端私钥不能为空且需小于24个字符");
        }
        if (openUnit.getMaxPerMinute() == null || openUnit.getMaxPerMinute() < 0) {
            throw new DmpBusException("每分钟接口调用最大次数不能为空且大于0");
        }
        if (StringUtils.isBlank(openUnit.getWhiteIp())) {
            throw new DmpBusException("白名单不允许为空");
        }
        String ips = openUnit.getWhiteIp();
        if (!ips.contains(",")) {
            if (!RegexUtils.checkIpAddress(ips)) {
                throw new DmpBusException("IP地址格式错误,如果存在多个IP请用,分割");
            }
        } else {
            String[] split = ips.trim().split(",");
            for (String ip : split) {
                if (!RegexUtils.checkIpAddress(ip)) {
                    throw new DmpBusException("多个IP地址存在格式错误");
                }
            }
        }
        //
    }

    @Transactional
    @Override
    public OpenUnitEntity upOpenUnitEntity(String unitCode, OpenUnitEntity upPpenUnit) {
        assertUnit(upPpenUnit);
        OpenUnitEntity orUnitEntity = unitMapper.selectByUnitCode(unitCode);
        if (orUnitEntity == null) {
            throw new DmpBusException("用户不存在");
        }
        upPpenUnit.setId(orUnitEntity.getId());
        unitMapper.updateByPrimaryKeySelective(upPpenUnit);
        SysUser user = userService.getUserByUserName(upPpenUnit.getUnitCode());
        SysUser sysUser = new SysUser();
        sysUser.setRealName(upPpenUnit.getUnitName());
        sysUser.setEmail(upPpenUnit.getEmail());
        sysUser.setPhone(upPpenUnit.getPhone());
        sysUser.setRemark(upPpenUnit.getRemark());
        userService.updateUserByUUid(user.getUuid(), sysUser);
        return orUnitEntity;
    }

    @Override
    public PagingResult<OpenUnitEntity> getUnitAccounts(String keyword, int page, int size) {
        PageHelper.startPage(page, size);
        List<OpenUnitEntity> openUnits = unitMapper.selectByKeyWord(keyword);
        PageInfo<OpenUnitEntity> pageInfo = new PageInfo<OpenUnitEntity>(openUnits);
        return new PagingResult<OpenUnitEntity>(pageInfo.getList(), pageInfo.getTotal(), page,
            size);
    }

    @Override
    public List<OpenUnitEntity> getUnitAccountList(String lock) {
        Map<String, Object> params = new HashMap<>();
        params.put("lock", lock);
        List<OpenUnitEntity> openUnits = unitMapper.selectByKeyWords(params);
        return openUnits;
    }

}
