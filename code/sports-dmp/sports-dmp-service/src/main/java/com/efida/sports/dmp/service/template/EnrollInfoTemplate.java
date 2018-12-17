package com.efida.sports.dmp.service.template;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 报名Excel模板Vo
 * @author wang yi
 * @desc 
 * @version $Id: EnrollInfoExcelVo.java, v 0.1 2018年7月27日 下午4:19:46 wang yi Exp $
 */
public class EnrollInfoTemplate {

    @NotEmpty(message = "报名唯一标识不能为空")
    @Size(min = 0, max = 64, message = "报名唯一标识名需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "A", name = "报名唯一标识")
    private String  idempotentId;

    @ExcelAttribute(column = "C", name = "报名时间")
    private Date    applyTime;

    @NotEmpty(message = "报名信息唯一编号不能为空")
    @Size(min = 0, max = 20, message = "报名信息唯一编号需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "B", name = "报名信息唯一编号")
    private String  applyCode;

    private String  payOrderCode;

    private Long    applyAmount;

    @ExcelAttribute(column = "D", name = "报名费用(元)")
    private Long    applyAmountY;

    private String  applyStatus;

    private String  unitCode;

    @NotEmpty(message = "项目编号不能为空")
    @ExcelAttribute(column = "E", name = "项目编号")
    private String  gameCode;

    @NotEmpty(message = "项目名称不能为空")
    @ExcelAttribute(column = "F", name = "项目名称")
    private String  gameName;

    @NotEmpty(message = "赛事编号不能为空")
    @ExcelAttribute(column = "G", name = "赛事编号")
    private String  matchCode;

    @NotEmpty(message = "赛事名称不能为空")
    @ExcelAttribute(column = "H", name = "赛事名称")
    private String  matchName;

    @NotEmpty(message = "赛场编号不能为空")
    @ExcelAttribute(column = "I", name = "赛场编号")
    private String  siteCode;

    @NotEmpty(message = "赛场名称不能为空")
    @ExcelAttribute(column = "J", name = "赛场名称")
    private String  siteName;

    @ExcelAttribute(column = "K", name = "赛事分组名称")
    private String  matchGroupName;

    @ExcelAttribute(column = "L", name = "赛事分组编号")
    private String  matchGroupCode;

    @NotEmpty(message = "比赛项(细类)编号不能为空")
    @ExcelAttribute(column = "M", name = "比赛项编号")
    private String  eventCode;

    @NotEmpty(message = "比赛项(细类)名称不能为空")
    @ExcelAttribute(column = "N", name = "比赛项名称")
    private String  eventName;

    @ExcelAttribute(column = "O", name = "比赛项开始时间")
    private Date    eventStartTime;

    @ExcelAttribute(column = "P", name = "比赛项结束时间")
    private Date    eventEndTime;

    private String  playerCode;

    @NotEmpty(message = "运动员电话号码不能为空")
    @ExcelAttribute(column = "Q", name = "运动员电话号码")
    private String  playerPhone;

    @NotEmpty(message = "运动员名称不能为空")
    @ExcelAttribute(column = "R", name = "运动员名称")
    private String  playerName;

    @ExcelAttribute(column = "S", name = "性别")
    private String  sex;

    private String  registerCode;

    @ExcelAttribute(column = "T", name = "邮箱")
    private String  email;

    @ExcelAttribute(column = "U", name = "身高")
    private Integer playerHeight;

    @ExcelAttribute(column = "V", name = "体重")
    private Double  playerWeight;

    @ExcelAttribute(column = "W", name = "生日")
    private Date    playerBirth;

    @ExcelAttribute(column = "X", name = "国籍")
    private String  playerNationality;

    @ExcelAttribute(column = "Y", name = "工作地址")
    private String  playerWorkUnit;

    @ExcelAttribute(column = "Z", name = "地址")
    private String  playerAddress;

    @ExcelAttribute(column = "AA", name = "证件类型")
    private String  playerCerType;

    @ExcelAttribute(column = "AB", name = "证件号码")
    private String  playerCerNo;

    @ExcelAttribute(column = "AC", name = "血型")
    private String  playerBloodType;

    @ExcelAttribute(column = "AD", name = "民族")
    private String  playerNation;

    @ExcelAttribute(column = "AE", name = "衣服尺码")
    private String  playerClothSize;

    @ExcelAttribute(column = "AF", name = "紧急联系人")
    private String  playerEmergencyName;

    @ExcelAttribute(column = "AG", name = "紧急联系人电话")
    private String  playerEmergencyPhone;

    @ExcelAttribute(column = "AH", name = "附件地址")
    private String  attUrl;

    @ExcelAttribute(column = "AI", name = "头像地址")
    private String  imgUrl;

    private String  playerCategory;

    private String  openPlatType;

    private String  openId;

    @ExcelAttribute(column = "AM", name = "拓展属性")
    private String  extProp;

    private Byte    isDelet;

    private Date    expireTime;

    private String  channelCode;

    private Byte    source;

    private Byte    sync;

    private Integer syncTotal;

    private String  syncStatus;

    private Date    nextSyncTime;

    private Date    gmtCreate;

    private Date    gmtModified;

    public Long getApplyAmountY() {
        return this.applyAmountY;
    }

    public void setApplyAmountY(Long applyAmountY) {
        this.applyAmountY = applyAmountY;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getPayOrderCode() {
        return payOrderCode;
    }

    public void setPayOrderCode(String payOrderCode) {
        this.payOrderCode = payOrderCode == null ? null : payOrderCode.trim();
    }

    public Long getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Long applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.apply_status
     *
     * @param applyStatus the value for open_enroll_info.apply_status
     *
     * @mbggenerated
     */
    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.unit_code
     *
     * @return the value of open_enroll_info.unit_code
     *
     * @mbggenerated
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.unit_code
     *
     * @param unitCode the value for open_enroll_info.unit_code
     *
     * @mbggenerated
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.game_code
     *
     * @return the value of open_enroll_info.game_code
     *
     * @mbggenerated
     */
    public String getGameCode() {
        return gameCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.game_code
     *
     * @param gameCode the value for open_enroll_info.game_code
     *
     * @mbggenerated
     */
    public void setGameCode(String gameCode) {
        this.gameCode = gameCode == null ? null : gameCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.game_name
     *
     * @return the value of open_enroll_info.game_name
     *
     * @mbggenerated
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.game_name
     *
     * @param gameName the value for open_enroll_info.game_name
     *
     * @mbggenerated
     */
    public void setGameName(String gameName) {
        this.gameName = gameName == null ? null : gameName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.match_code
     *
     * @return the value of open_enroll_info.match_code
     *
     * @mbggenerated
     */
    public String getMatchCode() {
        return matchCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.match_code
     *
     * @param matchCode the value for open_enroll_info.match_code
     *
     * @mbggenerated
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode == null ? null : matchCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.match_name
     *
     * @return the value of open_enroll_info.match_name
     *
     * @mbggenerated
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.match_name
     *
     * @param matchName the value for open_enroll_info.match_name
     *
     * @mbggenerated
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName == null ? null : matchName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.site_code
     *
     * @return the value of open_enroll_info.site_code
     *
     * @mbggenerated
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.site_code
     *
     * @param siteCode the value for open_enroll_info.site_code
     *
     * @mbggenerated
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode == null ? null : siteCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.site_name
     *
     * @return the value of open_enroll_info.site_name
     *
     * @mbggenerated
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.site_name
     *
     * @param siteName the value for open_enroll_info.site_name
     *
     * @mbggenerated
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.match_group_name
     *
     * @return the value of open_enroll_info.match_group_name
     *
     * @mbggenerated
     */
    public String getMatchGroupName() {
        return matchGroupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.match_group_name
     *
     * @param matchGroupName the value for open_enroll_info.match_group_name
     *
     * @mbggenerated
     */
    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName == null ? null : matchGroupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.match_group_code
     *
     * @return the value of open_enroll_info.match_group_code
     *
     * @mbggenerated
     */
    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.match_group_code
     *
     * @param matchGroupCode the value for open_enroll_info.match_group_code
     *
     * @mbggenerated
     */
    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode == null ? null : matchGroupCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.event_code
     *
     * @return the value of open_enroll_info.event_code
     *
     * @mbggenerated
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.event_code
     *
     * @param eventCode the value for open_enroll_info.event_code
     *
     * @mbggenerated
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode == null ? null : eventCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.event_name
     *
     * @return the value of open_enroll_info.event_name
     *
     * @mbggenerated
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.event_name
     *
     * @param eventName the value for open_enroll_info.event_name
     *
     * @mbggenerated
     */
    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.event_start_time
     *
     * @return the value of open_enroll_info.event_start_time
     *
     * @mbggenerated
     */
    public Date getEventStartTime() {
        return eventStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.event_start_time
     *
     * @param eventStartTime the value for open_enroll_info.event_start_time
     *
     * @mbggenerated
     */
    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.event_end_time
     *
     * @return the value of open_enroll_info.event_end_time
     *
     * @mbggenerated
     */
    public Date getEventEndTime() {
        return eventEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.event_end_time
     *
     * @param eventEndTime the value for open_enroll_info.event_end_time
     *
     * @mbggenerated
     */
    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_code
     *
     * @return the value of open_enroll_info.player_code
     *
     * @mbggenerated
     */
    public String getPlayerCode() {
        return playerCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_code
     *
     * @param playerCode the value for open_enroll_info.player_code
     *
     * @mbggenerated
     */
    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode == null ? null : playerCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_phone
     *
     * @return the value of open_enroll_info.player_phone
     *
     * @mbggenerated
     */
    public String getPlayerPhone() {
        return playerPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_phone
     *
     * @param playerPhone the value for open_enroll_info.player_phone
     *
     * @mbggenerated
     */
    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone == null ? null : playerPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_name
     *
     * @return the value of open_enroll_info.player_name
     *
     * @mbggenerated
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_name
     *
     * @param playerName the value for open_enroll_info.player_name
     *
     * @mbggenerated
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName == null ? null : playerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.sex
     *
     * @return the value of open_enroll_info.sex
     *
     * @mbggenerated
     */
    public String getSex() {
        return sex == null ? null : sex.trim().toLowerCase();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.sex
     *
     * @param sex the value for open_enroll_info.sex
     *
     * @mbggenerated
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim().toLowerCase();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.register_code
     *
     * @return the value of open_enroll_info.register_code
     *
     * @mbggenerated
     */
    public String getRegisterCode() {
        return registerCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.register_code
     *
     * @param registerCode the value for open_enroll_info.register_code
     *
     * @mbggenerated
     */
    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode == null ? null : registerCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.email
     *
     * @return the value of open_enroll_info.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.email
     *
     * @param email the value for open_enroll_info.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_height
     *
     * @return the value of open_enroll_info.player_height
     *
     * @mbggenerated
     */
    public Integer getPlayerHeight() {
        return playerHeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_height
     *
     * @param playerHeight the value for open_enroll_info.player_height
     *
     * @mbggenerated
     */
    public void setPlayerHeight(Integer playerHeight) {
        this.playerHeight = playerHeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_weight
     *
     * @return the value of open_enroll_info.player_weight
     *
     * @mbggenerated
     */
    public Double getPlayerWeight() {
        return playerWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_weight
     *
     * @param playerWeight the value for open_enroll_info.player_weight
     *
     * @mbggenerated
     */
    public void setPlayerWeight(Double playerWeight) {
        this.playerWeight = playerWeight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_birth
     *
     * @return the value of open_enroll_info.player_birth
     *
     * @mbggenerated
     */
    public Date getPlayerBirth() {
        return playerBirth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_birth
     *
     * @param playerBirth the value for open_enroll_info.player_birth
     *
     * @mbggenerated
     */
    public void setPlayerBirth(Date playerBirth) {
        this.playerBirth = playerBirth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_nationality
     *
     * @return the value of open_enroll_info.player_nationality
     *
     * @mbggenerated
     */
    public String getPlayerNationality() {
        return playerNationality;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_nationality
     *
     * @param playerNationality the value for open_enroll_info.player_nationality
     *
     * @mbggenerated
     */
    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality == null ? null : playerNationality.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_work_unit
     *
     * @return the value of open_enroll_info.player_work_unit
     *
     * @mbggenerated
     */
    public String getPlayerWorkUnit() {
        return playerWorkUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_work_unit
     *
     * @param playerWorkUnit the value for open_enroll_info.player_work_unit
     *
     * @mbggenerated
     */
    public void setPlayerWorkUnit(String playerWorkUnit) {
        this.playerWorkUnit = playerWorkUnit == null ? null : playerWorkUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_address
     *
     * @return the value of open_enroll_info.player_address
     *
     * @mbggenerated
     */
    public String getPlayerAddress() {
        return playerAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_address
     *
     * @param playerAddress the value for open_enroll_info.player_address
     *
     * @mbggenerated
     */
    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress == null ? null : playerAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_cer_type
     *
     * @return the value of open_enroll_info.player_cer_type
     *
     * @mbggenerated
     */
    public String getPlayerCerType() {
        return playerCerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_cer_type
     *
     * @param playerCerType the value for open_enroll_info.player_cer_type
     *
     * @mbggenerated
     */
    public void setPlayerCerType(String playerCerType) {
        this.playerCerType = playerCerType == null ? null : playerCerType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_cer_no
     *
     * @return the value of open_enroll_info.player_cer_no
     *
     * @mbggenerated
     */
    public String getPlayerCerNo() {
        return playerCerNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_cer_no
     *
     * @param playerCerNo the value for open_enroll_info.player_cer_no
     *
     * @mbggenerated
     */
    public void setPlayerCerNo(String playerCerNo) {
        this.playerCerNo = playerCerNo == null ? null : playerCerNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_blood_type
     *
     * @return the value of open_enroll_info.player_blood_type
     *
     * @mbggenerated
     */
    public String getPlayerBloodType() {
        return playerBloodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_blood_type
     *
     * @param playerBloodType the value for open_enroll_info.player_blood_type
     *
     * @mbggenerated
     */
    public void setPlayerBloodType(String playerBloodType) {
        this.playerBloodType = playerBloodType == null ? null : playerBloodType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_nation
     *
     * @return the value of open_enroll_info.player_nation
     *
     * @mbggenerated
     */
    public String getPlayerNation() {
        return playerNation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_nation
     *
     * @param playerNation the value for open_enroll_info.player_nation
     *
     * @mbggenerated
     */
    public void setPlayerNation(String playerNation) {
        this.playerNation = playerNation == null ? null : playerNation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_cloth_size
     *
     * @return the value of open_enroll_info.player_cloth_size
     *
     * @mbggenerated
     */
    public String getPlayerClothSize() {
        return playerClothSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_cloth_size
     *
     * @param playerClothSize the value for open_enroll_info.player_cloth_size
     *
     * @mbggenerated
     */
    public void setPlayerClothSize(String playerClothSize) {
        this.playerClothSize = playerClothSize == null ? null : playerClothSize.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_emergency_name
     *
     * @return the value of open_enroll_info.player_emergency_name
     *
     * @mbggenerated
     */
    public String getPlayerEmergencyName() {
        return playerEmergencyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_emergency_name
     *
     * @param playerEmergencyName the value for open_enroll_info.player_emergency_name
     *
     * @mbggenerated
     */
    public void setPlayerEmergencyName(String playerEmergencyName) {
        this.playerEmergencyName = playerEmergencyName == null ? null : playerEmergencyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_emergency_phone
     *
     * @return the value of open_enroll_info.player_emergency_phone
     *
     * @mbggenerated
     */
    public String getPlayerEmergencyPhone() {
        return playerEmergencyPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_emergency_phone
     *
     * @param playerEmergencyPhone the value for open_enroll_info.player_emergency_phone
     *
     * @mbggenerated
     */
    public void setPlayerEmergencyPhone(String playerEmergencyPhone) {
        this.playerEmergencyPhone = playerEmergencyPhone == null ? null
            : playerEmergencyPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.att_url
     *
     * @return the value of open_enroll_info.att_url
     *
     * @mbggenerated
     */
    public String getAttUrl() {
        return attUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.att_url
     *
     * @param attUrl the value for open_enroll_info.att_url
     *
     * @mbggenerated
     */
    public void setAttUrl(String attUrl) {
        this.attUrl = attUrl == null ? null : attUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.img_url
     *
     * @return the value of open_enroll_info.img_url
     *
     * @mbggenerated
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.img_url
     *
     * @param imgUrl the value for open_enroll_info.img_url
     *
     * @mbggenerated
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.player_category
     *
     * @return the value of open_enroll_info.player_category
     *
     * @mbggenerated
     */
    public String getPlayerCategory() {
        return playerCategory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.player_category
     *
     * @param playerCategory the value for open_enroll_info.player_category
     *
     * @mbggenerated
     */
    public void setPlayerCategory(String playerCategory) {
        this.playerCategory = playerCategory == null ? null : playerCategory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.open_plat_type
     *
     * @return the value of open_enroll_info.open_plat_type
     *
     * @mbggenerated
     */
    public String getOpenPlatType() {
        return openPlatType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.open_plat_type
     *
     * @param openPlatType the value for open_enroll_info.open_plat_type
     *
     * @mbggenerated
     */
    public void setOpenPlatType(String openPlatType) {
        this.openPlatType = openPlatType == null ? null : openPlatType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.open_id
     *
     * @return the value of open_enroll_info.open_id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.open_id
     *
     * @param openId the value for open_enroll_info.open_id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.ext_prop
     *
     * @return the value of open_enroll_info.ext_prop
     *
     * @mbggenerated
     */
    public String getExtProp() {
        return extProp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.ext_prop
     *
     * @param extProp the value for open_enroll_info.ext_prop
     *
     * @mbggenerated
     */
    public void setExtProp(String extProp) {
        this.extProp = extProp == null ? null : extProp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.is_delet
     *
     * @return the value of open_enroll_info.is_delet
     *
     * @mbggenerated
     */
    public Byte getIsDelet() {
        return isDelet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.is_delet
     *
     * @param isDelet the value for open_enroll_info.is_delet
     *
     * @mbggenerated
     */
    public void setIsDelet(Byte isDelet) {
        this.isDelet = isDelet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.expire_time
     *
     * @return the value of open_enroll_info.expire_time
     *
     * @mbggenerated
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.expire_time
     *
     * @param expireTime the value for open_enroll_info.expire_time
     *
     * @mbggenerated
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.channel_code
     *
     * @return the value of open_enroll_info.channel_code
     *
     * @mbggenerated
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.channel_code
     *
     * @param channelCode the value for open_enroll_info.channel_code
     *
     * @mbggenerated
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.idempotent_id
     *
     * @return the value of open_enroll_info.idempotent_id
     *
     * @mbggenerated
     */
    public String getIdempotentId() {
        return idempotentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.idempotent_id
     *
     * @param idempotentId the value for open_enroll_info.idempotent_id
     *
     * @mbggenerated
     */
    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId == null ? null : idempotentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.source
     *
     * @return the value of open_enroll_info.source
     *
     * @mbggenerated
     */
    public Byte getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.source
     *
     * @param source the value for open_enroll_info.source
     *
     * @mbggenerated
     */
    public void setSource(Byte source) {
        this.source = source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.sync
     *
     * @return the value of open_enroll_info.sync
     *
     * @mbggenerated
     */
    public Byte getSync() {
        return sync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.sync
     *
     * @param sync the value for open_enroll_info.sync
     *
     * @mbggenerated
     */
    public void setSync(Byte sync) {
        this.sync = sync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.sync_total
     *
     * @return the value of open_enroll_info.sync_total
     *
     * @mbggenerated
     */
    public Integer getSyncTotal() {
        return syncTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.sync_total
     *
     * @param syncTotal the value for open_enroll_info.sync_total
     *
     * @mbggenerated
     */
    public void setSyncTotal(Integer syncTotal) {
        this.syncTotal = syncTotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.sync_status
     *
     * @return the value of open_enroll_info.sync_status
     *
     * @mbggenerated
     */
    public String getSyncStatus() {
        return syncStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.sync_status
     *
     * @param syncStatus the value for open_enroll_info.sync_status
     *
     * @mbggenerated
     */
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus == null ? null : syncStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.next_sync_time
     *
     * @return the value of open_enroll_info.next_sync_time
     *
     * @mbggenerated
     */
    public Date getNextSyncTime() {
        return nextSyncTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.next_sync_time
     *
     * @param nextSyncTime the value for open_enroll_info.next_sync_time
     *
     * @mbggenerated
     */
    public void setNextSyncTime(Date nextSyncTime) {
        this.nextSyncTime = nextSyncTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.gmt_create
     *
     * @return the value of open_enroll_info.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.gmt_create
     *
     * @param gmtCreate the value for open_enroll_info.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_enroll_info.gmt_modified
     *
     * @return the value of open_enroll_info.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_enroll_info.gmt_modified
     *
     * @param gmtModified the value for open_enroll_info.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}