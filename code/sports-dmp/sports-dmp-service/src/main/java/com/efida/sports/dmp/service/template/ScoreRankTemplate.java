package com.efida.sports.dmp.service.template;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 成绩导入模板
 * @author wang yi
 * @desc 
 * @version $Id: ScoreExcelVo.java, v 0.1 2018年8月3日 下午8:00:45 wang yi Exp $
 */
public class ScoreRankTemplate {

    @ExcelAttribute(name = "唯一标识", column = "A")
    @NotEmpty(message = "唯一标不能为空")
    @Size(min = 0, max = 64, message = "唯一标识需在{min}和{max}个字符之间")
    private String idempotentId;

    @ExcelAttribute(name = "运动员名称", column = "D")
    @NotEmpty(message = "运动员名称不能为空")
    @Size(min = 0, max = 24, message = "运动员名称需在{min}和{max}个字符之间")
    private String playerName;

    @ExcelAttribute(name = "运动员电话", column = "C")
    @NotEmpty(message = "运动员电话不能为空")
    @Size(min = 0, max = 18, message = "运动员电话需在{min}和{max}个字符之间")
    private String playerPhone;

    @ExcelAttribute(name = "参赛编号", column = "E")
    @NotEmpty(message = "参赛编号不能为空")
    @Size(min = 0, max = 20, message = "参赛编号需在{min}和{max}个字符之间")
    private String partCode;

    @ExcelAttribute(name = "参赛时间", column = "F")
    //@Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[13579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "日期格式不正确，正确的格式类似于2018-08-03 15:24:00")
    @NotEmpty(message = "参赛时间不能为空")
    private String partTime;

    @ExcelAttribute(name = "运动员编号", column = "B")
    @NotEmpty(message = "运动员编号不能为空")
    @Size(min = 0, max = 20, message = "运动员编号需在{min}和{max}个字符之间")
    private String playerCode;

    @ExcelAttribute(name = "项目编号", column = "G")
    @NotEmpty(message = "项目编号不能为空")
    private String gameCode;

    @ExcelAttribute(name = "项目名称", column = "H")
    @NotEmpty(message = "项目名称不能为空")
    private String gameName;

    @ExcelAttribute(name = "赛事编号", column = "I")
    @NotEmpty(message = "赛事编号不能为空")
    private String matchCode;

    @ExcelAttribute(name = "赛事名称", column = "J")
    @NotEmpty(message = "赛事名称不能为空")
    private String matchName;

    @ExcelAttribute(name = "赛场编号", column = "K")
    @NotEmpty(message = "赛场编号不能为空")
    private String fieldCode;

    @ExcelAttribute(name = "赛场名称", column = "L")
    @NotEmpty(message = "赛场名称不能为空")
    private String fieldName;

    @ExcelAttribute(name = "组编号", column = "M")
    private String matchGroupCode;

    @ExcelAttribute(name = "组名称", column = "N")
    private String matchGroupName;

    @ExcelAttribute(name = "项目细类编号", column = "O")
    @NotEmpty(message = "项目细类编号不能为空")
    private String eventCode;

    @ExcelAttribute(name = "项目细类名称", column = "P")
    @NotEmpty(message = "项目细类名称不能为空")
    private String eventName;

    @ExcelAttribute(name = "成绩对应科目名", column = "Q")
    @NotEmpty(message = "成绩对应科目名不能为空")
    @Size(min = 0, max = 32, message = "成绩对应科目名需在{min}和{max}个字符之间")
    private String scoreName;

    @ExcelAttribute(name = "成绩数值", column = "R")
    @NotEmpty(message = "成绩数值不能为空")
    private String score;

    @ExcelAttribute(name = "成绩单位", column = "S")
    @NotEmpty(message = "成绩单位不能为空")
    private String scoreUnit;

    @ExcelAttribute(name = "成绩描述", column = "T")
    //@NotEmpty(message = "成绩描述不能为空")
    private String scoreDesc;

    @ExcelAttribute(name = "名次", column = "U")
    @NotEmpty(message = "名次不能为空")
    private String ranking;

    @ExcelAttribute(name = "晋级情况", column = "V")
    private String promotion;

    @ExcelAttribute(name = "扩展属性", column = "W")
    private String extProp;

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPhone() {
        return playerPhone;
    }

    public void setPlayerPhone(String playerPhone) {
        this.playerPhone = playerPhone;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
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

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPartTime() {
        return partTime;
    }

    public void setPartTime(String partTime) {
        this.partTime = partTime;
    }

}