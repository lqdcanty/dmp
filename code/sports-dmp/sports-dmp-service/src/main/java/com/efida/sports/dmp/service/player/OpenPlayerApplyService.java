/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.player;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;
import com.efida.sports.dmp.dao.entity.PlayerModel;
import com.efida.sports.dmp.service.template.ApplyInfoTeplate;
import com.efida.sports.dmp.service.template.EnrollInfoTemplate;
import com.efida.sports.dmp.service.template.PlayerTemplate;
import com.efida.sports.dmp.service.template.ScoreRankTemplate;
import com.efida.sports.dmp.service.template.ScoreTemplate;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 运动员报名信息Service
 * @author wang yi
 * @desc 
 * @version $Id: OpenPlayerService.java, v 0.1 2018年6月21日 下午7:45:13 wang yi Exp $
 */
public interface OpenPlayerApplyService {

    /**
     * 获取运动员报名详细
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerCode
     * @param unitCode
     * @return
     */
    PlayerModel selectByPlayerCodeAndUnitCode(String playerCode, String unitCode);

    /**
     * 将模板导入数据转换为Vo数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param templateJson
     * @return
     */
    List<EnrollInfoTemplate> parseEnrollInfoExcelVos(List<Map<String, Object>> templateJson);

    /**
     * 转换成Excel驼峰表头
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param map
     * @return
     */
    Map<String, Object> parseCamelTitle(Map<String, Object> map);

    /**
     * 提交报名信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param excelVos
     */
    void submitEnrollInfoExcelVos(String unitCode, List<EnrollInfoTemplate> excelVos);

    /**
     * 断言运动员报名信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sheet
     * @return 错误信息
     */
    List<String> assertEnrollInfos(List<EnrollInfoTemplate> sheet);

    /**
     * 断言报名信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param applySheet
     * @return
     */
    List<String> assertApplayInfos(List<ApplyInfoTeplate> applySheet);

    /**
     * 断言运动信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerSheet
     * @return
     */
    List<String> assertPlayerInfos(List<PlayerTemplate> playerSheet);

    /**
     * 提交团体报名信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param applyVos
     * @param playerVos
     */
    void submitTdApplayExcelVos(String uuid, List<ApplyInfoTeplate> applyVos,
                                List<PlayerTemplate> playerVos);

    /**
     * 报名信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param applySheet
     * @return
     */
    List<ApplyInfoTeplate> parseApplyExcelVos(List<Map<String, Object>> applySheet);

    /**
     * 运动员信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerSheet
     * @return
     */
    List<PlayerTemplate> parsePlayerExcelVos(List<Map<String, Object>> playerSheet);

    /**
     * 断言成绩信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scores
     * @return
     */
    List<String> assertScoreTemplates(String unitCode, List<ScoreTemplate> scores);

    /**
     * 清除空数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sheet
     * @return
     */
    List<Map<String, Object>> removeEnpty(List<Map<String, Object>> sheet);

    /**
    * 提交导入后成绩信息
    * @title
    * @methodName
    * @author wangyi
    * @description
    * @param unitCode 承办方编号
    * @param scores 成绩数据
    */
    String submitTdScores(String unitCode, List<ScoreTemplate> scores);

    /**
     * 断言成绩排名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scores
     * @return
     */
    List<String> assertScoreRankTemplates(String unitCode, List<ScoreRankTemplate> scores);

    /**
     * 提交成绩排名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userName
     * @param scores
     */
    void submitTdScoreRanks(String userName, List<ScoreRankTemplate> scores);

    /**
     * 查询未清算的运动员信息
     * 
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<OpenPlayerEntity> selectUnCleanPlayer(int currentPage, int pageSize);

    /**
     * 修改清洗状态为1
     * 
     * @param player
     */
    void updatePlayerCleanStatus(OpenPlayerEntity player);

    /**
     * 分页查询所有运动员信息
     * 
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<OpenPlayerEntity> selectPlayers(int currentPage, int pageSize);

    /**
     *  查询总运动员数
     * 
     * @return
     */
    long getApplyTotal();

    /**
     * 获取关联到用户的报名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userInfo 用户信息
     * @param unitCode 搜索该承办方数据
     * @param match
     * @param player
     * @param playerPhone
     * @param source
     * @param channelCode
     * @param startTime
     * @param endTime
     * @param sortField
     * @param sortOrder
     * @param eventType
     * @param page
     * @param limit
     * @return
     */
    PagingResult<PlayerModel> getPlayersWithUser(boolean allowData,
                                                           UserInfoModel userInfo, String unitCode,
                                                           String match, String player,
                                                           String playerPhone, Integer source,
                                                           String channelCode, String startTime,
                                                           String endTime, String sortField,
                                                           String sortOrder, String eventType,
                                                           int page, int limit);

}
