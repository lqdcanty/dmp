/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.model.OpenScoreResultModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.biz.open.request.ScoreQueryRequest;
import com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.user.UserUnitService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreServiceImpl.java, v 0.1 2018年6月21日 下午4:38:23 zhiyang Exp $
 */
@Service("openScoreService")
public class OpenScoreServiceImpl implements OpenScoreService {
    private Logger                logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenConfigService     openConfigService;

    @Autowired
    private OpenScoreEntityMapper openScoreEntityMapper;

    @Autowired
    private SpMatchFacadeClient   spMatchFacadeClient;

    @Autowired
    private UserUnitService       userUnitService;

    /**
     * 
     * @throws ParseException 
     * @throws OpenException 
     * @see com.efida.sports.dmp.biz.open.OpenScoreService#submitScoreInfo(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<OpenScoreResultModel> submitScoreInfo(String unitCode, int count, String data,
                                                      String timestamp, String sign,
                                                      String ipAddress) throws OpenException,
                                                                        ParseException {
        SignUtils.checkTimestamp(timestamp);
        if (count < 1) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条成绩信息，count必须大于0!");
        }
        if (StringUtils.isEmpty(data)) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条成绩信息，data不能为空!");
        }
        if (logger.isInfoEnabled()) {
            logger.info("data:" + data);
        }
        JSONArray enrollData = JSONObject.parseArray(data);
        if (enrollData.size() != count) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "count与提交成绩数据data中记录数不一致!");
        }
        String privateKey = openConfigService.getPrivateKey(unitCode);
        String signData = unitCode + privateKey + timestamp;
        SignUtils.assertSignTrue(signData, sign);
        List<OpenScoreResultModel> items = new ArrayList<OpenScoreResultModel>();
        for (int i = 0; i < enrollData.size(); i++) {
            JSONObject json = enrollData.getJSONObject(i);
            //数据转换    
            OpenScoreModel obj = parseOpenScoreModel(json);
            //检查提交成绩信息是否完整
            checkScoreModel(obj);
            // 检查提交运动员信息是否合法
            Date matchEndDate = checkScoreModelLegal(unitCode, obj);
            String idempotentId = obj.getIdempotentId();
            try {
                checkScoreModel(obj);
                processScore(unitCode, obj);
                OpenScoreResultModel or = addOpenScoreInfo(obj, unitCode, matchEndDate);
                items.add(or);
            } catch (Exception ex) {
                logger.error("存储成绩信息失败!报名内容,playerPhone:" + obj.getPlayerPhone() + ",eventName:"
                             + obj.getEventName(),
                    ex);
                OpenScoreResultModel or = new OpenScoreResultModel();
                or.setPlayerPhone(obj.getPlayerPhone());
                or.setPlayerName(obj.getPlayerName());
                or.setIdempotentId(idempotentId);
                or.setMatchCode(obj.getMatchCode());
                or.setEventCode(obj.getEventCode());
                or.setSuccess(2);
                or.setMessage("提交成绩信息失败,原因:" + ex.getMessage());

                items.add(or);
            }
        }
        return items;
    }

    /**
     * 处理成绩数据
     * @title
     * @methodName
     * @author wangyi
     * @param unitCode 
     * @description
     * @param obj
     */
    private void processScore(String unitCode, OpenScoreModel obj) {
        if (unitCode.equals("leke")) {
            if (StringUtils.isBlank(obj.getSex())) {
                return;
            }
            if (obj.getSex().equalsIgnoreCase("m")) {
                obj.setMatchGroupCode("group201807231145544670");
                obj.setMatchGroupName("男子组");
            }
            if (obj.getSex().equalsIgnoreCase("f")) {
                obj.setMatchGroupCode("group201807231145524475");
                obj.setMatchGroupName("女子组");
            }
        }
    }

    /**
     *检查成绩信息是否合法
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param obj
     * @return 
     * @throws OpenException 
     */
    private Date checkScoreModelLegal(String unitCode, OpenScoreModel obj) throws OpenException {
        if (unitCode.equals("test")) {
            return null;
        }
        if (StringUtils.isBlank(unitCode)) {
            return null;
        }
        //获取赛事信息
        MatchDetailModel unitMatchInfo = spMatchFacadeClient.getUnitMatchInfo(unitCode,
            obj.getMatchCode());
        if (unitMatchInfo == null) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                String.format("赛事编号错误", obj.getMatchCode()));
        }
        obj.setMatchName(unitMatchInfo.getMatchName());
        obj.setGameCode(unitMatchInfo.getGameCode());
        //项目编号
        if (!unitMatchInfo.getGameCode().equals(obj.getGameCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                String.format("项目编号错误", obj.getGameCode()));
        }
        //赛事组
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
                throw new OpenException(ErrorCode.PARAMETER_INVALID, "赛事分组编号错误");
            }
        }
        //赛事项目
        boolean eventCheck = false;
        for (EventTypeModel eventTypeModel : unitMatchInfo.getEventTypeList()) {
            if (eventTypeModel.getEventCode().equals(obj.getEventCode())) {
                obj.setEventName(eventTypeModel.getEventName());
                eventCheck = true;
                break;
            }
        }
        if (!eventCheck) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "赛事细类编号错误");
        }
        //分赛场
        boolean fiedCheck = false;
        for (FieldTypeModel fiedModel : unitMatchInfo.getFieldTypeList()) {
            if (fiedModel.getFieldCode().equals(obj.getFieldCode())) {
                obj.setFieldName(fiedModel.getFieldName());
                fiedCheck = true;
            }
        }
        if (!fiedCheck) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "赛场编号错误");
        }

        return unitMatchInfo.getEndTime();
    }

    /**
     * 转换报名Model
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param json
     * @return
     * @throws OpenException
     */
    private OpenScoreModel parseOpenScoreModel(JSONObject json) throws OpenException {
        OpenScoreModel obj = null;
        try {
            obj = JSON.toJavaObject(json, OpenScoreModel.class);
        } catch (Exception e) {
            //此处参数错误导致系统异常 安装参数错误处理
            logger.error("err data :{}", JSON.toJSONString(json));
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "data字段中JSON中存在数据类型错误,请检查参数");
        }
        return obj;
    }

    private String getIdempotentId(OpenScoreModel scoreInfo) {

        String idempotentId = scoreInfo.getEventCode() + "_" + scoreInfo.getPlayerPhone();
        if (StringUtils.isNotEmpty(scoreInfo.getPlayerName())) {
            idempotentId += "_" + scoreInfo.getPlayerName();
        } else {
            if (StringUtils.isNotEmpty(scoreInfo.getOpenId())) {
                if (StringUtils.isNotBlank(scoreInfo.getOpenPlatType())) {
                    idempotentId += "_" + scoreInfo.getOpenPlatType();
                }
                idempotentId += scoreInfo.getOpenId();
            }
        }
        if (idempotentId.length() > 64) {
            idempotentId = idempotentId.substring(0, 64);
        }

        return idempotentId;
    }

    /**
     * 通过开放接口进行报名
     * 
     * @param scoreInfo
     * @param unitCode
     * @param matchEndDate 
     * @return
     * @throws ParseException 
     * @throws OpenException 
     */
    @Transactional
    public OpenScoreResultModel addOpenScoreInfo(OpenScoreModel scoreInfo, String unitCode,
                                                 Date matchEndDate) throws ParseException,
                                                                    OpenException {

        if (StringUtils.isEmpty(scoreInfo.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "提交成绩信息手机号不能为空!");
        }
        Date endDataTime = null;
        if (matchEndDate != null) {
            endDataTime = new Date(matchEndDate.getTime() + 31L * 86400 * 1000);
        }
        String idempotentId = scoreInfo.getIdempotentId();
        if (StringUtils.isEmpty(idempotentId)) {
            idempotentId = getIdempotentId(scoreInfo);
            scoreInfo.setIdempotentId(idempotentId);
        }
        boolean isUpdate = false;
        OpenScoreEntity old = getOpenScoreInfoEntityByIdempotentId(unitCode, idempotentId);
        if (old != null) {
            if (StringUtils.isEmpty(scoreInfo.getModifyFlag())
                || !scoreInfo.getModifyFlag().equals("1")) {
                OpenScoreResultModel or = new OpenScoreResultModel();
                or.setScoreCode(old.getScoreCode());
                or.setPlayerCode(old.getPlayerCode());
                or.setPlayerName(scoreInfo.getPlayerName());
                or.setPlayerPhone(scoreInfo.getPlayerPhone());
                or.setIdempotentId(idempotentId);
                or.setMatchCode(old.getMatchCode());
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("重复提交，上次已经提交成绩成功,本次提交忽略!");
                return or;
            }
            isUpdate = true;

            boolean createExpire = System.currentTimeMillis() / 1000
                                   - old.getGmtCreate().getTime() / 1000 > 86400 * 31;
            boolean endExpire = endDataTime == null
                                || (System.currentTimeMillis() - 31L * 86400 * 1000 > endDataTime
                                    .getTime());

            if (createExpire && endExpire) {
                OpenScoreResultModel or = new OpenScoreResultModel();
                or.setScoreCode(old.getScoreCode());
                or.setPlayerCode(old.getPlayerCode());
                or.setPlayerName(scoreInfo.getPlayerName());
                or.setPlayerPhone(scoreInfo.getPlayerPhone());
                or.setIdempotentId(idempotentId);
                or.setMatchCode(old.getMatchCode());
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("不支持更新，提交时间已经超过比赛结束时间及初次上传时间31日!");
                return or;
            }
        }
        OpenScoreEntity oa = createScoreInfo(scoreInfo, unitCode);
        if (!isUpdate) {
            final String scoreCode = SeqWorkerUtil.generateTimeSequence();
            oa.setScoreCode(scoreCode);
            this.openScoreEntityMapper.insert(oa);
        } else {
            oa.setScoreCode(old.getScoreCode());
            oa.setId(old.getId());
            oa.setEnrollSource(old.getEnrollSource());
            oa.setSync(old.getSync());
            oa.setSyncStatus(old.getSyncStatus());
            oa.setSyncTotal(old.getSyncTotal());
            oa.setNextSyncTime(old.getNextSyncTime());
            //update
            this.openScoreEntityMapper.updateByPrimaryKey(oa);
        }
        OpenScoreResultModel or = new OpenScoreResultModel();
        or.setScoreCode(oa.getScoreCode());
        or.setPlayerCode(oa.getPlayerCode());
        or.setPlayerPhone(oa.getPlayerPhone());
        or.setPlayerName(oa.getPlayerName());
        or.setMatchCode(oa.getMatchCode());
        or.setEventCode(oa.getEventCode());
        or.setSuccess(1);
        or.setIdempotentId(idempotentId);
        or.setMessage("提交比赛成绩成功");
        return or;
    }

    private OpenScoreEntity createScoreInfo(OpenScoreModel sc,
                                            String unitCode) throws OpenException {
        OpenScoreEntity oa = new OpenScoreEntity();
        oa.setUnitCode(unitCode);
        oa.setPlayerCode(sc.getPlayerCode());
        oa.setPlayerName(sc.getPlayerName());
        oa.setPlayerPhone(sc.getPlayerPhone());
        oa.setSex(sc.getSex());
        oa.setOpenPlatType(sc.getOpenPlatType());
        oa.setOpenId(sc.getOpenId());
        oa.setGameCode(sc.getGameCode());
        oa.setGameName(sc.getGameName());
        oa.setMatchCode(sc.getMatchCode());
        oa.setMatchName(sc.getMatchName());
        oa.setFieldCode(sc.getFieldCode());
        oa.setFieldName(sc.getFieldName());
        oa.setMatchGroupCode(sc.getMatchGroupCode());
        oa.setMatchGroupName(sc.getMatchGroupName());
        oa.setEventCode(sc.getEventCode());
        oa.setEventName(sc.getEventName());
        oa.setPartTime(getDate(sc.getPartTime()));
        oa.setScoreName(sc.getScoreName());
        oa.setScore(new BigDecimal(sc.getScore() + ""));
        oa.setScoreUnit(sc.getScoreUnit());
        oa.setScoreDesc(sc.getScoreDesc());
        oa.setIsValid(sc.getIsValid());
        oa.setScoreType(sc.getScoreType());
        oa.setScoreProp(sc.getScoreProp() == null ? null : sc.getScoreProp().toJSONString());
        oa.setPlayerProp(sc.getPlayerProp() == null ? null : sc.getPlayerProp().toJSONString());
        oa.setChannelCode(unitCode);
        oa.setIdempotentId(sc.getIdempotentId());
        return oa;
    }

    private OpenScoreEntity getOpenScoreInfoEntityByIdempotentId(String unitCode,
                                                                 String idempotentId) {
        OpenScoreEntity openScoreEntity = this.openScoreEntityMapper
            .getScoreInfoByIdempotent(unitCode, idempotentId);
        return openScoreEntity;
    }

    private Date getDate(String dateStr) throws OpenException {

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            return DateUtil.parse(dateStr, DateUtil.LONG_WEB_FORMAT);

        } catch (Exception ex) {
            logger.error("date error , datestr:" + dateStr, ex);
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                "日期格式错误!格式:" + DateUtil.LONG_WEB_FORMAT);
        }
    }

    private void checkScoreModel(OpenScoreModel obj) throws OpenException {
        if (StringUtils.isEmpty(obj.getPartTime())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "参赛时间不能为空!");
        }
        if (StringUtils.isEmpty(obj.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "运动员手机号playerPhone不能为空!");
        }
        if (StringUtils.isEmpty(obj.getGameCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "项目编号gameCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getMatchCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "赛事编号matchCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getEventCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "项目细类编号eventCode不能为空!");
        }

    }

    /**
     * 
     * @throws OpenException 
     * @throws ParseException 
     * @see com.efida.sports.dmp.biz.open.OpenScoreService#queryScoreInfo(com.efida.sports.dmp.biz.open.request.ScoreQueryRequest)
     */
    @Override
    public List<OpenScoreModel> queryScoreInfo(ScoreQueryRequest qs) throws OpenException,
                                                                     ParseException {

        SignUtils.checkTimestamp(qs.getTimestamp());
        if (StringUtils.isEmpty(qs.getUnitCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "unitCode不能为空!");
        }

        String privateKey = openConfigService.getPrivateKey(qs.getUnitCode());
        //todo:老版本 scoreName未进行签名验证， 规范中未加入
        String signData = qs.getUnitCode() + privateKey + qs.getMatchCode() + qs.getEventCode()
                          + qs.getPlayerCode() + qs.getPlayerPhone() + qs.getPageNumber()
                          + qs.getPageSize() + qs.getTimestamp();
        SignUtils.assertSignTrue(signData, qs.getSign());

        List<OpenScoreEntity> items = this.queryScoreList(qs);
        List<OpenScoreModel> newItem = convert2ScoreInfo(items);
        return newItem;

    }

    public List<OpenScoreEntity> queryScoreList(ScoreQueryRequest qs) {

        int start = (qs.getPageNumber() - 1) * qs.getPageSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", qs.getUnitCode());
        map.put("matchCode", qs.getMatchCode());
        map.put("eventCode", qs.getEventCode());
        map.put("playerCode", qs.getPlayerCode());
        map.put("scoreName", qs.getScoreName());
        map.put("playerPhone", qs.getPlayerPhone());
        map.put("start", start);
        map.put("limit", qs.getPageSize());
        List<OpenScoreEntity> items = this.openScoreEntityMapper.selectByProp(map);

        return items;
    }

    private List<OpenScoreModel> convert2ScoreInfo(List<OpenScoreEntity> items) {
        List<OpenScoreModel> newItems = new ArrayList<OpenScoreModel>();

        for (OpenScoreEntity sc : items) {

            OpenScoreModel oa = new OpenScoreModel();

            oa.setPlayerCode(sc.getPlayerCode());
            oa.setPlayerName(sc.getPlayerName());
            oa.setPlayerPhone(sc.getPlayerPhone());
            oa.setSex(sc.getSex());
            oa.setOpenPlatType(sc.getOpenPlatType());
            oa.setOpenId(sc.getOpenId());
            oa.setGameCode(sc.getGameCode());
            oa.setGameName(sc.getGameName());
            oa.setMatchCode(sc.getMatchCode());
            oa.setMatchName(sc.getMatchName());
            oa.setFieldCode(sc.getFieldCode());
            oa.setFieldName(sc.getFieldName());
            oa.setMatchGroupCode(sc.getMatchGroupCode());
            oa.setMatchGroupName(sc.getMatchGroupName());

            oa.setEventCode(sc.getEventCode());
            oa.setEventName(sc.getEventName());
            oa.setPartTime(getDate(sc.getPartTime()));
            oa.setScoreName(sc.getScoreName());
            oa.setScore(sc.getScore().doubleValue());
            oa.setScoreUnit(sc.getScoreUnit());
            oa.setScoreDesc(sc.getScoreDesc());
            oa.setIsValid(sc.getIsValid());
            oa.setScoreType(sc.getScoreType());
            oa.setIdempotentId(sc.getIdempotentId());

            if (StringUtils.isEmpty(sc.getPlayerProp())) {
                oa.setPlayerProp(null);
            } else {
                try {
                    oa.setPlayerProp(JSONObject.parseObject(sc.getPlayerProp()));
                } catch (Exception ex) {
                    logger.error("convert playerProp to json failed . ", ex);
                }
            }
            if (StringUtils.isEmpty(sc.getScoreProp())) {
                oa.setScoreProp(null);
            } else {
                try {
                    oa.setScoreProp(JSONObject.parseObject(sc.getScoreProp()));
                } catch (Exception ex) {
                    logger.error("convert scoreProp to json failed . ", ex);
                }
            }

            newItems.add(oa);
        }
        return newItems;
    }

    private String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return DateUtil.format(date, DateUtil.LONG_WEB_FORMAT);
    }

    @Override
    public PagingResult<OpenScoreEntity> getPagePlayerLikeParams(boolean allowData,
                                                                 UserInfoModel userInfo,
                                                                 String unitCode, String match,
                                                                 String playerName,
                                                                 String playerPhone, String valid,
                                                                 Integer syncStatus,
                                                                 String startTime, String endTime,
                                                                 String channelCode,
                                                                 String sortField, String sortOrder,
                                                                 int page, int limit) {
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
            return new PagingResult<OpenScoreEntity>(new ArrayList<OpenScoreEntity>(), 0l, page,
                limit);
        }
        PageHelper.startPage(page, limit);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("unitCodes", unitCodes);
        queryParams.put("match", match);
        queryParams.put("playerName", playerName);
        queryParams.put("playerPhone", playerPhone);
        queryParams.put("valid", valid);
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);
        queryParams.put("channelCode", channelCode);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("syncStatus", syncStatus);
        List<OpenScoreEntity> scores = openScoreEntityMapper.selectPageLikeParams(queryParams);
        PageInfo<OpenScoreEntity> pageInfo = new PageInfo<OpenScoreEntity>(scores);
        return new PagingResult<OpenScoreEntity>(pageInfo.getList(), pageInfo.getTotal(), page,
            limit);
    }

    @Override
    public List<OpenScoreEntity> queryByPlayerPhoneAndEventCode(String playerPhone,
                                                                String eventCode) {
        return openScoreEntityMapper.queryByPlayerPhoneAndEventCode(playerPhone, eventCode);
    }

    @Override
    public OpenScoreEntity getRegisterBestScore(CompetitionEntity competition, String phoneNum) {

        Map<String, Object> queryParams = getScoreQueryParas(competition, phoneNum);
        OpenScoreEntity entity = openScoreEntityMapper.getScoreOrderByScoreDesc(queryParams);
        if (entity == null) {
            return null;
        }
        // 如果值越大成绩越好
        if ("big".equals(entity.getScoreType())) {
            return entity;
        }
        return openScoreEntityMapper.getScoreOrderByScoreAsc(queryParams);

    }

    public OpenScoreEntity getUserBestScore(String matchCode, String fieldCode, String groupCode,
                                            String eventCode, String phoneNum) {

        if (StringUtils.isEmpty(phoneNum)) {
            return null;
        }

        if (StringUtils.isEmpty(matchCode)) {
            return null;
        }

        if (StringUtils.isEmpty(eventCode)) {
            return null;
        }
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("playerPhone", phoneNum);
        queryParams.put("matchCode", matchCode);

        if (StringUtils.isNotBlank(fieldCode)) {
            List<String> areaCodes = new ArrayList<String>();
            areaCodes.add(fieldCode);
            queryParams.put("areas", areaCodes);
        }

        if (StringUtils.isNotBlank(groupCode)) {
            List<String> groupCodes = new ArrayList<String>();
            groupCodes.add(groupCode);
            queryParams.put("groups", groupCodes);
        }

        List<String> eventCodes = new ArrayList<String>();
        eventCodes.add(eventCode);
        queryParams.put("events", eventCodes);

        OpenScoreEntity entity = openScoreEntityMapper.getScoreOrderByScoreDesc(queryParams);
        if (entity == null) {
            return null;
        }
        // 如果值越大成绩越好
        if ("big".equalsIgnoreCase(entity.getScoreType())) {
            return entity;
        }
        return openScoreEntityMapper.getScoreOrderByScoreAsc(queryParams);

    }

    private Map<String, Object> getScoreQueryParas(CompetitionEntity competition, String phoneNum) {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("playerPhone", phoneNum);
        String matchCode = competition.getMatchCode();
        queryParams.put("matchCode", matchCode);

        String groups = competition.getGroups();
        if (StringUtils.isNotBlank(groups)) {
            List<String> groupCodes = JSONObject.parseArray(groups, String.class);//把字符串转换成集合
            queryParams.put("groups", groupCodes);
        }
        String events = competition.getEvents();
        if (StringUtils.isNotBlank(events)) {
            List<String> eventCodes = JSONObject.parseArray(events, String.class);//把字符串转换成集合
            queryParams.put("events", eventCodes);
        }
        String areas = competition.getAreas();
        if (StringUtils.isNotBlank(areas)) {
            List<String> areaCodes = JSONObject.parseArray(areas, String.class);//把字符串转换成集合
            queryParams.put("areas", areaCodes);
        }

        return queryParams;

    }

    @Override
    public List<OpenScoreEntity> queryRegisterScores(CompetitionEntity competition,
                                                     String phoneNum) {

        Map<String, Object> queryParams = getScoreQueryParas(competition, phoneNum);
        List<OpenScoreEntity> entitys = openScoreEntityMapper.queryScores(queryParams);
        return entitys;
    }

    @Override
    public PagingResult<OpenScoreEntity> queryRegisterScores(CompetitionEntity competition,
                                                             String phoneNum, int currentPage,
                                                             int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> queryParams = getScoreQueryParas(competition, phoneNum);
        List<OpenScoreEntity> list = openScoreEntityMapper.queryScores(queryParams);
        PageInfo<OpenScoreEntity> pageInfo = new PageInfo<OpenScoreEntity>(list);
        return new PagingResult<OpenScoreEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenScoreService#queryScoreList(com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest)
     */
    @Override
    public List<OpenScoreEntity> queryScoreList(SearchCompetionScoreRequest qs) {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("matchCode", qs.getMatchCode());
        queryParams.put("playerPhone", qs.getPlayerPhone());
        if (CollectionUtils.isNotEmpty(qs.getFieldCodes())) {
            queryParams.put("areas", qs.getFieldCodes());
        }
        if (CollectionUtils.isNotEmpty(qs.getGroupCodes())) {
            queryParams.put("groups", qs.getGroupCodes());
        }
        if (CollectionUtils.isNotEmpty(qs.getEventCodes())) {
            queryParams.put("events", qs.getEventCodes());
        }

        queryParams.put("start", (qs.getPageNumber() - 1) * qs.getPageSize());
        queryParams.put("limit", qs.getPageSize());

        List<OpenScoreEntity> list = openScoreEntityMapper.queryCompetitionScores(queryParams);

        return list;
    }
}
