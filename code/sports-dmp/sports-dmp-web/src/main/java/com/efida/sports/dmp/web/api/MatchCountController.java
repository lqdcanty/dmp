/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.GoodsOrderCountModel;
import com.efida.sports.dmp.biz.score.OpenApplyinfoCountService;
import com.efida.sports.dmp.biz.score.OpenScoreCountService;
import com.efida.sports.dmp.biz.score.ReportGoodsOrderService;
import com.efida.sports.dmp.biz.score.ReportMatchFinishService;
import com.efida.sports.dmp.biz.score.ReportMatchSourceService;
import com.efida.sports.dmp.biz.score.ReportUnitEnrollService;
import com.efida.sports.dmp.biz.score.ReportUnitFinishService;
import com.efida.sports.dmp.biz.score.quartz.ReportCountTask;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;
import com.efida.sports.dmp.dao.entity.ReportMatchFinishEntity;
import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;
import com.efida.sports.dmp.service.dubbo.intergration.GoodsOrderFacade;
import com.efida.sports.dmp.service.template.GoodsOrderEverydayTemplate;
import com.efida.sports.dmp.service.template.MatchEnrollEverydayTemplate;
import com.efida.sports.dmp.service.template.MatchEnrollTotalTemplate;
import com.efida.sports.dmp.service.template.ReportMatchSourceTemplate;
import com.efida.sports.dmp.service.template.ReportOfficialMatchTemplate;
import com.efida.sports.dmp.service.template.ReportUnitEnrollTemplate;
import com.efida.sports.dmp.service.template.UnitFinishEnrollTemplate;
import com.efida.sports.dmp.service.unit.UnitService;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.utils.JsonResultUtil;
import com.efida.sports.dmp.utils.NumberUtil;
import com.efida.sports.dmp.web.vo.MatchVo;
import com.efida.sports.dmp.web.vo.ReportGoodsOrderVo;
import com.efida.sports.dmp.web.vo.ReportMatchSourceDetailVo;
import com.efida.sports.dmp.web.vo.ReportMatchSourceVo;
import com.efida.sports.dmp.web.vo.ReportUnitEnrollVo;
import com.efida.sports.dmp.web.vo.UnitVo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import cn.evake.excel.handel.ExcelHanlel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 赛事统计分析
 * 
 * @author zengbo
 * @version $Id: MatchCountController.java, v 0.1 2018年8月30日 下午6:02:05 zengbo Exp $
 */
//@Controller

@RestController
@Api(value = "赛事统计分析接口", tags = "赛事统计分析")
@RequestMapping(value = "/match", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class MatchCountController {

    private Logger                    logger      = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportUnitEnrollService   reportUnitEnrollService;

    @Autowired
    private ReportUnitFinishService   reportUnitFinishService;

    @Autowired
    private ReportMatchSourceService  reportMatchSourceService;

    @Autowired
    private ReportMatchFinishService  reportMatchFinishService;

    @Autowired
    private UnitService               openUnitService;

    @Autowired
    private OpenApplyinfoCountService openApplyinfoCountService;

    @Autowired
    private OpenScoreCountService     openScoreCountService;

    @Autowired
    private ReportGoodsOrderService   reportGoodsOrderService;

    @Autowired
    private GoodsOrderFacade          goodsOrderFacade;

    /**
     * 承办方报名人次统计模板
     */
    private byte[]                    reportUnitEnrollTemplate;

    /**
     * 赛事报名人次统计模板
     */
    private byte[]                    reportMatchSourceTemplate;

    /**
     * 官方名人次统计模板
     */
    private byte[]                    reportOfficialMatchTemplate;

    /**
     * 赛事报名总数模板
     */
    private byte[]                    matchEnrollTotalTemplate;

    /**
     * 每日新增报名人次
     */
    private byte[]                    matchEnrollPersonDayTemplate;

    /**
     * 项目方完善人才比
     */
    private byte[]                    unitFinishEnrollTemplate;

    /**
     * 奖章购买数据分析模板
     */
    private byte[]                    goodsOrderCountTemplate;

    private int                       maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        reportUnitEnrollTemplate = IOUtils.toByteArray(
            MatchCountController.class.getResourceAsStream("/excle/项目方报名人次统计表-模板.xlsx"));
        reportMatchSourceTemplate = IOUtils.toByteArray(
            MatchCountController.class.getResourceAsStream("/excle/赛事报名人次统计表-模板.xlsx"));
        reportOfficialMatchTemplate = IOUtils
            .toByteArray(MatchCountController.class.getResourceAsStream("/excle/官方平台完赛率-模板.xlsx"));
        matchEnrollTotalTemplate = IOUtils.toByteArray(
            MatchCountController.class.getResourceAsStream("/excle/赛事总报名数统计表-模板.xlsx"));
        matchEnrollPersonDayTemplate = IOUtils
            .toByteArray(MatchCountController.class.getResourceAsStream("/excle/每日新增报名人次-模板.xlsx"));
        unitFinishEnrollTemplate = IOUtils.toByteArray(
            MatchCountController.class.getResourceAsStream("/excle/项目方完赛人次比统计-模板.xlsx"));
        goodsOrderCountTemplate = IOUtils
            .toByteArray(MatchCountController.class.getResourceAsStream("/excle/奖章购买数据分析-模板.xlsx"));

    }

    @RequestMapping("/count/query")
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    public ModelAndView matchCountIndex() {
        return new ModelAndView("/view/pages/chart/chartAnalysis");
    }

    @RequestMapping("/count/appquery")
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    public ModelAndView matchCountAppIndex() {
        return new ModelAndView("/view/pages/chart/macthChart");
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "总报名数接口", notes = "用于赛事总报名数查询")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "enroll/total", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getMatchUnitList(HttpServletRequest request) {
        try {
            int enroll = reportUnitEnrollService.countTotalUnitEnroll(null, null);
            int finish = reportUnitFinishService.countTotalUnitFinish(null, null);
            //            int applyPeople = reportUnitEnrollService.getTotalApplyPeople();
            //            int finishPepole = reportUnitEnrollService.getTotalFinishPeople();
            int applyPeople = reportMatchSourceService.enrolmentCount(null, null);
            int finishPepole = reportMatchSourceService.finishMatchCount(null, null);
            String percent = NumberUtil.percentageConvert(finish, enroll);
            String peoplePercent = NumberUtil.percentageConvert(finishPepole, applyPeople);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("enroll", NumberUtil.intFormatStr(enroll));
            map.put("finish", NumberUtil.intFormatStr(finish));
            map.put("applyPeople", NumberUtil.intFormatStr(applyPeople));
            map.put("finishPepole", NumberUtil.intFormatStr(finishPepole));
            map.put("percent", percent);
            map.put("peoplePercent", peoplePercent);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    /**
     * 承办方TOP5数据
     * 
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方TOP5数据", notes = "承办方TOP5数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/unit/top5", method = { RequestMethod.GET })
    ResultWrapper<List<UnitVo>> getUnitTop5(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String type) {
        ResultWrapper<List<UnitVo>> resultWrapper = new ResultWrapper<List<UnitVo>>();
        try {
            cn.evake.auth.usermodel.PagingResult<OpenUnitEntity> page = openUnitService
                .getUnitAccounts(keyword, 1, 100);
            List<String> unitCodes = reportUnitEnrollService.queryTop5UnitEnroll(type);
            List<UnitVo> vos = UnitVo.coverVos(page.getList(), unitCodes);
            resultWrapper.setData(vos);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    /**
     * 承办方TOP5数据
     * 
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事TOP5数据", notes = "赛事TOP5数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "关键字(1:官方赛事　2:承办方赛事)", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/match/top5", method = { RequestMethod.GET })
    ResultWrapper<List<MatchVo>> getMatchTop5(String type) {
        ResultWrapper<List<MatchVo>> resultWrapper = new ResultWrapper<List<MatchVo>>();
        try {
            List<SpMatchModel> list = reportMatchSourceService.queryMatchName(null);
            List<String> tops = reportMatchSourceService.matchEnrollTop5(type);
            List<MatchVo> vos = MatchVo.coverVos(list, tops);
            resultWrapper.setData(vos);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "根据日期查询报名人数完善人数接口", notes = "用于报名人数完善人数接口")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "enroll/bydate", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getMatchUnitEnrollList(@RequestParam(required = false) String startTime,
                                                      @RequestParam(required = false) String endTime,
                                                      HttpServletRequest request) {
        try {
            int enroll = reportUnitEnrollService.countTotalUnitEnroll(startTime, endTime);
            int finish = reportUnitFinishService.countTotalUnitFinish(startTime, endTime);
            int enrollPeople = reportMatchSourceService.enrolmentCount(startTime, endTime);
            int finishPeople = reportMatchSourceService.finishMatchCount(startTime, endTime);

            String percent = NumberUtil.percentageConvert(finish, enroll);
            String peoplePercent = NumberUtil.percentageConvert(finishPeople, enrollPeople);
            if (StringUtils.isBlank(startTime)) {
                startTime = reportUnitEnrollService.queryUnitEnrollTime("asc");
            }
            if (StringUtils.isBlank(endTime)) {
                endTime = DateUtil.formatDate(new Date());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("enroll", NumberUtil.intFormatStr(enroll));
            map.put("finish", NumberUtil.intFormatStr(finish));
            map.put("enrollPeople", NumberUtil.intFormatStr(enrollPeople));
            map.put("finishPeople", NumberUtil.intFormatStr(finishPeople));
            map.put("percent", percent);
            map.put("percentPeople", peoplePercent);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名总数统计", notes = "赛事报名总数统计导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/match/enroll/total", method = { RequestMethod.GET })
    public void exportMatchUnitEnrollList(@RequestParam(required = false) String startTime,
                                          @RequestParam(required = false) String endTime,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {

        try {
            int enroll = reportUnitEnrollService.countTotalUnitEnroll(startTime, endTime);
            int finish = reportUnitFinishService.countTotalUnitFinish(startTime, endTime);
            int enrollPeople = reportMatchSourceService.enrolmentCount(startTime, endTime);
            int finishPeople = reportMatchSourceService.finishMatchCount(startTime, endTime);

            byte[] excel = null;
            List<MatchEnrollTotalTemplate> increaseRegisters = MatchEnrollTotalTemplate
                .increaseRegisters(enroll, finish, enrollPeople, finishPeople);

            excel = new ExcelHanlel<>(MatchEnrollTotalTemplate.class)
                .generTemplateExcel(increaseRegisters, "赛事总报名数统计表", 1, matchEnrollTotalTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "赛事总报名数统计表";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "各个承办方人数占比", notes = "用于各个承办方人数占比")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/enroll/percent", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollPercentList(HttpServletRequest request) {
        try {
            List<Map<String, Object>> result = reportUnitEnrollService.countUnitEnrollGroup();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("units", result);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方完赛人次比", notes = "用于承办方完赛人次")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/finish/percent", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitFinishPercentList(@RequestParam(required = false) String likeParams,
                                                        @RequestParam(required = false) String inParams,
                                                        HttpServletRequest request) {
        try {

            List<Map<String, Object>> enrolls = reportUnitEnrollService
                .countToealUnitEnrollByParams(likeParams, inParams);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("enrolls", enrolls);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "项目方完赛人次比导出", notes = "用于项目方完赛人次导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/finish/percent/export", method = { RequestMethod.GET })
    public void getUnitFinishPercentExport(@RequestParam(required = false) String likeParams,
                                           @RequestParam(required = false) String inParams,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        try {
            List<Map<String, Object>> enrolls = reportUnitEnrollService
                .countToealUnitEnrollByParams(likeParams, inParams);
            byte[] excel = null;
            List<UnitFinishEnrollTemplate> increaseRegisters = UnitFinishEnrollTemplate
                .increaseRegisters(enrolls);
            excel = new ExcelHanlel<>(UnitFinishEnrollTemplate.class)
                .generTemplateExcel(increaseRegisters, "项目方完赛人次比统计", 1, unitFinishEnrollTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "项目方完赛人次比统计";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方报名数据（图形 已废弃）", notes = "用于承办方报名数据（图形  已废弃） ")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/enroll/graph", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollGraphList(@RequestParam(required = false) String startTime,
                                                      @RequestParam(required = false) String endTime,
                                                      @RequestParam(required = false) String likeParams,
                                                      @RequestParam(required = false) String inParams,
                                                      HttpServletRequest request) {
        try {
            List<Map<String, Object>> units = reportUnitEnrollService
                .countUnitEnrollByParams(startTime, endTime, likeParams, inParams);
            if (StringUtils.isBlank(startTime)) {
                startTime = reportUnitEnrollService.queryUnitEnrollTime("asc");
            }
            if (StringUtils.isBlank(endTime)) {
                endTime = DateUtil.formatDate(new Date());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("units", units);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方报名数据（柱状图）", notes = "用于承办方报名数据（柱状图）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "date", value = "查询时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/enroll/graphnew", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollGraphListNew(@RequestParam(required = false) String date,
                                                         @RequestParam(required = false) String inParams,
                                                         HttpServletRequest request) {
        try {
            List<Map<String, Object>> units = reportUnitEnrollService
                .countUnitEnrollByParamsNew(date, inParams);
            if (StringUtils.isBlank(date)) {
                //                date = reportUnitEnrollService.queryUnitEnrollTime("asc");
                date = DateUtil.formatDate(new Date());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("units", units);
            map.put("date", date);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方报名数据（详情 已废弃）", notes = "用于承办方报名数据（详情  已废弃）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/enroll/detail", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollDetailList(@RequestParam(required = false) String startTime,
                                                       @RequestParam(required = false) String endTime,
                                                       @RequestParam(required = false) String likeParams,
                                                       @RequestParam(required = false) String inParams,
                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest request) {
        try {
            PagingResult<ReportUnitEnrollEntity> result = reportUnitEnrollService
                .queryUnitEnrollByPage(startTime, endTime, likeParams, inParams, pageNumber,
                    pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", ReportUnitEnrollVo.coverToVos(result.getList()));
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方报名数据（新详情）", notes = "用于承办方报名数据（新详情）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "date", value = "查询时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "unit/enroll/detailnew", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollDetailListNew(@RequestParam(required = false) String date,
                                                          @RequestParam(required = false) String inParams,
                                                          @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest request) {
        try {
            PagingResult<ReportUnitEnrollEntity> result = reportUnitEnrollService
                .queryUnitEnrollByPageNew(date, inParams, pageNumber, pageSize);
            List<Map<String, Object>> units = reportUnitEnrollService.queryAllUnitEnroll(null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", ReportUnitEnrollVo.coverToVosNew(result.getList(), units));
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "承办方报名数据导出（表格数据）", notes = "承办方报名数据导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "date", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/unit/data", method = { RequestMethod.GET })
    public void exportChannelRegister(@RequestParam(required = false) String date,
                                      @RequestParam(required = false) String inParams,
                                      @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      HttpServletRequest request, HttpServletResponse response) {

        try {
            //            PagingResult<ReportUnitEnrollEntity> result = reportUnitEnrollService
            //                .queryUnitEnrollByPage(startTime, endTime, likeParams, inParams, pageNumber,
            //                    pageSize);
            //            List<ReportUnitEnrollEntity> list = result.getList();
            List<ReportUnitEnrollEntity> list = reportUnitEnrollService
                .queryUnitEnrollByExportNew(date, inParams);
            List<Map<String, Object>> units = reportUnitEnrollService.queryAllUnitEnroll(null);
            byte[] excel = null;
            List<ReportUnitEnrollTemplate> increaseRegisters = ReportUnitEnrollTemplate
                .increaseRegisters(list, units);

            excel = new ExcelHanlel<>(ReportUnitEnrollTemplate.class)
                .generTemplateExcel(increaseRegisters, "项目方报名数据明细", 1, reportUnitEnrollTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "项目方报名数据明细";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名每日新增报名人次（折线图）", notes = "赛事报名每日新增报名人次（折线图）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startDate", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endDate", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "enroll/person/total", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getEnrollPersonTotal(@RequestParam(required = false) String startDate,
                                                    @RequestParam(required = false) String endDate,
                                                    @RequestParam(required = false, defaultValue = "match201807300959161453") String matchCode,
                                                    HttpServletRequest request) {
        try {
            List<Map<String, Object>> matchs = reportMatchSourceService
                .countMatchEnrollPerson(startDate, endDate, matchCode);
            if (StringUtils.isBlank(startDate)) {
                startDate = DateUtil.format(DateUtil.getDayAgoTime(5), DateUtil.WEB_FORMAT);
            }
            if (StringUtils.isBlank(endDate)) {
                endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", matchs);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("matchCode", matchCode);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名每日新增报名人次（详情）", notes = "赛事报名每日新增报名人次（详情）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startDate", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endDate", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "enroll/person/count/detail", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getEnrollPersonCountDetail(@RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate,
                                                          @RequestParam(required = false, defaultValue = "match201807300959161453") String matchCode,
                                                          @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest request) {
        try {
            PagingResult<Map<String, Object>> result = reportMatchSourceService
                .countMatchEnrollPersonByPage(startDate, endDate, matchCode, pageNumber, pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", result.getList());
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名每日新增报名人次导出（表格数据）", notes = "赛事报名每日新增报名人次导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startDate", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endDate", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "matchCode", value = "赛事编号", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/enroll/person/count", method = { RequestMethod.GET })
    public void exportEnrollPersonCount(@RequestParam(required = false) String startDate,
                                        @RequestParam(required = false) String endDate,
                                        @RequestParam(required = false, defaultValue = "match201807300959161453") String matchCode,
                                        HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Map<String, Object>> datas = reportMatchSourceService
                .countMatchEnrollPersonByExport(startDate, endDate, matchCode);
            byte[] excel = null;
            List<MatchEnrollEverydayTemplate> increaseRegisters = MatchEnrollEverydayTemplate
                .increaseRegisters(datas);
            excel = new ExcelHanlel<>(MatchEnrollEverydayTemplate.class)
                .generTemplateExcel(increaseRegisters, "每日新增报名人次", 1, matchEnrollPersonDayTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "每日新增报名人次";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名渠道分析数据", notes = "赛事报名渠道分析数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "source/enroll", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollDetailList(@RequestParam(required = false) String likeParams,
                                                       @RequestParam(required = false) String inParams,
                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest request) {
        try {
            PagingResult<ReportMatchSourceEntity> result = reportMatchSourceService
                .getPageSourceEntity(likeParams, inParams, pageNumber, pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", ReportMatchSourceDetailVo.coverToVos(result.getList()));
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "赛事报名渠道数据导出（表格数据）", notes = "赛事报名渠道数据导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/unit/enroll", method = { RequestMethod.GET })
    public void exportUnitEnroll(@RequestParam(required = false) String likeParams,
                                 @RequestParam(required = false) String inParams,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 HttpServletRequest request, HttpServletResponse response) {

        try {
            //            PagingResult<ReportMatchSourceEntity> result = reportMatchSourceService
            //                .getPageSourceEntity(likeParams, inParams, pageNumber, pageSize);
            //            List<ReportMatchSourceEntity> list = result.getList();

            List<ReportMatchSourceEntity> list = reportMatchSourceService
                .getPageSourceEntityByExport(likeParams, inParams);

            byte[] excel = null;
            List<ReportMatchSourceTemplate> increaseRegisters = ReportMatchSourceTemplate
                .increaseRegisters(list);

            excel = new ExcelHanlel<>(ReportMatchSourceTemplate.class)
                .generTemplateExcel(increaseRegisters, "赛事报名渠道分析明细", 1, reportMatchSourceTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "赛事报名渠道分析明细";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "官方平台完善率分析数据（图形）", notes = "官方平台完善率分析数据（图形）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "official/enroll/graph", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getUnitEnrollGraphList(@RequestParam(required = false) String likeParams,
                                                      @RequestParam(required = false) String inParams,
                                                      HttpServletRequest request) {
        try {
            List<Map<String, Object>> result = reportMatchSourceService
                .officialEnrollCount(likeParams, inParams);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("officials", result);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "官方平台完善率分析数据（详细）", notes = "官方平台完善率分析数据（详细）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "official/enroll/detail", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getOfficialEnrollDetailList(@RequestParam(required = false) String likeParams,
                                                           @RequestParam(required = false) String inParams,
                                                           @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest request) {
        try {
            PagingResult<ReportMatchSourceEntity> result = reportMatchSourceService
                .officialEnrollByDetailParams(likeParams, inParams, pageNumber, pageSize);
            List<ReportMatchSourceVo> matchs = ReportMatchSourceVo.coverToVos(result.getList());
            if (matchs != null && matchs.size() > 0) {
                List<Map<String, Object>> finishMatch = reportMatchFinishService
                    .queryALLofficialMatchByCode(null);
                for (ReportMatchSourceVo entity : matchs) {
                    int finishCount = this.getFinishMatchVoEnroll(entity, finishMatch);
                    String percent = NumberUtil.percentageConvertDecimal(finishCount,
                        entity.getEnrollCount());
                    entity.setFinishCount(finishCount);
                    entity.setPercent(percent);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", matchs);
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "官方报名数据导出（表格数据）", notes = "官方报名数据导出数据导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "likeParams", value = "模糊查询参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "inParams", value = "选择多个参数", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/official/enroll", method = { RequestMethod.GET })
    public void exportEnrollOfficial(@RequestParam(required = false) String likeParams,
                                     @RequestParam(required = false) String inParams,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     HttpServletRequest request, HttpServletResponse response) {

        try {
            //            PagingResult<ReportMatchSourceEntity> result = reportMatchSourceService
            //                .officialEnrollByParams(likeParams, inParams, pageNumber, pageSize);
            //            List<ReportMatchSourceEntity> list = result.getList();
            List<ReportMatchSourceEntity> list = reportMatchSourceService
                .officialEnrollByEceport(likeParams, inParams);
            if (list != null && list.size() > 0) {
                List<Map<String, Object>> finishMatch = reportMatchFinishService
                    .queryALLofficialMatchByCode(null);
                for (ReportMatchSourceEntity entity : list) {
                    this.getFinishMatchEnroll(entity, finishMatch);
                }
            }

            byte[] excel = null;
            List<ReportOfficialMatchTemplate> increaseRegisters = ReportOfficialMatchTemplate
                .increaseRegisters(list);

            excel = new ExcelHanlel<>(ReportOfficialMatchTemplate.class)
                .generTemplateExcel(increaseRegisters, "官方平台报名明细", 1, reportOfficialMatchTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "官方平台报名明细";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "获取赛事名称", notes = "获取赛事名称")
    @ApiImplicitParams({ @ApiImplicitParam(name = "matchName", value = "赛事名称", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "enroll/name", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getMatchEnrollNameList(@RequestParam(required = false) String matchName,
                                                      HttpServletRequest request) {
        try {

            List<Map<String, Object>> result = reportMatchSourceService
                .queryMatchNameByParams(matchName);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("names", result);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    /**
     * 获取赛事完赛人数
     * 
     * @param match
     * @param finishMatch
     * @return
     */
    public void getFinishMatchEnroll(ReportMatchSourceEntity match,
                                     List<Map<String, Object>> finishMatch) {
        match.setEnrollTotalCount(0);
        if (finishMatch != null && finishMatch.size() > 0) {
            for (Map<String, Object> map : finishMatch) {
                String matchCode = (String) map.get("matchCode");
                if (match.getMatchCode().equals(matchCode)) {
                    BigDecimal finishCount = (BigDecimal) map.get("finishCount");
                    match.setEnrollTotalCount(finishCount.intValue());
                    continue;
                }
            }
        }

    }

    /**
     * 获取赛事完赛人数
     * 
     * @param match
     * @param finishMatch
     * @return
     */
    public int getFinishMatchVoEnroll(ReportMatchSourceVo match,
                                      List<Map<String, Object>> finishMatch) {
        if (finishMatch == null || finishMatch.size() <= 0) {
            return 0;
        }
        for (Map<String, Object> map : finishMatch) {
            String matchCode = (String) map.get("matchCode");
            if (match.getMatchCode().equals(matchCode)) {
                BigDecimal finishCount = (BigDecimal) map.get("finishCount");
                return finishCount.intValue();
            }
        }
        return 0;
    }

    /**
     * 生成reportUnitEnrollEntity
     * 
     * @param request
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @RequestMapping(value = "/create/reportUnitEnrollEntity", method = { RequestMethod.GET })
    public void createReportUnitEnrollEntity(HttpServletRequest request) {
        try {
            logger.info("statr excute reportUnitEnrollEntity");
            List<String> applydates = openApplyinfoCountService.queryUnitEnrollDate();
            List<ReportUnitEnrollEntity> unitList = openApplyinfoCountService
                .queryUnitEnrollCount("");
            for (String applydate : applydates) {
                List<ReportUnitEnrollEntity> units = ReportCountTask.getUnitList(unitList,
                    applydate);
                reportUnitEnrollService.saveReportUnitList(units);
            }
        } catch (BusinessException ex) {
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
        }
    }

    /**
     * 生成ReportMatchSourceEntity
     * 
     * @param request
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @RequestMapping(value = "/create/reportMatchSourceEntity", method = { RequestMethod.GET })
    public Map<String, Object> createReportMatchSourceEntity(HttpServletRequest request) {
        try {
            logger.info("statr excute reportMatchSourceEntity1");
            List<String> applydates = openApplyinfoCountService.queryUnitEnrollDate();
            logger.info("statr excute applydates" + JSON.toJSONString(applydates));
            List<ReportMatchSourceEntity> matchList = openApplyinfoCountService
                .queryMatchEnrollCount("");
            List<ReportMatchSourceEntity> olds = reportMatchSourceService.queryReportMatch(null,
                null);
            logger.info("matchList all" + JSON.toJSONString(matchList));
            for (String applydate : applydates) {
                List<ReportMatchSourceEntity> matchs = ReportCountTask.getMatchList(matchList,
                    applydate);
                logger.info("date matchs: " + JSON.toJSONString(matchs));
                List<ReportMatchSourceEntity> reportMatch = ReportCountTask.getOldMatch(olds,
                    applydate);
                if ((matchs == null || matchs.size() <= 0) && reportMatch != null
                    && reportMatch.size() > 0) {
                    for (ReportMatchSourceEntity entity : reportMatch) {
                        reportMatchSourceService.delReportMatch(entity.getId());
                    }
                }
                reportMatchSourceService.saveReportMatchSourceList(matchs);
            }
            return JsonResultUtil.getSuccessResult(JSON.toJSONString(matchList));
        } catch (BusinessException ex) {
            logger.error("业务错误 {}", ex);
            return JsonResultUtil.getFailResult("500", "reportMatchSourceEntity" + ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            return JsonResultUtil.getFailResult("500", "reportMatchSourceEntity" + ex.getMessage());
        }
    }

    /**
     * 生成ReportUnitFinishEntity
     * 
     * @param request
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @RequestMapping(value = "/create/reportUnitFinishEntity", method = { RequestMethod.GET })
    public void createReportUnitFinishEntity(HttpServletRequest request) {
        try {
            logger.info("statr excute reportUnitFinishEntity");
            List<String> partdates = openScoreCountService.queryUnitScoreDate();
            List<ReportUnitFinishEntity> unitRecords = openScoreCountService
                .queryUnitScoreCount("");
            for (String partdate : partdates) {
                List<ReportUnitFinishEntity> units = ReportCountTask.getUnitFinsh(unitRecords,
                    partdate);
                reportUnitFinishService.saveReportUnitFinishList(units);
            }
        } catch (BusinessException ex) {
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
        }
    }

    /**
     * 生成ReportMatchFinishEntity
     * 
     * @param request
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @RequestMapping(value = "/create/reportMatchFinishEntity", method = { RequestMethod.GET })
    public void createReportMatchFinishEntity(HttpServletRequest request) {
        try {
            logger.info("statr excute reportMatchFinishEntity");
            List<String> partdates = openScoreCountService.queryUnitScoreDate();
            List<ReportMatchFinishEntity> matchRecords = openScoreCountService
                .queryMatchScoreCount("");
            for (String partdate : partdates) {
                List<ReportMatchFinishEntity> matchs = ReportCountTask.getMatchFinsh(matchRecords,
                    partdate);
                reportMatchFinishService.saveReportMatchFinishList(matchs);
            }
        } catch (BusinessException ex) {
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "奖章购买数据分析（详情）", notes = "奖章购买数据分析（详情）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startDate", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endDate", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "pageNumber", value = "页数(默认1页)", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页数量(默认10条)", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "goods/order/detail", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> getDoodsOrderDetail(@RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate,
                                                   @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
                                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest request) {
        try {
            if (StringUtils.isBlank(startDate)) {
                startDate = DateUtil.format(DateUtil.getDayAgoTime(10), DateUtil.WEB_FORMAT);
            }
            if (StringUtils.isBlank(endDate)) {
                endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            }
            PagingResult<ReportGoodsOrder> result = reportGoodsOrderService
                .countGoodsOrderPersonByPage(startDate, endDate, pageNumber, pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dates", ReportGoodsOrderVo.getReportGoodsOrderVoList(result.getList()));
            map.put("total", result.getAllRow());
            map.put("totalPage", result.getTotalPage());
            map.put("currentPage", result.getCurrentPage());
            map.put("pageSize", result.getPageSize());
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException ex) {
            logger.error("", ex);
            return JsonResultUtil.getFailResult("500", ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            return JsonResultUtil.getFailResult("500", "请联系管理员!");
        }
    }

    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @ApiOperation(value = "奖章购买数据分析导出（表格数据）", notes = "奖章购买数据分析导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startDate", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endDate", value = "查询结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/goods/order", method = { RequestMethod.GET })
    public void exportGoodsOrder(@RequestParam(required = false) String startDate,
                                 @RequestParam(required = false) String endDate,
                                 HttpServletRequest request, HttpServletResponse response) {

        try {
            if (StringUtils.isBlank(startDate)) {
                startDate = DateUtil.format(DateUtil.getDayAgoTime(10), DateUtil.WEB_FORMAT);
            }
            if (StringUtils.isBlank(endDate)) {
                endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            }
            List<ReportGoodsOrder> orders = reportGoodsOrderService
                .countGoodsOrderPersonByExport(startDate, endDate);
            byte[] excel = null;
            List<GoodsOrderEverydayTemplate> increaseRegisters = GoodsOrderEverydayTemplate
                .increaseRegisters(orders);
            excel = new ExcelHanlel<>(GoodsOrderEverydayTemplate.class)
                .generTemplateExcel(increaseRegisters, "奖章购买数据分析", 1, goodsOrderCountTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = "奖章购买数据分析";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            //            response.setHeader("Content-Disposition",
            //                "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1")
            //                                                      + ".xlsx");
            fileName = new String(fileName.getBytes("utf-8"), "ISO_8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    /**
     * 生成ReportMatchFinishEntity
     * 
     * @param request
     * @return
     */
    @Authority(permissionCode = "MjAxODA4MzAxODM1MDc=")
    @RequestMapping(value = "/create/reportGoodsOrderEntity", method = { RequestMethod.GET })
    public void createReportGoodsOrderEntity(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        try {
            String startTime = reportGoodsOrderService.queryScoreStartTime();//赛事开始时间
            int day = DateUtil.getDateDifference(startTime,
                DateUtil.format(new Date(), DateUtil.WEB_FORMAT));
            /**获取从赛事开始到当前日期所有的时间*/
            List<String> dates = DateUtil.getAvgDateList(DateUtil.strToDate(startTime), new Date(),
                day);
            if (dates != null && dates.size() > 0) {
                List<Map<String, Object>> finishs = reportGoodsOrderService
                    .countFinishEnrollPerson();//每日完赛人数统计
                List<Map<String, Object>> medals = reportGoodsOrderService.countMedalReceive();//电子奖章每日人数统计
                List<GoodsOrderCountModel> entitys = goodsOrderFacade.queryGoodsOrderByDay(null,
                    null, "1");//实物奖章领取人数
                List<GoodsOrderCountModel> onlines = goodsOrderFacade.queryGoodsOrderByDay(null,
                    null, "0");//实物奖章在线订购数
                for (String date : dates) {
                    int finish = ReportCountTask.finishConut(date, finishs);
                    int medal = ReportCountTask.medalConut(date, medals);
                    int entity = ReportCountTask.entityConut(date, entitys);
                    int online = ReportCountTask.entityConut(date, onlines);
                    ReportGoodsOrder order = new ReportGoodsOrder();
                    order.setDate(DateUtil.strToDate(date));
                    order.setFinishCount(finish);
                    order.setMedalReceive(medal);
                    order.setEntityReceive(entity);
                    order.setOnlineOrder(online);
                    reportGoodsOrderService.saveGoodsOrder(order);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("reportGoodsOrderEntity excute end , spent time :"
                               + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
