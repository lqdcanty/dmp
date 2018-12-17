/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;

import com.efida.sport.dmp.facade.model.EnrollxInfoDto;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenEnrollxResultModel;
import com.efida.sports.dmp.biz.open.request.EnrollxQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenPlayerEntity;

/**
 * 
 * @author zhiyang
 * @version $Id: OpenEnrollxService.java, v 0.1 2018年7月26日 下午11:12:28 zhiyang Exp $
 */
public interface OpenEnrollxService {

    /**
     * 提交报名信息
     * 
     * @param unitCode
     * @param count
     * @param data
     * @param timestamp
     * @param sign
     * @param ipAddress
     * @param channelCode
     * @return
     * @throws OpenException
     * @throws ParseException 
     */
    List<OpenEnrollxResultModel> submitEnrollInfo(String unitCode, int count, String data,
                                                  String timestamp, String sign, String ipAddress,
                                                  String channelCode) throws OpenException,
                                                                      ParseException;

    /**
     * 新增一条报名信息到数据库，并返回执行结果
     * 
     * @param enrollInfo
     * @param unitCode
     * @param source 1.來自接口  0.來自组委会平台
     * @return
     * @throws ParseException
     * @throws OpenException
     */
    public OpenEnrollxResultModel addOpenEnrollxInfo(OpenEnrollxModel enrollInfo, String unitCode,
                                                     String channelCode,
                                                     int source) throws ParseException,
                                                                 OpenException;

    /**
     * 
     * 
     * @param enrollInfo
     * @return
     * @throws OpenException 
     */
    public OpenEnrollxResultModel addOpenEnrollInfo(EnrollxInfoDto enrollInfo) throws OpenException;

    /**
     * 查询报名信息
     * 
     * @param qr
     * @return
     * @throws OpenException 
     * @throws ParseException 
     */
    List<OpenEnrollxModel> queryEnrollInfo(EnrollxQueryRequest queryInfo) throws OpenException,
                                                                          ParseException;

    /**
     * 获取运动员信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param playerCode
     * @return
     */
    OpenPlayerEntity getPlayer(String playerCode);
}
