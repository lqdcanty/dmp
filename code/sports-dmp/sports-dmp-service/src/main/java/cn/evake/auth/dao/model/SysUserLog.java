package cn.evake.auth.dao.model;

import java.io.Serializable;
import java.util.Date;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统用户日志
 * @author wang yi
 * @desc 
 * @version $Id: SysUserLog.java, v 0.1 2018年2月27日 下午3:24:34 wang yi Exp $
 */
@ApiModel(value = "系统日志数据")
public class SysUserLog implements Serializable {
    /**  */
    private static final long serialVersionUID = 4633712518916693465L;

    private Long              id;

    @ApiModelProperty(value = "用户UID")
    private String            uuid;

    @ApiModelProperty(value = "用户名")
    private String            userName;

    @ApiModelProperty(value = "行为类型")
    private String            actionType;

    @ApiModelProperty(value = "日志记录")
    private String            action;

    @ApiModelProperty(value = "请求Ip")
    private String            ip;

    @ApiModelProperty(value = "浏览器信息")
    private String            browser;

    @ApiModelProperty(value = "访问URL")
    private String            viewUrl;

    @ApiModelProperty(value = "日志创建时间")
    private Date              gmtCreate;

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
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl == null ? null : viewUrl.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtCreateStr() {
        if (gmtCreate != null) {
            return DateUtil.formatDate(gmtCreate);
        }
        return null;
    }
}