package com.efida.sports.dmp.dao.entity;

import java.util.Date;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 界面运动员报名信息响应Model
 * @author wang yi
 * @desc 
 * @version $Id: PlayerModel.java, v 0.1 2018年6月26日 下午2:29:37 wang yi Exp $
 */
@ApiModel(description = "运动员报名信息")
public class PlayerModel {

    @ApiModelProperty(value = "唯一标识(幂等ID)")
    private String  idempotentId;

    @ApiModelProperty(value = "承办方唯一编号")
    private String  unitCode;

    @ApiModelProperty(value = "运动员唯一标识")
    private String  playerCode;

    @ApiModelProperty(value = "运动员电话号码")
    private String  playerPhone;

    @ApiModelProperty(value = "报名信息唯一编号")
    private String  applyCode;

    @ApiModelProperty(value = "渠道编号")
    private String  channelCode;

    @ApiModelProperty(value = "报名时间")
    private Date    applyTime;

    @ApiModelProperty(value = "运动员名称")
    private String  playerName;

    @ApiModelProperty(value = "性别(M:男 F:女)")
    private String  sex;

    @ApiModelProperty(value = "用户唯一编号")
    private String  registerCode;

    @ApiModelProperty(value = "邮箱")
    private String  email;

    @ApiModelProperty(value = "身高")
    private Integer playerHeight;

    @ApiModelProperty(value = "体重")
    private Double  playerWeight;

    @ApiModelProperty(value = "生日")
    private Date    playerBirth;

    @ApiModelProperty(value = "国籍")
    private String  playerNationality;

    @ApiModelProperty(value = "工作单位")
    private String  playerWorkUnit;

    @ApiModelProperty(value = "地址")
    private String  playerAddress;

    @ApiModelProperty(value = "证件类型 1身份证。 2.护照 3.驾驶证")
    private String  playerCerType;

    @ApiModelProperty(value = "证件号码", example = "xingguo")
    private String  playerCerNo;

    @ApiModelProperty(value = "血型")
    private String  playerBloodType;

    @ApiModelProperty(value = "民族")
    private String  playerNation;

    @ApiModelProperty(value = "衣服尺码")
    private String  playerClothSize;

    @ApiModelProperty(value = "紧急联系人")
    private String  playerEmergencyName;

    @ApiModelProperty(value = "紧急联系人电")
    private String  playerEmergencyPhone;

    @ApiModelProperty(value = "附件地址")
    private String  attUrl;

    @ApiModelProperty(value = "头像地址")
    private String  imgUrl;

    @ApiModelProperty(value = "common:普通用户，student:学生，square:广场用户")
    private String  playerCategory;

    @ApiModelProperty(value = "来源:1 来自接口; 其他值来自官网")
    private Integer source;

    @ApiModelProperty(value = "拓展数据(JSON数据)")
    private String  extProp;

    @ApiModelProperty(value = "项目编号")
    private String  gameCode;

    @ApiModelProperty(value = "项目名称")
    private String  gameName;

    @ApiModelProperty(value = "赛事编号")
    private String  matchCode;

    @ApiModelProperty(value = " 赛事名称")
    private String  matchName;

    @ApiModelProperty(value = "比赛分组编号")
    private String  matchGroupCode;

    @ApiModelProperty(value = " 比赛分组名称")
    private String  matchGroupName;

    @ApiModelProperty(value = "比赛项(细类)编号")
    private String  eventCode;

    @ApiModelProperty(value = " 比赛项(细类)名称")
    private String  eventName;

    @ApiModelProperty(value = "领队名称")
    private String  leaderName;

    @ApiModelProperty(value = "领队电话")
    private String  leaderPhone;

    @ApiModelProperty(value = "团队名称")
    private String  teamName;

    @ApiModelProperty(value = "类型（个人:personal,团体：group）")
    private String  eventType;

    @ApiModelProperty(value = "团队人数")
    private Integer registrationNum;

    @ApiModelProperty(value = "报名费用(已经转换为元)")
    private Long    applyAmount;

    @ApiModelProperty(value = "报名状态(待付款,成功)")
    private String  applyStatus;

    @ApiModelProperty(value = "赛场编号")
    private String  fieldCode;

    @ApiModelProperty(value = "赛场名称")
    private String  fieldName;

    @ApiModelProperty(value = "项目开始时间")
    private Date    eventStartTime;

    @ApiModelProperty(value = "项目结束时间")
    private Date    eventEndTime;

    @ApiModelProperty(value = "同步标志位,1已经同步；0未同步")
    private Byte    sync;

    @ApiModelProperty(value = "同步标志位,1已经同步；0未同步")
    private Integer syncTotal;

    @ApiModelProperty(value = "广场用户时提供。weixin:微信、weibo:微博、qq:QQ，等")
    private String  openPlatType;

    @ApiModelProperty(value = "三方平台openId")
    private String  openId;

    @ApiModelProperty(value = "批次号")
    private String  batchNumber;

    private Date    gmtCreate;

    private Date    gmtModified;

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode == null ? null : playerCode.trim();
    }

    public String getPlayerPhone() {
        return playerPhone;
    }

    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone == null ? null : playerPhone.trim();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName == null ? null : playerName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode == null ? null : registerCode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(Integer registrationNum) {
        this.registrationNum = registrationNum;
    }

    public Long getApplyAmount() {
        if (applyAmount == null) {
            return null;
        }
        // 获取金额转换为元
        return applyAmount / 100;
    }

    public void setApplyAmount(Long applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Date getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Byte getSync() {
        return sync;
    }

    public void setSync(Byte sync) {
        this.sync = sync;
    }

    public Integer getSyncTotal() {
        return syncTotal;
    }

    public void setSyncTotal(Integer syncTotal) {
        this.syncTotal = syncTotal;
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

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_nationality
     *
     * @return the value of open_player.player_nationality
     *
     * @mbggenerated
     */
    public String getPlayerNationality() {
        return playerNationality;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_nationality
     *
     * @param playerNationality the value for open_player.player_nationality
     *
     * @mbggenerated
     */
    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality == null ? null : playerNationality.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_address
     *
     * @return the value of open_player.player_address
     *
     * @mbggenerated
     */
    public String getPlayerAddress() {
        return playerAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_address
     *
     * @param playerAddress the value for open_player.player_address
     *
     * @mbggenerated
     */
    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress == null ? null : playerAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_cer_type
     *
     * @return the value of open_player.player_cer_type
     *
     * @mbggenerated
     */
    public String getPlayerCerType() {
        return playerCerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_cer_type
     *
     * @param playerCerType the value for open_player.player_cer_type
     *
     * @mbggenerated
     */
    public void setPlayerCerType(String playerCerType) {
        this.playerCerType = playerCerType == null ? null : playerCerType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_cer_no
     *
     * @return the value of open_player.player_cer_no
     *
     * @mbggenerated
     */
    public String getPlayerCerNo() {
        return playerCerNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_cer_no
     *
     * @param playerCerNo the value for open_player.player_cer_no
     *
     * @mbggenerated
     */
    public void setPlayerCerNo(String playerCerNo) {
        this.playerCerNo = playerCerNo == null ? null : playerCerNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_blood_type
     *
     * @return the value of open_player.player_blood_type
     *
     * @mbggenerated
     */
    public String getPlayerBloodType() {
        return playerBloodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_blood_type
     *
     * @param playerBloodType the value for open_player.player_blood_type
     *
     * @mbggenerated
     */
    public void setPlayerBloodType(String playerBloodType) {
        this.playerBloodType = playerBloodType == null ? null : playerBloodType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_nation
     *
     * @return the value of open_player.player_nation
     *
     * @mbggenerated
     */
    public String getPlayerNation() {
        return playerNation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_nation
     *
     * @param playerNation the value for open_player.player_nation
     *
     * @mbggenerated
     */
    public void setPlayerNation(String playerNation) {
        this.playerNation = playerNation == null ? null : playerNation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_cloth_size
     *
     * @return the value of open_player.player_cloth_size
     *
     * @mbggenerated
     */
    public String getPlayerClothSize() {
        return playerClothSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_cloth_size
     *
     * @param playerClothSize the value for open_player.player_cloth_size
     *
     * @mbggenerated
     */
    public void setPlayerClothSize(String playerClothSize) {
        this.playerClothSize = playerClothSize == null ? null : playerClothSize.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_emergency_name
     *
     * @return the value of open_player.player_emergency_name
     *
     * @mbggenerated
     */
    public String getPlayerEmergencyName() {
        return playerEmergencyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_emergency_name
     *
     * @param playerEmergencyName the value for open_player.player_emergency_name
     *
     * @mbggenerated
     */
    public void setPlayerEmergencyName(String playerEmergencyName) {
        this.playerEmergencyName = playerEmergencyName == null ? null : playerEmergencyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.player_emergency_phone
     *
     * @return the value of open_player.player_emergency_phone
     *
     * @mbggenerated
     */
    public String getPlayerEmergencyPhone() {
        return playerEmergencyPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.player_emergency_phone
     *
     * @param playerEmergencyPhone the value for open_player.player_emergency_phone
     *
     * @mbggenerated
     */
    public void setPlayerEmergencyPhone(String playerEmergencyPhone) {
        this.playerEmergencyPhone = playerEmergencyPhone == null ? null
            : playerEmergencyPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.att_url
     *
     * @return the value of open_player.att_url
     *
     * @mbggenerated
     */
    public String getAttUrl() {
        return attUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.att_url
     *
     * @param attUrl the value for open_player.att_url
     *
     * @mbggenerated
     */
    public void setAttUrl(String attUrl) {
        this.attUrl = attUrl == null ? null : attUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.img_url
     *
     * @return the value of open_player.img_url
     *
     * @mbggenerated
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.img_url
     *
     * @param imgUrl the value for open_player.img_url
     *
     * @mbggenerated
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.gmt_create
     *
     * @return the value of open_player.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.gmt_create
     *
     * @param gmtCreate the value for open_player.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_player.gmt_modified
     *
     * @return the value of open_player.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_player.gmt_modified
     *
     * @param gmtModified the value for open_player.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
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

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchGroupName() {
        return matchGroupName;
    }

    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getExtProp() {
        return extProp;
    }

    public void setExtProp(String extProp) {
        this.extProp = extProp;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getPlayerBirthStr() {
        return playerBirth == null ? null : DateUtil.formatDate(playerBirth);
    }

    public String getApplyTimeStr() {
        return applyTime == null ? null : DateUtil.formatDate(applyTime);
    }

    public String getGmtCreateStr() {
        return gmtCreate == null ? null : DateUtil.formatDate(gmtCreate);
    }

    public String getGmtModifiedStr() {
        return gmtModified == null ? null : DateUtil.formatDate(gmtModified);
    }

    public String getEventEndTimeStr() {
        return eventEndTime == null ? null : DateUtil.formatDate(eventEndTime);
    }

}