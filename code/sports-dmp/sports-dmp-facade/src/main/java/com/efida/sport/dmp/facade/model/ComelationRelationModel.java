/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

/**
 * 
 * @author zoutao
 * @version $Id: ComelationRelationVo.java, v 0.1 2018年8月2日 下午6:33:19 zoutao Exp $
 */
public class ComelationRelationModel implements Serializable {

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
