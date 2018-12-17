package com.efida.sports.dmp.biz.score.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sports.dmp.biz.score.ReportMatchFinishService;
import com.efida.sports.dmp.biz.score.ReportMatchSourceService;
import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.dao.mapper.ReportMatchSourceEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportMatchSourceServiceImpl.java, v 0.1 2018年8月30日 上午9:51:10 zengbo Exp $
 */
@Service("reportMatchSourceService")
public class ReportMatchSourceServiceImpl implements ReportMatchSourceService {

    @Autowired
    private ReportMatchSourceEntityMapper reportMatchSourceEntityMapper;

    @Autowired
    private ReportMatchFinishService      reportMatchFinishService;

    @Autowired
    private SpMatchFacadeClient           spMatchFacadeClient;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return reportMatchSourceEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReportMatchSourceEntity record) {
        return reportMatchSourceEntityMapper.insert(record);
    }

    @Override
    public int insertSelective(ReportMatchSourceEntity record) {
        return reportMatchSourceEntityMapper.insertSelective(record);
    }

    @Override
    public ReportMatchSourceEntity selectByPrimaryKey(Integer id) {
        return reportMatchSourceEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportMatchSourceEntity record) {
        return reportMatchSourceEntityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ReportMatchSourceEntity record) {
        return reportMatchSourceEntityMapper.updateByPrimaryKey(record);
    }

    @Override
    public void saveReportMatchSourceList(List<ReportMatchSourceEntity> records) {
        if (records != null && records.size() > 0) {
            for (ReportMatchSourceEntity entity : records) {
                entity.setGmtModified(new Date());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("matchCode", entity.getMatchCode());
                map.put("applyTimeDay", entity.getApplyTimeDay());
                List<ReportMatchSourceEntity> result = reportMatchSourceEntityMapper
                    .queryMatchEnrollByCode(map);
                if (result != null && result.size() > 0) {
                    ReportMatchSourceEntity oldEntity = result.get(0);
                    if (entity.getOrganizerCount() == oldEntity.getOrganizerCount()
                        && entity.getOfficialCount() == oldEntity.getOfficialCount()) {
                        continue;
                    }
                    entity.setId(oldEntity.getId());
                    this.updateByPrimaryKeySelective(entity);
                    continue;
                }
                entity.setGmtCreate(new Date());
                this.insertSelective(entity);
            }
        }
    }

    @Override
    public PagingResult<ReportMatchSourceEntity> getPageSourceEntity(String likeParams,
                                                                     String inParams,
                                                                     int pageNumber, int pageSize) {

        if (StringUtils.isBlank(inParams) && StringUtils.isBlank(likeParams)) {
            return new PagingResult<ReportMatchSourceEntity>(
                new ArrayList<ReportMatchSourceEntity>(), 0, pageNumber, pageSize);
        }
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            queryParams.put("inParams", strparamsConvertList(inParams));
        }
        //        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
        //            .countMatchEnrollByDetailParams(queryParams);

        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryCountMatchEnrollByParams(queryParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(results);
        List<ReportMatchSourceEntity> matchSource = mapToList(pageInfo.getList());

        return new PagingResult<ReportMatchSourceEntity>(matchSource, pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    public List<ReportMatchSourceEntity> mapToList(List<Map<String, Object>> list) {
        List<ReportMatchSourceEntity> result = new ArrayList<ReportMatchSourceEntity>();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                ReportMatchSourceEntity entity = new ReportMatchSourceEntity();
                String matchCode = (String) map.get("matchCode");
                String matchName = (String) map.get("matchName");
                String unitCode = (String) map.get("unitCode");
                String unitName = (String) map.get("unitName");
                BigDecimal enrollTotalCount = (BigDecimal) map.get("enrollTotalCount");
                BigDecimal organizerCount = (BigDecimal) map.get("organizerCount");
                BigDecimal officialCount = (BigDecimal) map.get("officialCount");
                entity.setMatchCode(matchCode);
                entity.setMatchName(matchName);
                entity.setUnitCode(unitCode);
                entity.setUnitName(unitName);
                entity.setEnrollTotalCount(enrollTotalCount.intValue());
                entity.setOrganizerCount(organizerCount.intValue());
                entity.setOfficialCount(officialCount.intValue());
                result.add(entity);
            }
        }
        return result;
    }

    public List<String> strparamsConvertList(String strs) {
        List<String> params = new ArrayList<String>();
        if (StringUtils.isBlank(strs)) {
            return params;
        }
        if (strs.indexOf(",") > 0) {
            String[] param = strs.split(",");
            for (int i = 0; i < param.length; i++) {
                params.add(param[i]);
            }
        } else {
            params.add(strs);
        }

        return params;
    }

    @Override
    public List<Map<String, Object>> officialEnrollCount(String likeParams, String inParams) {
        if (StringUtils.isBlank(inParams) && StringUtils.isBlank(likeParams)) {
            return new ArrayList<Map<String, Object>>();
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            map.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            List<String> params = strparamsConvertList(inParams);
            map.put("inParams", params);
        }
        List<Map<String, Object>> results = reportMatchSourceEntityMapper.officialEnrollCount(map);
        if (results != null && results.size() > 0) {
            for (Map<String, Object> result : results) {
                if (result != null && result.containsKey("matchCode")) {
                    String matchCode = (String) result.get("matchCode");
                    String matchName = (String) result.get("matchName");
                    if (StringUtils.isBlank(matchName)) {
                        matchName = getMatchName(matchCode);
                    }
                    int finshCount = reportMatchFinishService.officialMatchFinishCount(matchCode);
                    result.put("finishCount", finshCount);
                    result.put("matchName", matchName);
                    list.add(result);
                } else {
                    result.put("finishCount", 0);
                }
            }
        }
        return results;
    }

    @Override
    public PagingResult<ReportMatchSourceEntity> officialEnrollByParams(String likeParams,
                                                                        String inParams,
                                                                        int pageNumber,
                                                                        int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            queryParams.put("inParams", strparamsConvertList(inParams));
        }
        // #{start} , #{limit}

        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
            .officialEnrollByParams(queryParams);
        PageInfo<ReportMatchSourceEntity> pageInfo = new PageInfo<ReportMatchSourceEntity>(matchs);
        return new PagingResult<ReportMatchSourceEntity>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    @Override
    public List<Map<String, Object>> queryMatchNameByParams(String matchName) {
        List<Map<String, Object>> names = new ArrayList<Map<String, Object>>();
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(matchName)) {
            queryParams.put("matchName", matchName);
        }

        List<String> matchCodes = new ArrayList<String>();
        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryMatchNameByParams(queryParams);
        if (results != null && results.size() > 0) {
            for (Map<String, Object> result : results) {
                String matchCode = (String) result.get("matchCode");
                if (StringUtils.isNotBlank(matchCode)) {
                    matchCodes.add(matchCode);
                }
            }
        }
        List<SpMatchModel> models = spMatchFacadeClient.getMatchsByMatchCodes(matchCodes);
        if (models != null && models.size() > 0) {
            for (SpMatchModel model : models) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("matchName", model.getMatchName());
                map.put("matchCode", model.getMatchCode());
                names.add(map);
            }
        }
        return names;
    }

    @Override
    public List<SpMatchModel> queryMatchName(String matchName) {
        List<SpMatchModel> list = new ArrayList<SpMatchModel>();
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(matchName)) {
            queryParams.put("matchName", matchName);
        }

        List<String> matchCodes = new ArrayList<String>();
        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryMatchNameByParams(queryParams);
        if (results != null && results.size() > 0) {
            for (Map<String, Object> result : results) {
                String matchCode = (String) result.get("matchCode");
                String matchNames = (String) result.get("matchName");
                if (StringUtils.isNotBlank(matchCode) && StringUtils.isNotBlank(matchNames)) {
                    SpMatchModel model = new SpMatchModel();
                    model.setMatchCode(matchCode);
                    model.setMatchName(matchNames);
                    list.add(model);
                }
            }
        }
        return list;
        //        return spMatchFacadeClient.getMatchsByMatchCodes(matchCodes);
    }

    @Override
    public List<String> matchEnrollTop5(String type) {
        List<Map<String, Object>> top5 = null;
        if ("1".equals(type)) {//官方
            //            Map<String, Object> map = new HashMap<String, Object>();
            //            map.put("likeParams", null);
            //            map.put("inParams", null);
            //            map.put("default", "default");
            //            List<Map<String, Object>> results = reportMatchSourceEntityMapper
            //                .officialEnrollCount(map);
            //            return getMatchCode(results);
            top5 = reportMatchSourceEntityMapper.officialEnrollTop5();
        } else {

            top5 = reportMatchSourceEntityMapper.matchEnrollTop5();
        }
        return getMatchCode(top5);
    }

    public List<String> getMatchCode(List<Map<String, Object>> results) {
        List<String> array = new ArrayList<String>();
        if (results != null && results.size() > 0) {
            for (Map<String, Object> map : results) {
                String matchCode = (String) map.get("matchCode");
                array.add(matchCode);
            }
        }
        return array;
    }

    @Override
    public List<ReportMatchSourceEntity> getPageSourceEntityByExport(String likeParams,
                                                                     String inParams) {

        if (StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
            return new ArrayList<ReportMatchSourceEntity>();
        }
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            queryParams.put("inParams", strparamsConvertList(inParams));
        }

        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryCountMatchEnrollByParams(queryParams);
        List<ReportMatchSourceEntity> matchSource = mapToList(results);
        return matchSource;
    }

    @Override
    public List<ReportMatchSourceEntity> officialEnrollByEceport(String likeParams,
                                                                 String inParams) {
        if (StringUtils.isBlank(inParams) && StringUtils.isBlank(likeParams)) {
            return new ArrayList<ReportMatchSourceEntity>();
        }
        List<ReportMatchSourceEntity> results = new ArrayList<ReportMatchSourceEntity>();
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            queryParams.put("inParams", strparamsConvertList(inParams));
        }

        List<Map<String, Object>> matchs = reportMatchSourceEntityMapper
            .officialEnrollByDetailParams(queryParams);

        for (Map<String, Object> map : matchs) {
            ReportMatchSourceEntity entity = new ReportMatchSourceEntity();
            String matchName = (String) map.get("matchName");
            String matchCode = (String) map.get("matchCode");
            BigDecimal enrollTotalCount = (BigDecimal) map.get("officialCount");
            if (StringUtils.isBlank(matchName)) {
                matchName = getMatchName(matchCode);
            }
            entity.setMatchName(matchName);
            entity.setMatchCode(matchCode);
            entity.setOfficialCount(enrollTotalCount.intValue());
            results.add(entity);
        }
        return results;
    }

    @Override
    public PagingResult<ReportMatchSourceEntity> officialEnrollByDetailParams(String likeParams,
                                                                              String inParams,
                                                                              int pageNumber,
                                                                              int pageSize) {
        if (StringUtils.isBlank(inParams) && StringUtils.isBlank(likeParams)) {
            return new PagingResult<ReportMatchSourceEntity>(
                new ArrayList<ReportMatchSourceEntity>(), 0, pageNumber, pageSize);
        }
        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            queryParams.put("inParams", strparamsConvertList(inParams));
        }
        // #{start} , #{limit}

        List<Map<String, Object>> matchs = reportMatchSourceEntityMapper
            .officialEnrollByDetailParams(queryParams);

        //        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
        //            .officialEnrollByParams(queryParams);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(matchs);

        List<ReportMatchSourceEntity> results = new ArrayList<ReportMatchSourceEntity>();
        for (Map<String, Object> map : pageInfo.getList()) {
            ReportMatchSourceEntity entity = new ReportMatchSourceEntity();
            String matchName = (String) map.get("matchName");
            String matchCode = (String) map.get("matchCode");
            BigDecimal enrollTotalCount = (BigDecimal) map.get("officialCount");
            entity.setMatchName(matchName);
            entity.setMatchCode(matchCode);
            entity.setOfficialCount(enrollTotalCount.intValue());
            results.add(entity);
        }
        return new PagingResult<ReportMatchSourceEntity>(results, pageInfo.getTotal(), pageNumber,
            pageSize);
    }

    public String getMatchName(String matchCode) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryMatchNameByParams(queryParams);
        if (results != null && results.size() > 0) {
            for (Map<String, Object> result : results) {
                String code = (String) result.get("matchCode");
                if (matchCode.equals(code)) {
                    return code;
                }
            }
        }
        return "";
    }

    @Override
    public int enrolmentCount(String startTime, String endTime) {
        return reportMatchSourceEntityMapper.enrolmentCount(startTime, endTime);
    }

    @Override
    public int finishMatchCount(String startTime, String endTime) {
        return reportMatchSourceEntityMapper.finishMatchCount(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> countMatchEnrollPerson(String startDate, String endDate,
                                                            String matchCode) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<String> dates = new ArrayList<String>();
        if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
            startDate = DateUtil.format(DateUtil.getDayAgoTime(5), DateUtil.WEB_FORMAT);
            endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            dates = DateUtil.getAvgDateList(DateUtil.getDayAgoTime(5), new Date(), 5);
        } else {
            int difference = DateUtil.getDateDifference(startDate, endDate);
            dates = DateUtil.getAvgDateList(DateUtil.strToDate(startDate),
                DateUtil.strToDate(endDate), difference);
        }

        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
            .countMatchEnrollByParams(null, startDate, endDate);
        if (dates != null && dates.size() > 0) {
            for (String date : dates) {
                int enrollDay = getMatchTotalByCode(matchCode, date, matchs);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", date);//时间
                map.put("enrollDay", enrollDay);//单个赛事当日新增报名人次
                results.add(map);
            }
        }
        return results;
    }

    /**
     * 获取赛事某一天的报名总人次
     * 
     * @param matchCode
     * @param date
     * @param matchs
     * @return
     */
    public int getMatchTotalByCode(String matchCode, String date,
                                   List<ReportMatchSourceEntity> matchs) {
        if (matchs != null && matchs.size() > 0) {
            for (ReportMatchSourceEntity match : matchs) {
                String mdate = DateUtil.format(match.getApplyTimeDay(), DateUtil.WEB_FORMAT);
                if (matchCode.equals(match.getMatchCode()) && date.equals(mdate)) {
                    return match.getEnrollTotalCount();
                }
            }
        }
        return 0;
    }

    /**
     * 获取某一天赛事总报名人次
     * 
     * @param date
     * @return
     */
    public int getMatchTotalByDay(String date, List<ReportMatchSourceEntity> matchs) {
        int total = 0;
        if (matchs != null && matchs.size() > 0) {
            for (ReportMatchSourceEntity match : matchs) {
                String mdate = DateUtil.format(match.getApplyTimeDay(), DateUtil.WEB_FORMAT);
                if (date.equals(mdate)) {
                    total = total + match.getEnrollTotalCount();
                }
            }
        }
        return total;
    }

    @Override
    public PagingResult<Map<String, Object>> countMatchEnrollPersonByPage(String startDate,
                                                                          String endDate,
                                                                          String matchCode,
                                                                          int pageNumber,
                                                                          int pageSize) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<String> dates = new ArrayList<String>();
        if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
            startDate = DateUtil.format(DateUtil.getDayAgoTime(5), DateUtil.WEB_FORMAT);
            endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            dates = DateUtil.getAvgDateList(DateUtil.getDayAgoTime(5), new Date(), 5);
        } else {
            int difference = DateUtil.getDateDifference(startDate, endDate);
            dates = DateUtil.getAvgDateList(DateUtil.strToDate(startDate),
                DateUtil.strToDate(endDate), difference);
        }

        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
            .countMatchEnrollByParams(null, startDate, endDate);
        if (dates != null && dates.size() > 0) {
            for (String date : dates) {
                int enrollDay = getMatchTotalByCode(matchCode, date, matchs);
                int enrollTotalDay = getMatchTotalByDay(date, matchs);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", date);//时间
                map.put("enrollDay", enrollDay);//单个赛事当日新增报名人次
                map.put("enrollTotalDay", enrollTotalDay);//单日所有赛事报名人次
                results.add(map);
            }
        }
        int fromIndex = 0;
        int toIndex = 0;
        fromIndex = (pageNumber - 1) * pageSize;
        toIndex = pageNumber * pageSize;
        if (fromIndex > results.size()) {
            fromIndex = 0;
        }
        if (toIndex > results.size()) {
            toIndex = results.size();
        }
        List<Map<String, Object>> subList = results.subList(fromIndex, toIndex);
        return new PagingResult<Map<String, Object>>(subList, results.size(), pageNumber, pageSize);
    }

    @Override
    public List<Map<String, Object>> countMatchEnrollPersonByExport(String startDate,
                                                                    String endDate,
                                                                    String matchCode) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<String> dates = new ArrayList<String>();
        if (StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
            startDate = DateUtil.format(DateUtil.getDayAgoTime(5), DateUtil.WEB_FORMAT);
            endDate = DateUtil.format(new Date(), DateUtil.WEB_FORMAT);
            dates = DateUtil.getAvgDateList(DateUtil.getDayAgoTime(5), new Date(), 5);
        } else {
            int difference = DateUtil.getDateDifference(startDate, endDate);
            dates = DateUtil.getAvgDateList(DateUtil.strToDate(startDate),
                DateUtil.strToDate(endDate), difference);
        }

        List<ReportMatchSourceEntity> matchs = reportMatchSourceEntityMapper
            .countMatchEnrollByParams(null, startDate, endDate);
        if (dates != null && dates.size() > 0) {
            for (String date : dates) {
                int enrollDay = getMatchTotalByCode(matchCode, date, matchs);
                int enrollTotalDay = getMatchTotalByDay(date, matchs);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", date);//时间
                map.put("enrollDay", enrollDay);//单个赛事当日新增报名人次
                map.put("enrollTotalDay", enrollTotalDay);//单日所有赛事报名人次
                results.add(map);
            }
        }
        return results;
    }

    @Override
    public List<ReportMatchSourceEntity> queryReportMatch(String matchCode, Date applyTimeDay) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(matchCode)) {
            map.put("matchCode", matchCode);
        }
        if (applyTimeDay != null) {
            map.put("applyTimeDay", applyTimeDay);
        }
        return reportMatchSourceEntityMapper.queryMatchEnrollByCode(map);
    }

    @Override
    public void delReportMatch(int id) {
        reportMatchSourceEntityMapper.deleteByPrimaryKey(id);
    }

}
