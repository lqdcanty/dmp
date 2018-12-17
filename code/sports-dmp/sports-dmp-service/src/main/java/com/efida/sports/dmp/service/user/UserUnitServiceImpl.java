/**
 * 
 */
package com.efida.sports.dmp.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.efida.sports.dmp.dao.entity.UserUnit;
import com.efida.sports.dmp.dao.mapper.UserUnitMapper;
import com.efida.sports.dmp.exception.DmpBusException;

import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.service.user.UserService;

/**
 * 用户关联承办方
 * 
 * @author wang yi
 */
@Service
public class UserUnitServiceImpl implements UserUnitService {

    @Autowired
    private UserUnitMapper userUnitMapper;

    @Autowired
    private UserService    userService;

    @Override
    public List<UserUnit> getUserUnit(String uid) {
        EntityWrapper<UserUnit> entityWrapper = new EntityWrapper<UserUnit>();
        entityWrapper.eq("uid", uid);
        List<UserUnit> list = userUnitMapper.selectList(entityWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<UserUnit>();
        }
        return list;
    }

    @Override
    @Transactional
    public List<UserUnit> upAndInsertUserUnit(String uid, List<UserUnit> unitCodes) {
        List<UserUnit> userUnits = new ArrayList<UserUnit>();
        SysUser user = userService.getUserByUuid(uid);
        if (user == null) {
            throw new DmpBusException("uid:" + uid + "账户不存在");
        }
        // 删除所有联系
        EntityWrapper<UserUnit> entityWrapper = new EntityWrapper<UserUnit>();
        entityWrapper.eq("uid", uid);
        userUnitMapper.delete(entityWrapper);
        for (UserUnit unit : unitCodes) {
            UserUnit userUnit = new UserUnit();
            userUnit.setUid(uid);
            userUnit.setUserName(user.getUserName());
            userUnit.setUserRealName(user.getRealName());
            userUnit.setUnitCode(unit.getUnitCode());
            userUnit.setUnitName(unit.getUnitName());
            userUnit.setGmtModified(new Date());
            userUnitMapper.insert(userUnit);
            userUnits.add(userUnit);
        }
        return userUnits;
    }

    @Override
    public List<UserUnit> getUserUnitByUserName(String userName) {
        EntityWrapper<UserUnit> entityWrapper = new EntityWrapper<UserUnit>();
        entityWrapper.eq("user_name", userName);
        List<UserUnit> list = userUnitMapper.selectList(entityWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<UserUnit>();
        }
        return list;
    }

}
