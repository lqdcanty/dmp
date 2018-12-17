package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.utils.DateUtil;

public class ReportGoodsOrderVo {

    private Date    date;

    private Integer finishCount;

    private Integer medalReceive;

    private Integer entityReceive;

    private Integer onlineOrder;

    private String  dateStr;

    public static List<ReportGoodsOrderVo> getReportGoodsOrderVoList(List<ReportGoodsOrder> orders) {
        List<ReportGoodsOrderVo> vos = new ArrayList<ReportGoodsOrderVo>();
        if (orders == null || orders.size() <= 0) {
            return vos;
        }
        for (ReportGoodsOrder order : orders) {
            ReportGoodsOrderVo vo = getReportGoodsOrderVo(order);
            if (vo != null) {
                vos.add(vo);
            }
        }
        return vos;
    }

    public static ReportGoodsOrderVo getReportGoodsOrderVo(ReportGoodsOrder order) {
        ReportGoodsOrderVo vo = new ReportGoodsOrderVo();
        if (order != null) {
            String dateStrs = DateUtil.format(order.getDate(), DateUtil.WEB_FORMAT);
            vo.setDate(order.getDate());
            vo.setFinishCount(order.getFinishCount());
            vo.setMedalReceive(order.getMedalReceive());
            vo.setEntityReceive(order.getEntityReceive());
            vo.setOnlineOrder(order.getOnlineOrder());
            vo.setDateStr(dateStrs);
            return vo;
        }
        return vo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getMedalReceive() {
        return medalReceive;
    }

    public void setMedalReceive(Integer medalReceive) {
        this.medalReceive = medalReceive;
    }

    public Integer getEntityReceive() {
        return entityReceive;
    }

    public void setEntityReceive(Integer entityReceive) {
        this.entityReceive = entityReceive;
    }

    public Integer getOnlineOrder() {
        return onlineOrder;
    }

    public void setOnlineOrder(Integer onlineOrder) {
        this.onlineOrder = onlineOrder;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}