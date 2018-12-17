/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score;

import java.util.Date;
import java.util.List;

import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 
 * @author zhiyang
 * @version $Id: SyncCertService.java, v 0.1 2018年8月6日 下午8:14:42 zhiyang Exp $
 */
public interface SyncCertService {

    /**
    * 拷贝数据生成证书数据.
    * 
    * 通过手机号，比赛项查询用户最好成绩。然后以最好成绩颁发证书。其他相关比赛更改状态为已颁发。
    * 
    * @param item
    * @return
     * @throws Exception 
    */
    public String syncBestScore2Player(OpenScoreEntity item);

    /**
     * 通过成绩排名记录编号生成证书
     * 
     * @param scoreRankCode 成绩排名编号
     * @return
     */
    public String syncOneScoreByIdempotentId(String unitCode, String idempotentId);

    /**
     * 通过成绩id生成证书。 
     * @param ids
     * @return
     */
    int generateCertByScoreIds(List<Long> ids);

    /**
     * 通过日期查询日期范围内创建的成绩，并生成证书。 
     * @param date
     * @return
     */
    int generateCertByScoreCreateDay(Date date);

    /**
     * 
     * @return
     */
    int generateCertByAllScore();

    /**
     * 
     * 
     * @param id
     * @return
     */
    public String syncOneScoreById(Long id);

    /**
     * 查询证书信息通过成绩编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param id 成绩ID
     * @return
     */
    public ScoreCertEntity getCertPicBySoreId(Long id);

    /**
     * 获取证书分页信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerPhone
     * @param certSn
     * @param certName
     * @param isSmsSend
     * @param status
     * @return
     */
    public PagingResult<ScoreCertEntity> getPageCertEntity(String playerPhone, String certSn,
                                                           String certName, Integer isSmsSend,
                                                           String status, int pageNumber,
                                                           int pageSize);

    /**
     * 给发送证书人的人发送消息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scoreCert
     * @return
     */
    boolean sendMsgScoreCert(ScoreCertEntity scoreCert);

    /**
     * 查询证书
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param id
     * @return
     */
    public ScoreCertEntity getCertEntityById(Long id);

    /**
     * 再次发送短信
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param scoreCert
     * @return
     */
    public boolean sendReplayMsgScoreCert(ScoreCertEntity scoreCert);
}
