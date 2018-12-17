/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.dubbo.open.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 开放用户信息类
 * @author wang yi
 * @desc 
 * @version $Id: OpenUserModel.java, v 0.1 2018年8月2日 下午2:58:57 wang yi Exp $
 */
public class OpenUserModel implements Serializable {

    /**  */
    private static final long serialVersionUID = -2319410990039170492L;

    /**
     * 用户Uid(系统内部唯一编号)
     */
    private String            uuid;

    /**
     * 登录用户名(用户外部唯一标识)
     */
    private String            userName;

    /**
     * 真实名称
     */
    private String            realName;

    /**
     * 用户邮箱
     */
    private String            email;

    /**
     * 头像
     */
    private String            avatar;

    /**
     * 电话
     */
    private String            phone;

    /**
     * 身份
     */
    private String            identity;

    /**
     * 最后登录时间
     */
    private Date              lastLoginTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

}
