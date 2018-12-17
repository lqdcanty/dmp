/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.efida.sport.dmp.facade.model.UnitAccountDto;
import com.efida.sport.dmp.facade.model.UserUnitModel;
import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.dao.entity.UserUnit;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: CommonCover.java, v 0.1 2018年7月13日 下午4:05:20 wang yi Exp $
 */
public class CommonCover {

    public static List<UnitAccountDto> coverToUnitAccountDtos(List<OpenUnitEntity> list) {
        List<UnitAccountDto> units = new ArrayList<UnitAccountDto>();
        if (CollectionUtils.isEmpty(list)) {
            return units;
        }
        for (OpenUnitEntity openUnitEntity : list) {
            units.add(coverToUnitAccountDto(openUnitEntity));
        }
        return units;
    }

    private static UnitAccountDto coverToUnitAccountDto(OpenUnitEntity openUnitEntity) {
        if (openUnitEntity == null) {
            return null;
        }
        UnitAccountDto unitAccountDto = new UnitAccountDto();
        unitAccountDto.setUnitCode(openUnitEntity.getUnitCode());
        unitAccountDto.setUnitName(openUnitEntity.getUnitName());
        return unitAccountDto;
    }

    /**
     * 用戶关联承办方信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userUnits
     * @return
     */
    public static List<UserUnitModel> coverToUserUnitModes(List<UserUnit> userUnits) {
        List<UserUnitModel> userUnitModels = new ArrayList<UserUnitModel>();
        if (CollectionUtils.isEmpty(userUnits)) {
            return userUnitModels;
        }
        for (UserUnit userUnit : userUnits) {
            userUnitModels.add(coverToUserUnitMode(userUnit));
        }
        return userUnitModels;
    }

    /**
     * 用戶关联承办方信息转换
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userUnit
     * @return
     */
    private static UserUnitModel coverToUserUnitMode(UserUnit userUnit) {
        //用戶关联承办方信息
        if (userUnit != null) {
            UserUnitModel userUnitModel = new UserUnitModel();
            userUnitModel.setId(userUnitModel.getId());
            userUnitModel.setUid(userUnit.getUid());
            userUnitModel.setUnitCode(userUnit.getUnitCode());
            userUnitModel.setUnitName(userUnit.getUnitName());
            userUnitModel.setUserName(userUnit.getUserName());
            userUnitModel.setUserRealName(userUnit.getUserRealName());
            return userUnitModel;
        }
        return null;
    }

}
