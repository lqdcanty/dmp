package com.efida.sports.dmp.synch.data.jiubiao.dao.entity;

import java.util.Date;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author wangyi
 * @since 2018-09-14
 */
public class ShotMember {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String            id;
    /**
     * 微信openID
     */
    private String            wxopenId;
    /**
     * 用户微信唯一ID
     */
    private String            unionid;
    /**
     * 性别0.女 1.男 2.未设置
     */
    private String            gender;
    /**
     * 真实姓名
     */
    private String            truename;
    /**
     * 微信昵称
     */
    private String            nickname;
    /**
     * 手机号码
     */
    private String            phone;
    /**
     * 微信昵称
     */
    private String            avatarUrl;
    /**
     * 赛区城市
     */
    private String            cityName;
    /**
     * 是否进入复赛0.未进入 1.进入复赛
     */
    private String            rematch;
    /**
     * 年龄
     */
    private String            age;
    /**
     * 状态（0正常 1删除 2停用）
     */
    private String            status;
    /**
     * 创建者
     */
    private String            createBy;
    /**
     * 创建时间
     */
    private Date              createDate;
    /**
     * 更新者
     */
    private String            updateBy;
    /**
     * 更新时间
     */
    private Date              updateDate;
    /**
     * 备注信息
     */
    private String            remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxopenId() {
        return wxopenId;
    }

    public void setWxopenId(String wxopenId) {
        this.wxopenId = wxopenId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRematch() {
        return rematch;
    }

    public void setRematch(String rematch) {
        this.rematch = rematch;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ShotMember{" + "id=" + id + ", wxopenId=" + wxopenId + ", unionid=" + unionid
               + ", gender=" + gender + ", truename=" + truename + ", nickname=" + nickname
               + ", phone=" + phone + ", avatarUrl=" + avatarUrl + ", cityName=" + cityName
               + ", rematch=" + rematch + ", age=" + age + ", status=" + status + ", createBy="
               + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy
               + ", updateDate=" + updateDate + ", remarks=" + remarks + "}";
    }
}
