package com.efida.sports.dmp.web.vo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.utils.DateUtil;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportMatchSourceDetailVo.java, v 0.1 2018年8月31日 下午12:38:43 zengbo Exp $
 */
public class ReportMatchSourceDetailVo {

    private String  matchCode;

    private String  matchName;

    private String  unitCode;

    private String  unitName;

    private Integer enrollTotalCount;

    private Integer organizerCount;

    private Integer officialCount;

    private Date    applyTime;

    private String  applyTimeStr;
    //承办方占比
    private String  organizerPercent;
    //官方占比
    private String  officialPercent;

    public static List<ReportMatchSourceDetailVo> coverToVos(List<ReportMatchSourceEntity> list) {
        List<ReportMatchSourceDetailVo> results = new ArrayList<ReportMatchSourceDetailVo>();
        for (ReportMatchSourceEntity openScoreEntity : list) {
            results.add(coverToVo(openScoreEntity));
        }
        return results;
    }

    public static ReportMatchSourceDetailVo coverToVo(ReportMatchSourceEntity entity) {
        ReportMatchSourceDetailVo vo = new ReportMatchSourceDetailVo();
        if (entity != null) {
            vo.setMatchCode(entity.getMatchCode());
            vo.setMatchName(entity.getMatchName());
            vo.setUnitCode(entity.getUnitCode());
            vo.setUnitName(entity.getUnitName());
            vo.setApplyTime(entity.getApplyTime());
            vo.setApplyTimeStr("");
            if (entity.getApplyTime() != null) {
                vo.setApplyTimeStr(DateUtil.formatDate(entity.getApplyTime()));
            }
            vo.setEnrollTotalCount(entity.getEnrollTotalCount());
            vo.setOrganizerCount(entity.getOrganizerCount());
            vo.setOfficialCount(entity.getOfficialCount());
            int total = entity.getEnrollTotalCount();
            int organizer = entity.getOrganizerCount();
            float res = (float) organizer / total;
            float b = (float) (Math.round(res * 10000)) / 10000;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String percent = fnum.format(b * 100);
            String organizerPercent = percent + "%";
            String officialPercent = fnum.format(100 - Float.valueOf(percent)) + "%";
            vo.setOrganizerPercent(organizerPercent);
            vo.setOfficialPercent(officialPercent);
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getEnrollTotalCount() {
        return enrollTotalCount;
    }

    public void setEnrollTotalCount(Integer enrollTotalCount) {
        this.enrollTotalCount = enrollTotalCount;
    }

    public Integer getOrganizerCount() {
        return organizerCount;
    }

    public void setOrganizerCount(Integer organizerCount) {
        this.organizerCount = organizerCount;
    }

    public Integer getOfficialCount() {
        return officialCount;
    }

    public void setOfficialCount(Integer officialCount) {
        this.officialCount = officialCount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getOrganizerPercent() {
        return organizerPercent;
    }

    public void setOrganizerPercent(String organizerPercent) {
        this.organizerPercent = organizerPercent;
    }

    public String getOfficialPercent() {
        return officialPercent;
    }

    public void setOfficialPercent(String officialPercent) {
        this.officialPercent = officialPercent;
    }

    public String getApplyTimeStr() {
        return applyTimeStr;
    }

    public void setApplyTimeStr(String applyTimeStr) {
        this.applyTimeStr = applyTimeStr;
    }

}
