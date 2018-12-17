package cn.evake.auth.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import cn.evake.auth.util.DateUtil;

/**
 * 角色表
 * @author wang yi
 * @desc 
 * @version $Id: SysRole.java, v 0.1 2018年2月27日 下午3:23:49 wang yi Exp $
 */
@ApiModel(value = "角色")
public class SysRole implements Serializable {
    /**  */
    private static final long serialVersionUID = 6835939940350746301L;

    @ApiModelProperty(value = "ID")
    private Long              id;

    @ApiModelProperty(value = "角色名称")
    private String            roleName;

    @ApiModelProperty(value = "用户唯一标识")
    private String            uuid;

    @ApiModelProperty(value = "创建者名称")
    private String            createUserName;

    @ApiModelProperty(value = "状态")
    private String            status;

    private Date              gmtCreate;

    private Date              gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        return gmtCreate == null ? null : DateUtil.formatDate(gmtCreate);
    }

    public String getGmtModifiedStr() {
        return gmtModified == null ? null : DateUtil.formatDate(gmtModified);
    }

}