/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.log;

import javax.servlet.http.HttpServletRequest;

import cn.evake.auth.dao.model.SysUserLog;
import cn.evake.auth.usermodel.PagingResult;

/**
 * 日志服务
 * @author Evance
 * @version $Id: LogService.java, v 0.1 2018年4月26日 下午11:24:21 Evance Exp $
 */
public interface LogService {

    /**
     * 添加一条新日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userLog
     * @return
     */
    public SysUserLog addNewUserLog(SysUserLog userLog);

    /**
     * 查询日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PagingResult<SysUserLog> getPageUserLog(String keyword, Integer pageNumber,
                                                   Integer pageSize);

    /**
     * 添加用户访问日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     */
    public void addUserViewLog(HttpServletRequest request);

    /**
     * 添加用户访问日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @param desc 日志描述
     */
    public void addUserViewLog(HttpServletRequest request, String desc);

    /**
     * 添加用户操作日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @param desc
     */
    public void addUserOperateLog(HttpServletRequest request, String desc);

}
