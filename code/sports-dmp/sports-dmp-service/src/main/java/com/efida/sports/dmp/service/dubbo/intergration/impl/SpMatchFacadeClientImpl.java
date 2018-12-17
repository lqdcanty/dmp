/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.efida.sport.admin.facade.SpMatchAreaFacade;
import com.efida.sport.admin.facade.SpMatchFacade;
import com.efida.sport.admin.facade.model.MatchAreasModel;
import com.efida.sport.admin.facade.model.SpGroupItemModel;
import com.efida.sport.admin.facade.model.SpMatchAllInfoModel;
import com.efida.sport.admin.facade.model.SpMatchGroupAndItemInfoModel;
import com.efida.sport.admin.facade.model.SpMatchGroupModel;
import com.efida.sport.admin.facade.model.SpMatchInfoModel;
import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.admin.facade.model.SpPlayingAreaModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sport.admin.facade.result.SportAdminResult;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;

/**
 * 
 * @author zoutao
 * @version $Id: SpMatchFacadeClientImpl.java, v 0.1 2018年5月23日 下午6:24:35 zoutao Exp $
 */
@Service
public class SpMatchFacadeClientImpl implements SpMatchFacadeClient {
    @Reference
    private SpMatchFacade     spMatchFacade;
    @Reference
    private SpMatchAreaFacade areaFacade;

    private static Logger     log = LoggerFactory.getLogger(SpMatchFacadeClientImpl.class);

    @Override
    public List<SpMatchModel> getMatchs(String gameCode) {
        SportAdminResult<List<SpMatchModel>> rs = spMatchFacade.getSpMatchsWithGameCode(gameCode);
        if (log.isDebugEnabled()) {
            log.debug("查询 赛事列表,项目分类编号: " + gameCode + "  ,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public List<MatchAreasModel> getMatchAreas() {
        SportAdminResult<List<MatchAreasModel>> rs = areaFacade.getAllMatchAreas();
        if (log.isDebugEnabled()) {
            log.debug("查询所有赛场信息,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public List<SpMatchModel> getEnableSpMatchs() {
        SportAdminResult<List<SpMatchModel>> rs = spMatchFacade.getEnableSpMatchs();
        if (log.isDebugEnabled()) {
            log.debug("查询 赛事列表  ,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public List<SpPlayingAreaModel> getSpPlayingAreas(String matchCode) {
        SportAdminResult<List<SpPlayingAreaModel>> rs = spMatchFacade.getSpPlayingAreas(matchCode);
        if (log.isDebugEnabled()) {
            log.debug("查询赛场列表: " + matchCode + "  ,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public List<SpMatchGroupAndItemInfoModel> getMatchTypeAndGroupInfo(String fileidCode) {
        SportAdminResult<List<SpMatchGroupAndItemInfoModel>> rs = spMatchFacade
            .getMatchTypeAndGroupInfo(fileidCode);
        if (log.isDebugEnabled()) {
            log.debug("查询比赛组和比赛项数据 ,赛场编号: " + fileidCode + "  ,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public SpMatchInfoModel getMatchDetail(String code) {
        SportAdminResult<SpMatchInfoModel> rs = spMatchFacade.getEnableSpMathchInfo(code);
        if (log.isDebugEnabled()) {
            log.debug("查询赛事详情 ,赛事Code: " + code + "  ,返回结果为:" + JSON.toJSONString(rs));
        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();

    }

    @Override
    public SpMatchAllInfoModel getItemDetail(String fileidCode, String itemCode) {
        SportAdminResult<SpMatchAllInfoModel> rs = spMatchFacade.getSpMatchAllInfoModel(fileidCode,
            itemCode);
        if (log.isDebugEnabled()) {
            log.debug("查询比赛项详细信息，fileidCode: " + fileidCode + " ,itemCode:" + itemCode + " ,返回结果为:"
                      + JSON.toJSONString(rs));
        }

        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();

    }

    @Override
    public List<MatchDetailModel> queryMatches(String matchCode, String matchStatus,
                                               Date beginTimeMin, Date beginTimeMax,
                                               Date endTimeMin, Date endTimeMax, int pageNumber,
                                               int pageSize) {
        SportAdminResult<List<MatchDetailModel>> rs = spMatchFacade.queryMatches(matchCode,
            matchStatus, beginTimeMin, beginTimeMax, endTimeMin, endTimeMax, pageNumber, pageSize);
        if (log.isDebugEnabled()) {
            log.debug(
                "查询 赛事信息, matchCode=" + matchCode + "&matchStatus=" + matchStatus + "&beginTimeMin="
                      + beginTimeMin + "&beginTimeMax=" + beginTimeMax + "&endTimeMin=" + endTimeMin
                      + "&endTimeMax=" + endTimeMax + "," + "  ,返回结果为:" + JSON.toJSONString(rs));

        }
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public MatchDetailModel getMatchDetailModel(String matchCode) {
        SportAdminResult<List<MatchDetailModel>> rs = spMatchFacade.queryMatches(matchCode, null,
            null, null, null, null, 1, 1);
        if (!rs.isSuccess()) {
            throw new DmpBusException(rs.getErrorMsg());
        }
        List<MatchDetailModel> list = rs.getResultObj();
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public SportsAdminPageResult<SpMatchModel> searchMatchs(String keyword, int pageNumber,
                                                            int pageSize) {
        SportAdminResult<SportsAdminPageResult<SpMatchModel>> dr = spMatchFacade
            .getEnableSpMatchByKeyWord(keyword, pageNumber, pageSize);
        if (log.isDebugEnabled()) {
            log.debug("搜索赛事列表信息, keyword=" + keyword + "&pageNumber=" + pageNumber + "&pageSize="
                      + pageSize + "& ,返回结果为:" + JSON.toJSONString(dr));

        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public SportsAdminPageResult<SpMatchModel> getPageMatchs(String gameCode, int pageNumber,
                                                             int pageSize) {
        SportAdminResult<SportsAdminPageResult<SpMatchModel>> dr = spMatchFacade
            .getPageEnableSpMatchs(gameCode, pageNumber, pageSize);
        if (log.isDebugEnabled()) {
            log.debug("根据项目编号获取赛事列表, keyword=" + gameCode + "&pageNumber=" + pageNumber
                      + "&pageSize=" + pageSize + "& ,返回结果为:" + JSON.toJSONString(dr));
        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public SportsAdminPageResult<SpMatchModel> getUnitMatchByKeyWord(String unitCode,
                                                                     String keyword, int page,
                                                                     int size) {
        SportAdminResult<SportsAdminPageResult<SpMatchModel>> dr = spMatchFacade
            .getUnitMatchByKeyWord(unitCode, keyword, page, size);
        if (log.isDebugEnabled()) {
            log.debug(String.format(
                "根据承办方赛事列表, unitCode=%s  keyword=%s  &pageNumber=%s   &pageSize=%s  返回结果:{}",
                unitCode, keyword, page, size, JSON.toJSONString(dr)));
        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public SportsAdminPageResult<MatchDetailModel> getUnitMatchInfos(String unitCode,
                                                                     String matchCode,
                                                                     int pageNumber, int pageSize) {
        SportAdminResult<SportsAdminPageResult<MatchDetailModel>> dr = spMatchFacade
            .getUnitMatchInfos(unitCode, matchCode, pageNumber, pageSize);
        if (log.isDebugEnabled()) {
            log.debug(String.format(
                "根据承办方赛事列表及详情, unitCode=%s  matchCode=%s  &pageNumber=%s   &pageSize=%s  返回结果:{}",
                unitCode, matchCode, pageNumber, pageSize, JSON.toJSONString(dr)));
        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public MatchDetailModel getUnitMatchInfo(String unitCode, String matchCode) {
        if (StringUtils.isBlank(unitCode)) {
            throw new DmpBusException("承办方编号不允许为空");
        }
        if (StringUtils.isBlank(matchCode)) {
            throw new DmpBusException("赛事编号不允许为空");
        }
        SportsAdminPageResult<MatchDetailModel> unitMatchInfos = getUnitMatchInfos(unitCode,
            matchCode, 1, 1);
        List<MatchDetailModel> list = unitMatchInfos.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<SpMatchModel> getMatchsByMatchCodes(List<String> matchCodes) {

        SportAdminResult<List<SpMatchModel>> dr = spMatchFacade.getEnableSpMatchs(matchCodes);
        if (log.isDebugEnabled()) {
            log.debug("根据赛事编号获取赛事列表,赛事编号：{}, 返回赛事列表：{}", JSON.toJSONString(matchCodes),
                JSON.toJSONString(dr));
        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public SpGroupItemModel getEventItem(String eventCode) {

        SportAdminResult<SpGroupItemModel> dr = spMatchFacade.getEventItem(eventCode);
        if (!dr.isSuccess()) {
            log.error(eventCode + "," + dr.getErrorInfo());
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

    @Override
    public SpMatchGroupModel getGroupModel(String groupCode) {
        SportAdminResult<SpMatchGroupModel> rs = spMatchFacade.getGroupModel(groupCode);
        if (!rs.isSuccess()) {
            log.error(groupCode + "," + rs.getErrorInfo());
            throw new DmpBusException(rs.getErrorMsg());
        }
        return rs.getResultObj();
    }

    @Override
    public SpMatchModel getEnableSpMatch(String matchCode) {

        SportAdminResult<SpMatchModel> dr = spMatchFacade.getEnableSpMatch(matchCode);
        if (log.isDebugEnabled()) {
            log.debug("根据赛事编号获取赛事,赛事编号：{}, 返回赛事：{}", matchCode, JSON.toJSONString(dr));
        }
        if (!dr.isSuccess()) {
            throw new DmpBusException(dr.getErrorMsg());
        }
        return dr.getResultObj();
    }

}
