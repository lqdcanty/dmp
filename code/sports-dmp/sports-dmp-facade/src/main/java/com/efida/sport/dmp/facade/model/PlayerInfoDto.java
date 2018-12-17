/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhiyang
 * @version $Id: PlayerInfoDto.java, v 0.1 2018年7月27日 下午12:57:59 zhiyang Exp $
 */
public class PlayerInfoDto implements Serializable{

    //无需填充，dmp系统中对应报名编号
    private String            applyCode;
    //无需填充，dmp系统中对运动员编号 
    private String            playerCode;

    //手机号(必填）
    private String            playerPhone;
    //姓名
    private String            playerName;
    // M:男  F：女  
    private String            sex;

    //头像地址
    private String            imgUrl;

    //邮箱
    private String            email;

    //身高（cm）
    private Integer           playerHeight;

    //体重（kg）
    private Double            playerWeight;

    //生日
    private String            playerBirth;
    //国籍
    private String            playerNationality;

    //工作单位
    private String            playerWorkUnit;

    //联系地址
    private String            playerAddress;

    //证件类型
    private Integer           playerCerType;

    //证件号码
    private String            playerCerNo;

    //血型
    private String            playerBloodType;
    //民族
    private String            playerNation;
    //衣服尺码
    private String            playerClothSize;
    //紧急联系人
    private String            playerEmergencyName;
    //紧急联系电话
    private String            playerEmergencyPhone;
    /**
     * 运动员分类标签。 common:普通用户 , student:学生， square:广场用户。
     */
    private String            playerCategory;
    /**:
     * 上方平台类型
     */
    private String            openPlatType;

    /**
     * 上方账号Id
     */
    private String            openId;

    /**
     * 其他属性
     */
    private JSONObject        extProp;

    /**
     * 
     */
    private Integer playerNo;

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getPlayerPhone() {
        return playerPhone;
    }

    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(Integer playerHeight) {
        this.playerHeight = playerHeight;
    }

    public Double getPlayerWeight() {
        return playerWeight;
    }

    public void setPlayerWeight(Double playerWeight) {
        this.playerWeight = playerWeight;
    }

    public String getPlayerBirth() {
        return playerBirth;
    }

    public void setPlayerBirth(String playerBirth) {
        this.playerBirth = playerBirth;
    }

    public String getPlayerNationality() {
        return playerNationality;
    }

    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality;
    }

    public String getPlayerWorkUnit() {
        return playerWorkUnit;
    }

    public void setPlayerWorkUnit(String playerWorkUnit) {
        this.playerWorkUnit = playerWorkUnit;
    }

    public String getPlayerAddress() {
        return playerAddress;
    }

    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress;
    }

    public Integer getPlayerCerType() {
        return playerCerType;
    }

    public void setPlayerCerType(Integer playerCerType) {
        this.playerCerType = playerCerType;
    }

    public String getPlayerCerNo() {
        return playerCerNo;
    }

    public void setPlayerCerNo(String playerCerNo) {
        this.playerCerNo = playerCerNo;
    }

    public String getPlayerBloodType() {
        return playerBloodType;
    }

    public void setPlayerBloodType(String playerBloodType) {
        this.playerBloodType = playerBloodType;
    }

    public String getPlayerNation() {
        return playerNation;
    }

    public void setPlayerNation(String playerNation) {
        this.playerNation = playerNation;
    }

    public String getPlayerClothSize() {
        return playerClothSize;
    }

    public void setPlayerClothSize(String playerClothSize) {
        this.playerClothSize = playerClothSize;
    }

    public String getPlayerEmergencyName() {
        return playerEmergencyName;
    }

    public void setPlayerEmergencyName(String playerEmergencyName) {
        this.playerEmergencyName = playerEmergencyName;
    }

    public String getPlayerEmergencyPhone() {
        return playerEmergencyPhone;
    }

    public void setPlayerEmergencyPhone(String playerEmergencyPhone) {
        this.playerEmergencyPhone = playerEmergencyPhone;
    }

    public String getPlayerCategory() {
        return playerCategory;
    }

    public void setPlayerCategory(String playerCategory) {
        this.playerCategory = playerCategory;
    }

    public String getOpenPlatType() {
        return openPlatType;
    }

    public void setOpenPlatType(String openPlatType) {
        this.openPlatType = openPlatType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public JSONObject getExtProp() {
        return extProp;
    }

    public void setExtProp(JSONObject extProp) {
        this.extProp = extProp;
    }

    public Integer getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(Integer playerNo) {
        this.playerNo = playerNo;
    }

    @Override
    public String toString() {
        return "PlayerInfoDto [applyCode=" + applyCode + ", playerCode=" + playerCode
               + ", playerPhone=" + playerPhone + ", playerName=" + playerName + ", sex=" + sex
               + ", imgUrl=" + imgUrl + ", email=" + email + ", playerHeight=" + playerHeight
               + ", playerWeight=" + playerWeight + ", playerBirth=" + playerBirth
               + ", playerNationality=" + playerNationality + ", playerWorkUnit=" + playerWorkUnit
               + ", playerAddress=" + playerAddress + ", playerCerType=" + playerCerType
               + ", playerCerNo=" + playerCerNo + ", playerBloodType=" + playerBloodType
               + ", playerNation=" + playerNation + ", playerClothSize=" + playerClothSize
               + ", playerEmergencyName=" + playerEmergencyName + ", playerEmergencyPhone="
               + playerEmergencyPhone + ", playerCategory=" + playerCategory + ", openPlatType="
               + openPlatType + ", openId=" + openId + ", extProp=" + extProp + ", playerNo="
               + playerNo + "]";
    }
    
     
}
