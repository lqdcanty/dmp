package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportMatchSourceVo.java, v 0.1 2018年8月30日 下午7:38:27 zengbo Exp $
 */
public class ReportMatchSourceVo {

    private String matchCode;

    private String matchName;

    private int    enrollCount;

    private int    finishCount;

    private String percent;

    public static List<ReportMatchSourceVo> coverToVos(List<ReportMatchSourceEntity> list) {
        List<ReportMatchSourceVo> results = new ArrayList<ReportMatchSourceVo>();
        for (ReportMatchSourceEntity openScoreEntity : list) {
            results.add(coverToVo(openScoreEntity));
        }
        return results;
    }

    public static ReportMatchSourceVo coverToVo(ReportMatchSourceEntity entity) {
        ReportMatchSourceVo vo = new ReportMatchSourceVo();
        if (entity != null) {
            vo.setMatchCode(entity.getMatchCode());
            vo.setMatchName(entity.getMatchName());
            vo.setEnrollCount(entity.getOfficialCount());
        }
        return vo;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public int getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        this.enrollCount = enrollCount;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

}
