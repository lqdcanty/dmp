package cn.evake.auth.dubbo.open.exception;

/**
 * 业务参数验证导致异常
 * @author wang yi
 * @desc 
 * @version $Id: BusinessException.java, v 0.1 2018年8月2日 下午2:45:28 wang yi Exp $
 */
public class OpenAuthBusException extends RuntimeException {

    private static final long serialVersionUID = 775477636706597359L;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public OpenAuthBusException() {
        super();
    }

    public OpenAuthBusException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAuthBusException(String message) {
        super(message);
    }

    public OpenAuthBusException(Throwable cause) {
        super(cause);
    }

}
