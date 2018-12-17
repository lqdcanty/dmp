/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.service.cert.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efida.sports.cert.dao.entity.ScoreCertEntity;
import com.efida.sports.cert.dao.entity.ScoreCertPicEntity;
import com.efida.sports.cert.dao.mapper.ScoreCertEntityMapper;
import com.efida.sports.cert.dao.mapper.ScoreCertPicEntityMapper;
import com.efida.sports.cert.model.PagingResult;
import com.efida.sports.cert.service.cert.ScoreCertServcie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author Evance
 * @version $Id: ScoreCertServcie.java, v 0.1 2018年8月7日 上午1:03:45 Evance Exp $
 */
@Service
public class ScoreCertServcieImpl implements ScoreCertServcie {

    @Autowired
    private ScoreCertEntityMapper    certEntityMaper;

    @Autowired
    private ScoreCertPicEntityMapper certPicMaper;

    private Logger                   logger = LoggerFactory.getLogger(ScoreCertServcieImpl.class);

    @Override
    public PagingResult<ScoreCertEntity> getNeedGenPic(int page, int size) {
        PageHelper.startPage(page, size);
        List<ScoreCertEntity> selectNeedGenPic = certEntityMaper.selectNeedGenPic();
        PageInfo<ScoreCertEntity> pageInfo = new PageInfo<ScoreCertEntity>(selectNeedGenPic);
        return new PagingResult<ScoreCertEntity>(pageInfo.getList(), pageInfo.getTotal(), page,
            size);
    }

    @Transactional
    @Override
    public ScoreCertPicEntity upSynScoreCertByCertNo(String certSn, String imgUrl) {
        if (StringUtils.isBlank(imgUrl)) {
            logger.error("garange cert img err certSn:{}", certSn);
            return null;
        }
        String picCode = new Date().getTime() + "";
        //插入图片
        ScoreCertPicEntity scoreCertPicEntity = new ScoreCertPicEntity();
        scoreCertPicEntity.setPicCode(picCode);
        scoreCertPicEntity.setCertPicUrl(imgUrl);
        scoreCertPicEntity.setCertSn(certSn);
        scoreCertPicEntity.setGeneratePicTime(new Date());
        scoreCertPicEntity.setGmtCreate(new Date());
        scoreCertPicEntity.setGmtModified(new Date());
        scoreCertPicEntity.setIsDelete(false);
        scoreCertPicEntity.setIsFastdfsDelete(false);
        certPicMaper.insert(scoreCertPicEntity);
        //修改信息
        ScoreCertEntity certEntity = certEntityMaper.getScoreCertByCertSn(certSn);
        ScoreCertEntity upCertEy = new ScoreCertEntity();
        upCertEy.setId(certEntity.getId());
        upCertEy.setCertPicUrl(imgUrl);
        upCertEy.setPicCode(picCode);
        upCertEy.setIsPicOk(true);
        upCertEy.setStatus("normal");
        upCertEy.setNeedGeneratePic(false);
        certEntityMaper.updateByPrimaryKeySelective(upCertEy);
        return scoreCertPicEntity;
    }

    @Override
    public ScoreCertEntity getCertBySn(String certSn) {
        return certEntityMaper.getScoreCertByCertSn(certSn);
    }

    @Override
    public ScoreCertEntity getCertByIdForUpdate(long id) {
        return certEntityMaper.lockScorecertById(id);
    }
}
