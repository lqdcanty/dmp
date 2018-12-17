/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.PlayerModel;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: PlayerModelMapper.java, v 0.1 2018年6月21日 下午8:41:45 wang yi Exp $
 */
public interface PlayerModelMapper {

    /**
     * 查询运动员信息
     * @title
     * uniCode
     * match
     * source
     * player
     * playerPhone
     * gmtOrderField
     * applyOrderField
     * modifyOrderField
     * @methodName
     * @author wangyi
     * @description
     * @param queryParams
     * @return
     */
    List<PlayerModel> selectPlayerByLikeParams(Map<String, Object> queryParams);

}
