/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.vo;

import java.util.List;

import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zoutao
 * @version $Id: CompetitionVo.java, v 0.1 2018年8月2日 下午6:26:04 zoutao Exp $
 */
public class CompetitionVo {

    @ApiModelProperty(value = "比赛编号")
    private String                     competitionCode;
    @ApiModelProperty(value = "比赛名称")
    private String                     name;
    @ApiModelProperty(value = "赛事编号")
    private String                     matchCode;
    @ApiModelProperty(value = "赛事名称")
    private String                     matchName;
    @ApiModelProperty(value = "修改人昵称")
    private String                     modifyName;
    @ApiModelProperty(value = "修改人唯一编号")
    private String                     modifyUid;
    @ApiModelProperty(value = "创建人昵称")
    private String                     creator;
    @ApiModelProperty(value = "排名方式(unit:合作伙伴排名 dmp:官方排名)")
    private String                     rankType;
    @ApiModelProperty(value = "创建人唯一编号")
    private String                     creatorUid;
    @ApiModelProperty(value = "比赛项目")
    private List<ComelationRelationVo> events;
    @ApiModelProperty(value = "比赛项名称")
    private String                     eventName;
    @ApiModelProperty(value = "比赛分组名称")
    private String                     groupName;
    @ApiModelProperty(value = "是否显示")
    private Integer                    isShow;
    @ApiModelProperty(value = "赛场名称")
    private String                     siteName;
    @ApiModelProperty(value = "比赛 分组")
    private List<ComelationRelationVo> groups;
    @ApiModelProperty(value = "赛场")
    private List<ComelationRelationVo> sites;

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public List<ComelationRelationVo> getEvents() {
        return events;
    }

    public void setEvents(List<ComelationRelationVo> events) {
        this.events = events;
    }

    public List<ComelationRelationVo> getGroups() {
        return groups;
    }

    public void setGroups(List<ComelationRelationVo> groups) {
        this.groups = groups;
    }

    public List<ComelationRelationVo> getSites() {
        return sites;
    }

    public void setSites(List<ComelationRelationVo> sites) {
        this.sites = sites;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getModifyUid() {
        return modifyUid;
    }

    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public static CompetitionVo getVo(CompetitionEntity entity, MatchDetailModel matchModel) {
        String events = null;
        String groups = null;
        String areas = null;
        CompetitionVo vo = new CompetitionVo();
        if (entity != null) {
            vo.setCompetitionCode(entity.getCompetitionCode());
            vo.setMatchCode(entity.getMatchCode());
            vo.setName(entity.getName());
            vo.setIsShow(entity.getIsShow());
            vo.setRankType(entity.getRankType());
            events = entity.getEvents();
            groups = entity.getGroups();
            areas = entity.getAreas();
            vo.setCreator(entity.getCreatorName());
            vo.setModifyName(entity.getModifyName());
            vo.setCreatorUid(entity.getCreatorUid());
            vo.setModifyUid(entity.getModifyUid());
        }
        if (matchModel != null) {
            vo.setMatchName(matchModel.getMatchName());
            List<EventTypeModel> eventList = matchModel.getEventTypeList();
            if (eventList != null && eventList.size() > 0) {
                List<ComelationRelationVo> vos = ComelationRelationVo.getVosByEvents(events,
                    eventList);
                vo.setEventName(eventList.get(0).getEventName());
                vo.setEvents(vos);
            }
            List<FieldTypeModel> fieldList = matchModel.getFieldTypeList();
            if (fieldList != null && fieldList.size() > 0) {
                List<ComelationRelationVo> vos = ComelationRelationVo.getVosBySites(areas,
                    fieldList);
                vo.setSiteName(fieldList.get(0).getFieldName());
                vo.setSites(vos);
            }
            List<GroupTypeModel> groupTypeList = matchModel.getGroupTypeList();
            if (groupTypeList != null && groupTypeList.size() > 0) {
                List<ComelationRelationVo> vos = ComelationRelationVo.getVosByGroups(groups,
                    groupTypeList);
                vo.setGroupName(groupTypeList.get(0).getGroupName());
                vo.setGroups(vos);
            }
        }
        return vo;
    }

}
