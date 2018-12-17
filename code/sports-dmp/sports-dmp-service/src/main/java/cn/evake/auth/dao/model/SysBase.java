package cn.evake.auth.dao.model;

import java.util.Date;

public class SysBase {
    private Integer id;

    private String sysName;

    private String sysSortName;

    private String version;

    private String sysLog;

    private String sysManagerName;

    private String sysManagerPhone;

    private String sysManagerEmail;

    private String onlineExpress;

    private Boolean singleLogin;

    private String pop3Title;

    private String smtpServer;

    private String pop3;

    private String pop3m;

    private String lastUpUid;

    private String lastUname;

    private Date gmtCreate;

    private Date gmtModified;

    private String intro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysSortName() {
        return sysSortName;
    }

    public void setSysSortName(String sysSortName) {
        this.sysSortName = sysSortName == null ? null : sysSortName.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getSysLog() {
        return sysLog;
    }

    public void setSysLog(String sysLog) {
        this.sysLog = sysLog == null ? null : sysLog.trim();
    }

    public String getSysManagerName() {
        return sysManagerName;
    }

    public void setSysManagerName(String sysManagerName) {
        this.sysManagerName = sysManagerName == null ? null : sysManagerName.trim();
    }

    public String getSysManagerPhone() {
        return sysManagerPhone;
    }

    public void setSysManagerPhone(String sysManagerPhone) {
        this.sysManagerPhone = sysManagerPhone == null ? null : sysManagerPhone.trim();
    }

    public String getSysManagerEmail() {
        return sysManagerEmail;
    }

    public void setSysManagerEmail(String sysManagerEmail) {
        this.sysManagerEmail = sysManagerEmail == null ? null : sysManagerEmail.trim();
    }

    public String getOnlineExpress() {
        return onlineExpress;
    }

    public void setOnlineExpress(String onlineExpress) {
        this.onlineExpress = onlineExpress == null ? null : onlineExpress.trim();
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
        this.pop3Title = pop3Title == null ? null : pop3Title.trim();
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer == null ? null : smtpServer.trim();
    }

    public String getPop3() {
        return pop3;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3 == null ? null : pop3.trim();
    }

    public String getPop3m() {
        return pop3m;
    }

    public void setPop3m(String pop3m) {
        this.pop3m = pop3m == null ? null : pop3m.trim();
    }

    public String getLastUpUid() {
        return lastUpUid;
    }

    public void setLastUpUid(String lastUpUid) {
        this.lastUpUid = lastUpUid == null ? null : lastUpUid.trim();
    }

    public String getLastUname() {
        return lastUname;
    }

    public void setLastUname(String lastUname) {
        this.lastUname = lastUname == null ? null : lastUname.trim();
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }
}