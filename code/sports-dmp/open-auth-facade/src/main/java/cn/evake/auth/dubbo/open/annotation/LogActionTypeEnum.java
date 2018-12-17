/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.dubbo.open.annotation;

/**
 * 日志记录类型
 * @author wang yi
 * @desc 
 * @version $Id: ActionType.java, v 0.1 2018年4月28日 上午11:19:40 wang yi Exp $
 */
public enum LogActionTypeEnum {

    LOGIN("login"), ADD("add"), UPDATE("update"), DELETE("delete"), OTHER("other"), LOGINOUT(
                                                                                             "loginout"), SYS_ERR(
                                                                                                                  "sys_err"), ;

    private String type;

    private LogActionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
