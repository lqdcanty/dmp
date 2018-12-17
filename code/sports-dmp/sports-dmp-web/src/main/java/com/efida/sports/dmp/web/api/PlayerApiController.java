/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.dao.entity.PlayerModel;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.template.ApplyInfoTeplate;
import com.efida.sports.dmp.service.template.EnrollInfoTemplate;
import com.efida.sports.dmp.service.template.PlayerTemplate;
import com.efida.sports.dmp.web.cover.CommonCover;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import cn.evake.excel.handel.ExcelHanlel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 数据中心运动员提交信息
 * @author Evance
 * @version $Id: PlayerApiController.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/player", produces = "application/json")
@Api(value = "报名信息接口", tags = "数据中心-报名信息管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class PlayerApiController {

    private Logger                 logger      = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService            userService;

    @Autowired
    private OpenPlayerApplyService playerService;

    /**
     * 个人报名模板文件
     */
    private byte[]                 applyPTemplate;

    /**
     * 团体报名模板
     */
    private byte[]                 applyTTemplate;

    /**
     * 最大下载数
     */
    private int                    maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        applyPTemplate = IOUtils.toByteArray(
            PlayerApiController.class.getResourceAsStream("/excle/运动员报名-个人赛报名-模板.xlsx"));
        logger.info("success load applyPlayerTemplate.....");
        applyTTemplate = IOUtils.toByteArray(
            PlayerApiController.class.getResourceAsStream("/excle/运动员报名-团体赛报名-模板.xlsx"));

    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @ApiOperation(value = "运动员报名信息", notes = "用于运动员报名信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "player", value = "报名名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "报名电话号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "source", value = "渠道(1 来自接口; 其他值来自官网)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "eventType", value = "报名类型（默认全部:   个人:personal,团体：group）", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(applyTime 报名时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<PlayerModel> getPlayerList(@RequestParam(required = false) String unitCode,
                                                  @RequestParam(required = false) String match,
                                                  @RequestParam(required = false) String player,
                                                  @RequestParam(required = false) String playerPhone,
                                                  @RequestParam(required = false) Integer source,
                                                  @RequestParam(required = false) String eventType,
                                                  @RequestParam(required = false) String channelCode,
                                                  @RequestParam(required = false) String startTime,
                                                  @RequestParam(required = false) String endTime,
                                                  @RequestParam(required = false, defaultValue = "gmtCreate") String sortField,
                                                  @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                                  @RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                  HttpServletRequest request) {
        LayerPagevo<PlayerModel> resultWrapper = new LayerPagevo<PlayerModel>();
        try {
            PagingResult<PlayerModel> playerModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            playerModels = playerService.getPlayersWithUser(allowData, userInfo, unitCode,
                match, player, playerPhone, source, channelCode, startTime, endTime, sortField,
                sortOrder, eventType, page, limit);
            resultWrapper.setData(playerModels.getList());
            resultWrapper.setCount(playerModels.getAllRow());
        } catch (DmpBusException ex) {
            resultWrapper.setMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @ApiOperation(value = "运动员报名信息导出", notes = "运动员报名信息导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "exportAll", value = "是否导出所有", required = false, dataType = "boolean", paramType = "query"),
                         @ApiImplicitParam(name = "match", value = "赛事名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "player", value = "报名名称/编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "playerPhone", value = "报名电话号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "source", value = "渠道(1 来自接口; 其他值来自官网)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "eventType", value = "报名类型（默认全部:   个人:personal,团体：group）", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCode", value = "来源(import:模板导入)", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortField", value = "排序字段(score:成绩 partTime 参赛时间 gmtCreate 数据创建时间 )", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "sortOrder", value = "排序方式(asc:升序 desc:降序) 默认降序", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export", method = { RequestMethod.GET })
    public void exportPlayerList(@RequestParam(required = false) String unitCode,
                                 @RequestParam(required = false) Boolean exportAll,
                                 @RequestParam(required = false) String match,
                                 @RequestParam(required = false) String player,
                                 @RequestParam(required = false) String playerPhone,
                                 @RequestParam(required = false) Integer source,
                                 @RequestParam(required = false) String eventType,
                                 @RequestParam(required = false) String channelCode,
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
            long scurrentT = System.currentTimeMillis();
            PagingResult<PlayerModel> pagePlayer = playerService.getPlayersWithUser(
                allowData, userInfo, unitCode, match, player, playerPhone, source, channelCode,
                startTime, endTime, sortField, sortOrder, eventType, page, limit);
            long queryEnd = System.currentTimeMillis();
            System.err.println("query data spends " + (queryEnd - scurrentT) + " ms");
            byte[] excel = null;
            if (StringUtils.isBlank(eventType) || eventType.equals("personal")) {
                excel = new ExcelHanlel<>(EnrollInfoTemplate.class).generTemplateExcel(
                    CommonCover.coverToPlayers(pagePlayer.getList()), "open_enroll_info", 2,
                    applyPTemplate);
            } else {
                //生成团体报名数据
                List<PlayerModel> list = pagePlayer.getList();
                excel = new ExcelHanlel<>(ApplyInfoTeplate.class).generTemplateExcel(
                    CommonCover.coverToApplyInfos(list), "报名信息", 2, applyTTemplate);
                //生成第二个sheet
                excel = new ExcelHanlel<>(PlayerTemplate.class).generTemplateExcel(
                    CommonCover.coverToPlayerInfps(pagePlayer.getList()), "参赛运动员信息", 2, excel);
            }
            System.err
                .println("gener data spends " + (System.currentTimeMillis() - queryEnd) + " ms");
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
    @ApiOperation(value = "报名详细", notes = "用于查询报名详细信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "playerCode", value = "运动员编号", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PlayerModel> getPlayerDetail(@RequestParam(required = true) String playerCode,
                                                      HttpServletRequest request) {
        ResultWrapper<PlayerModel> resultWrapper = new ResultWrapper<PlayerModel>();
        try {
            String unitCode = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //查询详情权限
            Boolean isAllowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            if (!isAllowData) {
                unitCode = userInfo.getUserName();
            }
            resultWrapper
                .setData(playerService.selectByPlayerCodeAndUnitCode(playerCode, unitCode));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
