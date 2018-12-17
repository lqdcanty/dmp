package com.efida.sports.dmp.biz.score.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.biz.score.OpenApplyinfoCountService;
import com.efida.sports.dmp.biz.score.ReportUnitEnrollService;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.ReportMatchSourceEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenUnitEntityMapper;
import com.efida.sports.dmp.utils.DateUtil;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: OpenApplyinfoServiceImpl.java, v 0.1 2018年8月29日 下午5:06:00 zengbo Exp $
 */
@Service("openApplyinfoCountService")
public class OpenApplyinfoCountServiceImpl implements OpenApplyinfoCountService {

    private static Logger             logger    = LoggerFactory
        .getLogger(OpenApplyinfoCountServiceImpl.class);

    private static final String       ORGANIZER = "organizer";

    @Autowired
    private OpenApplyInfoEntityMapper openApplyInfoEntityMapper;

    @Autowired
    private ReportUnitEnrollService   reportUnitEnrollService;

    @Autowired
    private OpenUnitEntityMapper      OpenUnitEntityMapper;

    @Override
    public List<String> queryUnitEnrollDate() {
        return openApplyInfoEntityMapper.queryUnitEnrollDate();
    }

    @Override
    public List<ReportUnitEnrollEntity> queryUnitEnrollCount(String date) {
        List<ReportUnitEnrollEntity> list = new ArrayList<ReportUnitEnrollEntity>();
        Map<String, Object> unitParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(date)) {
            unitParams.put("applyTime", date);
        }
        List<Map<String, Object>> applyinfos = openApplyInfoEntityMapper
            .queryUnitEnrollCount(unitParams);
        List<OpenUnitEntity> units = OpenUnitEntityMapper.queryLikeUnitName(null, null);
        if (applyinfos != null && applyinfos.size() > 0) {
            for (Map<String, Object> unitMap : applyinfos) {
                int enrollCount = 0;
                ReportUnitEnrollEntity entity = new ReportUnitEnrollEntity();
                String unitCode = StringUtils.defaultString((String) unitMap.get("unit_code"), "");
                if (unitMap.containsKey("counts") && unitMap.get("counts") != null) {
                    Long conut = (Long) unitMap.get("counts");
                    enrollCount = conut.intValue();
                }
                Date applyTime = (Date) unitMap.get("apply_time");
                String day = new SimpleDateFormat("yyyy-MM-dd").format(applyTime);
                int total = reportUnitEnrollService.countUnitEnrollByCode(unitCode, day);
                total = total + enrollCount;
                entity.setUnitCode(unitCode);
                entity.setUnitName(getUnitName(units, unitCode));
                entity.setEnrollCount(enrollCount);
                entity.setEnrollTotalCount(total);
                entity.setApplyTime(applyTime);
                entity.setApplyTimeDay(DateUtil.strToDate(day));
                list.add(entity);
            }
        }
        return list;
    }

    public static String getUnitName(List<OpenUnitEntity> units, String unitCode) {
        if (units != null && units.size() > 0) {
            for (OpenUnitEntity entity : units) {
                if (unitCode.equals(entity.getUnitCode())) {
                    String name = entity.getUnitName();
                    if (StringUtils.isNotBlank(name)) {
                        return name;
                    }
                }
            }
        }
        return "";
    }

    @Override
    public List<ReportMatchSourceEntity> queryMatchEnrollCount(String date) {
        List<ReportMatchSourceEntity> results = new ArrayList<ReportMatchSourceEntity>();
        Map<String, Object> organizerParams = new HashMap<String, Object>();
        organizerParams.put("source", 1);
        if (StringUtils.isNotBlank(date)) {
            organizerParams.put("applyTime", date);
        }
        Map<String, Object> officialParams = new HashMap<String, Object>();
        officialParams.put("source", 0);
        if (StringUtils.isNotBlank(date)) {
            officialParams.put("applyTime", date);
        }
        List<Map<String, Object>> organizermaps = openApplyInfoEntityMapper
            .queryMatchEnrollCount(organizerParams);
        List<Map<String, Object>> officialmaps = openApplyInfoEntityMapper
            .queryMatchEnrollCount(officialParams);
        List<OpenUnitEntity> units = OpenUnitEntityMapper.queryLikeUnitName(null, null);
        List<ReportMatchSourceEntity> organizers = this.getOrganizerCount(organizermaps, units);
        List<ReportMatchSourceEntity> officials = this.getOfficialCount(officialmaps, units);
        for (ReportMatchSourceEntity organizer : organizers) {
            if (organizer == null) {
                continue;
            }
            organizer.setEnrollTotalCount(organizer.getOrganizerCount());
            ReportMatchSourceEntity official = this.getReportMatchSourceEntity(
                organizer.getMatchCode(), organizer.getApplyTimeDay(), officials);
            if (official != null) {
                organizer.setOfficialCount(official.getOfficialCount());
                organizer.setEnrollTotalCount(
                    organizer.getOrganizerCount() + official.getOfficialCount());
                if (StringUtils.isBlank(organizer.getMatchName())) {
                    organizer.setMatchName(official.getMatchName());
                }
            }
            results.add(organizer);
        }
        for (ReportMatchSourceEntity official : officials) {
            ReportMatchSourceEntity organizer = this.getReportMatchSourceEntity(
                official.getMatchCode(), official.getApplyTimeDay(), results);
            if (organizer == null) {
                official.setEnrollTotalCount(official.getOfficialCount());
                results.add(official);
            }
        }
        return results;
    }

    public ReportMatchSourceEntity getReportMatchSourceEntity(String matchCode, Date applyTimeDay,
                                                              List<ReportMatchSourceEntity> list) {
        if (list == null || list.size() < 0) {
            return null;
        }
        for (ReportMatchSourceEntity entity : list) {
            if (entity != null && matchCode.equals(entity.getMatchCode())
                && applyTimeDay.getTime() == entity.getApplyTimeDay().getTime()) {
                return entity;
            }
        }
        return null;
    }

    /**
     * 获取承办方报名数据
     * 
     * @param applyinfos
     * @return
     */
    public List<ReportMatchSourceEntity> getOrganizerCount(List<Map<String, Object>> applyinfos,
                                                           List<OpenUnitEntity> units) {
        List<ReportMatchSourceEntity> list = new ArrayList<ReportMatchSourceEntity>();
        if (applyinfos != null && applyinfos.size() > 0) {
            for (Map<String, Object> matchMap : applyinfos) {
                ReportMatchSourceEntity entity = mapCovertEntity(matchMap, ORGANIZER, units);
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 获取官网报名数据
     * 
     * @param applyinfos
     * @return
     */
    public List<ReportMatchSourceEntity> getOfficialCount(List<Map<String, Object>> applyinfos,
                                                          List<OpenUnitEntity> units) {
        List<ReportMatchSourceEntity> list = new ArrayList<ReportMatchSourceEntity>();
        if (applyinfos != null && applyinfos.size() > 0) {
            for (Map<String, Object> matchMap : applyinfos) {
                ReportMatchSourceEntity entity = mapCovertEntity(matchMap, "", units);
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 统计数据转换为对象
     * 
     * @param matchMap
     * @param type
     * @return
     */
    public ReportMatchSourceEntity mapCovertEntity(Map<String, Object> matchMap, String type,
                                                   List<OpenUnitEntity> units) {
        ReportMatchSourceEntity entity = new ReportMatchSourceEntity();
        int enrollCount = 0;
        String matchCode = StringUtils.defaultString((String) matchMap.get("match_code"), "");
        String matchName = StringUtils.defaultString((String) matchMap.get("match_name"), "");
        String unitCode = StringUtils.defaultString((String) matchMap.get("unit_code"), "");
        String unitName = getUnitName(units, unitCode);
        Date applyTime = (Date) matchMap.get("apply_time");
        String day = new SimpleDateFormat("yyyy-MM-dd").format(applyTime);
        if (matchMap.containsKey("counts") && matchMap.get("counts") != null) {
            Long conut = (Long) matchMap.get("counts");
            enrollCount = conut.intValue();
        }
        entity.setMatchCode(matchCode);
        entity.setMatchName(matchName);
        entity.setUnitCode(unitCode);
        entity.setUnitName(unitName);
        entity.setApplyTime(applyTime);
        entity.setApplyTimeDay(DateUtil.strToDate(day));
        if (ORGANIZER.equals(type)) {
            entity.setOrganizerCount(enrollCount);
            entity.setOfficialCount(0);
        } else {
            entity.setOfficialCount(enrollCount);
            entity.setOrganizerCount(0);
        }
        return entity;
    }

}
