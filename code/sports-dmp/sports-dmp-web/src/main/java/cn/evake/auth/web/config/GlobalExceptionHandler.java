package cn.evake.auth.web.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.evake.auth.constants.WebConstants;
import cn.evake.auth.exception.AuthException;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.web.vo.ResultWrapper;

/**
 *全局异常处理
 * @author wang
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultWrapper<Map<String, Object>> handleJSONError(HttpServletRequest req, Exception e) {
        log.info("encounter an exception", e);
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        resultWrapper.setCode(WebConstants.SERVER_ERROR);
        resultWrapper.setErrorMsg(e.getMessage());
        return resultWrapper;
    }

    /**
     * 业务异常处理
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = AuthBusException.class)
    @ResponseBody
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.info("encounter an BusinessException", e);
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        resultWrapper.setCode(WebConstants.SERVER_ERROR);
        resultWrapper.setErrorMsg(e.getMessage());
        return resultWrapper;
    }

    /**
     * 登陆异常处理
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public Object authErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.info("encounter an BusinessException", e);
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        resultWrapper.setCode(WebConstants.NO_AUTH);
        resultWrapper.setErrorMsg(e.getMessage());
        return resultWrapper;
    }

}