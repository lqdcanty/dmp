package com.efida.sports.dmp.service.player;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.efida.sports.dmp.dao.entity.ApplyStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
public interface ApplyStatisticsService extends IService<ApplyStatistics> {

    public ApplyStatistics getApplyStatisticsByType(String type);

    public List<ApplyStatistics> getApplyStatistics();

}
