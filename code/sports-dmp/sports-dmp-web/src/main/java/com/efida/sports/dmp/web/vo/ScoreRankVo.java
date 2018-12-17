package com.efida.sports.dmp.web.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;
import com.efida.sports.dmp.enums.RankTypeEnum;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "成绩排名信息")
public class ScoreRankVo {

    @ApiModelProperty(value = "承办方编号")
    private String     unitCode;

    @ApiModelProperty(value = "成绩名次编号")
    private String     scoreRankCode;

    @ApiModelProperty(value = "运动员编号")
    private String     playerCode;

    @ApiModelProperty(value = "运动员手机号")
    private String     playerPhone;

    @ApiModelProperty(value = "运动员名称")
    private String     playerName;

    @ApiModelProperty(value = "参赛编号")
    private String     partCode;

    @ApiModelProperty(value = "参赛时间")
    private String     partTime;

    @ApiModelProperty(value = "赛事编号")
    private String     matchCode;

    @ApiModelProperty(value = "赛事名称")
    private String     matchName;

    @ApiModelProperty(value = "赛场编号")
    private String     fieldCode;

    @ApiModelProperty(value = "赛场名称")
    private String     fieldName;

    @ApiModelProperty(value = "组编号")
    private String     matchGroupCode;

    @ApiModelProperty(value = "组名称")
    private String     matchGroupName;

    @ApiModelProperty(value = "项编号")
    private String     eventCode;

    @ApiModelProperty(value = "项名称")
    private String     eventName;

    @ApiModelProperty(value = "成绩对应科目名")
    private String     scoreName;

    @ApiModelProperty(value = "参赛成绩")
    private BigDecimal score;

    @ApiModelProperty(value = "成绩单位")
    private String     scoreUnit;

    @ApiModelProperty(value = "成绩描述")
    private String     scoreDesc;

    @ApiModelProperty(value = "名次")
    private Integer    ranking;

    @ApiModelProperty(value = "批次号")
    private String     batchNumber;

    @ApiModelProperty(value = "晋级情况")
    private String     promotion;

    @ApiModelProperty(value = "排名方式")
    private String     rankType;

    @ApiModelProperty(value = "其他属性(JSON数据)")
    private String     extProp;

    @ApiModelProperty(value = "其他属性(JSON数据)")
    private String     channelCode;

    @ApiModelProperty(value = "排名单唯一标识")
    private String     idempotentId;

    @ApiModelProperty(value = "创建时间")
    private String     gmtCreateStr;

    public static List<ScoreRankVo> coverToVos(List<OpenScoreRankEntity> list) {
        List<ScoreRankVo> results = new ArrayList<ScoreRankVo>();
        for (OpenScoreRankEntity openScoreEntity : list) {
            results.add(coverToVo(openScoreEntity));
        }
        return results;
    }

    public static ScoreRankVo coverToVo(OpenScoreRankEntity scoreRank) {
        if (scoreRank == null) {
            return null;
        }
        ScoreRankVo scoreRankVo = new ScoreRankVo();
        scoreRankVo.setChannelCode(scoreRank.getChannelCode());
        scoreRankVo.setEventCode(scoreRank.getEventCode());
        scoreRankVo.setEventName(scoreRank.getEventName());
        scoreRankVo.setExtProp(scoreRank.getExtProp());
        scoreRankVo.setFieldCode(scoreRank.getFieldCode());
        scoreRankVo.setFieldName(scoreRank.getFieldName());
        scoreRankVo.setGmtCreateStr(DateUtil.formatDate(scoreRank.getGmtCreate()));
        scoreRankVo.setIdempotentId(scoreRank.getIdempotentId());
        scoreRankVo.setMatchCode(scoreRank.getMatchCode());
        scoreRankVo.setMatchGroupCode(scoreRank.getMatchGroupCode());
        scoreRankVo.setMatchGroupName(scoreRank.getMatchGroupName());
        scoreRankVo.setMatchName(scoreRank.getMatchName());
        scoreRankVo.setPartCode(scoreRank.getPartCode());
        scoreRankVo.setPartTime(DateUtil.formatDate(scoreRank.getPartTime()));
        scoreRankVo.setPlayerCode(scoreRank.getPlayerCode());
        scoreRankVo.setPlayerName(scoreRank.getPlayerName());
        scoreRankVo.setPlayerPhone(scoreRank.getPlayerPhone());
        scoreRankVo.setPromotion(scoreRank.getPromotion());
        scoreRankVo.setRanking(scoreRank.getRanking());
        scoreRankVo.setScore(scoreRank.getScore());
        scoreRankVo.setScoreDesc(scoreRank.getScoreDesc());
        scoreRankVo.setScoreName(scoreRank.getScoreName());
        scoreRankVo.setScoreRankCode(scoreRank.getScoreRankCode());
        scoreRankVo.setScoreUnit(scoreRank.getScoreUnit());
        scoreRankVo.setUnitCode(scoreRank.getUnitCode());
        scoreRankVo.setBatchNumber(scoreRank.getBatchNumber());
        if (StringUtils.isBlank(scoreRank.getCompetitionCode())) {
            scoreRankVo.setRankType(RankTypeEnum.unit.getCode());
        } else {
            scoreRankVo.setRankType(RankTypeEnum.dmp.getCode());
        }

        return scoreRankVo;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getScoreRankCode() {
        return scoreRankCode;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public void setScoreRankCode(String scoreRankCode) {
        this.scoreRankCode = scoreRankCode;
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

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
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

    public String getMatchGroupCode() {
        return matchGroupCode;
    }

    public void setMatchGroupCode(String matchGroupCode) {
        this.matchGroupCode = matchGroupCode;
    }

    public String getMatchGroupName() {
        return matchGroupName;
    }

    public void setMatchGroupName(String matchGroupName) {
        this.matchGroupName = matchGroupName;
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

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public String getScoreDesc() {
        return scoreDesc;
    }

    public void setScoreDesc(String scoreDesc) {
        this.scoreDesc = scoreDesc;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getExtProp() {
        return extProp;
    }

    public void setExtProp(String extProp) {
        this.extProp = extProp;
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

    public String getGmtCreateStr() {
        return gmtCreateStr;
    }

    public void setGmtCreateStr(String gmtCreateStr) {
        this.gmtCreateStr = gmtCreateStr;
    }

    public String getPartTime() {
        return partTime;
    }

    public void setPartTime(String partTime) {
        this.partTime = partTime;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

}