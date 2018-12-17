/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade;

import java.util.List;

import com.efida.sport.dmp.facade.model.ScoreCertModel;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.dmp.facade.result.DmpPageResult;

/**
 * 证书接口
 * @author zoutao
 * @version $Id: scoreCertFacade.java, v 0.1 2018年8月6日 下午3:31:17 zoutao Exp $
 */
public interface ScoreCertFacade {

    /**
     * 获取用户的证书
     * 获取已查看和未查看的证书
     *  分页查询
     * @param registerCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    public DefaultResult<DmpPageResult<ScoreCertModel>> getRegisterPageScoreCert(String registerCode,
                                                                                 int currentPage,
                                                                                 int pageSize);

    /**
     * 获取用户最新未查看的证书
     * 
     * @param registerCode
     * @return
     */
    public DefaultResult<ScoreCertModel> getRegisterNewestScoreCert(String registerCode);

    /**
     * 
     * 查看证书（标识该证书我已查看）
     * @param certCode
     * @return
     */
    public DefaultResult<Boolean> viewScoreCert(String certCode);

    /**
     * 获取用户所有证书
     * 
     * @param registerCode
     * @return
     */
    DefaultResult<List<ScoreCertModel>> getRegisterAllScoreCert(String registerCode);

}
