/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.score.ScoreRankService;
import com.efida.sports.dmp.service.template.ScoreRankTemplate;
import com.efida.sports.dmp.web.cover.CommonCover;
import com.efida.sports.dmp.web.vo.DmpResult;
import com.efida.sports.dmp.web.vo.ScoreRankVo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.excel.handel.ExcelHanlel;
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
@RequestMapping(value = "dmp/api/score/rank", produces = "application/json")
@Api(value = "成绩排名接口", tags = "数据中心-成绩排名信息管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class PlayerScoreRankApiController {

    private Logger           logger      = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService      userService;

    @Autowired
    private ScoreRankService scoreRankService;

    /**
     * 排名模板文件
     */
    private byte[]           scoreRankTemplate;

    /**
     * 最大下载数
     */
    private int              maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        scoreRankTemplate = IOUtils
            .toByteArray(PlayerApiController.class.getResourceAsStream("/excle/成绩排名-模板.xlsx"));
        logger.info("success load applyPlayerTemplate.....");
    }

    @Authority(permissionCode = "MjAxODA2MjExODI0MDI=")
    @ApiOperation(value = "成绩排名信息", notes = "用于成绩排名信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "运动员手机", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerName", value = "运动员名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(score:成绩  ranking:排名 partTime 参赛时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "socreType", value = "排名方式(dmp:官方 unit:承办方) 默认 全部", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public DmpResult<PagingResult<ScoreRankVo>> getPlayerList(@RequestParam(required = false) String unitCode,
                                                              @RequestParam(required = false) String channelCode,
                                                              @RequestParam(required = false) String match,
                                                              @RequestParam(required = false) String playerPhone,
                                                              @RequestParam(required = false) String playerName,
                                                              @RequestParam(required = false) String startTime,
                                                              @RequestParam(required = false) String endTime,
                                                              @RequestParam(required = false, defaultValue = "gmtCreate") String sortField,
                                                              @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                                              @RequestParam(required = false, defaultValue = "") String socreType,
                                                              @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                                              @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                              HttpServletRequest request) {
        DmpResult<PagingResult<ScoreRankVo>> resultWrapper = new DmpResult<PagingResult<ScoreRankVo>>();
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            PagingResult<OpenScoreRankEntity> scorePage = scoreRankService.getScoreRankWithUser(
                allowData, userInfo, unitCode, socreType, match, playerPhone, playerName, startTime,
                endTime, channelCode, sortField, sortOrder, pageNumber, pageSize);
            resultWrapper
                .setData(new PagingResult<ScoreRankVo>(ScoreRankVo.coverToVos(scorePage.getList()),
                    scorePage.getAllRow(), pageNumber, pageSize));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @ApiOperation(value = "成绩排名导出", notes = "用于成绩排名导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "exportAll", value = "是否导出所有", required = false, dataType = "boolean", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "运动员手机", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerName", value = "运动员名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(score:成绩  ranking:排名 partTime 参赛时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "socreType", value = "排名方式(dmp:官方 unit:承办方) 默认 全部", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export", method = { RequestMethod.GET })
    public void getPlayerList(@RequestParam(required = false) String unitCode,
                              @RequestParam(required = false) Boolean exportAll,
                              @RequestParam(required = false) String channelCode,
                              @RequestParam(required = false) String match,
                              @RequestParam(required = false) String playerPhone,
                              @RequestParam(required = false) String playerName,
                              @RequestParam(required = false) String startTime,
                              @RequestParam(required = false) String endTime,
                              @RequestParam(required = false, defaultValue = "gmtCreate") String sortField,
                              @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                              @RequestParam(required = false, defaultValue = "") String socreType,
                              @RequestParam(required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(required = false, defaultValue = "10") int pageSize,
                              HttpServletRequest request, HttpServletResponse repson) {
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //导出所有数据
            if (exportAll != null && exportAll == true) {
                pageNumber = 1;
                pageSize = maxDownLoad;
            }
            PagingResult<OpenScoreRankEntity> scorePage = scoreRankService.getScoreRankWithUser(
                allowData, userInfo, unitCode, socreType, match, playerPhone, playerName, startTime,
                endTime, channelCode, sortField, sortOrder, pageNumber, pageSize);
            byte[] excel = new ExcelHanlel<>(ScoreRankTemplate.class).generTemplateExcel(
                CommonCover.coverToPlayerScoreRanks(scorePage.getList()), "open_score_rank", 2,
                scoreRankTemplate);
            ServletOutputStream outputStream = repson.getOutputStream();
            repson.setContentType("application/vnd.ms-excel;charset=utf-8");
            repson.setCharacterEncoding("utf-8");
            repson.setHeader("Content-Disposition",
                String.format("attachment;filename=%s.xlsx", new Date().getTime()));
            IOUtils.write(excel, outputStream);
        } catch (IOException e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA2MjExODI0MDI=")
    @ApiOperation(value = "成绩排名详细", notes = "用于查询成绩排名详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "idempotentId", value = "排名单唯一标识", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    @ResponseBody
    public DmpResult<ScoreRankVo> getPlayerDetail(@RequestParam(required = true) String idempotentId,
                                                  HttpServletRequest request) {
        DmpResult<ScoreRankVo> resultWrapper = new DmpResult<ScoreRankVo>();
        try {
            String unitCode = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //查询详情权限
            Boolean isAllowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            if (!isAllowData) {
                unitCode = userInfo.getUserName();
            }
            resultWrapper.setData(
                ScoreRankVo.coverToVo(scoreRankService.getScoreRank(unitCode, idempotentId)));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
