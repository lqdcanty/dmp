/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.enmus;

/**
 * 用户权限类型
 * @author wang yi
 * @desc 
 * @version $Id: MenuTypeEnmu.java, v 0.1 2018年9月29日 下午5:35:29 wang yi Exp $
 */
public enum MenuTypeEnmu {

                          catalog("catalog", "目录"), permission("permission", "权限"), url("url",
                                                                                        "URL");

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
        MenuTypeEnmu[] cardStatus = MenuTypeEnmu.values();
        for (MenuTypeEnmu status : cardStatus) {
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
    public static MenuTypeEnmu getEnumByCode(String code) {
        MenuTypeEnmu[] cardStatus = MenuTypeEnmu.values();
        for (MenuTypeEnmu status : cardStatus) {
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
    private MenuTypeEnmu(String code, String desc) {
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
