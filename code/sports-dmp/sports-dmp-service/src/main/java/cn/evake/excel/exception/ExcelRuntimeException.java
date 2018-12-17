package cn.evake.excel.exception;

/**
 * 
 * excel自定义异常
 * @author wang yi
 * @desc 
 * @version $Id: ExcelRuntimeException.java, v 0.1 2018年7月27日 下午3:42:32 wang yi Exp $
 */
public class ExcelRuntimeException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;

    public static String      SHEETNOTFOUND;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ExcelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelRuntimeException(String message) {
        super(message);
    }

    public ExcelRuntimeException(Throwable cause) {
        super(cause);
    }

}
