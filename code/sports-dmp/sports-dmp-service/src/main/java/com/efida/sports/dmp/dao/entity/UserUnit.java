package com.efida.sports.dmp.dao.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 
 *
 * @author wang yi
 * @since 2018-08-27
 */
@ApiModel(value = "用户关联承办方信息")
public class UserUnit extends Model<UserUnit> {

	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 用户uid
	 */
	@ApiModelProperty(value = "用户uid(前端必传)")
	private String uid;
	/**
	 * 用户登陆名
	 */
	@ApiModelProperty(value = "用户登陆名")
	private String userName;
	/**
	 * 用户真实名称
	 */
	@ApiModelProperty(value = "用户真实名称")
	private String userRealName;
	/**
	 * 承办方账号
	 */
	@ApiModelProperty(value = "承办方账号(前端必传)")
	private String unitCode;
	/**
	 * 承办方名称
	 */
	@ApiModelProperty(value = "承办方名称(前端必传)")
	private String unitName;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "UserUnit{" + "id=" + id + ", uid=" + uid + ", userName=" + userName + ", userRealName=" + userRealName
				+ ", unitCode=" + unitCode + ", unitName=" + unitName + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + "}";
	}
}
