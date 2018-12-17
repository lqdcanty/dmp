/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.file.enmus;

/**
 * 文件服务器枚举
 * @author Evance
 * @version $Id: FilePlatEnum.java, v 0.1 2018年7月22日 下午5:24:42 Evance Exp $
 */
public enum FileServerEnum {
    /**fastdfs */
    fastdfs("fastdfs", "fastdfs"),
    /**阿里云OSS */
    aliyunoss("aliyunoss", "阿里云OSS"),
    /** 本地服务器 */
    localserver("localserver", "本地服务器");

    /**  */
    private String code;
    /**  */
    private String desc;

    /**
     * 
     * @param code
     * @return
     */
    public static String getDescByCode(String code) {
        FileServerEnum[] cardStatus = FileServerEnum.values();
        for (FileServerEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return null;
    }

    /**
     * @param code
     * @return
     */
    public static FileServerEnum getEnumByCode(String code) {
        FileServerEnum[] cardStatus = FileServerEnum.values();
        for (FileServerEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    private FileServerEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
