/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.app.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.evake.app.dao.mapper.AppInfoMapper;
import cn.evake.app.dao.model.AppInfo;
import cn.evake.app.dao.model.AppInfoExample;
import cn.evake.app.dao.model.AppInfoExample.Criteria;
import cn.evake.app.service.AppEmServcie;
import cn.evake.auth.exception.AuthBusException;

/**
 * 系统应用管理
 * @author wang yi
 * @desc 
 * @version $Id: AppEmServcieImpl.java, v 0.1 2018年9月21日 下午3:39:57 wang yi Exp $
 */
@Service
public class AppEmServcieImpl implements AppEmServcie {

    @Autowired
    private AppInfoMapper appinfpMapper;

    @Override
    public List<AppInfo> getAppsKeyword(String app, String user) {
        AppInfoExample appInfoExample = new AppInfoExample();
        Criteria criteria = appInfoExample.createCriteria();
        if (StringUtils.isNotBlank(app)) {
            criteria.andAppCodeLike(app);
            criteria.andAppNameLike(app);
        }
        if (StringUtils.isNotBlank(user)) {
            criteria.andManagerNameLike(user);
            criteria.andManagerRealnameLike(user);
        }
        List<AppInfo> appinofs = appinfpMapper.selectByExample(appInfoExample);
        return appinofs;
    }

    @Override
    public boolean checkAppCodeUsed(String appCode) {
        AppInfo appinfo = getAppInfo(appCode);
        if (appinfo == null) {
            return false;
        }
        return true;
    }

    @Override
    public void assertAppInfo(AppInfo appinfo) {
        if (appinfo == null) {
            return;
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<AppInfo>> set = validator.validate(appinfo);
        for (ConstraintViolation<AppInfo> constraintViolation : set) {
            throw new AuthBusException(constraintViolation.getMessage());
        }
    }

    @Override
    public AppInfo getAppInfo(String appCode) {
        AppInfoExample appInfoExample = new AppInfoExample();
        Criteria criteria = appInfoExample.createCriteria();
        criteria.andAppCodeEqualTo(appCode);
        List<AppInfo> appinofs = appinfpMapper.selectByExample(appInfoExample);
        if (!CollectionUtils.isEmpty(appinofs)) {
            return appinofs.get(0);
        }
        return null;
    }

    @Override
    public AppInfo addAppInfo(AppInfo appInfo) {
        assertAppInfo(appInfo);
        appinfpMapper.insert(appInfo);
        return appInfo;
    }

    @Override
    public AppInfo upAppInfo(AppInfo appInfo) {
        assertAppInfo(appInfo);
        AppInfoExample appInfoExample = new AppInfoExample();
        Criteria criteria = appInfoExample.createCriteria();
        criteria.andAppCodeEqualTo(appInfo.getAppCode());
        appinfpMapper.updateByExample(appInfo, appInfoExample);
        return appInfo;
    }

    @Override
    public boolean delAppInfo(String appCode) {
        AppInfo appInfo = getAppInfo(appCode);
        if (appInfo == null) {
            throw new AuthBusException("编号为:" + appCode + "数据不存在");
        }
        appinfpMapper.deleteByPrimaryKey(appInfo.getId());
        return true;
    }

}
