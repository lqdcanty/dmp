package cn.evake.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 未登陆发生的错误信息
 * @author Evance
 * @version $Id: AuthException.java, v 0.1 2018年5月4日 下午12:52:01 Evance Exp $
 */
public class AuthException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 2588742087808966160L;
    /**
     * Logger
     */
    private Logger            logger           = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public AuthException() {
        super();
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
        logger.debug("message :" + message);
    }

    public AuthException(String message) {
        super(message);
        logger.debug("message :" + message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

}
