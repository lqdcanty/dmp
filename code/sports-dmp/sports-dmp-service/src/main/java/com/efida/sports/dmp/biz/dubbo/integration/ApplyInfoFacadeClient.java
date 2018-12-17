/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.dubbo.integration;

import com.efida.sport.facade.model.ApplyInfoModel;

/**
 * 
 * @author zhiyang
 * @version $Id: ApplyInfoFacadeClient.java, v 0.1 2018年7月20日 下午11:24:56 zhiyang Exp $
 */
public interface ApplyInfoFacadeClient {

    /**
     * 
     * 
     * @param applyInfoModel
     * @return
     */
    public Boolean syncApplyInfo(ApplyInfoModel applyInfoModel);

}
