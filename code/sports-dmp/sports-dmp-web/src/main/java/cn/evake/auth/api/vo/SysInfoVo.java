package cn.evake.auth.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  系统信息
 * @author wang yi
 * @desc 
 * @version $Id: SysInfoVo.java, v 0.1 2018年8月23日 下午3:14:07 wang yi Exp $
 */
@ApiModel(value = "系统信息模型")
public class SysInfoVo {
    //基本信息配置
    @ApiModelProperty(value = "系统名")
    private String  sysName;

    @ApiModelProperty(value = "系统简称(登录页名称)")
    private String  sysSortName;

    @ApiModelProperty(value = "系统版本")
    private String  version;

    @ApiModelProperty(value = "系统logo")
    private String  sysLog;

    @ApiModelProperty(value = "简介(此处数据为富文本)")
    private String  intro;

    //系统安全配置
    @ApiModelProperty(value = "系统管理员")
    private String  sysManagerName;

    @ApiModelProperty(value = "管理员电话")
    private String  sysManagerPhone;

    @ApiModelProperty(value = "管理员邮箱")
    private String  sysManagerEmail;

    @ApiModelProperty(value = "用户在线时间")
    private Integer onlineExpress;

    @ApiModelProperty(value = "支持单点登录(启用,禁用)")
    private Boolean singleLogin;

    //系统邮箱配置
    @ApiModelProperty(value = "系统邮箱标题")
    private String  pop3Title;

    @ApiModelProperty(value = "发送系统邮件的SMTP服务器")
    private String  smtpServer;

    @ApiModelProperty(value = "系统邮箱POP3帐号")
    private String  pop3;

    @ApiModelProperty(value = "系统邮箱POP3密码")
    private String  pop3M;

    @ApiModelProperty(value = "最后更新用户(添加时不传)")
    private String  lastUname;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysSortName() {
        return sysSortName;
    }

    public void setSysSortName(String sysSortName) {
        this.sysSortName = sysSortName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSysLog() {
        return sysLog;
    }

    public void setSysLog(String sysLog) {
        this.sysLog = sysLog;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSysManagerName() {
        return sysManagerName;
    }

    public void setSysManagerName(String sysManagerName) {
        this.sysManagerName = sysManagerName;
    }

    public String getSysManagerPhone() {
        return sysManagerPhone;
    }

    public void setSysManagerPhone(String sysManagerPhone) {
        this.sysManagerPhone = sysManagerPhone;
    }

    public String getSysManagerEmail() {
        return sysManagerEmail;
    }

    public void setSysManagerEmail(String sysManagerEmail) {
        this.sysManagerEmail = sysManagerEmail;
    }

    public Integer getOnlineExpress() {
        return onlineExpress;
    }

    public void setOnlineExpress(Integer onlineExpress) {
        this.onlineExpress = onlineExpress;
    }

    public Boolean getSingleLogin() {
        return singleLogin;
    }

    public void setSingleLogin(Boolean singleLogin) {
        this.singleLogin = singleLogin;
    }

    public String getPop3Title() {
        return pop3Title;
    }

    public void setPop3Title(String pop3Title) {
        this.pop3Title = pop3Title;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getPop3() {
        return pop3;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3;
    }

    public String getPop3M() {
        return pop3M;
    }

    public void setPop3M(String pop3m) {
        pop3M = pop3m;
    }

    public String getLastUname() {
        return lastUname;
    }

    public void setLastUname(String lastUname) {
        this.lastUname = lastUname;
    }

}