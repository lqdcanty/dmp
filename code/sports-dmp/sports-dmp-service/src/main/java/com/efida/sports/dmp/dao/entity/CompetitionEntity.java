package com.efida.sports.dmp.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang yi
 * @since 2018-07-28
 */
@TableName("competition")
public class CompetitionEntity extends Model<CompetitionEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 比赛唯一编号
     */
    private String            competitionCode;
    /**
     * 比赛名称
     */
    private String            name;
    /**
     * 赛事编号
     */
    private String            matchCode;
    /**
     * 比赛分类 编号
     */
    private String            gameCode;
    /**
     * 比赛项编号（1,2）
     */
    private String            events;
    /**
     * 分组编号
     */
    private String            groups;
    /**
     * 赛场编号
     */
    private String            areas;
    /**
     * 排序
     */
    private Integer           sortIndex;

    /**
    * unit:合作伙伴排名 dmp:官方排名
    */
    private String            rankType;

    /**
     * 创建时间
     */
    private Date              gmtCreate;
    /**
     * 修改时间
     */
    private Date              gmtModified;

    private int               isDelete;

    private int               isShow;

    private String            creatorUid;
    private String            creatorName;
    private String            modifyUid;
    private String            modifyName;

    @TableField(exist = false)
    public Date               competitionDate;

    @TableField(exist = false)
    public MatchDetailModel   matchDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getGroups() {
        return groups;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
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

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Date getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(Date competitionDate) {
        this.competitionDate = competitionDate;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public MatchDetailModel getMatchDetail() {
        return matchDetail;
    }

    public void setMatchDetail(MatchDetailModel matchDetail) {
        this.matchDetail = matchDetail;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getModifyUid() {
        return modifyUid;
    }

    public void setModifyUid(String modifyUid) {
        this.modifyUid = modifyUid;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    @Override
    public String toString() {
        return "CompetitionEntity [id=" + id + ", competitionCode=" + competitionCode + ", name="
               + name + ", matchCode=" + matchCode + ", gameCode=" + gameCode + ", events=" + events
               + ", groups=" + groups + ", areas=" + areas + ", sortIndex=" + sortIndex
               + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", isDelete="
               + isDelete + ", isShow=" + isShow + ", creatorUid=" + creatorUid + ", creatorName="
               + creatorName + ", modifyUid=" + modifyUid + ", modifyName=" + modifyName
               + ", competitionDate=" + competitionDate + ", matchDetail=" + matchDetail
               + ", getId()=" + getId() + ", getCompetitionCode()=" + getCompetitionCode()
               + ", getName()=" + getName() + ", getMatchCode()=" + getMatchCode()
               + ", getEvents()=" + getEvents() + ", getGroups()=" + getGroups() + ", getAreas()="
               + getAreas() + ", getSortIndex()=" + getSortIndex() + ", getGmtCreate()="
               + getGmtCreate() + ", getGmtModified()=" + getGmtModified() + ", pkVal()=" + pkVal()
               + ", getCompetitionDate()=" + getCompetitionDate() + ", getIsDelete()="
               + getIsDelete() + ", getMatchDetail()=" + getMatchDetail() + ", getGameCode()="
               + getGameCode() + ", getIsShow()=" + getIsShow() + ", getCreatorUid()="
               + getCreatorUid() + ", getCreatorName()=" + getCreatorName() + ", getModifyUid()="
               + getModifyUid() + ", getModifyName()=" + getModifyName() + ", insert()=" + insert()
               + ", insertAllColumn()=" + insertAllColumn() + ", insertOrUpdate()="
               + insertOrUpdate() + ", deleteById()=" + deleteById() + ", updateById()="
               + updateById() + ", updateAllColumnById()=" + updateAllColumnById()
               + ", selectAll()=" + selectAll() + ", selectById()=" + selectById() + ", sql()="
               + sql() + ", sqlSession()=" + sqlSession() + ", getClass()=" + getClass()
               + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
