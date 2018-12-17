/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.efida.sport.admin.facade.model.SpGroupItemModel;
import com.efida.sport.admin.facade.model.SpMatchGroupModel;
import com.efida.sport.admin.facade.model.SpMatchInfoModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.biz.score.ScoreCertService;
import com.efida.sports.dmp.biz.score.SyncCertService;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.ScoreCertEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;
import com.efida.sports.dmp.dao.mapper.ScoreCertEntityMapper;
import com.efida.sports.dmp.dao.mapper.ScoreCertMaxNoEntityMapper;
import com.efida.sports.dmp.dao.mapper.ScoreCertNoEntityMapper;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.RegisterFacadeClient;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.msg.IMsgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.util.date.DateUtil;

/**
 * 
 * @author zhiyang
 * @version $Id: SyncCertServiceImpl.java, v 0.1 2018年8月6日 下午8:10:03 zhiyang Exp $
 */
@Service("syncCertService")
public class SyncCertServiceImpl implements SyncCertService {

    private Logger                     logger        = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheService               cacheSevcice;

    @Autowired
    private SpMatchFacadeClient        spMatchFacadeClient;

    @Autowired
    private OpenScoreEntityMapper      openScoreEntityMapper;

    @Autowired
    private ScoreCertService           scoreCertService;

    @Autowired
    private RegisterFacadeClient       registerFacadeClient;

    @Autowired
    private ScoreCertEntityMapper      scoreCertEntityMapper;

    @Autowired
    private ScoreCertNoEntityMapper    scoreCertNoEntityMapper;

    @Autowired
    private ScoreCertMaxNoEntityMapper scoreCertMaxNoEntityMapper;

    @Autowired
    private OpenScoreService           openScoreService;

    @Autowired
    private IMsgService                smgService;

    @Value("${spring.scoreCert.isConfigFirst}")
    private Boolean                    isConfigFirst = true;

    /**
     * 
     * @see com.efida.sports.dmp.biz.score.SyncCertService#syncBestScore2Player(com.efida.sports.dmp.dao.entity.OpenScoreEntity)
     */
    public String syncBestScore2Player(OpenScoreEntity item) {
        if (item == null) {
            throw new BusinessException("同步数据记录不能为空!");
        }
        OpenScoreEntity bstItem = openScoreService.getUserBestScore(item.getMatchCode(),
            item.getFieldCode(), item.getMatchGroupCode(), item.getEventCode(),
            item.getPlayerPhone());
        String str = null;
        if (bstItem != null && bstItem.getId().longValue() != item.getId()) {
            str = this.syncOneScoreInfo(bstItem);
        }
        String str2 = this.syncOneScoreInfo(item);
        if (str == null) {
            return str2;
        }
        return str;
    }

    public String syncOneScoreInfo(OpenScoreEntity item) {
        if (item == null) {
            throw new BusinessException("同步数据记录不能为空!");
        }
        int syncTotal = item.getSyncTotal() == null ? 1 : item.getSyncTotal() + 1;
        long syncInterval = syncTotal * 30;
        if (syncInterval > 30 * 60) {
            syncInterval = 1800;
        }
        byte sync = 0;
        long nexSyncTime = System.currentTimeMillis() + syncInterval * 1000;
        String syncStatus = "";
        try {
            if (StringUtils.isEmpty(item.getPlayerPhone())) {
                sync = 2;
                syncStatus = "phoneNum为空不颁发证书!";
                throw new BusinessException("phoneNum 不能为空!");
            }
            if (StringUtils.isEmpty(item.getMatchCode())) {
                sync = 2;
                syncStatus = "matchCode为空不颁发证书!";
                throw new BusinessException("matchCode 不能为空!");
            }
            if (StringUtils.isEmpty(item.getEventCode())) {
                sync = 2;
                syncStatus = "eventCode为空不颁发证书!";
                throw new BusinessException("eventCode 不能为空!");
            }
            if (item.getIsValid() == 0) {
                sync = 0;
                syncStatus = "无效成绩不颁发证书!";
            } else {
                ScoreCertEntity certInfo = convert2ScoreCertInfo(item);
                filledByConfig(item, certInfo);
                checkCertInfo(item, certInfo);
                boolean isBigGood = true;
                if ("small".equalsIgnoreCase(item.getScoreType())) {
                    isBigGood = false;
                }
                SaveCertStatus st = this.scoreCertService.saveScoreCert(certInfo, isBigGood);
                syncStatus = st.getTipInfo();
                item.setSyncStatus(syncStatus);
                if (st.isSuccess()) {
                    sync = 1;
                    if (StringUtils.isEmpty(item.getSyncStatus())) {
                        syncStatus = "生成证书数据成功,证书编号:" + certInfo.getCertSn() + ",耐心等待颁发电子证书!";
                    }
                } else {
                    sync = 0;
                    item.setNextSyncTime(new Date(nexSyncTime));
                    if (StringUtils.isEmpty(item.getSyncStatus())) {
                        syncStatus = "failed";
                    }
                }
            }

        } catch (BusinessException ex1) {
            logger.error("syncScoreInfo failed, scoreCode:{}", item.getScoreCode(), ex1);
            if (sync != 0) {
                item.setSync(sync);
            } else {
                sync = 0;
            }
            syncStatus = ex1.getMessage();
            item.setNextSyncTime(new Date(nexSyncTime));
            item.setSyncStatus(syncStatus);
        } catch (Exception ex2) {
            logger.error("syncScoreInfo failed, scoreCode:{}", item.getScoreCode(), ex2);
            if (sync != 0) {
                item.setSync(sync);
            } else {
                sync = 0;
            }
            syncStatus = "请联系管理员," + ex2.getMessage();
            item.setNextSyncTime(new Date(nexSyncTime));
            item.setSyncStatus(syncStatus);
        } finally {
            if (syncStatus != null && syncStatus.length() > 30) {
                syncStatus = syncStatus.substring(0, 30);
            }
            item.setSyncStatus(syncStatus);
            item.setSyncTotal(syncTotal);
        }
        OpenScoreEntity osc = new OpenScoreEntity();
        osc.setId(item.getId());
        osc.setSync(sync);
        osc.setSyncTotal(syncTotal);
        osc.setSyncStatus(syncStatus);
        osc.setNextSyncTime(item.getNextSyncTime());
        this.openScoreEntityMapper.updateByPrimaryKeySelective(osc);
        return syncStatus;
    }

    private void filledByConfig(OpenScoreEntity item, ScoreCertEntity certInfo) {
        byte sync = 0;
        String syncStatus = "";
        SpMatchInfoModel matchInfo = spMatchFacadeClient.getMatchDetail(certInfo.getMatchCode());
        if (matchInfo != null) {
            certInfo.setMatchName(matchInfo.getMatchName());
        } else {
            sync = 3;
            syncStatus = "matchCode未找到赛事配置！";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }

        if (StringUtils.isNotEmpty(certInfo.getMatchGroupCode())) {
            SpMatchGroupModel group = spMatchFacadeClient
                .getGroupModel(certInfo.getMatchGroupCode());
            if (group != null) {
                certInfo.setMatchGroupName(group.getGroupName());
            }
        }
        SpGroupItemModel eventInfo = spMatchFacadeClient.getEventItem(item.getEventCode());
        if (eventInfo != null) {
            item.setEventName(eventInfo.getItemName());
        }

    }

    private void checkCertInfo(OpenScoreEntity item, ScoreCertEntity certInfo) {

        byte sync = 0;
        String syncStatus = "";
        if (StringUtils.isEmpty(certInfo.getUnitCode())) {
            item.setSync((byte) 3);
            item.setSyncStatus("unitCode 为空！");
            throw new BusinessException(item.getSyncStatus());
        }
        if (StringUtils.isEmpty(certInfo.getMatchCode())) {
            sync = 3;
            syncStatus = "matchCode 为空！";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }

        if (StringUtils.isEmpty(certInfo.getEventCode())) {
            sync = 3;
            syncStatus = "eventCode 为空！";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }

        if (StringUtils.isEmpty(certInfo.getMatchName())) {

            sync = 3;
            syncStatus = "matchName为空赛事配置！";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }

        if (StringUtils.isEmpty(certInfo.getEventName())) {
            sync = 3;
            syncStatus = "比赛项eventName目为空配置";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }

        if (StringUtils.isNotEmpty(certInfo.getMatchGroupCode())) {
            if (StringUtils.isEmpty(certInfo.getMatchGroupName())) {
                sync = 3;
                syncStatus = "比赛分组名不能为空！";
                item.setSync(sync);
                item.setSyncStatus(syncStatus);
                throw new BusinessException(syncStatus);
            }
        }

        if (StringUtils.isEmpty(certInfo.getPlayerPhone())) {
            sync = 3;
            syncStatus = "运动员手机号不能为空！";
            item.setSync(sync);
            item.setSyncStatus(syncStatus);
            throw new BusinessException(syncStatus);
        }
    }

    /**
     * 获取成绩幂等ID
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param app
     * @return
     */
    public String getIdempotentId(OpenScoreEntity app) {
        String idempotentId = app.getEventCode() + "_" + app.getPlayerPhone();
        if (StringUtils.isNotEmpty(app.getPlayerName())) {
            idempotentId += "_" + app.getPlayerName();
        } else {
            if (StringUtils.isNotEmpty(app.getOpenId())) {
                if (StringUtils.isNotBlank(app.getOpenPlatType())) {
                    idempotentId += "_" + app.getOpenPlatType();
                }
                idempotentId += app.getOpenId();
            }
        }
        if (idempotentId.length() > 64) {
            idempotentId = idempotentId.substring(0, 64);
        }
        return idempotentId;
    }

    /**
     * 缺失： registercode:
     * @param app
     * @return
     */
    private ScoreCertEntity convert2ScoreCertInfo(OpenScoreEntity app) {

        ScoreCertEntity ap = new ScoreCertEntity();
        String idempotentId = getIdempotentId(app);
        ap.setIdempotentId(idempotentId);

        ap.setPlayerPhone(app.getPlayerPhone());
        ap.setScoreRankCode(app.getScoreCode());
        ap.setUnitCode(app.getUnitCode());
        ap.setPlayerCode(app.getPlayerCode());
        ap.setPlayerName(app.getPlayerName());
        ap.setPartTime(app.getPartTime());

        ap.setMatchCode(app.getMatchCode());
        ap.setMatchName(app.getMatchName());
        ap.setFieldCode(app.getFieldCode());
        ap.setFieldName(app.getFieldName());

        ap.setMatchGroupCode(app.getMatchGroupCode());
        ap.setMatchGroupName(app.getMatchGroupName());

        ap.setEventCode(app.getEventCode());
        ap.setEventName(app.getEventName());
        ap.setIsValid(app.getIsValid() == 1 ? true : false);
        ap.setScore(app.getScore());
        ap.setScoreUnit(app.getScoreUnit());
        ap.setScoreDesc(app.getScoreDesc());
        ap.setScoreName(app.getScoreName());
        ap.setChannelCode(app.getChannelCode());

        return ap;
    }

    /**
     * 
     * 
     * @param unitCode
     * @param idempotentId
     * @return
     */
    @Override
    public String syncOneScoreByIdempotentId(String unitCode, String idempotentId) {

        OpenScoreEntity item = this.openScoreEntityMapper.getScoreInfoByIdempotent(unitCode,
            idempotentId);

        return this.syncBestScore2Player(item);
    }

    /**
     * 
     */
    @Override
    public int generateCertByScoreIds(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }

        int total = 0;
        for (Long id : ids) {
            try {
                syncOneScoreById(id);
                total++;
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }

        return total;
    }

    @Override
    public int generateCertByAllScore() {
        int pageNumber = 0;
        int pageSize = 100;
        int total = 0;
        while (true) {
            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("start", pageNumber);
            queryParams.put("limit", pageSize);
            List<OpenScoreEntity> items = this.openScoreEntityMapper
                .queryOpenScoreByDateForCert(queryParams);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            for (OpenScoreEntity item : items) {
                this.syncBestScore2Player(item);
                total++;
            }
            if (items.size() < pageSize) {
                break;
            }
            //单次最多处理20万，防止程序出现错误导致死循环
            if (total >= 200000) {
                logger.warn("generateCertByAllScore, exit 。 total exceed 200000 。");
                break;
            }
        }
        return total;
    }

    @Override
    public int generateCertByScoreCreateDay(Date date) {

        Date startTime = DateUtil.parseStr(DateUtil.format(date, DateUtil.WEB_FORMAT),
            DateUtil.WEB_FORMAT);
        Date endTime = DateUtil.addDay(startTime, 1);

        int pageSize = 100;
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("startTime", startTime);
        queryParams.put("endTime", endTime);
        queryParams.put("start", 0);
        queryParams.put("limit", pageSize);

        int total = 0;
        while (true) {
            List<OpenScoreEntity> items = this.openScoreEntityMapper
                .queryOpenScoreByDateForCert(queryParams);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }
            for (OpenScoreEntity item : items) {

                this.syncBestScore2Player(item);
                total++;
            }
            if (items.size() < pageSize) {
                break;
            }
            //单次最多处理20万，防止程序出现错误导致死循环
            if (total >= 200000) {
                logger.warn("generateCertByScoreCreateDay, exit 。 total exceed 200000 。");
                break;
            }
        }

        return total;
    }

    @Override
    public String syncOneScoreById(Long id) {
        OpenScoreEntity item = this.openScoreEntityMapper.selectByPrimaryKey(id);
        if (item == null) {
            return "记录不存在!";
        }
        return this.syncBestScore2Player(item);
    }

    @Override
    public ScoreCertEntity getCertPicBySoreId(Long id) {
        OpenScoreEntity item = this.openScoreEntityMapper.selectByPrimaryKey(id);
        if (item == null) {
            return null;
        }
        String idempotentId = getIdempotentId(item);
        Map<String, Object> params = new java.util.HashMap<String, Object>();
        params.put("idempotentId", idempotentId);
        return scoreCertEntityMapper.selectByIdempotentId(params);
    }

    @Override
    public PagingResult<ScoreCertEntity> getPageCertEntity(String playerPhone, String certSn,
                                                           String certName, Integer isSmsSend,
                                                           String status, int pageNumber,
                                                           int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("playerPhone", playerPhone);
        queryParams.put("certSn", certSn);
        queryParams.put("certName", certName);
        queryParams.put("isSmsSend", isSmsSend);
        queryParams.put("status", status);
        List<ScoreCertEntity> certs = scoreCertEntityMapper.selectCertEntityLikeParams(queryParams);
        PageInfo<ScoreCertEntity> pageInfo = new PageInfo<ScoreCertEntity>(certs);
        return new PagingResult<ScoreCertEntity>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    @Override
    public boolean sendMsgScoreCert(ScoreCertEntity scoreCert) {
        boolean isSmsSend = true;
        Date nextSendTime = null;
        String smsStatus = "";
        int sendSmsTotal = 1;
        if (scoreCert == null) {
            throw new DmpBusException("证书存储信息不能为空");
        }
        if (StringUtils.isBlank(scoreCert.getPlayerPhone())) {
            throw new DmpBusException("运动员手机为空发送信息失败");
        }
        if (scoreCert.getNeedSendSms() == null || scoreCert.getNeedSendSms() == false) {
            isSmsSend = false;
            logger.info("数据标识不需要发送短信 证书编号:{}", scoreCert.getCertSn());
            smsStatus = "数据标识不需要发送短信";
            throw new DmpBusException(smsStatus);
        }
        try {
            if (scoreCert.getSendSmsTotal() != null) {
                sendSmsTotal = sendSmsTotal + scoreCert.getSendSmsTotal();
            }
            isSmsSend = smgService.sendDownMsg(scoreCert.getPlayerPhone());
        } catch (Exception e) {
            isSmsSend = false;
            nextSendTime = DateUtil.add(new Date(), 5 * 60 * 60);
            smsStatus = e.getMessage();
            logger.error("", e);
        } finally {
            //更新该手机号所有记录
            ScoreCertEntity scoreCertEntity = new ScoreCertEntity();
            scoreCertEntity.setPlayerPhone(scoreCert.getPlayerPhone());
            scoreCertEntity.setIsSmsSend(isSmsSend);
            scoreCertEntity.setNextSendSmsTime(nextSendTime);
            scoreCertEntity.setSendSmsTotal(sendSmsTotal);
            scoreCertEntity.setSmsSendTime(new Date());
            scoreCertEntity.setSmsStatus(StringUtils.substring(smsStatus, 0, 30));
            scoreCertEntityMapper.updateByPhoneSelective(scoreCertEntity);
        }
        if (StringUtils.isNotBlank(smsStatus)) {
            throw new DmpBusException(smsStatus);
        }
        return isSmsSend;
    }

    @Override
    public ScoreCertEntity getCertEntityById(Long id) {
        return scoreCertEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean sendReplayMsgScoreCert(ScoreCertEntity scoreCert) {
        boolean isSmsSend = true;
        int sendSmsTotal = 1;
        String smsStatus = "";
        if (scoreCert == null) {
            throw new DmpBusException("证书存储信息不能为空");
        }
        if (StringUtils.isBlank(scoreCert.getPlayerPhone())) {
            throw new DmpBusException("运动员手机为空发送信息失败");
        }
        if (scoreCert.getSendSmsTotal() != null) {
            sendSmsTotal = sendSmsTotal + scoreCert.getSendSmsTotal();
        }
        try {
            isSmsSend = smgService.sendDownMsg(scoreCert.getPlayerPhone());
        } catch (Exception e) {
            isSmsSend = false;
            smsStatus = e.getMessage();
            logger.error("", e);
        } finally {
            ScoreCertEntity scoreCertEntity = new ScoreCertEntity();
            scoreCertEntity.setId(scoreCert.getId());
            scoreCertEntity.setSendSmsTotal(sendSmsTotal);
            scoreCertEntity.setSmsStatus(StringUtils.substring(smsStatus, 0, 30));
            scoreCertEntityMapper.updateByPrimaryKeySelective(scoreCertEntity);
        }
        if (StringUtils.isNotBlank(smsStatus)) {
            throw new DmpBusException(smsStatus);
        }
        return isSmsSend;
    }

}
