package com.efida.sports.dmp.enums;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ParamsTypeEnum.java, v 0.1 2018年8月30日 下午4:30:23 zengbo Exp $
 */
public enum ParamsTypeEnum {

                            /**
                             * 图形
                             */
                            GRAPH("graph", "图形"),
                            /**
                             * 详细
                             */
                            DEATIL("detail", "详细");

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
    private ParamsTypeEnum(String code, String desc) {
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
