package com.efida.sport.dmp.facade.result;

/**
 * 对外接口返回值 wrapper
 * @author 
 * @desc 该类用于
 * @version $Id: DefaultResult.java, v 0.1 2017年5月20日 下午14:01:09  Exp $
 */
import java.io.Serializable;

public class DefaultResult<T> implements Serializable {

    /**  */
    private static final long serialVersionUID = -8083342000603673482L;

    /**
     * 是否成功
     */
    private boolean           success;

    /**
     * 错误信息
     */
    private String            errorMsg;

    /**
     * 结果对象
     * 
     */
    private T                 resultObj;

    /**
     * 默认构造函数
     */
    public DefaultResult() {
        setSuccess(true);
    }

    /**
     * 根据业务数据构造处理成功的结果
     * 
     * @param t
     */
    public DefaultResult(T t) {
        setSuccess(true);
        setResultObj(t);
    }

    /**
     * 设置错误码
     * 
     * @param errorInfo
     */
    public void setErrorCode(String errorInfo) {
        setSuccess(false);
        setError(errorInfo);
    }

    /**
     * 设置错误信息
     * 
     * @param errorCode
     * @param errorMsg
     */
    private void setError(String errorMsg) {
        setSuccess(false);
        this.errorMsg = errorMsg;
    }

    /**
     * 获取错误描述
     * 
     * @return 错误信息
     */
    public String getErrorInfo() {

        return this.errorMsg;
    }

    /**
     * Getter method for property <tt>success</tt>.
     * 
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     * 
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>errorMsg</tt>.
     * 
     * @return property value of errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter method for property <tt>errorMsg</tt>.
     * 
     * @param errorMsg value to be assigned to property errorMsg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Getter method for property <tt>resultObj</tt>.
     * 
     * @return property value of resultObj
     */
    public T getResultObj() {
        return resultObj;
    }

    /**
     * Setter method for property <tt>resultObj</tt>.
     * 
     * @param resultObj value to be assigned to property resultObj
     */
    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

}