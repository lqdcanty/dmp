package com.efida.sports.dmp.exception;

/**
 * @since 1.1
 */
public class DaoException extends BaseRuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7176843013958471483L;

    public DaoException() {
        super();
    }

    public DaoException(String message, Throwable cause) {
        super("Dao层异常: " + message, cause);
    }

    public DaoException(String message) {
        super("Dao层异常: " + message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
