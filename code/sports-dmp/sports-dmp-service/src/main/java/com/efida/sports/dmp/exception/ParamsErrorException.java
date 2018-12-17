package com.efida.sports.dmp.exception;

;

/**
 * 
 * @author lijiaqi
 * @date 2015年5月20日
 */
public class ParamsErrorException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 853204046648557453L;

    public ParamsErrorException() {
        super();
    }

    public ParamsErrorException(String message, Throwable cause) {
        super("系统提示: " + message, cause);
    }

    public ParamsErrorException(String message) {
        super("系统提示: " + message);
    }

    public ParamsErrorException(Throwable cause) {
        super(cause);
    }

}
