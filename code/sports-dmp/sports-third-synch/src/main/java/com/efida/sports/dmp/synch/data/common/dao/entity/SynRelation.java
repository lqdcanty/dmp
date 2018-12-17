package com.efida.sports.dmp.synch.data.common.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyi
 * @since 2018-09-10
 */
public class SynRelation {

    private static final long serialVersionUID = 1L;

    private Long              id;
    /**
     * 承办方编号
     */
    private String            unitCode;
    /**
     * 承办方名称
     */
    private String            unitName;
    /**
     * 项目编号
     */
    private String            gameCode;
    /**
     * 项目名称
     */
    private String            gameName;
    /**
     * 承办方活动id
     */
    private String            reMatchId;
    /**
     * 赛事编号
     */
    private String            matchCode;
    /**
     * 赛事名称
     */
    private String            matchName;
    /**
     * 对应承办方分赛场id
     */
    private String            reFileId;
    /**
     * 赛场编号
     */
    private String            fileCode;
    /**
     * 赛场名称
     */
    private String            fileName;
    /**
     * 合作方分组id
     */
    private String            reGroupId;
    /**
     * 分组编号
     */
    private String            groupCode;
    /**
     * 分组名称
     */
    private String            groupName;
    /**
     * 对应赛事项id
     */
    private String            reEventId;
    /**
     * 赛事项编号
     */
    private String            eventCode;
    /**
     * 赛事项名称
     */
    private String            eventName;

    /**
     * 分赛场
     */
    private String            site;

    /**
     * 赛事组
     */
    private String            group;

    /**
     * 赛事项
     */
    private String            event;

    /**
     * 是否开启同步
     */
    private Integer           isSyn;
    /**
     * 同步是否完成
     */
    private Integer           synOk;
    /**
     * 创建时间
     */
    private Date              gmtCreate;
    /**
     * 修改时间
     */
    private Date              gmtModify;

    /**
     * 赛事项信息
     */
    private List<EventModel>  eventModes;

    /**
     * 分组信息
     */
    private List<GroupModel>  groupModels;

    /**
     * 赛场信息
     */
    private List<SiteModel>   siteModels;

    /**
     * 获取比赛项
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public List<EventModel> getEventModes() {
        List<EventModel> events = new ArrayList<EventModel>();
        if (StringUtils.isEmpty(event)) {
            return events;
        }
        try {
            events = JSON.parseArray(event, EventModel.class);
        } catch (Exception e) {
            System.err.println(e);
        }
        return events;
    }

    /**
     * 获取分组信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public List<GroupModel> getGroupModes() {
        List<GroupModel> groupModels = new ArrayList<GroupModel>();
        if (StringUtils.isEmpty(group)) {
            return groupModels;
        }
        try {
            groupModels = JSON.parseArray(group, GroupModel.class);
        } catch (Exception e) {
            System.err.println(e);
        }
        return groupModels;
    }

    /**
     * 获取分赛场
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @return
     */
    public List<SiteModel> getSiteModes() {
        List<SiteModel> sitesModels = new ArrayList<SiteModel>();
        if (StringUtils.isEmpty(site)) {
            return sitesModels;
        }
        try {
            sitesModels = JSON.parseArray(site, SiteModel.class);
        } catch (Exception e) {
            System.err.println(e);
        }
        return sitesModels;
    }

    public void setEventModes(List<EventModel> eventModes) {
        this.eventModes = eventModes;
    }

    public void setGroupModels(List<GroupModel> groupModels) {
        this.groupModels = groupModels;
    }

    public void setSiteModels(List<SiteModel> siteModels) {
        this.siteModels = siteModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getReMatchId() {
        return reMatchId;
    }

    public void setReMatchId(String reMatchId) {
        this.reMatchId = reMatchId;
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

    public String getReFileId() {
        return reFileId;
    }

    public void setReFileId(String reFileId) {
        this.reFileId = reFileId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReGroupId() {
        return reGroupId;
    }

    public void setReGroupId(String reGroupId) {
        this.reGroupId = reGroupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getReEventId() {
        return reEventId;
    }

    public void setReEventId(String reEventId) {
        this.reEventId = reEventId;
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

    public Integer getIsSyn() {
        return isSyn;
    }

    public void setIsSyn(Integer isSyn) {
        this.isSyn = isSyn;
    }

    public Integer getSynOk() {
        return synOk;
    }

    public void setSynOk(Integer synOk) {
        this.synOk = synOk;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public String toString() {
        return "SynRelation{" + "id=" + id + ", unitCode=" + unitCode + ", unitName=" + unitName
               + ", gameCode=" + gameCode + ", gameName=" + gameName + ", reMatchId=" + reMatchId
               + ", matchCode=" + matchCode + ", matchName=" + matchName + ", reFileId=" + reFileId
               + ", fileCode=" + fileCode + ", fileName=" + fileName + ", reGroupId=" + reGroupId
               + ", groupCode=" + groupCode + ", groupName=" + groupName + ", reEventId="
               + reEventId + ", eventCode=" + eventCode + ", eventName=" + eventName + ", isSyn="
               + isSyn + ", synOk=" + synOk + ", gmtCreate=" + gmtCreate + ", gmtModify="
               + gmtModify + "}";
    }
}
