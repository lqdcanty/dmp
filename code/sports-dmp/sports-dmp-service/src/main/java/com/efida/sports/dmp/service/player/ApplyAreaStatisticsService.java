package com.efida.sports.dmp.service.player;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.efida.sports.dmp.dao.entity.ApplyAreaStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
public interface ApplyAreaStatisticsService extends IService<ApplyAreaStatistics> {

    void createAreaStatistics();

    List<ApplyAreaStatistics> selectApplyAreaStatistics();

}
