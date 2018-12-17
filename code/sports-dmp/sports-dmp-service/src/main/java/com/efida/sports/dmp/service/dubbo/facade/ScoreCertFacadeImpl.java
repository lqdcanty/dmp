/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.facade;

import java.util.List;

import com.efida.sports.dmp.service.score.CompetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.efida.sport.admin.facade.exception.BusinessException;
import com.efida.sport.dmp.facade.ScoreCertFacade;
import com.efida.sport.dmp.facade.model.ScoreCertModel;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.dmp.facade.result.DmpPageResult;
import com.efida.sports.dmp.biz.score.ScoreCertService;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;
import com.efida.sports.dmp.service.dubbo.cover.ScoreCertCover;

import cn.evake.auth.usermodel.PagingResult;

/**
 *
 * @author zoutao
 * @version $Id: ScoreCertFacadeImpl.java, v 0.1 2018年8月6日 下午4:13:28 zoutao Exp $
 */
@Service
public class ScoreCertFacadeImpl implements ScoreCertFacade {

    private Logger           logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScoreCertService scoreCertService;

    @Autowired
    private CompetitionService competitionService;

    @Override
    public DefaultResult<DmpPageResult<ScoreCertModel>> getRegisterPageScoreCert(String registerCode,
                                                                                 int currentPage,
                                                                                 int pageSize) {
        DefaultResult<DmpPageResult<ScoreCertModel>> dr = new DefaultResult<DmpPageResult<ScoreCertModel>>();
        try {
            PagingResult<ScoreCertEntity> page = scoreCertService
                .getRegisterPageScoreCerts(registerCode, currentPage, pageSize);
            List<ScoreCertModel> scoreCertList = ScoreCertCover.entitys2models(page.getList());
            for(ScoreCertModel scoreCert:scoreCertList){
                String competitionName = competitionService.selectNameByRankCode(scoreCert.getScoreCertCode());
                scoreCert.setComtitionName(competitionName);
            }
            DmpPageResult<ScoreCertModel> retpage = new DmpPageResult(
                    scoreCertList, page.getAllRow(), currentPage,
                pageSize);
            dr.setResultObj(retpage);
            dr.setSuccess(true);
        } catch (BusinessException e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg("获取用户证书失败");
        }
        return dr;
    }

    @Override
    public DefaultResult<List<ScoreCertModel>> getRegisterAllScoreCert(String registerCode) {

        DefaultResult<List<ScoreCertModel>> dr = new DefaultResult<List<ScoreCertModel>>();
        try {
            List<ScoreCertEntity> scoreCerts = scoreCertService
                .getRegisterAllScoreCerts(registerCode);
            dr.setSuccess(true);
            dr.setResultObj(ScoreCertCover.entitys2models(scoreCerts));
        } catch (BusinessException e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg("获取用户证书失败");
        }
        return dr;

    }

    @Override
    public DefaultResult<ScoreCertModel> getRegisterNewestScoreCert(String registerCode) {

        DefaultResult<ScoreCertModel> dr = new DefaultResult<ScoreCertModel>();
        try {
            ScoreCertEntity entity = scoreCertService.getRegisterNewestScoreCert(registerCode);
            dr.setSuccess(true);
            dr.setResultObj(ScoreCertCover.entity2model(entity));
        } catch (BusinessException e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg("获取用户证书失败");
        }
        return dr;

    }

    @Override
    public DefaultResult<Boolean> viewScoreCert(String certCode) {

        DefaultResult<Boolean> dr = new DefaultResult<Boolean>();
        try {
            scoreCertService.viewScorePic(certCode);
            dr.setSuccess(true);
            dr.setResultObj(true);
        } catch (BusinessException e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            dr.setSuccess(false);
            dr.setErrorMsg("修改证书查看状态失败");
        }
        return dr;
    }

}
