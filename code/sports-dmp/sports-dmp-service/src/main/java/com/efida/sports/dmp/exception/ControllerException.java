package com.efida.sports.dmp.exception;

/**
 * Action层定义的异常
 * @since 1.1
 */
public class ControllerException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1174631778969969863L;

    public ControllerException() {
        super();
    }

    public ControllerException(String message, Throwable cause) {
        super("Controller层异常: " + message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

}
