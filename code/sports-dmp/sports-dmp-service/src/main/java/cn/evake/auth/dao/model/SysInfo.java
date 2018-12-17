package cn.evake.auth.dao.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import cn.evake.auth.util.DateUtil;

/**
 * 
 * 系统表
 * @author wang yi
 * @desc 
 * @version $Id: SysInfo.java, v 0.1 2018年2月27日 下午3:23:30 wang yi Exp $
 */
@ApiModel(value = "系统信息")
public class SysInfo implements Serializable {
    /**  */
    private static final long serialVersionUID = -1789609436505444661L;

    @ApiModelProperty(value = "系统ID")
    private Long              id;

    @ApiModelProperty(value = "系统名称")
    private String            name;

    @ApiModelProperty(value = "创建者名称")
    private String            createUserName;

    @ApiModelProperty(value = "ICON")
    private String            icon;

    @ApiModelProperty(value = "是否显示")
    private Boolean           dispaly;

    @ApiModelProperty(value = "域名")
    private String            host;

    private Date              gmtCreate;

    private Date              gmtModifed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Boolean getDispaly() {
        return dispaly;
    }

    public void setDispaly(Boolean dispaly) {
        this.dispaly = dispaly;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModifed() {
        return gmtModifed;
    }

    public void setGmtModifed(Date gmtModifed) {
        this.gmtModifed = gmtModifed;
    }

    public String getGmtCreateStr() {
        return getGmtCreate() == null ? null : DateUtil.formatDate(gmtCreate);
    }

    public String getGmtModifedStr() {
        return getGmtModifed() == null ? null : DateUtil.formatDate(gmtModifed);
    }
}