package cn.evake.auth.api.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文档类型
 * @author wang yi
 * @desc 
 * @version $Id: SysDocument.java, v 0.1 2018年7月26日 下午3:08:02 wang yi Exp $
 */
@ApiModel(value = "文档模型")
public class DocumentVo {

    @ApiModelProperty(value = "类型")
    private Integer            type;

    @ApiModelProperty(value = "名称")
    private String             name;

    @ApiModelProperty(value = "版本")
    private String             version;

    @ApiModelProperty(value = "发布时间")
    private String             publishTimeStr;

    @ApiModelProperty(value = "简介(此处数据为富文本)")
    private String             intro;

    @ApiModelProperty(value = "附件模型")
    private List<AttachmentVo> attachment;

    @ApiModelProperty(value = "最后更新用户标识(前端不展示)")
    private Integer            lastUid;

    @ApiModelProperty(value = "最后更新用户(添加时不传)")
    private String             lastUname;

    @ApiModelProperty(value = "创建时间(添加时不传)")
    private String             gmtCreateStr;

    @ApiModelProperty(value = "最后修改时间(添加时不传)")
    private String             gmtModifiedStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLastUid() {
        return lastUid;
    }

    public void setLastUid(Integer lastUid) {
        this.lastUid = lastUid;
    }

    public String getLastUname() {
        return lastUname;
    }

    public void setLastUname(String lastUname) {
        this.lastUname = lastUname == null ? null : lastUname.trim();
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public List<AttachmentVo> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentVo> attachment) {
        this.attachment = attachment;
    }

    public String getGmtCreateStr() {
        return gmtCreateStr;
    }

    public void setGmtCreateStr(String gmtCreateStr) {
        this.gmtCreateStr = gmtCreateStr;
    }

    public String getGmtModifiedStr() {
        return gmtModifiedStr;
    }

    public void setGmtModifiedStr(String gmtModifiedStr) {
        this.gmtModifiedStr = gmtModifiedStr;
    }

    public String getIntro() {
        return intro;
    }

    public String getPublishTimeStr() {
        return publishTimeStr;
    }

    public void setPublishTimeStr(String publishTimeStr) {
        this.publishTimeStr = publishTimeStr;
    }

}