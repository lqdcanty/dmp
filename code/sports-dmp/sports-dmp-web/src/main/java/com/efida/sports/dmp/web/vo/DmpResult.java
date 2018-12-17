package com.efida.sports.dmp.web.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 返回包装信息
 * @author Evance
 * @version $Id: ResultWrapper.java, v 0.1 2017年12月27日 下午11:06:53 Evance Exp $
 */
@ApiModel(value = "接口响应信息")
public class DmpResult<T> implements Serializable {

    private static final long serialVersionUID = 8597508639708679908L;

    @ApiModelProperty(value = "响应码(备注:0:正常1:后台异常)")
    private int               code             = 0;

    @ApiModelProperty(value = "当前承办方账号")
    private String            unitCode;

    @ApiModelProperty(value = "错误信息")
    private String            errorMsg         = "success";

    private T                 data             = null;

    public DmpResult() {
        super();
    }

    public DmpResult(T data) {
        super();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        //当有错误信息时候code 为1
        this.setCode(1);
        this.errorMsg = errorMsg;
    }

    public void setError(int code, String errorMsg) {
        //当有错误信息时候code自定义code
        this.setCode(code);
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

}
