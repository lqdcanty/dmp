/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.enums;

/**
 * 
 * @author zhiyang
 * @version $Id: ProjectStatusEnum.java, v 0.1 2018年5月24日 下午4:05:46 zhiyang Exp $
 */
public enum ProjectStatusEnum {

                               //无效
                               InValid("0", "无效"),
                               //有效
                               Valid("1", "有效");

    /**  */
    private String code;
    /**  */
    private String desc;

    /**
     * @param code
     * @param desc
     */
    private ProjectStatusEnum(String code, String desc) {
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

    /**
     * 
     * @param code
     * @return
     */
    public static ProjectStatusEnum getEnumByCode(String code) {
        ProjectStatusEnum[] cardStatus = ProjectStatusEnum.values();
        for (ProjectStatusEnum status : cardStatus) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
