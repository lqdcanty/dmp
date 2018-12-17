package com.efida.sports.dmp.service.player.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.efida.sport.admin.facade.model.MatchAreasModel;
import com.efida.sports.dmp.dao.entity.ApplyAreaStatistics;
import com.efida.sports.dmp.dao.mapper.ApplyAreaStatisticsMapper;
import com.efida.sports.dmp.dao.mapper.OpenApplyInfoEntityMapper;
import com.efida.sports.dmp.service.dubbo.intergration.SpMatchFacadeClient;
import com.efida.sports.dmp.service.player.ApplyAreaStatisticsService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
@Service
public class ApplyAreaStatisticsServiceImpl extends
                                            ServiceImpl<ApplyAreaStatisticsMapper, ApplyAreaStatistics>
                                            implements ApplyAreaStatisticsService {

    @Autowired
    private OpenApplyInfoEntityMapper openApplyInfoEntityMapper;
    @Autowired
    private SpMatchFacadeClient       spMatchFacadeClient;

    @Override
    public void createAreaStatistics() {
        List<MatchAreasModel> matchAreas = spMatchFacadeClient.getMatchAreas();
        if (matchAreas == null || matchAreas.size() < 1) {
            return;
        }
        for (MatchAreasModel model : matchAreas) {
            String fieldCode = model.getFieldCode();
            long applyCount = openApplyInfoEntityMapper.getCountByFieldCode(fieldCode);
            ApplyAreaStatistics entity = getApplyAreaStatisticsByFieldCode(fieldCode);
            if (entity == null) {
                entity = new ApplyAreaStatistics();
                entity.setGmtCreate(new Date());
            }
            entity.setArea(model.getArea());
            entity.setCity(model.getCity());
            //            entity.setFieldAddress(model.get);
            entity.setFieldCode(fieldCode);
            entity.setFieldName(model.getFieldName());
            entity.setGmtModified(new Date());
            entity.setLatitude(model.getLatitude());
            entity.setLongitude(model.getLongitude());
            entity.setProvince(model.getProvince());
            entity.setStatus(model.getStatus());
            entity.setApplyCount(applyCount);
            insertOrUpdate(entity);
        }
    }

    @Override
    public List<ApplyAreaStatistics> selectApplyAreaStatistics() {
        Wrapper<ApplyAreaStatistics> wrapper = new EntityWrapper<ApplyAreaStatistics>();
        return selectList(wrapper);
    }

    private ApplyAreaStatistics getApplyAreaStatisticsByFieldCode(String fieldCode) {
        Wrapper<ApplyAreaStatistics> wrapper = new EntityWrapper<ApplyAreaStatistics>();
        wrapper.eq("field_code", fieldCode);
        return selectOne(wrapper);
    }

}
