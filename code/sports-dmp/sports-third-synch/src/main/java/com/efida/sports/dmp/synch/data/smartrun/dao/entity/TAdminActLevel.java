package com.efida.sports.dmp.synch.data.smartrun.dao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyi
 * @since 2018-09-04
 */
public class TAdminActLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer           id;
    /**
     * 活动id
     */
    private Integer           actId;
    /**
     * 竞技级别名称
     */
    private String            name;
    /**
     * 报名费用，0表示免费,单位分
     */
    private Integer           fee;
    /**
     * 报名限制人数
     */
    private Integer           number;
    /**
     * 赛事地图点坐标id列表
     */
    private String            eventMapPointIdList;
    /**
     * 检查点上打卡url地址
     */
    private String            eventMapPointLinkList;
    /**
     * 1 有效；0 无效
     */
    private Integer           state;
    /**
     * 1 展现，2不展现
     */
    private Integer           showPosition;
    /**
     * 报名码，如果有，报名时需要验证
     */
    private String            applyCode;
    /**
     * 出发方式，0各自打卡出发，1同时出发
     */
    private Integer           startType;
    /**
     * 同时出发时的状态，0未出发，1已出发
     */
    private Integer           startState;
    /**
     * 同时出发的时间
     */
    private Date              startTime;
    /**
     * 是否显示卫星图层，0不显示，1显示
     */
    private Integer           showSatelliteTile;
    /**
     * 积分关门时间，单位秒 超过关门时间成绩无效
     */
    private Integer           jfTotalTime;
    /**
     * 积分扣分时间，单位秒 到达扣分时间开始扣分
     */
    private Integer           jfCloseTime;
    /**
     * 积分赛每分钟罚分
     */
    private Integer           jfPunishScore;
    private Date              createTime;
    private Date              updateTime;
    /**
     * 检查点打卡类型：1：蓝牙打卡 ; 2:gps打卡
     */
    private Integer           pointCheckType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEventMapPointIdList() {
        return eventMapPointIdList;
    }

    public void setEventMapPointIdList(String eventMapPointIdList) {
        this.eventMapPointIdList = eventMapPointIdList;
    }

    public String getEventMapPointLinkList() {
        return eventMapPointLinkList;
    }

    public void setEventMapPointLinkList(String eventMapPointLinkList) {
        this.eventMapPointLinkList = eventMapPointLinkList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(Integer showPosition) {
        this.showPosition = showPosition;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    public Integer getStartState() {
        return startState;
    }

    public void setStartState(Integer startState) {
        this.startState = startState;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getShowSatelliteTile() {
        return showSatelliteTile;
    }

    public void setShowSatelliteTile(Integer showSatelliteTile) {
        this.showSatelliteTile = showSatelliteTile;
    }

    public Integer getJfTotalTime() {
        return jfTotalTime;
    }

    public void setJfTotalTime(Integer jfTotalTime) {
        this.jfTotalTime = jfTotalTime;
    }

    public Integer getJfCloseTime() {
        return jfCloseTime;
    }

    public void setJfCloseTime(Integer jfCloseTime) {
        this.jfCloseTime = jfCloseTime;
    }

    public Integer getJfPunishScore() {
        return jfPunishScore;
    }

    public void setJfPunishScore(Integer jfPunishScore) {
        this.jfPunishScore = jfPunishScore;
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

    public Integer getPointCheckType() {
        return pointCheckType;
    }

    public void setPointCheckType(Integer pointCheckType) {
        this.pointCheckType = pointCheckType;
    }

    @Override
    public String toString() {
        return "TAdminActLevel{" + "id=" + id + ", actId=" + actId + ", name=" + name + ", fee="
               + fee + ", number=" + number + ", eventMapPointIdList=" + eventMapPointIdList
               + ", eventMapPointLinkList=" + eventMapPointLinkList + ", state=" + state
               + ", showPosition=" + showPosition + ", applyCode=" + applyCode + ", startType="
               + startType + ", startState=" + startState + ", startTime=" + startTime
               + ", showSatelliteTile=" + showSatelliteTile + ", jfTotalTime=" + jfTotalTime
               + ", jfCloseTime=" + jfCloseTime + ", jfPunishScore=" + jfPunishScore
               + ", createTime=" + createTime + ", updateTime=" + updateTime + ", pointCheckType="
               + pointCheckType + "}";
    }
}
