/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.dubbo.open.facade;

import cn.evake.auth.dubbo.open.model.OpenUserModel;
import cn.evake.auth.dubbo.open.result.OpenAuthResult;

/**
 * 开放用户接口
 * @author wang yi
 * @desc 
 * @version $Id: OpenUserFacade.java, v 0.1 2018年8月2日 下午2:46:58 wang yi Exp $
 */
public interface OpenUserFacade {

    /**
     * 获取用户信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid 用户UID (必填)
     * @return
     */
    OpenAuthResult<OpenUserModel> getUserInfo(String uuid);
}
