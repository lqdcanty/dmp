package cn.evake.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务参数验证导致异常，希望让用户看到的错误提示信息使用该异常 
 * @author zhiyang
 * @version $Id: BusinessException.java, v 0.1 2015年12月8日 下午4:29:12 zhiyang Exp $
 */
public class AuthBusException extends RuntimeException {

    /**
     * Logger
     */
    private Logger            logger           = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    private static final long serialVersionUID = 775477636706597359L;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public AuthBusException() {
        super();
    }

    public AuthBusException(String message, Throwable cause) {
        super(message, cause);
        logger.debug("message :" + message);
    }

    public AuthBusException(String message) {
        super(message);
        logger.debug("message :" + message);
    }

    public AuthBusException(Throwable cause) {
        super(cause);
    }

}
