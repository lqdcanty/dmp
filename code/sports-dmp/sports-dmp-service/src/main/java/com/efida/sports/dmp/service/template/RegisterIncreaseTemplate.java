/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.template;

import java.util.ArrayList;
import java.util.List;

import com.efida.sport.facade.model.RegisterTrendAnalysisModel;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * @author zoutao
 * @version $Id: ChannelRegisterTemplate.java, v 0.1 2018年8月31日 下午6:45:41 zoutao Exp $
 */
public class RegisterIncreaseTemplate {

    @ExcelAttribute(column = "A", name = "日期")
    private String date;

    @ExcelAttribute(column = "B", name = "新增用户")
    private Long   newRegister;

    @ExcelAttribute(column = "C", name = "累计用户")
    private Long   totalRegister;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getNewRegister() {
        return newRegister;
    }

    public void setNewRegister(Long newRegister) {
        this.newRegister = newRegister;
    }

    public Long getTotalRegister() {
        return totalRegister;
    }

    public void setTotalRegister(Long totalRegister) {
        this.totalRegister = totalRegister;
    }

    public static List<RegisterIncreaseTemplate> increaseRegisters(List<RegisterTrendAnalysisModel> models) {
        ArrayList<RegisterIncreaseTemplate> list = new ArrayList<RegisterIncreaseTemplate>();
        if (models == null || models.size() < 1) {
            return list;
        }
        for (RegisterTrendAnalysisModel registerTrendAnalysisModel : models) {
            RegisterIncreaseTemplate temp = new RegisterIncreaseTemplate();
            temp.setDate(registerTrendAnalysisModel.getDateStr());
            temp.setNewRegister(registerTrendAnalysisModel.getNewRegister());
            temp.setTotalRegister(registerTrendAnalysisModel.getTotalRregister());
            list.add(temp);
        }
        return list;
    }

}
