package cn.evake.app.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyi
 * @since 2018-09-21
 */
@ApiModel(value = "应用信息")
public class AppInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = -6211455523583096723L;

    @ApiModelProperty(value = "ID(添加/修改时不填写)", required = false)
    private Integer           id;
    /**
     * 系统编号
     */
    @NotEmpty(message = "系统编号不能为空")
    @Size(min = 0, max = 20, message = "系统编号需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统编号(数据唯一标识)", required = true)
    private String            appCode;
    /**
     * 系统名称
     */
    @NotEmpty(message = "系统名称不能为空")
    @Size(min = 0, max = 64, message = "系统名称需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统编号", required = true)
    private String            appName;
    /**
     * 系统版本
     */
    @NotEmpty(message = "系统版本不能为空")
    @Size(min = 0, max = 64, message = "系统版本需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统版本", required = true)
    private String            appVersion;
    /**
     * 系统简介
     */
    @NotEmpty(message = "系统简介不能为空")
    @Size(min = 0, max = 255, message = "系统简介需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统简介", required = true)
    private String            appIntro;
    /**
     * 系统负责人账号
     */
    @NotEmpty(message = "系统负责人账号不能为空")
    @Size(min = 0, max = 20, message = "系统负责人账号需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统负责人账号", required = true)
    private String            managerName;
    /**
     * 系统负责人名称
     */
    @NotEmpty(message = "系统负责人名称不能为空")
    @Size(min = 0, max = 24, message = "系统负责人名称需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "系统负责人名称", required = true)
    private String            managerRealname;
    /**
     * 系统负责人电话
     */
    @ApiModelProperty(value = "系统负责人电话", required = false)
    private String            managerPhone;
    /**
     * 系统负责人邮箱
     */
    @ApiModelProperty(value = "系统负责人邮箱", required = false)
    private String            managerEmail;
    /**
     * 值班人员账号
     */
    @NotEmpty(message = "值班人员不能为空")
    @Size(min = 0, max = 20, message = "值班人员需在{min}和{max}个字符之间")
    @ApiModelProperty(value = "值班人员账号", required = true)
    private String            dutyUserName;
    /**
     * svn: git:
     */
    @ApiModelProperty(value = "代码类型(git svn)", required = false)
    private String            codeType;
    /**
     * 代码地址
     */
    @ApiModelProperty(value = "代码地址", required = false)
    private String            codeUrl;
    /**
     * 创建者账号
     */
    @ApiModelProperty(value = "创建者(添加/修改时不填写)", required = false)
    private String            createUser;
    /**
     * 最后一次修改人
     */
    @ApiModelProperty(value = "修改者(添加/修改时不填写)", required = false)
    private String            lastModifyUser;
    /**
     * 最近一次修改时间
     */
    @ApiModelProperty(value = "修改时间(添加/修改时不填写)", required = false)
    private Date              lastModifyTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(添加/修改时不填写)", required = false)
    private Date              gmtCreate;
    /**
     * 修改时间
     */
    private Date              gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerRealname() {
        return managerRealname;
    }

    public void setManagerRealname(String managerRealname) {
        this.managerRealname = managerRealname;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getDutyUserName() {
        return dutyUserName;
    }

    public void setDutyUserName(String dutyUserName) {
        this.dutyUserName = dutyUserName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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

    public String getLastModifyTimeStr() {
        if (lastModifyTime != null) {
            return DateUtil.formatDate(lastModifyTime);
        }
        return null;
    }

}
