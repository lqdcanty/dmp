package com.efida.sports.dmp.service.template;

import java.util.Date;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 运动员导入模板
 * @author wang yi
 * @desc 
 * @version $Id: PlayerVo.java, v 0.1 2018年7月31日 下午3:13:11 wang yi Exp $
 */
public class PlayerTemplate {

    @ExcelAttribute(name = "报名唯一标识", column = "A")
    private String  refIdempotentId;

    private String  playerCode;

    private String  applyCode;

    @ExcelAttribute(name = "运动员序号", column = "B")
    private Integer playerNo;

    @ExcelAttribute(name = "运动员电话号码", column = "C")
    private String  playerPhone;

    @ExcelAttribute(name = "运动员名称", column = "D")
    private String  playerName;

    @ExcelAttribute(name = "性别", column = "E")
    private String  sex;

    private String  registerCode;

    @ExcelAttribute(name = "邮箱", column = "F")
    private String  email;

    @ExcelAttribute(name = "身高", column = "G")
    private Integer playerHeight;

    @ExcelAttribute(name = "体重", column = "H")
    private Double  playerWeight;

    @ExcelAttribute(name = "生日", column = "I")
    private Date    playerBirth;

    @ExcelAttribute(name = "国籍", column = "J")
    private String  playerNationality;

    @ExcelAttribute(name = "工作单位", column = "K")
    private String  playerWorkUnit;

    @ExcelAttribute(name = "地址", column = "L")
    private String  playerAddress;

    @ExcelAttribute(name = "证件类型", column = "M")
    private String  playerCerType;

    @ExcelAttribute(name = "证件号码", column = "N")
    private String  playerCerNo;

    @ExcelAttribute(name = "血型", column = "O")
    private String  playerBloodType;

    @ExcelAttribute(name = "民族", column = "P")
    private String  playerNation;

    @ExcelAttribute(name = "衣服尺码", column = "Q")
    private String  playerClothSize;

    @ExcelAttribute(name = "紧急联系人", column = "R")
    private String  playerEmergencyName;

    @ExcelAttribute(name = "紧急联系人电话人", column = "S")
    private String  playerEmergencyPhone;

    @ExcelAttribute(name = "附件地址", column = "T")
    private String  attUrl;

    @ExcelAttribute(name = "头像地址", column = "U")
    private String  imgUrl;

    @ExcelAttribute(name = "", column = "U")
    private String  playerCategory;

    @ExcelAttribute(name = "类型", column = "U")
    private String  openPlatType;

    @ExcelAttribute(name = "外部账号ID", column = "X")
    private String  openId;

    @ExcelAttribute(name = "拓展属性", column = "Y")
    private String  extProp;

    private Byte    isDelete;

    private Date    gmtCreate;

    private Date    gmtModified;

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Integer getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(Integer playerNo) {
        this.playerNo = playerNo;
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

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
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

    public Date getPlayerBirth() {
        return playerBirth;
    }

    public void setPlayerBirth(Date playerBirth) {
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

    public String getPlayerCerType() {
        return playerCerType;
    }

    public void setPlayerCerType(String playerCerType) {
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

    public String getAttUrl() {
        return attUrl;
    }

    public void setAttUrl(String attUrl) {
        this.attUrl = attUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getExtProp() {
        return extProp;
    }

    public void setExtProp(String extProp) {
        this.extProp = extProp;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRefIdempotentId() {
        return refIdempotentId;
    }

    public void setRefIdempotentId(String refIdempotentId) {
        this.refIdempotentId = refIdempotentId;
    }

}