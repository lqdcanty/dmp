/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.smartrun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.synch.data.common.dao.ZntyConfigDao;
import com.efida.sports.dmp.synch.data.common.dao.entity.SynRelation;
import com.efida.sports.dmp.synch.data.jiubiao.constants.JiubiaoConstants;
import com.efida.sports.dmp.synch.data.jiubiao.service.JiuBiaoSynService;
import com.efida.sports.dmp.synch.data.smartrun.constants.SmartConstants;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActAndCustomerExt;

/**
 * 数据同步程序
 * @author wang yi
 * @desc 
 * @version $Id: SmartrunSynComp.java, v 0.1 2018年9月3日 下午6:35:11 wang yi Exp $
 */
@Service
public class SmartrunRunSynComp {

    @Autowired
    private SmartrunEnrollService smartEnrollService;

    @Autowired
    private SmartrunSocreService  socreService;

    @Autowired
    private JiuBiaoSynService     jiubiaoSynService;

    @Autowired
    private ZntyConfigDao         zntyDao;

    private Logger                logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 开始同步定向数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     */
    public void synSmartRunApplyInfo() {
        //setp1 获取/读取需要同步的赛事项目
        List<SynRelation> needSynRelation = zntyDao.getNeedSynRelation(SmartConstants.UNITCODE);
        logger.info("开始智能定向报名人数数据 总赛事条数:{}", needSynRelation.size());
        for (SynRelation synRelation : needSynRelation) {
            List<TActAndCustomerExt> enrollInfos = smartEnrollService
                .readCactEnrollInfos(Integer.parseInt(synRelation.getReMatchId()));
            logger.info("开始智能定向报名人数数据 对方赛事id:{} 总条数:{}", synRelation.getReMatchId(),
                enrollInfos.size());
            //获取需要同步的报名数据
            smartEnrollService.synEnrollInfos(enrollInfos, synRelation);
            //同步成绩数据
            socreService.synScores(enrollInfos, synRelation);
        }
    }

    /**
     * 开始同步九镖数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     */
    public void synJiuBiaoInfo() {
        //setp1 获取/读取需要同步的赛事项目
        List<SynRelation> needSynRelation = zntyDao.getNeedSynRelation(JiubiaoConstants.UNITCODE);
        for (SynRelation synRelation : needSynRelation) {
            //获取需要同步的报名数据
            jiubiaoSynService.synEnrollxs(synRelation);
            //同步成绩数据
            jiubiaoSynService.synMatchScores(synRelation);
        }
    }

}
