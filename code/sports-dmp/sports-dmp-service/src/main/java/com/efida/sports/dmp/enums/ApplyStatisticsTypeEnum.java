/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.enums;

/**
 * 
 * @author zoutao
 * @version $Id: ApplyStatisticsTypeEnum.java, v 0.1 2018年9月13日 下午4:50:57 zoutao Exp $
 */
public enum ApplyStatisticsTypeEnum {

                                     /**
                                      * 报名一次
                                      */
                                     APPLY_ONCE("apply_once", "报名一次"),
                                     /**
                                      * 报名多次
                                      */
                                     APPLY_MANY("apply_many", "报名多次"),
                                     /**
                                      * 报名对个项目
                                      */
                                     APPLY_MANY_EVENT("apply_many_event", "报名多个项目");

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
        ApplyStatisticsTypeEnum[] enums = ApplyStatisticsTypeEnum.values();
        for (ApplyStatisticsTypeEnum type : enums) {
            if (type.getCode().equals(code)) {
                return type.getDesc();
            }
        }
        return null;
    }

    /**
    * @param code
    * @return
    */
    public static UnitLockEnmu getEnumByCode(String code) {
        UnitLockEnmu[] cardStatus = UnitLockEnmu.values();
        for (UnitLockEnmu status : cardStatus) {
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
    private ApplyStatisticsTypeEnum(String code, String desc) {
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
