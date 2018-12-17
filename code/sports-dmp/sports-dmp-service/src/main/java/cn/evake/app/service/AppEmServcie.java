/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.app.service;

import java.util.List;

import cn.evake.app.dao.model.AppInfo;

/**
 * 系统应用管理
 * @author wang yi
 * @desc 
 * @version $Id: AppEmServcie.java, v 0.1 2018年9月21日 下午3:39:43 wang yi Exp $
 */
public interface AppEmServcie {

    /**
     * 获取应用管理
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param app
     * @param user
     * @return
     */
    List<AppInfo> getAppsKeyword(String app, String user);

    /**
     * 检查系统应用编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appCode 系统编号
     * @return
     */
    boolean checkAppCodeUsed(String appCode);

    /**
     * 获取系统应用
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appCode
     * @return
     */
    AppInfo getAppInfo(String appCode);

    /**
     * 添加系统应用
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appInfo
     * @return
     */
    AppInfo addAppInfo(AppInfo appInfo);

    /**
     * 更新系统应用信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appInfo
     * @return
     */
    AppInfo upAppInfo(AppInfo appInfo);

    /**
     * 删除系统应用信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appCode
     * @return
     */
    boolean delAppInfo(String appCode);

    /**
     * 断言系统应用信息是否符合标准
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param appinfo
     */
    void assertAppInfo(AppInfo appinfo);

}
