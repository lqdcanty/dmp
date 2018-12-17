package com.efida.sports.dmp.service.template;

import java.util.Date;

import cn.evake.auth.util.DateUtil;
import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 报名信息模板
 * 
 * @author wang yi
 * @desc
 * @version $Id: ApplyInfoTeplate.java, v 0.1 2018年8月16日 下午5:11:24 wang yi Exp $
 */
public class ApplyInfoTeplate {

	@ExcelAttribute(name = "报名唯一标识", column = "A")
	private String idempotentId;

	@ExcelAttribute(name = "报名时间", column = "B")
	private String applyTime;

	@ExcelAttribute(name = "领队名称", column = "E")
	private String leaderName;

	@ExcelAttribute(name = "领队电话", column = "F")
	private String leaderPhone;

	@ExcelAttribute(name = "团队名称", column = "D")
	private String teamName;

	@ExcelAttribute(name = "比赛报名人数", column = "C")
	private Integer registrationNum;

	private Long applyAmount;

	@ExcelAttribute(name = "报名费用(元)", column = "G")
	private Long applyAmountT;

	private String applyStatus;

	private String unitCode;

	@ExcelAttribute(name = "项目编号", column = "H")
	private String gameCode;

	@ExcelAttribute(name = "项目名称", column = "I")
	private String gameName;

	@ExcelAttribute(name = "赛事编号", column = "J")
	private String matchCode;

	@ExcelAttribute(name = "赛事名称", column = "K")
	private String matchName;

	@ExcelAttribute(name = "赛场编号", column = "L")
	private String siteCode;

	@ExcelAttribute(name = "赛场名称", column = "M")
	private String siteName;

	@ExcelAttribute(name = "赛事分组名称", column = "N")
	private String matchGroupName;

	@ExcelAttribute(name = "赛事分组编号", column = "O")
	private String matchGroupCode;

	@ExcelAttribute(name = "赛事分组编号", column = "P")
	private String eventCode;

	@ExcelAttribute(name = "赛事分组编号", column = "Q")
	private String eventName;

	private Date eventStartTime;

	private Date eventEndTime;

	@ExcelAttribute(name = "比赛项开始时间", column = "R")
	private String eventStartTimeStr;

	@ExcelAttribute(name = "比赛项结束时间", column = "S")
	private String eventEndTimeStr;

	private Date expireTime;

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public Long getApplyAmountT() {
		if (this.applyAmount != null && this.applyAmount > 0) {
			return this.applyAmount / 100;
		}
		return this.applyAmountT;
	}

	public void setApplyAmountT(Long applyAmountT) {
		this.applyAmountT = applyAmountT;
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

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getIdempotentId() {
		return idempotentId;
	}

	public void setIdempotentId(String idempotentId) {
		this.idempotentId = idempotentId;
	}

	public String getEventStartTimeStr() {
		if (eventStartTimeStr != null) {
			return eventStartTimeStr;
		}
		return DateUtil.formatDate(getEventStartTime());
	}

	public void setEventStartTimeStr(String eventStartTimeStr) {
		this.eventStartTimeStr = eventStartTimeStr;
	}

	public String getEventEndTimeStr() {
		if (eventEndTimeStr != null) {
			return eventEndTimeStr;
		}
		return DateUtil.formatDate(getEventEndTime());
	}

	public void setEventEndTimeStr(String eventEndTimeStr) {
		this.eventEndTimeStr = eventEndTimeStr;
	}

}