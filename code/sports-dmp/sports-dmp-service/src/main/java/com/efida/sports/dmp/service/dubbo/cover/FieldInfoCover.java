package com.efida.sports.dmp.service.dubbo.cover;

import com.efida.sport.dmp.facade.model.FieldInfoModel;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

import java.util.ArrayList;
import java.util.List;

public class FieldInfoCover {
    public static List<FieldInfoModel> entitys2models(List<OpenScoreRankEntity> list) {
        List<FieldInfoModel> itmes = new ArrayList<FieldInfoModel>();
        if (list == null || list.size() < 1) {
            return itmes;
        }
        for (OpenScoreRankEntity entity : list) {
            if (entity == null) {
                continue;
            }
            itmes.add(entity2Model(entity));
        }
        return itmes;
    }

    public static FieldInfoModel entity2Model(OpenScoreRankEntity entity) {
        if (entity == null) {
            return null;
        }
        FieldInfoModel model = new FieldInfoModel();
        model.setCompetitionCode(entity.getCompetitionCode());
        model.setArea(entity.getFieldName());
        model.setEvent(entity.getEventName());
        model.setGroup(entity.getMatchGroupName());
        model.setAreaCode(entity.getFieldCode());
        model.setEventCode(entity.getEventCode());
        model.setGroupCode(entity.getMatchGroupCode());
        return model;
    }

}
