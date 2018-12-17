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
public class ChannelRegisterTemplate {

    @ExcelAttribute(column = "A", name = "渠道名称")
    private String channelName;

    @ExcelAttribute(column = "B", name = "新增用户")
    private Long   newRegister;

    @ExcelAttribute(column = "C", name = "用户占比")
    private String Proportion;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getNewRegister() {
        return newRegister;
    }

    public void setNewRegister(Long newRegister) {
        this.newRegister = newRegister;
    }

    public String getProportion() {
        return Proportion;
    }

    public void setProportion(String proportion) {
        Proportion = proportion;
    }

    public static List<ChannelRegisterTemplate> channelRegisters(List<RegisterTrendAnalysisModel> models) {
        ArrayList<ChannelRegisterTemplate> list = new ArrayList<ChannelRegisterTemplate>();
        if (models == null || models.size() < 1) {
            return list;
        }
        for (RegisterTrendAnalysisModel registerTrendAnalysisModel : models) {
            ChannelRegisterTemplate temp = new ChannelRegisterTemplate();
            temp.setChannelName(registerTrendAnalysisModel.getChannelName());
            temp.setNewRegister(registerTrendAnalysisModel.getNewRegister());
            temp.setProportion(registerTrendAnalysisModel.getProportion());
            list.add(temp);
        }
        return list;
    }
}
