/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.cover;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.facade.model.GoodsOrderModel;
import com.efida.sport.facade.model.SmtPayOrderModel;
import com.efida.sports.dmp.service.template.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.entity.PlayerModel;

import cn.evake.auth.util.DateUtil;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: PlayerCover.java, v 0.1 2018年8月16日 下午1:56:18 wang yi Exp $
 */
public class CommonCover {

    /**
     * 报名信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerModels
     * @return
     */
    public static List<EnrollInfoTemplate> coverToPlayers(List<PlayerModel> playerModels) {
        List<EnrollInfoTemplate> results = new ArrayList<EnrollInfoTemplate>();
        if (CollectionUtils.isEmpty(playerModels)) {
            return results;
        }
        for (PlayerModel playerModel : playerModels) {
            results.add(coverToPlayer(playerModel));
        }
        return results;
    }

    /**
     * 报名信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerModel
     * @return
     */
    private static EnrollInfoTemplate coverToPlayer(PlayerModel playerModel) {
        if (playerModel == null) {
            return null;
        }
        //转换数据
        EnrollInfoTemplate enrollInfoTemplate = new EnrollInfoTemplate();
        BeanUtils.copyProperties(playerModel, enrollInfoTemplate);
        enrollInfoTemplate.setApplyAmountY(playerModel.getApplyAmount());
        return enrollInfoTemplate;
    }

    /**
     * 转换成绩数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param list
     * @return
     */
    public static List<ScoreTemplate> coverToPlayerScores(List<OpenScoreEntity> list) {
        List<ScoreTemplate> results = new ArrayList<ScoreTemplate>();
        if (CollectionUtils.isEmpty(list)) {
            return results;
        }
        for (OpenScoreEntity playerSc : list) {
            results.add(coverToPlayerScore(playerSc));
        }
        return results;
    }

    private static ScoreTemplate coverToPlayerScore(OpenScoreEntity scoreVo) {
        ScoreTemplate op = new ScoreTemplate();
        if (scoreVo == null) {
            return op;
        }
        op.setIdempotentId(scoreVo.getIdempotentId());
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
        op.setOpenPlatType(scoreVo.getOpenPlatType());
        op.setOpenId(scoreVo.getOpenId());
        op.setPartTime(DateUtil.formatDate(scoreVo.getPartTime()));
        op.setScoreName(scoreVo.getScoreName());
        op.setScore(scoreVo.getScore().toString());
        op.setScoreUnit(scoreVo.getScoreUnit());
        op.setScoreDesc(scoreVo.getScoreDesc());
        op.setScoreProp(scoreVo.getScoreProp());
        return op;
    }

    /**
     * 转换成绩排名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param list
     * @return
     */
    public static List<ScoreRankTemplate> coverToPlayerScoreRanks(List<OpenScoreRankEntity> list) {
        List<ScoreRankTemplate> results = new ArrayList<ScoreRankTemplate>();
        if (CollectionUtils.isEmpty(list)) {
            return results;
        }
        for (OpenScoreRankEntity playerSc : list) {
            results.add(coverToPlayerScoreRank(playerSc));
        }
        return results;
    }

    private static ScoreRankTemplate coverToPlayerScoreRank(OpenScoreRankEntity scoreVo) {
        ScoreRankTemplate op = new ScoreRankTemplate();
        op.setIdempotentId(scoreVo.getIdempotentId());
        op.setPlayerCode(scoreVo.getPlayerCode());
        op.setPlayerName(scoreVo.getPlayerName());
        op.setPlayerPhone(scoreVo.getPlayerPhone());
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
        op.setScoreName(scoreVo.getScoreName());
        op.setScore(scoreVo.getScore().toString());
        op.setScoreUnit(scoreVo.getScoreUnit());
        op.setScoreDesc(scoreVo.getScoreDesc());
        op.setRanking(scoreVo.getRanking().toString());
        op.setPromotion(scoreVo.getPromotion());
        op.setExtProp(scoreVo.getExtProp());
        return op;
    }

    public static List<ApplyInfoTeplate> coverToApplyInfos(List<PlayerModel> list) {
        List<ApplyInfoTeplate> results = new ArrayList<ApplyInfoTeplate>();
        if (CollectionUtils.isEmpty(list)) {
            return results;
        }
        for (PlayerModel playerModel : list) {
            results.add(coverToApplyInfo(playerModel));
        }
        return results;
    }

    private static ApplyInfoTeplate coverToApplyInfo(PlayerModel playerModel) {
        ApplyInfoTeplate applyInfoTeplate = new ApplyInfoTeplate();
        if (playerModel != null) {
            BeanUtils.copyProperties(playerModel, applyInfoTeplate);
        }
        return applyInfoTeplate;
    }

    public static List<PlayerTemplate> coverToPlayerInfps(List<PlayerModel> list) {
        List<PlayerTemplate> results = new ArrayList<PlayerTemplate>();
        for (PlayerModel playerModel : list) {
            results.add(coverToPlayerInfp(playerModel));
        }
        return results;
    }

    private static PlayerTemplate coverToPlayerInfp(PlayerModel playerModel) {
        PlayerTemplate playerTemplate = new PlayerTemplate();
        if (playerModel == null) {
            return playerTemplate;
        }
        BeanUtils.copyProperties(playerModel, playerTemplate);
        playerTemplate.setRefIdempotentId(playerModel.getIdempotentId());
        return playerTemplate;
    }

    public static List<SmtPayOrderTemplate> coverSmtPayOrderList(List<SmtPayOrderModel> list) {
        List<SmtPayOrderTemplate> results = new ArrayList<SmtPayOrderTemplate>();
        if (CollectionUtils.isEmpty(list)) {
            return results;
        }
        for (SmtPayOrderModel smtPayOrderModel : list) {
            results.add(coverSmtPayOrder(smtPayOrderModel));
        }
        return results;
    }

    private static SmtPayOrderTemplate coverSmtPayOrder(SmtPayOrderModel smtPayOrderModel) {
        SmtPayOrderTemplate smtPayOrderTemplate = new SmtPayOrderTemplate();
        if (smtPayOrderModel == null) {
            return smtPayOrderTemplate;
        }
        BeanUtils.copyProperties(smtPayOrderModel, smtPayOrderTemplate);
        return smtPayOrderTemplate;
    }

    public static List<GoodsOrderTemplate> coverToGoodsOrderList(List<GoodsOrderModel> list) {
        List<GoodsOrderTemplate> results = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return results;
        }
        for (GoodsOrderModel goodsOrder : list) {
            results.add(coverToGoodsOrder(goodsOrder));
        }
        return results;
    }

    private static GoodsOrderTemplate coverToGoodsOrder(GoodsOrderModel goodsOrder) {
        GoodsOrderTemplate template = new GoodsOrderTemplate();
        if (goodsOrder == null) {
            return template;
        }
        BeanUtils.copyProperties(goodsOrder, template);
        if (null != goodsOrder.getOrderTime())
            template.setOrderTime(DateUtil.sdf.format(goodsOrder.getOrderTime()));
        if (null != goodsOrder.getOptTime())
            template.setOptTime(DateUtil.sdf.format(goodsOrder.getOptTime()));
        String orderStatus = goodsOrder.getOrderStatus();
        if ("wait_pay".equals(orderStatus))
            orderStatus = "待支付";
        if ("success".equals(orderStatus))
            orderStatus = "已支付";
        if ("shipped".equals(orderStatus))
            orderStatus = "已发货";
        if ("completed".equals(orderStatus))
            orderStatus = "已完成";
        template.setOrderStatus(orderStatus);
        return template;
    }

}
