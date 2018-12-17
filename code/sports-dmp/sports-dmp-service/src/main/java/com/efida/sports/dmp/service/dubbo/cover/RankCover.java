/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.efida.sport.dmp.facade.model.RankModel;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

/**
 * 
 * @author zoutao
 * @version $Id: RankCover.java, v 0.1 2018年7月30日 下午5:48:47 zoutao Exp $
 */

public class RankCover {

    public static List<RankModel> entitys2models(List<OpenScoreRankEntity> list,
                                                 String competitionCode) {
        List<RankModel> models = new ArrayList<RankModel>();
        if (list == null || list.size() < 1) {
            return models;
        }
        for (OpenScoreRankEntity openScoreRankEntity : list) {
            RankModel model = entity2model(openScoreRankEntity, competitionCode);
            if (model != null) {
                models.add(model);
            }
        }
        return models;
    }

    public static RankModel entity2model(OpenScoreRankEntity entity, String competitionCode) {
        if (entity == null) {
            return null;
        }
        RankModel model = new RankModel();
        model.setCompetitionDate(entity.getPartTime());
        model.setPlayerCode(entity.getPlayerCode());
        model.setPlayerName(entity.getPlayerName());
        model.setMatchName(entity.getMatchName());
        model.setRank(entity.getRanking());
        String scoreDesc = "";
        if (entity.getScore() != null) {
            double doubleValue = entity.getScore().doubleValue();
            long dev = (long) doubleValue;
            if (dev == doubleValue) {
                scoreDesc += dev;
            } else {
                scoreDesc += entity.getScore().doubleValue();
            }
        }
        if (entity.getScoreUnit() != null) {
            scoreDesc += entity.getScoreUnit();
        }
        model.setScore(
            StringUtils.isEmpty(entity.getScoreDesc()) ? scoreDesc : entity.getScoreDesc());
        model.setRankCode(entity.getScoreRankCode());
        model.setCompetitionCode(competitionCode);
        return model;

    }

    public static List<RankModel> scores2models(List<OpenScoreEntity> myScores,
                                                String competitionCode) {

        List<RankModel> models = new ArrayList<RankModel>();
        if (myScores == null || myScores.size() < 1) {
            return models;
        }
        for (OpenScoreEntity openScore : myScores) {
            RankModel model = openScore2model(openScore, competitionCode);
            if (model != null) {
                models.add(model);
            }
        }
        return models;

    }

    private static RankModel openScore2model(OpenScoreEntity entity, String competitionCode) {
        //        if (entity == null) {
        //            return null;
        //        }
        //        RankModel model = new RankModel();
        //        model.setCompetitionDate(entity.getPartTime());
        //        model.setPlayerCode(entity.getPlayerCode());
        //        model.setPlayerName(entity.getPlayerName());
        //        model.setMatchName(entity.getMatchName());
        //        model.setRank(entity.get);
        //        model.setScore(entity.getScoreDesc());
        //        model.setRankCode(entity.getScoreRankCode());
        //        model.setCompetitionCode(competitionCode);
        //        return model;
        return null;
    }

}
