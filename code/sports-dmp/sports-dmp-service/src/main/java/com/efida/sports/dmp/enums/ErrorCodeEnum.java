package com.efida.sports.dmp.enums;

/**
 * 
 * @author zoutao
 * @version $Id: OperationType.java, v 0.1 2017年5月5日 下午2:01:22 zoutao Exp $
 */
public enum ErrorCodeEnum {
                           /**
                            * 系统繁忙，此时请开发者稍候再试
                            */
                           SYSTEM_BUSY(-1, "系统繁忙，此时请开发者稍候再试"),
                           /**
                            * 参数错误
                            */
                           PARAM_ERROR(201, "参数错误"),
                           /**
                            * 系统异常
                            */
                           SERVER_ERROR(202, "系统异常"),
                           /**
                            * 不合法的参数
                            */
                           ILLEGAL_PARAMAS(203, "不合法的参数"),
                           /**
                            * 未登录
                            */
                           NOT_LOGGED(204, "未登录"),

                           /**
                            * 未绑定手机号
                            */
                           UN_BIND_PHONE(205, "未绑定手机号");

    private Integer code;
    private String  msg;

    private ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
        this.msg = msg;
    }

}
