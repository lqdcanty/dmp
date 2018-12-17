/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.AccessAnalysisModel;
import com.efida.sport.facade.model.ChannelTrendAnalysisModel;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sport.facade.model.RegisterStatisticsModel;
import com.efida.sport.facade.model.RegisterTrendAnalysisModel;
import com.efida.sport.facade.model.TerminaStatisticsModel;
import com.efida.sports.dmp.dao.entity.ApplyAreaStatistics;
import com.efida.sports.dmp.dao.entity.ApplyStatistics;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.PlayerStatisticalAnalysisModel;
import com.efida.sports.dmp.service.dubbo.intergration.RegisterAnalysisFacadeClient;
import com.efida.sports.dmp.service.player.ApplyAreaStatisticsService;
import com.efida.sports.dmp.service.player.ApplyStatisticsService;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.player.OpenPlayerCleanService;
import com.efida.sports.dmp.service.template.ChannelRegisterTemplate;
import com.efida.sports.dmp.service.template.RegisterIncreaseTemplate;
import com.efida.sports.dmp.service.unit.UnitService;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.web.vo.ApplyAreaVo;
import com.efida.sports.dmp.web.vo.RegisterStatisticsVO;
import com.efida.sports.dmp.web.vo.UnitVo;

import cn.evake.auth.web.vo.ResultWrapper;
import cn.evake.excel.handel.ExcelHanlel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author zoutao
 * @version $Id: RegisterAnalysisController.java, v 0.1 2018年8月30日 下午4:16:00 zoutao Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/register/analysis", produces = "application/json")
@Api(value = "用户分析相关接口", tags = "数据中心-用户分析")
public class RegisterAnalysisController {

    @Autowired
    private RegisterAnalysisFacadeClient registerAnalysisClient;
    @Autowired
    private UnitService                  openUnitService;
    @Autowired
    private ApplyStatisticsService       applyStatisticsService;
    @Autowired
    private OpenPlayerCleanService       openPlayerCleanService;

    @Autowired
    private ApplyAreaStatisticsService   applyAreaStatisticsService;

    @Autowired
    private OpenPlayerApplyService       openPlayerApplyService;

    private Logger                       logger      = LoggerFactory.getLogger(this.getClass());
    /**
     * 渠道用户模板
     */
    private byte[]                       channelRegisterTemplate;
    /**
     * 用户趋势模板
     */
    private byte[]                       registerIncreaseTemplate;

    private int                          maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        channelRegisterTemplate = IOUtils
            .toByteArray(PlayerApiController.class.getResourceAsStream("/excle/项目方用户-模板.xlsx"));
        registerIncreaseTemplate = IOUtils
            .toByteArray(PlayerApiController.class.getResourceAsStream("/excle/新增用户-模板.xlsx"));
    }

    /**
     * 获取用户统计数据
     * @return
     */

    @ApiOperation(value = "获取用户统计数据", notes = "获取用户统计数据信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/statistics", method = { RequestMethod.GET })
    public ResultWrapper<RegisterStatisticsVO> getRegisterStatistics() {
        ResultWrapper<RegisterStatisticsVO> resultWrapper = new ResultWrapper<RegisterStatisticsVO>();
        try {
            RegisterStatisticsModel model = registerAnalysisClient.getRegisterStatistics();
            RegisterStatisticsVO registerStatistics = RegisterStatisticsVO.getVO(model);
            resultWrapper.setData(registerStatistics);
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
     * 获取用户增长数据
     * 
     * @return
     */

    @ApiOperation(value = "获取用户增长数据（图表数据）", notes = "获取用户增长数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/increase", method = { RequestMethod.GET })
    public ResultWrapper<Map<String, Object>> getRegisterGrowthTrend(@RequestParam(required = false) String startTime,
                                                                     @RequestParam(required = false) String endTime,
                                                                     HttpServletRequest request) {

        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<RegisterTrendAnalysisModel> registerGrowthTrend = registerAnalysisClient
                .getRegisterGrowthTrend(parStr, parEnd);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", registerGrowthTrend);
            map.put("date", getIncreaseAvgDateList(registerGrowthTrend));
            resultWrapper.setData(map);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    private List<String> getIncreaseAvgDateList(List<RegisterTrendAnalysisModel> registerGrowthTrend) {

        Date minDate = null;
        Date maxDate = null;
        if (registerGrowthTrend != null && registerGrowthTrend.size() < 1) {
            for (RegisterTrendAnalysisModel registerTrendAnalysisModel : registerGrowthTrend) {
                Date reportDay = registerTrendAnalysisModel.getDate();
                if (minDate == null) {
                    minDate = reportDay;
                } else {
                    minDate = minDate.after(reportDay) ? reportDay : minDate;
                }
                if (maxDate == null) {
                    maxDate = reportDay;
                } else {
                    maxDate = maxDate.before(reportDay) ? reportDay : maxDate;
                }
            }
        }
        return DateUtil.getAvgDateList(minDate, maxDate, 6);
    }

    /**
    * 分页获取用户增长数据
    * 
    * @param currentPage
    * @param PageSize
    * @param startTime
    * @param endTime
    * @return
    */

    @ApiOperation(value = "获取用户增长明细数据（表格数据）", notes = "获取用户增长明细数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "currentPage", value = "当前页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/page/increase", method = { RequestMethod.GET })
    public ResultWrapper<PagingResult<RegisterTrendAnalysisModel>> getRegisterGrowthTrend(@RequestParam(required = false) String startTime,
                                                                                          @RequestParam(required = false) String endTime,
                                                                                          @RequestParam(required = false, defaultValue = "1") int currentPage,
                                                                                          @RequestParam(required = false, defaultValue = "5") int pageSize,
                                                                                          HttpServletRequest request) {

        ResultWrapper<PagingResult<RegisterTrendAnalysisModel>> resultWrapper = new ResultWrapper<PagingResult<RegisterTrendAnalysisModel>>();
        try {
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            PagingResult<RegisterTrendAnalysisModel> page = registerAnalysisClient
                .getRegisterGrowthTrend(currentPage, pageSize, parStr, parEnd);
            List<RegisterTrendAnalysisModel> list = page.getList();
            PagingResult<RegisterTrendAnalysisModel> pageResult = new PagingResult<RegisterTrendAnalysisModel>(
                list, page.getAllRow(), currentPage, pageSize);
            resultWrapper.setData(pageResult);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @ApiOperation(value = "获取用户增长明细数据导出（表格数据）", notes = "获取用户增长明细数据导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/increase", method = { RequestMethod.GET })
    public void exportRegisterIncrease(@RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime,
                                       HttpServletRequest request, HttpServletResponse response) {

        try {
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<RegisterTrendAnalysisModel> list = registerAnalysisClient
                .getRegisterGrowthTrend(parStr, parEnd);
            byte[] excel = null;
            List<RegisterIncreaseTemplate> increaseRegisters = RegisterIncreaseTemplate
                .increaseRegisters(list);
            excel = new ExcelHanlel<>(RegisterIncreaseTemplate.class)
                .generTemplateExcel(increaseRegisters, "增长趋势明细", 1, registerIncreaseTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = startTime + "-" + endTime + "新增用户统计表";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition",
                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
                                                      + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
        }

    }

    /**
     * 按渠道分组 ，统计总用户数，按总用户数倒序查询渠道编号
    
     * 
     * @param startTime
     * @param endTime
     * @param topNum
     * @return
     */
    @ApiOperation(value = "获取渠道数据", notes = "获取渠道数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/channel", method = { RequestMethod.GET })
    ResultWrapper<List<UnitVo>> getChannel(String keyword) {
        ResultWrapper<List<UnitVo>> resultWrapper = new ResultWrapper<List<UnitVo>>();
        try {
            cn.evake.auth.usermodel.PagingResult<OpenUnitEntity> page = openUnitService
                .getUnitAccounts(keyword, 1, 100);
            List<String> unitCodes = registerAnalysisClient.getTopNumChannel(null, null, 5);
            List<UnitVo> vos = UnitVo.coverVos(page.getList(), unitCodes);
            Collections.sort(vos, new Comparator<UnitVo>() {
                @Override
                public int compare(UnitVo o1, UnitVo o2) {
                    boolean top1 = o1.isTop();
                    boolean top2 = o2.isTop();
                    if (top1 ^ top2) {
                        return top2 ? 1 : -1;
                    } else {
                        return 0;
                    }
                }
            });
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
     * 
     * 渠道用户分析 一段时间段类 各个渠道的新增用户数，总用户数，新增占比数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    //TODO  这个地方需要分页
    @ApiOperation(value = "获取渠道用户增长明细数据（表格数据）", notes = "获取渠道用户增长明细数据")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCodes", value = "渠道编号（多个用,隔开）", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "currentPage", value = "当前页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, dataType = "int", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/channel/register", method = { RequestMethod.GET })
    ResultWrapper<Map<String, Object>> channelRegitserAnalysis(@RequestParam(required = false) String startTime,
                                                               @RequestParam(required = false) String endTime,
                                                               @RequestParam(required = false) String channelCodes,
                                                               @RequestParam(required = false, defaultValue = "1") int currentPage,
                                                               @RequestParam(required = false, defaultValue = "5") int pageSize,
                                                               HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            List<String> codes = null;
            if (StringUtils.isNotBlank(channelCodes)) {
                codes = new ArrayList<String>();
                if (channelCodes.indexOf(",") > 0) {
                    String[] split = channelCodes.split(",");
                    if (split != null && split.length > 0) {
                        for (String code : split) {
                            codes.add(code);
                        }
                    }
                } else {
                    codes.add(channelCodes);
                }
            }
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<RegisterTrendAnalysisModel> channelRegitser = registerAnalysisClient
                .channelRegitserAnalysis(parStr, parEnd, codes);
            int startIndex = (currentPage - 1) * pageSize;
            int toIndex = currentPage * pageSize;

            List<RegisterTrendAnalysisModel> subList = new ArrayList<RegisterTrendAnalysisModel>();
            if (channelRegitser.size() <= toIndex) {
                toIndex = channelRegitser.size();
            }
            subList = channelRegitser.subList(startIndex, toIndex);
            PagingResult<RegisterTrendAnalysisModel> pagingResult = new PagingResult<RegisterTrendAnalysisModel>(
                subList, channelRegitser.size(), currentPage, pageSize);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("list", subList);
            map.put("allRow", pagingResult.getAllRow());
            map.put("currentPage", pagingResult.getCurrentPage());
            map.put("pageSize", pagingResult.getPageSize());
            map.put("totalPage", pagingResult.getTotalPage());
            resultWrapper.setData(map);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;

    }

    @ApiOperation(value = "获取渠道用户增长明细数据导出", notes = "获取渠道用户增长明细数据导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCodes", value = "渠道编号（多个用,隔开）", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "export/channel/register", method = { RequestMethod.GET })
    public void exportChannelRegister(@RequestParam(required = false) String startTime,
                                      @RequestParam(required = false) String endTime,
                                      @RequestParam(required = false) String channelCodes,
                                      HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> codes = null;
            if (StringUtils.isNotBlank(channelCodes)) {
                codes = new ArrayList<String>();
                if (channelCodes.indexOf(",") > 0) {
                    String[] split = channelCodes.split(",");
                    if (split != null && split.length > 0) {
                        for (String code : split) {
                            codes.add(code);
                        }
                    }
                } else {
                    codes.add(channelCodes);
                }
            }
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<RegisterTrendAnalysisModel> channelRegitser = registerAnalysisClient
                .channelRegitserAnalysis(parStr, parEnd, codes);

            byte[] excel = null;
            List<ChannelRegisterTemplate> channelRegisters = ChannelRegisterTemplate
                .channelRegisters(channelRegitser);

            excel = new ExcelHanlel<>(ChannelRegisterTemplate.class)
                .generTemplateExcel(channelRegisters, "项目方用户", 1, channelRegisterTemplate);

            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = startTime + "-" + endTime + "项目方用户统计表";

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition",
                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")
                                                      + ".xlsx");
            IOUtils.write(excel, outputStream);
        } catch (IOException e) {
            logger.error("文件下载失败!", e);
        }

    }

    /**
     * 
     *  渠道用户趋势分析 一段时间段了各个渠道 用户新增数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    @ApiOperation(value = "获取渠道用户增长趋势（图标数据）", notes = "获取渠道用户增长趋势（图标数据）")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "channelCodes", value = "渠道编号（多个用,隔开）", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/channel/register/increase", method = { RequestMethod.GET })
    ResultWrapper<Map<String, Object>> channelTrendAnalysis(@RequestParam(required = false) String startTime,
                                                            @RequestParam(required = false) String endTime,
                                                            @RequestParam(required = false) String channelCodes,
                                                            HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            List<String> codes = null;
            if (StringUtils.isNotBlank(channelCodes)) {
                codes = new ArrayList<String>();
                if (channelCodes.indexOf(",") > 0) {
                    String[] split = channelCodes.split(",");
                    if (split != null && split.length > 0) {
                        for (String code : split) {
                            codes.add(code);
                        }
                    }
                } else {
                    codes.add(channelCodes);
                }
            }
            HashMap<String, Object> map = new HashMap<String, Object>();
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<ChannelTrendAnalysisModel> channelTrendAnalysis = registerAnalysisClient
                .channelTrendAnalysis(parStr, parEnd, codes);
            map.put("list", channelTrendAnalysis);
            map.put("dates", getChannelIncreaseAvgDateList(channelTrendAnalysis));
            resultWrapper.setData(map);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    private List<String> getChannelIncreaseAvgDateList(List<ChannelTrendAnalysisModel> channelTrendAnalysis) {
        Date minDate = null;
        Date maxDate = null;
        if (channelTrendAnalysis != null && channelTrendAnalysis.size() > 0) {
            for (ChannelTrendAnalysisModel registerTrendAnalysisModel : channelTrendAnalysis) {
                Date reportDay = registerTrendAnalysisModel.getDate();
                if (minDate == null) {
                    minDate = reportDay;
                } else {
                    minDate = minDate.after(reportDay) ? reportDay : minDate;
                }
                if (maxDate == null) {
                    maxDate = reportDay;
                } else {
                    maxDate = maxDate.before(reportDay) ? reportDay : maxDate;
                }
            }
        }
        return DateUtil.getAvgDateList(minDate, maxDate, 6);
    }

    /*************************用户分析新功能*******************************/
    /**
     * 
     *  渠道用户趋势分析 一段时间段了各个渠道 用户新增数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    @ApiOperation(value = "获取全国总报名人次", notes = "获取全国总报名人次")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "apply/total", method = { RequestMethod.GET })
    ResultWrapper<Long> channelTrendAnalysis(HttpServletRequest request) {
        ResultWrapper<Long> resultWrapper = new ResultWrapper<Long>();
        try {
            long total = openPlayerApplyService.getApplyTotal();
            resultWrapper.setData(total);
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
     * 
     *  统计报名人数区域分布数据
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    @ApiOperation(value = "统计报名人数区域分布数据", notes = "统计报名人数区域分布数据(地图)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })

    @RequestMapping(value = "/area/distribution", method = { RequestMethod.GET })
    ResultWrapper<Map<String, Object>> areaDistribution(HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            List<ApplyAreaStatistics> applyAreaStatistics = applyAreaStatisticsService
                .selectApplyAreaStatistics();
            long online = 0;
            List<ApplyAreaVo> underLine = new ArrayList<ApplyAreaVo>();
            Map<String, ApplyAreaVo> amap = new HashMap<String, ApplyAreaVo>();
            if (applyAreaStatistics != null && applyAreaStatistics.size() > 0) {
                for (ApplyAreaStatistics item : applyAreaStatistics) {
                    BigDecimal latitude = item.getLatitude();
                    BigDecimal longitude = item.getLongitude();
                    ApplyAreaVo old = amap.get(item.getCity());
                    if (latitude != null && longitude != null) {
                        if (old == null) {
                            ApplyAreaVo vo = new ApplyAreaVo();
                            vo.setApplyCount(item.getApplyCount());
                            vo.setArea(item.getArea());
                            vo.setCity(item.getCity());
                            //赛场暂时用城市代替。
                            vo.setFileName(item.getCity());
                            vo.setLat(latitude.toString());
                            vo.setLon(longitude.toString());
                            vo.setProvince(item.getProvince());
                            vo.setLevel(3);
                            underLine.add(vo);
                            amap.put(item.getCity(), vo);
                        } else {
                            old.setApplyCount(old.getApplyCount() + item.getApplyCount());
                        }
                    } else {
                        online += item.getApplyCount();
                    }
                }
            }
            //报名数多的排前面
            CollectionUtils.sort(underLine);
            //设置level。
            int total = underLine.size();
            double pageSize = total*1.0/100;
            
            //等级按 10%,15%, 20%, 25%, 30% 比例分布;
            int start = 0;
            int length = (int) (pageSize*10);
            setLevel(underLine, 0, length, 1);
            start = start+length;
            length =  (int) (pageSize*15);
            setLevel(underLine, start, length, 2);
            start = start+length;
            length =  (int) (pageSize*20);            
            setLevel(underLine, start, length, 3);
            start = start+length;
            length =  (int) (pageSize*25);  
            setLevel(underLine, start, length, 4);
            start = start+length;
            length =  total-start; 
            setLevel(underLine, start, length, 5);
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("underLine", underLine);
            map.put("online", online);
            resultWrapper.setData(map);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("", ex);
        } catch (Exception ex) {
            logger.error("", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    
    private void setLevel(List<ApplyAreaVo> items, int startIndex, int length, int level) {
        
        if(items==null||startIndex>=items.size()){
            return;
        }
        
        for(int i=startIndex; i<(startIndex+length)&& i< items.size(); i++){
            items.get(i).setLevel(level);
        }
    }

    /**
     * 
     *  报名群体分析
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    @ApiOperation(value = "报名分析", notes = "报名次数分析(赛事报名分析图表)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })

    @RequestMapping(value = "/apply/analysis", method = { RequestMethod.GET })
    ResultWrapper<List<ApplyStatistics>> applyAnalysis(HttpServletRequest request) {
        ResultWrapper<List<ApplyStatistics>> resultWrapper = new ResultWrapper<List<ApplyStatistics>>();
        try {
            List<ApplyStatistics> applyStatistics = applyStatisticsService.getApplyStatistics();
            resultWrapper.setData(applyStatistics);
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
     * 
     *  报名群体分析
     * 
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */

    @ApiOperation(value = "平台访问量分析", notes = "平台访问量分析")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/access/analysis", method = { RequestMethod.GET })
    public ResultWrapper<Map<String, Object>> accessAnalysis(@RequestParam(required = false) String startTime,
                                                             @RequestParam(required = false) String endTime,
                                                             HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            Date parStr = DateUtil.parseStr(startTime);
            Date parEnd = DateUtil.parseStr(endTime);
            List<AccessAnalysisModel> pcAccessAnalysis = registerAnalysisClient
                .getPCAccessAnalysis(parStr, parEnd);
            Map<String, Object> map = new HashMap<String, Object>();
            //昨天数据
            Date dayAgoTime = DateUtil.getDayAgoTime(1);
            List<TerminaStatisticsModel> termina = registerAnalysisClient
                .getTerMinaStatisticsAnalysis(dayAgoTime);
            map.put("access", pcAccessAnalysis);
            map.put("termina", termina);
            resultWrapper.setData(map);
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
     * 
     *  报名群体分析
     * @param startTime
     * @param endTime
     * @param channelCodes
     * @return
     */
    @ApiOperation(value = "报名群体分析", notes = "报名群体分析")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })

    @RequestMapping(value = "/group", method = { RequestMethod.GET })
    ResultWrapper<Map<String, Object>> grouAnalytic(HttpServletRequest request) {
        ResultWrapper<Map<String, Object>> resultWrapper = new ResultWrapper<Map<String, Object>>();
        try {
            PlayerStatisticalAnalysisModel model = new PlayerStatisticalAnalysisModel();
            PlayerStatisticalAnalysisModel sexStatistics = openPlayerCleanService
                .getSexStatistics();
            if (sexStatistics != null) {
                model.setMaleTotal(sexStatistics.getMaleTotal());
                model.setFemaleTotal(sexStatistics.getFemaleTotal());
            }
            PlayerStatisticalAnalysisModel terminalStatistics = openPlayerCleanService
                .getTerminalStatistics();
            if (terminalStatistics != null) {
                model.setAndroidTotal(terminalStatistics.getAndroidTotal());
                model.setDmpTotal(terminalStatistics.getDmpTotal());
                model.setIosTotal(terminalStatistics.getIosTotal());
                model.setPcTotal(terminalStatistics.getPcTotal());
                model.setWeichatTotal(terminalStatistics.getWeichatTotal());
            }
            PlayerStatisticalAnalysisModel adultStatistics = openPlayerCleanService
                .getAdultStatistics();
            if (adultStatistics != null) {
                model.setAdultTotal(adultStatistics.getAdultTotal());
                model.setUnAdultTotal(adultStatistics.getUnAdultTotal());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("group", model);
            resultWrapper.setData(map);
        } catch (BusinessException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 {}", ex);
        } catch (Exception ex) {
            logger.error("系统错误 {}", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
