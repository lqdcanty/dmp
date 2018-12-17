/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.dmp.facade.model.ScoreCertModel;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;

import cn.evake.auth.util.DateUtil;

/**
 * 
 * @author zoutao
 * @version $Id: ScoreCertCover.java, v 0.1 2018年8月6日 下午4:36:41 zoutao Exp $
 */
public class ScoreCertCover {
    public static List<ScoreCertModel> entitys2models(List<ScoreCertEntity> list) {
        List<ScoreCertModel> models = new ArrayList<ScoreCertModel>();
        if (list == null || list.size() < 1) {
            return models;
        }
        for (ScoreCertEntity entity : list) {
            ScoreCertModel model = entity2model(entity);
            if (model != null) {
                models.add(model);
            }
        }
        return models;
    }

    public static ScoreCertModel entity2model(ScoreCertEntity entity) {
        if (entity == null) {
            return null;
        }
        ScoreCertModel model = new ScoreCertModel();
        model.setCertPicUrl(entity.getCertPicUrl());
        model.setEventName(entity.getEventName());
        model.setMatchName(entity.getMatchName());
        model.setPartTime(entity.getPartTime());
        model.setPartTimeStr(DateUtil.format(entity.getPartTime(), DateUtil.LONG_WEB_FORMAT));
        model.setCertShortPicuUrl(entity.getCertShortPicUrl());
        model.setScoreCertCode(entity.getCertCode());
        model.setScoreCertSn(entity.getCertSn());
        return model;
    }

}
