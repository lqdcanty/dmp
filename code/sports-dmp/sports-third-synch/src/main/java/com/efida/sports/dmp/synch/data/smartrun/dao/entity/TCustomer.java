package com.efida.sports.dmp.synch.data.smartrun.dao.entity;

import java.util.Date;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author wangyi
 * @since 2018-09-04
 */
public class TCustomer {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long              id;
    /**
     * 客户名
     */
    private String            nickName;
    /**
     * 手机号码
     */
    private String            mobile;
    /**
     * 国际码
     */
    private String            nationCode;
    /**
     * 密码
     */
    private String            password;
    /**
     * 性别，0女；1男
     */
    private String            avatar;
    /**
     * 受邀ID
     */
    private Integer           sex;
    /**
     * 身高
     */
    private String            height;
    /**
     * 体重
     */
    private String            weight;
    /**
     * 状态， 0：删除 1：正常
     */
    private Integer           state;
    /**
     * 修改时间
     */
    private Date              updateTime;
    /**
     * 创建时间
     */
    private Date              createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "TCustomer{" + "id=" + id + ", nickName=" + nickName + ", mobile=" + mobile
               + ", nationCode=" + nationCode + ", password=" + password + ", avatar=" + avatar
               + ", sex=" + sex + ", height=" + height + ", weight=" + weight + ", state=" + state
               + ", updateTime=" + updateTime + ", createTime=" + createTime + "}";
    }
}
