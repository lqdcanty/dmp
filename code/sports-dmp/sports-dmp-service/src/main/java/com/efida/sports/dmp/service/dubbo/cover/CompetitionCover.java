/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.dmp.facade.model.CompetitionModel;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;

/**
 * 
 * @author zoutao
 * @version $Id: CompetitionCover.java, v 0.1 2018年7月28日 下午1:53:54 zoutao Exp $
 */
public class CompetitionCover {

    public static List<CompetitionModel> entitys2models(List<CompetitionEntity> list) {
        List<CompetitionModel> itmes = new ArrayList<CompetitionModel>();
        if (list == null || list.size() < 1) {
            return itmes;
        }
        for (CompetitionEntity entity : list) {
            if (entity == null) {
                continue;
            }
            itmes.add(entity2Model(entity));
        }
        return itmes;
    }

    public static CompetitionModel entity2Model(CompetitionEntity entity) {
        if (entity == null) {
            return null;
        }
        CompetitionModel model = new CompetitionModel();
        model.setCompetitionCode(entity.getCompetitionCode());
        model.setCompetitionDate(entity.getCompetitionDate());
        model.setCompetitionName(entity.getName());
        model.setIndexSort(entity.getSortIndex() == null ? 0 : entity.getSortIndex());
        model.setAreas(entity.getAreas());
        model.setGroups(entity.getGroups());
        model.setEvents(entity.getEvents());
        MatchDetailModel matchDetail = entity.getMatchDetail();
        if (matchDetail != null) {
            model.setRelationAreas(CompetitionRelationCover.getRelationsBySites(entity.getAreas(),
                matchDetail.getFieldTypeList()));
            model.setRelationEvents(CompetitionRelationCover
                .getRelationsByEvents(entity.getEvents(), matchDetail.getEventTypeList()));
            model.setRelationGroups(CompetitionRelationCover
                .getRelationsByGroups(entity.getGroups(), matchDetail.getGroupTypeList()));
        }
        return model;
    }

}
