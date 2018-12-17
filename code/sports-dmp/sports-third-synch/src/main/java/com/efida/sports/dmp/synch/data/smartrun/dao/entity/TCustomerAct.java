package com.efida.sports.dmp.synch.data.smartrun.dao.entity;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyi
 * @since 2018-09-04
 */
public class TCustomerAct {

    private static final long serialVersionUID = 1L;

    private Integer           id;
    /**
     * 订单号
     */
    private String            orderNo;
    /**
     * 线下成绩关联的指卡id
     */
    private String            cardTokenId;
    private Integer           customerId;
    /**
     * 活动ID
     */
    private Integer           actId;
    private String            actImage;
    private String            actName;
    /**
     * 活动类型
     */
    private Integer           actType;
    /**
     * 活动组别
     */
    private Integer           actLevelId;
    /**
     * 活动组别名称
     */
    private String            actLevelName;
    /**
     * 赛事地图ID
     */
    private Integer           eventMapId;
    /**
     * 0等待开始；1已开始；2用户打终点结束；3 管理后台结束活动；4 用户提前结束活动
     */
    private Integer           status;
    /**
     * 用户开始时间
     */
    private Date              startTime;
    /**
     * 用户结束时间
     */
    private Date              endTime;
    /**
     * 花费时间
     */
    private Long              takeTime;
    /**
     * 得分
     */
    private Integer           score;
    /**
     * 累计得分（不含扣除）
     */
    private Integer           totalScore;
    /**
     * 成绩是否有效；1 有效；0 无效；-1 人工取消成绩
     */
    private Integer           isScoreValid;
    /**
     * 人工取消成绩的原因
     */
    private String            invalidDesc;
    /**
     * 有效的检查点个数
     */
    private Integer           checkPointNum;
    /**
     * 检查点打卡类型：1：蓝牙打卡 ; 2:gps打卡
     */
    private Integer           pointCheckType;
    private Integer           state;
    private Date              updateTime;
    private Date              createTime;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TCustomerAct{" + "id=" + id + ", orderNo=" + orderNo + ", cardTokenId="
               + cardTokenId + ", customerId=" + customerId + ", actId=" + actId + ", actImage="
               + actImage + ", actName=" + actName + ", actType=" + actType + ", actLevelId="
               + actLevelId + ", actLevelName=" + actLevelName + ", eventMapId=" + eventMapId
               + ", status=" + status + ", startTime=" + startTime + ", endTime=" + endTime
               + ", takeTime=" + takeTime + ", score=" + score + ", totalScore=" + totalScore
               + ", isScoreValid=" + isScoreValid + ", invalidDesc=" + invalidDesc
               + ", checkPointNum=" + checkPointNum + ", pointCheckType=" + pointCheckType
               + ", state=" + state + ", updateTime=" + updateTime + ", createTime=" + createTime
               + "}";
    }
}
