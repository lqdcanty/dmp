/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.file.enmus;

/**
 * 文件平台枚举
 * @author Evance
 * @version $Id: FilePlatEnum.java, v 0.1 2018年7月22日 下午5:24:42 Evance Exp $
 */
public enum FilePlatEnum {

    /**其他 */
    other("other", "其他"),
    /** 节节升 */
    jjsheng("jjsheng", "节节升");

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
        FilePlatEnum[] cardStatus = FilePlatEnum.values();
        for (FilePlatEnum status : cardStatus) {
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
    public static FilePlatEnum getEnumByCode(String code) {
        FilePlatEnum[] cardStatus = FilePlatEnum.values();
        for (FilePlatEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    private FilePlatEnum(String code, String desc) {
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
