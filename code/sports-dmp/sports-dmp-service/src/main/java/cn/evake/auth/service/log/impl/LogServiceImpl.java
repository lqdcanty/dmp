/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.log.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.annotation.LogActionTypeEnum;
import cn.evake.auth.dao.mapper.SysUserLogMapper;
import cn.evake.auth.dao.model.SysUserLog;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.user.UserInfoUtil;

/**
 * 日志记录Service
 * @author Evance
 * @version $Id: LogServiceImpl.java, v 0.1 2018年4月26日 下午11:24:52 Evance Exp $
 */
@Service
public class LogServiceImpl implements LogService {

    private Logger           logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserLogMapper logMapper;

    @Autowired
    private UserService      userService;

    @Override
    public SysUserLog addNewUserLog(SysUserLog userLog) {
        if (userLog == null) {
            throw new AuthBusException("日志记录不能为空");
        }
        userLog.setGmtCreate(new Date());
        logMapper.insert(userLog);
        logger.debug("success insert log to db json:{}", JSON.toJSONString(userLog));
        return userLog;
    }

    @Override
    public PagingResult<SysUserLog> getPageUserLog(String keyword, Integer pageNumber,
                                                   Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<SysUserLog> logs = logMapper.selectKeyWord(keyword);
        PageInfo<SysUserLog> pageInfo = new PageInfo<>(logs);
        return new PagingResult<>(pageInfo.getList(), pageInfo.getTotal(), pageNumber, pageSize);
    }

    @Override
    public void addUserViewLog(HttpServletRequest request) {
        String userName = UserInfoUtil.getUserName(request);
        String requestURI = request.getRequestURI();
        String action = String.format("用户: %s 访问url: %s ", userName, requestURI);
        addUserViewLog(request, action);
    }

    @Override
    public void addUserViewLog(HttpServletRequest request, String desc) {
        UserInfoModel userModel = userService.getUserModelChecked(request);
        String userAgent = ServletUtil.getUserAgent(request);
        String requestURI = request.getRequestURI();
        SysUserLog userLog = new SysUserLog();
        userLog.setUuid(userModel.getUuid());
        userLog.setUserName(userModel.getUserName());
        userLog.setActionType(LogActionTypeEnum.VIEWPAGE.getType());
        userLog.setAction(desc);
        userLog.setBrowser(userAgent);
        userLog.setViewUrl(requestURI);
        userLog.setIp(ServletUtil.getIpAddress(request));
        addNewUserLog(userLog);
    }

    @Override
    public void addUserOperateLog(HttpServletRequest request, String desc) {
        UserInfoModel userModel = userService.getUserModelChecked(request);
        String userAgent = ServletUtil.getUserAgent(request);
        String requestURI = request.getRequestURI();
        SysUserLog userLog = new SysUserLog();
        userLog.setUuid(userModel.getUuid());
        userLog.setUserName(userModel.getUserName());
        userLog.setActionType(LogActionTypeEnum.OPERATE.getType());
        userLog.setAction(desc);
        userLog.setBrowser(userAgent);
        userLog.setViewUrl(requestURI);
        userLog.setIp(ServletUtil.getIpAddress(request));
        addNewUserLog(userLog);
    }

}
