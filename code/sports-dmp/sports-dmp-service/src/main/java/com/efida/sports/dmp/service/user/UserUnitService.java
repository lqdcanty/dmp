/**
 * 
 */
package com.efida.sports.dmp.service.user;

import java.util.List;

import com.efida.sports.dmp.dao.entity.UserUnit;

/**
 * 用户关联承办方信息
 * 
 * @author wang yi
 *
 */
public interface UserUnitService {

    /**
     * 获取用户关联承办方数据
     * @param uid
     * @return
     */
    public List<UserUnit> getUserUnit(String uid);

    /**
     * 添加/更新承办方数据
     * @param uid
     * @param unitCodes
     * @return
     */
    public List<UserUnit> upAndInsertUserUnit(String uid, List<UserUnit> unitCodes);

    /**
     * 获取承办方关联数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userName
     * @return
     */
    public List<UserUnit> getUserUnitByUserName(String userName);

}
