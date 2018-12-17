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

import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.template.ScoreTemplate;
import com.efida.sports.dmp.web.cover.CommonCover;

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
 * 数据中心比赛成绩
 * @author Evance
 * @version $Id: PlayerApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/score", produces = "application/json")
@Api(value = "成绩信息接口", tags = "数据中心-成绩信息管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class PlayerScoreController {

    private Logger           logger      = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService      userService;

    @Autowired
    private OpenScoreService openScoreService;

    /**
     * 报名模板文件
     */
    private byte[]           scoreTemplate;

    /**
     * 最大下载数
     */
    private int              maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        scoreTemplate = IOUtils
            .toByteArray(PlayerApiController.class.getResourceAsStream("/excle/成绩信息-模板.xlsx"));
        logger.info("success load applyPlayerTemplate.....");
    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @ApiOperation(value = "运动员成绩信息", notes = "用于运动员成绩信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerName", value = "运动员名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "运动员电话", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "valid", value = "状态", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "syncStatus", value = "证书状态(1已处理,2未处理,3需要人工介入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(score:成绩 partTime 参赛时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<OpenScoreEntity> getPlayerScoreList(@RequestParam(required = false) String unitCode,
                                                           @RequestParam(required = false) String channelCode,
                                                           @RequestParam(required = false) String match,
                                                           @RequestParam(required = false) String playerName,
                                                           @RequestParam(required = false) String playerPhone,
                                                           @RequestParam(required = false) String valid,
                                                           @RequestParam(required = false) Integer syncStatus,
                                                           @RequestParam(required = false) String startTime,
                                                           @RequestParam(required = false) String endTime,
                                                           @RequestParam(required = false, defaultValue = "gmtCreate") String sortField,
                                                           @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                                           @RequestParam(required = false, defaultValue = "1") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int limit,
                                                           HttpServletRequest request) {
        LayerPagevo<OpenScoreEntity> resultWrapper = new LayerPagevo<OpenScoreEntity>();
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            PagingResult<OpenScoreEntity> resultModels = openScoreService.getPagePlayerLikeParams(
                allowData, userInfo, unitCode, match, playerName, playerPhone, valid, syncStatus,
                startTime, endTime, channelCode, sortField, sortOrder, page, limit);
            resultWrapper.setData(resultModels.getList());
            resultWrapper.setCount(resultModels.getAllRow());
        } catch (DmpBusException ex) {
            resultWrapper.setMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @ApiOperation(value = "运动员成绩导出", notes = "用于运动员成绩导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "exportAll", value = "是否导出所有", required = false, dataType = "boolean", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerName", value = "运动员名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "运动员电话", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "valid", value = "状态", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(score:成绩  partTime 参赛时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export", method = { RequestMethod.GET })
    public void exportPlayerScore(@RequestParam(required = false) String unitCode,
                                  @RequestParam(required = false) Boolean exportAll,
                                  @RequestParam(required = false) String channelCode,
                                  @RequestParam(required = false) String match,
                                  @RequestParam(required = false) String playerName,
                                  @RequestParam(required = false) String playerPhone,
                                  @RequestParam(required = false) String valid,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                  @RequestParam(required = false, defaultValue = "gmtCreate") String sortField,
                                  @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                  @RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                  HttpServletRequest request, HttpServletResponse repson) {
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //导出所有数据
            if (exportAll != null && exportAll == true) {
                page = 1;
                limit = maxDownLoad;
            }
            PagingResult<OpenScoreEntity> pagePlayerSc = openScoreService.getPagePlayerLikeParams(
                allowData, userInfo, unitCode, match, playerName, playerPhone, valid, null,
                startTime, endTime, channelCode, sortField, sortOrder, page, limit);
            byte[] excel = new ExcelHanlel<>(ScoreTemplate.class).generTemplateExcel(
                CommonCover.coverToPlayerScores(pagePlayerSc.getList()), "open_score", 2,
                scoreTemplate);
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

}
