package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.dmp.facade.model.ScoreMatchModel;

public class ScoreMatchCover {
    public static List<ScoreMatchModel> geModels(List<SpMatchModel> matchs) {
        List<ScoreMatchModel> list = new ArrayList<ScoreMatchModel>();
        if (matchs == null || matchs.size() < 1) {
            return list;
        }
        for (SpMatchModel model : matchs) {
            list.add(getModel(model));
        }
        return list;

    }

    public static ScoreMatchModel getModel(SpMatchModel match) {
        if (match == null) {
            return null;
        }
        ScoreMatchModel model = new ScoreMatchModel();
        model.setMatchCode(match.getMatchCode());
        model.setMatchImg(match.getPoster());
        model.setMatchName(match.getMatchName());
        model.setStartTime(match.getStartTime());
        model.setEndTime(match.getEndTime());
        return model;

    }
}
