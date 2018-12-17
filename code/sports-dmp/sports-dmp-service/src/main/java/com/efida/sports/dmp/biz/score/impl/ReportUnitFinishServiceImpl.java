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

import com.efida.sports.dmp.biz.score.ReportUnitFinishService;
import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;
import com.efida.sports.dmp.dao.mapper.ReportMatchFinishEntityMapper;
import com.efida.sports.dmp.dao.mapper.ReportUnitFinishEntityMapper;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: ReportUnitFinishServiceImpl.java, v 0.1 2018年8月30日 上午9:50:57 zengbo Exp $
 */
@Service("reportUnitFinishService")
public class ReportUnitFinishServiceImpl implements ReportUnitFinishService {

    @Autowired
    private ReportUnitFinishEntityMapper  reportUnitFinishEntityMapper;

    @Autowired
    private ReportMatchFinishEntityMapper reportMatchFinishEntityMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return reportUnitFinishEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReportUnitFinishEntity record) {
        return reportUnitFinishEntityMapper.insert(record);
    }

    @Override
    public int insertSelective(ReportUnitFinishEntity record) {
        return reportUnitFinishEntityMapper.insertSelective(record);
    }

    @Override
    public ReportUnitFinishEntity selectByPrimaryKey(Long id) {
        return reportUnitFinishEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ReportUnitFinishEntity record) {
        return reportUnitFinishEntityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ReportUnitFinishEntity record) {
        return reportUnitFinishEntityMapper.updateByPrimaryKey(record);
    }

    @Override
    public void saveReportUnitFinishList(List<ReportUnitFinishEntity> records) {
        if (records != null && records.size() > 0) {
            for (ReportUnitFinishEntity entity : records) {
                entity.setGmtModified(new Date());
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("unitCode", entity.getUnitCode());
                map.put("partTimeDay", entity.getPartTimeDay());
                List<ReportUnitFinishEntity> result = reportUnitFinishEntityMapper
                    .queryUnitFinishByCode(map);
                if (result != null && result.size() > 0) {
                    ReportUnitFinishEntity oldEntity = result.get(0);
                    if (entity.getFinishCont() == oldEntity.getFinishCont()) {
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
    public int countTotalUnitFinish(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(startTime)) {
            map.put("startTime", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            map.put("endTime", endTime);
        }
        Map<String, Object> result = reportMatchFinishEntityMapper.countFinishMatchByParams(map);
        if (result != null) {
            BigDecimal count = (BigDecimal) result.get("finishCont");
            return count.intValue();
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> countUnitFinishByParams(String startTime, String endTime,
                                                             String likeParams, String inParams) {

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
        return reportUnitFinishEntityMapper.countUnitFinishByParams(map);
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
    public int officialMatchFinishCount(String unitCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("unitCode", unitCode);
        Map<String, Object> resultMap = reportUnitFinishEntityMapper.officialUnitFinishCount(map);
        if (resultMap != null) {
            BigDecimal count = (BigDecimal) resultMap.get("counts");
            return count.intValue();
        }
        return 0;
    }

}
