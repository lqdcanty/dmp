package com.efida.sports.dmp.synch.data.jiubiao.dao.entity;

import java.util.Date;

/**
 * <p>
 * 预赛表
 * </p>
 *
 * @author wangyi
 * @since 2018-09-14
 */
public class ShotMatch {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String            id;
    /**
     * 微信openId
     */
    private String            wxopenId;
    /**
     * 微信openID
     */
    private String            userid;
    /**
     * 第一次总分
     */
    private String            score1;
    /**
     * 第二次总分
     */
    private String            score2;
    /**
     * 第三次总分
     */
    private String            score3;
    /**
     * 最佳成绩
     */
    private String            bestScore;
    /**
     * 城市名称
     */
    private String            cityName;
    /**
     * 比赛次数
     */
    private String            matchCount;
    /**
     * 昵称
     */
    private String            nickname;
    /**
     * 微信昵称
     */
    private String            avatarUrl;
    /**
     * 手机号码
     */
    private String            phone;
    /**
     * 状态（0正常 1删除 2停用）
     */
    private String            status;
    /**
     * 创建者
     */
    private String            createBy;
    /**
     * 创建时间
     */
    private Date              createDate;
    /**
     * 更新者
     */
    private String            updateBy;
    /**
     * 更新时间
     */
    private Date              updateDate;
    /**
     * 备注信息
     */
    private String            remarks;

    //拓展字段
    private String            gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxopenId() {
        return wxopenId;
    }

    public void setWxopenId(String wxopenId) {
        this.wxopenId = wxopenId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getBestScore() {
        return bestScore;
    }

    public void setBestScore(String bestScore) {
        this.bestScore = bestScore;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(String matchCount) {
        this.matchCount = matchCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ShotMatch{" + "id=" + id + ", wxopenId=" + wxopenId + ", userid=" + userid
               + ", score1=" + score1 + ", score2=" + score2 + ", score3=" + score3 + ", bestScore="
               + bestScore + ", cityName=" + cityName + ", matchCount=" + matchCount + ", nickname="
               + nickname + ", avatarUrl=" + avatarUrl + ", phone=" + phone + ", status=" + status
               + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy
               + ", updateDate=" + updateDate + ", remarks=" + remarks + "}";
    }
}
