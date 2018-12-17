/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open;

import java.text.ParseException;
import java.util.List;

import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenProjectModel;

/**
 * 项目服务接口
 * @author zhiyang
 * @version $Id: ProjectService.java, v 0.1 2018年5月28日 下午4:58:06 zhiyang Exp $
 * @param <OpenProjectModel>
 */
public interface ProjectService {

    /**
     * 查询项目信息接口
     * 
     * @param unitCode
     * @param timestamp
     * @param sign
     * @return
     * @throws OpenException 
     * @throws ParseException 
     */
    List<OpenProjectModel> queryProjectList(String unitCode, String timestamp,
                                        String sign) throws OpenException, ParseException;

    /**
     * 查询项目信息-内部使用
     *
     * @throws OpenException
     * @throws ParseException
     */
    List<OpenProjectModel> selectProjectList();

}
