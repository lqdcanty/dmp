/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.web.vo;

import java.util.List;

/**
 * 百度ueditor上传插件返回信息包装
 * @author wang yi
 * @desc 
 * @version $Id: UeditorResult.java, v 0.1 2018年7月10日 下午1:55:32 wang yi Exp $
 */
public class UeditorResult {
    /**状态 */
    private String              state = "SUCCESS";
    /**URL */
    private String              url;
    /**标题 */
    private String              title;
    /**原始名称 */
    private String              original;

    private List<UeditorResult> list;

    public String setErrMessage(String errMessage) {
        this.state = errMessage;
        return state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public List<UeditorResult> getList() {
        return list;
    }

    public void setList(List<UeditorResult> list) {
        this.list = list;
    }

}
