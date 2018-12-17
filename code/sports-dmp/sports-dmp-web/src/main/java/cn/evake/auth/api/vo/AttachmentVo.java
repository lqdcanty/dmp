/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 附件Vo
 * @author wang yi
 * @desc 
 * @version $Id: AttachmentVo.java, v 0.1 2018年7月26日 下午4:02:42 wang yi Exp $
 */
@ApiModel(value = "文档附件模型")
public class AttachmentVo {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "下载地址")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
