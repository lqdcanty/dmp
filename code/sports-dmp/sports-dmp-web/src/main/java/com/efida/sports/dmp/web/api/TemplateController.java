/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.template.ApplyInfoTeplate;
import com.efida.sports.dmp.service.template.EnrollInfoTemplate;
import com.efida.sports.dmp.service.template.PlayerTemplate;
import com.efida.sports.dmp.service.template.ScoreRankTemplate;
import com.efida.sports.dmp.service.template.ScoreTemplate;
import com.efida.sports.dmp.web.vo.DmpResult;
import com.efida.sports.dmp.web.vo.TemplateResultVo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.excel.exception.ExcelRuntimeException;
import cn.evake.excel.handel.ExcelHanlel;
import cn.evake.excel.util.ExcelAnalyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 模板导入接口
 * 
 * @author wang yi
 * @desc
 * @version $Id: TemplateController.java, v 0.1 2018年7月26日 下午6:11:20 wang yi Exp
 *          $
 */
@RestController()
@RequestMapping(value = "dmp/api/template", produces = "application/json")
@Api(value = "模板导入接口", tags = "数据中心-模板导入管理")
@Authority
public class TemplateController {

    private Logger                 logger           = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService            userService;

    @Autowired
    private OpenPlayerApplyService playerService;

    @Autowired
    private SpMatchFacadeClient    spMatchService;

    private final String           enrollSheet      = "open_enroll_info";

    private final String           importAllPerCode = "dmp-import-all";

    private int                    startRow         = 3;

    /**
     * 个人报名信息
     * 
     * @param request
     * @param file
     * @return
     */
    @Authority(permissionCode = "MjAxODA3MjgxNjQ4NDU=")
    @ApiOperation(value = "个人报名模板上传", notes = "上传文件到个人报名模板解析入库", httpMethod = "POST")
    @PostMapping("enroll/import")
    public DmpResult<TemplateResultVo> upload(HttpServletRequest request,
                                              @RequestParam("file") MultipartFile file) {
        DmpResult<TemplateResultVo> result = new DmpResult<TemplateResultVo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            String unitCode = userModel.getUserName();
            List<EnrollInfoTemplate> enroList = new ExcelHanlel<>(EnrollInfoTemplate.class)
                .getExcelToList(enrollSheet, startRow, file.getBytes());
            // 解析拓展数据
            List<Map<String, Object>> enroListTemp = new ExcelAnalyUtils(file.getBytes())
                .analyExcelTemplate(enrollSheet, "ext_");
            ArrayList<EnrollInfoTemplate> arrayList = new ArrayList<EnrollInfoTemplate>();
            for (EnrollInfoTemplate enrollInfoTemplate : enroList) {
                if (StringUtils.isBlank(enrollInfoTemplate.getIdempotentId())) {
                    continue;
                }
                for (Map<String, Object> map : enroListTemp) {
                    if (enrollInfoTemplate.getIdempotentId().equals(map.get("idempotentId"))) {
                        enrollInfoTemplate.setExtProp(
                            map.get("extProp") == null ? null : map.get("extProp").toString());
                    }
                }
                arrayList.add(enrollInfoTemplate);
            }
            //处理脏数
            enroList = arrayList;
            System.err.println(JSON.toJSONString(enroList));
            System.err.println(JSON.toJSONString(enroListTemp));
            // 断言报名信息
            List<String> applyErr = playerService.assertEnrollInfos(enroList);
            if (CollectionUtils.isNotEmpty(applyErr)) {
                throw new DmpBusException(JSON.toJSONString(applyErr));
            }
            if (CollectionUtils.isEmpty(enroList)) {
                throw new DmpBusException("未解析到运动员报名数据");
            }
            //如果拥有导入所有权限
            if (userService.checkUserLimit(request, importAllPerCode)) {
                EnrollInfoTemplate enrollInfoTemplate = enroList.get(0);
                String matchCode = enrollInfoTemplate.getMatchCode();
                SpMatchModel matchDetail = spMatchService.getEnableSpMatch(matchCode);
                if (matchDetail == null) {
                    logger.error("导入赛事获取赛事信息为空....赛事编号:{}", matchCode);
                } else {
                    unitCode = matchDetail.getUnitCode();
                }
                if (unitCode == null) {
                    throw new DmpBusException("赛事编号:" + matchCode + "暂时未关联承办方,请将赛事关联承办方");
                }
            }
            logger.info("开始导入承办方:{} 数据:{}", unitCode, JSON.toJSONString(enroList));
            playerService.submitEnrollInfoExcelVos(unitCode, enroList);
        } catch (ExcelRuntimeException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (DmpBusException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (Exception e) {
            result.setErrorMsg("系统异常");
            logger.error("", e);
        }
        return result;
    }

    /**
     * 团队报名
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @param file
     * @return
     */
    @Authority(permissionCode = "MjAxODA3MjgxNjQ4NDU=")
    @ApiOperation(value = "团队报名模板上传", notes = "上传文件到团队报名解析入库", httpMethod = "POST")
    @PostMapping("/td/enroll/import")
    public DmpResult<TemplateResultVo> uploaTdEnroll(HttpServletRequest request,
                                                     @RequestParam("file") MultipartFile file) {
        DmpResult<TemplateResultVo> result = new DmpResult<TemplateResultVo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            List<ApplyInfoTeplate> applyVos = new ExcelHanlel<>(ApplyInfoTeplate.class)
                .getExcelToList("报名信息", startRow, file.getBytes());
            List<PlayerTemplate> playerVos = new ExcelHanlel<>(PlayerTemplate.class)
                .getExcelToList("参赛运动员信息", startRow, file.getBytes());
            if (CollectionUtils.isEmpty(applyVos)) {
                throw new DmpBusException("未解析到报名数据");
            }
            if (CollectionUtils.isEmpty(playerVos)) {
                throw new DmpBusException("未解析到运动员信息数据");
            }
            // 解析拓展数据
            List<Map<String, Object>> enroListTemp = new ExcelAnalyUtils(file.getBytes())
                .analyExcelTemplate("参赛运动员信息", "ext_");
            for (PlayerTemplate playerVo : playerVos) {
                for (Map<String, Object> map : enroListTemp) {
                    if (playerVo.getRefIdempotentId().equals(map.get("refIdempotentId"))) {
                        playerVo.setExtProp(
                            map.get("extProp") == null ? null : map.get("extProp").toString());
                    }
                }
            }
            System.err.println(JSON.toJSONString(applyVos));
            System.err.println(JSON.toJSONString(playerVos));
            // 断言报名信息
            List<String> applyErr = playerService.assertApplayInfos(applyVos);
            // 断言参赛运动员信息
            List<String> playerErr = playerService.assertPlayerInfos(playerVos);
            if (CollectionUtils.isNotEmpty(applyErr)) {
                throw new DmpBusException(JSON.toJSONString(applyErr));
            }
            if (CollectionUtils.isNotEmpty(playerErr)) {
                throw new DmpBusException(JSON.toJSONString(playerErr));
            }
            //如果拥有导入所有权限
            String unitCode = userModel.getUserName();
            if (userService.checkUserLimit(request, importAllPerCode)) {
                ApplyInfoTeplate applyInfoTeplate = applyVos.get(0);
                String matchCode = applyInfoTeplate.getMatchCode();
                SpMatchModel matchDetail = spMatchService.getEnableSpMatch(matchCode);
                if (matchDetail == null) {
                    logger.error("导入赛事获取赛事信息为空....赛事编号:{}", matchCode);
                } else {
                    unitCode = matchDetail.getUnitCode();
                }
                if (unitCode == null) {
                    throw new DmpBusException("赛事编号:" + matchCode + "暂时未关联承办方,请将赛事关联承办方");
                }
            }
            playerService.submitTdApplayExcelVos(unitCode, applyVos, playerVos);
        } catch (ExcelRuntimeException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (DmpBusException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (Exception e) {
            result.setErrorMsg("导入失败,解析异常");
            logger.error("", e);
        }
        return result;
    }

    /**
     * 成绩信息
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @param file
     * @return
     */
    @Authority(permissionCode = "dmp-import-score")
    @ApiOperation(value = "成绩模板上传", notes = "上传文件到成绩解析入库", httpMethod = "POST")
    @PostMapping("/score/import")
    public DmpResult<TemplateResultVo> uploaScore(HttpServletRequest request,
                                                  @RequestParam("file") MultipartFile file) {
        DmpResult<TemplateResultVo> result = new DmpResult<TemplateResultVo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            ExcelAnalyUtils excelHandel = new ExcelAnalyUtils(file.getBytes());
            List<Map<String, Object>> excelTemplate = excelHandel.analyExcelTemplate("open_score",
                "score_prop_");
            List<ScoreTemplate> scores = JSON.parseArray(JSON.toJSONString(excelTemplate),
                ScoreTemplate.class);
            if (CollectionUtils.isEmpty(scores)) {
                throw new DmpBusException("未解析到成绩数据");
            }
            System.err.println(("Score json str " + JSON.toJSONString(excelTemplate)));
            System.err.println(("Score json str " + JSON.toJSONString(scores)));
            //如果拥有导入所有权限
            String unitCode = userModel.getUserName();
            if (userService.checkUserLimit(request, importAllPerCode)) {
                ScoreTemplate scoreTemplate = scores.get(0);
                String matchCode = scoreTemplate.getMatchCode();
                SpMatchModel matchDetail = spMatchService.getEnableSpMatch(matchCode);
                if (matchDetail == null) {
                    logger.error("导入赛事获取赛事信息为空....赛事编号:{}", matchCode);
                } else {
                    unitCode = matchDetail.getUnitCode();
                }
                if (unitCode == null) {
                    throw new DmpBusException("赛事编号:" + matchCode + "暂时未关联承办方,请将赛事关联承办方");
                }
            }
            // 断言报名信息
            List<String> errMsgs = playerService.assertScoreTemplates(unitCode, scores);
            if (CollectionUtils.isNotEmpty(errMsgs)) {
                throw new DmpBusException(JSON.toJSONString(errMsgs));
            }
            logger.info("开始导入承办方:{} 数据:{}", unitCode, JSON.toJSONString(scores));
            playerService.submitTdScores(unitCode, scores);
        } catch (ExcelRuntimeException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (DmpBusException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (Exception e) {
            result.setErrorMsg("系统异常");
            logger.error("", e);
        }
        return result;
    }

    @Authority(permissionCode = "dmp-import-score-rank")
    @ApiOperation(value = "成绩排名模板上传", notes = "上传文件到成绩排名解析入库", httpMethod = "POST")
    @PostMapping("/score/rank/import")
    public DmpResult<TemplateResultVo> uploaScoreRank(HttpServletRequest request,
                                                      @RequestParam("file") MultipartFile file) {
        DmpResult<TemplateResultVo> result = new DmpResult<TemplateResultVo>();
        try {
            UserInfoModel userModel = userService.getUserModelChecked(request);
            ExcelAnalyUtils excelHandel = new ExcelAnalyUtils(file.getBytes());
            List<Map<String, Object>> excelTemplate = excelHandel
                .analyExcelTemplate("open_score_rank", "prop_");
            List<ScoreRankTemplate> scores = JSON.parseArray(JSON.toJSONString(excelTemplate),
                ScoreRankTemplate.class);
            System.err.println(JSON.toJSONString(scores));
            if (CollectionUtils.isEmpty(scores)) {
                throw new DmpBusException("未解析到成绩数据");
            }
            //如果拥有导入所有权限
            String unitCode = userModel.getUserName();
            if (userService.checkUserLimit(request, importAllPerCode)) {
                ScoreRankTemplate scoreRankTemplate = scores.get(0);
                String matchCode = scoreRankTemplate.getMatchCode();
                SpMatchModel matchDetail = spMatchService.getEnableSpMatch(matchCode);
                if (matchDetail == null) {
                    logger.error("导入赛事获取赛事信息为空....赛事编号:{}", matchCode);
                } else {
                    unitCode = matchDetail.getUnitCode();
                }
                if (unitCode == null) {
                    throw new DmpBusException("赛事编号:" + matchCode + "暂时未关联承办方,请将赛事关联承办方");
                }
            }
            // 断言报名信息
            List<String> errMsgs = playerService.assertScoreRankTemplates(unitCode, scores);
            if (CollectionUtils.isNotEmpty(errMsgs)) {
                throw new DmpBusException(JSON.toJSONString(errMsgs));
            }
            logger.info("开始导入承办方:{} 数据:{}", unitCode, JSON.toJSONString(scores));
            playerService.submitTdScoreRanks(unitCode, scores);
        } catch (ExcelRuntimeException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (DmpBusException e) {
            result.setErrorMsg(e.getMessage());
            logger.error("", e);
        } catch (Exception e) {
            result.setErrorMsg("系统异常");
            logger.error("", e);
        }
        return result;
    }

}
