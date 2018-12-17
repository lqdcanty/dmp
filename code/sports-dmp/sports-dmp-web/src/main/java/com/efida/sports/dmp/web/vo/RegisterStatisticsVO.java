/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.vo;

import com.efida.sport.facade.model.RegisterStatisticsModel;
import com.efida.sports.dmp.utils.NumberUtil;

/**
 * 
 * @author zoutao
 * @version $Id: RegisterStatisticsModelVO.java, v 0.1 2018年9月7日 下午4:59:59 zoutao Exp $
 */
public class RegisterStatisticsVO {

    /**
     * 总用户数
     */
    private String totalRegister;
    /**
     * 七日平均活跃用户数
     */
    private double sevenAvgActive;
    /**
     * 七日平均活跃用户数
     */
    private String sevenAvgActiveStr;

    /**
     * 三十日总活跃用户数
     */
    private String thirtyActiveRegitsers;
    /**
     * 七日平均新增用户数
     */
    private double sevenAvgNewRegister;
    /**
     * 七日留存率
     */
    private String sevenRetention;
    /**
    * 七日总活跃用户
    */
    private String sevenActiveRegitsers;

    public String getTotalRegister() {
        return totalRegister;
    }

    public void setTotalRegister(String totalRegister) {
        this.totalRegister = totalRegister;
    }

    public double getSevenAvgActive() {
        return sevenAvgActive;
    }

    public void setSevenAvgActive(double sevenAvgActive) {
        this.sevenAvgActive = sevenAvgActive;
    }

    public String getSevenAvgActiveStr() {
        return sevenAvgActiveStr;
    }

    public void setSevenAvgActiveStr(String sevenAvgActiveStr) {
        this.sevenAvgActiveStr = sevenAvgActiveStr;
    }

    public String getThirtyActiveRegitsers() {
        return thirtyActiveRegitsers;
    }

    public void setThirtyActiveRegitsers(String thirtyActiveRegitsers) {
        this.thirtyActiveRegitsers = thirtyActiveRegitsers;
    }

    public double getSevenAvgNewRegister() {
        return sevenAvgNewRegister;
    }

    public void setSevenAvgNewRegister(double sevenAvgNewRegister) {
        this.sevenAvgNewRegister = sevenAvgNewRegister;
    }

    public String getSevenRetention() {
        return sevenRetention;
    }

    public void setSevenRetention(String sevenRetention) {
        this.sevenRetention = sevenRetention;
    }

    public String getSevenActiveRegitsers() {
        return sevenActiveRegitsers;
    }

    public void setSevenActiveRegitsers(String sevenActiveRegitsers) {
        this.sevenActiveRegitsers = sevenActiveRegitsers;
    }

    public static RegisterStatisticsVO getVO(RegisterStatisticsModel model) {
        if (model == null) {
            return null;
        }
        RegisterStatisticsVO vo = new RegisterStatisticsVO();
        vo.setSevenActiveRegitsers(NumberUtil.longFormatStr(model.getSevenActiveRegitsers()));
        vo.setSevenAvgActive(model.getSevenAvgActive());
        vo.setSevenAvgActiveStr(model.getSevenAvgActiveStr());
        vo.setSevenAvgNewRegister(model.getSevenAvgNewRegister());
        vo.setSevenRetention(model.getSevenRetention());
        vo.setThirtyActiveRegitsers(NumberUtil.longFormatStr(model.getThirtyActiveRegitsers()));
        vo.setTotalRegister(NumberUtil.longFormatStr(model.getTotalRegister()));
        return vo;

    }

}
