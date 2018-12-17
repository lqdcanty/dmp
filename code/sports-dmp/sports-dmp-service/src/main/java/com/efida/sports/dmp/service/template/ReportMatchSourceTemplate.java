package com.efida.sports.dmp.service.template;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollTemplate.java, v 0.1 2018年8月31日 下午9:42:51 zengbo Exp $
 */
public class ReportMatchSourceTemplate {

    @ExcelAttribute(column = "A", name = "赛事名称")
    private String matchName;

    @ExcelAttribute(column = "B", name = "承办方")
    private String unitName;

    @ExcelAttribute(column = "C", name = "总报名人次")
    private int    enrollTotalCount;

    @ExcelAttribute(column = "D", name = "举办方数据")
    private int    organizerCount;

    @ExcelAttribute(column = "E", name = "官方数据")
    private int    officialCount;

    @ExcelAttribute(column = "F", name = "举办方数据占比")
    private String organizerPercent;

    @ExcelAttribute(column = "G", name = "官方数据占比")
    private String officialPercent;

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getEnrollTotalCount() {
        return enrollTotalCount;
    }

    public void setEnrollTotalCount(int enrollTotalCount) {
        this.enrollTotalCount = enrollTotalCount;
    }

    public int getOrganizerCount() {
        return organizerCount;
    }

    public void setOrganizerCount(int organizerCount) {
        this.organizerCount = organizerCount;
    }

    public int getOfficialCount() {
        return officialCount;
    }

    public void setOfficialCount(int officialCount) {
        this.officialCount = officialCount;
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

    public static List<ReportMatchSourceTemplate> increaseRegisters(List<ReportMatchSourceEntity> models) {
        ArrayList<ReportMatchSourceTemplate> list = new ArrayList<ReportMatchSourceTemplate>();
        if (models == null || models.size() < 1) {
            return list;
        }
        for (ReportMatchSourceEntity entity : models) {
            ReportMatchSourceTemplate temp = new ReportMatchSourceTemplate();
            int total = entity.getEnrollTotalCount();
            int organizer = entity.getOrganizerCount();
            float res = (float) organizer / total;
            float b = (float) (Math.round(res * 10000)) / 10000;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String percent = fnum.format(b * 100);
            String organizerPercent = percent + "%";
            String officialPercent = fnum.format(100 - Float.valueOf(percent)) + "%";
            temp.setMatchName(entity.getMatchName());
            temp.setUnitName(entity.getUnitName());
            temp.setEnrollTotalCount(entity.getEnrollTotalCount());
            temp.setOrganizerCount(entity.getOrganizerCount());
            temp.setOfficialCount(entity.getOfficialCount());
            temp.setOrganizerPercent(organizerPercent);
            temp.setOfficialPercent(officialPercent);
            list.add(temp);
        }
        return list;
    }

}
