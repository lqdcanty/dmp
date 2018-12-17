package com.efida.sports.dmp.dao.entity;

import java.util.Date;

public class OpenEnrollInfoEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.id
    *
    * @mbggenerated
    */
    private Long    id;

    //open_player 表 id
    private Long    playerId;
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.apply_time
    *
    * @mbggenerated
    */
    private Date    applyTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.apply_code
    *
    * @mbggenerated
    */
    private String  applyCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.leader_name
    *
    * @mbggenerated
    */
    private String  leaderName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.leader_phone
    *
    * @mbggenerated
    */
    private String  leaderPhone;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.term_name
    *
    * @mbggenerated
    */
    private String  teamName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.event_type
    *
    * @mbggenerated
    */
    private String  eventType;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.registration_num
    *
    * @mbggenerated
    */
    private Integer registrationNum;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.apply_amount
    *
    * @mbggenerated
    */
    private Long    applyAmount;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.apply_status
    *
    * @mbggenerated
    */
    private String  applyStatus;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.unit_code
    *
    * @mbggenerated
    */
    private String  unitCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.game_code
    *
    * @mbggenerated
    */
    private String  gameCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.game_name
    *
    * @mbggenerated
    */
    private String  gameName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.match_code
    *
    * @mbggenerated
    */
    private String  matchCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.match_name
    *
    * @mbggenerated
    */
    private String  matchName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.field_code
    *
    * @mbggenerated
    */
    private String  fieldCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.field_name
    *
    * @mbggenerated
    */
    private String  fieldName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.match_group_name
    *
    * @mbggenerated
    */
    private String  matchGroupName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.match_group_code
    *
    * @mbggenerated
    */
    private String  matchGroupCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.event_code
    *
    * @mbggenerated
    */
    private String  eventCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.event_name
    *
    * @mbggenerated
    */
    private String  eventName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.event_start_time
    *
    * @mbggenerated
    */
    private Date    eventStartTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.event_end_time
    *
    * @mbggenerated
    */
    private Date    eventEndTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.is_delet
    *
    * @mbggenerated
    */
    private Byte    isDelet;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.expire_time
    *
    * @mbggenerated
    */
    private Date    expireTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.channel_code
    *
    * @mbggenerated
    */
    private String  channelCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.idempotent_id
    *
    * @mbggenerated
    */
    private String  idempotentId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.source
    *
    * @mbggenerated
    */
    private Byte    source;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync
    *
    * @mbggenerated
    */
    private Byte    sync;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync_total
    *
    * @mbggenerated
    */
    private Integer syncTotal;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync_status
    *
    * @mbggenerated
    */
    private String  syncStatus;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.next_sync_time
    *
    * @mbggenerated
    */
    private Date    nextSyncTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_code
     *
     * @mbggenerated
     */
    private String  playerCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_no
     *
     * @mbggenerated
     */
    private Integer playerNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_phone
     *
     * @mbggenerated
     */
    private String  playerPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_name
     *
     * @mbggenerated
     */
    private String  playerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.sex
     *
     * @mbggenerated
     */
    private String  sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.register_code
     *
     * @mbggenerated
     */
    private String  registerCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.email
     *
     * @mbggenerated
     */
    private String  email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_height
     *
     * @mbggenerated
     */
    private Integer playerHeight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_weight
     *
     * @mbggenerated
     */
    private Double  playerWeight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_birth
     *
     * @mbggenerated
     */
    private Date    playerBirth;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_nationality
     *
     * @mbggenerated
     */
    private String  playerNationality;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_work_unit
     *
     * @mbggenerated
     */
    private String  playerWorkUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_address
     *
     * @mbggenerated
     */
    private String  playerAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_cer_type
     *
     * @mbggenerated
     */
    private String  playerCerType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_cer_no
     *
     * @mbggenerated
     */
    private String  playerCerNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_blood_type
     *
     * @mbggenerated
     */
    private String  playerBloodType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_nation
     *
     * @mbggenerated
     */
    private String  playerNation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_cloth_size
     *
     * @mbggenerated
     */
    private String  playerClothSize;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_emergency_name
     *
     * @mbggenerated
     */
    private String  playerEmergencyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_emergency_phone
     *
     * @mbggenerated
     */
    private String  playerEmergencyPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.att_url
     *
     * @mbggenerated
     */
    private String  attUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.img_url
     *
     * @mbggenerated
     */
    private String  imgUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.player_category
     *
     * @mbggenerated
     */
    private String  playerCategory;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.open_plat_type
     *
     * @mbggenerated
     */
    private String  openPlatType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.open_id
     *
     * @mbggenerated
     */
    private String  openId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.ext_prop
     *
     * @mbggenerated
     */
    private String  extProp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_player.is_delete
     *
     * @mbggenerated
     */
    private Byte    isDelete;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.gmt_create
    *
    * @mbggenerated
    */
    private Date    gmtCreate;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.gmt_modified
    *
    * @mbggenerated
    */
    private Date    gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.applyCode = applyCode;
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
        return applyAmount;
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
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

    public String getMatchGroupName() {
        return matchGroupName;
    }

    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public Byte getIsDelet() {
        return isDelet;
    }

    public void setIsDelet(Byte isDelet) {
        this.isDelet = isDelet;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Date getNextSyncTime() {
        return nextSyncTime;
    }

    public void setNextSyncTime(Date nextSyncTime) {
        this.nextSyncTime = nextSyncTime;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
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

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

}