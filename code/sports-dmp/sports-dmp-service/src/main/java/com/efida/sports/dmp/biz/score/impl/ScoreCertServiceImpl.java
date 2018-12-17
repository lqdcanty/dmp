/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.RegisterModel;
import com.efida.sports.dmp.biz.score.ScoreCertService;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;
import com.efida.sports.dmp.dao.entity.ScoreCertMaxNoEntity;
import com.efida.sports.dmp.dao.entity.ScoreCertNoEntity;
import com.efida.sports.dmp.dao.mapper.ScoreCertEntityMapper;
import com.efida.sports.dmp.dao.mapper.ScoreCertMaxNoEntityMapper;
import com.efida.sports.dmp.dao.mapper.ScoreCertNoEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.RegisterFacadeClient;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.utils.SeqWorkerUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 
 * @author zhiyang
 * @version $Id: ScoreCertServiceImpl.java, v 0.1 2018年8月5日 下午10:57:03 zhiyang Exp $
 */
@Service
public class ScoreCertServiceImpl implements ScoreCertService {

    private static Logger              logger = LoggerFactory.getLogger(ScoreCertServiceImpl.class);
    @Autowired
    private RegisterFacadeClient       registerFacadeClient;
    @Autowired
    private ScoreCertEntityMapper      scoreCertEntityMapper;

    @Autowired
    private ScoreCertNoEntityMapper    scoreCertNoEntityMapper;

    @Autowired
    private ScoreCertMaxNoEntityMapper scoreCertMaxNoEntityMapper;

    /**
     * 
     * @see com.efida.sports.dmp.biz.score.ScoreCertService#saveScoreCert(com.efida.sports.dmp.dao.entity.ScoreCertEntity, boolean)
     */
    public SaveCertStatus saveScoreCert(ScoreCertEntity certInfo, boolean isBigGood) {

        ScoreCertEntity old = this.queryByIdempotentId(certInfo.getUnitCode(),
            certInfo.getIdempotentId());
        SaveCertStatus st = new SaveCertStatus();
        st.setSuccess(true);
        String tipInfo = "";
        if (old != null) {

            if (old.getIsValid()) {
                if (isBigGood) {
                    if (old.getScore().doubleValue() >= certInfo.getScore().doubleValue()) {
                        tipInfo = "证书已颁发，无更优异成绩，不更新证书，证书编号：" + old.getCertSn();
                        st.setTipInfo(tipInfo);
                        return st;
                    }
                } else {
                    if (old.getScore().doubleValue() <= certInfo.getScore().doubleValue()) {
                        tipInfo = "证书已颁发，无更优异成绩，不更新证书，证书编号：" + old.getCertSn();
                        st.setTipInfo(tipInfo);
                        return st;
                    }

                }

            }
            ScoreCertEntity ns = new ScoreCertEntity();
            ns.setId(old.getId());
            ns.setScore(certInfo.getScore());
            ns.setScoreDesc(certInfo.getScoreDesc());
            ns.setScoreRankCode(certInfo.getScoreRankCode());
            ns.setScoreUnit(certInfo.getScoreUnit());
            ns.setRanking(certInfo.getRanking());
            ns.setPlayerName(certInfo.getPlayerName());
            ns.setPlayerPhone(certInfo.getPlayerPhone());

            ns.setMatchCode(certInfo.getMatchCode());
            ns.setMatchName(certInfo.getMatchName());
            ns.setFieldCode(certInfo.getFieldCode());
            ns.setFieldName(certInfo.getFieldName());
            ns.setMatchGroupCode(certInfo.getMatchGroupCode());
            ns.setMatchGroupName(certInfo.getMatchGroupName());
            ns.setEventCode(certInfo.getEventCode());
            ns.setEventName(certInfo.getEventName());
            //ns.setStatus("waitCheck");
            ns.setStatusDesc("证书发生更新!");
            ns.setCertTime(new Date());
            Date partTime = certInfo.getPartTime();

            ns.setPartTime(partTime);
            ns.setCertMatchName(certInfo.getCertMatchName());
            ns.setCertEventName(certInfo.getCertEventName());
            ns.setCertName(certInfo.getCertName());
            ns.setCertTime(certInfo.getCertTime());
            filledCertInfo(ns);

            if (StringUtils.isEmpty(ns.getCertMatchName())) {
                throw new BusinessException("证书cerMatchName为空!");
            }
            if (StringUtils.isEmpty(ns.getCertName())) {
                throw new BusinessException("证书certName为空!");
            }
            if (StringUtils.isEmpty(ns.getCertEventName())) {
                throw new BusinessException("证书certEventName为空!");
            }
            ns.setIsPicOk(false);
            ns.setNeedGeneratePic(true);
            ns.setNeedSendSms(true);

            this.scoreCertEntityMapper.updateByPrimaryKeySelective(ns);
            tipInfo = "有新成绩产生,证书重新颁发，请到电子证书管理页面查看!证书编号：" + old.getCertSn();
            st.setSuccess(true);
            st.setTipInfo(tipInfo);
            return st;
        }

        RegisterModel register = registerFacadeClient
            .getRegisterModelByPhone(certInfo.getPlayerPhone());
        if (register == null) {
            throw new BusinessException(" 颁发证书，自动创建用户账号时失败!请联系管理员！");
        }

        certInfo.setRegisterCode(register.getRegisterCode());
        // certInfo.setRegisterCode("2762306153302016");
        boolean success = saveCertInfo(certInfo);
        tipInfo = "";
        st.setSuccess(success);
        st.setTipInfo(tipInfo);
        return st;
    }

    @Transactional
    private boolean saveCertInfo(ScoreCertEntity certInfo) {

        Date certTime = Calendar.getInstance().getTime();
        String certYear = DateUtil.format(certTime, DateUtil.YEAR_FORMAT);
        ScoreCertNoEntity scn = getSnInfo(certYear);
        if (scn == null) {
            logger.warn("尝试10次获取 sn失败！请联系管理员！");
            return false;
        }

        String certCode = SeqWorkerUtil.generateTimeSequence();
        certInfo.setCertCode(certCode);
        certInfo.setCertNo(scn.getCertNo());
        certInfo.setCertYear(certYear);
        certInfo.setCertSn(scn.getCertSn());
        certInfo.setCertTime(certTime);

        filledCertInfo(certInfo);
        certInfo.setIsValid(true);
        if (StringUtils.isEmpty(certInfo.getCertMatchName())) {
            throw new BusinessException("证书cerMatchName为空!");
        }
        if (StringUtils.isEmpty(certInfo.getCertName())) {
            throw new BusinessException("证书certName为空!");
        }
        if (StringUtils.isEmpty(certInfo.getCertEventName())) {
            throw new BusinessException("证书certEventName为空!");
        }

        certInfo.setStatus("waitPic");
        certInfo.setIsViewPic(false);
        certInfo.setIsPicOk(false);
        certInfo.setNeedGeneratePic(true);
        certInfo.setNeedSendSms(true);
        scn.setIsUse(true);
        scn.setCertCode(certCode);
        scn.setRegiserCode(certInfo.getRegisterCode());
        boolean success = saveOrUpdate(scn, certInfo);
        return success;
    }

    private void filledCertInfo(ScoreCertEntity certInfo) {

        if (!StringUtils.isEmpty(certInfo.getPlayerName())) {
            certInfo.setCertName(certInfo.getPlayerName());
        } else {
            certInfo.setCertName(certInfo.getPlayerPhone());
        }
        certInfo.setCertMatchName(certInfo.getMatchName());
        String certEventName = null;
        //去掉比赛组
        /*      if (StringUtils.isNotEmpty(certInfo.getMatchGroupName())) {
            certEventName = certInfo.getMatchGroupName() + "-";
        }*/
        certEventName = certInfo.getEventName();
        certInfo.setCertEventName(certEventName);
    }

    /**
     * 顺序 
     *   score_cert_maxno
     *   score_cert_no
     *   score_cert
     * 
     * @param scn
     * @param certInfo
     * @return
     */
    @Transactional
    public boolean saveOrUpdate(ScoreCertNoEntity scn, ScoreCertEntity certInfo) {

        if (scn.getId() != null) {
            this.scoreCertNoEntityMapper.updateByPrimaryKey(scn);
        } else {
            this.scoreCertNoEntityMapper.insert(scn);
        }
        int id = scoreCertEntityMapper.insert(certInfo);

        return id > 0;
    }

    public ScoreCertNoEntity getSnInfo(String certYear) {

        int max = 10;
        while (max >= 1) {
            ScoreCertNoEntity item = tryGetSnInfo(certYear);
            if (item != null) {
                return item;
            }
            logger.info("继续尝试获取新证书编号！");
            max--;
        }
        return null;

    }

    public ScoreCertNoEntity tryGetSnInfo(String certYear) {

        ScoreCertMaxNoEntity item = this.queryCertNo(certYear);
        int certNo = item.getCurrentNo();
        String certSn = getCertSn(certYear, certNo);
        ScoreCertNoEntity scn = scoreCertNoEntityMapper.queryByCertSn(certSn);
        if (scn == null) {
            scn = new ScoreCertNoEntity();
            scn.setCertSn(certSn);
            scn.setCertNo(certNo);
            scn.setCertYear(certYear);
            scn.setIsUse(true);
            return scn;
        }

        if (scn.getIsUse()) {
            logger.error("获取snInfo失败!sn in use:" + certSn);
            return null;
        }

        return scn;
    }

    public ScoreCertEntity queryByIdempotentId(String unitCode, String idempotentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", unitCode);
        map.put("idempotentId", idempotentId);
        return this.scoreCertEntityMapper.selectByIdempotentId(map);

    }

    private String getCertSn(String certYear, int certNo) {

        String str = "" + certNo;
        int len = str.length();
        for (int i = len; i < 8; i++) {
            str = "0" + str;
        }
        return certYear + str;
    }

    @Transactional
    public ScoreCertMaxNoEntity queryCertNo(String certYear) {

        ScoreCertMaxNoEntity item = this.scoreCertMaxNoEntityMapper.selectMaxNoForUpdate(certYear);
        if (item == null) {
            item = new ScoreCertMaxNoEntity();
            item.setCertYear(certYear);
            item.setCurrentNo(1);
            this.scoreCertMaxNoEntityMapper.insert(item);
        } else {
            int increase = 1;//(int) (1 + 6 * Math.random());
            item.setCurrentNo(item.getCurrentNo() + increase);
            this.scoreCertMaxNoEntityMapper.updateByPrimaryKey(item);
        }
        return item;
    }

    @Override
    public void viewScorePic(String certCode) {
        scoreCertEntityMapper.updateViewScorePicStatus(certCode);
    }

    @Override
    public ScoreCertEntity getRegisterNewestScoreCert(String registerCode) {
        return scoreCertEntityMapper.getRegisterNewestScoreCert(registerCode);
    }

    @Override
    public PagingResult<ScoreCertEntity> getRegisterPageScoreCerts(String registerCode,
                                                                   int currentPage, int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        List<ScoreCertEntity> entitys = scoreCertEntityMapper.getRegisterScoreCerts(registerCode);
        PageInfo<ScoreCertEntity> pageInfo = new PageInfo<ScoreCertEntity>(entitys);
        return new PagingResult<ScoreCertEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);

    }

    @Override
    public List<ScoreCertEntity> getRegisterAllScoreCerts(String registerCode) {
        return scoreCertEntityMapper.getRegisterScoreCerts(registerCode);
    }

}
