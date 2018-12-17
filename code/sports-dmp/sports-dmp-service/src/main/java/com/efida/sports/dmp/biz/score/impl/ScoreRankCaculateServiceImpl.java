/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sports.dmp.biz.open.OpenScoreRankService;
import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.biz.open.request.SearchCompetionScoreRequest;
import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreRankEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.score.CompetitionService;
import com.efida.sports.dmp.service.score.ScoreRankService;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

/**
 * 
 * @author Lenovo
 * @version $Id: ScoreRankCaculateServiceImpl.java, v 0.1 2018年8月13日 下午5:54:58 Lenovo Exp $
 */
@Service("scoreRankCaculateService")
public class ScoreRankCaculateServiceImpl implements ScoreRankCaculateService {
    private static Logger             logger = LoggerFactory
        .getLogger(ScoreRankCaculateServiceImpl.class);

    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;

    @Autowired
    private OpenScoreRankEntityMapper openScoreRankEntityMapper;
    @Autowired
    private OpenScoreService          openScoreService;
    @Autowired
    private OpenScoreRankService      openScoreRankService;

    @Autowired
    private ScoreRankService          scoreRankService;
    @Autowired
    private CompetitionService        competitionService;

    /**
     * 通过赛事编号生成成绩排名数据
     * 
     * 一个赛事一个总排名，只适用于一个比赛项情况
     * 
     * @param unitCode
     */
    public long sortScoreByUnitCode(String unitCode) {

        SportsAdminPageResult<SpMatchModel> result = spMatchFacadeClient
            .getUnitMatchByKeyWord(unitCode, null, 1, 10);
        if (result == null) {
            return 0;
        }

        List<SpMatchModel> items = result.getList();
        if (CollectionUtils.isEmpty(items)) {
            return 0;
        }

        long total = 0;
        for (SpMatchModel match : items) {
            total += sortScoreByMatchCode(match.getMatchCode());
        }

        return total;
    }

    public int sortScoreByMatchCode(String matchCode) {

        SearchCompetionScoreRequest qs = new SearchCompetionScoreRequest();
        qs.setMatchCode(matchCode);

        int num = this.sortScoreByCompetition(qs, "");
        return num;
    }

    private void saveOrUpdate(List<OpenScoreRankEntity> rankItems) {

        if (rankItems != null && rankItems.size() >= 10) {
            threadSaveOrUpdate(rankItems);
        } else {
            saveOrUpdate1(rankItems);
        }
    }

    private void deleteRecords(List<Long> deleteList, String competitionCode) {

        if (CollectionUtils.isEmpty(deleteList)) {
            return;
        }
        if (deleteList.size() >= 10) {
            threadDeleteAutoRankRecord(deleteList);
        } else {
            deleteAutoRankRecord(deleteList);
        }
    }

    private void deleteAutoRankRecord(List<Long> deleteList) {

        if (deleteList == null) {
            return;
        }
        for (Long id : deleteList) {
            deleteAutoRankRecord(id);
        }
    }

    public int deleteAutoRankRecord(Long id) {

        return this.openScoreRankEntityMapper.deleteAutoRankByPrimaryKey(id);
    }

    private void saveOrUpdate1(List<OpenScoreRankEntity> rankItems) {
        for (OpenScoreRankEntity rank : rankItems) {
            saveOrUpdate(rank);
        }
    }

    private void threadDeleteAutoRankRecord(List<Long> items) {
        long start = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        logger.info("start threadDeleteAutoRankRecord , size:" + items.size());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Long item : items) {

            DeleteRankTask srt = new DeleteRankTask(item);
            srt.setScoreRankCaculateService(this);
            executor.execute(srt);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
        logger.info("end threadDeleteAutoRankRecord , size:" + items.size() + ", spent:"
                    + (System.currentTimeMillis() - start) + "ms。");
    }

    private void threadSaveOrUpdate(List<OpenScoreRankEntity> rankItems) {
        long start = System.currentTimeMillis();
        if (CollectionUtils.isEmpty(rankItems)) {
            return;
        }
        logger.info("start trheadSaveOrUpdate , size:" + rankItems.size());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (OpenScoreRankEntity rank : rankItems) {

            SaveRankTask srt = new SaveRankTask(rank);
            srt.setScoreRankCaculateService(this);
            executor.execute(srt);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
        logger.info("end trheadSaveOrUpdate , size:" + rankItems.size() + ", spent:"
                    + (System.currentTimeMillis() - start) + "ms。");

    }

    public void saveOrUpdate(OpenScoreRankEntity rank) {

        OpenScoreRankEntity old = this.openScoreRankService
            .queryScoreRankByIdempotent(rank.getUnitCode(), rank.getIdempotentId());
        if (old != null) {
            //排名及成绩未发生改变，不更新；
            if (rank.getRanking().longValue() == old.getRanking()
                && rank.getScore().doubleValue() == old.getScore().doubleValue()) {

                return;
            }
            old.setRanking(rank.getRanking());
            old.setPromotion(rank.getPromotion());
            rank.setId(old.getId());
            rank.setSync(null);
            rank.setSource(null);
            this.openScoreRankEntityMapper.updateByPrimaryKeySelective(rank);
        } else {
            this.openScoreRankEntityMapper.insert(rank);
        }
    }

    private OpenScoreRankEntity convert2RankEntity(OpenScoreEntity record) {

        OpenScoreRankEntity re = new OpenScoreRankEntity();
        BeanUtils.copyProperties(record, re);
        final String scoreRankCode = SeqWorkerUtil.generateTimeSequence();

        re.setScoreRankCode(scoreRankCode);
        re.setId(null);
        re.setPromotion("");
        re.setSync((byte) 0);
        re.setSyncStatus("");
        re.setSource((byte) 3);

        return re;
    }

    /**
     * 
     * 
     * @param competitionCode
     * @param map
     * @return
     */
    private List<Long> findNeedDeleteCompetitionRecords(String competitionCode,
                                                        Map<String, OpenScoreEntity> map) {

        int pageNumber = 1;
        int pageSize = 500;
        List<Long> deleteList = new ArrayList<Long>();
        while (true) {

            List<OpenScoreRankEntity> items = this.scoreRankService
                .querySelfRankByCompetition(competitionCode, null, pageNumber, pageSize);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }

            for (OpenScoreRankEntity item : items) {

                String idempotentId = item.getIdempotentId();
                if (!map.containsKey(idempotentId)) {
                    deleteList.add(item.getId());
                }
            }
            if (items.size() < pageSize) {
                break;
            }
            pageNumber++;
        }

        return deleteList;
    }

    /**
     * 
     * 
     * @param qs
     * @param competionCode
     * @return
     */
    public int sortScoreByCompetition(SearchCompetionScoreRequest qs, String competitionCode) {

        long start = System.currentTimeMillis();
        logger.info("start call sortScoreByCompetition, competitionCode:" + competitionCode
                    + ", qs:" + qs.toString());
        int pageNumber = 1;
        int pageSize = 200;

        qs.setPageNumber(pageNumber);
        qs.setPageSize(pageSize);
        Map<String, OpenScoreEntity> map = new HashMap<String, OpenScoreEntity>();
        boolean isBigGood = true;
        boolean first = true;
        while (true) {
            qs.setPageNumber(pageNumber);
            List<OpenScoreEntity> items = openScoreService.queryScoreList(qs);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }

            for (OpenScoreEntity item : items) {
                if (first) {
                    if ("small".equals(item.getScoreType())) {
                        isBigGood = false;
                    }
                    first = false;
                }
                String idempotent = getIdempotentId(item, competitionCode);
                OpenScoreEntity old = map.get(idempotent);
                if (old == null) {
                    map.put(idempotent, item);
                } else {

                    if (isBigGood) {
                        if (item.getScore().doubleValue() > old.getScore().doubleValue()) {
                            map.put(idempotent, item);
                        }
                    } else {
                        if (item.getScore().doubleValue() < old.getScore().doubleValue()) {
                            map.put(idempotent, item);
                        }
                    }
                }
            }
            if (items.size() < pageSize) {
                break;
            }
            pageNumber++;
        }

        Set<Entry<String, OpenScoreEntity>> set = map.entrySet();
        Iterator<Entry<String, OpenScoreEntity>> iter = set.iterator();
        List<OpenScoreRankEntity> rankItems = new ArrayList<OpenScoreRankEntity>();
        while (iter.hasNext()) {
            Entry<String, OpenScoreEntity> entry = iter.next();
            String id = entry.getKey();
            OpenScoreEntity record = entry.getValue();
            OpenScoreRankEntity re = convert2RankEntity(record);
            re.setIdempotentId(id);
            re.setCompetitionCode(competitionCode);
            rankItems.add(re);
        }

        OpenScoreRankComparable comp = new OpenScoreRankComparable();
        comp.setBigGood(isBigGood);
        Collections.sort(rankItems, comp);
        int ranking = 1;
        int total = 1;
        double lastScore = 100000.6666;
        for (OpenScoreRankEntity rk : rankItems) {
            if (rk.getScore().doubleValue() == lastScore) {
                ranking--;
            } else {
                ranking = total;
            }
            rk.setRanking(ranking);
            lastScore = rk.getScore().doubleValue();
            ranking++;
            total++;
        }
        //需要删除记录
        List<Long> deleteList = findNeedDeleteCompetitionRecords(competitionCode, map);
        deleteRecords(deleteList, competitionCode);
        saveOrUpdate(rankItems);
        long spent = System.currentTimeMillis() - start;
        logger.info("end call sortScoreByCompetition, competitionCode:" + competitionCode
                    + ", rankItems size:" + rankItems.size() + ", qs:" + qs.toString() + ", spent:"
                    + spent + " ms.");
        return rankItems.size();
    }

    public int sortScoreByCompetition(String competitionCode) {

        CompetitionEntity cmp = competitionService.getCompetitionByCode(competitionCode);
        if (cmp == null) {
            throw new BusinessException("未找到对应比赛项配置");
        }
        SearchCompetionScoreRequest qs = new SearchCompetionScoreRequest();
        qs.setMatchCode(cmp.getMatchCode());
        String areas = cmp.getAreas();
        if (StringUtils.isNotBlank(areas)) {
            List<String> fieldCodes = JSONObject.parseArray(areas, String.class);//把字符串转换成集合
            qs.setFieldCodes(fieldCodes);
        }
        String groups = cmp.getGroups();
        if (StringUtils.isNotBlank(groups)) {
            List<String> groupCodes = JSONObject.parseArray(groups, String.class);//把字符串转换成集合
            qs.setGroupCodes(groupCodes);
        }
        String events = cmp.getEvents();
        if (StringUtils.isNotBlank(events)) {
            List<String> eventCodes = JSONObject.parseArray(events, String.class);//把字符串转换成集合
            qs.setEventCodes(eventCodes);
        }
        int num = this.sortScoreByCompetition(qs, competitionCode);
        this.competitionService.updateLastRankingTime(cmp);

        return num;
    }

    private String getIdempotentId(OpenScoreEntity app, String competitionCode) {
        String idempotentId = app.getEventCode();
        if (!StringUtils.isEmpty(competitionCode)) {

            if (competitionCode.length() > 10) {
                competitionCode = competitionCode.substring(10);
            }
            idempotentId += "_" + competitionCode;
        }
        idempotentId += "_" + app.getPlayerPhone();

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

    @Override
    public void autoRankingByConfig() {

        int pageNumber = 1;
        int pageSize = 20;
        while (true) {
            List<CompetitionEntity> items = this.competitionService
                .queryNeedRankingCompetition(pageNumber, pageSize);
            if (CollectionUtils.isEmpty(items)) {
                break;
            }

            for (CompetitionEntity item : items) {
                this.sortScoreByCompetition(item.getCompetitionCode());
            }

            pageNumber++;
            if (items.size() < pageSize) {
                break;
            }
        }

    }
}
