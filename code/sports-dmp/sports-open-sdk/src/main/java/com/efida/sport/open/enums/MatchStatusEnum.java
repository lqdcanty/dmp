/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.enums;

/**
 * 
 * @author zhiyang
 * @version $Id: MatchStatusEnum.java, v 0.1 2018年5月24日 下午5:48:33 zhiyang Exp $
 */
public enum MatchStatusEnum {

                             //未开始
                             un_start(0, "未开始"),
                             //进行中
                             ongoing(1, "进行中"),
                             //已结束
                             finished(2, "已结束");
    /**  */
    private int    code;
    /**  */
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private MatchStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(int code) {
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

    /**
     * 
     * @param code
     * @return
     */
    public static MatchStatusEnum getEnumByCode(int code) {
        MatchStatusEnum[] cardStatus = MatchStatusEnum.values();
        for (MatchStatusEnum status : cardStatus) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
