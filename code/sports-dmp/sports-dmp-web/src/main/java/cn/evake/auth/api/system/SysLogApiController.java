/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.api.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.dao.model.SysUserLog;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: SysManagerController.java, v 0.1 2018年2月26日 上午11:21:33 wang yi Exp $
 */
@RestController
@RequestMapping(value = "api/sys/log", produces = "application/json")
@Api(value = "系统日志接口", tags = "系统日志管理")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class SysLogApiController {

    private Logger     logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;

    /**
     * 日志查询
     * @return
     */
    @ApiOperation(value = "日志查询", notes = "用于日志查询")
    @ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字(日志内容 用户)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<SysUserLog>> getSysLogsl(@RequestParam(required = false) String keyword,
                                                               @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                               HttpServletRequest request) {
        ResultWrapper<PagingResult<SysUserLog>> resultWrapper = new ResultWrapper<PagingResult<SysUserLog>>();
        try {
            PagingResult<SysUserLog> pageUserLog = logService.getPageUserLog(keyword, pageNumber,
                pageSize);
            resultWrapper.setData(pageUserLog);
        } catch (AuthBusException e) {
            logger.error("日志信息获取错误 ", e);
            resultWrapper.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("日志信息获取错误 ", e);
            resultWrapper.setErrorMsg("系统异常");
        }
        return resultWrapper;
    }

}
