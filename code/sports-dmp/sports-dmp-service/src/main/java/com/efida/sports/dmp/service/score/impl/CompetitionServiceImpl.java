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

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.dmp.facade.model.ScoreDetail;
import com.efida.sport.dmp.facade.model.ScoreGridData;
import com.efida.sport.dmp.facade.model.ScoreHeader;
import com.efida.sport.dmp.facade.model.SoreExtPro;
import com.efida.sports.dmp.biz.open.OpenScoreService;
import com.efida.sports.dmp.biz.score.ScoreRankCaculateService;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.dao.mapper.CompetitionMapper;
import com.efida.sports.dmp.enums.RankTypeEnum;
import com.efida.sports.dmp.service.dubbo.cover.ScoreCover;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.score.CompetitionService;
import com.efida.sports.dmp.service.score.ScoreConfigerService;
import com.efida.sports.dmp.service.score.ScoreRankService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.UucodeUtil;

/**
 * 
 * @author zoutao
 * @version $Id: CompetitionServiceImpl.java, v 0.1 2018年7月28日 下午2:36:16 zoutao Exp $
 */
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, CompetitionEntity>
                                    implements CompetitionService {

    private Logger                   logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScoreRankService         scoreRankService;

    @Autowired
    private ScoreConfigerService     scoreConfigerService;

    @Autowired
    private CompetitionMapper        competitionMapper;

    @Autowired
    private OpenScoreService         scoreService;

    @Autowired
    private SpMatchFacadeClient      spMatchFacadeClient;

    @Autowired
    private ScoreRankCaculateService scoreRankCalaService;

    @Override
    public List<CompetitionEntity> selectCompetitions(String matchCode, String phoneNum) {

        if (StringUtils.isBlank(matchCode)) {
            throw new BusinessException("赛事编号不能为空");
        }
        List<CompetitionEntity> competitions = selectCompetitionByMatchCode(matchCode);

        if (StringUtils.isBlank(phoneNum)) {
            return competitions;
        }
        return competitions;
        //        if (competitions == null || competitions.size() < 1) {
        //            return null;
        //        }
        //        ArrayList<CompetitionEntity> list = new ArrayList<CompetitionEntity>();
        //        for (CompetitionEntity competitionEntity : competitions) {
        //            List<OpenScoreRankEntity> ranks = scoreRankService
        //                .getRanksByCompetition(competitionEntity, phoneNum);
        //            if (ranks != null && ranks.size() > 0) {
        //                list.add(competitionEntity);
        //            }
        //        }
        //        return list;
    }

    @Override
    public List<CompetitionEntity> selectCompetitionByMatchCode(String matchCode) {
        Wrapper<CompetitionEntity> wrapper = new EntityWrapper<CompetitionEntity>();
        wrapper.eq("match_code", matchCode);
        wrapper.eq("is_delete", 0);
        return selectList(wrapper);
    }

    @Override
    public List<Date> queyCompetitionDate(String competitionCode, String phoneNum) {

        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }

        return null;
    }

    @Override
    public List<OpenScoreRankEntity> getMyRanks(String phoneNum, Date competitionDate,
                                                String competitionCode) {
        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        if (StringUtils.isBlank(phoneNum)) {
            throw new BusinessException("电话号码不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }
        List<OpenScoreRankEntity> ranks = scoreRankService.getRanksByCompetition(competition,
            phoneNum);

        return ranks;
    }

    @Override
    public PagingResult<OpenScoreRankEntity> queryRankByCompetition(String competitionCode,
                                                                    Date competitionDate,
                                                                    String phoneNum,
                                                                    int currentPage, int pageSize) {

        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }
        return scoreRankService.queryPageRankByCompetition(competition, competitionDate, phoneNum,
            currentPage, pageSize);

    }

    @Override
    public List<ScoreHeader> getHeaderInfo(String rankCode, String competitionCode) {

        List<ScoreHeader> headers = new ArrayList<ScoreHeader>();
        ScoreHeader header = new ScoreHeader();
        header.setDataInex("sex");
        header.setHeader("性别");
        header.setH5IsShow(false);
        header.setWidth("20px");
        header.setH5Width("20px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("matchName");
        header.setHeader("赛事名称");
        header.setH5IsShow(true);
        header.setWidth("220px");
        header.setH5Width("200px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("fieldName");
        header.setHeader("赛场名称");
        header.setH5IsShow(true);
        header.setWidth("220px");
        header.setH5Width("200px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("groupName");
        header.setHeader("分组名称");
        header.setH5IsShow(true);
        header.setWidth("220px");
        header.setH5Width("200px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("eventName");
        header.setHeader("比赛项名称");
        header.setH5IsShow(true);
        header.setWidth("220px");
        header.setH5Width("200px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("partTime");
        header.setHeader("参赛时间");
        header.setH5IsShow(true);
        header.setWidth("160px");
        header.setH5Width("160px");
        headers.add(header);

        header = new ScoreHeader();
        header.setDataInex("scoreDesc");
        header.setHeader("成绩");
        header.setH5IsShow(true);
        header.setWidth("100px");
        header.setH5Width("100px");
        headers.add(header);

        return headers;

    }

    @Override
    @Deprecated
    public List<SoreExtPro> getExtPro(String rankCode, String competitionCode) {
        if (StringUtils.isBlank(rankCode)) {
            throw new BusinessException("排名编号不能为空");
        }
        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        List<SoreExtPro> pors = new ArrayList<SoreExtPro>();
        OpenScoreRankEntity rank = scoreRankService.getScoreRankByRankCode(rankCode);
        if (rank != null) {
            List<OpenScoreEntity> scores = scoreService
                .queryByPlayerPhoneAndEventCode(rank.getPlayerPhone(), rank.getEventCode());
            List<SoreExtPro> list = ScoreCover.scoreCoverSoreExtPro(scores);
            pors.addAll(list);
        }
        return pors;
    }

    @Override
    public ScoreGridData getScoreGridData(String rankCode, String competitionCode) {
        if (StringUtils.isBlank(rankCode)) {
            throw new BusinessException("排名编号不能为空");
        }
        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        ScoreDetail detail = null;
        OpenScoreRankEntity rank = scoreRankService.getScoreRankByRankCode(rankCode);
        if (rank != null) {
            List<OpenScoreEntity> scores = scoreService
                .queryByPlayerPhoneAndEventCode(rank.getPlayerPhone(), rank.getEventCode());
            detail = ScoreCover.scoreCoverScoreDetail(scores);
        }
        List<ScoreHeader> headers = getHeaderInfo(rankCode, competitionCode);
        ScoreGridData gridData = new ScoreGridData();
        gridData.setHeader(headers);
        gridData.setDetail(detail);
        return gridData;
    }

    @Override
    public PagingResult<CompetitionEntity> selectPageCompetitionByMatchCode(String matchCode,
                                                                            int currentPage,
                                                                            int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<CompetitionEntity> list = competitionMapper.selectCompetitionByMatchCode(matchCode);
        PageInfo<CompetitionEntity> pageInfo = new PageInfo<CompetitionEntity>(list);
        return new PagingResult<CompetitionEntity>(pageInfo.getList(), pageInfo.getTotal(),
            currentPage, pageSize);

    }

    @Override
    public CompetitionEntity getCompetitionByCode(String competitionCode) {
        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        Wrapper<CompetitionEntity> wrapper = new EntityWrapper<CompetitionEntity>();
        wrapper.eq("competition_code", competitionCode);
        return selectOne(wrapper);
    }

    @Override
    public void editCompetition(CompetitionEntity entity, UserInfoModel userInfo) {
        if (entity == null) {
            throw new BusinessException("对象不能为空");
        }
        if (userInfo == null) {
            throw new BusinessException("用户未登录");
        }
        if (StringUtils.isBlank(entity.getMatchCode())) {
            throw new BusinessException("赛事编号不能为空");
        }
        if (StringUtils.isBlank(entity.getName())) {
            throw new BusinessException("比赛名称不能为空");
        }
        if (entity.getName().length() > 24) {
            throw new BusinessException("比赛名称不能超过24个字符");
        }

        if (StringUtils.isBlank(entity.getRankType())) {
            throw new BusinessException("排名方式不允许为空");
        }
        MatchDetailModel matchModel = spMatchFacadeClient
            .getMatchDetailModel(entity.getMatchCode());
        if (matchModel == null) {
            throw new BusinessException("赛事不存在");
        }
        Date date = new Date();
        entity.setGmtModified(date);
        String code = entity.getCompetitionCode();
        if (StringUtils.isNotBlank(code)) {
            CompetitionEntity competition = getCompetitionByCode(code);
            if (competition != null) {
                entity.setId(competition.getId());
                entity.setModifyName(userInfo.getRealName());
                entity.setModifyUid(userInfo.getUserName());
                entity.setIsShow(competition.getIsShow());
            } else {
                entity.setGmtModified(new Date());
                entity.setGmtCreate(date);
                entity.setCreatorUid(userInfo.getUserName());
                entity.setCreatorName(userInfo.getRealName());
            }
        } else {
            entity.setCompetitionCode("competition" + UucodeUtil.getUuid());
            entity.setGmtCreate(date);
            entity.setCreatorUid(userInfo.getUserName());
            entity.setCreatorName(userInfo.getRealName());
            entity.setModifyName(userInfo.getRealName());
            entity.setModifyUid(userInfo.getUserName());
            entity.setIsShow(0);
        }
        entity.setGameCode(matchModel.getGameCode());
        logger.info("insert or update to competition data :{}", JSON.toJSONString(entity));
        insertOrUpdate(entity);
    }

    @Override
    public void deleteByCode(String competitionCode) {
        //通过比赛配置编号删除 自动 排名数据。 
        scoreRankService.deleteAutoRankInfoByCompetitionCode(competitionCode);
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition != null) {
            competition.setIsDelete(1);
            this.updateById(competition);
        }

    }

    @Override
    public PagingResult<String> queryMatchCodes(String gameCode, String phoneNum, int currentPage,
                                                int pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("gameCode", gameCode);
        queryParams.put("playerPhone", phoneNum);
        if (StringUtils.isBlank(phoneNum)) {
            List<String> matchCodes = competitionMapper.queryMatchCodes(queryParams);
            PageInfo<String> pageInfo = new PageInfo<String>(matchCodes);
            return new PagingResult<String>(pageInfo.getList(), pageInfo.getTotal(), currentPage,
                pageSize);
        }
        List<String> matchCodes = competitionMapper.queryRegisterMatchCodes(queryParams);
        PageInfo<String> pageInfo = new PageInfo<String>(matchCodes);
        return new PagingResult<String>(pageInfo.getList(), pageInfo.getTotal(), currentPage,
            pageSize);
    }

    /**
     * 查询我最好的成绩
     * rank表中查询我排名最好的成绩 ，如果查询返回结果为空
     * 从成绩表中查询，按score到序查询一条记录，如果score_type 为big 表示到序第一条为最好成绩
     * 如果score_type 不为big按score正序查询一条记录，改记录为最好成绩
     * @param phoneNum 电话号码 （必传）
     * @param competitionDate   比赛时间
     * @param competitionCode   比赛编号（必传）
     * @return
     */

    @Override
    public OpenScoreEntity getRegisterBestScore(String phoneNum, Date competitionDate,
                                                String competitionCode) {

        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        if (StringUtils.isBlank(phoneNum)) {
            throw new BusinessException("电话号码不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }
        return scoreService.getRegisterBestScore(competition, phoneNum);

    }

    @Override
    public List<OpenScoreEntity> queryRegisterScores(String phoneNum, Date competitionDate,
                                                     String competitionCode) {

        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        if (StringUtils.isBlank(phoneNum)) {
            throw new BusinessException("电话号码不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }

        return scoreService.queryRegisterScores(competition, phoneNum);
    }

    @Override
    public PagingResult<OpenScoreEntity> queryRegisterScores(String phoneNum, Date competitionDate,
                                                             String competitionCode,
                                                             int currentPage, int pageSize) {

        if (StringUtils.isBlank(competitionCode)) {
            throw new BusinessException("比赛编号不能为空");
        }
        CompetitionEntity competition = getCompetitionByCode(competitionCode);
        if (competition == null) {
            throw new BusinessException("比赛不存在");
        }
        return scoreService.queryRegisterScores(competition, phoneNum, currentPage, pageSize);

    }

    @Override
    public PagingResult<String> queryMatchCodesByName(String keyword,int currentPage,
                                              int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("keyword", keyword);
        List<String> matchCodes = competitionMapper.queryRegisterMatchCodes(queryParams);
        PageInfo<String> pageInfo = new PageInfo<String>(matchCodes);
        return new PagingResult<String>(pageInfo.getList(), pageInfo.getTotal(), currentPage,
                pageSize);
    }

    /**
     * 
     * 
     * @param rankType
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<CompetitionEntity> queryNeedRankingCompetion(RankTypeEnum rankType, int pageNumber,
                                                             int pageSize) {

        Wrapper<CompetitionEntity> wrapper = new EntityWrapper<CompetitionEntity>();
        wrapper.eq("rank_type", rankType.getCode());

        return null;
    }

    @Override
    public boolean lockCompetition(String competitionCode, Integer isShow) {
        Wrapper<CompetitionEntity> wrapper = new EntityWrapper<CompetitionEntity>();
        wrapper.eq("competition_code", competitionCode);
        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setIsShow(isShow);
        Integer update = competitionMapper.update(competitionEntity, wrapper);
        if (update == null || update < 2) {
            return false;
        }
        return true;
    }

    @Override
    public PagingResult<OpenScoreRankEntity> queryCompetitionScoreRank(String competitionCode,
                                                                       String matchCode,
                                                                       List<String> groups,
                                                                       List<String> areas,
                                                                       List<String> events,
                                                                       Integer pageNumber,
                                                                       Integer pageSize) {
        return scoreRankService.queryCompetitionScoreRank(competitionCode, matchCode, groups, areas,
            events, pageNumber, pageSize);
    }

    @Override
    public boolean refreshRankCompetition(String competitionCode) {

        scoreRankCalaService.sortScoreByCompetition(competitionCode);
        return true;
    }

    /**
     * 
     * @see com.efida.sports.dmp.service.score.CompetitionService#queryNeedRankingCompetition(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<CompetitionEntity> queryNeedRankingCompetition(Integer pageNumber,
                                                               Integer pageSize) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", (pageNumber - 1) * pageSize);
        map.put("limit", pageSize);

        return this.competitionMapper.queryNeedRankingCompetition(map);
    }

    @Override
    public boolean updateLastRankingTime(CompetitionEntity cmp) {

        int num = this.competitionMapper.updateLastRankingTime(cmp.getCompetitionCode());
        return num >= 0;
    }

    @Override
    public PagingResult<OpenScoreRankEntity> queryPageRankByCompetition(String competitionCode,
                                                                        String phoneNum,
                                                                        int currentPage,
                                                                        int pageSize) {

        CompetitionEntity cmp = this.getCompetitionByCode(competitionCode);

        return this.scoreRankService.queryPageRankByCompetition(cmp, null, phoneNum, currentPage,
            pageSize);
    }

    @Override
    public String selectNameByRankCode(String scoreRankCode){
        return  competitionMapper.selectNameByRankCode(scoreRankCode);
    }
}
