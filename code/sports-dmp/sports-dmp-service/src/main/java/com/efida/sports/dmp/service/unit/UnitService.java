/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.unit;

import com.efida.sports.dmp.dao.entity.OpenUnitEntity;

import cn.evake.auth.usermodel.PagingResult;

import java.util.List;

/**
 * 开放账户服务
 * @author wang yi
 * @desc 
 * @version $Id: UnitService.java, v 0.1 2018年7月4日 上午10:55:02 wang yi Exp $
 */
public interface UnitService {

    /**
     * 获取合作承办方
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param unitName
     * @param email
     * @param phone
     * @param lock
     * @param page
     * @param limit
     * @return
     */
    PagingResult<OpenUnitEntity> getPageUnitByKeywords(String unitCode, String unitName,
                                                       String email, String phone, String lock,
                                                       int page, int limit);

    /**
     * 锁定/解锁承办方
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param lock
     */
    void lockUnitByCode(String unitCode, boolean lock);

    /**
     * 获取一个承办方
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @return
     */
    OpenUnitEntity getUnitByCode(String unitCode);

    /**
     * 创建一个新承办方
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param OpenUnitEntity
     * @return
     */
    OpenUnitEntity addNewOpenUnitEntity(OpenUnitEntity openUnit);

    /**
     * 更新承办方数据
     * 根据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param openUnit
     * @return
     */
    OpenUnitEntity upOpenUnitEntity(String unitCode, OpenUnitEntity openUnit);

    /**
     * 获取所有承办方
     * 关键字匹配范围
     * 承办方账号
     * 承办方名称
     * 承办方手机号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    PagingResult<OpenUnitEntity> getUnitAccounts(String keyword, int page, int size);

    /**
     * 获取所有承办方-查询条件
     * @methodName
     * @author antony
     * @return
     */
    List<OpenUnitEntity> getUnitAccountList(String lock);

}
