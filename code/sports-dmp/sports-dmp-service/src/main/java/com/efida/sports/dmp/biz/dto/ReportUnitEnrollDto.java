package com.efida.sports.dmp.biz.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.utils.NumberUtil;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollVo.java, v 0.1 2018年8月30日 下午10:55:52 zengbo Exp $
 */
public class ReportUnitEnrollDto {

    private String  unitCode;

    private String  unitName;

    private Integer enrollCount;

    private Integer enrollTotalCount;

    private Integer totalCount;

    private Date    applyTime;

    private String  percent;

    private String  applyTimeStr;

    public static List<ReportUnitEnrollDto> coverToVos(List<ReportUnitEnrollEntity> list) {
        List<ReportUnitEnrollDto> results = new ArrayList<ReportUnitEnrollDto>();
        for (ReportUnitEnrollEntity openScoreEntity : list) {
            results.add(coverToVo(openScoreEntity));
        }
        return results;
    }

    public static ReportUnitEnrollDto coverToVo(ReportUnitEnrollEntity entity) {
        ReportUnitEnrollDto vo = new ReportUnitEnrollDto();
        if (entity != null) {
            String percent = NumberUtil.percentageConvert(entity.getEnrollCount(),
                entity.getEnrollTotalCount());
            vo.setUnitCode(entity.getUnitCode());
            vo.setUnitName(entity.getUnitName());
            vo.setEnrollCount(entity.getEnrollCount());
            vo.setEnrollTotalCount(entity.getEnrollTotalCount());
            vo.setApplyTime(entity.getApplyTime());
            vo.setTotalCount(entity.getEnrollTotalCount());
            vo.setPercent(percent);
            vo.setApplyTimeStr(DateUtil.formatDate(entity.getApplyTime()));
        }
        return vo;
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

    public Integer getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(Integer enrollCount) {
        this.enrollCount = enrollCount;
    }

    public Integer getEnrollTotalCount() {
        return enrollTotalCount;
    }

    public void setEnrollTotalCount(Integer enrollTotalCount) {
        this.enrollTotalCount = enrollTotalCount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getApplyTimeStr() {
        return applyTimeStr;
    }

    public void setApplyTimeStr(String applyTimeStr) {
        this.applyTimeStr = applyTimeStr;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
