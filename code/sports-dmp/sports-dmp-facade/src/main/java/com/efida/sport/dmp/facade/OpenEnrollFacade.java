/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade;

import com.efida.sport.dmp.facade.model.EnrollInfoDto;
import com.efida.sport.dmp.facade.model.EnrollxInfoDto;
import com.efida.sport.dmp.facade.result.DefaultResult;

/**
 * 报名数据提交
 * @author zhiyang
 * @version $Id: OpenEnrollFacade.java, v 0.1 2018年7月5日 下午2:53:43 zhiyang Exp $
 */
public interface OpenEnrollFacade {

    /**
     * 提交报名信息到 数据交换平台
     * (老接口，不再支持）
     * 
     * @param unitCode
     * @param enrollInfo
     * @return 返回是否成功
     */
    @Deprecated
    public DefaultResult<Boolean> submitEnrollInfo(String unitCode, EnrollInfoDto enrollInfo);

    /**
     * 提交报名信息到 数据交换平台。 支持团体报名。 
     * 
     * @param unitCode
     * @param enrollInfo
     * @return 返回是否成功
     */
    public DefaultResult<Boolean> submitEnrollInfo(String unitCode, EnrollxInfoDto enrollInfo);
}
