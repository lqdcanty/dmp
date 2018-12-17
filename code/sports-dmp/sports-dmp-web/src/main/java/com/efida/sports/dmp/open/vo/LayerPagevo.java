/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.open.vo;

import java.util.List;

/**
 *data demo
 *
 *
 *{
 * code: 0,
 * msg: "",
 * count: 1000,
 * data: []
 *} 
 * layUI分页返回数据
 * @author Evance
 * @version $Id: LayerPagevo.java, v 0.1 2018年4月4日 上午1:48:56 Evance Exp $
 */
public class LayerPagevo<T> {

    private Integer code = 0;

    private String  msg;

    private String  unitCode;

    private Long    count;

    private List<T> data;

    public LayerPagevo() {
        super();
    }

    public LayerPagevo(Integer code, String msg, Long count, List<T> data) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.code = 1;
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

}
