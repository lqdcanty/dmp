/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.score.CompetitionService;
import com.efida.sports.dmp.web.vo.CompetitionVo;

import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author zoutao
 * @version $Id: CompetitionController.java, v 0.1 2018年8月2日 下午4:31:27 zoutao Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/competition", produces = "application/json")
@Api(value = "数据中心-比赛排名接口", tags = "数据中心-比赛排名接口")
public class CompetitionController {

    private Logger              logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpMatchFacadeClient spMatchFacadeClient;

    @Autowired
    private CompetitionService  competitionService;

    @Autowired
    private UserService         userService;

    @ApiOperation(value = "获取比赛列表", notes = "获取比赛列表日志信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页大小", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<CompetitionVo>> getCompetitionList(@RequestParam(required = false) String matchCode,
                                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                                         @RequestParam(required = false, defaultValue = "10") int limit,
                                                                         HttpServletRequest request) {
        ResultWrapper<PagingResult<CompetitionVo>> resultWrapper = new ResultWrapper<PagingResult<CompetitionVo>>();
        try {

            PagingResult<CompetitionEntity> pageCompetition = competitionService
                .selectPageCompetitionByMatchCode(matchCode, page, limit);
            List<CompetitionVo> list = new ArrayList<CompetitionVo>();
            if (pageCompetition.getAllRow() > 0) {
                List<CompetitionEntity> records = pageCompetition.getList();
                for (CompetitionEntity competitionEntity : records) {
                    CompetitionEntity entity = competitionService
                        .getCompetitionByCode(competitionEntity.getCompetitionCode());
                    MatchDetailModel matchModel = spMatchFacadeClient
                        .getMatchDetailModel(entity.getMatchCode());
                    CompetitionVo vo = CompetitionVo.getVo(entity, matchModel);
                    list.add(vo);
                }
            }
            PagingResult<CompetitionVo> pageResult = new PagingResult<CompetitionVo>(list,
                pageCompetition.getAllRow(), page, limit);
            resultWrapper.setData(pageResult);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "获取赛事详情", notes = "获取赛事详情")
    @ApiImplicitParams({ @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/match/detail", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<CompetitionVo> getMatchDetail(@RequestParam(required = true) String matchCode,
                                                       HttpServletRequest request) {
        ResultWrapper<CompetitionVo> resultWrapper = new ResultWrapper<CompetitionVo>();
        try {

            MatchDetailModel matchModel = spMatchFacadeClient.getMatchDetailModel(matchCode);
            CompetitionVo vo = CompetitionVo.getVo(null, matchModel);
            resultWrapper.setData(vo);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "获取比赛详情", notes = "获取比赛详情")
    @ApiImplicitParams({ @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/detail", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<CompetitionVo> getCompetitionDetail(@RequestParam(required = true) String competitionCode,
                                                             HttpServletRequest request) {
        ResultWrapper<CompetitionVo> resultWrapper = new ResultWrapper<CompetitionVo>();
        try {

            CompetitionEntity entity = competitionService.getCompetitionByCode(competitionCode);
            MatchDetailModel matchModel = spMatchFacadeClient
                .getMatchDetailModel(entity.getMatchCode());
            CompetitionVo vo = CompetitionVo.getVo(entity, matchModel);
            resultWrapper.setData(vo);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "获取排名列表", notes = "获取排名列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "groups", value = "分组编号：a,b", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "areas", value = "赛场编号：n,g", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "events", value = "比赛项目编号,格式：1,2", required = false, dataType = "String"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认第1页)", required = false, dataType = "int"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "scoreRank/list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<OpenScoreRankEntity>> getPcompetitionList(@RequestParam(required = false) String competitionCode,
                                                                                @RequestParam(required = true) String matchCode,
                                                                                @RequestParam(required = false) List<String> groups,
                                                                                @RequestParam(required = false) List<String> areas,
                                                                                @RequestParam(required = false) List<String> events,
                                                                                @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                                HttpServletRequest request) {
        ResultWrapper<PagingResult<OpenScoreRankEntity>> resultWrapper = new ResultWrapper<PagingResult<OpenScoreRankEntity>>();
        try {
            PagingResult<OpenScoreRankEntity> scoreRankEntitys = this.competitionService
                .queryRankByCompetition(competitionCode, null, null, pageNumber, pageSize);
            resultWrapper.setData(scoreRankEntitys);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "修改比赛状态", notes = "修改比赛状态")
    @ApiImplicitParams({ @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "isShow", value = "是否显示(1显示0不显示)", required = true, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/lock", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> lockCompetition(@RequestParam(required = true) String competitionCode,
                                                  @RequestParam Integer isShow,
                                                  HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            boolean status = competitionService.lockCompetition(competitionCode, isShow);
            resultWrapper.setData(status);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "刷新比赛排名", notes = "刷新比赛排名")
    @ApiImplicitParams({ @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = true, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "rank/refresh", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<Boolean> refreshCompetition(@RequestParam(required = true) String competitionCode,
                                                     HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            boolean status = competitionService.refreshRankCompetition(competitionCode);
            resultWrapper.setData(status);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "编辑比赛", notes = "编辑比赛")
    @ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "比赛名称", required = true, dataType = "String"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = true, dataType = "String"),
                         @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = false, dataType = "String"),
                         @ApiImplicitParam(name = "sites", value = "赛场编号,格式：['1','2']", required = false, dataType = "String"),
                         @ApiImplicitParam(name = "events", value = "比赛项目编号,格式：['1',2]", required = false, dataType = "String"),
                         @ApiImplicitParam(name = "groups", value = "分组编号,格式：['1','2','3']", required = false, dataType = "String"),
                         @ApiImplicitParam(name = "rankType", value = "unit:合作伙伴排名 dmp:官方排名", required = false, dataType = "String") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<Boolean> getCallLogList(@RequestParam(required = false) String competitionCode,
                                                 @RequestParam(required = true) String name,
                                                 @RequestParam(required = true) String matchCode,
                                                 @RequestParam(required = false) String sites,
                                                 @RequestParam(required = false) String events,
                                                 @RequestParam(required = false) String groups,
                                                 @RequestParam(required = false) String rankType,
                                                 HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            UserInfoModel userInfo = userService.getUserModelAndExpire(request);
            CompetitionEntity entity = new CompetitionEntity();
            entity.setCompetitionCode(competitionCode);
            entity.setAreas(sites);
            entity.setEvents(events);
            entity.setGroups(groups);
            entity.setMatchCode(matchCode);
            entity.setName(name);
            entity.setRankType(rankType);
            competitionService.editCompetition(entity, userInfo);
            resultWrapper.setData(true);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);

        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);

        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "删除比赛", notes = "删除比赛")
    @ApiImplicitParams({ @ApiImplicitParam(name = "competitionCode", value = "比赛编号", required = true, dataType = "String"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/del", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<Boolean> delete(@RequestParam(required = false) String competitionCode,
                                         HttpServletRequest request) {
        ResultWrapper<Boolean> resultWrapper = new ResultWrapper<Boolean>();
        try {
            competitionService.deleteByCode(competitionCode);
            resultWrapper.setData(true);
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
