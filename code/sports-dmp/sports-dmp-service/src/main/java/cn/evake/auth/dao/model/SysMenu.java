package cn.evake.auth.dao.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统菜单表
 * @author wang yi
 * @desc 
 * @version $Id: SysMenu.java, v 0.1 2018年2月27日 下午3:23:16 wang yi Exp $
 */
@ApiModel(value = "系统菜单")
public class SysMenu implements Serializable {
    /**  */
    private static final long serialVersionUID = -5764959164183928080L;

    @ApiModelProperty(value = "ID")
    private Long              id;

    @ApiModelProperty(value = "系统ID")
    private Long              sysId;

    @ApiModelProperty(value = "目录名称")
    private String            menuName;

    @ApiModelProperty(value = "目录URL")
    private String            menuUrl;

    @ApiModelProperty(value = "类型")
    private String            type;

    @ApiModelProperty(value = "授权码")
    private String            permissionCode;

    @ApiModelProperty(value = "是否属于本系统")
    private Boolean           isSelfSys;

    @ApiModelProperty(value = "新窗口打开")
    private Boolean           newWindow;

    @ApiModelProperty(value = "是否展开")
    private Boolean           isOpen;

    @ApiModelProperty(value = "状态:是否可使用")
    private Boolean           status;

    @ApiModelProperty(value = "父级ID")
    private Long              parentId;

    @ApiModelProperty(value = "是否支持手机")
    private Boolean           supportMobile;

    //数据创建时间
    private Date              gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    public Boolean getIsSelfSys() {
        return isSelfSys;
    }

    public void setIsSelfSys(Boolean isSelfSys) {
        this.isSelfSys = isSelfSys;
    }

    public Boolean getNewWindow() {
        return newWindow;
    }

    public void setNewWindow(Boolean newWindow) {
        this.newWindow = newWindow;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return menuName;
    }

    public Boolean getSupportMobile() {
        return supportMobile;
    }

    public void setSupportMobile(Boolean supportMobile) {
        this.supportMobile = supportMobile;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}