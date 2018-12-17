package com.efida.sports.dmp.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang yi
 * @since 2018-07-28
 */
@TableName("score_configer")
public class ScoreConfigerEntity extends Model<ScoreConfigerEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 成绩配置唯一编号
     */
    private String            scoreCode;
    /**
     * 比赛唯一编号
     */
    private String            competitionCode;
    /**
     * 配置显示项
     */
    private String            configer;
    /**
     * 创建时间
     */
    private Date              gmtCreate;
    /**
     * 修改时间
     */
    private Date              gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getConfiger() {
        return configer;
    }

    public void setConfiger(String configer) {
        this.configer = configer;
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

    @Override
    public String toString() {
        return "ScoreConfiger{" + ", id=" + id + ", scoreCode=" + scoreCode + ", competitionCode="
               + competitionCode + ", configer=" + configer + ", gmtCreate=" + gmtCreate
               + ", gmtModified=" + gmtModified + "}";
    }
}
