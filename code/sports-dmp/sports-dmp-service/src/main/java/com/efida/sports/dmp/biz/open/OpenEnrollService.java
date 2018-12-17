/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.efida.sport.dmp.facade.model.EnrollInfoDto;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenEnrollModel;
import com.efida.sport.open.model.OpenEnrollResultModel;
import com.efida.sports.dmp.biz.open.request.EnrollQueryRequest;
import com.efida.sports.dmp.dao.entity.OpenEnrollInfoEntity;

/**
 * 报名开放接口服务
 * @author zhiyang
 * @version $Id: OpenEnrollService.java, v 0.1 2018年5月29日 下午9:04:22 zhiyang Exp $
 */
public interface OpenEnrollService {

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
    List<OpenEnrollResultModel> submitEnrollInfo(String unitCode, int count, String data,
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
    public OpenEnrollResultModel addOpenEnrollInfo(OpenEnrollModel enrollInfo, String unitCode,
                                                   String channelCode,
                                                   int source) throws ParseException, OpenException;

    /**
     * 
     * 
     * @param enrollInfo
     * @return
     * @throws OpenException 
     */
    public OpenEnrollResultModel addOpenEnrollInfo(EnrollInfoDto enrollInfo) throws OpenException;

    /**
     * 查询报名信息
     * 
     * @param qr
     * @return
     * @throws OpenException 
     * @throws ParseException 
     */
    List<OpenEnrollModel> queryEnrollInfo(EnrollQueryRequest queryInfo) throws OpenException,
                                                                        ParseException;

    /**
     * 获取用户某个比赛项标识
     * 
     * @param enrollInfo
     * @return
     */
    public String getUserId(OpenEnrollInfoEntity enrollInfo);

    /**
     * 加载官方报名所有信息。 
     * 返回官方：手机号+姓名|(openType+openId)
     * @param eventCode
     * @param maxNumber
     * @return
     */
    Set<String> loadOurEnrollInfoByEvent(String eventCode, int maxNumber);

}
