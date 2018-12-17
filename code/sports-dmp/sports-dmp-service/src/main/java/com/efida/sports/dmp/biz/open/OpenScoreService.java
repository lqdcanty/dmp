/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.model.OpenScoreResultModel;
import com.efida.sports.dmp.biz.open.request.ScoreQueryRequest;
import com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreService.java, v 0.1 2018年6月21日 下午4:37:59 zhiyang Exp $
 */
public interface OpenScoreService {

    /**
     * 提交 比赛成绩信息
     * 
     * @param unitCode  承办方编号
     * @param count  json数据格式
     * @param data
     * @param timestamp
     * @param sign
     * @param ipAddress
     * @return
     * @throws ParseException 
     * @throws OpenException 
     */
    List<OpenScoreResultModel> submitScoreInfo(String unitCode, int count, String data,
                                               String timestamp, String sign,
                                               String ipAddress) throws OpenException,
                                                                 ParseException;

    /**
     * 
     * 
     * @param qr
     * @return
     * @throws OpenException 
     * @throws ParseException 
     */
    List<OpenScoreModel> queryScoreInfo(ScoreQueryRequest qr) throws OpenException, ParseException;

    /**
     * 条件查询 分数列表
     * 
     * @param qs
     * @return
     */
    public List<OpenScoreEntity> queryScoreList(ScoreQueryRequest qs);

    /**
     * 查询分页运动员信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param match
     * @param playerName
     * @param playerPhone
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    PagingResult<OpenScoreEntity> getPagePlayerLikeParams(boolean allowData, UserInfoModel userInfo,
                                                          String unitCode, String match,
                                                          String playerName, String playerPhone,
                                                          String valid, Integer syncStatus,
                                                          String startTime, String endTime,
                                                          String channelCode, String sortField,
                                                          String sortOrder, int page, int limit);

    /**
     * 查询远动员成绩
     * 
     * @param playerPhone 电话号码
     * @param eventCode   比赛项编号
     * @return
     */
    List<OpenScoreEntity> queryByPlayerPhoneAndEventCode(String playerPhone, String eventCode);

    /**
     * 根据比赛获取用户最好的成绩
     * 
     * @param competition
     * @param phoneNum
     * @return
     */
    OpenScoreEntity getRegisterBestScore(CompetitionEntity competition, String phoneNum);

    /**
     * 查询某用户某场比赛的最好成绩记录
     * 
     * matchCode, phoneNum, eventCode为空则返回空。 
     * 
     * @param matchCode
     * @param fieldCode
     * @param groupCode
     * @param eventCode
     * @param phoneNum
     * @return
     * @throws Exception
     */
    public OpenScoreEntity getUserBestScore(String matchCode, String fieldCode, String groupCode,
                                            String eventCode, String phoneNum);

    /**
     * 根据比赛查询成绩列表
     * 
     * @param competition
     * @param phoneNum
     * @return
     */
    List<OpenScoreEntity> queryRegisterScores(CompetitionEntity competition, String phoneNum);

    /**
     * 根据比赛查询分页查询
     * 
     * @param competition
     * @param phoneNum
     * @return
     */
    PagingResult<OpenScoreEntity> queryRegisterScores(CompetitionEntity competition,
                                                      String phoneNum, int currentPage,
                                                      int pageSize);

    /**
     * 通过比赛配置查询符合条件成绩信息
     * 
     * @param qs
     * @return
     */
    List<OpenScoreEntity> queryScoreList(SearchCompetionScoreRequest qs);

}
