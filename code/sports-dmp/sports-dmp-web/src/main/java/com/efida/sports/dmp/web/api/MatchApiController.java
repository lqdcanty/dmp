/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.JsonResultUtil;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 赛事信息获取
 * @author Evance
 * @version $Id: UnitApiController2.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@Controller
@RequestMapping(value = "dmp/api/match", produces = "application/json")
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class MatchApiController {

    private Logger              logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpMatchFacadeClient spMatchFacadeClient;

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "selectMatch", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> selectMatch(@RequestParam(required = false, defaultValue = "") String keyword,
                                           @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                           @RequestParam(required = false, defaultValue = "10000") int pageSize,
                                           HttpServletRequest request) {
        SportsAdminPageResult<SpMatchModel> spMatch = spMatchFacadeClient.searchMatchs(keyword,
            pageNumber, pageSize);
        return JsonResultUtil.getSuccessOpenApiResult("ok", spMatch.getList());
    }

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "selectMatchByProject", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> selectMatchByProject(@RequestParam(required = false, defaultValue = "") String projectCode,
                                                    HttpServletRequest request) {
        List<SpMatchModel> matchModelList = spMatchFacadeClient.getMatchs(projectCode);
        return JsonResultUtil.getSuccessOpenApiResult("ok", matchModelList);
    }

    @ApiOperation(value = "获取所有赛事列表", notes = "获取所有赛事列表")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/enable/matchs", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<List<SpMatchModel>> findMatchs(HttpServletRequest request) {
        ResultWrapper<List<SpMatchModel>> resultWrapper = new ResultWrapper<List<SpMatchModel>>();
        try {
            List<SpMatchModel> enableSpMatchs = spMatchFacadeClient.getEnableSpMatchs();
            resultWrapper.setData(enableSpMatchs);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
