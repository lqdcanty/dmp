/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import cn.evake.auth.usermodel.PagingResult;

import com.efida.sports.dmp.dao.entity.OpenCallLogEntity;

/**
 * 日志保存服务
 * @author zhiyang
 * @version $Id: CallLogService.java, v 0.1 2018年5月29日 下午2:45:42 zhiyang Exp $
 */
public interface CallLogService {

    /**
     * 保存日志信息
     * 
     * @param callLog
     */
    void saveLog(OpenCallLogEntity callLog);

    /**
     * 保存日志详细内容
     * 
     * @param seqNo
     * @param content
     */
    void saveLogContent(String seqNo, String content);

    /**
     * 查询日志
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param page
     * @param limit
     * @return
     */
    PagingResult<OpenCallLogEntity> selectPage(String unitCode, Integer success, String startTime,
                                               String endTime, int page, int limit);

    /**
     * 查询日志详情
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param callLogId
     * @param unitCode
     * @return
     */
    OpenCallLogEntity selectByIdAndUnitCode(Long callLogId, String unitCode);

}
