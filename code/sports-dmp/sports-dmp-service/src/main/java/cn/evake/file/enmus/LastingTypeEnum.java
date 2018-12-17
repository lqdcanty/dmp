/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.file.enmus;

/**
 * 
 * @author Evance
 * @version $Id: LastingTypeEnum.java, v 0.1 2018年7月22日 下午5:31:53 Evance Exp $
 */
public enum LastingTypeEnum {
    /**其他 */
    temp("1", "临时文件"),
    /** 节节升 */
    forever("0", "永久");

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
        LastingTypeEnum[] cardStatus = LastingTypeEnum.values();
        for (LastingTypeEnum status : cardStatus) {
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
    public static LastingTypeEnum getEnumByCode(String code) {
        LastingTypeEnum[] cardStatus = LastingTypeEnum.values();
        for (LastingTypeEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    private LastingTypeEnum(String code, String desc) {
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
