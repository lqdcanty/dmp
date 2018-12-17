/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.efida.sport.dmp.facade.model.EnrollxInfoDto;
import com.efida.sport.dmp.facade.model.PlayerInfoDto;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenEnrollxResultModel;
import com.efida.sport.open.model.OpenPlayerModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.OpenEnrollxService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.biz.open.enms.ApplyStatusEnum;
import com.efida.sports.dmp.biz.open.request.EnrollxQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.JsonUtils;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollxServiceImpl.java, v 0.1 2018年7月26日 下午11:20:18 zhiyang Exp $
 */
@Service("openEnrollxService")
public class OpenEnrollxServiceImpl implements OpenEnrollxService {

    private Logger                    logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenConfigService         openConfigService;

    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;

    @Autowired
    private OpenApplyInfoEntityMapper openApplyInfoEntityMapper;

    @Autowired
    private OpenPlayerEntityMapper    openPlayerEntityMapper;

    /**
     * 
     * 
     * @param enrollInfo
     * @return
     */
    private String getEnrollIdempotentId(OpenEnrollxModel enrollInfo) {

        OpenPlayerModel player = enrollInfo.getPlayerData().get(0);
        String idempotentId = enrollInfo.getEventCode() + "_" + player.getPlayerPhone();
        if (StringUtils.isNotEmpty(player.getPlayerName())) {
            idempotentId += "_" + player.getPlayerName();
        } else {
            if (StringUtils.isNotEmpty(player.getOpenId())) {
                if (StringUtils.isNotBlank(player.getOpenPlatType())) {
                    idempotentId += "_" + player.getOpenPlatType();
                }
                idempotentId += player.getOpenId();
            }
        }
        if (idempotentId.length() > 64) {
            idempotentId = idempotentId.substring(0, 64);
        }

        return idempotentId;
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenEnrollxService#addOpenEnrollxInfo(com.efida.sport.open.model.OpenEnrollxModel, java.lang.String, java.lang.String, int)
     */
    @Override
    public OpenEnrollxResultModel addOpenEnrollxInfo(OpenEnrollxModel enrollInfo, String unitCode,
                                                     String channelCode,
                                                     int source) throws ParseException,
                                                                 OpenException {

        if (CollectionUtils.isEmpty(enrollInfo.getPlayerData())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "运动员信息不能为空，至少需要包含一条运动员信息!");
        }
        String idempotentId = enrollInfo.getIdempotentId();
        if (StringUtils.isEmpty(idempotentId)) {
            idempotentId = getEnrollIdempotentId(enrollInfo);
            enrollInfo.setIdempotentId(idempotentId);
        }

        OpenApplyInfoEntity old = getOpenApplyInfoEntityByIdempotentId(unitCode, idempotentId);
        boolean isUpdate = false;
        if (old != null) {
            if (enrollInfo.getModifyFlag() == null || !enrollInfo.getModifyFlag().equals("1")) {
                OpenEnrollxResultModel or = new OpenEnrollxResultModel();
                or.setApplayCode(old.getApplyCode());
                or.setIdempotentId(idempotentId);
                or.setMatchCode(old.getMatchCode());
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("重复提交，上次已经报名成功,本次提交忽略!");
                return or;
            }
            isUpdate = true;
            if (System.currentTimeMillis() / 1000 - old.getGmtCreate().getTime() / 1000 > 86400
                                                                                          * 31) {
                OpenEnrollxResultModel or = new OpenEnrollxResultModel();
                or.setApplayCode(old.getApplyCode());
                or.setMatchCode(old.getMatchCode());
                or.setIdempotentId(idempotentId);
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("不支持更新，提交时间已经超过31日!");
                return or;
            }
        }

        OpenApplyInfoEntity oa = createOpenApplyInfo(enrollInfo, unitCode);
        oa.setSource((byte) source);
        if (source == 1) {
            oa.setSync((byte) 0);
            oa.setNextSyncTime(new Date());
            oa.setSyncTotal(0);
        }
        oa.setChannelCode(channelCode);
        enrollInfo.setApplyCode(oa.getApplyCode());
        if (old != null) {
            enrollInfo.setApplyCode(old.getApplyCode());
            oa.setApplyCode(old.getApplyCode());
        }
        List<OpenPlayerEntity> players = createOpenPlayerInfo(oa.getApplyCode(),
            enrollInfo.getPlayerData());
        //处理特殊数据
        handleApplyInfo(oa, players, unitCode);
        if (!isUpdate) {
            saveNewApplyInfo(oa, players);
        } else {
            oa.setIdempotentId(idempotentId);
            oa.setId(old.getId());
            oa.setApplyCode(old.getApplyCode());
            updateApplyInfo(oa, players);
        }

        OpenEnrollxResultModel or = new OpenEnrollxResultModel();
        or.setIdempotentId(idempotentId);
        or.setApplayCode(oa.getApplyCode());
        or.setMatchCode(oa.getMatchCode());
        or.setFieldCode(oa.getFieldCode());
        or.setEventCode(oa.getEventCode());
        or.setSuccess(1);
        or.setMessage("提交报名信息成功");

        return or;
    }

    /**
     * 处理特殊数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param oa
     * @param players 
     * @param unitCode
     */
    private void handleApplyInfo(OpenApplyInfoEntity oa, List<OpenPlayerEntity> players,
                                 String unitCode) {
        if (oa == null || CollectionUtils.isEmpty(players)) {
            return;
        }
        //九镖电话空数据处理
        if (unitCode.equals("jiubiao") && StringUtils.isBlank(players.get(0).getPlayerPhone())) {
            oa.setSync((byte) 2);
        }
    }

    private List<OpenPlayerEntity> createOpenPlayerInfo(String applyCode,
                                                        List<OpenPlayerModel> playerDatas) throws OpenException {

        List<OpenPlayerEntity> players = new ArrayList<OpenPlayerEntity>();
        if (CollectionUtils.isEmpty(playerDatas)) {
            return players;
        }
        for (OpenPlayerModel item : playerDatas) {

            OpenPlayerEntity op = new OpenPlayerEntity();
            String playerCode = SeqWorkerUtil.generateTimeSequence();
            op.setApplyCode(applyCode);
            op.setPlayerCode(playerCode);
            op.setPlayerPhone(item.getPlayerPhone());
            op.setPlayerName(item.getPlayerName());
            op.setSex(item.getSex());
            op.setImgUrl(item.getImgUrl());
            op.setEmail(item.getEmail());
            op.setPlayerHeight(item.getPlayerHeight());
            op.setPlayerWeight(item.getPlayerWeight());
            op.setPlayerBirth(getDate(item.getPlayerBirth()));
            op.setPlayerNationality(item.getPlayerNationality());
            op.setPlayerWorkUnit(item.getPlayerWorkUnit());
            op.setPlayerAddress(item.getPlayerAddress());
            if (item.getPlayerCerType() == null) {
                op.setPlayerCerType(null);
            } else {
                op.setPlayerCerType("" + item.getPlayerCerType());
            }
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
            op.setExtProp(item.getExtProp() == null ? null : item.getExtProp().toJSONString());
            op.setIsDelete((byte) 0);
            op.setPlayerNo(item.getPlayerNo());
            players.add(op);
        }

        return players;
    }

    /**
     * 
     * 
     * @see com.efida.sports.dmp.biz.open.OpenEnrollxService#submitEnrollInfo(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<OpenEnrollxResultModel> submitEnrollInfo(String unitCode, int count, String data,
                                                         String timestamp, String sign,
                                                         String ipAddress,
                                                         String channelCode) throws OpenException,
                                                                             ParseException {
        SignUtils.checkTimestamp(timestamp);
        if (count < 1) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条报名信息，count必须大于0!");
        }
        if (StringUtils.isEmpty(data)) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "至少提交1条报名信息，data不能为空!");
        }
        if (logger.isInfoEnabled()) {
            logger.info("data:" + data);
        }
        if (!JsonUtils.isJSONArray(data)) {
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "数据格式错误 data传输数据需为数组!");
        }
        JSONArray enrollData = JSONObject.parseArray(data);
        if (enrollData.size() != count) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "count与提交报名数据data中记录数不一致!");
        }
        String privateKey = openConfigService.getPrivateKey(unitCode);
        String signData = unitCode + privateKey + timestamp;
        SignUtils.assertSignTrue(signData, sign);
        List<OpenEnrollxResultModel> items = new ArrayList<OpenEnrollxResultModel>();
        for (int i = 0; i < enrollData.size(); i++) {
            JSONObject json = enrollData.getJSONObject(i);
            OpenEnrollxModel obj = parseOpenEnrollxModel(json);
            try {

                //检查提交运动员信息是否完整
                checkEnrollModel(obj);
                //检查提交运动员信息是否合法
                checkEnrollModelLegal(unitCode, obj);
                OpenEnrollxResultModel or = addOpenEnrollxInfo(obj, unitCode, channelCode, 1);
                items.add(or);
            } catch (Exception ex) {
                logger.error(String.format("存储报名信息失败!报名内容,playerPhone: %s  ,eventName: %s",
                    obj.getPlayerData().get(0).getPlayerPhone(), obj.getEventName()), ex);
                OpenEnrollxResultModel or = new OpenEnrollxResultModel();

                or.setIdempotentId(obj.getIdempotentId());
                or.setMatchCode(obj.getMatchCode());
                or.setFieldCode(obj.getFieldCode());
                or.setEventCode(obj.getEventCode());
                or.setSuccess(2);
                or.setMessage("提交报名信息失败,原因:" + ex.getMessage());
                items.add(or);
            }
        }
        return items;

    }

    /**
     * 检查运动员是否合法
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param obj
     * @throws OpenException 
     */
    private void checkEnrollModelLegal(String unitCode, OpenEnrollxModel obj) throws OpenException {
        if (unitCode.equals("test")) {
            return;
        }
        if (StringUtils.isBlank(unitCode)) {
            return;
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
    }

    /**
     *数据转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param json
     * @return
     * @throws OpenException
     */
    private OpenEnrollxModel parseOpenEnrollxModel(JSONObject json) throws OpenException {
        OpenEnrollxModel enrollModel = null;
        try {
            enrollModel = JSON.toJavaObject(json, OpenEnrollxModel.class);
        } catch (Exception e) {
            //此处参数错误导致系统异常 安装参数错误处理
            logger.error("err data :{}", JSON.toJSONString(json));
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "data字段中JSON中存在数据类型错误,请检查参数");
        }
        return enrollModel;
    }

    private void checkEnrollModel(OpenEnrollxModel obj) throws OpenException {
        if (StringUtils.isEmpty(obj.getApplyTime())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "申请报名时间不能为空!");
        }
        if (obj.getEntryFee() == null) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名费不能为空!");
        }

        if (obj.getPlayerData() == null || obj.getPlayerData().size() < 1) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名信息必须包含运动员信息!");
        }

        if (StringUtils.isEmpty(obj.getGameCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名项目编号gameCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getMatchCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名赛事编号matchCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getEventCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名项目细类编号eventCode不能为空!");
        }
        if (StringUtils.isEmpty(obj.getEventType())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "比赛类型eventType不能为空!");
        }
        if (obj.getEventType().equals("group")) {
            if (StringUtils.isBlank(obj.getLeaderName())) {
                throw new OpenException(ErrorCode.PARAMETER_INVALID, "领队名称leaderName不能为空!");
            }
            if (StringUtils.isBlank(obj.getLeaderPhone())) {
                throw new OpenException(ErrorCode.PARAMETER_INVALID, "领队电话leaderPhone不能为空!");
            }
        }

    }

    public OpenApplyInfoEntity getOpenApplyInfoEntityByIdempotentId(String unitCode,
                                                                    String idempotentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", unitCode);
        map.put("idempotentId", idempotentId);
        return this.openApplyInfoEntityMapper.selectByIdempotentId(map);
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenEnrollService#addOpenEnrollxInfo(com.efida.sport.dmp.facade.model.EnrollInfoDto)
     */
    @Override
    public OpenEnrollxResultModel addOpenEnrollInfo(EnrollxInfoDto enrollInfo) throws OpenException {

        String idempotentId = enrollInfo.getIdempotentId();
        if (StringUtils.isEmpty(idempotentId)) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "idempotentId不能为空!");
        }

        OpenApplyInfoEntity old = getOpenApplyInfoEntityByIdempotentId(enrollInfo.getUnitCode(),
            idempotentId);
        boolean isUpdate = false;
        if (old != null) {
            if (enrollInfo.isModifyFlag()) {
                OpenEnrollxResultModel or = new OpenEnrollxResultModel();
                or.setApplayCode(old.getApplyCode());
                or.setIdempotentId(idempotentId);
                or.setMatchCode(old.getMatchCode());
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("重复提交，上次已经报名成功,本次提交忽略!");
                return or;
            }
            isUpdate = true;
            if (System.currentTimeMillis() / 1000 - old.getGmtCreate().getTime() / 1000 > 86400
                                                                                          * 31) {
                OpenEnrollxResultModel or = new OpenEnrollxResultModel();
                or.setApplayCode(old.getApplyCode());
                or.setMatchCode(old.getMatchCode());
                or.setIdempotentId(idempotentId);
                or.setFieldCode(old.getFieldCode());
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("不支持更新，提交时间已经超过31日!");
                return or;
            }
        }

        OpenApplyInfoEntity oa = createOpenApplyInfo(enrollInfo);
        enrollInfo.setApplyCode(oa.getApplyCode());
        if (old != null) {
            enrollInfo.setApplyCode(old.getApplyCode());
            oa.setApplyCode(old.getApplyCode());
        }
        List<OpenPlayerEntity> players = createOpenPlayerInfo(enrollInfo);
        if (!isUpdate) {

            saveNewApplyInfo(oa, players);
        } else {
            oa.setIdempotentId(idempotentId);
            oa.setId(old.getId());
            oa.setApplyCode(old.getApplyCode());
            updateApplyInfo(oa, players);
        }

        OpenEnrollxResultModel or = new OpenEnrollxResultModel();
        or.setIdempotentId(idempotentId);
        or.setApplayCode(oa.getApplyCode());
        or.setMatchCode(oa.getMatchCode());
        or.setFieldCode(oa.getFieldCode());
        or.setEventCode(oa.getEventCode());
        or.setSuccess(1);
        or.setMessage("提交报名信息成功");
        return or;
    }

    private OpenApplyInfoEntity createOpenApplyInfo(EnrollxInfoDto item) throws OpenException {

        OpenApplyInfoEntity op = new OpenApplyInfoEntity();
        op.setUnitCode(item.getUnitCode());
        final String applyCode = SeqWorkerUtil.generateTimeSequence();

        op.setEventType(item.getEventType());
        op.setLeaderName(item.getLeaderName());
        op.setLeaderPhone(item.getLeaderPhone());
        op.setTeamName(item.getTeamName());
        op.setRegistrationNum(item.getRegistrationNum());
        op.setApplyAmount((long) (item.getEntryFee() * 100));
        op.setApplyTime(getDate(item.getApplyTime()));
        op.setApplyCode(applyCode);
        op.setApplyStatus(ApplyStatusEnum.SUCCESS.getCode());
        op.setGameCode(item.getGameCode());
        op.setGameName(item.getGameName());
        op.setMatchCode(item.getMatchCode());
        op.setMatchName(item.getMatchName());
        op.setEventStartTime(item.getEventStartTime());
        op.setEventEndTime(item.getEventEndTime());
        op.setFieldCode(item.getFieldCode());
        op.setFieldName(item.getFieldName());
        op.setMatchGroupCode(item.getMatchGroupCode());
        op.setMatchGroupName(item.getMatchGroupName());
        op.setEventCode(item.getEventCode());
        op.setEventName(item.getEventName());

        op.setIsDelet((byte) 0);
        op.setIdempotentId(item.getIdempotentId());

        op.setSource(item.getSource());
        op.setChannelCode(item.getChannelCode());
        if (item.getSource() == 1) {
            op.setSync((byte) 0);
            op.setSyncTotal(0);
            op.setNextSyncTime(new Date());
        }

        Date time = Calendar.getInstance().getTime();
        op.setGmtCreate(time);
        op.setGmtModified(time);
        return op;
    }

    private List<OpenPlayerEntity> createOpenPlayerInfo(EnrollxInfoDto enrollInfo) throws OpenException {

        List<OpenPlayerEntity> players = new ArrayList<OpenPlayerEntity>();

        List<PlayerInfoDto> playerDatas = enrollInfo.getPlayerData();
        if (CollectionUtils.isEmpty(playerDatas)) {
            return players;
        }
        String applyCode = enrollInfo.getApplyCode();
        for (PlayerInfoDto item : playerDatas) {

            OpenPlayerEntity op = new OpenPlayerEntity();
            String playerCode = SeqWorkerUtil.generateTimeSequence();

            op.setApplyCode(applyCode);
            op.setPlayerCode(playerCode);

            op.setPlayerPhone(item.getPlayerPhone());
            op.setPlayerName(item.getPlayerName());
            op.setSex(item.getSex());
            op.setImgUrl(item.getImgUrl());
            op.setEmail(item.getEmail());
            op.setPlayerHeight(item.getPlayerHeight());
            op.setPlayerWeight(item.getPlayerWeight());
            op.setPlayerBirth(getDate(item.getPlayerBirth()));
            op.setPlayerNationality(item.getPlayerNationality());
            op.setPlayerWorkUnit(item.getPlayerWorkUnit());
            op.setPlayerAddress(item.getPlayerAddress());
            if (item.getPlayerCerType() == null) {
                op.setPlayerCerType(null);
            } else {
                op.setPlayerCerType("" + item.getPlayerCerType());
            }
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
            op.setExtProp(item.getExtProp() == null ? null : item.getExtProp().toJSONString());
            op.setIsDelete((byte) 0);
            op.setTerminal(enrollInfo.getChannelCode());
            op.setIsClean((byte) 0);
            op.setPlayerNo(item.getPlayerNo());
            players.add(op);
        }

        return players;
    }

    @Transactional
    private void updateApplyInfo(OpenApplyInfoEntity oa, List<OpenPlayerEntity> players) {

        this.openApplyInfoEntityMapper.updateByPrimaryKey(oa);
        updatePlayerInfo(oa.getApplyCode(), players);
        /*        this.openPlayerEntityMapper.deleteByApplyCode(oa.getApplyCode());
        for (OpenPlayerEntity player : players) {
            this.openPlayerEntityMapper.insert(player);
        }*/
    }

    /**
     * 在原有记录基础上进行更新
     * 
     * @param applyCode
     * @param players
     */
    @Transactional
    private void updatePlayerInfo(String applyCode, List<OpenPlayerEntity> players) {

        List<OpenPlayerEntity> oldPlayers = this.openPlayerEntityMapper
            .selectPlayerByApplyCode(applyCode);
        if (CollectionUtils.isEmpty(oldPlayers)) {

            for (OpenPlayerEntity player : players) {
                this.openPlayerEntityMapper.insert(player);
            }
            return;
        }

        if (CollectionUtils.isEmpty(players)) {
            for (OpenPlayerEntity op : oldPlayers) {
                op.setIsDelete((byte) 1);
                this.openPlayerEntityMapper.updateByPrimaryKey(op);
            }

            return;
        }

        int oldNum = oldPlayers.size();
        for (int i = 0; i < players.size(); i++) {

            OpenPlayerEntity player = players.get(i);
            if (i < oldNum) {
                OpenPlayerEntity op = oldPlayers.get(i);
                player.setId(op.getId());
                player.setIsDelete((byte) 0);
                player.setPlayerCode(op.getPlayerCode());
                this.openPlayerEntityMapper.updateByPrimaryKey(op);
            } else {
                player.setIsDelete((byte) 0);
                this.openPlayerEntityMapper.insert(player);
            }
        }

        if (oldNum > players.size()) {
            for (int i = players.size(); i < oldPlayers.size(); i++) {
                OpenPlayerEntity op = oldPlayers.get(i);
                op.setIsDelete((byte) 1);
                this.openPlayerEntityMapper.updateByPrimaryKey(op);
            }

        }
    }

    @Transactional
    private void saveNewApplyInfo(OpenApplyInfoEntity oa, List<OpenPlayerEntity> players) {

        this.openApplyInfoEntityMapper.insert(oa);
        for (OpenPlayerEntity player : players) {
            this.openPlayerEntityMapper.insert(player);
        }

    }

    private List<OpenPlayerEntity> createOpenPlayer(OpenEnrollxModel enrollInfo,
                                                    String unitCode) throws OpenException {

        List<OpenPlayerEntity> players = new ArrayList<OpenPlayerEntity>();

        List<OpenPlayerModel> playerDatas = enrollInfo.getPlayerData();
        if (CollectionUtils.isEmpty(playerDatas)) {
            return players;
        }
        String applyCode = enrollInfo.getApplyCode();
        for (OpenPlayerModel item : playerDatas) {

            OpenPlayerEntity op = new OpenPlayerEntity();
            op.setApplyCode(applyCode);
            String playerCode = SeqWorkerUtil.generateTimeSequence();
            op.setPlayerCode(playerCode);

            op.setPlayerPhone(item.getPlayerPhone());
            op.setPlayerName(item.getPlayerName());
            op.setSex(item.getSex());
            op.setImgUrl(item.getImgUrl());
            op.setEmail(item.getEmail());
            op.setPlayerHeight(item.getPlayerHeight());
            op.setPlayerWeight(item.getPlayerWeight());
            op.setPlayerBirth(getDate(item.getPlayerBirth()));
            op.setPlayerNationality(item.getPlayerNationality());
            op.setPlayerWorkUnit(item.getPlayerWorkUnit());
            op.setPlayerAddress(item.getPlayerAddress());
            if (item.getPlayerCerType() == null) {
                op.setPlayerCerType(null);
            } else {
                op.setPlayerCerType("" + item.getPlayerCerType());
            }
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
            op.setExtProp(item.getExtProp() == null ? null : item.getExtProp().toJSONString());
            op.setIsDelete((byte) 0);
            op.setPlayerNo(item.getPlayerNo());
            players.add(op);
        }
        return players;
    }

    private OpenApplyInfoEntity createOpenApplyInfo(OpenEnrollxModel item,
                                                    String unitCode) throws OpenException {

        OpenApplyInfoEntity op = new OpenApplyInfoEntity();
        op.setUnitCode(unitCode);
        final String applyCode = SeqWorkerUtil.generateTimeSequence();

        op.setEventType(item.getEventType());
        op.setLeaderName(item.getLeaderName());
        op.setLeaderPhone(item.getLeaderPhone());
        op.setTeamName(item.getTeamName());
        op.setRegistrationNum(item.getRegistrationNum());
        op.setApplyAmount((long) (item.getEntryFee() * 100));
        op.setApplyTime(getDate(item.getApplyTime()));
        op.setApplyCode(applyCode);
        op.setApplyStatus(ApplyStatusEnum.SUCCESS.getCode());
        op.setGameCode(item.getGameCode());
        op.setGameName(item.getGameName());
        op.setMatchCode(item.getMatchCode());
        op.setMatchName(item.getMatchName());
        // op.setEventEndTime();
        // op.setEventName(enrollInfo);
        //op.setEventStartTime(itemInfo.getStartTime());
        op.setFieldCode(item.getFieldCode());
        op.setFieldName(item.getFieldName());
        op.setMatchGroupCode(item.getMatchGroupCode());
        op.setMatchGroupName(item.getMatchGroupName());
        op.setEventCode(item.getEventCode());
        op.setEventName(item.getEventName());

        op.setIsDelet((byte) 0);
        op.setIdempotentId(item.getIdempotentId());
        Date time = Calendar.getInstance().getTime();
        op.setGmtCreate(time);
        op.setGmtModified(time);
        return op;
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

    @Override
    public List<OpenEnrollxModel> queryEnrollInfo(EnrollxQueryRequest qs) throws OpenException,
                                                                          ParseException {
        SignUtils.checkTimestamp(qs.getTimestamp());
        if (StringUtils.isEmpty(qs.getUnitCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "unitCode不能为空!");
        }

        String privateKey = openConfigService.getPrivateKey(qs.getUnitCode());
        String signData = qs.getUnitCode() + privateKey + qs.getApplyCode() + qs.getMatchCode()
                          + qs.getFieldCode() + qs.getMatchGroupCode() + qs.getEventCode()
                          + qs.getLeaderPhone() + qs.getPlayerPhone() + qs.getPageNumber()
                          + qs.getPageSize() + qs.getTimestamp();
        SignUtils.assertSignTrue(signData, qs.getSign());

        List<OpenApplyInfoEntity> items = queryOpenApplyInfo(qs);
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList<OpenEnrollxModel>();
        }
        List<String> applyCodes = new ArrayList<String>();
        for (OpenApplyInfoEntity item : items) {
            applyCodes.add(item.getApplyCode());
        }
        List<OpenPlayerEntity> ops = queryPlayers(applyCodes);
        Map<String, List<OpenPlayerModel>> omap = new HashMap<String, List<OpenPlayerModel>>();
        for (OpenPlayerEntity op : ops) {
            OpenPlayerModel opm = convert2opm(op);
            List<OpenPlayerModel> players = omap.get(op.getApplyCode());
            if (players == null) {
                players = new ArrayList<OpenPlayerModel>();
                omap.put(op.getApplyCode(), players);
            }
            players.add(opm);

        }

        List<OpenEnrollxModel> reList = new ArrayList<OpenEnrollxModel>();
        for (OpenApplyInfoEntity item : items) {
            OpenEnrollxModel ro = convert2oem(item);
            ro.setPlayerData(omap.get(item.getApplyCode()));
            reList.add(ro);
        }

        return reList;
    }

    private OpenEnrollxModel convert2oem(OpenApplyInfoEntity item) {

        OpenEnrollxModel op = new OpenEnrollxModel();
        op.setEventType(item.getEventType());
        op.setLeaderName(item.getLeaderName());
        op.setLeaderPhone(item.getLeaderPhone());
        op.setTeamName(item.getTeamName());
        op.setRegistrationNum(item.getRegistrationNum());
        double amount = item.getApplyAmount() * 1.0 / 100;
        op.setEntryFee(amount);
        op.setApplyTime(getDate(item.getApplyTime()));
        op.setApplyCode(item.getApplyCode());
        op.setGameCode(item.getGameCode());
        op.setGameName(item.getGameName());
        op.setMatchCode(item.getMatchCode());
        op.setMatchName(item.getMatchName());
        op.setFieldCode(item.getFieldCode());
        op.setFieldName(item.getFieldName());
        op.setMatchGroupCode(item.getMatchGroupCode());
        op.setMatchGroupName(item.getMatchGroupName());
        op.setEventCode(item.getEventCode());
        op.setEventName(item.getEventName());
        op.setIdempotentId(item.getIdempotentId());

        return op;
    }

    private OpenPlayerModel convert2opm(OpenPlayerEntity item) {
        OpenPlayerModel oe = new OpenPlayerModel();
        oe.setApplyCode(item.getApplyCode());
        oe.setPlayerCode(item.getPlayerCode());
        oe.setPlayerNo(item.getPlayerNo());
        oe.setPlayerPhone(item.getPlayerPhone());
        oe.setPlayerName(item.getPlayerName());
        oe.setSex(item.getSex());
        oe.setImgUrl(item.getImgUrl());
        oe.setEmail(item.getEmail());
        oe.setPlayerHeight(item.getPlayerHeight());
        oe.setPlayerWeight(item.getPlayerWeight());
        oe.setPlayerBirth(getDate(item.getPlayerBirth()));
        oe.setPlayerNationality(item.getPlayerNationality());
        oe.setPlayerWorkUnit(item.getPlayerWorkUnit());

        oe.setPlayerAddress(item.getPlayerAddress());
        if (!StringUtils.isEmpty(item.getPlayerCerType())) {
            oe.setPlayerCerType(Integer.parseInt(item.getPlayerCerType()));
        }
        oe.setPlayerCerNo(item.getPlayerCerNo());
        oe.setPlayerBloodType(item.getPlayerBloodType());

        oe.setPlayerNation(item.getPlayerNation());
        oe.setPlayerClothSize(item.getPlayerClothSize());
        oe.setPlayerEmergencyName(item.getPlayerEmergencyName());
        oe.setPlayerEmergencyPhone(item.getPlayerEmergencyPhone());
        oe.setImgUrl(item.getImgUrl());
        oe.setPlayerCategory(item.getPlayerCategory());
        oe.setOpenPlatType(item.getOpenPlatType());
        oe.setOpenId(item.getOpenId());
        if (StringUtils.isEmpty(item.getExtProp())) {
            oe.setExtProp(null);
        } else {
            try {
                oe.setExtProp(JSONObject.parseObject(item.getExtProp()));
            } catch (Exception ex) {
                logger.error("convert to json failed . ", ex);
            }
        }
        return oe;
    }

    private List<OpenPlayerEntity> queryPlayers(List<String> applyCodes) {

        return this.openPlayerEntityMapper.selectByApplyCodes(applyCodes);
    }

    private List<OpenApplyInfoEntity> queryOpenApplyInfo(EnrollxQueryRequest qs) {

        int start = (qs.getPageNumber() - 1) * qs.getPageSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", qs.getUnitCode());
        map.put("applyCode", qs.getApplyCode());
        map.put("matchCode", qs.getMatchCode());
        map.put("fieldCode", qs.getFieldCode());
        map.put("matchGroupCode", qs.getMatchGroupCode());
        map.put("eventCode", qs.getEventCode());
        map.put("leaderPhone", qs.getLeaderPhone());
        map.put("playerPhone", qs.getPlayerPhone());
        map.put("start", start);
        map.put("limit", qs.getPageSize());

        List<OpenApplyInfoEntity> players = null;
        if (!StringUtils.isEmpty(qs.getPlayerPhone())) {
            players = this.openApplyInfoEntityMapper.selectOpenApplyByPropHavePlayerPhone(map);
        } else {
            players = this.openApplyInfoEntityMapper.selectOpenApplyByProp(map);
        }

        return players;
    }

    private String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return DateUtil.format(date, DateUtil.LONG_WEB_FORMAT);
    }

    public static void main(String[] args) {

        OpenEnrollModel oem = new OpenEnrollModel();
        oem.setApplyTime(DateUtil.format(new Date(), DateUtil.LONG_WEB_FORMAT));
        oem.setEmail("lzyang1012@qq.com");
        oem.setEventCode("test eventcode2");
        oem.setPlayerPhone("18628380221");
        oem.setGameCode("testgamecode");
        oem.setMatchCode("testmatchcode");
        oem.setPlayerWorkUnit("畅元国讯");
        oem.setEntryFee(38.52);
        Object json = JSON.toJSON(oem);
        System.out.println(json.toString());
        String str = "[" + json.toString() + "]";//java.net.URLEncoder.encode(json.toString());
        System.out.println(str);
    }

    @Override
    public OpenPlayerEntity getPlayer(String playerCode) {
        OpenPlayerEntity ope = openPlayerEntityMapper.selectByplayerNo(playerCode);
        return ope;
    }

}
