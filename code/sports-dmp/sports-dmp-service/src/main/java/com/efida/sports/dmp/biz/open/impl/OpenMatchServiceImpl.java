/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.admin.facade.model.open.MatchAwardModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.enums.MatchStatusEnum;
import com.efida.sport.open.model.OpenEventTypeModel;
import com.efida.sport.open.model.OpenGroupTypeModel;
import com.efida.sport.open.model.OpenMatchAwardModel;
import com.efida.sport.open.model.OpenMatchModel;
import com.efida.sport.open.model.OpenPlayingAreaModel;
import com.efida.sport.open.result.ErrorCode;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.OpenMatchService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenMatchServiceImpl.java, v 0.1 2018年5月29日 下午3:39:44 zhiyang Exp $
 */
@Service("openMatchService")
public class OpenMatchServiceImpl implements OpenMatchService {

    private Logger              logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SpMatchFacadeClient spMatchFacadeClient;

    @Autowired
    private OpenConfigService   openConfigService;

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.OpenMatchService#queryMatchList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String)
     */
    public List<OpenMatchModel> queryMatchList(String unitCode, String matchCode,
                                               String matchStatus, String beginTimeMin,
                                               String beginTimeMax, String endTimeMin,
                                               String endTimeMax, int pageNumber, int pageSize,
                                               String timestamp,
                                               String sign) throws OpenException, ParseException {

        SignUtils.checkTimestamp(timestamp);
        if (!StringUtils.isEmpty(matchStatus)) {
            int statusInt = 0;
            try {
                statusInt = Integer.parseInt(matchStatus);
            } catch (Exception ex) {
                throw new OpenException(ErrorCode.PARAMETER_INVALID, "matchStatus必须为空或者数字!");
            }

            MatchStatusEnum mse = MatchStatusEnum.getEnumByCode(Integer.parseInt(matchStatus));
            if (mse == null) {
                throw new OpenException(ErrorCode.PARAMETER_INVALID, "matchStatus类型错误!");
            }
        }
        Date beginDateMin = null;
        if (!StringUtils.isEmpty(beginTimeMin)) {

            beginDateMin = DateUtil.parse(beginTimeMin, DateUtil.LONG_WEB_FORMAT);
        }
        Date beginDateMax = null;
        if (!StringUtils.isEmpty(beginTimeMax)) {

            beginDateMax = DateUtil.parse(beginTimeMax, DateUtil.LONG_WEB_FORMAT);
        }
        Date endDateMin = null;
        if (!StringUtils.isEmpty(endTimeMin)) {

            endDateMin = DateUtil.parse(endTimeMin, DateUtil.LONG_WEB_FORMAT);
        }
        Date endDateMax = null;
        if (!StringUtils.isEmpty(beginTimeMax)) {
            endDateMax = DateUtil.parse(endTimeMax, DateUtil.LONG_WEB_FORMAT);
        }

        if (pageSize > 20) {
            throw new OpenException(ErrorCode.PARAMETER_INVALID, "pageSize取值太大，单页最多请求20条记录!");
        }
        String privateKey = openConfigService.getPrivateKey(unitCode);
        String signData = unitCode + privateKey + matchCode + matchStatus + beginTimeMin
                          + beginTimeMax + endTimeMin + endTimeMax + pageNumber + pageSize
                          + timestamp;
        SignUtils.assertSignTrue(signData, sign);
        List<MatchDetailModel> detailMatches = spMatchFacadeClient.queryMatches(matchCode,
            matchStatus, beginDateMin, beginDateMax, endDateMin, endDateMax, pageNumber, pageSize);
        List<OpenMatchModel> items = convert2MatchModel(detailMatches);

        return items;
    }

    /**
     * 
     * 
     * @param details
     * @return
     */
    private List<OpenMatchModel> convert2MatchModel(List<MatchDetailModel> details) {

        List<OpenMatchModel> items = new ArrayList<OpenMatchModel>();
        if (details == null) {
            return items;
        }
        for (MatchDetailModel detail : details) {
            OpenMatchModel item = new OpenMatchModel();
            item.setGameCode(detail.getGameCode());
            item.setMatchCode(detail.getMatchCode());
            item.setMatchName(detail.getMatchName());
            item.setMatchService(detail.getMatchService());
            item.setImgUrl(detail.getImgUrl());
            item.setBeginTime(getDateStr(detail.getBeginTime()));
            item.setEndTime(getDateStr(detail.getEndTime()));
            if (detail.getMatchSize() == null) {
                item.setMatchSize(0);
            } else {
                item.setMatchSize(detail.getMatchSize());
            }
            item.setRegTime(getDateStr(detail.getRegTime()));
            item.setMatchIntro(detail.getMatchIntro());
            List<OpenPlayingAreaModel> fieldTypeList = convert2AreadModel(
                detail.getFieldTypeList());
            item.setFieldType(fieldTypeList);
            List<OpenGroupTypeModel> groupTypeList = convert2GroupTypeList(
                detail.getGroupTypeList());
            item.setGroupType(groupTypeList);
            List<OpenEventTypeModel> eventTypeList = convert2EventTypeList(
                detail.getEventTypeList());
            item.setEventType(eventTypeList);
            item.setMatchStatus(detail.getMatchStatus());
            item.setMatchType(detail.getMatchType());
            List<OpenMatchAwardModel> awardList = convert2AwardModel(detail.getMatchAwardList());
            item.setMatchAward(awardList);

            items.add(item);
        }
        return items;
    }

    private List<OpenEventTypeModel> convert2EventTypeList(List<EventTypeModel> details) {

        List<OpenEventTypeModel> items = new ArrayList<OpenEventTypeModel>();
        if (details == null) {
            return items;
        }
        for (EventTypeModel detail : details) {

            OpenEventTypeModel item = new OpenEventTypeModel();
            item.setEventCode(detail.getEventCode());
            item.setEventName(detail.getEventName());
            item.setEventNumber(detail.getEventNumber());
            item.setEventTime(getDateStr(detail.getEventTime()));
            item.setPersonLimit(detail.getPersonLimit());
            item.setEntryFee(detail.getEntryFee());
            item.setFieldCode(detail.getFieldCode());
            item.setGroupCode(detail.getGroupCode());

            items.add(item);
        }
        return items;
    }

    private List<OpenGroupTypeModel> convert2GroupTypeList(List<GroupTypeModel> details) {

        List<OpenGroupTypeModel> items = new ArrayList<OpenGroupTypeModel>();
        if (details == null) {
            return items;
        }
        for (GroupTypeModel detail : details) {
            OpenGroupTypeModel item = new OpenGroupTypeModel();
            item.setGroupCode(detail.getGroupCode());
            item.setGroupName(detail.getGroupName());
            item.setGroupNumber(detail.getGroupNumber());
            item.setFieldCode(detail.getFieldCode());
            items.add(item);
        }
        return items;
    }

    private List<OpenPlayingAreaModel> convert2AreadModel(List<FieldTypeModel> details) {

        List<OpenPlayingAreaModel> items = new ArrayList<OpenPlayingAreaModel>();
        if (details == null) {
            return items;
        }
        for (FieldTypeModel detail : details) {
            OpenPlayingAreaModel item = new OpenPlayingAreaModel();
            item.setFieldCode(detail.getFieldCode());
            item.setFieldName(detail.getFieldName());
            item.setFieldAddress(detail.getFieldAddress());
            item.setFieldTime(getDateStr(detail.getFieldTime()));
            items.add(item);
        }
        return items;
    }

    private List<OpenMatchAwardModel> convert2AwardModel(List<MatchAwardModel> details) {

        List<OpenMatchAwardModel> items = new ArrayList<OpenMatchAwardModel>();
        if (details == null) {
            return items;
        }
        for (MatchAwardModel detail : details) {

            OpenMatchAwardModel item = new OpenMatchAwardModel();
            item.setAwardName(detail.getAwardName());
            item.setAwardImg(detail.getAwardImg());
            items.add(item);
        }

        return items;
    }

    private String getDateStr(Date date) {

        return DateUtil.format(date, DateUtil.LONG_WEB_FORMAT);

    }
}
