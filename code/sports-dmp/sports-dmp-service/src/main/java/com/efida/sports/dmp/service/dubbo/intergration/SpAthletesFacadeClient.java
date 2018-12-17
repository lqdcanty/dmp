/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.intergration;

import com.efida.sport.admin.facade.model.SpAthletesEnrollModel;

/**
 * 
 * @author zoutao
 * @version $Id: SpAthletesFacadeClient.java, v 0.1 2018年5月28日 下午3:24:31 zoutao Exp $
 */
public interface SpAthletesFacadeClient {
    /**
     * 通过赛事编号获取指定赛事运动员报名模板信息
     * 
     * @param matchCode   赛事编号
     * @return
     */

    SpAthletesEnrollModel getAthietes(String matchCode);

}
