/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;

/**
 * 项目信息。 如 跑步，跳水，篮球，足球，跳绳
 * @author zhiyang
 * @version $Id: OpenProjectModel.java, v 0.1 2018年5月24日 下午3:54:56 zhiyang Exp $
 */
public class OpenProjectModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long              id;

    /**
     *项目编号
     */
    private String            projectCode;
    /**
     * 项目名称
     */
    private String            projectName;

    /**
     * 背景图片
     */
    private String            imageUrl;

    /**
     * 有效状态
     * @since  0 无效 1有效
     */
    private String            status;

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>projectCode</tt>.
     * 
     * @return property value of projectCode
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Setter method for property <tt>projectCode</tt>.
     * 
     * @param projectCode value to be assigned to property projectCode
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Getter method for property <tt>projectName</tt>.
     * 
     * @return property value of projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Setter method for property <tt>projectName</tt>.
     * 
     * @param projectName value to be assigned to property projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Getter method for property <tt>imageUrl</tt>.
     * 
     * @return property value of imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter method for property <tt>imageUrl</tt>.
     * 
     * @param imageUrl value to be assigned to property imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
