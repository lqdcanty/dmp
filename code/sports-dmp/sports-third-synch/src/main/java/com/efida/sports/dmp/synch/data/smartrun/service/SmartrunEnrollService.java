package com.efida.sports.dmp.synch.data.smartrun.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.efida.sport.open.OpenApi;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenPlayerModel;
import com.efida.sport.open.result.OpenEnrollxSubmitResult;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.synch.data.common.dao.ZntyConfigDao;
import com.efida.sports.dmp.synch.data.common.dao.entity.EventModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.GroupModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.SiteModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.SynRelation;
import com.efida.sports.dmp.synch.data.smartrun.constants.SmartConstants;
import com.efida.sports.dmp.synch.data.smartrun.dao.SmartrunDao;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.TAdminActLevel;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActAndCustomerExt;
import com.efida.sports.dmp.synch.data.smartrun.dao.entity.ext.TActLevelExt;

/**
 * 
 * 智能定向报名数据同步
 * @author wang yi
 * @desc 
 * @version $Id: SmartrunEnrollService.java, v 0.1 2018年9月4日 下午7:33:01 wang yi Exp $
 */
@Service
public class SmartrunEnrollService {

    @Autowired
    private SmartrunDao    smartTDao;

    @Autowired
    private ZntyConfigDao  zntyDao;

    @Autowired
    private CommonCodeComp smartCodeComp;

    @Value(value = "${openApi}")
    private String         openApi;

    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取需要同步的赛事项目
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return 返回需要同步的赛事信息
     */
    public List<TActLevelExt> readActEventMatch(int actId) {
        //读取承办方数据
        List<TActLevelExt> actLeveExts = smartTDao.getActLevelExt(actId);
        return actLeveExts;
    };

    /**
     * 获取该赛事报名信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public List<TActAndCustomerExt> readCactEnrollInfos(int actId) {
        //setp读取报名数据
        List<TActAndCustomerExt> actLeveExts = smartTDao.getActAndCustomerExt(actId);
        return actLeveExts;
    };

    /**
     * 同步报名数据(多条)
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param datas
     * @param synRelation 
     */
    public void synEnrollInfos(List<TActAndCustomerExt> datas, SynRelation synRelation) {
        //转换数据
        for (TActAndCustomerExt tActAndCustomerExt : datas) {
            try {
                synEnrollInfo(tActAndCustomerExt, synRelation);
            } catch (Exception e) {
                logger.error("同步智能定向数据失败 data:" + JSON.toJSONString(tActAndCustomerExt), e);
            }
        }

    };

    /**
     * 獲取赛事分组详细信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param actLevelId
     * @return
     */
    private TAdminActLevel getActLevel(Integer actLevelId) {
        TAdminActLevel actLevel = smartTDao.getActLevel(actLevelId);
        return actLevel;
    }

    /**
     * 同步报名数据(单条)
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param tac
     * @param synRelation 
     * @throws OpenException 
     */
    public void synEnrollInfo(TActAndCustomerExt tac,
                              SynRelation actRelation) throws OpenException {
        if (tac == null || actRelation == null || StringUtils.isEmpty(tac.getMobile())) {
            return;
        }
        TAdminActLevel actLevel = getActLevel(tac.getActLevelId());

        if (actLevel == null) {
            logger.error("table t_admin_act_level id:{} is not fond ,end syn this data",
                tac.getActLevelId());
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
        SiteModel siteInfo = getSiteInfo(actRelation, tac);
        if (siteInfo != null) {
            fildCode = siteInfo.getFileCode();
            fildName = siteInfo.getFileName();
        }
        GroupModel groupInfo = getGrouInfo(actRelation, tac);
        if (groupInfo != null) {
            groupCode = groupInfo.getGroupCode();
            groupName = groupInfo.getGroupName();
        }
        EventModel eventInfo = getEventInfo(actRelation, tac);
        if (eventInfo != null) {
            eventCode = eventInfo.getEventCode();
            eventName = eventInfo.getEventName();
        }
        if (StringUtils.isEmpty(groupCode)) {
            logger.warn("数据废弃 未找到分组 源数据:{}", JSON.toJSONString(tac));
            return;
        }
        OpenApi api = new OpenApi();
        api.setOpenHost(openApi);
        api.setPrivateKey(SmartConstants.privateKey);
        String unitCode = SmartConstants.UNITCODE;
        api.setUnitCode(unitCode);
        List<OpenEnrollxModel> items = new ArrayList<OpenEnrollxModel>();
        OpenEnrollxModel item = new OpenEnrollxModel();
        item.setApplyTime(DateUtil.format(tac.getCreateTime(), DateUtil.LONG_WEB_FORMAT));
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
        //源数据为分转换为元
        item.setEntryFee(Double.parseDouble((actLevel.getFee() * 100) + ""));
        item.setEventType("personal");

        List<OpenPlayerModel> playerData = new ArrayList<OpenPlayerModel>();
        OpenPlayerModel player = new OpenPlayerModel();
        player.setPlayerPhone(tac.getMobile());
        player.setPlayerCode(
            smartCodeComp.generatePlayerCode(unitCode, tac.getMobile(), tac.getName()));
        player.setPlayerName(tac.getName());
        player.setPlayerAddress(tac.getAddress());
        player.setPlayerHeight(tac.getHeight() == null ? null : Integer.valueOf(tac.getHeight()));
        player.setPlayerWeight(tac.getWeight() == null ? null : Double.valueOf(tac.getWeight()));
        player.setImgUrl(StringUtils.isEmpty(tac.getAvatar()) == true ? null
            : SmartConstants.avatarPrefixUrl + tac.getAvatar());
        player.setPlayerBloodType(tac.getBloodType());
        player.setEmail(tac.getMail());
        if (tac.getSex() == 1) {
            player.setSex("m");
        }
        if (tac.getSex() == 0) {
            player.setSex("f");
        }
        playerData.add(player);
        item.setPlayerData(playerData);
        //处理可多次报名
        item.setIdempotentId(item.getEventCode() + "_" + tac.getCuId() + "_" + tac.getName() + "_"
                             + tac.getMobile());
        items.add(item);

        logger.debug("开始同步智能定向报名数据 数据:{}", JSON.toJSONString(items));

        OpenEnrollxSubmitResult result = api.submitEnrollx(items);

        if (result.getResultCode().equals("200") && StringUtils.isEmpty(result.getMessage())) {

        } else {
            logger.error("提交智能定向报名信息失败 原因:{}", result.getMessage());
        }
    }

    /**
     * 获取比赛项信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     * @param tac
     * @return
     */
    public EventModel getEventInfo(SynRelation synRelation, TActAndCustomerExt tac) {
        if (synRelation == null || tac == null) {
            return null;
        }
        List<EventModel> eventModels = synRelation.getEventModes();
        if (CollectionUtils.isEmpty(eventModels)) {
            return null;
        }
        //赛场对应赛事
        EventModel event = null;
        for (EventModel eventModel : eventModels) {
            if (tac.getActType() == 1 && eventModel.getEventName().contains("短距离")) {
                event = eventModel;
            }
            if (tac.getActType() != 1 && eventModel.getEventName().contains("积分")) {
                event = eventModel;
            }
        }
        return event;
    }

    /**
     * 获取分组信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     * @param tac
     * @return
     */
    public GroupModel getGrouInfo(SynRelation synRelation, TActAndCustomerExt tac) {
        if (synRelation == null || tac == null) {
            return null;
        }
        List<GroupModel> groupModes = synRelation.getGroupModes();
        if (CollectionUtils.isEmpty(groupModes)) {
            return null;
        }
        //赛场对应赛事
        for (GroupModel groupModel : groupModes) {
            if (groupModel.getReGroupid().equals(tac.getActLevelId() + "")) {
                return groupModel;
            }
        }
        return null;
    }

    /**
     * 获取赛场信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     * @param tac
     * @return
     */
    public SiteModel getSiteInfo(SynRelation synRelation, TActAndCustomerExt tac) {
        if (synRelation == null || tac == null) {
            return null;
        }
        List<SiteModel> siteModes = synRelation.getSiteModes();
        if (CollectionUtils.isEmpty(siteModes)) {
            return null;
        }
        //赛场对应赛事
        for (SiteModel siteModel : siteModes) {
            if (siteModel.getReFileId().equals(tac.getActId() + "")) {
                return siteModel;
            }
        }
        return null;
    }

}
