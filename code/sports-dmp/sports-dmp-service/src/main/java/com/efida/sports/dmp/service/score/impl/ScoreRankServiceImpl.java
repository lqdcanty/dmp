/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.score.impl;

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

import com.alibaba.fastjson.JSONObject;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.dao.mapper.OpenScoreRankEntityMapper;
import com.efida.sports.dmp.enums.RankTypeEnum;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.score.ScoreRankService;
import com.efida.sports.dmp.service.user.UserUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: ScoreRankServiceImpl.java, v 0.1 2018年7月19日 下午3:49:54 wang yi Exp $
 */
@Service
public class ScoreRankServiceImpl implements ScoreRankService {

    private static Logger             logger = LoggerFactory.getLogger(ScoreRankServiceImpl.class);

    @Autowired
    private OpenScoreRankEntityMapper scoreRankMapper;

    @Autowired
    private UserUnitService           userUnitService;

    @Override
    public PagingResult<OpenScoreRankEntity> getScoreRankWithUser(boolean allowData,
                                                                  UserInfoModel userInfo,
                                                                  String unitCode, String socreType,
                                                                  String match, String playerPhone,
                                                                  String playerName,
                                                                  String startTime, String endTime,
                                                                  String channelCode,
                                                                  String sortField,
                                                                  String sortOrder, int pageNumber,
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
            return new PagingResult<OpenScoreRankEntity>(new ArrayList<OpenScoreRankEntity>(), 0l,
                pageNumber, pageSize);
        }
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("unitCodes", unitCodes);
        queryParams.put("match", match);
        queryParams.put("playerPhone", playerPhone);
        queryParams.put("playerName", playerName);
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);
        queryParams.put("channelCode", channelCode);
        queryParams.put("socreType", socreType);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        List<OpenScoreRankEntity> ranks = scoreRankMapper.selectScoreRankByLikeParams(queryParams);
        PageInfo<OpenScoreRankEntity> pageInfo = new PageInfo<OpenScoreRankEntity>(ranks);
        return new PagingResult<OpenScoreRankEntity>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    @Override
    public OpenScoreRankEntity getScoreRank(String unitCode, String idempotentId) {
        OpenScoreRankEntity scoreRank = null;
        if (StringUtils.isBlank(unitCode)) {
            scoreRank = scoreRankMapper.getScoreRankByIdempId(idempotentId);
        } else {
            scoreRank = scoreRankMapper.getScoreRankInfoByIdempotent(unitCode, idempotentId);
        }
        return scoreRank;
    }

    @Override
    public PagingResult<String> queryHasScoreMatch(String gameCode, String phoneNum,
                                                   int currentPage, int pageSzie) {

        PageHelper.startPage(currentPage, pageSzie);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("gameCode", gameCode);
        queryParams.put("playerPhone", phoneNum);
        List<String> matchCodes = scoreRankMapper.queryHasScoreMatch(queryParams);
        PageInfo<String> pageInfo = new PageInfo<String>(matchCodes);
        return new PagingResult<String>(pageInfo.getList(), pageInfo.getTotal(), currentPage,
            pageSzie);
    }

    @Override
    public List<OpenScoreRankEntity> getAllRankByMatchAndPhone(String matchCode, String phoneNum) {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("matchCode", matchCode);
        queryParams.put("playerPhone", phoneNum);
        List<OpenScoreRankEntity> scoreRankEntity = scoreRankMapper
            .getAllRankByMatchAndPhone(queryParams);

        return scoreRankEntity;
    }

    @Override
    public List<OpenScoreRankEntity> getRanksByCompetition(CompetitionEntity competition,
                                                           String phoneNum) {
        if (competition == null) {
            return null;
        }
        Map<String, Object> queryParams = getQueryParas(competition, phoneNum);

        List<OpenScoreRankEntity> list = scoreRankMapper.querySoreByMap(queryParams);

        return list;
    }

    @Override
    public List<Date> queyCompetitionDate(CompetitionEntity competition, String phoneNum) {

        if (competition == null) {
            return null;
        }
        //        Map<String, Object> queryParams = new HashMap<String, Object>();
        //        String matchCode = competition.getMatchCode();
        //        queryParams.put("matchCode", matchCode);
        //        queryParams.put("playerPhone", phoneNum);
        //        String groups = competition.getGroups();
        //        if (StringUtils.isNotBlank(groups)) {
        //            List<String> groupCodes = JSONObject.parseArray(groups, String.class);//把字符串转换成集合
        //            queryParams.put("groups", groupCodes);
        //        }
        //        String events = competition.getEvents();
        //        if (StringUtils.isNotBlank(events)) {
        //            List<String> eventCodes = JSONObject.parseArray(events, String.class);//把字符串转换成集合
        //            queryParams.put("events", eventCodes);
        //        }
        //        String areas = competition.getAreas();
        //        if (StringUtils.isNotBlank(areas)) {
        //            List<String> areaCodes = JSONObject.parseArray(areas, String.class);//把字符串转换成集合
        //            queryParams.put("areas", areaCodes);
        //        }
        //        List<Date> list = scoreRankMapper.queryDateSoreByMap(queryParams);
        return null;
    }

    /**
     * 约定生成查询条件。 
     * 
     * @param competition
     * @param phoneNum
     * @return
     */
    private Map<String, Object> getQueryParas(CompetitionEntity competition, String phoneNum) {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("playerPhone", phoneNum);
        String matchCode = competition.getMatchCode();
        queryParams.put("matchCode", matchCode);
        RankTypeEnum rt = RankTypeEnum.getEnumByCode(competition.getRankType());
        //官网排名则直接通过 competitionCode进行查询;
        if (rt == RankTypeEnum.dmp) {
            queryParams.put("competitionCode", competition.getCompetitionCode());
        } else {
            queryParams.put("rankType", "unit");
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
        }

        return queryParams;

    }

    /**
     * 通过 比赛编号查询排名数据。 仅用于自定义排名数据。
     * 
     * @param competitionCode
     * @return
     */
    public List<OpenScoreRankEntity> querySelfRankByCompetition(String competitionCode,
                                                                String phoneNum, int currentPage,
                                                                int pageSize) {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("playerPhone", phoneNum);
        queryParams.put("competitionCode", competitionCode);
        queryParams.put("start", (currentPage - 1) * pageSize);
        queryParams.put("limit", pageSize);
        List<OpenScoreRankEntity> list = scoreRankMapper.querySelfRankByMap(queryParams);
        return list;
    }

    /**
     *
     * @see com.efida.sports.dmp.service.score.ScoreRankService#queryPageRankByCompetition(com.efida.sports.dmp.dao.entity.CompetitionEntity, java.util.Date, java.lang.String, int, int)
     */
    @Override
    public PagingResult<OpenScoreRankEntity> queryPageRankByCompetition(CompetitionEntity competition,
                                                                        Date competitionDate,
                                                                        String phoneNum,
                                                                        int currentPage,
                                                                        int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> queryParams = getQueryParas(competition, phoneNum);
        List<OpenScoreRankEntity> list = scoreRankMapper.querySoreByMap(queryParams);
        PageInfo<OpenScoreRankEntity> pageInfo = new PageInfo<OpenScoreRankEntity>(list);
        return new PagingResult<OpenScoreRankEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);
    }

    @Override
    public OpenScoreRankEntity getScoreRankByRankCode(String rankCode) {
        return scoreRankMapper.getScoreRankByRankCode(rankCode);
    }

    @Override
    public PagingResult<OpenScoreRankEntity> queryCompetitionScoreRank(String competitionCode,
                                                                       String matchCode,
                                                                       List<String> groups,
                                                                       List<String> areas,
                                                                       List<String> events,
                                                                       int pageNumber,
                                                                       int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("competitionCode", competitionCode);
        queryParams.put("matchCode", matchCode);
        queryParams.put("groups", groups);
        queryParams.put("areas", areas);
        queryParams.put("events", events);
        List<OpenScoreRankEntity> list = scoreRankMapper.queryCompetitionScoreRank(queryParams);
        PageInfo<OpenScoreRankEntity> pageInfo = new PageInfo<OpenScoreRankEntity>(list);
        return new PagingResult<OpenScoreRankEntity>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    /**
     * 
     * @see com.efida.sports.dmp.service.score.ScoreRankService#deleteAutoRankInfoByCompetitionCode(java.lang.String)
     */
    @Override
    public int deleteAutoRankInfoByCompetitionCode(String competitionCode) {

        if (StringUtils.isEmpty(competitionCode)) {
            logger.warn("deleteAutoRankInfoByCompetitionCode, competitionCode can't be null !");
            return 0;
        }
        logger.info(
            "start call deleteAutoRankInfoByCompetitionCode, competitionCode: " + competitionCode);
        int num = this.scoreRankMapper.deleteByCompetitionCode(competitionCode);
        logger.info("end call deleteAutoRankInfoByCompetitionCode, competitionCode: "
                    + competitionCode + ", relate number:" + num);

        return num;
    }

    @Override
    public List<OpenScoreRankEntity> selectCompetitionInfoByScore(String competitionCode) {

        return scoreRankMapper.selectCompetitionInfoByScore(competitionCode);
    }

}
