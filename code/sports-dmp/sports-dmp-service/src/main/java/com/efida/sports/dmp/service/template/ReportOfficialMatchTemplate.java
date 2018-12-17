package com.efida.sports.dmp.service.template;

import java.util.ArrayList;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.utils.NumberUtil;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollTemplate.java, v 0.1 2018年8月31日 下午9:42:51 zengbo Exp $
 */
public class ReportOfficialMatchTemplate {

    @ExcelAttribute(column = "A", name = "赛事名称")
    private String matchName;

    @ExcelAttribute(column = "B", name = "报名人次")
    private int    enrollCount;

    @ExcelAttribute(column = "C", name = "完赛人次")
    private int    finishCount;

    @ExcelAttribute(column = "D", name = "完赛率")
    private String percent;

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

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public static List<ReportOfficialMatchTemplate> increaseRegisters(List<ReportMatchSourceEntity> models) {
        ArrayList<ReportOfficialMatchTemplate> list = new ArrayList<ReportOfficialMatchTemplate>();
        if (models == null || models.size() < 1) {
            return list;
        }
        for (ReportMatchSourceEntity entity : models) {
            ReportOfficialMatchTemplate temp = new ReportOfficialMatchTemplate();
            temp.setMatchName(entity.getMatchName());
            temp.setEnrollCount(entity.getOfficialCount());
            temp.setFinishCount(entity.getEnrollTotalCount());
            String percent = NumberUtil.percentageConvertDecimal(temp.getFinishCount(),
                temp.getEnrollCount());
            temp.setPercent(percent);
            list.add(temp);
        }
        return list;
    }

}
