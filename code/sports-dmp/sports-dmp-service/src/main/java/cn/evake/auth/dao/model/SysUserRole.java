package cn.evake.auth.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import cn.evake.auth.util.DateUtil;

/**
 * 用户角色中间表
 * @author wang yi
 * @desc 
 * @version $Id: SysUserRole.java, v 0.1 2018年2月27日 下午3:24:53 wang yi Exp $
 */
@ApiModel(value = "用户角色")
public class SysUserRole implements Serializable {
    /**  */
    private static final long serialVersionUID = 2298119468604616096L;

    @ApiModelProperty(value = "ID")
    private Long              id;

    @ApiModelProperty(value = "uuid")
    private String            uuid;

    @ApiModelProperty(value = "角色ID")
    private Long              roleId;

    @ApiModelProperty(value = "角色名称")
    private String            roleName;

    private Date              gmtCreate;

    private Date              gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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