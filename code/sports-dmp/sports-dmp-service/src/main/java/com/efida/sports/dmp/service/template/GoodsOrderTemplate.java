package com.efida.sports.dmp.service.template;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 商品订单模板
 * 
 * @author yanglei
 * @version $Id: GoodsOrderTemplate.java, v 0.1 2018年10月14日 下午2:24:27 yanglei Exp $
 */
public class GoodsOrderTemplate {
    @ExcelAttribute(name = "订单编号", column = "A")
    private String orderCode;
    @ExcelAttribute(name = "订单时间", column = "B")
    private String orderTime;
    @ExcelAttribute(name = "订单状态", column = "C")
    private String orderStatus;
    @ExcelAttribute(name = "发货时间", column = "D")
    private String optTime;
    @ExcelAttribute(name = "购买数量", column = "E")
    private int    goodsNum;
    @ExcelAttribute(name = "购买Id", column = "F")
    private String loginPhone;
    @ExcelAttribute(name = "用户姓名", column = "G")
    private String realname;
    @ExcelAttribute(name = "用户手机号", column = "H")
    private String mobile;
    @ExcelAttribute(name = "省份", column = "I")
    private String province;
    @ExcelAttribute(name = "城市", column = "J")
    private String city;
    @ExcelAttribute(name = "地区", column = "K")
    private String area;
    @ExcelAttribute(name = "详细地址", column = "L")
    private String address;
    @ExcelAttribute(name = "备注", column = "M")
    private String remark;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

}
