package com.efida.sports.dmp.synch.data.jiubiao.service;

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
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenPlayerModel;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.result.OpenEnrollxSubmitResult;
import com.efida.sport.open.result.OpenScoreSubmitResult;
import com.efida.sport.open.util.DateUtil;
import com.efida.sports.dmp.synch.data.common.dao.entity.SiteModel;
import com.efida.sports.dmp.synch.data.common.dao.entity.SynRelation;
import com.efida.sports.dmp.synch.data.jiubiao.constants.JiubiaoConstants;
import com.efida.sports.dmp.synch.data.jiubiao.dao.JiuBiaoDao;
import com.efida.sports.dmp.synch.data.jiubiao.dao.entity.ShotLastmatch;
import com.efida.sports.dmp.synch.data.jiubiao.dao.entity.ShotMember;
import com.efida.sports.dmp.synch.data.smartrun.service.CommonCodeComp;

/**
 * 
 * 九镖报名数据同步
 * @author wang yi
 * @desc 
 * @version $Id: SmartrunEnrollService.java, v 0.1 2018年9月4日 下午7:33:01 wang yi Exp $
 */
@Service
public class JiuBiaoSynService {

    @Autowired
    private JiuBiaoDao     jiubiaoDao;

    @Autowired
    private CommonCodeComp codeComp;

    @Value(value = "${openApi}")
    private String         openApi;

    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 同步报名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     */
    public void synEnrollxs(SynRelation synRelation) {
        //按照城市同步
        List<SiteModel> siteModels = synRelation.getSiteModes();
        for (SiteModel siteModel : siteModels) {
            synEnrollx(synRelation, siteModel);
        }
    }

    /**
     * 同步城市成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     * @param eventModel
     */
    private void synEnrollx(SynRelation synRelation, SiteModel siteModel) {
        if (synRelation == null || siteModel == null) {
            return;
        }
        List<ShotMember> shortMenbers = jiubiaoDao.getShotApplyMember(siteModel.getReFileId());
        logger.info("开始九镖报名人数数据 总条数:{}", shortMenbers.size());
        int sucess = 0;
        for (ShotMember shotMember : shortMenbers) {
            try {
                boolean fla = synEnrollInfo(shotMember, synRelation, siteModel);
                if (fla) {
                    sucess = sucess + 1;
                }
            } catch (OpenException e) {
                logger.error("同步九镖报名数据失败 数据:{} 城市:{}", shotMember, siteModel, e);
            }
        }
        logger.info("同步九镖报名人数数据 总条数:{} 成功:{}条 失败:{}条", shortMenbers.size(), sucess,
            shortMenbers.size() - sucess);
    }

    /**
     * 同步报名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param tac
     * @param synRelation
     * @param siteModel
     * @throws OpenException
     */
    public boolean synEnrollInfo(ShotMember tac, SynRelation actRelation,
                                 SiteModel siteModel) throws OpenException {
        if (tac == null || actRelation == null) {
            return false;
        }

        OpenApi api = new OpenApi();
        api.setOpenHost(openApi);
        api.setPrivateKey(JiubiaoConstants.privateKey);
        String unitCode = JiubiaoConstants.UNITCODE;
        api.setUnitCode(unitCode);
        List<OpenEnrollxModel> items = new ArrayList<OpenEnrollxModel>();
        OpenEnrollxModel item = new OpenEnrollxModel();
        //报名时间
        item.setApplyTime(DateUtil.format(tac.getCreateDate(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode(actRelation.getGameCode());
        item.setGameName(actRelation.getGameName());
        item.setMatchCode(actRelation.getMatchCode());
        item.setMatchName(actRelation.getMatchName());
        item.setEventCode(actRelation.getEventCode());
        item.setMatchGroupCode(actRelation.getGroupCode());
        item.setMatchGroupName(actRelation.getGroupName());
        item.setEventName(actRelation.getEventCode());
        item.setFieldCode(siteModel.getFileCode());
        item.setFieldName(siteModel.getFileName());
        //源数据为分转换为元
        item.setEntryFee(Double.parseDouble((0 * 100) + ""));
        item.setEventType("personal");

        List<OpenPlayerModel> playerData = new ArrayList<OpenPlayerModel>();
        OpenPlayerModel player = new OpenPlayerModel();
        player.setPlayerPhone(tac.getPhone());
        player.setPlayerCode(generatePlayerCode(unitCode, tac.getId()));
        player.setPlayerName(tac.getNickname());
        player.setPlayerAddress("");
        player.setSex(getJiuBiaoUserSex(tac.getGender()));
        player.setImgUrl(tac.getAvatarUrl());
        playerData.add(player);
        item.setPlayerData(playerData);
        item.setIdempotentId(generatetIdemp("py_", tac.getId()));
        items.add(item);

        logger.debug("开始同步九镖报名数据 数据:{}", JSON.toJSONString(items));

        OpenEnrollxSubmitResult result = api.submitEnrollx(items);

        if (result.getResultCode().equals("200") && StringUtils.isEmpty(result.getMessage())) {

        } else {
            logger.error("提交九镖报名信息失败 原因:{}", result.getMessage());
            return false;
        }
        return true;
    }

    private String generatetIdemp(String prx, String id) {
        return prx + id;
    }

    private String generatePlayerCode(String unitCode, String id) {
        String palyCode = (unitCode + "ply" + id);
        if (palyCode.length() > 20) {
            return palyCode.substring(0, 20);
        }
        return palyCode;
    }

    /**
     * 获取九镖人物性别
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param gender
     * @return
     */
    private String getJiuBiaoUserSex(String gender) {
        String sex = "";
        if (!StringUtils.isEmpty(gender)) {
            if (gender.equals("2")) {
                sex = "f";
            }
            if (gender.equals("1")) {
                sex = "m";
            }
        }
        return sex;
    }

    /**
     * 同步成绩数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     */
    public void synMatchScores(SynRelation synRelation) {
        //按照城市同步
        List<SiteModel> siteModels = synRelation.getSiteModes();
        for (SiteModel siteModel : siteModels) {
            //同步初赛决赛成绩
            synLastMatchScore(synRelation, siteModel);
        }
    }

    /**
     * 同步决赛成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param synRelation
     * @param siteModel
     */
    private void synLastMatchScore(SynRelation synRelation, SiteModel siteModel) {
        if (synRelation == null || siteModel == null) {
            return;
        }
        List<ShotLastmatch> shortMenbers = jiubiaoDao.getLastMatchScore(siteModel.getReFileId());
        logger.info("开始九镖报名决赛成绩数据 总条数:{}", shortMenbers.size());
        for (ShotLastmatch shotMatch : shortMenbers) {
            try {
                synLastMatchScore(shotMatch, synRelation, siteModel);
            } catch (OpenException e) {
                logger.error("同步九镖报名决赛成绩数据失败 数据:{} 城市:{}", shotMatch, siteModel, e);
            }
        }
    }

    /**
     * 同步最终成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param shotMatch
     * @param synRelation
     * @param siteModel
     * @throws OpenException
     */
    private void synLastMatchScore(ShotLastmatch tactd, SynRelation actRelation,
                                   SiteModel siteModel) throws OpenException {
        //成绩数据
        OpenApi api = new OpenApi();
        api.setOpenHost(openApi);
        api.setPrivateKey(JiubiaoConstants.privateKey);
        String unitCode = JiubiaoConstants.UNITCODE;
        api.setUnitCode(unitCode);
        List<OpenScoreModel> items = new ArrayList<OpenScoreModel>();
        OpenScoreModel item = new OpenScoreModel();
        item.setPlayerCode(generatePlayerCode(unitCode, tactd.getId()));
        item.setPlayerName(tactd.getNickname());
        item.setPlayerPhone(tactd.getPhone());
        item.setSex(getJiuBiaoUserSex(tactd.getGender()));
        item.setPartTime(DateUtil.format(tactd.getCreateDate(), DateUtil.LONG_WEB_FORMAT));
        item.setGameCode(actRelation.getGameCode());
        item.setGameName(actRelation.getGameName());
        item.setMatchCode(actRelation.getMatchCode());
        item.setMatchName(actRelation.getMatchName());
        item.setEventCode(actRelation.getEventCode());
        item.setMatchGroupCode(actRelation.getGroupCode());
        item.setMatchGroupName(actRelation.getGroupName());
        item.setEventName(actRelation.getEventName());
        item.setEventCode(actRelation.getEventCode());
        item.setFieldCode(siteModel.getFileCode());
        item.setFieldName(siteModel.getFileName());
        item.setScore(Double.parseDouble(tactd.getBestScore()));
        item.setScoreName(item.getEventName());
        item.setScoreUnit("");
        item.setScoreType("big");
        item.setScoreDesc(item.getScore() + item.getScoreUnit());
        item.setIsValid((byte) 1);
        item.setIdempotentId(generatetIdemp("sc_", tactd.getId()));
        items.add(item);

        logger.debug("开始同步九镖成绩数据 数据:{}", JSON.toJSONString(items));

        OpenScoreSubmitResult result = api.submitScore(items);
        if (result.getResultCode().equals("200") && StringUtils.isEmpty(result.getMessage())) {

        } else {
            logger.error("提交九镖成绩信息失败 原因:{}", result.getMessage());
        }
    }

}
