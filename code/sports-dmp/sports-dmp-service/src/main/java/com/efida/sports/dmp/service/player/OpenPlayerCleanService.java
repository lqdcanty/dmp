package com.efida.sports.dmp.service.player;

import com.baomidou.mybatisplus.service.IService;
import com.efida.sports.dmp.dao.entity.OpenPlayerClean;
import com.efida.sports.dmp.dao.entity.PlayerStatisticalAnalysisModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zoutao
 * @since 2018-09-12
 */
public interface OpenPlayerCleanService extends IService<OpenPlayerClean> {
    /**
     * 清洗运动员数据
     */
    void cleanOpenPlayer();

    /**
     * 根据性别统计运动员数量
     * 
     * @return
     */
    PlayerStatisticalAnalysisModel getSexStatistics();

    /**
     * 根据是否成年进行统计
     * 
     * @return
     */
    PlayerStatisticalAnalysisModel getAdultStatistics();

    /**
     * 根据报名终端分析统计
     * 
     * @return
     */
    PlayerStatisticalAnalysisModel getTerminalStatistics();

    /**
     * 报名用户分析：
     */
    void createApplyStatistics();

    OpenPlayerClean getPlayerCleanByPlayerCode(String playerCode);

}
