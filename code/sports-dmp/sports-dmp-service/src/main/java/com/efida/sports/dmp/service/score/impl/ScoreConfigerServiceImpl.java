/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.score.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.efida.sports.dmp.dao.entity.ScoreConfigerEntity;
import com.efida.sports.dmp.dao.mapper.ScoreConfigerMapper;
import com.efida.sports.dmp.service.score.ScoreConfigerService;

/**
 * 
 * @author zoutao
 * @version $Id: ScoreConfigerServiceImpl.java, v 0.1 2018年7月28日 下午2:35:00 zoutao Exp $
 */
@Service
public class ScoreConfigerServiceImpl extends ServiceImpl<ScoreConfigerMapper, ScoreConfigerEntity>
                                      implements ScoreConfigerService {

    @Override
    public ScoreConfigerEntity getConfigByCompetitionCode(String competitionCode) {
        Wrapper<ScoreConfigerEntity> wrapper = new EntityWrapper<ScoreConfigerEntity>();
        wrapper.eq("competition_code", competitionCode);
        return selectOne(wrapper);
    }

}
