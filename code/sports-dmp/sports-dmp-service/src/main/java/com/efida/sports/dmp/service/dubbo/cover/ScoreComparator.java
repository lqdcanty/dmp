package com.efida.sports.dmp.service.dubbo.cover;

import java.util.Comparator;

import com.efida.sport.dmp.facade.model.ScoreModel;

public class ScoreComparator implements Comparator {

    private boolean isBigOk = true;

    public boolean isBigOk() {
        return isBigOk;
    }

    public void setBigOk(boolean isBigOk) {
        this.isBigOk = isBigOk;
    }

    @Override
    public int compare(Object o1, Object o2) {
        ScoreModel x1 = (ScoreModel) o1;
        ScoreModel x2 = (ScoreModel) o2;
        if (x1.getScore().doubleValue() > x2.getScore().doubleValue()) {
            if (!isBigOk) {
                return 1;
            } else {
                return -1;
            }
        }
        if (x1.getScore().doubleValue() < x2.getScore().doubleValue()) {
            if (!isBigOk) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

}
