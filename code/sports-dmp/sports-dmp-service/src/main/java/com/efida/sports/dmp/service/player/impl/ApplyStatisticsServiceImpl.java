package com.efida.sports.dmp.service.player.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.efida.sports.dmp.dao.entity.ApplyStatistics;
import com.efida.sports.dmp.dao.mapper.ApplyStatisticsMapper;
import com.efida.sports.dmp.service.player.ApplyStatisticsService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
@Service
public class ApplyStatisticsServiceImpl extends ServiceImpl<ApplyStatisticsMapper, ApplyStatistics>
                                        implements ApplyStatisticsService {

    @Override
    public ApplyStatistics getApplyStatisticsByType(String type) {
        Wrapper<ApplyStatistics> wrapper = new EntityWrapper<ApplyStatistics>();
        wrapper.eq("type", type);
        return selectOne(wrapper);
    }

    @Override
    public List<ApplyStatistics> getApplyStatistics() {
        Wrapper<ApplyStatistics> wrapper = new EntityWrapper<ApplyStatistics>();
        return selectList(wrapper);
    }

}
