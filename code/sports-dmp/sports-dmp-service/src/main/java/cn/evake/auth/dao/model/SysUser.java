package cn.evake.auth.dao.model;

import java.io.Serializable;
import java.util.Date;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户表
 * @author wang yi
 * @desc 
 * @version $Id: SysUser.java, v 0.1 2018年2月27日 下午3:24:20 wang yi Exp $
 */
@ApiModel(value = "用户")
public class SysUser implements Serializable {
    /**  */
    private static final long serialVersionUID = -7342248123679164425L;

    @ApiModelProperty(value = "ID")
    private Integer           id;

    @ApiModelProperty(value = "uuid")
    private String            uuid;

    @ApiModelProperty(value = "用户名(登录名)")
    private String            userName;

    @ApiModelProperty(value = "密码")
    private String            password;

    @ApiModelProperty(value = "真实名称")
    private String            realName;

    @ApiModelProperty(value = "昵称(暂时废弃)")
    private String            nickName;

    @ApiModelProperty(value = "email")
    private String            email;

    @ApiModelProperty(value = "头像")
    private String            avatar;

    @ApiModelProperty(value = "电话")
    private String            phone;

    @ApiModelProperty(value = "性别")
    private String            gender;

    @ApiModelProperty(value = "状态")
    private String            status;

    @ApiModelProperty(value = "最后登录时间")
    private Date              lastLoginTime;

    @ApiModelProperty(value = "备注")
    private String            remark;

    @ApiModelProperty(value = "身份")
    private String            identity;

    private Date              gmtCreate;

    private Date              gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
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

    public String getGmtCreateStr() {
        if (gmtCreate != null) {
            return DateUtil.formatDate(gmtCreate);
        }
        return null;
    }

    public String getGmtModifiedStr() {
        if (gmtModified != null) {
            return DateUtil.formatDate(gmtModified);
        }
        return null;
    }

    public String getLastLoginTimeStr() {
        if (lastLoginTime != null) {
            return DateUtil.formatDate(lastLoginTime);
        }
        return null;
    }

}