/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zoutao
 * @version $Id: RankModel.java, v 0.1 2018年7月28日 上午11:12:26 zoutao Exp $
 */
public class RankModel implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    /**
     * 运动员名称
     */
    private String            playerName;
    /**
     * 比赛编号
     */
    private String            competitionCode;

    /**
     * 排名编号
     */
    private String            rankCode;

    /**
    * 运动员编号
    */
    private String            playerCode;
    /**
     * 比赛时间
     */
    private Date              competitionDate;
    /**
     * 排名
     */
    private int               rank;
    /**
     * 运动员头像  可能为空
     */
    private String            playerHeaderImg;
    /**
     * 显示成绩
     */
    private String            score;
    /**
     * 
     * 赛事名称
     * @return
     */
    private String            matchName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPlayerHeaderImg() {
        return playerHeaderImg;
    }

    public void setPlayerHeaderImg(String playerHeaderImg) {
        this.playerHeaderImg = playerHeaderImg;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public Date getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(Date competitionDate) {
        this.competitionDate = competitionDate;
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

}
