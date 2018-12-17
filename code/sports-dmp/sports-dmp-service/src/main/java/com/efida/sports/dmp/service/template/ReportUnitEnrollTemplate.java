package com.efida.sports.dmp.service.template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.utils.NumberUtil;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollTemplate.java, v 0.1 2018年8月31日 下午9:42:51 zengbo Exp $
 */
public class ReportUnitEnrollTemplate {

    @ExcelAttribute(column = "A", name = "承办方")
    private String unitName;

    @ExcelAttribute(column = "B", name = "当日新增报名人次")
    private int    enrollCount;

    @ExcelAttribute(column = "C", name = "累积总人次")
    private int    enrollTotalCount;

    @ExcelAttribute(column = "D", name = "总报名人次占比")
    private String percent;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(int enrollCount) {
        this.enrollCount = enrollCount;
    }

    public int getEnrollTotalCount() {
        return enrollTotalCount;
    }

    public void setEnrollTotalCount(int enrollTotalCount) {
        this.enrollTotalCount = enrollTotalCount;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public static List<ReportUnitEnrollTemplate> increaseRegisters(List<ReportUnitEnrollEntity> models,
                                                                   List<Map<String, Object>> units) {
        ArrayList<ReportUnitEnrollTemplate> list = new ArrayList<ReportUnitEnrollTemplate>();
        if (models == null || models.size() < 1) {
            return list;
        }
        for (ReportUnitEnrollEntity entity : models) {
            ReportUnitEnrollTemplate temp = new ReportUnitEnrollTemplate();
            int total = getEnrollTotal(units);
            int enrollTotalCount = getEnrollTotalByCode(entity.getUnitCode(), units);
            String percent = NumberUtil.percentageConvertDecimal(enrollTotalCount, total);
            temp.setUnitName(entity.getUnitName());
            temp.setEnrollCount(entity.getEnrollCount());
            temp.setEnrollTotalCount(enrollTotalCount);
            temp.setPercent(percent);
            list.add(temp);
        }
        return list;
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

}
