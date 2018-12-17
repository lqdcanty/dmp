/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.tread;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.efida.sports.cert.component.cache.CacheService;
import com.efida.sports.cert.dao.entity.ScoreCertEntity;
import com.efida.sports.cert.model.CerpicInfoModel;
import com.efida.sports.cert.service.cert.ScoreCertServcie;
import com.efida.sports.cert.service.certpic.CertPicService;
import com.efida.sports.cert.utils.ApplicationContextProvider;

/**
 * 证书图片处理线程
 * @author wang yi
 * @desc 
 * @version $Id: CertPicHander.java, v 0.1 2018年8月8日 下午12:18:33 wang yi Exp $
 */

public class CertPicHanderTeard implements Runnable {

    private Logger           logger = LoggerFactory.getLogger(this.getClass());

    private CacheService     cacheSevcice;

    private CertPicService   certPicService;

    private ScoreCertServcie scoreCetService;

    private ScoreCertEntity  data;

    public CertPicHanderTeard(ScoreCertEntity data) {
        super();
        //new的时候注入需要的bean
        this.data = data;
        this.cacheSevcice = ApplicationContextProvider.getBean(CacheService.class);
        this.certPicService = ApplicationContextProvider.getBean(CertPicService.class);
        this.scoreCetService = ApplicationContextProvider.getBean(ScoreCertServcie.class);
    }

    @Override
    public void run() {
        this.processPic(data);
    }

    @Transactional
    public void processPic(ScoreCertEntity scoreCertEntity) {
        long start = System.currentTimeMillis();
        logger.info("开始处理,证书编号： " + scoreCertEntity.getCertSn());
        String lockKey = getScoreLockey(scoreCertEntity);
        long id = scoreCertEntity.getId();
        String lock = cacheSevcice.get(lockKey);
        if (StringUtils.isNotBlank(lock)) {
            logger.info("放弃处理 证书编号数据 :{} 原因: 已被其他线程处理 ", scoreCertEntity.getCertSn());
            return;
        }
        ScoreCertEntity sct = scoreCetService.getCertByIdForUpdate(id);
        Boolean isPicOk = sct.getIsPicOk();
        if (isPicOk) {
            logger.info("放弃处理证书编号:{} 状态已改变为成功", scoreCertEntity.getCertSn());
            return;
        }
        cacheSevcice.put(lockKey, "lock", 10);
        CerpicInfoModel cerpicInfoModel = new CerpicInfoModel();
        cerpicInfoModel.setCertSn(scoreCertEntity.getCertSn());
        cerpicInfoModel.setCertTime(scoreCertEntity.getCertTime());
        cerpicInfoModel.setPartTime(scoreCertEntity.getPartTime());
        cerpicInfoModel.setCertName(scoreCertEntity.getCertName());
        cerpicInfoModel.setCertMatchName(scoreCertEntity.getCertMatchName());
        cerpicInfoModel.setCertEventName(scoreCertEntity.getCertEventName());
        cerpicInfoModel.setScore(scoreCertEntity.getScore());
        cerpicInfoModel.setScoreUnit(scoreCertEntity.getScoreUnit());
        cerpicInfoModel.setScoreDesc(scoreCertEntity.getScoreDesc());
        String certPicFastDfs = certPicService.generateCertPicFastDfs(cerpicInfoModel);
        if (StringUtils.isNotBlank(certPicFastDfs)) {
            scoreCetService.upSynScoreCertByCertNo(cerpicInfoModel.getCertSn(), certPicFastDfs);
        }
        cacheSevcice.remove(lockKey);
        long endTime = System.currentTimeMillis();
        logger.info("总共耗时:{} ms", endTime - start);
    }

    /**
     * 获取赛事成绩证书Lockkey
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scoreCertEntity
     * @return
     */
    private String getScoreLockey(ScoreCertEntity scoreCertEntity) {
        return "cert:" + scoreCertEntity.getId() + "" + scoreCertEntity.getCertCode();
    }

}
