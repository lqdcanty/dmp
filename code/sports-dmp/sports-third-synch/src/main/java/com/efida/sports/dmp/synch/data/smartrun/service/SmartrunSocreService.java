package com.efida.sports.dmp.synch.data.smartrun.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.efida.sport.open.OpenApi;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.result.OpenScoreSubmitResult;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.synch.data.common.dao.ZntyConfigDao;
import com.efida.sports.dmp.synch.data.common.dao.entity.EventModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.GroupModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.SiteModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.SynRelation;
import com.efida.sports.dmp.synch.data.smartrun.constants.SmartConstants;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActAndCustomerExt;

/**
 * 
 * 智能定向成绩数据同步
 * @author wang yi
 * @desc 
 * @version $Id: SmartrunSocreService.java, v 0.1 2018年9月11日 下午7:33:01 wang yi Exp $
 */
@Service
public class SmartrunSocreService {

    @Autowired
    private ZntyConfigDao         zntyDao;

    @Autowired
    private CommonCodeComp        smartCodeComp;

    @Autowired
    private SmartrunEnrollService enrollservice;

    @Value(value = "${openApi}")
    private String                openApi;

    private Logger                logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 同步成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param enrollInfos 报名成绩数据
     * @param synRelation 
     */
    public void synScores(List<TActAndCustomerExt> enrollInfos, SynRelation synRelation) {
        for (TActAndCustomerExt tactd : enrollInfos) {
            try {
                synScore(tactd, synRelation);
            } catch (Exception e) {
                logger.error("同步智能定向成绩数据失败 data:" + JSON.toJSONString(tactd), e);
            }
        }
    }

    /**
     * 同步成绩数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param tactd
     * @param synRelation 
     * @throws OpenException 
     */
    private void synScore(TActAndCustomerExt tactd, SynRelation actRelation) throws OpenException {
        //判断是否具备成绩
        if (tactd == null || StringUtils.isEmpty(tactd.getMobile())) {
            return;
        }
        //未开始 提前结束
        if (tactd.getStatus() == 0 || tactd.getStatus() == 4) {
            return;
        }
        String fildCode = null;
        String fildName = null;
        String groupCode = null;
        String groupName = null;
        String eventCode = null;
        String eventName = null;
        String gameCode = actRelation.getGameCode();
        String gameName = actRelation.getGameName();
        String matchCode = actRelation.getMatchCode();
        String matchName = actRelation.getMatchName();
        //处理分组以及分赛场数据
        SiteModel siteInfo = enrollservice.getSiteInfo(actRelation, tactd);
        if (siteInfo != null) {
            fildCode = siteInfo.getFileCode();
            fildName = siteInfo.getFileName();
        }
        GroupModel groupInfo = enrollservice.getGrouInfo(actRelation, tactd);
        if (groupInfo != null) {
            groupCode = groupInfo.getGroupCode();
            groupName = groupInfo.getGroupName();
        }
        EventModel eventInfo = enrollservice.getEventInfo(actRelation, tactd);
        if (eventInfo != null) {
            eventCode = eventInfo.getEventCode();
            eventName = eventInfo.getEventName();
        }
        if (StringUtils.isEmpty(groupCode)) {
            logger.warn("数据废弃 未找到分组 源数据:{}", JSON.toJSONString(tactd));
            return;
        }
        //成绩数据
        OpenApi api = new OpenApi();
        api.setOpenHost(openApi);
        api.setPrivateKey(SmartConstants.privateKey);
        String unitCode = SmartConstants.UNITCODE;
        api.setUnitCode(unitCode);
        List<OpenScoreModel> items = new ArrayList<OpenScoreModel>();
        OpenScoreModel item = new OpenScoreModel();
        item.setPlayerCode(smartCodeComp.generatePlayerCode(SmartConstants.UNITCODE,
            tactd.getMobile(), tactd.getName()));
        item.setPlayerName(tactd.getName());
        item.setPlayerPhone(tactd.getMobile());
        if (tactd.getSex() == 1) {
            item.setSex("m");
        }
        if (tactd.getSex() == 0) {
            item.setSex("f");
        }
        item.setPartTime(DateUtil.format(tactd.getCreateTime(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode(gameCode);
        item.setGameName(gameName);
        item.setMatchCode(matchCode);
        item.setMatchName(matchName);
        item.setEventCode(eventCode);
        item.setEventName(eventName);
        item.setMatchGroupCode(groupCode);
        item.setMatchGroupName(groupName);
        item.setFieldCode(fildCode);
        item.setFieldName(fildName);
        item.setScoreName(item.getEventName());
        //成绩
        item.setScore(tactd.getTakeTime());
        item.setScoreUnit("ms");
        item.setScoreType("big");
        item.setScoreDesc(item.getScore() + item.getScoreUnit());
        if (tactd.getIsScoreValid() == 1) {
            item.setIsValid((byte) 1);
        } else {
            item.setIsValid((byte) 0);
        }
        item.setIsValid((byte) 1);
        item.setIdempotentId(item.getEventCode() + "_" + tactd.getCuId() + "_" + tactd.getName()
                             + "_" + tactd.getMobile());
        items.add(item);
        logger.debug("开始同步智能定向成绩数据 数据:{}", JSON.toJSONString(items));
        OpenScoreSubmitResult result = api.submitScore(items);
        if (result.getResultCode().equals("200") && StringUtils.isEmpty(result.getMessage())) {

        } else {
            logger.error("提交智能定向报名成绩信息失败 原因:{}", result.getMessage());
        }
    }

}
