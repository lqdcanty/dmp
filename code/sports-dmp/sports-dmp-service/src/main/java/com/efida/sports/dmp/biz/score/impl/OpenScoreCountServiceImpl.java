package com.efida.sports.dmp.biz.score.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.biz.score.OpenScoreCountService;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.ReportMatchFinishEntity;
import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;
import com.efida.sports.dmp.dao.mapper.OpenScoreEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenUnitEntityMapper;
import com.efida.sports.dmp.utils.DateUtil;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: OpenScoreCountServiceImpl.java, v 0.1 2018年8月29日 下午9:00:19 zengbo Exp $
 */
@Service("openScoreCountService")
public class OpenScoreCountServiceImpl implements OpenScoreCountService {

    //接口数据
    private static final String   OFFICIAL = "official";

    @Autowired
    private OpenScoreEntityMapper openScoreEntityMapper;

    @Autowired
    private OpenUnitEntityMapper  OpenUnitEntityMapper;

    @Override
    public List<String> queryUnitScoreDate() {
        return openScoreEntityMapper.queryUnitScoreDate();
    }

    @Override
    public List<ReportUnitFinishEntity> queryUnitScoreCount(String date) {
        List<ReportUnitFinishEntity> list = new ArrayList<ReportUnitFinishEntity>();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(date)) {
            map.put("partTime", date);
        }
        List<Map<String, Object>> scoreMaps = openScoreEntityMapper.queryUnitScoreByCount(map);
        List<OpenUnitEntity> units = OpenUnitEntityMapper.queryLikeUnitName(null, null);
        if (scoreMaps != null && scoreMaps.size() > 0) {
            for (Map<String, Object> scoreMap : scoreMaps) {
                ReportUnitFinishEntity entity = new ReportUnitFinishEntity();
                int finishCont = 0;
                String unitCode = StringUtils.defaultString((String) scoreMap.get("unit_code"), "");
                String unitName = OpenApplyinfoCountServiceImpl.getUnitName(units, unitCode);
                if (scoreMap.containsKey("counts") && scoreMap.get("counts") != null) {
                    Long conut = (Long) scoreMap.get("counts");
                    finishCont = conut.intValue();
                }
                Date partTime = (Date) scoreMap.get("part_time");
                String day = new SimpleDateFormat("yyyy-MM-dd").format(partTime);
                entity.setUnitCode(unitCode);
                entity.setUnitName(unitName);
                entity.setFinishCont(finishCont);
                entity.setPartTime(partTime);
                entity.setPartTimeDay(DateUtil.strToDate(day));
                list.add(entity);
            }
        }
        return list;
    }

    @Override
    public List<ReportMatchFinishEntity> queryMatchScoreCount(String date) {
        List<ReportMatchFinishEntity> results = new ArrayList<ReportMatchFinishEntity>();
        Map<String, Object> organizerParams = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(date)) {
            organizerParams.put("partTime", date);
        }
        List<Map<String, Object>> organizermaps = openScoreEntityMapper
            .queryMatchScoreByCount(organizerParams);
        List<OpenUnitEntity> units = OpenUnitEntityMapper.queryLikeUnitName(null, null);
        List<ReportMatchFinishEntity> organizers = this.getOrganizerCount(organizermaps, units);
        List<ReportMatchFinishEntity> officials = this.getOfficialCount(organizermaps, units);
        for (ReportMatchFinishEntity organizer : organizers) {
            ReportMatchFinishEntity official = this.getReportMatchSourceEntity(
                organizer.getMatchCode(), organizer.getPartTimeDay(), officials);
            if (official != null) {
                organizer.setFinishCount(official.getFinishCount());
                organizer
                    .setFinishTotalCount(organizer.getOrganizerCount() + official.getFinishCount());
            } else {
                organizer.setFinishCount(0);
                organizer.setFinishTotalCount(organizer.getOrganizerCount());
            }
            results.add(organizer);
        }
        for (ReportMatchFinishEntity official : officials) {
            ReportMatchFinishEntity organizer = this.getReportMatchSourceEntity(
                official.getMatchCode(), official.getPartTimeDay(), results);
            if (organizer == null) {
                official.setOrganizerCount(0);
                official.setFinishCount(official.getFinishCount());
                official.setFinishTotalCount(official.getFinishCount());
                results.add(official);
            }
        }
        return results;
    }

    public ReportMatchFinishEntity getReportMatchSourceEntity(String matchCode, Date partTimeDay,
                                                              List<ReportMatchFinishEntity> list) {
        if (list == null || list.size() < 0) {
            return null;
        }
        for (ReportMatchFinishEntity entity : list) {
            if (entity != null && matchCode.equals(entity.getMatchCode())
                && partTimeDay.getTime() == entity.getPartTimeDay().getTime()) {
                return entity;
            }
        }
        return null;
    }

    /**
     * 获取承办方完赛数据
     * 
     * @param applyinfos
     * @return
     */
    public List<ReportMatchFinishEntity> getOrganizerCount(List<Map<String, Object>> applyinfos,
                                                           List<OpenUnitEntity> units) {
        List<ReportMatchFinishEntity> list = new ArrayList<ReportMatchFinishEntity>();
        int enrollSource = 2;
        if (applyinfos != null && applyinfos.size() > 0) {
            for (Map<String, Object> matchMap : applyinfos) {
                if (matchMap.containsKey("enroll_source")
                    && matchMap.get("enroll_source") != null) {
                    enrollSource = (int) matchMap.get("enroll_source");
                }
                if (enrollSource != 1) {
                    ReportMatchFinishEntity entity = scoremapCovertEntity(matchMap, units, null);
                    System.out.println(DateUtil.format(entity.getPartTimeDay(), DateUtil.WEB_FORMAT)
                                       + "——" + entity.getOrganizerCount());
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 获取官网完赛数据
     * 
     * @param applyinfos
     * @return
     */
    public List<ReportMatchFinishEntity> getOfficialCount(List<Map<String, Object>> applyinfos,
                                                          List<OpenUnitEntity> units) {
        int enrollSource = 2;
        List<ReportMatchFinishEntity> list = new ArrayList<ReportMatchFinishEntity>();
        if (applyinfos != null && applyinfos.size() > 0) {
            for (Map<String, Object> matchMap : applyinfos) {
                if (matchMap.containsKey("enroll_source")
                    && matchMap.get("enroll_source") != null) {
                    enrollSource = (int) matchMap.get("enroll_source");
                }
                if (enrollSource == 1) {
                    ReportMatchFinishEntity entity = scoremapCovertEntity(matchMap, units,
                        OFFICIAL);
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
    public ReportMatchFinishEntity scoremapCovertEntity(Map<String, Object> matchMap,
                                                        List<OpenUnitEntity> units, String type) {
        ReportMatchFinishEntity entity = new ReportMatchFinishEntity();
        int finishCount = 0;
        String matchCode = StringUtils.defaultString((String) matchMap.get("match_code"), "");
        String matchName = StringUtils.defaultString((String) matchMap.get("match_name"), "");
        String unitCode = StringUtils.defaultString((String) matchMap.get("unit_code"), "");
        String unitName = OpenApplyinfoCountServiceImpl.getUnitName(units, unitCode);
        Date partTime = (Date) matchMap.get("part_time");
        String day = new SimpleDateFormat("yyyy-MM-dd").format(partTime);
        if (matchMap.containsKey("counts") && matchMap.get("counts") != null) {
            Long conut = (Long) matchMap.get("counts");
            finishCount = conut.intValue();
        }
        if (OFFICIAL.equals(type)) {
            entity.setFinishCount(finishCount);
            entity.setOrganizerCount(0);
        } else {
            entity.setOrganizerCount(finishCount);
            entity.setFinishCount(0);
        }
        entity.setMatchCode(matchCode);
        entity.setMatchName(matchName);
        entity.setUnitCode(unitCode);
        entity.setUnitName(unitName);
        entity.setPartTime(partTime);
        entity.setPartTimeDay(DateUtil.strToDate(day));
        return entity;
    }

}
