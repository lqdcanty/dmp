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
import com.efida.sport.open.model.OpenScoreRankModel;
import com.efida.sport.open.model.OpenScoreRankResultModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.OpenScoreRankService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.biz.open.request.ScoreRankQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreRankEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreRankServiceImpl.java, v 0.1 2018年7月9日 下午10:25:48 zhiyang Exp $
 */
@Service("openScoreRankService")
public class OpenScoreRankServiceImpl implements OpenScoreRankService {

    private Logger                    logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenConfigService         openConfigService;

    @Autowired
    private OpenScoreRankEntityMapper openScoreRankEntityMapper;

    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenScoreRankService#submitScoreRankInfo(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<OpenScoreRankResultModel> submitScoreRankInfo(String unitCode, String matchCode,
                                                              int count, String data,
                                                              String timestamp, String sign,
                                                              String ipAddress) throws OpenException,
                                                                                ParseException {
        SignUtils.checkTimestamp(timestamp);
        if (count < 1) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条成绩名次信息，count必须大于0!");
        }
        if (StringUtils.isEmpty(data)) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条成绩名次信息，data不能为空!");
        }
        if (logger.isInfoEnabled()) {
            logger.info("data:" + data);
        }
        JSONArray enrollData = JSONObject.parseArray(data);
        if (enrollData.size() != count) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "count与提交成绩数据data中记录数不一致!");
        }

        if (matchCode == null) {
            matchCode = "";
        }
        String privateKey = openConfigService.getPrivateKey(unitCode);
        String signData = unitCode + matchCode + privateKey + timestamp;
        SignUtils.assertSignTrue(signData, sign);
        List<OpenScoreRankResultModel> items = new ArrayList<OpenScoreRankResultModel>();
        //获取赛事信息
        MatchDetailModel unitMatchInfo = spMatchFacadeClient.getUnitMatchInfo(unitCode, matchCode);
        for (int i = 0; i < enrollData.size(); i++) {
            JSONObject json = enrollData.getJSONObject(i);
            //数据转换
            OpenScoreRankModel obj = parseOpenScoreRanklModel(json);
            //设置赛事编号
            obj.setMatchCode(matchCode);
            //检查成绩排名信息是否完整
            checkScoreRankModel(obj);
            //检查提成绩排名信息是否合法
            Date matchEndDate = checkOpenScoreRankLegal(unitCode, obj, unitMatchInfo);
            obj.setMatchCode(matchCode);
            String idempotentId = obj.getIdempotentId();
            try {
                checkScoreRankModel(obj);
                OpenScoreRankResultModel or = addOpenScoreRankInfo(obj, unitCode, matchEndDate);
                items.add(or);
            } catch (Exception ex) {
                logger.error("存储成绩信息失败!报名内容,playerPhone:" + obj.getPlayerPhone() + ",eventName:"
                             + obj.getEventName(),
                    ex);
                OpenScoreRankResultModel or = new OpenScoreRankResultModel();
                or.setIdempotentId(idempotentId);
                or.setPlayerCode(obj.getPlayerCode());
                or.setScoreRankCode(obj.getScoreRankCode());
                or.setPlayerName(obj.getPlayerName());
                or.setPlayerPhone(obj.getPlayerPhone());
                or.setSuccess(2);
                or.setMessage("提交成绩名次信息失败,原因:" + ex.getMessage());

                items.add(or);
            }
        }

        return items;
    }

    /**
     * 校验成绩排名合法
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param obj
     * @return 
     * @throws OpenException 
     */
    private Date checkOpenScoreRankLegal(String unitCode, OpenScoreRankModel obj,
                                         MatchDetailModel unitMatchInfo) throws OpenException {
        if (unitCode.equals("test")) {
            return null;
        }
        if (StringUtils.isBlank(unitCode)) {
            return null;
        }
        if (unitMatchInfo == null) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID,
                String.format("赛事编号错误", obj.getMatchCode()));
        }
        obj.setMatchName(unitMatchInfo.getMatchName());
        obj.setGameCode(unitMatchInfo.getGameCode());
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
     * 检查比赛排名
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param json
     * @return
     * @throws OpenException
     */
    private OpenScoreRankModel parseOpenScoreRanklModel(JSONObject json) throws OpenException {
        OpenScoreRankModel obj = null;
        try {
            obj = JSON.toJavaObject(json, OpenScoreRankModel.class);
        } catch (Exception e) {
            //此处参数错误导致系统异常 安装参数错误处理
            logger.error("err data :{}", JSON.toJSONString(json));
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "data字段中JSON中存在数据类型错误,请检查参数");
        }
        return obj;
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenScoreRankService#queryScoreRankInfo(com.efida.sports.dmp.biz.open.request.ScoreRankQueryRequest)
     */
    @Override
    public List<OpenScoreRankModel> queryScoreRankInfo(ScoreRankQueryRequest qs) throws OpenException,
                                                                                 ParseException {
        SignUtils.checkTimestamp(qs.getTimestamp());
        if (StringUtils.isEmpty(qs.getUnitCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "unitCode不能为空!");
        }

        String privateKey = openConfigService.getPrivateKey(qs.getUnitCode());
        String signData = qs.getUnitCode() + privateKey + qs.getMatchCode() + qs.getEventCode()
                          + qs.getPlayerCode() + qs.getScoreName() + qs.getPlayerPhone()
                          + qs.getPageNumber() + qs.getPageSize() + qs.getTimestamp();
        SignUtils.assertSignTrue(signData, qs.getSign());

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
        List<OpenScoreRankEntity> items = this.openScoreRankEntityMapper.selectByProp(map);
        List<OpenScoreRankModel> newItem = convert2ScoreRankInfo(items);
        return newItem;
    }

    /**
     * 通过开放接口进行报名
     * 
     * @param scoreInfo
     * @param unitCode
     * @return
     * @throws ParseException 
     * @throws OpenException 
     */
    @Transactional
    public OpenScoreRankResultModel addOpenScoreRankInfo(OpenScoreRankModel scoreInfo,
                                                         String unitCode,
                                                         Date matchEndDate) throws ParseException,
                                                                            OpenException {

        if (StringUtils.isEmpty(scoreInfo.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "提交成绩名次信息手机号不能为空!");
        }
        Date endDataTime = null;
        if (matchEndDate != null) {
            endDataTime = new Date(matchEndDate.getTime() + 31L * 86400 * 1000);
        }
        String idempotentId = scoreInfo.getIdempotentId();
        if (StringUtils.isEmpty(idempotentId)) {
            idempotentId = scoreInfo.getEventCode() + "_" + scoreInfo.getPlayerPhone();
            if (StringUtils.isNotEmpty(scoreInfo.getPlayerName())) {
                idempotentId += "_" + scoreInfo.getPlayerName();
            }
            if (idempotentId.length() > 64) {
                idempotentId = idempotentId.substring(0, 64);
            }
            scoreInfo.setIdempotentId(idempotentId);
        }
        boolean isUpdate = false;
        OpenScoreRankEntity old = getOpenScoreRankInfoEntityByIdempotentId(unitCode, idempotentId);
        if (old != null) {
            if (StringUtils.isEmpty(scoreInfo.getModifyFlag())
                || !scoreInfo.getModifyFlag().equals("1")) {
                OpenScoreRankResultModel or = new OpenScoreRankResultModel();
                or.setIdempotentId(idempotentId);
                or.setPlayerCode(old.getPlayerCode());
                or.setScoreRankCode(old.getScoreRankCode());
                or.setPlayerName(scoreInfo.getPlayerName());
                or.setPlayerPhone(scoreInfo.getPlayerPhone());
                or.setSuccess(1);
                or.setMessage("重复提交，上次已经提交成绩名次信息成功,本次提交忽略!");
                return or;
            }
            boolean createExpire = System.currentTimeMillis() / 1000
                                   - old.getGmtCreate().getTime() / 1000 > 86400 * 31;
            boolean endExpire = endDataTime == null
                                || (System.currentTimeMillis() - 31L * 86400 * 1000 > endDataTime
                                    .getTime());

            isUpdate = true;
            if (createExpire && endExpire) {
                OpenScoreRankResultModel or = new OpenScoreRankResultModel();
                or.setIdempotentId(idempotentId);
                or.setPlayerCode(old.getPlayerCode());
                or.setScoreRankCode(old.getScoreRankCode());
                or.setPlayerName(scoreInfo.getPlayerName());
                or.setPlayerPhone(scoreInfo.getPlayerPhone());
                or.setSuccess(1);
                or.setMessage("支持更新，提交时间已经超过比赛结束时间及初次上传时间31日!");
                return or;
            }
        }
        OpenScoreRankEntity oa = createScoreRankInfo(scoreInfo, unitCode);
        if (!isUpdate) {
            final String scoreRankCode = SeqWorkerUtil.generateTimeSequence();
            oa.setScoreRankCode(scoreRankCode);
            this.openScoreRankEntityMapper.insert(oa);
        } else {
            oa.setScoreRankCode(old.getScoreRankCode());
            oa.setId(old.getId());
            this.openScoreRankEntityMapper.updateByPrimaryKey(oa);
        }
        OpenScoreRankResultModel or = new OpenScoreRankResultModel();
        or.setIdempotentId(idempotentId);
        or.setPlayerCode(scoreInfo.getPlayerCode());
        or.setScoreRankCode(oa.getScoreRankCode());
        or.setPlayerName(scoreInfo.getPlayerName());
        or.setPlayerPhone(scoreInfo.getPlayerPhone());
        or.setSuccess(1);
        or.setMessage("提交比赛成绩名次信息成功");
        return or;
    }

    private OpenScoreRankEntity createScoreRankInfo(OpenScoreRankModel sc,
                                                    String unitCode) throws OpenException {

        OpenScoreRankEntity oa = new OpenScoreRankEntity();
        oa.setUnitCode(unitCode);
        oa.setPlayerCode(sc.getPlayerCode());
        oa.setPlayerName(sc.getPlayerName());
        oa.setPlayerPhone(sc.getPlayerPhone());
        oa.setPartCode(sc.getPartCode());
        oa.setPartTime(getDate(sc.getPartTime()));
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
        oa.setScoreName(sc.getScoreName());
        oa.setScore(new BigDecimal(sc.getScore() + ""));
        oa.setScoreUnit(sc.getScoreUnit());
        oa.setScoreDesc(sc.getScoreDesc());
        oa.setRanking(sc.getRanking());
        oa.setPromotion(sc.getPromotion());
        oa.setExtProp(sc.getExtProp() == null ? null : sc.getExtProp().toJSONString());
        oa.setChannelCode(unitCode);
        oa.setIdempotentId(sc.getIdempotentId());
        return oa;
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

    private OpenScoreRankEntity getOpenScoreRankInfoEntityByIdempotentId(String unitCode,
                                                                         String idempotentId) {
        OpenScoreRankEntity openScoreEntity = this.openScoreRankEntityMapper
            .getScoreRankInfoByIdempotent(unitCode, idempotentId);
        return openScoreEntity;
    }

    /**
     * 数据强校验
     * @title
     * @methodName
     * @author lizhiyang
     * @description
     * @param obj
     * @throws OpenException
     */
    private void checkScoreRankModel(OpenScoreRankModel obj) throws OpenException {
        if (StringUtils.isEmpty(obj.getPlayerCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "运动员编号不能为空!");
        }
        if (StringUtils.isEmpty(obj.getPartCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "参赛编号不能为空!");
        }
        if (StringUtils.isEmpty(obj.getPartTime())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "参赛时间不能为空!");
        }
        if (StringUtils.isEmpty(obj.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "运动员手机号playerPhone不能为空!");
        }
        if (StringUtils.isEmpty(obj.getMatchCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "赛事编号matchCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getEventCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "项目细类编号eventCode不能为空!");
        }
    }

    private List<OpenScoreRankModel> convert2ScoreRankInfo(List<OpenScoreRankEntity> items) {
        List<OpenScoreRankModel> newItems = new ArrayList<OpenScoreRankModel>();

        for (OpenScoreRankEntity sc : items) {

            OpenScoreRankModel oa = new OpenScoreRankModel();
            oa.setScoreRankCode(sc.getScoreRankCode());
            oa.setPlayerCode(sc.getPlayerCode());
            oa.setPlayerName(sc.getPlayerName());
            oa.setPlayerPhone(sc.getPlayerPhone());
            oa.setPartCode(sc.getPartCode());
            oa.setPartTime(getDate(sc.getPartTime()));
            oa.setMatchCode(sc.getMatchCode());
            oa.setFieldCode(sc.getFieldCode());
            oa.setFieldName(sc.getFieldName());
            oa.setMatchGroupCode(sc.getMatchGroupCode());
            oa.setMatchGroupName(sc.getMatchGroupName());

            oa.setEventCode(sc.getEventCode());
            oa.setEventName(sc.getEventName());

            oa.setScoreName(sc.getScoreName());
            oa.setScore(sc.getScore().doubleValue());
            oa.setScoreUnit(sc.getScoreUnit());
            oa.setScoreDesc(sc.getScoreDesc());
            oa.setRanking(sc.getRanking());
            oa.setPromotion(sc.getPromotion());
            if (StringUtils.isEmpty(sc.getExtProp())) {
                oa.setExtProp(null);
            } else {
                try {
                    oa.setExtProp(JSONObject.parseObject(sc.getExtProp()));
                } catch (Exception ex) {
                    logger.error("convert extProp to json failed . ", ex);
                }
            }
            oa.setIdempotentId(sc.getIdempotentId());

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

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenScoreRankService#queryScoreRankByIdempotent(java.lang.String, java.lang.String)
     */
    @Override
    public OpenScoreRankEntity queryScoreRankByIdempotent(String unitCode, String idempotentId) {

        OpenScoreRankEntity old = getOpenScoreRankInfoEntityByIdempotentId(unitCode, idempotentId);

        return old;
    }

}
