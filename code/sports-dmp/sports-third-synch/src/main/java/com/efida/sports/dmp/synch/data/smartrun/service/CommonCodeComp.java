/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.synch.data.smartrun.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.efida.sport.open.model.OpenEnrollxModel;
import com.efida.sport.open.model.OpenPlayerModel;
import com.efida.sport.open.model.OpenScoreModel;
import com.efida.sport.open.util.Md5Util;

/**
 * 
 * @author wang yi
 * @desc 
 * @version $Id: SmartCodeComp.java, v 0.1 2018年9月13日 上午11:01:46 wang yi Exp $
 */
@Component
public class CommonCodeComp {

    /**
     * 生成运动员编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param mobile
     * @param name
     * @return
     */
    public String generatePlayerCode(String unitCode, String mobile, String name) {
        String signature = ("py"
                            + Md5Util.signature(String.format("%s_%s_%s", unitCode, mobile, name)))
                                .substring(0, 20);
        return signature;
    }

    /**
     * 生成报名唯一标识
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param item
     * @return
     */
    public String genEnroIdempotentId(OpenEnrollxModel item) {
        String IdempotentId = "";
        if (item == null) {
            return IdempotentId;
        }
        if (item.getEventType().equals("personal")) {
            List<OpenPlayerModel> playerData = item.getPlayerData();
            if (CollectionUtils.isEmpty(playerData)) {
                return IdempotentId;
            }
            //个人报名
            OpenPlayerModel openPlayerModel = playerData.get(0);
            IdempotentId = Md5Util.signature(
                (item.getMatchCode() + "" + item.getMatchCode() + "" + item.getEventCode() + ""
                 + openPlayerModel.getPlayerNo() + "" + openPlayerModel.getPlayerName() + ""));
        }
        return IdempotentId;
    }

    /**
     * 生成成绩编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param item 成绩对象
     * @return
     */
    public String genScoreIdempotentId(OpenScoreModel item) {
        String IdempotentId = "sc" + (item.getPlayerCode() + "" + item.getPlayerPhone() + ""
                                      + item.getPlayerName() + "");
        return IdempotentId;
    }

}
