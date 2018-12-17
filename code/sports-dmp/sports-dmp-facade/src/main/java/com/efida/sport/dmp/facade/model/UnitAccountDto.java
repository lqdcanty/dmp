/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

/**
 * 承办方账号信息
 * @author wang yi
 * @desc 
 * @version $Id: UnitAccountDto.java, v 0.1 2018年7月12日 下午3:40:10 wang yi Exp $
 */
public class UnitAccountDto implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    //编号 
    private String            unitCode;

    //承办方名称
    private String            unitName;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

}
