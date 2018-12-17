/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenMatchModel;

/**
 * 赛事开放接口服务
 * @author zhiyang
 * @version $Id: MatchService.java, v 0.1 2018年5月29日 下午3:38:44 zhiyang Exp $
 */
public interface OpenMatchService {

    /**
     * 
     * 
     * @param unitCode
     * @param matchCode
     * @param matchStatus
     * @param beginTimeMin
     * @param beginTimeMax
     * @param endTimeMin
     * @param endTimeMax
     * @param pageNumber
     * @param pageSize
     * @param timestamp
     * @param sign
     * @return
     * @throws OpenException
     * @throws ParseException
     */
    public List<OpenMatchModel> queryMatchList(String unitCode, String matchCode,
                                               String matchStatus, String beginTimeMin,
                                               String beginTimeMax, String endTimeMin,
                                               String endTimeMax, int pageNumber, int pageSize,
                                               String timestamp,
                                               String sign) throws OpenException, ParseException;
}
