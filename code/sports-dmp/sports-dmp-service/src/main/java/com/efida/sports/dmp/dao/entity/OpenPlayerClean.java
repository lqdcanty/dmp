package com.efida.sports.dmp.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author zoutao
 * @since 2018-09-12
 */
public class OpenPlayerClean implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 运动员编号
     */
    private String            playerCode;
    /**
     * 报名编号
     */
    private String            applyCode;
    /**
     * 运动员名称
     */
    private String            playerName;
    /**
     * 性别(M:男 F:女)
     */
    private String            sex;
    /**
     * 证件类型 1身份证。 2.护照 3.驾驶证,4:军官证
     */
    private String            playerCerType;
    /**
     * 证件号码
     */
    private String            playerCerNo;
    /**
     * 生日
     */
    private Date              playerBirth;
    /**
     * 年龄
     */
    private Integer           age;
    /**
     * 是否已成年 1:已成年 0 未成年(规则：乐雪杯智能滑雪联赛可以根据身份证信息区分；)
     */
    private Integer           isAdult;
    /**
     * 终端类型
     */
    private String            terminal;

    /**
     * 报名时间
     */
    private Date              applyTime;
    /**
     * 创建时间
     */
    private Date              gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerCode() {
        return playerCode;
    }

    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPlayerCerType() {
        return playerCerType;
    }

    public void setPlayerCerType(String playerCerType) {
        this.playerCerType = playerCerType;
    }

    public String getPlayerCerNo() {
        return playerCerNo;
    }

    public void setPlayerCerNo(String playerCerNo) {
        this.playerCerNo = playerCerNo;
    }

    public Date getPlayerBirth() {
        return playerBirth;
    }

    public void setPlayerBirth(Date playerBirth) {
        this.playerBirth = playerBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Integer isAdult) {
        this.isAdult = isAdult;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        return "OpenPlayerClean [id=" + id + ", playerCode=" + playerCode + ", applyCode="
               + applyCode + ", playerName=" + playerName + ", sex=" + sex + ", playerCerType="
               + playerCerType + ", playerCerNo=" + playerCerNo + ", playerBirth=" + playerBirth
               + ", age=" + age + ", isAdult=" + isAdult + ", terminal=" + terminal + ", applyTime="
               + applyTime + ", gmtCreate=" + gmtCreate + ", getClass()=" + getClass()
               + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
