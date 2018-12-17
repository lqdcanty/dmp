package com.efida.sports.dmp.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
public class ApplyAreaStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 分赛场编号
     */
    private String            fieldCode;
    /**
     * 分赛场名称名称
     */
    private String            fieldName;
    /**
     * 赛场地址
     */
    private String            fieldAddress;
    /** 
     * 报名次数
     */
    private Long              applyCount;

    /**
     * 有效状态(0 无效  1有效)
     */
    private String            status;
    /**
     * 经度
     */
    private BigDecimal        longitude;
    /**
     * 纬度
     */
    private BigDecimal        latitude;
    /**
     * 省
     */
    private String            province;
    /**
     * 市
     */
    private String            city;
    /**
     * 区/县
     */
    private String            area;
    /**
     * 系统创建时间
     */
    private Date              gmtCreate;
    /**
     * 系统修改时间
     */
    private Date              gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldAddress() {
        return fieldAddress;
    }

    public void setFieldAddress(String fieldAddress) {
        this.fieldAddress = fieldAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
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

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Long applyCount) {
        this.applyCount = applyCount;
    }

    @Override
    public String toString() {
        return "ApplyAreaStatistics{" + ", id=" + id + ", fieldCode=" + fieldCode + ", fieldName="
               + fieldName + ", fieldAddress=" + fieldAddress + ", status=" + status
               + ", longitude=" + longitude + ", latitude=" + latitude + ", province=" + province
               + ", city=" + city + ", area=" + area + ", gmtCreate=" + gmtCreate + ", gmtModified="
               + gmtModified + "}";
    }
}
