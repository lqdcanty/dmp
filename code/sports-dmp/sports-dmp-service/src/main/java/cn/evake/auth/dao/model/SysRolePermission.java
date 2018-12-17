package cn.evake.auth.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限表
 * 
 * @author wang yi
 * @desc 
 * @version $Id: SysRolePermission.java, v 0.1 2018年2月27日 下午3:24:10 wang yi Exp $
 */
@ApiModel(value = "角色权限")
public class SysRolePermission implements Serializable {
    /**  */
    private static final long serialVersionUID = 289990496660838270L;

    @ApiModelProperty(value = "ID")
    private Long              id;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long              roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String            roleName;

    /**
     * 目录ID
     */
    @ApiModelProperty(value = "目录ID")
    private Long              menuId;

    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称")
    private String            menuName;

    /**
     * UUID
     */
    @ApiModelProperty(value = "uuid")
    private String            uuid;

    /**
     * 创建者名称
     */
    @ApiModelProperty(value = "创建者名称")
    private String            createUserName;

    /**
     * 创建时间
     */
    private Date              gmtCreate;

    /**
     * 修改时间
     */
    private Date              gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
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
}