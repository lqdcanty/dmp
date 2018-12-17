package com.efida.sports.dmp.exception;;

/**
 * 应用层异常
 * @since 1.1
 */
public class ServiceException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2117281866458463200L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super("系统提示: " + message, cause);
	}

	public ServiceException(String message) {
		super("系统提示: " + message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
