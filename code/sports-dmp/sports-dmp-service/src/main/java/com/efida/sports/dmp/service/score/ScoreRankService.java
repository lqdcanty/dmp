/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.score;

import java.util.Date;
import java.util.List;

import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: ScoreRankService.java, v 0.1 2018年7月19日 下午3:48:41 wang yi Exp $
 */
public interface ScoreRankService {

    /**
     * 获取比赛排名
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param match
     * @param playerPhone
     * @param playerName
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PagingResult<OpenScoreRankEntity> getScoreRankWithUser(boolean allowData,
                                                           UserInfoModel userInfo, String unitCode,
                                                           String socreType, String match,
                                                           String playerPhone, String playerName,
                                                           String startTime, String endTime,
                                                           String channelCode, String sortField,
                                                           String sortOrder, int pageNumber,
                                                           int pageSize);

    /**
     * 获取一个比赛成绩
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param idempotent_id
     * @return
     */
    OpenScoreRankEntity getScoreRank(String unitCode, String idempotentId);

    /**
     * 查询所有有成绩的赛事信息
     * web系统调用
     * @param gameCode  比赛编号
     * @param phoneNum  电话号码
     * @param currentPage 当前页数
     * @param pageSzie   每页大小
     * @return
     */
    PagingResult<String> queryHasScoreMatch(String gameCode, String phoneNum, int currentPage,
                                            int pageSize);

    /**
     * 根据赛事编号和手机号号码查询排名信息
     * web系统调用
     * @param matchCode
     * @param phoneNum
     * @return
     */
    List<OpenScoreRankEntity> getAllRankByMatchAndPhone(String matchCode, String phoneNum);

    /**
     * 根据比赛对象查询排名数据, 最多返回10000条数据。
     * 
     * @param competitionEntity
     * @param phoneNum 电话号码
     * @return
     */
    List<OpenScoreRankEntity> getRanksByCompetition(CompetitionEntity competitionEntity,
                                                    String phoneNum);

    /**
     * 获取比赛时间 
     * 
     * @param CompetitionEntity competitionEntity,
     * @param phoneNum
     * @return
     */
    List<Date> queyCompetitionDate(CompetitionEntity competitionEntity, String phoneNum);

    /**
      * 通过 比赛编号查询排名数据。 仅用于自定义排名数据。
      * 
      * @param competitionCode
      * @return
      */
    List<OpenScoreRankEntity> querySelfRankByCompetition(String competitionCode, String phoneNum,
                                                         int currentPage, int pageSize);

    /**
     * 根据比赛信息查询排名数据
     * 
     * @param competition
     * @param competitionDate
     * @param phoneNum
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<OpenScoreRankEntity> queryPageRankByCompetition(CompetitionEntity competition,
                                                                 Date competitionDate,
                                                                 String phoneNum, int currentPage,
                                                                 int pageSize);

    /**
     * 根据排名编号获取排名数据
     * 
     * @param rankCode
     * @return
     */
    OpenScoreRankEntity getScoreRankByRankCode(String rankCode);

    /**
     * 查询赛事排名
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param competitionCode
     * @param matchCode
     * @param groups
     * @param areas
     * @param events
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PagingResult<OpenScoreRankEntity> queryCompetitionScoreRank(String competitionCode,
                                                                String matchCode,
                                                                List<String> groups,
                                                                List<String> areas,
                                                                List<String> events, int pageNumber,
                                                                int pageSize);

    /**
     * 通过比赛配置编号删除 自动 排名数据。 
     * 
     * @param cometitionCode 比赛编号
     * @return
     */
    public int deleteAutoRankInfoByCompetitionCode(String cometitionCode);

    /**
     * 根据比赛编码查询有成绩的赛场信息
     *
     * @param competitionCode 比赛编号
     * @return
     */
    public List<OpenScoreRankEntity> selectCompetitionInfoByScore(String competitionCode);

}
