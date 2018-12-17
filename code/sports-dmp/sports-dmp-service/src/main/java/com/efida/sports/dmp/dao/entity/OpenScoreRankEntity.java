package com.efida.sports.dmp.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "成绩排名model")
public class OpenScoreRankEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.id
     *
     * @mbggenerated
     */
    private Long       id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.unit_code
     *
     * @mbggenerated
     */
    private String     unitCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.score_rank_code
     *
     * @mbggenerated
     */
    private String     scoreRankCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.player_code
     *
     * @mbggenerated
     */
    private String     playerCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.player_phone
     *
     * @mbggenerated
     */
    private String     playerPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.player_name
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "运动员名称")
    private String     playerName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.part_code
     *
     * @mbggenerated
     */
    private String     partCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.part_time
     *
     * @mbggenerated
     */
    private Date       partTime;

    /**
     *项目编号
     */
    private String     gameCode;

    /**
     * 项目名称
     */
    private String     gameName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.match_code
     *
     * @mbggenerated
     */
    private String     matchCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.match_name
     *
     * @mbggenerated
     */
    private String     matchName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.field_code
     *
     * @mbggenerated
     */
    private String     fieldCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.field_name
     *
     * @mbggenerated
     */
    private String     fieldName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.match_group_code
     *
     * @mbggenerated
     */
    private String     matchGroupCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.match_group_name
     *
     * @mbggenerated
     */
    private String     matchGroupName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.event_code
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "比赛项目编号")
    private String     eventCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.event_name
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "比赛项目名称")
    private String     eventName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.score_name
     *
     * @mbggenerated
     */
    private String     scoreName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_score.score
    *
    * @mbggenerated
    */
    @ApiModelProperty(value = "成绩")
    private BigDecimal score;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.score_unit
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "成绩单位")
    private String     scoreUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.score_desc
     *
     * @mbggenerated
     */
    private String     scoreDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.ranking
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "排名")
    private Integer    ranking;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.promotion
     *
     * @mbggenerated
     */
    private String     promotion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.ext_prop
     *
     * @mbggenerated
     */
    private String     extProp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.channel_code
     *
     * @mbggenerated
     */
    private String     channelCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.idempotent_id
     *
     * @mbggenerated
     */
    private String     idempotentId;

    /**
     * 批次号
     */
    private String     batchNumber;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.source
    *
    * @mbggenerated
    */
    private Byte       source;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync
    *
    * @mbggenerated
    */
    private Byte       sync;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync_total
    *
    * @mbggenerated
    */
    private Integer    syncTotal;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.sync_status
    *
    * @mbggenerated
    */
    private String     syncStatus;

    /**
     * 赛事唯一编号
     */
    private String     competitionCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column open_apply_info.next_sync_time
    *
    * @mbggenerated
    */
    private Date       nextSyncTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.gmt_create
     *
     * @mbggenerated
     */
    private Date       gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column open_score_rank.gmt_modified
     *
     * @mbggenerated
     */
    private Date       gmtModified;

    /**
     * 获取参赛时间
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public String getPartTimeStr() {
        return DateUtil.format(this.getPartTime(), DateUtil.LONG_WEB_FORMAT);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.id
     *
     * @return the value of open_score_rank.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.id
     *
     * @param id the value for open_score_rank.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.unit_code
     *
     * @return the value of open_score_rank.unit_code
     *
     * @mbggenerated
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.unit_code
     *
     * @param unitCode the value for open_score_rank.unit_code
     *
     * @mbggenerated
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.score_rank_code
     *
     * @return the value of open_score_rank.score_rank_code
     *
     * @mbggenerated
     */
    public String getScoreRankCode() {
        return scoreRankCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.score_rank_code
     *
     * @param scoreRankCode the value for open_score_rank.score_rank_code
     *
     * @mbggenerated
     */
    public void setScoreRankCode(String scoreRankCode) {
        this.scoreRankCode = scoreRankCode == null ? null : scoreRankCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.player_code
     *
     * @return the value of open_score_rank.player_code
     *
     * @mbggenerated
     */
    public String getPlayerCode() {
        return playerCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.player_code
     *
     * @param playerCode the value for open_score_rank.player_code
     *
     * @mbggenerated
     */
    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode == null ? null : playerCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.player_phone
     *
     * @return the value of open_score_rank.player_phone
     *
     * @mbggenerated
     */
    public String getPlayerPhone() {
        return playerPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.player_phone
     *
     * @param playerPhone the value for open_score_rank.player_phone
     *
     * @mbggenerated
     */
    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone == null ? null : playerPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.player_name
     *
     * @return the value of open_score_rank.player_name
     *
     * @mbggenerated
     */
    public String getPlayerName() {
        return playerName;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.player_name
     *
     * @param playerName the value for open_score_rank.player_name
     *
     * @mbggenerated
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName == null ? null : playerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.part_code
     *
     * @return the value of open_score_rank.part_code
     *
     * @mbggenerated
     */
    public String getPartCode() {
        return partCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.part_code
     *
     * @param partCode the value for open_score_rank.part_code
     *
     * @mbggenerated
     */
    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.part_time
     *
     * @return the value of open_score_rank.part_time
     *
     * @mbggenerated
     */
    public Date getPartTime() {
        return partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.part_time
     *
     * @param partTime the value for open_score_rank.part_time
     *
     * @mbggenerated
     */
    public void setPartTime(Date partTime) {
        this.partTime = partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.match_code
     *
     * @return the value of open_score_rank.match_code
     *
     * @mbggenerated
     */
    public String getMatchCode() {
        return matchCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.match_code
     *
     * @param matchCode the value for open_score_rank.match_code
     *
     * @mbggenerated
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode == null ? null : matchCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.match_name
     *
     * @return the value of open_score_rank.match_name
     *
     * @mbggenerated
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.match_name
     *
     * @param matchName the value for open_score_rank.match_name
     *
     * @mbggenerated
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName == null ? null : matchName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.field_code
     *
     * @return the value of open_score_rank.field_code
     *
     * @mbggenerated
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.field_code
     *
     * @param fieldCode the value for open_score_rank.field_code
     *
     * @mbggenerated
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.field_name
     *
     * @return the value of open_score_rank.field_name
     *
     * @mbggenerated
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.field_name
     *
     * @param fieldName the value for open_score_rank.field_name
     *
     * @mbggenerated
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.match_group_code
     *
     * @return the value of open_score_rank.match_group_code
     *
     * @mbggenerated
     */
    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.match_group_code
     *
     * @param matchGroupCode the value for open_score_rank.match_group_code
     *
     * @mbggenerated
     */
    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode == null ? null : matchGroupCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.match_group_name
     *
     * @return the value of open_score_rank.match_group_name
     *
     * @mbggenerated
     */
    public String getMatchGroupName() {
        return matchGroupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.match_group_name
     *
     * @param matchGroupName the value for open_score_rank.match_group_name
     *
     * @mbggenerated
     */
    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName == null ? null : matchGroupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.event_code
     *
     * @return the value of open_score_rank.event_code
     *
     * @mbggenerated
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.event_code
     *
     * @param eventCode the value for open_score_rank.event_code
     *
     * @mbggenerated
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode == null ? null : eventCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.event_name
     *
     * @return the value of open_score_rank.event_name
     *
     * @mbggenerated
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.event_name
     *
     * @param eventName the value for open_score_rank.event_name
     *
     * @mbggenerated
     */
    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.score_name
     *
     * @return the value of open_score_rank.score_name
     *
     * @mbggenerated
     */
    public String getScoreName() {
        return scoreName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.score_name
     *
     * @param scoreName the value for open_score_rank.score_name
     *
     * @mbggenerated
     */
    public void setScoreName(String scoreName) {
        this.scoreName = scoreName == null ? null : scoreName.trim();
    }

    /**
     * Getter method for property <tt>score</tt>.
     * 
     * @return property value of score
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * Setter method for property <tt>score</tt>.
     * 
     * @param score value to be assigned to property score
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.score_unit
     *
     * @return the value of open_score_rank.score_unit
     *
     * @mbggenerated
     */
    public String getScoreUnit() {
        return scoreUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.score_unit
     *
     * @param scoreUnit the value for open_score_rank.score_unit
     *
     * @mbggenerated
     */
    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit == null ? null : scoreUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.score_desc
     *
     * @return the value of open_score_rank.score_desc
     *
     * @mbggenerated
     */
    public String getScoreDesc() {
        return scoreDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.score_desc
     *
     * @param scoreDesc the value for open_score_rank.score_desc
     *
     * @mbggenerated
     */
    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc == null ? null : scoreDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.ranking
     *
     * @return the value of open_score_rank.ranking
     *
     * @mbggenerated
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.ranking
     *
     * @param ranking the value for open_score_rank.ranking
     *
     * @mbggenerated
     */
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.promotion
     *
     * @return the value of open_score_rank.promotion
     *
     * @mbggenerated
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.promotion
     *
     * @param promotion the value for open_score_rank.promotion
     *
     * @mbggenerated
     */
    public void setPromotion(String promotion) {
        this.promotion = promotion == null ? null : promotion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.ext_prop
     *
     * @return the value of open_score_rank.ext_prop
     *
     * @mbggenerated
     */
    public String getExtProp() {
        return extProp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.ext_prop
     *
     * @param extProp the value for open_score_rank.ext_prop
     *
     * @mbggenerated
     */
    public void setExtProp(String extProp) {
        this.extProp = extProp == null ? null : extProp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.channel_code
     *
     * @return the value of open_score_rank.channel_code
     *
     * @mbggenerated
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.channel_code
     *
     * @param channelCode the value for open_score_rank.channel_code
     *
     * @mbggenerated
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.idempotent_id
     *
     * @return the value of open_score_rank.idempotent_id
     *
     * @mbggenerated
     */
    public String getIdempotentId() {
        return idempotentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.idempotent_id
     *
     * @param idempotentId the value for open_score_rank.idempotent_id
     *
     * @mbggenerated
     */
    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId == null ? null : idempotentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column open_score_rank.gmt_create
     *
     * @return the value of open_score_rank.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column open_score_rank.gmt_create
     *
     * @param gmtCreate the value for open_score_rank.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getGmtCreateStr() {
        return gmtCreate == null ? null : DateUtil.formatDate(gmtCreate);
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

}