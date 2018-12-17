package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

public class UserUnitModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;
    /**
     * 用户uid
     */
    private String            uid;
    /**
     * 用户登陆名
     */
    private String            userName;
    /**
     * 用户真实名称
     */
    private String            userRealName;
    /**
     * 承办方账号
     */
    private String            unitCode;
    /**
     * 承办方名称
     */
    private String            unitName;

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

}