package com.efida.sports.dmp.exception;

import org.apache.log4j.Logger;

/**
 * 
 * 业务异常
 * @author wang yi
 * @desc 
 * @version $Id: DmpBusException.java, v 0.1 2018年8月2日 下午2:26:15 wang yi Exp $
 */
public class DmpBusException extends RuntimeException {

    /**
     * Logger
     */
    Logger                    logger           = Logger.getLogger(DmpBusException.class);
    /**
     * 
     */
    private static final long serialVersionUID = 775477636706597359L;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public DmpBusException() {
        super();
    }

    public DmpBusException(String message, Throwable cause) {
        super(message, cause);
        logger.debug("message :" + message);
    }

    public DmpBusException(String message) {
        super(message);
        logger.debug("message :" + message);
    }

    public DmpBusException(Throwable cause) {
        super(cause);
    }

}
