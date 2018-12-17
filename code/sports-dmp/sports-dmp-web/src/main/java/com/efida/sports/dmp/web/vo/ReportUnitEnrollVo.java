package com.efida.sports.dmp.web.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.utils.NumberUtil;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollVo.java, v 0.1 2018年8月30日 下午10:55:52 zengbo Exp $
 */
public class ReportUnitEnrollVo {

    private String  unitCode;

    private String  unitName;

    private Integer enrollCount;

    private Integer enrollTotalCount;

    private Integer totalCount;

    private Date    applyTime;

    private String  percent;

    private String  applyTimeStr;

    public static List<ReportUnitEnrollVo> coverToVos(List<ReportUnitEnrollEntity> list) {
        List<ReportUnitEnrollVo> results = new ArrayList<ReportUnitEnrollVo>();
        for (ReportUnitEnrollEntity openScoreEntity : list) {
            ReportUnitEnrollVo vo = coverToVo(openScoreEntity);
            results.add(vo);
        }
        return results;
    }

    public static List<ReportUnitEnrollVo> coverToVosNew(List<ReportUnitEnrollEntity> list,
                                                         List<Map<String, Object>> units) {
        List<ReportUnitEnrollVo> results = new ArrayList<ReportUnitEnrollVo>();
        for (ReportUnitEnrollEntity openScoreEntity : list) {
            ReportUnitEnrollVo vo = coverToVoNew(openScoreEntity, units);
            results.add(vo);
        }
        return results;
    }

    public static ReportUnitEnrollVo coverToVo(ReportUnitEnrollEntity entity) {
        ReportUnitEnrollVo vo = new ReportUnitEnrollVo();
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

    public static ReportUnitEnrollVo coverToVoNew(ReportUnitEnrollEntity entity,
                                                  List<Map<String, Object>> units) {
        ReportUnitEnrollVo vo = new ReportUnitEnrollVo();
        if (entity != null) {
            int total = getEnrollTotal(units);
            int enrollTotalCount = getEnrollTotalByCode(entity.getUnitCode(), units);
            String percent = NumberUtil.percentageConvertDecimal(enrollTotalCount, total);
            vo.setUnitCode(entity.getUnitCode());
            vo.setUnitName(entity.getUnitName());
            vo.setEnrollCount(entity.getEnrollCount());
            vo.setEnrollTotalCount(enrollTotalCount);
            vo.setApplyTime(entity.getApplyTime());
            vo.setTotalCount(enrollTotalCount);
            vo.setPercent(percent);
            vo.setApplyTimeStr(DateUtil.formatDate(entity.getApplyTime()));
        }
        return vo;
    }

    public static int getEnrollTotal(List<Map<String, Object>> units) {
        int total = 0;
        if (units != null && units.size() > 0) {
            for (Map<String, Object> unit : units) {
                BigDecimal decimal = (BigDecimal) unit.get("enrollCount");
                total = total + decimal.intValue();
            }
        }
        return total;
    }

    public static int getEnrollTotalByCode(String unitCode, List<Map<String, Object>> units) {
        int total = 0;
        if (units != null && units.size() > 0) {
            for (Map<String, Object> unit : units) {
                String codes = (String) unit.get("unitCode");
                if (unitCode.equals(codes)) {
                    BigDecimal decimal = (BigDecimal) unit.get("enrollCount");
                    return decimal.intValue();
                }
            }
        }
        return total;
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
