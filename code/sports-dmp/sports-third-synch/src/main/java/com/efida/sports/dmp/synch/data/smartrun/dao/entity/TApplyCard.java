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
public class TApplyCard {

    private static final long serialVersionUID = 1L;

    private Integer           id;
    private Integer           customerId;
    /**
     * 姓名
     */
    private String            name;
    /**
     * 邮箱
     */
    private String            mail;
    /**
     * 身份证
     */
    private String            idNum;
    /**
     * 性别，0女；1男
     */
    private Integer           sex;
    /**
     * 国际码
     */
    private String            nationCode;
    /**
     * 电话
     */
    private String            mobile;
    /**
     * 省市，空格隔开
     */
    private String            city;
    /**
     * 详细地址
     */
    private String            address;
    private String            dressSize;
    private String            bloodType;
    private String            contactName;
    private String            contactNationCode;
    private String            contactMobile;
    private Integer           state;
    private Date              updateTime;
    private Date              createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        return "TApplyCard{" + "id=" + id + ", customerId=" + customerId + ", name=" + name
               + ", mail=" + mail + ", idNum=" + idNum + ", sex=" + sex + ", nationCode="
               + nationCode + ", mobile=" + mobile + ", city=" + city + ", address=" + address
               + ", dressSize=" + dressSize + ", bloodType=" + bloodType + ", contactName="
               + contactName + ", contactNationCode=" + contactNationCode + ", contactMobile="
               + contactMobile + ", state=" + state + ", updateTime=" + updateTime + ", createTime="
               + createTime + "}";
    }
}
