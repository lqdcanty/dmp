/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.report.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.efida.sports.dmp.biz.open.OpenEnrollService;
import com.efida.sports.dmp.biz.report.UserPartAnalysisService;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;

/**
 * 
 * @author lizhiyang
 * @version $Id: UserPartAnalysisServiceImpl.java, v 0.1 2018年8月31日 下午3:29:39 lizhiyang Exp $
 */
@Service("userPartAnalysisService")
public class UserPartAnalysisServiceImpl implements UserPartAnalysisService {

    private Logger                logger = LoggerFactory
        .getLogger(UserPartAnalysisServiceImpl.class);

    @Autowired
    private OpenScoreEntityMapper openScoreEntityMapper;

    @Autowired
    private OpenEnrollService     openEnrollService;

    /**
     * 
     * @see com.efida.sports.dmp.biz.report.UserPartAnalysisService#computeUsertPartSoruce()
     */
    @Override
    public void computeUsertPartSoruce() {

        List<String> eventCodes = queryUnComputeEventCode(1, 100);
        if (CollectionUtils.isEmpty(eventCodes)) {
            return;
        }
        for (String eventCode : eventCodes) {
            computeUsertPartSoruce(eventCode);
        }
    }

    public String getUserId(OpenScoreEntity enrollInfo) {

        String idempotentId = enrollInfo.getPlayerPhone();
        if (idempotentId == null) {
            idempotentId = "";
        }
        if (!StringUtils.isEmpty(enrollInfo.getPlayerName())) {
            idempotentId += "_" + enrollInfo.getPlayerName();
        } else {
            if (StringUtils.isEmpty(enrollInfo.getOpenId())) {
                if (!StringUtils.isEmpty(enrollInfo.getOpenPlatType())) {
                    idempotentId += "_" + enrollInfo.getOpenPlatType();
                }
                idempotentId += enrollInfo.getOpenId();
            }
        }
        if (idempotentId.length() > 64) {
            idempotentId = idempotentId.substring(0, 64);
        }

        return idempotentId;
    }

    /**
     * 对成绩数据对于报名来源进行分析。 
     * 
     * @param eventCode
     * @return
     */
    public int computeUsertPartSoruce(String eventCode) {
        if (StringUtils.isEmpty(eventCode)) {
            return 0;
        }

        logger.info("start call computeUsertPartSoruce:" + eventCode);
        long start = System.currentTimeMillis();
        Set<String> set = this.openEnrollService.loadOurEnrollInfoByEvent(eventCode, 200000);

        int pageNumber = 1;
        int pageSize = 200;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", (pageNumber - 1) * pageSize);
        map.put("limit", pageSize);
        map.put("eventCode", eventCode);

        int total = 0;
        while (true) {
            List<OpenScoreEntity> items = this.openScoreEntityMapper.queryUnMatchRecordByMap(map);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            for (OpenScoreEntity item : items) {
                total++;
                String userId = this.getUserId(item);
                if (set.contains(userId)) {
                    item.setEnrollSource(1);
                } else {
                    item.setEnrollSource(2);
                }
                OpenScoreEntity record = new OpenScoreEntity();
                record.setId(item.getId());
                record.setEnrollSource(item.getEnrollSource());
                this.openScoreEntityMapper.updateByPrimaryKeySelective(record);
            }
            if (items.size() < pageSize) {
                break;
            }
        }

        long spent = System.currentTimeMillis() - start;
        logger.info("end call computeUsertPartSoruce:" + eventCode + ", process:" + total
                    + ", spent:" + spent + "ms.");

        return 0;
    }

    private List<String> queryUnComputeEventCode(int pageNumber, int pageSize) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", (pageNumber - 1) * pageSize);
        map.put("limit", pageSize);
        List<String> items = this.openScoreEntityMapper.queryUnComputeEventCodeByMap(map);

        return items;
    }

}
