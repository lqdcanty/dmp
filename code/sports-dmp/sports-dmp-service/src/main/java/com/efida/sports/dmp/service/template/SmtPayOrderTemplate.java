package com.efida.sports.dmp.service.template;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 支付报表模板
 * @author liuchen
 * @desc
 * @version $Id: SmtPayOrderTeplate.java, v 0.1 2018年8月28日 下午5:11:24 liuchen Exp $
 */
public class SmtPayOrderTemplate {

    @ExcelAttribute(name = "时间", column = "A")
    private String  orderDate;

    @ExcelAttribute(name = "订单总数", column = "B")
    private String  ordersTotal;

    @ExcelAttribute(name = "订单总金额(元)", column = "C")
    private String  moneyTotal;

    @ExcelAttribute(name = "支付总数", column = "D")
    private String  payOrdersTotal;

    @ExcelAttribute(name = "支付总金额(元)", column = "E")
    private String  payMoneyTotal;

    @ExcelAttribute(name = "退单总数", column = "F")
    private String  refundOrdersTotal;

    @ExcelAttribute(name = "退单总金额(元)", column = "G")
    private String  refundMoneyTotal;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrdersTotal() {
        return ordersTotal;
    }

    public void setOrdersTotal(String ordersTotal) {
        this.ordersTotal = ordersTotal;
    }

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public String getPayOrdersTotal() {
        return payOrdersTotal;
    }

    public void setPayOrdersTotal(String payOrdersTotal) {
        this.payOrdersTotal = payOrdersTotal;
    }

    public String getPayMoneyTotal() {
        return payMoneyTotal;
    }

    public void setPayMoneyTotal(String payMoneyTotal) {
        this.payMoneyTotal = payMoneyTotal;
    }

    public String getRefundOrdersTotal() {
        return refundOrdersTotal;
    }

    public void setRefundOrdersTotal(String refundOrdersTotal) {
        this.refundOrdersTotal = refundOrdersTotal;
    }

    public String getRefundMoneyTotal() {
        return refundMoneyTotal;
    }

    public void setRefundMoneyTotal(String refundMoneyTotal) {
        this.refundMoneyTotal = refundMoneyTotal;
    }
}
