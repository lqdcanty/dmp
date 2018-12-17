/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.open.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.efida.sport.open.OpenException;

/**
 * 报名模型信息
 * @author zhiyang
 * @version $Id: OpenEnrollModel.java, v 0.1 2018年5月29日 下午9:19:36 zhiyang Exp $
 */
public class OpenEnrollModel implements ICommonModel, Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    //运动员编号 
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

    //项目编号（必选）
    private String            gameCode;
    //项目名称（ 必选）
    private String            gameName;
    //赛事编号  （必选）
    private String            matchCode;
    //赛事名称 （必选）
    private String            matchName;
    //分赛场编号 (可选)
    private String            fieldCode;
    //分赛场名称 （可选）
    private String            fieldName;

    //组别编号(可选)
    private String            matchGroupCode;
    //组别名称（可选）
    private String            matchGroupName;
    //项目细类编号（必选）
    private String            eventCode;
    //项目细类名称（必选）
    private String            eventName;
    //报名时间 （必填）
    private String            applyTime;

    //报名费（元）（ 必填）
    private Double            entryFee;

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

    //记录唯一性标识
    private String            idempotentId;

    // 1.已经存在则修改属性，但是只能在一段时间内可修改，具体时间同业务有关系； 0或其他取值：不支持修改，记录存在则返回已经存在记录。
    private String            modifyFlag       = "0";

    /**
     * Getter method for property <tt>playerCode</tt>.
     * 
     * @return property value of playerCode
     */
    public String getPlayerCode() {
        return playerCode;
    }

    /**
     * Setter method for property <tt>playerCode</tt>.
     * 
     * @param playerCode value to be assigned to property playerCode
     */
    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    /**
     * Getter method for property <tt>playerPhone</tt>.
     * 
     * @return property value of playerPhone
     */
    public String getPlayerPhone() {
        return playerPhone;
    }

    /**
     * Setter method for property <tt>playerPhone</tt>.
     * 
     * @param playerPhone value to be assigned to property playerPhone
     */
    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>imgUrl</tt>.
     * 
     * @return property value of imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Setter method for property <tt>imgUrl</tt>.
     * 
     * @param imgUrl value to be assigned to property imgUrl
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>playerHeight</tt>.
     * 
     * @return property value of playerHeight
     */
    public Integer getPlayerHeight() {
        return playerHeight;
    }

    /**
     * Setter method for property <tt>playerHeight</tt>.
     * 
     * @param playerHeight value to be assigned to property playerHeight
     */
    public void setPlayerHeight(Integer playerHeight) {
        this.playerHeight = playerHeight;
    }

    /**
     * Getter method for property <tt>playerWeight</tt>.
     * 
     * @return property value of playerWeight
     */
    public Double getPlayerWeight() {
        return playerWeight;
    }

    /**
     * Setter method for property <tt>playerWeight</tt>.
     * 
     * @param playerWeight value to be assigned to property playerWeight
     */
    public void setPlayerWeight(Double playerWeight) {
        this.playerWeight = playerWeight;
    }

    /**
     * Getter method for property <tt>playerNationality</tt>.
     * 
     * @return property value of playerNationality
     */
    public String getPlayerNationality() {
        return playerNationality;
    }

    /**
     * Setter method for property <tt>playerNationality</tt>.
     * 
     * @param playerNationality value to be assigned to property playerNationality
     */
    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality;
    }

    /**
     * Getter method for property <tt>playerAddress</tt>.
     * 
     * @return property value of playerAddress
     */
    public String getPlayerAddress() {
        return playerAddress;
    }

    /**
     * Setter method for property <tt>playerAddress</tt>.
     * 
     * @param playerAddress value to be assigned to property playerAddress
     */
    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress;
    }

    /**
     * Getter method for property <tt>playerCerType</tt>.
     * 
     * @return property value of playerCerType
     */
    public Integer getPlayerCerType() {
        return playerCerType;
    }

    /**
     * Setter method for property <tt>playerCerType</tt>.
     * 
     * @param playerCerType value to be assigned to property playerCerType
     */
    public void setPlayerCerType(Integer playerCerType) {
        this.playerCerType = playerCerType;
    }

    /**
     * Getter method for property <tt>playerCerNo</tt>.
     * 
     * @return property value of playerCerNo
     */
    public String getPlayerCerNo() {
        return playerCerNo;
    }

    /**
     * Setter method for property <tt>playerCerNo</tt>.
     * 
     * @param playerCerNo value to be assigned to property playerCerNo
     */
    public void setPlayerCerNo(String playerCerNo) {
        this.playerCerNo = playerCerNo;
    }

    /**
     * Getter method for property <tt>playerBloodType</tt>.
     * 
     * @return property value of playerBloodType
     */
    public String getPlayerBloodType() {
        return playerBloodType;
    }

    /**
     * Setter method for property <tt>playerBloodType</tt>.
     * 
     * @param playerBloodType value to be assigned to property playerBloodType
     */
    public void setPlayerBloodType(String playerBloodType) {
        this.playerBloodType = playerBloodType;
    }

    /**
     * Getter method for property <tt>playerNation</tt>.
     * 
     * @return property value of playerNation
     */
    public String getPlayerNation() {
        return playerNation;
    }

    /**
     * Setter method for property <tt>playerNation</tt>.
     * 
     * @param playerNation value to be assigned to property playerNation
     */
    public void setPlayerNation(String playerNation) {
        this.playerNation = playerNation;
    }

    /**
     * Getter method for property <tt>playerClothSize</tt>.
     * 
     * @return property value of playerClothSize
     */
    public String getPlayerClothSize() {
        return playerClothSize;
    }

    /**
     * Setter method for property <tt>playerClothSize</tt>.
     * 
     * @param playerClothSize value to be assigned to property playerClothSize
     */
    public void setPlayerClothSize(String playerClothSize) {
        this.playerClothSize = playerClothSize;
    }

    /**
     * Getter method for property <tt>playerEmergencyName</tt>.
     * 
     * @return property value of playerEmergencyName
     */
    public String getPlayerEmergencyName() {
        return playerEmergencyName;
    }

    /**
     * Setter method for property <tt>playerEmergencyName</tt>.
     * 
     * @param playerEmergencyName value to be assigned to property playerEmergencyName
     */
    public void setPlayerEmergencyName(String playerEmergencyName) {
        this.playerEmergencyName = playerEmergencyName;
    }

    /**
     * Getter method for property <tt>playerEmergencyPhone</tt>.
     * 
     * @return property value of playerEmergencyPhone
     */
    public String getPlayerEmergencyPhone() {
        return playerEmergencyPhone;
    }

    /**
     * Setter method for property <tt>playerEmergencyPhone</tt>.
     * 
     * @param playerEmergencyPhone value to be assigned to property playerEmergencyPhone
     */
    public void setPlayerEmergencyPhone(String playerEmergencyPhone) {
        this.playerEmergencyPhone = playerEmergencyPhone;
    }

    /**
     * Getter method for property <tt>matchCode</tt>.
     * 
     * @return property value of matchCode
     */
    public String getMatchCode() {
        return matchCode;
    }

    /**
     * Setter method for property <tt>matchCode</tt>.
     * 
     * @param matchCode value to be assigned to property matchCode
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    /**
     * Getter method for property <tt>fieldCode</tt>.
     * 
     * @return property value of fieldCode
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * Setter method for property <tt>fieldCode</tt>.
     * 
     * @param fieldCode value to be assigned to property fieldCode
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * Getter method for property <tt>matchGroupCode</tt>.
     * 
     * @return property value of matchGroupCode
     */
    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    /**
     * Setter method for property <tt>matchGroupCode</tt>.
     * 
     * @param matchGroupCode value to be assigned to property matchGroupCode
     */
    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode;
    }

    /**
     * Getter method for property <tt>eventCode</tt>.
     * 
     * @return property value of eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Setter method for property <tt>eventCode</tt>.
     * 
     * @param eventCode value to be assigned to property eventCode
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Getter method for property <tt>playerBirth</tt>.
     * 
     * @return property value of playerBirth
     */
    public String getPlayerBirth() {
        return playerBirth;
    }

    /**
     * Setter method for property <tt>playerBirth</tt>.
     * 
     * @param playerBirth value to be assigned to property playerBirth
     */
    public void setPlayerBirth(String playerBirth) {
        this.playerBirth = playerBirth;
    }

    /**
     * Getter method for property <tt>applyTime</tt>.
     * 
     * @return property value of applyTime
     */
    public String getApplyTime() {
        return applyTime;
    }

    /**
     * Setter method for property <tt>applyTime</tt>.
     * 
     * @param applyTime value to be assigned to property applyTime
     */
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * Getter method for property <tt>entryFee</tt>.
     * 
     * @return property value of entryFee
     */
    public Double getEntryFee() {
        return entryFee;
    }

    /**
     * Setter method for property <tt>entryFee</tt>.
     * 
     * @param entryFee value to be assigned to property entryFee
     */
    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    @Override
    public void assertEqual(ICommonModel commonModel) throws OpenException {
    }

    @Override
    public void validate() throws OpenException {
    }

    /**
     * Getter method for property <tt>playerName</tt>.
     * 
     * @return property value of playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter method for property <tt>playerName</tt>.
     * 
     * @param playerName value to be assigned to property playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Getter method for property <tt>matchName</tt>.
     * 
     * @return property value of matchName
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * Setter method for property <tt>matchName</tt>.
     * 
     * @param matchName value to be assigned to property matchName
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    /**
     * Getter method for property <tt>fieldName</tt>.
     * 
     * @return property value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter method for property <tt>fieldName</tt>.
     * 
     * @param fieldName value to be assigned to property fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter method for property <tt>matchGroupName</tt>.
     * 
     * @return property value of matchGroupName
     */
    public String getMatchGroupName() {
        return matchGroupName;
    }

    /**
     * Setter method for property <tt>matchGroupName</tt>.
     * 
     * @param matchGroupName value to be assigned to property matchGroupName
     */
    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
    }

    /**
     * Getter method for property <tt>eventName</tt>.
     * 
     * @return property value of eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Setter method for property <tt>eventName</tt>.
     * 
     * @param eventName value to be assigned to property eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Getter method for property <tt>gameCode</tt>.
     * 
     * @return property value of gameCode
     */
    public String getGameCode() {
        return gameCode;
    }

    /**
     * Setter method for property <tt>gameCode</tt>.
     * 
     * @param gameCode value to be assigned to property gameCode
     */
    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    /**
     * Getter method for property <tt>gameName</tt>.
     * 
     * @return property value of gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Setter method for property <tt>gameName</tt>.
     * 
     * @param gameName value to be assigned to property gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Getter method for property <tt>idempotentId</tt>.
     * 
     * @return property value of idempotentId
     */
    public String getIdempotentId() {
        return idempotentId;
    }

    /**
     * Setter method for property <tt>idempotentId</tt>.
     * 
     * @param idempotentId value to be assigned to property idempotentId
     */
    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    /**
     * Getter method for property <tt>playerWorkUnit</tt>.
     * 
     * @return property value of playerWorkUnit
     */
    public String getPlayerWorkUnit() {
        return playerWorkUnit;
    }

    /**
     * Setter method for property <tt>playerWorkUnit</tt>.
     * 
     * @param playerWorkUnit value to be assigned to property playerWorkUnit
     */
    public void setPlayerWorkUnit(String playerWorkUnit) {
        this.playerWorkUnit = playerWorkUnit;
    }

    /**
     * Getter method for property <tt>playerCategory</tt>.
     * 
     * @return property value of playerCategory
     */
    public String getPlayerCategory() {
        return playerCategory;
    }

    /**
     * Setter method for property <tt>playerCategory</tt>.
     * 
     * @param playerCategory value to be assigned to property playerCategory
     */
    public void setPlayerCategory(String playerCategory) {
        this.playerCategory = playerCategory;
    }

    /**
     * Getter method for property <tt>openPlatType</tt>.
     * 
     * @return property value of openPlatType
     */
    public String getOpenPlatType() {
        return openPlatType;
    }

    /**
     * Setter method for property <tt>openPlatType</tt>.
     * 
     * @param openPlatType value to be assigned to property openPlatType
     */
    public void setOpenPlatType(String openPlatType) {
        this.openPlatType = openPlatType;
    }

    /**
     * Getter method for property <tt>openId</tt>.
     * 
     * @return property value of openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * Setter method for property <tt>openId</tt>.
     * 
     * @param openId value to be assigned to property openId
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * Getter method for property <tt>extProp</tt>.
     * 
     * @return property value of extProp
     */
    public JSONObject getExtProp() {
        return extProp;
    }

    /**
     * Setter method for property <tt>extProp</tt>.
     * 
     * @param extProp value to be assigned to property extProp
     */
    public void setExtProp(JSONObject extProp) {
        this.extProp = extProp;
    }

    /**
     * Getter method for property <tt>modifyFlag</tt>.
     * 
     * @return property value of modifyFlag
     */
    public String getModifyFlag() {
        return modifyFlag;
    }

    /**
     * Setter method for property <tt>modifyFlag</tt>.
     * 
     * @param modifyFlag value to be assigned to property modifyFlag
     */
    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OpenEnrollModel [playerCode=" + playerCode + ", playerPhone=" + playerPhone
               + ", playerName=" + playerName + ", sex=" + sex + ", imgUrl=" + imgUrl + ", email="
               + email + ", playerHeight=" + playerHeight + ", playerWeight=" + playerWeight
               + ", playerBirth=" + playerBirth + ", playerNationality=" + playerNationality
               + ", playerWorkUnit=" + playerWorkUnit + ", playerAddress=" + playerAddress
               + ", playerCerType=" + playerCerType + ", playerCerNo=" + playerCerNo
               + ", playerBloodType=" + playerBloodType + ", playerNation=" + playerNation
               + ", playerClothSize=" + playerClothSize + ", playerEmergencyName="
               + playerEmergencyName + ", playerEmergencyPhone=" + playerEmergencyPhone
               + ", gameCode=" + gameCode + ", gameName=" + gameName + ", matchCode=" + matchCode
               + ", matchName=" + matchName + ", fieldCode=" + fieldCode + ", fieldName="
               + fieldName + ", matchGroupCode=" + matchGroupCode + ", matchGroupName="
               + matchGroupName + ", eventCode=" + eventCode + ", eventName=" + eventName
               + ", applyTime=" + applyTime + ", entryFee=" + entryFee + ", playerCategory="
               + playerCategory + ", openPlatType=" + openPlatType + ", openId=" + openId
               + ", extProp=" + extProp + ", idempotentId=" + idempotentId + ", modifyFlag="
               + modifyFlag + "]";
    }

}
