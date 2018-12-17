/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade;

import java.util.List;

import com.efida.sport.dmp.facade.model.UnitAccountDto;
import com.efida.sport.dmp.facade.model.UserUnitModel;
import com.efida.sport.dmp.facade.result.DefaultResult;
import com.efida.sport.dmp.facade.result.DmpPageResult;

/**
 * 承办方对外接口
 * @author wang yi
 * @desc 
 * @version $Id: OpenUnitFacade.java, v 0.1 2018年7月12日 下午3:37:46 wang yi Exp $
 */
public interface OpenUnitFacade {

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
     * @param keyword 关键字 null查询所有
     * @param page 页数
     * @param size  数量
     * @return
     */
    DefaultResult<DmpPageResult<UnitAccountDto>> getUnitAccounts(String keyword, int page,
                                                                 int size);

    /**
     * 获取用户关联到的承办方数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userName用户名
     * @return
     */
    DefaultResult<List<UserUnitModel>> getUserUnit(String userName);

}
