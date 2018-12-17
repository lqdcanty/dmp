package cn.evake.file.model;

import java.io.Serializable;

import cn.evake.file.enmus.FilePlatEnum;
import cn.evake.file.enmus.LastingTypeEnum;

public class FileTransferModel implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 文件名称 
     */
    private String            fileName;
    /**
     * 文件类型
     */
    private String            fileType;
    /**
     * 文件来源
     * @see FilePlatEnum
     */
    private String            appName;
    /**
     * 文件存储类型
     * @see LastingTypeEnum
     */
    private String            lastingType;
    /**
     * 字节文件 
     */
    private byte[]            btyes;
    /**
     * 调用者自定义用户标识
     * uid
     */
    private String            uid;

    public FileTransferModel() {
        super();
    }

    public FileTransferModel(String fileName, String fileType, String appName, String lastingType,
                             byte[] btyes, String uid) {
        super();
        this.fileName = fileName;
        this.fileType = fileType;
        this.appName = appName;
        this.lastingType = lastingType;
        this.btyes = btyes;
        this.uid = uid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLastingType() {
        return lastingType;
    }

    public void setLastingType(String lastingType) {
        this.lastingType = lastingType;
    }

    public byte[] getBtyes() {
        return btyes;
    }

    public void setBtyes(byte[] btyes) {
        this.btyes = btyes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
