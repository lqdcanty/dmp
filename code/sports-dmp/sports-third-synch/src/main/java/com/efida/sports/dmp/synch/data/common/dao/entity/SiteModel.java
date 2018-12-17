package com.efida.sports.dmp.synch.data.common.dao.entity;

/**
 * 赛场信息
 * @author wang yi
 * @desc 
 * @version $Id: SiteModel.java, v 0.1 2018年9月14日 下午5:09:33 wang yi Exp $
 */
public class SiteModel {

    /**
     * 对方ID
     */
    private String reFileId;

    /**
     * 分赛场编号(官方)
     */
    private String fileCode;

    /**
     * 分赛场名称
     */
    private String fileName;

    public String getReFileId() {
        return reFileId;
    }

    public void setReFileId(String reFileId) {
        this.reFileId = reFileId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
