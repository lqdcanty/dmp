package com.efida.sports.dmp.biz.score.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sports.dmp.biz.score.ReportMatchFinishService;
import com.efida.sports.dmp.dao.entity.ReportMatchFinishEntity;
import com.efida.sports.dmp.dao.mapper.ReportMatchFinishEntityMapper;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportMatchFinishServiceImpl.java, v 0.1 2018年8月30日 上午10:05:01 zengbo Exp $
 */
@Service("reportMatchFinishService")
public class ReportMatchFinishServiceImpl implements ReportMatchFinishService {

    @Autowired
    private ReportMatchFinishEntityMapper reportMatchFinishEntityMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return reportMatchFinishEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReportMatchFinishEntity record) {
        return reportMatchFinishEntityMapper.insert(record);
    }

    @Override
    public int insertSelective(ReportMatchFinishEntity record) {
        return reportMatchFinishEntityMapper.insertSelective(record);
    }

    @Override
    public ReportMatchFinishEntity selectByPrimaryKey(Integer id) {
        return reportMatchFinishEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportMatchFinishEntity record) {
        return reportMatchFinishEntityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ReportMatchFinishEntity record) {
        return reportMatchFinishEntityMapper.updateByPrimaryKey(record);
    }

    @Override
    public void saveReportMatchFinishList(List<ReportMatchFinishEntity> records) {
        if (records != null && records.size() > 0) {
            for (ReportMatchFinishEntity entity : records) {
                entity.setGmtModified(new Date());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("matchCode", entity.getMatchCode());
                map.put("partTimeDay", entity.getPartTimeDay());
                List<ReportMatchFinishEntity> result = reportMatchFinishEntityMapper
                    .queryMatchFinishByCode(map);
                if (result != null && result.size() > 0) {
                    ReportMatchFinishEntity oldEntity = result.get(0);
                    if (entity.getOrganizerCount() == oldEntity.getOrganizerCount()
                        && entity.getFinishCount() == oldEntity.getFinishCount()) {
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
    public int officialMatchFinishCount(String matchCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("matchCode", matchCode);
        Map<String, Object> resultMap = reportMatchFinishEntityMapper.officialMatchFinishCount(map);
        if (resultMap != null) {
            BigDecimal count = (BigDecimal) resultMap.get("counts");
            return count.intValue();
        }
        return 0;
    }

    @Override
    public List<ReportMatchFinishEntity> queryAllData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("matchCode", null);
        map.put("partTimeDay", null);
        return reportMatchFinishEntityMapper.queryMatchFinishByCode(map);
    }

    @Override
    public List<Map<String, Object>> queryALLofficialMatchByCode(String matchCode) {
        return reportMatchFinishEntityMapper.queryALLofficialMatch(matchCode);
    }

}
