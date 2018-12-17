/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.annotation;

/**
 * 日志记录类型
 * @author wang yi
 * @desc 
 * @version $Id: ActionType.java, v 0.1 2018年4月28日 上午11:19:40 wang yi Exp $
 */
public enum LogActionTypeEnum {

                               LOGIN("登录系统"), OTHER("其他"), OPERATE("操作日志"), VIEWPAGE("页面访问"), LOGINOUT("退出登录"), SYS_ERR("系统错误"),;

    private String type;

    private LogActionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
