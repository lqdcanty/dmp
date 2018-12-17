package com.efida.sport.dmp.facade.exception;

/**
 * 业务参数验证导致异常，希望让用户看到的错误提示信息使用该异常 
 * @author zhiyang
 * @version $Id: BusinessException.java, v 0.1 2015年12月8日 下午4:29:12 zhiyang Exp $
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 775477636706597359L;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
