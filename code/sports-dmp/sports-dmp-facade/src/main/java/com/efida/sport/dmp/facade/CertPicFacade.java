/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade;

import com.efida.sport.dmp.facade.model.CertPicModel;
import com.efida.sport.dmp.facade.result.DefaultResult;

/**
 * 生成证书接口
 * @author wang yi
 * @desc 
 * @version $Id: CertPicFacade.java, v 0.1 2018年8月7日 下午9:07:20 wang yi Exp $
 */
public interface CertPicFacade {

    /**
    * 实时生成证书
    * @title
    * @methodName
    * @author wangyi
    * @description
    * @param certSn
    * @return
    */
    public DefaultResult<CertPicModel> generateCertPic(String certSn);

}
