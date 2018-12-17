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
public class ScoreTemplate {

    @NotEmpty(message = "成绩唯一标不能为空")
    @Size(min = 0, max = 32, message = "成绩唯一标识需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "A", name = "成绩唯一标")
    private String idempotentId;

    @NotEmpty(message = "运动员名称不能为空")
    @Size(min = 0, max = 24, message = "运动员名称需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "C", name = "运动员编号")
    private String playerName;

    @NotEmpty(message = "运动员电话不能为空")
    @Size(min = 0, max = 18, message = "运动员电话需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "D", name = "运动员编号")
    private String playerPhone;

    private String unitCode;

    @NotEmpty(message = "运动员编号不能为空")
    @Size(min = 0, max = 20, message = "运动员电话需在{min}和{max}个字符之间")
    @ExcelAttribute(column = "B", name = "运动员编号")
    private String playerCode;

    @ExcelAttribute(column = "E", name = "运动员性别")
    @NotEmpty(message = "运动员性别不能为空")
    private String sex;

    @ExcelAttribute(column = "F", name = "平台类型")
    private String openPlatType;

    @ExcelAttribute(column = "G", name = "三方平台openId")
    private String openId;

    @ExcelAttribute(column = "H", name = "项目编号")
    @NotEmpty(message = "项目编号不能为空")
    private String gameCode;

    @ExcelAttribute(column = "I", name = "项目名称")
    @NotEmpty(message = "项目名称不能为空")
    private String gameName;

    @ExcelAttribute(column = "J", name = "赛事编号")
    @NotEmpty(message = "赛事编号不能为空")
    private String matchCode;

    @ExcelAttribute(column = "K", name = "赛事名称")
    @NotEmpty(message = "赛事名称不能为空")
    private String matchName;

    @ExcelAttribute(column = "L", name = "赛场编号")
    @NotEmpty(message = "赛场编号不能为空")
    private String fieldCode;

    @ExcelAttribute(column = "M", name = "赛场名称")
    @NotEmpty(message = "赛场名称不能为空")
    private String fieldName;

    @ExcelAttribute(column = "N", name = "分组编号")
    private String matchGroupCode;

    @ExcelAttribute(column = "O", name = "分组名称")
    private String matchGroupName;

    @ExcelAttribute(column = "P", name = "项目细类")
    @NotEmpty(message = "项目细类不能为空")
    private String eventCode;

    @ExcelAttribute(column = "Q", name = "项目细类名称")
    @NotEmpty(message = "项目细类名称不能为空")
    private String eventName;

    @ExcelAttribute(column = "R", name = "参赛时间")
    @NotEmpty(message = "参赛时间不能为空")
    private String partTime;

    @ExcelAttribute(column = "S", name = "成绩对应科目名")
    @NotEmpty(message = "成绩对应科目名不能为空")
    @Size(min = 0, max = 32, message = "成绩对应科目名需在{min}和{max}个字符之间")
    private String scoreName;

    @ExcelAttribute(column = "T", name = "成绩数值")
    @NotEmpty(message = "成绩数值不能为空")
    private String score;

    @ExcelAttribute(column = "U", name = "成绩单位")
    @NotEmpty(message = "成绩单位不能为空")
    private String scoreUnit;

    @ExcelAttribute(column = "W", name = "成绩描述")
    //@NotEmpty(message = "成绩描述不能为空")
    private String scoreDesc;

    @ExcelAttribute(column = "V", name = "成绩属性类型")
    @NotEmpty(message = "成绩属性类型不能为空")
    private String scoreType;

    @ExcelAttribute(column = "X", name = "成绩相关属性")
    private String scoreProp;

    @ExcelAttribute(column = "Y", name = "运动员相关属性")
    private String playerProp;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getPartTime() {
        return partTime;
    }

    public void setPartTime(String partTime) {
        this.partTime = partTime;
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

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreProp() {
        return scoreProp;
    }

    public void setScoreProp(String scoreProp) {
        this.scoreProp = scoreProp;
    }

    public String getPlayerProp() {
        return playerProp;
    }

    public void setPlayerProp(String playerProp) {
        this.playerProp = playerProp;
    }

    public String getIdempotentId() {
        return idempotentId;
    }

    public void setIdempotentId(String idempotentId) {
        this.idempotentId = idempotentId;
    }

}