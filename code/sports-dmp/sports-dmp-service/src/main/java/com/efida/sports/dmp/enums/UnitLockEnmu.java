/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.enums;

/**
 * 用户锁定状态
 * @author wang yi
 * @desc 
 * @version $Id: MatchRegEums.java, v 0.1 2018年5月25日 下午3:22:15 wang yi Exp $
 */
public enum UnitLockEnmu {

                          /**锁定 */
                          lock("1", "锁定"),
                          /** 未锁定 */
                          unlock("0", "未锁定");

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
        UnitLockEnmu[] cardStatus = UnitLockEnmu.values();
        for (UnitLockEnmu status : cardStatus) {
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
    private UnitLockEnmu(String code, String desc) {
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
