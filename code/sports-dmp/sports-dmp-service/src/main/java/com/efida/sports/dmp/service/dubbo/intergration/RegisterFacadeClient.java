/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration;

import com.efida.sport.facade.model.RegisterModel;

/**
 * 
 * @author zhiyang
 * @version $Id: RegisterFacadeClient.java, v 0.1 2018年8月5日 下午11:02:08 zhiyang Exp $
 */
public interface RegisterFacadeClient {

    public RegisterModel getRegisterModelByPhone(String phoneNum);

}
