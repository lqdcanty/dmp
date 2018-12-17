package com.efida.sports.dmp.service.score;

import com.baomidou.mybatisplus.service.IService;
import com.efida.sports.dmp.dao.entity.ScoreConfigerEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang yi
 * @since 2018-07-28
 */
public interface ScoreConfigerService extends IService<ScoreConfigerEntity> {

    ScoreConfigerEntity getConfigByCompetitionCode(String competitionCode);
}
