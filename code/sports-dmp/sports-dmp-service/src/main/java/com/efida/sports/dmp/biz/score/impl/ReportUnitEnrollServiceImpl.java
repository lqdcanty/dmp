package com.efida.sports.dmp.biz.score.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.efida.sports.dmp.biz.dto.ReportUnitEnrollDto;
import com.efida.sports.dmp.biz.score.ReportMatchSourceService;
import com.efida.sports.dmp.biz.score.ReportUnitEnrollService;
import com.efida.sports.dmp.biz.score.ReportUnitFinishService;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.dao.mapper.OpenUnitEntityMapper;
import com.efida.sports.dmp.dao.mapper.ReportMatchFinishEntityMapper;
import com.efida.sports.dmp.dao.mapper.ReportMatchSourceEntityMapper;
import com.efida.sports.dmp.dao.mapper.ReportUnitEnrollEntityMapper;
import com.efida.sports.dmp.dao.mapper.ReportUnitFinishEntityMapper;
import com.efida.sports.dmp.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollServiceImpl.java, v 0.1 2018年8月29日 下午11:07:31 zengbo Exp $
 */
@Service("reportUnitEnrollService")
public class ReportUnitEnrollServiceImpl implements ReportUnitEnrollService {

    private static Logger                 logger = LoggerFactory
        .getLogger(ReportUnitEnrollServiceImpl.class);

    @Autowired
    private ReportUnitEnrollEntityMapper  reportUnitEnrollEntityMapper;

    @Autowired
    private ReportUnitFinishService       reportUnitFinishService;

    @Autowired
    private ReportUnitEnrollService       reportUnitEnrollService;

    @Autowired
    private ReportMatchSourceService      reportMatchSourceService;

    @Autowired
    private OpenUnitEntityMapper          openUnitEntityMapper;

    @Autowired
    private ReportUnitFinishEntityMapper  reportUnitFinishEntityMapper;

    @Autowired
    private ReportMatchFinishEntityMapper reportMatchFinishEntityMapper;

    @Autowired
    private ReportMatchSourceEntityMapper reportMatchSourceEntityMapper;

    @Override
    public int insert(ReportUnitEnrollEntity record) {
        return reportUnitEnrollEntityMapper.insert(record);
    }

    @Override
    public int insertSelective(ReportUnitEnrollEntity record) {
        return reportUnitEnrollEntityMapper.insertSelective(record);
    }

    @Override
    public ReportUnitEnrollEntity selectByPrimaryKey(Long id) {
        return reportUnitEnrollEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportUnitEnrollEntity record) {
        return reportUnitEnrollEntityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ReportUnitEnrollEntity record) {
        return reportUnitEnrollEntityMapper.updateByPrimaryKey(record);
    }

    @Override
    public void saveReportUnitList(List<ReportUnitEnrollEntity> list) {
        if (list != null && list.size() > 0) {
            for (ReportUnitEnrollEntity entity : list) {
                entity.setGmtModified(new Date());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("unitCode", entity.getUnitCode());
                map.put("applyTimeDay", entity.getApplyTimeDay());
                List<ReportUnitEnrollEntity> result = reportUnitEnrollEntityMapper
                    .queryEnrollByCode(map);
                if (result != null && result.size() > 0) {
                    ReportUnitEnrollEntity oldEntity = result.get(0);
                    if (entity.getEnrollCount() == oldEntity.getEnrollCount()
                        && entity.getEnrollTotalCount() == oldEntity.getEnrollTotalCount()) {
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
    public int countUnitEnrollByCode(String unitCode, String applyTimeDay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", unitCode);
        map.put("applyTimeDay", applyTimeDay);
        Map<String, Object> result = reportUnitEnrollEntityMapper.countUnitEnrollByCode(map);
        if (result != null) {
            BigDecimal count = (BigDecimal) result.get("enrollCount");
            return count.intValue();
        }
        return 0;
    }

    @Override
    public int countTotalUnitEnroll(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(startTime)) {
            map.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            map.put("endTime", endTime);
        }
        Map<String, Object> result = reportMatchSourceEntityMapper.countEnrollByParams(map);
        if (result != null) {
            BigDecimal count = (BigDecimal) result.get("enrollCount");
            return count.intValue();
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> countUnitEnrollGroup() {
        //        List<Map<String, Object>> lists = reportUnitEnrollEntityMapper.countUnitEnrollGroup();
        //        return dislodgeEnrollGroup(lists);
        return reportMatchSourceEntityMapper.countAllUnitEnroll();
    }

    public List<Map<String, Object>> dislodgeEnrollGroup(List<Map<String, Object>> lists) {
        List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
        //        int enroll = reportUnitEnrollService.countTotalUnitEnroll(null, null);
        List<String> unitCodes = reportUnitEnrollService.queryTop5UnitEnroll("all");

        if (lists != null && lists.size() > 0) {
            int total = 0;
            for (String unitCode : unitCodes) {//需求调整为取TOP的数据
                for (Map<String, Object> map : lists) {
                    //                BigDecimal count = (BigDecimal) map.get("value");
                    //                double f1 = new BigDecimal((float) count.intValue() / enroll)
                    //                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    //                if (f1 > 0.05) {
                    //                    groups.add(map);
                    //                }
                    String codes = (String) map.get("unitCode");
                    if (unitCode.equals(codes)) {
                        BigDecimal count = (BigDecimal) map.get("value");
                        total = total + count.intValue();
                        groups.add(map);
                    }
                }
            }
        }
        return groups;
    }

    @Override
    public List<Map<String, Object>> countToealUnitEnrollByParams(String startTime, String endTime,
                                                                  String likeParams,
                                                                  String inParams) {
        if (StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
            return new ArrayList<Map<String, Object>>();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            map.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            List<String> params = strparamsConvertList(inParams);
            map.put("inParams", params);
        }
        if (StringUtils.isNotBlank(startTime)) {
            map.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            map.put("endTime", endTime);
        }
        if (StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
            map.put("default", "default");
        }
        List<Map<String, Object>> results = reportUnitEnrollEntityMapper
            .countUnitEnrollByParams(map);
        if (results != null && results.size() > 0) {
            //            List<Map<String, Object>> finishs = reportUnitFinishEntityMapper
            //                .queryAllFinishOfficial(null);
            List<Map<String, Object>> finishs = reportMatchFinishEntityMapper
                .organizerMatchFinishCount(null);
            for (Map<String, Object> result : results) {
                String unitCode = (String) result.get("unitCode");
                //                int finshCount = reportUnitFinishService.officialMatchFinishCount(unitCode);
                int finshCount = this.getFinishCount(unitCode, finishs);
                result.put("finshCount", finshCount);
            }
        }

        return results;
    }

    public int getFinishCount(String unitCode, List<Map<String, Object>> finishs) {
        if (finishs == null || finishs.size() <= 0) {
            return 0;
        }
        for (Map<String, Object> finish : finishs) {
            String finishCode = (String) finish.get("unitCode");
            if (unitCode.equals(finishCode)) {
                BigDecimal finshCount = (BigDecimal) finish.get("organizerCount");
                return finshCount.intValue();
            }
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> countUnitEnrollByParams(String startTime, String endTime,
                                                             String likeParams, String inParams) {
        if (StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
            return new ArrayList<Map<String, Object>>();
        }
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<String> unitCodes = new ArrayList<String>();
        List<String> unitDates = new ArrayList<String>();
        if (StringUtils.isNotBlank(inParams)) {
            unitCodes = strparamsConvertList(inParams);
        } else {
            List<Map<String, Object>> units = reportUnitEnrollEntityMapper
                .queryTop5UnitEnroll(null);
            if (units != null && units.size() > 0) {
                unitCodes = new ArrayList<String>();
                for (Map<String, Object> unit : units) {
                    String unitCode = (String) unit.get("unitCode");
                    unitCodes.add(unitCode);
                }
            }
        }
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            unitDates = DateUtil.getAvgDateList(DateUtil.parseStr(startTime),
                DateUtil.parseStr(endTime), 6);
        } else {
            unitDates = getBeforeWeek();
        }
        //        Map<String, Object> map = new HashMap<String, Object>();
        //        if (StringUtils.isNotBlank(likeParams)) {
        //            unitNames = queryLikeUnitName(likeParams);
        //        }
        //        if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
        //            map.put("startTime", startTime);
        //        }
        //        if (StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)) {
        //            map.put("endTime", endTime);
        //        }
        //当为默认时获取承办方top5 前一周数据
        //        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)
        //            && StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
        //            List<Map<String, Object>> units = reportUnitEnrollEntityMapper.queryTop5UnitEnroll();
        //            if (units != null && units.size() > 0) {
        //                unitNames = new ArrayList<String>();
        //                for (Map<String, Object> unit : units) {
        //                    String unitName = (String) unit.get("unitName");
        //                    unitNames.add(unitName);
        //                }
        //            }
        //            unitDates = getBeforeWeek();
        //        }
        List<ReportUnitEnrollEntity> result = reportUnitEnrollEntityMapper.queryAllUnitEnroll();
        List<OpenUnitEntity> openUnit = openUnitEntityMapper.queryLikeUnitName(null, null);
        if (unitCodes != null && unitCodes.size() > 0) {
            for (String unitCode : unitCodes) {
                Map<String, Object> unitMap = new HashMap<String, Object>();
                List<ReportUnitEnrollEntity> units = new ArrayList<ReportUnitEnrollEntity>();
                for (String unitDate : unitDates) {
                    ReportUnitEnrollEntity entity = getReportUnitEnroll(result, unitCode, unitDate);
                    if (entity == null) {
                        entity = getDefaultData(unitCode, unitDate, openUnit);
                    }
                    units.add(entity);
                }
                unitMap.put("unitName",
                    OpenApplyinfoCountServiceImpl.getUnitName(openUnit, unitCode));
                unitMap.put("unitCode", unitCode);
                unitMap.put("data", ReportUnitEnrollDto.coverToVos(units));
                results.add(unitMap);
            }
        }
        return results;
    }

    /**
     * 从内存里面去承办方数据
     * 
     * @param result
     * @param unitName
     * @param unitDate
     * @return
     */
    public ReportUnitEnrollEntity getReportUnitEnroll(List<ReportUnitEnrollEntity> result,
                                                      String unitCode, String unitDate) {
        if (result != null && result.size() > 0) {
            for (ReportUnitEnrollEntity entity : result) {
                String applyTime = DateUtil.formatDate(entity.getApplyTimeDay());
                if (unitCode.equals(entity.getUnitCode()) && unitDate.equals(applyTime)) {
                    return entity;
                }
            }
        }
        return null;
    }

    /**
     * 补全空数据
     * 
     * @param unitName
     * @param unitDate
     * @return
     */
    public ReportUnitEnrollEntity getDefaultData(String unitCode, String unitDate,
                                                 List<OpenUnitEntity> units) {
        ReportUnitEnrollEntity entity = new ReportUnitEnrollEntity();
        entity.setUnitCode(unitCode);
        entity.setUnitName(OpenApplyinfoCountServiceImpl.getUnitName(units, unitCode));
        entity.setApplyTime(DateUtil.parseStr(unitDate));
        entity.setEnrollCount(0);
        entity.setEnrollTotalCount(0);
        return entity;
    }

    /**
     * 获取前一周的时间
     * 
     * @return
     */
    public List<String> getBeforeWeek() {
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -6);
        Date d = c.getTime();
        String day = format.format(d);
        String cacheDay = "";
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                cacheDay = day;
                dates.add(day);
                continue;
            }
            Date date = DateUtil.strToDate(cacheDay);
            Date newDate = DateUtil.addDay(date, 1);
            String newStr = DateUtil.formatDate(newDate);
            cacheDay = newStr;
            dates.add(newStr);
        }
        return dates;
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
    public PagingResult<ReportUnitEnrollEntity> queryUnitEnrollByPage(String startTime,
                                                                      String endTime,
                                                                      String likeParams,
                                                                      String inParams,
                                                                      int pageNumber,
                                                                      int pageSize) {

        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            List<String> params = strparamsConvertList(inParams);
            queryParams.put("inParams", params);
        }
        if (StringUtils.isNotBlank(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            queryParams.put("endTime", endTime);
        }
        List<ReportUnitEnrollEntity> matchs = reportUnitEnrollEntityMapper
            .countUnitEnrollByPages(queryParams);
        PageInfo<ReportUnitEnrollEntity> pageInfo = new PageInfo<ReportUnitEnrollEntity>(matchs);
        return new PagingResult<ReportUnitEnrollEntity>(pageInfo.getList(), pageInfo.getTotal(),
            pageNumber, pageSize);
    }

    @Override
    public List<String> queryLikeUnitName(String unitName) {
        List<String> names = new ArrayList<String>();
        List<OpenUnitEntity> list = openUnitEntityMapper.queryLikeUnitName(unitName, null);
        if (list != null && list.size() > 0) {
            for (OpenUnitEntity entity : list) {
                names.add(entity.getUnitName());
            }
        }
        return names;
    }

    @Override
    public List<String> queryTop5UnitEnroll(String type) {
        List<String> list = new ArrayList<String>();
        Date date = DateUtil.getDayAgoTime(7);
        List<Map<String, Object>> units = null;
        if (StringUtils.isNotBlank(type)) {
            units = reportUnitEnrollEntityMapper.queryTop5UnitEnroll(null);
        } else {
            units = reportUnitEnrollEntityMapper.queryTop5UnitEnroll(date);
        }
        if (units != null && units.size() > 0) {
            for (Map<String, Object> unit : units) {
                String unitCode = (String) unit.get("unitCode");
                list.add(unitCode);
            }
        }
        return list;
    }

    @Override
    public String queryUnitEnrollTime(String sortParam) {
        String date = "";
        String result = reportUnitEnrollEntityMapper.queryUnitEnrollStartTime();
        if (StringUtils.isNotBlank(result)) {
            date = result.split(" ")[0];
        }
        return date;
    }

    @Override
    public List<ReportUnitEnrollEntity> queryUnitEnrollByExport(String startTime, String endTime,
                                                                String likeParams,
                                                                String inParams) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            List<String> params = strparamsConvertList(inParams);
            queryParams.put("inParams", params);
        }
        if (StringUtils.isNotBlank(startTime)) {
            queryParams.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            queryParams.put("endTime", endTime);
        }
        return reportUnitEnrollEntityMapper.countUnitEnrollByPages(queryParams);
    }

    @Override
    public List<Map<String, Object>> countUnitEnrollByParamsNew(String date, String inParams) {
        if (StringUtils.isBlank(inParams)) {
            return new ArrayList<Map<String, Object>>();
        }
        if (StringUtils.isBlank(date)) {
            date = DateUtil.formatDate(new Date());
        }
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<String> unitCodes = new ArrayList<String>();
        if (StringUtils.isNotBlank(inParams)) {
            unitCodes = strparamsConvertList(inParams);
        } else {//为空获取TOP5的数据
            List<String> codes = this.queryTop5UnitEnroll(null);
            if (codes != null && codes.size() > 0) {
                for (String code : codes) {
                    unitCodes.add(code);
                }
            }
            //            List<Map<String, Object>> units = reportUnitEnrollEntityMapper
            //                .queryTop5UnitEnroll(null);
            //            if (units != null && units.size() > 0) {
            //                unitCodes = new ArrayList<String>();
            //                for (Map<String, Object> unit : units) {
            //                    String unitCode = (String) unit.get("unitCode");
            //                    unitCodes.add(unitCode);
            //                }
            //            }
        }
        List<ReportUnitEnrollEntity> result = reportUnitEnrollEntityMapper.queryAllUnitEnroll();
        List<OpenUnitEntity> openUnit = openUnitEntityMapper.queryLikeUnitName(null, null);
        if (unitCodes != null && unitCodes.size() > 0) {
            for (String unitCode : unitCodes) {
                Map<String, Object> unitMap = new HashMap<String, Object>();
                ReportUnitEnrollEntity entity = getReportUnitEnroll(result, unitCode, date);
                if (entity == null) {
                    entity = getDefaultData(unitCode, date, openUnit);
                }
                String unitName = entity.getUnitName();
                if (StringUtils.isBlank(unitName)) {
                    unitName = OpenApplyinfoCountServiceImpl.getUnitName(openUnit, unitCode);
                }
                unitMap.put("unitName", unitName);
                unitMap.put("unitCode", unitCode);
                unitMap.put("enrollCount", entity.getEnrollCount());
                results.add(unitMap);
            }
        }
        return results;
    }

    @Override
    public PagingResult<ReportUnitEnrollEntity> queryUnitEnrollByPageNew(String date,
                                                                         String inParams,
                                                                         int pageNumber,
                                                                         int pageSize) {
        if (StringUtils.isBlank(inParams)) {
            return new PagingResult<ReportUnitEnrollEntity>(new ArrayList<ReportUnitEnrollEntity>(),
                0, pageNumber, pageSize);
        }
        if (StringUtils.isBlank(date)) {
            date = DateUtil.formatDate(new Date());
        }
        List<String> unitCodes = reportUnitEnrollService.queryTop5UnitEnroll(null);
        //        PageHelper.startPage(pageNumber, pageSize);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        List<String> params = new ArrayList<String>();
        if (StringUtils.isNotBlank(inParams)) {
            params = strparamsConvertList(inParams);
        } else {
            if (unitCodes != null && unitCodes.size() > 0) {
                for (String unitCode : unitCodes) {
                    params.add(unitCode);
                }
            }
        }
        queryParams.put("inParams", params);
        if (StringUtils.isNotBlank(date)) {
            queryParams.put("startTime", date);
        }
        List<ReportUnitEnrollEntity> matchs = reportUnitEnrollEntityMapper
            .countUnitEnrollByPages(queryParams);
        //        logger.info("分页查询到的数据" + JSON.toJSONString(matchs));
        //        PageInfo<ReportUnitEnrollEntity> pageInfo = new PageInfo<ReportUnitEnrollEntity>(matchs);
        //        List<ReportUnitEnrollEntity> matchs = reportUnitEnrollEntityMapper.queryAllUnitEnroll();
        List<ReportUnitEnrollEntity> resultList = dataPerfect(matchs, params, date);
        int fromIndex = 0;
        int toIndex = 0;
        fromIndex = (pageNumber - 1) * pageSize;
        toIndex = pageNumber * pageSize;
        if (fromIndex > resultList.size()) {
            fromIndex = 0;
        }
        if (toIndex > resultList.size()) {
            toIndex = resultList.size();
        }
        List<ReportUnitEnrollEntity> subList = resultList.subList(fromIndex, toIndex);
        return new PagingResult<ReportUnitEnrollEntity>(subList, resultList.size(), pageNumber,
            pageSize);
    }

    public ReportUnitEnrollEntity getTestData(String unitCode, String date) {
        List<ReportUnitEnrollEntity> result = reportUnitEnrollEntityMapper.queryAllUnitEnroll();
        for (ReportUnitEnrollEntity entity : result) {
            if (unitCode.equals(entity.getUnitCode())
                && date.equals(DateUtil.formatDate(entity.getApplyTimeDay()))) {
                return entity;
            }
        }
        return null;
    }

    public List<ReportUnitEnrollEntity> dataPerfect(List<ReportUnitEnrollEntity> resultList,
                                                    List<String> unitCodes, String date) {
        List<OpenUnitEntity> openUnit = openUnitEntityMapper.queryLikeUnitName(null, null);
        List<ReportUnitEnrollEntity> list = new ArrayList<ReportUnitEnrollEntity>();

        if (resultList != null && resultList.size() > 0) {
            for (String code : unitCodes) {
                ReportUnitEnrollEntity entity = isContineEntity(code, resultList);
                if (entity != null) {
                    list.add(entity);
                } else {
                    entity = createReportUnitEnroll(code, date, openUnit);
                    list.add(entity);
                }
            }
        } else {
            for (String code : unitCodes) {
                ReportUnitEnrollEntity entity = createReportUnitEnroll(code, date, openUnit);
                list.add(entity);
            }
        }
        return list;
    }

    public ReportUnitEnrollEntity createReportUnitEnroll(String code, String date,
                                                         List<OpenUnitEntity> openUnit) {
        ReportUnitEnrollEntity entity = new ReportUnitEnrollEntity();
        String unitName = OpenApplyinfoCountServiceImpl.getUnitName(openUnit, code);
        entity.setUnitCode(code);
        entity.setUnitName(unitName);
        entity.setEnrollCount(0);
        entity.setEnrollTotalCount(0);
        entity.setApplyTime(DateUtil.parseStr(date));
        entity.setApplyTimeDay(DateUtil.parseStr(date));
        return entity;
    }

    public ReportUnitEnrollEntity isContineEntity(String code,
                                                  List<ReportUnitEnrollEntity> resultList) {
        if (resultList != null && resultList.size() > 0) {
            for (ReportUnitEnrollEntity entity : resultList) {
                if (code.equals(entity.getUnitCode())) {
                    logger.info("符合条件的数据:" + JSON.toJSONString(entity));
                    return entity;
                }
            }
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> queryAllUnitEnroll(String unitCode) {
        return reportUnitEnrollEntityMapper.queryAllUnitEnrollByCode(null);
    }

    @Override
    public List<ReportUnitEnrollEntity> queryUnitEnrollByExportNew(String date, String inParams) {
        if (StringUtils.isBlank(inParams) && StringUtils.isBlank(date)) {
            return new ArrayList<ReportUnitEnrollEntity>();
        }
        List<String> params = new ArrayList<String>();
        if (StringUtils.isBlank(date)) {
            date = DateUtil.formatDate(new Date());
        }
        List<String> unitCodes = reportUnitEnrollService.queryTop5UnitEnroll(null);
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(inParams)) {
            params = strparamsConvertList(inParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            params = strparamsConvertList(inParams);
        } else {
            if (unitCodes != null && unitCodes.size() > 0) {
                for (String unitCode : unitCodes) {
                    params.add(unitCode);
                }
            }
        }
        queryParams.put("inParams", params);
        if (StringUtils.isNotBlank(date)) {
            queryParams.put("startTime", date);
        }
        List<ReportUnitEnrollEntity> matchs = reportUnitEnrollEntityMapper
            .countUnitEnrollByPages(queryParams);

        //        List<ReportUnitEnrollEntity> matchs = reportUnitEnrollEntityMapper.queryAllUnitEnroll();
        List<ReportUnitEnrollEntity> resultList = dataPerfect(matchs, params, date);
        return resultList;
    }

    @Override
    public int getTotalApplyPeople() {
        return reportUnitEnrollEntityMapper.getTotalApplyPeople();
    }

    @Override
    public int getTotalFinishPeople() {
        return reportUnitEnrollEntityMapper.getTotalFinishPeople();
    }

    @Override
    public List<Map<String, Object>> countToealUnitEnrollByParams(String likeParams,
                                                                  String inParams) {
        if (StringUtils.isBlank(likeParams) && StringUtils.isBlank(inParams)) {
            return new ArrayList<Map<String, Object>>();
        }
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(likeParams)) {
            queryParams.put("likeParams", likeParams);
        }
        if (StringUtils.isNotBlank(inParams)) {
            List<String> params = strparamsConvertList(inParams);
            queryParams.put("inParams", params);
        }
        List<Map<String, Object>> results = reportMatchSourceEntityMapper
            .queryCountUnitEnrollByParams(queryParams);
        if (results != null && results.size() > 0) {
            List<Map<String, Object>> finishs = reportMatchFinishEntityMapper
                .organizerMatchFinishCount(null);
            for (Map<String, Object> result : results) {
                String unitCode = (String) result.get("unitCode");
                //                int finshCount = reportUnitFinishService.officialMatchFinishCount(unitCode);
                int finshCount = this.getFinishCount(unitCode, finishs);
                result.put("finshCount", finshCount);
            }
        }
        return results;
    }

}
