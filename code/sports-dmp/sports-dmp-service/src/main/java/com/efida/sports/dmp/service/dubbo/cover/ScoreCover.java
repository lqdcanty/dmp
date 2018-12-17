/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.dmp.facade.model.ScoreDetail;
import com.efida.sport.dmp.facade.model.ScoreModel;
import com.efida.sport.dmp.facade.model.SoreExtPro;
import com.efida.sport.facade.enums.SexEnum;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;

import cn.evake.auth.util.DateUtil;

/**
 * 
 * @author zoutao
 * @version $Id: SoreExtProCover.java, v 0.1 2018年8月3日 下午4:41:46 zoutao Exp $
 */
public class ScoreCover {

    public static List<SoreExtPro> scoreCoverSoreExtPro(List<OpenScoreEntity> scores) {

        ArrayList<SoreExtPro> list = new ArrayList<SoreExtPro>();
        if (scores == null || scores.size() < 1) {
            return list;
        }
        for (OpenScoreEntity openScoreEntity : scores) {
            list.add(getSoreExtPro(openScoreEntity));
        }
        return list;
    }

    public static ScoreDetail scoreCoverScoreDetail(List<OpenScoreEntity> scores) {

        ScoreDetail detail = new ScoreDetail();
        detail.setCurentPage(1);
        if (scores == null || scores.size() < 1) {
            detail.setTotal(0);
            return detail;
        }
        detail.setTotal(scores.size());
        List<List<String>> records = new ArrayList<List<String>>();

        for (OpenScoreEntity score : scores) {
            List<String> record = new ArrayList<String>();
            SoreExtPro citem = getSoreExtPro(score);

            record.add(citem.getGenderStr());
            record.add(citem.getMatchName());
            record.add(citem.getSiteName());
            record.add(citem.getGroupName());
            record.add(citem.getEventName());
            record.add(citem.getPartTimeStr());
            record.add(citem.getScoreDesc());
            records.add(record);
        }

        detail.setData(records);
        return detail;
    }

    public static SoreExtPro getSoreExtPro(OpenScoreEntity openScoreEntity) {
        if (openScoreEntity == null) {
            return null;
        }
        SoreExtPro pro = new SoreExtPro();
        pro.setEventName(openScoreEntity.getEventName());
        pro.setGender(openScoreEntity.getSex());
        pro.setGenderStr(SexEnum.getDescByCode(openScoreEntity.getSex()));
        pro.setGroupName(openScoreEntity.getMatchGroupName());
        pro.setMatchName(openScoreEntity.getMatchName());
        pro.setPartTime(openScoreEntity.getPartTime());
        pro.setPartTimeStr(
            DateUtil.format(openScoreEntity.getPartTime(), DateUtil.LONG_WEB_FORMAT));
        pro.setScoreDesc(openScoreEntity.getScoreDesc());
        pro.setSiteName(openScoreEntity.getFieldName());
        return pro;

    }

    public static ScoreModel score2model(OpenScoreEntity openScoreEntity, String competitionCode) {
        if (openScoreEntity == null) {
            return null;
        }
        ScoreModel model = new ScoreModel();
        model.setEventName(openScoreEntity.getEventName());
        model.setGender(openScoreEntity.getSex());
        model.setGenderStr(SexEnum.getDescByCode(openScoreEntity.getSex()));
        model.setGroupName(openScoreEntity.getMatchGroupName());
        model.setMatchCode(openScoreEntity.getMatchCode());
        model.setMatchName(openScoreEntity.getMatchName());
        model.setPartTime(openScoreEntity.getPartTime());
        model.setPartTimeStr(
            DateUtil.format(openScoreEntity.getPartTime(), DateUtil.LONG_WEB_FORMAT));
        model.setScoreDesc(openScoreEntity.getScoreDesc());
        model.setScore(openScoreEntity.getScore());
        model.setSiteName(openScoreEntity.getFieldName());
        model.setCompetitionCode(competitionCode);
        model.setPalyerCode(openScoreEntity.getPlayerCode());
        model.setPalyerName(openScoreEntity.getPlayerName());
        model.setRanking("-");
        return model;
    }

    public static List<ScoreModel> scores2models(List<OpenScoreEntity> openScores,
                                                 String competitionCode) {
        List<ScoreModel> list = new ArrayList<ScoreModel>();
        if (openScores == null || openScores.size() < 1) {
            return list;
        }
        for (OpenScoreEntity entity : openScores) {
            ScoreModel scoreModel = score2model(entity, competitionCode);
            if (scoreModel != null) {
                list.add(scoreModel);
            }
        }
        return list;
    }

}
