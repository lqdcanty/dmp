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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.efida.sport.dmp.facade.model.EnrollInfoDto;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollResultModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.OpenEnrollService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.biz.open.enms.ApplyStatusEnum;
import com.efida.sports.dmp.biz.open.request.EnrollQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenEnrollInfoEntity;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenEnrollInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenPlayerEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.JsonUtils;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollService.java, v 0.1 2018年5月29日 下午9:05:09 zhiyang Exp $
 */
@Service("openEnrollService")
public class OpenEnrollServiceImpl implements OpenEnrollService {

    private Logger                     logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenConfigService          openConfigService;

    @Autowired
    private SpMatchFacadeClient        spMatchFacadeClient;

    @Autowired
    private OpenEnrollInfoEntityMapper openEnrollInfoEntityMapper;

    @Autowired
    private OpenApplyInfoEntityMapper  openApplyInfoEntityMapper;

    @Autowired
    private OpenPlayerEntityMapper     openPlayerEntityMapper;

    @Autowired
    private CacheService               cacheService;

    @Override
    public List<OpenEnrollResultModel> submitEnrollInfo(String unitCode, int count, String data,
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
        List<OpenEnrollResultModel> items = new ArrayList<OpenEnrollResultModel>();
        for (int i = 0; i < enrollData.size(); i++) {
            JSONObject json = enrollData.getJSONObject(i);
            OpenEnrollModel obj = parseOpenEnrollModel(json);
            //检查提交运动员信息是否完整
            checkEnrollModel(obj);
            //检查提交运动员信息是否合法
            checkEnrollModelLegal(unitCode, obj);
            try {
                OpenEnrollResultModel or = addOpenEnrollInfo(obj, unitCode, channelCode, 1);
                items.add(or);
            } catch (Exception ex) {
                logger.error(String.format("存储报名信息失败!报名内容,playerPhone: %s  ,eventName: %s",
                    obj.getPlayerPhone(), obj.getEventName()), ex);
                OpenEnrollResultModel or = new OpenEnrollResultModel();
                or.setPlayerPhone(obj.getPlayerPhone());
                or.setPlayerName(obj.getPlayerName());
                or.setMatchCode(obj.getMatchCode());
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
    private void checkEnrollModelLegal(String unitCode, OpenEnrollModel obj) throws OpenException {
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
    private OpenEnrollModel parseOpenEnrollModel(JSONObject json) throws OpenException {
        OpenEnrollModel enrollModel = null;
        try {
            enrollModel = JSON.toJavaObject(json, OpenEnrollModel.class);
        } catch (Exception e) {
            //此处参数错误导致系统异常 安装参数错误处理
            logger.error("err data :{}", JSON.toJSONString(json));
            throw new OpenException(ErrorCode.UNKNOW_ERROR, "data字段中JSON中存在数据类型错误,请检查参数");
        }
        return enrollModel;
    }

    private void checkEnrollModel(OpenEnrollModel obj) throws OpenException {
        if (StringUtils.isEmpty(obj.getApplyTime())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "申请报名时间不能为空!");
        }
        if (obj.getEntryFee() == null) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名费不能为空!");
        }
        if (StringUtils.isEmpty(obj.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名运动员手机号playerPhone不能为空!");
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
        if (StringUtils.isEmpty(obj.getFieldCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "报名赛场编号fieldCode不能为空!");
        }

    }

    public OpenEnrollInfoEntity getOpenApplyInfoEntityByIdempotentId(String unitCode,
                                                                     String idempotentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", unitCode);
        map.put("idempotentId", idempotentId);
        return this.openEnrollInfoEntityMapper.selectByIdempotentId(map);
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenEnrollService#addOpenEnrollInfo(com.efida.sport.dmp.facade.model.EnrollInfoDto)
     */
    @Override
    public OpenEnrollResultModel addOpenEnrollInfo(EnrollInfoDto enrollInfo) throws OpenException {

        throw new OpenException(ErrorCode.PARAMETER_INVALID, "该接口已经废弃，请使用新接口同步数据!");

    }

    private String getEnrollIdempotentId(OpenEnrollModel enrollInfo) {

        String idempotentId = enrollInfo.getEventCode() + "_" + enrollInfo.getPlayerPhone();
        if (StringUtils.isNotEmpty(enrollInfo.getPlayerName())) {
            idempotentId += "_" + enrollInfo.getPlayerName();
        } else {
            if (StringUtils.isNotEmpty(enrollInfo.getOpenId())) {
                if (StringUtils.isNotBlank(enrollInfo.getOpenPlatType())) {
                    idempotentId += "_" + enrollInfo.getOpenPlatType();
                }
                idempotentId += enrollInfo.getOpenId();
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
     * @param enrollInfo
     * @param unitCode
     * @param channelCode
     * @param source
     * @return
     * @throws ParseException 
     * @throws OpenException 
     */
    @Transactional
    public OpenEnrollResultModel addOpenEnrollInfo(OpenEnrollModel enrollInfo, String unitCode,
                                                   String channelCode, int source)
                                                                                   throws ParseException,
                                                                                   OpenException {

        if (StringUtils.isEmpty(enrollInfo.getPlayerPhone())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "提交运动员信息报名手机号不能为空!");
        }
        String idempotentId = enrollInfo.getIdempotentId();
        if (StringUtils.isEmpty(idempotentId)) {
            idempotentId = getEnrollIdempotentId(enrollInfo);
            enrollInfo.setIdempotentId(idempotentId);
        }
        if (StringUtils.isEmpty(enrollInfo.getPlayerCategory())) {
            enrollInfo.setPlayerCategory("common");
        }
        OpenEnrollInfoEntity old = getOpenApplyInfoEntityByIdempotentId(unitCode, idempotentId);
        boolean isUpdate = false;
        if (old != null) {
            if (StringUtils.isEmpty(enrollInfo.getModifyFlag())
                || !enrollInfo.getModifyFlag().equals("1")) {
                OpenEnrollResultModel or = new OpenEnrollResultModel();
                or.setPlayerCode(old.getPlayerCode());
                or.setPlayerPhone(enrollInfo.getPlayerPhone());
                or.setPlayerName(enrollInfo.getPlayerName());
                or.setMatchCode(old.getMatchCode());
                or.setIdempotentId(idempotentId);
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("重复提交，上次已经报名成功,本次提交忽略!");
                return or;
            }
            isUpdate = true;
            if (System.currentTimeMillis() / 1000 - old.getGmtCreate().getTime() / 1000 > 86400
                                                                                          * 31) {
                OpenEnrollResultModel or = new OpenEnrollResultModel();
                or.setPlayerCode(old.getPlayerCode());
                or.setPlayerPhone(enrollInfo.getPlayerPhone());
                or.setPlayerName(enrollInfo.getPlayerName());
                or.setMatchCode(old.getMatchCode());
                or.setIdempotentId(idempotentId);
                or.setEventCode(old.getEventCode());
                or.setSuccess(1);
                or.setMessage("不支持更新，提交时间已经超过7日!");
                return or;
            }
        }

        OpenApplyInfoEntity oa = createOpenApplyInfo(enrollInfo, unitCode);
        String applyCode = oa.getApplyCode();
        OpenPlayerEntity player = createOpenPlayerInfo(applyCode, enrollInfo);
        if (old != null) {
            oa.setApplyCode(old.getApplyCode());
            player.setApplyCode(old.getApplyCode());
            player.setPlayerCode(old.getPlayerCode());
            oa.setId(old.getId());
            player.setId(old.getPlayerId());
        }

        oa.setSource((byte) source);
        oa.setChannelCode(channelCode);
        if (source == 1) {
            oa.setSync((byte) 0);
            oa.setSyncTotal(0);
            oa.setNextSyncTime(new Date());
        }

        if (!isUpdate) {
            this.openApplyInfoEntityMapper.insert(oa);
            this.openPlayerEntityMapper.insertSelective(player);

        } else {
            this.openApplyInfoEntityMapper.updateByPrimaryKey(oa);
            this.openPlayerEntityMapper.updateByPrimaryKey(player);
        }

        OpenEnrollResultModel or = new OpenEnrollResultModel();
        or.setPlayerCode(player.getPlayerCode());
        or.setPlayerPhone(player.getPlayerPhone());
        or.setPlayerName(player.getPlayerPhone());
        or.setMatchCode(oa.getMatchCode());
        or.setEventCode(oa.getEventCode());
        or.setSuccess(1);
        or.setIdempotentId(idempotentId);
        or.setMessage("提交报名信息成功");
        return or;
    }

    private OpenPlayerEntity createOpenPlayerInfo(String applyCode,
                                                  OpenEnrollModel item) throws OpenException {

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
        op.setPlayerNo(null);

        return op;
    }

    private OpenApplyInfoEntity createOpenApplyInfo(OpenEnrollModel item,
                                                    String unitCode) throws OpenException {
        OpenApplyInfoEntity op = new OpenApplyInfoEntity();
        op.setUnitCode(unitCode);
        final String applyCode = SeqWorkerUtil.generateTimeSequence();
        op.setEventType("personal");
        op.setRegistrationNum(1);
        op.setApplyAmount((long) (item.getEntryFee() * 100));
        op.setApplyTime(getDate(item.getApplyTime()));
        op.setApplyCode(applyCode);
        op.setApplyStatus(ApplyStatusEnum.SUCCESS.getCode());
        op.setGameCode(item.getGameCode());
        op.setGameName(item.getGameName());
        op.setMatchCode(item.getMatchCode());
        op.setMatchName(item.getMatchName());
        // op.setEventEndTime();
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

    /**
     * 
     * @throws OpenException 
     * @throws ParseException 
     * @see com.efida.sports.dmp.biz.open.OpenEnrollService#queryEnrollInfo(com.efida.sports.dmp.biz.open.request.EnrollQueryRequest)
     */
    @Override
    public List<OpenEnrollModel> queryEnrollInfo(EnrollQueryRequest qs) throws OpenException,
                                                                        ParseException {
        SignUtils.checkTimestamp(qs.getTimestamp());
        if (StringUtils.isEmpty(qs.getUnitCode())) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "unitCode不能为空!");
        }

        String privateKey = openConfigService.getPrivateKey(qs.getUnitCode());
        String signData = qs.getUnitCode() + privateKey + qs.getPlayerCode() + qs.getMatchCode()
                          + qs.getFieldCode() + qs.getMatchGroupCode() + qs.getEventCode()
                          + qs.getPlayerPhone() + qs.getPageNumber() + qs.getPageSize()
                          + qs.getTimestamp();
        SignUtils.assertSignTrue(signData, qs.getSign());
        String cacheKey = getCacheKey(qs);
        List<OpenEnrollModel> obj = (List<OpenEnrollModel>) cacheService.getObj(cacheKey);
        if (obj != null) {
            return obj;
        }
        int start = (qs.getPageNumber() - 1) * qs.getPageSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", qs.getUnitCode());
        map.put("playerCode", qs.getPlayerCode());
        map.put("matchCode", qs.getMatchCode());
        map.put("fieldCode", qs.getFieldCode());
        map.put("matchGroupCode", qs.getMatchGroupCode());
        map.put("eventCode", qs.getEventCode());
        map.put("playerPhone", qs.getPlayerPhone());
        map.put("start", start);
        map.put("limit", qs.getPageSize());

        List<OpenEnrollInfoEntity> items = this.openEnrollInfoEntityMapper.selectByProp(map);

        List<OpenEnrollModel> newItem = convert2EnrollInfo(items);
        cacheService.putObj(cacheKey, newItem, 5);
        return newItem;
    }

    private String getCacheKey(EnrollQueryRequest qs) {

        String key = "dmp" + qs.getUnitCode() + qs.getPlayerCode() + qs.getMatchCode()
                     + qs.getFieldCode() + qs.getMatchCode() + qs.getEventCode()
                     + qs.getPlayerPhone() + qs.getPageNumber() + qs.getPageSize();

        return key;

    }

    private List<OpenEnrollModel> convert2EnrollInfo(List<OpenEnrollInfoEntity> items) {
        List<OpenEnrollModel> newItems = new ArrayList<OpenEnrollModel>();

        for (OpenEnrollInfoEntity item : items) {

            OpenEnrollModel oe = new OpenEnrollModel();

            oe.setEntryFee(item.getApplyAmount() * 1.0 / 100);
            oe.setApplyTime(getDate(item.getApplyTime()));
            oe.setPlayerCode(item.getPlayerCode());

            oe.setGameCode(item.getGameCode());
            oe.setGameName(item.getGameName());
            oe.setMatchCode(item.getMatchCode());
            oe.setMatchName(item.getMatchName());
            // oa.setEventEndTime();
            // oa.setEventName(enrollInfo);
            //oa.setEventStartTime(itemInfo.getStartTime());
            oe.setFieldCode(item.getFieldCode());
            oe.setFieldName(item.getFieldName());

            oe.setMatchGroupCode(item.getMatchGroupCode());
            oe.setMatchGroupName(item.getMatchGroupName());

            oe.setEventCode(item.getEventCode());
            oe.setEventName(item.getEventName());
            oe.setIdempotentId(item.getIdempotentId());

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
            oe.setIdempotentId(item.getIdempotentId());
            if (StringUtils.isEmpty(item.getExtProp())) {
                oe.setExtProp(null);
            } else {
                try {
                    oe.setExtProp(JSONObject.parseObject(item.getExtProp()));
                } catch (Exception ex) {
                    logger.error("convert to json failed . ", ex);
                }
            }
            newItems.add(oe);
        }
        return newItems;
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

    /**
     * 获取用户唯一标识
     * 
     * @param enrollInfo
     * @return
     */
    public String getUserId(OpenEnrollInfoEntity enrollInfo) {

        String idempotentId = enrollInfo.getPlayerPhone();
        if (idempotentId == null) {
            idempotentId = "";
        }
        if (StringUtils.isNotEmpty(enrollInfo.getPlayerName())) {
            idempotentId += "_" + enrollInfo.getPlayerName();
        } else {
            if (StringUtils.isNotEmpty(enrollInfo.getOpenId())) {
                if (StringUtils.isNotBlank(enrollInfo.getOpenPlatType())) {
                    idempotentId += "_" + enrollInfo.getOpenPlatType();
                }
                idempotentId += enrollInfo.getOpenId();
            }
        }
        if (idempotentId.length() > 64) {
            idempotentId = idempotentId.substring(0, 64);
        }

        return idempotentId;
    }

    /**
     * 返回的是 手机号+姓名(或者openType+openId)
     * @see com.efida.sports.dmp.biz.open.OpenEnrollService#loadOurEnrollInfoByEvent(java.lang.String, int)
     */
    @Override
    public Set<String> loadOurEnrollInfoByEvent(String eventCode, int maxNumber) {

        int pageNumber = 1;
        int pageSize = 500;
        Set<String> set = new HashSet<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("eventCode", eventCode);
        map.put("source", 0);
        map.put("limit", pageSize);
        while (true) {
            map.put("start", (pageNumber - 1) * pageSize);
            List<OpenEnrollInfoEntity> items = this.openEnrollInfoEntityMapper.selectByProp(map);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            for (OpenEnrollInfoEntity item : items) {
                set.add(getUserId(item));
            }

            if (set.size() >= maxNumber) {
                break;
            }
            if (items.size() < pageSize) {
                break;
            }
            pageNumber++;
        }

        return set;
    }

}
