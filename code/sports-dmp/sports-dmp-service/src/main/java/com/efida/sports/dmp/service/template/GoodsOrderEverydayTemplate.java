package com.efida.sports.dmp.service.template;

import java.util.ArrayList;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.utils.DateUtil;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollTemplate.java, v 0.1 2018年8月31日 下午9:42:51 zengbo Exp $
 */
public class GoodsOrderEverydayTemplate {

    @ExcelAttribute(column = "A", name = "日期")
    private String date;

    @ExcelAttribute(column = "B", name = "完赛人数")
    private int    finishCount;

    @ExcelAttribute(column = "C", name = "电子奖章领取人数")
    private int    medalReceive;

    @ExcelAttribute(column = "D", name = "实物奖章领取人数")
    private int    entityReceive;

    @ExcelAttribute(column = "E", name = "实体奖章在线订购数")
    private int    onlineOrder;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public int getMedalReceive() {
        return medalReceive;
    }

    public void setMedalReceive(int medalReceive) {
        this.medalReceive = medalReceive;
    }

    public int getEntityReceive() {
        return entityReceive;
    }

    public void setEntityReceive(int entityReceive) {
        this.entityReceive = entityReceive;
    }

    public int getOnlineOrder() {
        return onlineOrder;
    }

    public void setOnlineOrder(int onlineOrder) {
        this.onlineOrder = onlineOrder;
    }

    public static List<GoodsOrderEverydayTemplate> increaseRegisters(List<ReportGoodsOrder> orders) {
        ArrayList<GoodsOrderEverydayTemplate> list = new ArrayList<GoodsOrderEverydayTemplate>();
        if (orders == null || orders.size() < 1) {
            return list;
        }
        for (ReportGoodsOrder order : orders) {
            GoodsOrderEverydayTemplate temp = new GoodsOrderEverydayTemplate();
            String dates = DateUtil.format(order.getDate(), DateUtil.WEB_FORMAT);
            temp.setDate(dates);
            temp.setFinishCount(order.getFinishCount());
            temp.setMedalReceive(order.getMedalReceive());
            temp.setEntityReceive(order.getEntityReceive());
            temp.setOnlineOrder(order.getOnlineOrder());
            list.add(temp);
        }
        return list;
    }

}
