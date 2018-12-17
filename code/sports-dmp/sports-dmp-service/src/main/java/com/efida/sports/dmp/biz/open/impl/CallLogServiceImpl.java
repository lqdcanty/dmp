/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.evake.auth.usermodel.PagingResult;

import com.efida.sports.dmp.biz.open.CallLogService;
import com.efida.sports.dmp.dao.entity.OpenCallLogEntity;
import com.efida.sports.dmp.dao.entity.OpenLogContentEntity;
import com.efida.sports.dmp.dao.mapper.OpenCallLogEntityMapper;
import com.efida.sports.dmp.dao.mapper.OpenLogContentEntityMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author zhiyang
 * @version $Id: CallLogServiceImpl.java, v 0.1 2018年5月29日 下午2:46:12 zhiyang Exp $
 */
@Service("callLogService")
public class CallLogServiceImpl implements CallLogService {

    @Autowired
    private OpenCallLogEntityMapper    openCallLogEntityMapper;

    @Autowired
    private OpenLogContentEntityMapper openLogContentEntityMapper;

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.CallLogService#saveLog(com.efida.sports.entity.OpenCallLogEntity)
     */
    @Override
    public void saveLog(OpenCallLogEntity callLog) {

        openCallLogEntityMapper.insert(callLog);
    }

    /**
     * 
     * @see com.efida.sports.dmp.biz.open.CallLogService#saveLogContent(java.lang.String, java.lang.String)
     */
    @Override
    public void saveLogContent(String seqNo, String content) {
        OpenLogContentEntity record = new OpenLogContentEntity();
        record.setContent(content);
        record.setSeqNo(seqNo);
        openLogContentEntityMapper.insert(record);
    }

    @Override
    public PagingResult<OpenCallLogEntity> selectPage(String unitCode, Integer success,
                                                      String startTime, String endTime, int page,
                                                      int limit) {
        PageHelper.startPage(page, limit);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("unitCode", unitCode);
        params.put("success", success);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        List<OpenCallLogEntity> callLogs = openCallLogEntityMapper.selectByUnitCode(params);
        PageInfo<OpenCallLogEntity> pageInfo = new PageInfo<OpenCallLogEntity>(callLogs);
        return new PagingResult<OpenCallLogEntity>(pageInfo.getList(), pageInfo.getTotal(), page,
            limit);
    }

    @Override
    public OpenCallLogEntity selectByIdAndUnitCode(Long callLogId, String unitCode) {
        return openCallLogEntityMapper.selectByIdAndUnitCode(callLogId, unitCode);
    }
}
