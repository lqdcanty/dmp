package com.efida.sports.dmp.synch.data.jiubiao.dao.entity;

import java.util.Date;

/**
 * <p>
 * 城市表
 * </p>
 *
 * @author wangyi
 * @since 2018-09-14
 */
public class ShotCity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String            id;
    /**
     * 姓名
     */
    private String            name;
    /**
     * 赛区人数
     */
    private String            peopleNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
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
        return "ShotCity{" + "id=" + id + ", name=" + name + ", peopleNumber=" + peopleNumber
               + ", status=" + status + ", createBy=" + createBy + ", createDate=" + createDate
               + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks
               + "}";
    }
}
