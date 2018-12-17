/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score;

import java.util.List;

import com.efida.sports.dmp.biz.score.impl.SaveCertStatus;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;

import cn.evake.auth.usermodel.PagingResult;

/**
 *  分数证书服务
 * @author zhiyang
 * @version $Id: ScoreCertService.java, v 0.1 2018年8月5日 下午10:53:37 zhiyang Exp $
 */
public interface ScoreCertService {

    /**
     * 保存数字证书
     * 
     * @param certInfo
     * @return
     */
    SaveCertStatus saveScoreCert(ScoreCertEntity certInfo, boolean isBigGood);

    /**
     * 
     * 查看证书
     * @param certCode
     */
    void viewScorePic(String certCode);

    /**
     * 获取用户最新的证书
     * 
     * @param registerCode
     * @return
     */
    ScoreCertEntity getRegisterNewestScoreCert(String registerCode);

    /**
     * 获取用户证书
     * 分页查询
     * 
     * @param registerCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<ScoreCertEntity> getRegisterPageScoreCerts(String registerCode, int currentPage,
                                                            int pageSize);

    /**
     * 获取用户所有证书
     * 
     * @param registerCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<ScoreCertEntity> getRegisterAllScoreCerts(String registerCode);

}
