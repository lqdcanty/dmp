/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.dubbo.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.evake.auth.dubbo.open.facade.OpenAuthFacade;
import cn.evake.auth.dubbo.open.model.OpenUserModel;
import cn.evake.auth.dubbo.open.result.OpenAuthResult;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 *  系统鉴权对外开放接口
 * @author wang yi
 * @version $Id: OpenAuthFacadeImpl.java, v 0.1 2018年8月2日 下午5:40:37 wang yi Exp $
 */
@com.alibaba.dubbo.config.annotation.Service
public class OpenAuthFacadeImpl implements OpenAuthFacade {

    @Autowired
    private UserService userService;

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OpenAuthResult<OpenUserModel> getLoginUserInfo(String uuid, String authToken) {
        OpenAuthResult<OpenUserModel> defaultResult = new OpenAuthResult<OpenUserModel>();
        try {
            UserInfoModel userModel = userService.getLoginUserInfo(uuid);
            if (userModel == null) {
                throw new AuthBusException("用户未登录!");
            }
            if (!userModel.getAuthToken().equals(authToken)) {
                throw new AuthBusException("非法访问!");
            }
            OpenUserModel openUserModel = new OpenUserModel();
            BeanUtils.copyProperties(userModel, openUserModel);
            defaultResult.setResultObj(openUserModel);
        } catch (AuthBusException e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg("系统异常");
        }
        return defaultResult;
    }

    @Override
    public OpenAuthResult<Boolean> checkIsLogin(String uuid, String authToken) {
        OpenAuthResult<Boolean> defaultResult = new OpenAuthResult<Boolean>();
        try {
            UserInfoModel userModel = userService.getLoginUserInfo(uuid);
            if (userModel == null) {
                defaultResult.setResultObj(false);
                return defaultResult;
            }
            if (!userModel.getAuthToken().equals(authToken)) {
                defaultResult.setResultObj(false);
                return defaultResult;
            }
            defaultResult.setResultObj(true);
            return defaultResult;
        } catch (AuthBusException e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg("系统异常");
        }
        return defaultResult;
    }

    @Override
    public OpenAuthResult<Boolean> checkIsLimit(String uuid, String permissionCode) {
        OpenAuthResult<Boolean> defaultResult = new OpenAuthResult<Boolean>();
        try {
            boolean isPermission = userService.checkUserLimit(uuid, permissionCode);
            defaultResult.setResultObj(isPermission);
        } catch (AuthBusException e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            defaultResult.setSuccess(false);
            defaultResult.setErrorMsg("系统异常");
        }
        return defaultResult;
    }

}
