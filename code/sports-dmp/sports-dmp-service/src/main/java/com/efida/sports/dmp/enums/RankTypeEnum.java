/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.enums;

/**
 * 
 * @author lizhiyang
 * @version $Id: RankTypeEnum.java, v 0.1 2018年8月30日 下午2:54:54 lizhiyang Exp $
 */
public enum RankTypeEnum {

                          /**可使用 */
                          dmp("dmp", "官方排名"),
                          /** 禁用 */
                          unit("unit", "合作方");

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
        RankTypeEnum[] cardStatus = RankTypeEnum.values();
        for (RankTypeEnum status : cardStatus) {
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
    public static RankTypeEnum getEnumByCode(String code) {
        RankTypeEnum[] cardStatus = RankTypeEnum.values();
        for (RankTypeEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
    * @param code
    * @param desc
    */
    private RankTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
    * Getter method for property <tt>code</tt>.
    * 
    * @return property value of code
    */
    public String getCode() {
        return code;
    }

    /**
    * Setter method for property <tt>code</tt>.
    * 
    * @param code value to be assigned to property code
    */
    public void setCode(String code) {
        this.code = code;
    }

    /**
    * Getter method for property <tt>desc</tt>.
    * 
    * @return property value of desc
    */
    public String getDesc() {
        return desc;
    }

    /**
    * Setter method for property <tt>desc</tt>.
    * 
    * @param desc value to be assigned to property desc
    */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
