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
public class TAdminAct {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer           id;
    /**
     * 活动的uuid
     */
    private String            actUuid;
    /**
     * 创建者id
     */
    private Integer           userId;
    /**
     * 赛事地图id
     */
    private Integer           eventMapId;
    /**
     * 赛事详情文章id
     */
    private Integer           articleId;
    /**
     * 赛事详情文章uuid
     */
    private String            articleUuid;
    /**
     * 名称
     */
    private String            name;
    /**
     * 宣传图片7牛key
     */
    private String            image;
    /**
     * 活动开始时间
     */
    private Date              startTime;
    /**
     * 活动发布时间
     */
    private Date              publishTime;
    /**
     * 可以查看地图开始时间
     */
    private Date              mapViewTime;
    /**
     * 报名截止时间
     */
    private Date              applyTime;
    /**
     * 是否需要报名卡，0不需要，1需要
     */
    private Integer           needApplyCard;
    /**
     * 活动发布状态：0：创建未发布，1：发布外网，2：下线 , 3已归档，4 申请上线， 5申请被拒绝，6被删除
     */
    private Integer           state;
    /**
     * 拒绝上线理由，对合作方申请上线的活动有效
     */
    private String            refuseMessage;
    /**
     * 审批人名称
     */
    private String            auditName;
    /**
     * 活动类型：1：竞速赛，2：积分赛
     */
    private Integer           type;
    private Date              createTime;
    private Date              updateTime;
    /**
     * 活动地点精度
     */
    private String            lng;
    /**
     * 活动地点维度
     */
    private String            lat;
    /**
     * 地理位置详情
     */
    private String            locationDesc;
    /**
     * 活动进行状态：1：未开放 4，活动进行中，5 已结束
     */
    private Integer           status;
    /**
     * 可报名次数，默认1，不限就设置为10000
     */
    private Integer           applyCount;
    private String            country;
    private String            province;
    private String            city;
    private String            area;
    /**
     * 是否置顶活动；1是，0否
     */
    private Integer           top;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActUuid() {
        return actUuid;
    }

    public void setActUuid(String actUuid) {
        this.actUuid = actUuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventMapId() {
        return eventMapId;
    }

    public void setEventMapId(Integer eventMapId) {
        this.eventMapId = eventMapId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleUuid() {
        return articleUuid;
    }

    public void setArticleUuid(String articleUuid) {
        this.articleUuid = articleUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getMapViewTime() {
        return mapViewTime;
    }

    public void setMapViewTime(Date mapViewTime) {
        this.mapViewTime = mapViewTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getNeedApplyCard() {
        return needApplyCard;
    }

    public void setNeedApplyCard(Integer needApplyCard) {
        this.needApplyCard = needApplyCard;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRefuseMessage() {
        return refuseMessage;
    }

    public void setRefuseMessage(String refuseMessage) {
        this.refuseMessage = refuseMessage;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "TAdminAct{" + "id=" + id + ", actUuid=" + actUuid + ", userId=" + userId
               + ", eventMapId=" + eventMapId + ", articleId=" + articleId + ", articleUuid="
               + articleUuid + ", name=" + name + ", image=" + image + ", startTime=" + startTime
               + ", publishTime=" + publishTime + ", mapViewTime=" + mapViewTime + ", applyTime="
               + applyTime + ", needApplyCard=" + needApplyCard + ", state=" + state
               + ", refuseMessage=" + refuseMessage + ", auditName=" + auditName + ", type=" + type
               + ", createTime=" + createTime + ", updateTime=" + updateTime + ", lng=" + lng
               + ", lat=" + lat + ", locationDesc=" + locationDesc + ", status=" + status
               + ", applyCount=" + applyCount + ", country=" + country + ", province=" + province
               + ", city=" + city + ", area=" + area + ", top=" + top + "}";
    }
}
