/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext;

import java.util.Date;

/**
 * 智能定向比赛结果与用户拓展
 * @author wang yi
 * @desc 
 * @version $Id: TAdminActLevelExt.java, v 0.1 2018年9月6日 下午5:04:59 wang yi Exp $
 */
public class TActAndCustomerExt {
    /**
     * 报名id
     */
    private Integer cuId;

    private Integer id;
    /**
     * 订单号
     */
    private String  orderNo;
    /**
     * 线下成绩关联的指卡id
     */
    private String  cardTokenId;
    /**
     * 客户ID
     */
    private Integer customerId;
    /**
     * 活动ID
     */
    private Integer actId;
    /**
     * 赛事头像
     */
    private String  actImage;
    /**
     * 赛事名称
     */
    private String  actName;
    /**
     * 活动类型
     */
    private Integer actType;
    /**
     * 活动组别
     */
    private Integer actLevelId;
    /**
     * 活动组别名称
     */
    private String  actLevelName;
    /**
     * 赛事地图ID
     */
    private Integer eventMapId;
    /**
     * 0等待开始；1已开始；2用户打终点结束；3 管理后台结束活动；4 用户提前结束活动
     */
    private Integer status;
    /**
     * 用户开始时间
     */
    private Date    startTime;
    /**
     * 用户结束时间
     */
    private Date    endTime;
    /**
     * 花费时间
     */
    private Long    takeTime;
    /**
     * 得分
     */
    private Integer score;
    /**
     * 累计得分（不含扣除）
     */
    private Integer totalScore;
    /**
     * 成绩是否有效；1 有效；0 无效；-1 人工取消成绩
     */
    private Integer isScoreValid;
    /**
     * 人工取消成绩的原因
     */
    private String  invalidDesc;
    /**
     * 有效的检查点个数
     */
    private Integer checkPointNum;
    /**
     * 检查点打卡类型：1：蓝牙打卡 ; 2:gps打卡
     */
    private Integer pointCheckType;
    /**
     * 比赛赛事状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date    createTime;
    /**
     * 更新时间
     */
    private Date    updateTime;

    /**
     * 客户名
     */
    private String  nickName;
    /**
     * 手机号码
     */
    private String  mobile;
    /**
     * 国际码
     */
    private String  nationCode;
    /**
     * 密码
     */
    private String  password;
    /**
     * 头像
     */
    private String  avatar;
    /**
     * 0女；1男
     */
    private Integer sex;
    /**
     * 身高
     */
    private String  height;
    /**
     * 体重
     */
    private String  weight;
    /**
     * 姓名
     */
    private String  name;
    /**
     * 邮箱
     */
    private String  mail;
    /**
     * 身份证
     */
    private String  idNum;
    /**
     * 省市，空格隔开
     */
    private String  city;
    /**
     * 详细地址
     */
    private String  address;
    private String  dressSize;
    private String  bloodType;
    private String  contactName;
    private String  contactNationCode;
    private String  contactMobile;

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCardTokenId() {
        return cardTokenId;
    }

    public void setCardTokenId(String cardTokenId) {
        this.cardTokenId = cardTokenId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getActImage() {
        return actImage;
    }

    public void setActImage(String actImage) {
        this.actImage = actImage;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Integer getActLevelId() {
        return actLevelId;
    }

    public void setActLevelId(Integer actLevelId) {
        this.actLevelId = actLevelId;
    }

    public String getActLevelName() {
        return actLevelName;
    }

    public void setActLevelName(String actLevelName) {
        this.actLevelName = actLevelName;
    }

    public Integer getEventMapId() {
        return eventMapId;
    }

    public void setEventMapId(Integer eventMapId) {
        this.eventMapId = eventMapId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Long takeTime) {
        this.takeTime = takeTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getIsScoreValid() {
        return isScoreValid;
    }

    public void setIsScoreValid(Integer isScoreValid) {
        this.isScoreValid = isScoreValid;
    }

    public String getInvalidDesc() {
        return invalidDesc;
    }

    public void setInvalidDesc(String invalidDesc) {
        this.invalidDesc = invalidDesc;
    }

    public Integer getCheckPointNum() {
        return checkPointNum;
    }

    public void setCheckPointNum(Integer checkPointNum) {
        this.checkPointNum = checkPointNum;
    }

    public Integer getPointCheckType() {
        return pointCheckType;
    }

    public void setPointCheckType(Integer pointCheckType) {
        this.pointCheckType = pointCheckType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDressSize() {
        return dressSize;
    }

    public void setDressSize(String dressSize) {
        this.dressSize = dressSize;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNationCode() {
        return contactNationCode;
    }

    public void setContactNationCode(String contactNationCode) {
        this.contactNationCode = contactNationCode;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

}
