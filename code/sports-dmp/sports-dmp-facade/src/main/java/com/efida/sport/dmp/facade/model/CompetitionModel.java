/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sport.dmp.facade.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author zoutao
 * @version $Id: competition.java, v 0.1 2018年7月28日 上午10:53:21 zoutao Exp $
 */
public class CompetitionModel implements Serializable {

    /**  */
    private static final long             serialVersionUID = 1L;
    /**
     * 比赛编号
     */
    private String                        competitionCode;
    /**
     * 比赛名称
     */
    private String                        competitionName;
    /**
     * 排序字段
     */
    private int                           indexSort;
    /**
     * 比赛时间
     */
    private Date                          competitionDate;

    /**
     * 比赛项编号（1,2）
     */
    private String                        events;
    /**
    * 关联比赛项名称
    */
    private List<ComelationRelationModel> relationEvents;

    /**
     * 分组编号
     */
    private String                        groups;

    /**
    * 关联分组信息
    */
    private List<ComelationRelationModel> relationGroups;
    /**
     * 赛场编号
     */
    private String                        areas;

    /**
    * 关联赛场信息
    */
    private List<ComelationRelationModel> relationAreas;

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public int getIndexSort() {
        return indexSort;
    }

    public void setIndexSort(int indexSort) {
        this.indexSort = indexSort;
    }

    public Date getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(Date competitionDate) {
        this.competitionDate = competitionDate;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public List<ComelationRelationModel> getRelationEvents() {
        return relationEvents;
    }

    public void setRelationEvents(List<ComelationRelationModel> relationEvents) {
        this.relationEvents = relationEvents;
    }

    public List<ComelationRelationModel> getRelationGroups() {
        return relationGroups;
    }

    public void setRelationGroups(List<ComelationRelationModel> relationGroups) {
        this.relationGroups = relationGroups;
    }

    public List<ComelationRelationModel> getRelationAreas() {
        return relationAreas;
    }

    public void setRelationAreas(List<ComelationRelationModel> relationAreas) {
        this.relationAreas = relationAreas;
    }

}
