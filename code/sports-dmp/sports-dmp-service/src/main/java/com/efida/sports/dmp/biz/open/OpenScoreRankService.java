/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenScoreRankModel;
import com.efida.sport.open.model.OpenScoreRankResultModel;
import com.efida.sports.dmp.biz.open.request.ScoreRankQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenScoreRankService.java, v 0.1 2018年7月9日 下午10:25:00 zhiyang Exp $
 */
public interface OpenScoreRankService {

    /**
     * 提交 比赛成绩信息
     * 
     * @param unitCode  承办方编号
     * @param matchCode 赛事编号
     * @param count  json数据格式
     * @param data
     * @param timestamp
     * @param sign
     * @param ipAddress
     * @return
     * @throws ParseException 
     * @throws OpenException 
     */
    List<OpenScoreRankResultModel> submitScoreRankInfo(String unitCode, String matchCode, int count,
                                                       String data, String timestamp, String sign,
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
    List<OpenScoreRankModel> queryScoreRankInfo(ScoreRankQueryRequest qr) throws OpenException,
                                                                          ParseException;

    /**
     * 通过承办方编号及唯一标识查询成绩排名
     * @param unitCode
     * @param idempotentId
     * @return
     */
    OpenScoreRankEntity queryScoreRankByIdempotent(String unitCode, String idempotentId);

}
