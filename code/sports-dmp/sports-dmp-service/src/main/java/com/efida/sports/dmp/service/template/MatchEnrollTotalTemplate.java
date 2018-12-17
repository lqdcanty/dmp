package com.efida.sports.dmp.service.template;

import java.util.ArrayList;
import java.util.List;

import com.efida.sports.dmp.utils.NumberUtil;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 赛事报名总数模板
 * 
 * @author zengbo
 * @version $Id: MatchEnrollTotalTemplate.java, v 0.1 2018年10月9日 下午3:56:29 zengbo Exp $
 */
public class MatchEnrollTotalTemplate {

    @ExcelAttribute(column = "A", name = "报名人数")
    private int    enrollPeople;

    @ExcelAttribute(column = "B", name = "完赛人数")
    private int    finishPeople;

    @ExcelAttribute(column = "C", name = "人数完赛比率")
    private String peoplePercent;

    @ExcelAttribute(column = "D", name = "报名人次")
    private int    enroll;

    @ExcelAttribute(column = "E", name = "完赛人次")
    private int    finish;

    @ExcelAttribute(column = "F", name = "人次完赛比率")
    private String percent;

    public int getEnrollPeople() {
        return enrollPeople;
    }

    public void setEnrollPeople(int enrollPeople) {
        this.enrollPeople = enrollPeople;
    }

    public int getFinishPeople() {
        return finishPeople;
    }

    public void setFinishPeople(int finishPeople) {
        this.finishPeople = finishPeople;
    }

    public String getPeoplePercent() {
        return peoplePercent;
    }

    public void setPeoplePercent(String peoplePercent) {
        this.peoplePercent = peoplePercent;
    }

    public int getEnroll() {
        return enroll;
    }

    public void setEnroll(int enroll) {
        this.enroll = enroll;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public static List<MatchEnrollTotalTemplate> increaseRegisters(int enroll, int finish,
                                                                   int enrollPeople,
                                                                   int finishPeople) {
        ArrayList<MatchEnrollTotalTemplate> list = new ArrayList<MatchEnrollTotalTemplate>();
        MatchEnrollTotalTemplate temp = new MatchEnrollTotalTemplate();
        String percent = NumberUtil.percentageConvert(finish, enroll);
        String peoplePercent = NumberUtil.percentageConvert(finishPeople, enrollPeople);
        temp.setEnroll(enroll);
        temp.setFinish(finish);
        temp.setPercent(percent);
        temp.setEnrollPeople(enrollPeople);
        temp.setFinishPeople(finishPeople);
        temp.setPeoplePercent(peoplePercent);
        list.add(temp);
        return list;
    }

}
