package com.efida.sports.dmp.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *模板解析细信息
 * @author wang yi
 * @desc 
 * @version $Id: MatchDetailVo.java, v 0.1 2018年7月18日 下午3:19:56 wang yi Exp $
 */
@ApiModel(value = "模板解析细信息")
public class TemplateResultVo {

    @ApiModelProperty(value = "解析状态0 数据校验成功  1 数据校验失败")
    private int    status = 0;

    @ApiModelProperty(value = "文件UID")
    private String fileUid;

    @ApiModelProperty(value = "错误记录数")
    private int    errRecord;

    @ApiModelProperty(value = "校验错误记产生_文件")
    private String attachment;

    public TemplateResultVo() {
        super();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getFileUid() {
        return fileUid;
    }

    public void setFileUid(String fileUid) {
        this.fileUid = fileUid;
    }

    public int getErrRecord() {
        return errRecord;
    }

    public void setErrRecord(int errRecord) {
        this.errRecord = errRecord;
    }

}