/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

/**
 * 
 * @author zhiyang
 * @version $Id: MatchAwardModel.java, v 0.1 2018年5月24日 下午5:46:41 zhiyang Exp $
 */
public class OpenMatchAwardModel {
    //奖项名称
    private String awardName;
    //奖项图片
    private String awardImg;

    /**
     * Getter method for property <tt>awardName</tt>.
     * 
     * @return property value of awardName
     */
    public String getAwardName() {
        return awardName;
    }

    /**
     * Setter method for property <tt>awardName</tt>.
     * 
     * @param awardName value to be assigned to property awardName
     */
    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    /**
     * Getter method for property <tt>awardImg</tt>.
     * 
     * @return property value of awardImg
     */
    public String getAwardImg() {
        return awardImg;
    }

    /**
     * Setter method for property <tt>awardImg</tt>.
     * 
     * @param awardImg value to be assigned to property awardImg
     */
    public void setAwardImg(String awardImg) {
        this.awardImg = awardImg;
    }
}
