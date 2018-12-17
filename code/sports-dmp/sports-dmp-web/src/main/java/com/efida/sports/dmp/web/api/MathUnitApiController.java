/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.web.vo.DmpResult;
import com.efida.sports.dmp.web.vo.MatchDetailVo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 数据中心成绩提交信息
 * @author Evance
 * @version $Id: PlayerApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/match", produces = "application/json")
@Api(value = "赛事信息接口", tags = "数据中心-赛事信息管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class MathUnitApiController {

    private Logger              logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService         userService;

    @Autowired
    private SpMatchFacadeClient spMatchService;

    @Authority(permissionCode = "MjAxODA3MTgxODMxMDA=")
    @ApiOperation(value = "赛事信息", notes = "用于赛事信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "keyword", value = " 赛事名称/项目名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public DmpResult<PagingResult<SpMatchModel>> getMatchUnitList(@RequestParam(required = false) String unitCode,
                                                                  @RequestParam(required = false) String keyword,
                                                                  @RequestParam(required = false, defaultValue = "1") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                                  HttpServletRequest request) {
        DmpResult<PagingResult<SpMatchModel>> resultWrapper = new DmpResult<PagingResult<SpMatchModel>>();
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            if (!allowData && StringUtils.isNotBlank(unitCode)
                && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }
            if (!allowData) {
                unitCode = userInfo.getUserName();
                resultWrapper.setUnitCode(unitCode);
            }
            SportsAdminPageResult<SpMatchModel> matchs = spMatchService
                .getUnitMatchByKeyWord(unitCode, keyword, page, limit);
            resultWrapper.setData(
                new PagingResult<SpMatchModel>(matchs.getList(), matchs.getAllRow(), page, limit));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA3MTgxODMxMDA=")
    @ApiOperation(value = "赛事信息", notes = "用于赛事信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    @ResponseBody
    public DmpResult<MatchDetailVo> getUnitMatch(@RequestParam(required = true) String matchCode,
                                                 HttpServletRequest request) {
        DmpResult<MatchDetailVo> resultWrapper = new DmpResult<MatchDetailVo>();
        try {
            List<MatchDetailModel> matchs = spMatchService.queryMatches(matchCode, null, null, null,
                null, null, 1, 1);
            if (CollectionUtils.isNotEmpty(matchs) && matchs.size() == 1) {
                resultWrapper.setData(MatchDetailVo.coverToVo(matchs.get(0)));
            }
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
