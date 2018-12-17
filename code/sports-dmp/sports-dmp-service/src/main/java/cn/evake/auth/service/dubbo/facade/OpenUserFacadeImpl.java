/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.dubbo.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.dubbo.open.facade.OpenUserFacade;
import cn.evake.auth.dubbo.open.model.OpenUserModel;
import cn.evake.auth.dubbo.open.result.OpenAuthResult;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.user.UserService;

/**
 *系统用户对外开放接口
 * @author wang yi
 * @desc 
 * @version $Id: SysUserFacadeService.java, v 0.1 2018年6月15日 下午3:41:20 wang yi Exp $
 */
@com.alibaba.dubbo.config.annotation.Service
public class OpenUserFacadeImpl implements OpenUserFacade {

    @Autowired
    private UserService userService;

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OpenAuthResult<OpenUserModel> getUserInfo(String uuid) {
        OpenAuthResult<OpenUserModel> defaultResult = new OpenAuthResult<OpenUserModel>();
        try {
            SysUser user = userService.getUserByUuid(uuid);
            OpenUserModel openUserModel = new OpenUserModel();
            BeanUtils.copyProperties(user, openUserModel);
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

}
