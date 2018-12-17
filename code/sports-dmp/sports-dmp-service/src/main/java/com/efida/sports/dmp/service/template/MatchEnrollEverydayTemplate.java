package com.efida.sports.dmp.service.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollTemplate.java, v 0.1 2018年8月31日 下午9:42:51 zengbo Exp $
 */
public class MatchEnrollEverydayTemplate {

    @ExcelAttribute(column = "A", name = "日期")
    private String date;

    @ExcelAttribute(column = "B", name = "单个赛事当日新增报名人次")
    private int    enrollDay;

    @ExcelAttribute(column = "C", name = "赛事系统当日新增报名人次")
    private int    enrollTotalDay;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEnrollDay() {
        return enrollDay;
    }

    public void setEnrollDay(int enrollDay) {
        this.enrollDay = enrollDay;
    }

    public int getEnrollTotalDay() {
        return enrollTotalDay;
    }

    public void setEnrollTotalDay(int enrollTotalDay) {
        this.enrollTotalDay = enrollTotalDay;
    }

    public static List<MatchEnrollEverydayTemplate> increaseRegisters(List<Map<String, Object>> matchs) {
        ArrayList<MatchEnrollEverydayTemplate> list = new ArrayList<MatchEnrollEverydayTemplate>();
        if (matchs == null || matchs.size() < 1) {
            return list;
        }
        for (Map<String, Object> match : matchs) {
            MatchEnrollEverydayTemplate temp = new MatchEnrollEverydayTemplate();
            String dates = (String) match.get("date");
            int enroll = (int) match.get("enrollDay");
            int enrollTotal = (int) match.get("enrollTotalDay");
            temp.setDate(dates);
            temp.setEnrollDay(enroll);
            temp.setEnrollTotalDay(enrollTotal);
            list.add(temp);
        }
        return list;
    }

}
