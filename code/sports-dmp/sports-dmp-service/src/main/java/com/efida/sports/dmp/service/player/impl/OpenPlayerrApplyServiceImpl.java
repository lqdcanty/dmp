/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.player.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sports.dmp.biz.open.enms.ApplyStatusEnum;
import com.efida.sports.dmp.biz.open.impl.OpenEnrollServiceImpl;
import com.efida.sports.dmp.biz.open.impl.OpenEnrollxServiceImpl;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenEnrollInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.entity.PlayerModel;
import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenScoreRankEntityMapper;
import com.efida.sports.dmp.dao.mapper.PlayerModelMapper;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.player.OpenPlayerApplyService;
import com.efida.sports.dmp.service.template.ApplyInfoTeplate;
import com.efida.sports.dmp.service.template.EnrollInfoTemplate;
import com.efida.sports.dmp.service.template.PlayerTemplate;
import com.efida.sports.dmp.service.template.ScoreRankTemplate;
import com.efida.sports.dmp.service.template.ScoreTemplate;
import com.efida.sports.dmp.service.user.UserUnitService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.DateUtil;
import cn.evake.auth.util.underline.Underline2Camel;

/**
 * 报名/成绩后台服务
 * 
 * @author wang yi
 * @desc
 * @version $Id: OpenPlayerrApplyServiceImpl.java, v 0.1 2018年6月21日 下午8:03:03
 *          wang yi Exp $
 */
@Service
public class OpenPlayerrApplyServiceImpl implements OpenPlayerApplyService {

    @Autowired
    private PlayerModelMapper         playerMapper;

    @Autowired
    private OpenEnrollServiceImpl     openEnrollService;

    @Autowired
    private OpenEnrollxServiceImpl    openEnrollxService;

    @Autowired
    private OpenApplyInfoEntityMapper openApplyInfoEntityMapper;

    @Autowired
    private OpenPlayerEntityMapper    openPlayerEntityMapper;

    @Autowired
    private OpenScoreEntityMapper     openScoreEntityMapper;

    @Autowired
    private OpenScoreRankEntityMapper openScoreRankEntityMapper;

    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;

    @Autowired
    private UserUnitService           userUnitService;

    private Logger                    logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PlayerModel selectByPlayerCodeAndUnitCode(String playerCode, String unitCode) {
        return null;
    }

    @Override
    public List<EnrollInfoTemplate> parseEnrollInfoExcelVos(List<Map<String, Object>> templateJson) {
        List<EnrollInfoTemplate> resultVos = new ArrayList<EnrollInfoTemplate>();
        // 转换新表头
        Map<String, Object> newTitle = parseCamelTitle(templateJson.get(0));
        // 删除表头
        templateJson.remove(0);
        for (Map<String, Object> map : templateJson) {
            // 将原始表头映射成数据模型字段
            Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
            for (Entry<String, Object> titleMap : newTitle.entrySet()) {
                for (Entry<String, Object> enrollInfoExcelVo : map.entrySet()) {
                    if (titleMap.getKey().equals(enrollInfoExcelVo.getKey())) {
                        newEnrollInfoMap.put(titleMap.getValue().toString(),
                            enrollInfoExcelVo.getValue());
                    }
                }
            }
            EnrollInfoTemplate enrollVo = parseToModel(newEnrollInfoMap);
            resultVos.add(enrollVo);
        }
        return resultVos;
    }

    @Override
    public Map<String, Object> parseCamelTitle(Map<String, Object> map) {
        // 转换表头
        Map<String, Object> newTitle = new HashMap<String, Object>();
        for (Entry<String, Object> titleVo : map.entrySet()) {
            newTitle.put(titleVo.getKey(),
                Underline2Camel.underline2Camel(titleVo.getValue().toString(), true));
        }
        return newTitle;
    }

    private EnrollInfoTemplate parseToModel(Map<String, Object> map) {
        EnrollInfoTemplate parseObject = null;
        try {
            Map<String, Object> extJson = new HashMap<String, Object>();
            // 转换拓展属性
            for (Entry<String, Object> extMap : map.entrySet()) {
                // 拓展属性标识
                if (extMap.getKey().contains("ext_")) {
                    String extKey = extMap.getKey().substring(extMap.getKey().indexOf("_") + 1,
                        extMap.getKey().length());
                    extJson.put(extKey, extMap.getValue());
                }
            }
            String jsonString = JSON.toJSONString(map);
            System.err.println(jsonString);
            EnrollInfoTemplate enrollInfoTemplate = new EnrollInfoTemplate();
            enrollInfoTemplate.setIdempotentId(map.get("idempotentId").toString());
            enrollInfoTemplate.setApplyTime(DateUtil.parseCompStr(map.get("applyTime") + ""));
            enrollInfoTemplate.setApplyCode(map.get("applyCode") + "");
            enrollInfoTemplate.setExtProp(JSON.toJSONString(extJson));
        } catch (Exception e) {
            throw new DmpBusException("数据转换错误");
        }
        return parseObject;
    }

    @Override
    public void submitEnrollInfoExcelVos(String unitCode, List<EnrollInfoTemplate> excelVos) {
        String batchNumber = "BT" + SeqWorkerUtil.generateTimeSequence();
        // 提交文件到数据库
        for (EnrollInfoTemplate enrollInfoExcelVo : excelVos) {
            if (enrollInfoExcelVo == null) {
                continue;
            }
            OpenEnrollInfoEntity old = openEnrollService.getOpenApplyInfoEntityByIdempotentId(
                unitCode, enrollInfoExcelVo.getIdempotentId());
            if (old != null) {
                // 旧数据跳过
                logger
                    .info(String.format("数据:%s 已导入过..跳过....", enrollInfoExcelVo.getIdempotentId()));
            } else {
                // 添加导入数据
                System.err
                    .println(String.format("数据:%s 导入中....", enrollInfoExcelVo.getIdempotentId()));
                addNewErollInf(unitCode, batchNumber, enrollInfoExcelVo);
            }
        }
    }

    /**
     * 添加新报名信息
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param item
     */
    @Transactional
    private void addNewErollInf(String unitCode, String batchNumber, EnrollInfoTemplate item) {
        // 幂等ID
        String idempotentId = item.getIdempotentId();
        if (StringUtils.isBlank(idempotentId)) {
            return;
        }
        if (idempotentId.length() > 64) {
            item.setIdempotentId(idempotentId.substring(0, 64));
        }
        OpenApplyInfoEntity applyInfoEntity = openEnrollxService
            .getOpenApplyInfoEntityByIdempotentId(unitCode, item.getIdempotentId());
        if (applyInfoEntity != null) {
            logger.info(" ignore this , data IdempotentId:{} is exist", idempotentId);
            return;
        }
        // OpenApply
        OpenApplyInfoEntity oa = new OpenApplyInfoEntity();
        oa.setBatchNumber(batchNumber);
        oa.setUnitCode(unitCode);
        oa.setEventType("personal");
        oa.setRegistrationNum(1);
        oa.setApplyAmount(item.getApplyAmountY() == null ? 0 : item.getApplyAmountY() * 100);
        oa.setApplyTime(item.getApplyTime());
        oa.setApplyCode(item.getApplyCode());
        oa.setApplyStatus(ApplyStatusEnum.SUCCESS.getCode());
        oa.setGameCode(item.getGameCode());
        oa.setGameName(item.getGameName());
        oa.setMatchCode(item.getMatchCode());
        oa.setMatchName(item.getMatchName());
        oa.setFieldCode(item.getSiteCode());
        oa.setFieldName(item.getSiteName());
        oa.setMatchGroupCode(item.getMatchGroupCode());
        oa.setMatchGroupName(item.getMatchGroupName());
        oa.setEventCode(item.getEventCode());
        oa.setEventName(item.getEventName());
        oa.setEventStartTime(item.getEventStartTime());
        oa.setEventEndTime(item.getEventEndTime());
        oa.setIsDelet((byte) 0);
        oa.setSource((byte) 1);
        oa.setIdempotentId(item.getIdempotentId());
        oa.setChannelCode("import");
        oa.setGmtCreate(new Date());
        oa.setGmtModified(new Date());
        // OpenPlayer
        OpenPlayerEntity op = new OpenPlayerEntity();
        op.setApplyCode(oa.getApplyCode());
        op.setPlayerCode(SeqWorkerUtil.generateTimeSequence());
        op.setPlayerPhone(item.getPlayerPhone());
        op.setPlayerName(item.getPlayerName());
        op.setSex(item.getSex());
        op.setImgUrl(item.getImgUrl());
        op.setEmail(item.getEmail());
        op.setPlayerHeight(item.getPlayerHeight());
        op.setPlayerWeight(item.getPlayerWeight());
        op.setPlayerBirth(item.getPlayerBirth());
        op.setPlayerNationality(item.getPlayerNationality());
        op.setPlayerWorkUnit(item.getPlayerWorkUnit());
        op.setPlayerAddress(item.getPlayerAddress());
        op.setPlayerCerType(item.getPlayerCerType());
        op.setPlayerCerNo(item.getPlayerCerNo());
        op.setPlayerBloodType(item.getPlayerBloodType());
        op.setPlayerNation(item.getPlayerNation());
        op.setPlayerClothSize(item.getPlayerClothSize());
        op.setPlayerEmergencyName(item.getPlayerEmergencyName());
        op.setPlayerEmergencyPhone(item.getPlayerEmergencyPhone());
        op.setImgUrl(item.getImgUrl());
        op.setAttUrl(item.getAttUrl());
        op.setPlayerCategory(item.getPlayerCategory());
        op.setOpenPlatType(item.getOpenPlatType());
        op.setOpenId(item.getOpenId());
        op.setExtProp(item.getExtProp());
        op.setIsDelete((byte) 0);
        op.setPlayerNo(null);
        // 检查是否合法
        checkIsLegal(unitCode, oa);
        if (applyInfoEntity == null) {
            this.openApplyInfoEntityMapper.insert(oa);
            this.openPlayerEntityMapper.insertSelective(op);
        }
    }

    @Override
    public List<String> assertApplayInfos(List<ApplyInfoTeplate> data) {
        List<String> resultErrMeg = new ArrayList<String>();
        // 检查数据是否符合规范
        if (CollectionUtils.isEmpty(data)) {
            resultErrMeg.add("导入数据不能为空");
            return resultErrMeg;
        }
        int i = 1;
        for (ApplyInfoTeplate at : data) {
            // 校验每行数据是否符合规范
            try {
                assertApplayInfo(at);
            } catch (Exception e) {
                String message = e.getMessage();
                resultErrMeg.add(String.format("sheet报名信息,表格第%s行 错误信息:%s", i, message));
                return resultErrMeg;
            }
            i = i + 1;
        }
        return resultErrMeg;
    }

    @Override
    public List<String> assertPlayerInfos(List<PlayerTemplate> data) {
        List<String> resultErrMeg = new ArrayList<String>();
        // 检查数据是否符合规范
        if (CollectionUtils.isEmpty(data)) {
            resultErrMeg.add("导入数据不能为空");
            return resultErrMeg;
        }
        int i = 2;
        for (PlayerTemplate player : data) {
            i = i + 1;
            // 校验每行数据是否符合规范
            try {
                assertPlayerInfo(player);
            } catch (Exception e) {
                String message = e.getMessage();
                resultErrMeg.add(String.format("sheet运动信息,表格第%s行 错误信息:%s", i, message));
                return resultErrMeg;
            }
        }
        return resultErrMeg;
    }

    private void assertPlayerInfo(PlayerTemplate at) {
        if (at == null) {
            return;
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<PlayerTemplate>> set = validator.validate(at);
        for (ConstraintViolation<PlayerTemplate> constraintViolation : set) {
            throw new BusinessException(constraintViolation.getMessage());
        }
    }

    @Override
    public List<ApplyInfoTeplate> parseApplyExcelVos(List<Map<String, Object>> templateJson) {
        List<ApplyInfoTeplate> resultVos = new ArrayList<ApplyInfoTeplate>();
        // 转换新表头
        Map<String, Object> newTitle = parseCamelTitle(templateJson.get(0));
        // 删除表头
        templateJson.remove(0);
        for (Map<String, Object> map : templateJson) {
            // 将原始表头映射成数据模型字段
            Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
            for (Entry<String, Object> titleMap : newTitle.entrySet()) {
                for (Entry<String, Object> enrollInfoExcelVo : map.entrySet()) {
                    if (titleMap.getKey().equals(enrollInfoExcelVo.getKey())) {
                        newEnrollInfoMap.put(titleMap.getValue().toString(),
                            enrollInfoExcelVo.getValue());
                    }
                }
            }
            ApplyInfoTeplate enrollVo = parseToApplyModel(newEnrollInfoMap);
            resultVos.add(enrollVo);
        }
        return resultVos;
    }

    private ApplyInfoTeplate parseToApplyModel(Map<String, Object> newEnrollInfoMap) {
        ApplyInfoTeplate parseObject = null;
        try {
            parseObject = JSON.parseObject(JSON.toJSONString(newEnrollInfoMap),
                ApplyInfoTeplate.class);
        } catch (Exception e) {
            throw new DmpBusException("数据转换错误");
        }
        return parseObject;
    }

    @Override
    public List<PlayerTemplate> parsePlayerExcelVos(List<Map<String, Object>> playerSheet) {
        List<PlayerTemplate> resultVos = new ArrayList<PlayerTemplate>();
        // 转换新表头
        Map<String, Object> newTitle = parseCamelTitle(playerSheet.get(0));
        // 删除表头
        playerSheet.remove(0);
        for (Map<String, Object> map : playerSheet) {
            // 将原始表头映射成数据模型字段
            Map<String, Object> newEnrollInfoMap = new HashMap<String, Object>();
            for (Entry<String, Object> titleMap : newTitle.entrySet()) {
                for (Entry<String, Object> enrollInfoExcelVo : map.entrySet()) {
                    if (titleMap.getKey().equals(enrollInfoExcelVo.getKey())) {
                        if (enrollInfoExcelVo.getValue() != null) {
                            newEnrollInfoMap.put(titleMap.getValue().toString(),
                                enrollInfoExcelVo.getValue());
                        }
                    }
                }
            }
            PlayerTemplate enrollVo = parseToPlayerModel(newEnrollInfoMap);
            resultVos.add(enrollVo);
        }
        return resultVos;
    }

    private PlayerTemplate parseToPlayerModel(Map<String, Object> newEnrollInfoMap) {
        PlayerTemplate parseObject = null;
        try {
            Map<String, Object> extJson = new HashMap<String, Object>();
            // 转换拓展属性
            for (Entry<String, Object> extMap : newEnrollInfoMap.entrySet()) {
                // 拓展属性标识
                if (extMap.getKey().contains("ext_")) {
                    String extKey = extMap.getKey().substring(extMap.getKey().indexOf("_") + 1,
                        extMap.getKey().length());
                    extJson.put(extKey, extMap.getValue());
                }
            }
            parseObject = JSON.parseObject(JSON.toJSONString(newEnrollInfoMap),
                PlayerTemplate.class);
            parseObject.setExtProp(JSON.toJSONString(extJson));
        } catch (Exception e) {
            throw new DmpBusException("数据转换错误");
        }
        return parseObject;
    }

    @Override
    public void submitTdApplayExcelVos(String unitCode, List<ApplyInfoTeplate> applyVos,
                                       List<PlayerTemplate> playerVos) {
        String batchNumber = "BT" + SeqWorkerUtil.generateTimeSequence();
        // 报名信息
        for (ApplyInfoTeplate applyVo : applyVos) {
            // 存数据库
            OpenApplyInfoEntity old = openEnrollxService
                .getOpenApplyInfoEntityByIdempotentId(unitCode, applyVo.getIdempotentId());
            if (old == null) {
                // 报名运动员信息
                addNewApplyInfo(unitCode, batchNumber, applyVo, playerVos);
            } else {
                logger.info("ingro this data IdempotentId:{}", applyVo.getIdempotentId());
            }
        }
    }

    @Transactional
    private void addNewApplyInfo(String unitCode, String batchNumber, ApplyInfoTeplate applyVo,
                                 List<PlayerTemplate> playerVos) {
        String applyCode = addNewApply(unitCode, batchNumber, applyVo);
        for (PlayerTemplate playerVo : playerVos) {
            if (playerVo.getRefIdempotentId().equals(applyVo.getIdempotentId())) {
                addNewPlayer(unitCode, applyCode, playerVo);
            }
        }
    }

    /**
     * 添加新运动员
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param item
     */
    private void addNewPlayer(String unitCode, String applyCode, PlayerTemplate item) {
        if (StringUtils.isBlank(applyCode)) {
            return;
        }
        OpenPlayerEntity op = new OpenPlayerEntity();
        op.setPlayerCode(SeqWorkerUtil.generateTimeSequence());
        op.setApplyCode(applyCode);
        op.setPlayerPhone(item.getPlayerPhone());
        op.setPlayerName(item.getPlayerName());
        op.setSex(item.getSex());
        op.setImgUrl(item.getImgUrl());
        op.setEmail(item.getEmail());
        op.setPlayerHeight(item.getPlayerHeight());
        op.setPlayerWeight(item.getPlayerWeight());
        op.setPlayerBirth(item.getPlayerBirth());
        op.setPlayerNationality(item.getPlayerNationality());
        op.setPlayerWorkUnit(item.getPlayerWorkUnit());
        op.setPlayerAddress(item.getPlayerAddress());
        op.setPlayerCerType(item.getPlayerCerType());
        op.setPlayerCerNo(item.getPlayerCerNo());
        op.setPlayerBloodType(item.getPlayerBloodType());
        op.setPlayerNation(item.getPlayerNation());
        op.setPlayerClothSize(item.getPlayerClothSize());
        op.setPlayerEmergencyName(item.getPlayerEmergencyName());
        op.setPlayerEmergencyPhone(item.getPlayerEmergencyPhone());
        op.setImgUrl(item.getImgUrl());
        op.setPlayerCategory(item.getPlayerCategory());
        op.setOpenPlatType(item.getOpenPlatType());
        op.setOpenId(item.getOpenId());
        op.setExtProp(item.getExtProp());
        op.setIsDelete((byte) 0);
        op.setPlayerNo(item.getPlayerNo());
        op.setGmtCreate(new Date());
        op.setGmtModified(new Date());
        this.openPlayerEntityMapper.insert(op);
    }

    /**
     * 添加新报名记录
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param item
     * @return
     */
    private String addNewApply(String unitCode, String batchNumber, ApplyInfoTeplate item) {
        if (StringUtils.isBlank(item.getIdempotentId())) {
            return "";
        }
        OpenApplyInfoEntity op = new OpenApplyInfoEntity();
        op.setUnitCode(unitCode);
        String applyCode = SeqWorkerUtil.generateTimeSequence();
        op.setEventType("group");
        op.setBatchNumber(batchNumber);
        op.setLeaderName(item.getLeaderName());
        op.setLeaderPhone(item.getLeaderPhone());
        op.setTeamName(item.getTeamName());
        op.setRegistrationNum(item.getRegistrationNum());
        if (item.getApplyAmountT() != null) {
            op.setApplyAmount(item.getApplyAmountT() * 100);
        }
        op.setApplyTime(DateUtil.parseStr(item.getApplyTime(), DateUtil.LONG_WEB_FORMAT));
        op.setApplyCode(applyCode);
        op.setUnitCode(unitCode);
        op.setApplyStatus(ApplyStatusEnum.SUCCESS.getCode());
        op.setGameCode(item.getGameCode());
        op.setGameName(item.getGameName());
        op.setMatchCode(item.getMatchCode());
        op.setMatchName(item.getMatchName());
        op.setFieldCode(item.getSiteCode());
        op.setFieldName(item.getSiteName());
        op.setMatchGroupCode(item.getMatchGroupCode());
        op.setMatchGroupName(item.getMatchGroupName());
        op.setEventCode(item.getEventCode());
        op.setEventName(item.getEventName());
        op.setEventStartTime(DateUtil.parseCompStr(item.getEventStartTimeStr()));
        op.setEventEndTime(DateUtil.parseCompStr(item.getEventEndTimeStr()));
        op.setChannelCode("import");
        op.setIsDelet((byte) 0);
        op.setSource((byte) 1);
        op.setIdempotentId(item.getIdempotentId());
        Date time = Calendar.getInstance().getTime();
        op.setGmtCreate(time);
        op.setGmtModified(time);
        // 检查是否合法
        checkIsLegal(unitCode, op);
        this.openApplyInfoEntityMapper.insert(op);
        return applyCode;
    }

    @Override
    public List<String> assertEnrollInfos(List<EnrollInfoTemplate> templateJson) {
        List<String> resultErrMeg = new ArrayList<String>();
        // 检查数据是否符合规范
        if (CollectionUtils.isEmpty(templateJson)) {
            resultErrMeg.add("导入数据不能为空");
            return resultErrMeg;
        }
        int i = 2;
        for (EnrollInfoTemplate enll : templateJson) {
            i = i + 1;
            // 校验每行数据是否符合规范
            try {
                assertEnrollInfo(enll);
            } catch (Exception e) {
                String message = e.getMessage();
                resultErrMeg.add(String.format("表格第%s 行,错误信息:%s", i, message));
                return resultErrMeg;
            }
        }
        return resultErrMeg;
    }

    public void assertEnrollInfo(EnrollInfoTemplate enll) {
        if (enll == null) {
            return;
        }
        if (enll.getApplyTime() == null) {
            throw new BusinessException(
                "报名时间不能为空或格式错误,正确格式应为:yyyy-MM-dd HH:mm:ss格式,如2018-05-12 12:13:00");
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<EnrollInfoTemplate>> set = validator.validate(enll);
        for (ConstraintViolation<EnrollInfoTemplate> constraintViolation : set) {
            throw new BusinessException(constraintViolation.getMessage());
        }
    }

    /**
     * 断言报名信息
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param map
     */
    private void assertApplayInfo(ApplyInfoTeplate at) {
        if (at == null) {
            return;
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ApplyInfoTeplate>> set = validator.validate(at);
        for (ConstraintViolation<ApplyInfoTeplate> constraintViolation : set) {
            throw new BusinessException(constraintViolation.getMessage());
        }
    }

    /**
     * 检查报名信息是否合法
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param obj
     */
    private void checkIsLegal(String unitCode, OpenApplyInfoEntity obj) {
        if (unitCode.equals("test")) {
            return;
        }
        if (StringUtils.isBlank(unitCode)) {
            return;
        }
        // 获取赛事信息
        MatchDetailModel unitMatchInfo = spMatchFacadeClient.getUnitMatchInfo(unitCode,
            obj.getMatchCode());
        if (unitMatchInfo == null) {
            throw new DmpBusException("赛事编号错误");
        }
        // 项目编号
        if (!unitMatchInfo.getGameCode().equals(obj.getGameCode())) {
            throw new DmpBusException("项目编号错误");
        }
        // 赛事组
        if (StringUtils.isNotBlank(obj.getMatchGroupCode())) {
            boolean grouCheck = false;
            for (GroupTypeModel groupTypeModel : unitMatchInfo.getGroupTypeList()) {
                if (groupTypeModel.getGroupCode().equals(obj.getMatchGroupCode())) {
                    grouCheck = true;
                }
            }
            if (!grouCheck) {
                throw new DmpBusException("赛事分组编号错误");
            }
        }
        // 赛事项目
        boolean eventCheck = false;
        for (EventTypeModel eventTypeModel : unitMatchInfo.getEventTypeList()) {
            if (eventTypeModel.getEventCode().equals(obj.getEventCode())) {
                eventCheck = true;
            }
        }
        if (!eventCheck) {
            throw new DmpBusException("赛事细类编号错误");
        }
        // 分赛场
        boolean fiedCheck = false;
        for (FieldTypeModel fiedModel : unitMatchInfo.getFieldTypeList()) {
            if (fiedModel.getFieldCode().equals(obj.getFieldCode())) {
                fiedCheck = true;
            }
        }
        if (!fiedCheck) {
            throw new DmpBusException("赛场编号错误");
        }
    }

    @Override
    public List<String> assertScoreTemplates(String unitCode, List<ScoreTemplate> scores) {
        List<String> resultErrMeg = new ArrayList<String>();
        int i = 2;
        // 断言报名信息
        for (ScoreTemplate scoreTemplate : scores) {
            i = i + 1;
            // 校验每行数据是否符合规范
            try {
                assertScoreTemplat(unitCode, scoreTemplate);
            } catch (Exception e) {
                String message = e.getMessage();
                resultErrMeg.add(String.format("表格第%s行 错误信息:%s", i, message));
                return resultErrMeg;
            }
        }
        return resultErrMeg;
    }

    /**
     * 断言比赛成绩
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scores
     */
    public void assertScoreTemplat(String unitCode, ScoreTemplate score) {
        if (score == null) {
            return;
        }
        if (!NumberUtils.isNumber(score.getScore())) {
            throw new BusinessException("成绩必须全为数值,解析后错误结果:" + score.getScore());
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ScoreTemplate>> set = validator.validate(score);
        for (ConstraintViolation<ScoreTemplate> constraintViolation : set) {
            throw new BusinessException(constraintViolation.getMessage());
        }
        // 检查是否合法
        checkIsScoreLegal(unitCode, score);
    }

    @Override
    public List<Map<String, Object>> removeEnpty(List<Map<String, Object>> sheet) {
        List<Map<String, Object>> ntemplateJson = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : sheet) {
            boolean isEnpty = true;
            for (Entry<String, Object> enty : map.entrySet()) {
                if (enty.getValue() != null && StringUtils.isNotBlank(enty.getValue().toString())) {
                    isEnpty = false;
                }
            }
            if (!isEnpty) {
                ntemplateJson.add(map);
            }
        }
        return ntemplateJson;
    }

    @Override
    public String submitTdScores(String unitCode, List<ScoreTemplate> scores) {
        String resultMsg = null;
        int sucess = 0;
        int fail = 0;
        String batchNumber = "BT" + SeqWorkerUtil.generateTimeSequence();
        // 报名信息
        for (ScoreTemplate scoreVo : scores) {
            if (scoreVo == null) {
                continue;
            }
            // 存数据库
            try {
                addNewPpenScore(unitCode, batchNumber, scoreVo);
                sucess = sucess + 1;
            } catch (Exception e) {
                fail = fail + 1;
            }
        }
        resultMsg = String.format("导入成功:%s条,导入失败:%s条", sucess, fail);
        return resultMsg;
    }

    /**
     * 添加新的成绩信息
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param batchNumber
     * @param scoreVo
     */
    public void addNewPpenScore(String unitCode, String batchNumber, ScoreTemplate scoreVo) {
        if (StringUtils.isBlank(unitCode) || StringUtils.isBlank(batchNumber) || scoreVo == null) {
            return;
        }
        OpenScoreEntity scoreEntity = openScoreEntityMapper.getScoreInfoByIdempotent(unitCode,
            scoreVo.getIdempotentId());
        if (scoreEntity != null) {
            // 旧数据跳过
            logger.info(String.format("成绩数据:%s 已导入过..跳过....", scoreEntity.getIdempotentId()));
            return;
        }
        String scoreCode = SeqWorkerUtil.generateTimeSequence();
        OpenScoreEntity op = new OpenScoreEntity();
        op.setScoreCode(scoreCode);
        op.setUnitCode(unitCode);
        op.setBatchNumber(batchNumber);
        op.setIdempotentId(scoreVo.getIdempotentId());
        op.setBatchNumber(batchNumber);
        op.setPlayerCode(scoreVo.getPlayerCode());
        op.setPlayerName(scoreVo.getPlayerName());
        op.setPlayerPhone(scoreVo.getPlayerPhone());
        op.setSex(scoreVo.getSex());
        op.setGameCode(scoreVo.getGameCode());
        op.setGameName(scoreVo.getGameName());
        op.setMatchCode(scoreVo.getMatchCode());
        op.setMatchName(scoreVo.getMatchName());
        op.setFieldCode(scoreVo.getFieldCode());
        op.setFieldName(scoreVo.getFieldName());
        op.setMatchGroupCode(scoreVo.getMatchGroupCode());
        op.setMatchGroupName(scoreVo.getMatchGroupName());
        op.setEventCode(scoreVo.getEventCode());
        op.setEventName(scoreVo.getEventName());
        op.setPartTime(DateUtil.parseCompStr(scoreVo.getPartTime()));
        op.setScoreName(scoreVo.getScoreName());
        op.setScore(BigDecimal.valueOf(Double.parseDouble(scoreVo.getScore())));
        op.setScoreUnit(scoreVo.getScoreUnit());
        op.setScoreDesc(scoreVo.getScoreDesc());
        op.setPlayerProp(scoreVo.getPlayerProp());
        op.setScoreProp(scoreVo.getScoreProp());
        op.setUnitCode(unitCode);
        op.setChannelCode("import");
        op.setIsValid((byte) 1);
        op.setSource((byte) 1);
        Date time = Calendar.getInstance().getTime();
        op.setGmtCreate(time);
        op.setGmtModified(time);
        this.openScoreEntityMapper.insert(op);

    }

    /**
     * 检查成绩数据是否合法
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param op
     */
    public void checkIsScoreLegal(String unitCode, ScoreTemplate obj) {
        if (unitCode.equals("test")) {
            return;
        }
        if (StringUtils.isBlank(unitCode)) {
            return;
        }
        // 获取赛事信息
        MatchDetailModel unitMatchInfo = spMatchFacadeClient.getUnitMatchInfo(unitCode,
            obj.getMatchCode());
        if (unitMatchInfo == null) {
            throw new DmpBusException(String.format("赛事编号错误", obj.getMatchCode()));
        }
        obj.setMatchName(unitMatchInfo.getMatchName());
        obj.setGameCode(unitMatchInfo.getGameCode());
        // 项目编号
        if (!unitMatchInfo.getGameCode().equals(obj.getGameCode())) {
            throw new DmpBusException(String.format("项目编号错误", obj.getGameCode()));
        }
        // 赛事组
        if (StringUtils.isNotBlank(obj.getMatchGroupCode())) {
            boolean grouCheck = false;
            for (GroupTypeModel groupTypeModel : unitMatchInfo.getGroupTypeList()) {
                if (groupTypeModel.getGroupCode().equals(obj.getMatchGroupCode())) {
                    grouCheck = true;
                    obj.setMatchGroupName(groupTypeModel.getGroupName());
                    break;
                }
            }
            if (!grouCheck) {
                throw new DmpBusException("赛事分组编号错误");
            }
        }
        // 赛事项目
        boolean eventCheck = false;
        for (EventTypeModel eventTypeModel : unitMatchInfo.getEventTypeList()) {
            if (eventTypeModel.getEventCode().equals(obj.getEventCode())) {
                obj.setEventName(eventTypeModel.getEventName());
                eventCheck = true;
                break;
            }
        }
        if (!eventCheck) {
            throw new DmpBusException("赛事细类编号错误");
        }
        // 分赛场
        boolean fiedCheck = false;
        for (FieldTypeModel fiedModel : unitMatchInfo.getFieldTypeList()) {
            if (fiedModel.getFieldCode().equals(obj.getFieldCode())) {
                obj.setFieldName(fiedModel.getFieldName());
                fiedCheck = true;
            }
        }
        if (!fiedCheck) {
            throw new DmpBusException("赛场编号错误");
        }
    }

    @Override
    public List<String> assertScoreRankTemplates(String unitCode, List<ScoreRankTemplate> scores) {
        List<String> resultErrMeg = new ArrayList<String>();
        int i = 2;
        // 断言报名信息
        for (ScoreRankTemplate scoreTemplate : scores) {
            i = i + 1;
            // 校验每行数据是否符合规范
            try {
                assertScoreRankTemplat(unitCode, scoreTemplate);
            } catch (Exception e) {
                String message = e.getMessage();
                resultErrMeg.add(String.format("表格第%s行 错误信息:%s", i, message));
                return resultErrMeg;
            }
        }
        return resultErrMeg;
    }

    /**
     * 断言成绩排名模板
     * 
     * @title
     * @methodName
     * @author wangyi
     * @param unitCode 
     * @description
     * @param scoreTemplate
     */
    public void assertScoreRankTemplat(String unitCode, ScoreRankTemplate scoreTemplate) {
        if (scoreTemplate == null) {
            return;
        }
        if (!NumberUtils.isNumber(scoreTemplate.getScore())) {
            throw new BusinessException("成绩必须全为数值,解析后错误结果:" + scoreTemplate.getScore());
        }
        if (!NumberUtils.isNumber(scoreTemplate.getRanking())) {
            throw new BusinessException("排名必须全为数值,解析后错误结果:" + scoreTemplate.getScore());
        }
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ScoreRankTemplate>> set = validator.validate(scoreTemplate);
        for (ConstraintViolation<ScoreRankTemplate> constraintViolation : set) {
            throw new BusinessException(constraintViolation.getMessage());
        }
        // 检查是否合法
        checkIsScoreRankLegal(unitCode, scoreTemplate);
    }

    @Override
    public void submitTdScoreRanks(String unitCode, List<ScoreRankTemplate> scores) {
        String batchNumber = "BT" + SeqWorkerUtil.generateTimeSequence();
        // 报名信息
        for (ScoreRankTemplate scoreVo : scores) {
            if (scoreVo == null) {
                continue;
            }
            // 存数据库
            addNewScoreRank(unitCode, batchNumber, scoreVo);
        }
    }

    /***
     * 添加新成绩排名
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param batchNumber
     * @param scoreVo
     */
    @Transactional
    public void addNewScoreRank(String unitCode, String batchNumber, ScoreRankTemplate scoreVo) {
        if (StringUtils.isBlank(unitCode) || StringUtils.isBlank(batchNumber) || scoreVo == null) {
            return;
        }
        OpenScoreRankEntity rankEntity = openScoreRankEntityMapper
            .getScoreRankByIdempId(scoreVo.getIdempotentId());
        if (rankEntity != null) {
            logger.info("成绩排名数据存在,idempotentId:{} 跳过本次导入....", scoreVo.getIdempotentId());
            return;
        }
        OpenScoreRankEntity op = new OpenScoreRankEntity();
        op.setUnitCode(unitCode);
        op.setIdempotentId(scoreVo.getIdempotentId());
        op.setScoreRankCode(SeqWorkerUtil.generateTimeSequence());
        op.setBatchNumber(batchNumber);
        op.setPlayerCode(scoreVo.getPlayerCode());
        op.setPartCode(scoreVo.getPartCode());
        op.setPlayerName(scoreVo.getPlayerName());
        op.setPlayerPhone(scoreVo.getPlayerPhone());
        op.setGameCode(scoreVo.getGameCode());
        op.setGameName(scoreVo.getGameName());
        op.setMatchCode(scoreVo.getMatchCode());
        op.setMatchName(scoreVo.getMatchName());
        op.setFieldCode(scoreVo.getFieldCode());
        op.setFieldName(scoreVo.getFieldName());
        op.setPartTime(DateUtil.parseCompStr(scoreVo.getPartTime()));
        op.setMatchGroupCode(scoreVo.getMatchGroupCode());
        op.setMatchGroupName(scoreVo.getMatchGroupName());
        op.setEventCode(scoreVo.getEventCode());
        op.setEventName(scoreVo.getEventName());
        op.setScoreName(scoreVo.getScoreName());
        op.setScore(BigDecimal.valueOf(Double.parseDouble(scoreVo.getScore())));
        op.setScoreUnit(scoreVo.getScoreUnit());
        op.setScoreDesc(scoreVo.getScoreDesc());
        op.setRanking(Double.valueOf(scoreVo.getRanking()).intValue());
        op.setPromotion(scoreVo.getPromotion());
        op.setExtProp(scoreVo.getExtProp());
        op.setUnitCode(unitCode);
        op.setChannelCode("import");
        op.setSource((byte) 1);
        Date time = Calendar.getInstance().getTime();
        op.setGmtCreate(time);
        op.setGmtModified(time);
        this.openScoreRankEntityMapper.insert(op);
    }

    /**
     * 检查数据是否合法
     * 
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param op
     */
    private void checkIsScoreRankLegal(String unitCode, ScoreRankTemplate obj) {
        if (unitCode.equals("test")) {
            return;
        }
        if (StringUtils.isBlank(unitCode)) {
            return;
        }
        // 获取赛事信息
        MatchDetailModel unitMatchInfo = spMatchFacadeClient.getUnitMatchInfo(unitCode,
            obj.getMatchCode());
        if (unitMatchInfo == null) {
            throw new DmpBusException(String.format("赛事编号错误", obj.getMatchCode()));
        }
        obj.setMatchName(unitMatchInfo.getMatchName());
        obj.setGameCode(unitMatchInfo.getGameCode());
        // 项目编号
        if (!unitMatchInfo.getGameCode().equals(obj.getGameCode())) {
            throw new DmpBusException(String.format("项目编号错误", obj.getGameCode()));
        }
        // 赛事组
        if (StringUtils.isNotBlank(obj.getMatchGroupCode())) {
            boolean grouCheck = false;
            for (GroupTypeModel groupTypeModel : unitMatchInfo.getGroupTypeList()) {
                if (groupTypeModel.getGroupCode().equals(obj.getMatchGroupCode())) {
                    grouCheck = true;
                    obj.setMatchGroupName(groupTypeModel.getGroupName());
                    break;
                }
            }
            if (!grouCheck) {
                throw new DmpBusException("赛事分组编号错误");
            }
        }
        // 赛事项目
        boolean eventCheck = false;
        for (EventTypeModel eventTypeModel : unitMatchInfo.getEventTypeList()) {
            if (eventTypeModel.getEventCode().equals(obj.getEventCode())) {
                obj.setEventName(eventTypeModel.getEventName());
                eventCheck = true;
                break;
            }
        }
        if (!eventCheck) {
            throw new DmpBusException("赛事细类编号错误");
        }
        // 分赛场
        boolean fiedCheck = false;
        for (FieldTypeModel fiedModel : unitMatchInfo.getFieldTypeList()) {
            if (fiedModel.getFieldCode().equals(obj.getFieldCode())) {
                obj.setFieldName(fiedModel.getFieldName());
                fiedCheck = true;
            }
        }
        if (!fiedCheck) {
            throw new DmpBusException("赛场编号错误");
        }
    }

    @Override
    public PagingResult<OpenPlayerEntity> selectUnCleanPlayer(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<OpenPlayerEntity> players = openPlayerEntityMapper.selectUnCleanPlayers();
        PageInfo<OpenPlayerEntity> pageInfo = new PageInfo<OpenPlayerEntity>(players);
        return new PagingResult<OpenPlayerEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);
    }

    @Override
    public void updatePlayerCleanStatus(OpenPlayerEntity player) {
        if (player == null) {
            throw new BusinessException("运动员不能为空");
        }
        openPlayerEntityMapper.updateCleanStatus(player.getId());

    }

    @Override
    public PagingResult<OpenPlayerEntity> selectPlayers(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<OpenPlayerEntity> players = openPlayerEntityMapper.selectPlayers();
        PageInfo<OpenPlayerEntity> pageInfo = new PageInfo<OpenPlayerEntity>(players);
        return new PagingResult<OpenPlayerEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);

    }

    @Override
    public long getApplyTotal() {
        return openPlayerEntityMapper.getTotalPlayer();
    }

    @Override
    public PagingResult<PlayerModel> getPlayersWithUser(boolean allowData, UserInfoModel userInfo,
                                                        String unitCode, String match,
                                                        String player, String playerPhone,
                                                        Integer source, String channelCode,
                                                        String startTime, String endTime,
                                                        String sortField, String sortOrder,
                                                        String eventType, int pageNumber,
                                                        int pageSize) {
        //获取默认查询所有权限
        List<String> unitCodes = null;
        if (!allowData) {
            unitCodes = new ArrayList<String>();
            List<UserUnit> users = userUnitService.getUserUnit(userInfo.getUuid());
            for (UserUnit userUnit : users) {
                unitCodes.add(userUnit.getUnitCode());
            }
            unitCodes.add(userInfo.getUserName());
            //校验搜索授权
            if (StringUtils.isNotBlank(unitCode)) {
                boolean legal = false;
                for (String ucode : unitCodes) {
                    if (unitCode.equals(ucode)) {
                        legal = true;
                    }
                }
                if (legal) {
                    unitCodes = new ArrayList<String>();
                    unitCodes.add(unitCode);
                } else {
                    throw new DmpBusException("你没有权限获取该承办方数据,您可以联系管理员授权");
                }
            }
        } else {
            if (StringUtils.isNotBlank(unitCode)) {
                unitCodes = new ArrayList<String>();
                unitCodes.add(unitCode);
            }
        }
        if (unitCodes != null && unitCodes.size() == 0) {
            return new PagingResult<PlayerModel>(new ArrayList<PlayerModel>(), 0l, pageNumber,
                pageSize);
        }
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("unitCodes", unitCodes);
        queryParams.put("match", match);
        queryParams.put("player", player);
        queryParams.put("channelCode", channelCode);
        queryParams.put("source", source);
        queryParams.put("playerPhone", playerPhone);
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("eventType", eventType);
        List<PlayerModel> players = playerMapper.selectPlayerByLikeParams(queryParams);
        PageInfo<PlayerModel> pageInfo = new PageInfo<PlayerModel>(players);
        return new PagingResult<PlayerModel>(pageInfo.getList(), pageInfo.getTotal(), pageNumber,
            pageSize);
    }

}
