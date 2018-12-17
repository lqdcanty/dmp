package com.efida.sports.dmp.dao.entity;

import java.util.Date;

public class ReportMatchFinishEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.match_code
     *
     * @mbggenerated
     */
    private String  matchCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.match_name
     *
     * @mbggenerated
     */
    private String  matchName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.unit_code
     *
     * @mbggenerated
     */
    private String  unitCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.unit_name
     *
     * @mbggenerated
     */
    private String  unitName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column report_match_finish.finish_total_count
    *
    * @mbggenerated
    */
    private Integer finishTotalCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.organizer_count
     *
     * @mbggenerated
     */
    private Integer organizerCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.finish_count
     *
     * @mbggenerated
     */
    private Integer finishCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.create_date
     *
     * @mbggenerated
     */
    private Date    partTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.create_date_day
     *
     * @mbggenerated
     */
    private Date    partTimeDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.gmt_create
     *
     * @mbggenerated
     */
    private Date    gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_match_finish.gmt_modified
     *
     * @mbggenerated
     */
    private Date    gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.id
     *
     * @return the value of report_match_finish.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.id
     *
     * @param id the value for report_match_finish.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.match_code
     *
     * @return the value of report_match_finish.match_code
     *
     * @mbggenerated
     */
    public String getMatchCode() {
        return matchCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.match_code
     *
     * @param matchCode the value for report_match_finish.match_code
     *
     * @mbggenerated
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode == null ? null : matchCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.match_name
     *
     * @return the value of report_match_finish.match_name
     *
     * @mbggenerated
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.match_name
     *
     * @param matchName the value for report_match_finish.match_name
     *
     * @mbggenerated
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName == null ? null : matchName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.unit_code
     *
     * @return the value of report_match_finish.unit_code
     *
     * @mbggenerated
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.unit_code
     *
     * @param unitCode the value for report_match_finish.unit_code
     *
     * @mbggenerated
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.unit_name
     *
     * @return the value of report_match_finish.unit_name
     *
     * @mbggenerated
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.unit_name
     *
     * @param unitName the value for report_match_finish.unit_name
     *
     * @mbggenerated
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.organizer_count
     *
     * @return the value of report_match_finish.organizer_count
     *
     * @mbggenerated
     */
    public Integer getOrganizerCount() {
        return organizerCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.organizer_count
     *
     * @param organizerCount the value for report_match_finish.organizer_count
     *
     * @mbggenerated
     */
    public void setOrganizerCount(Integer organizerCount) {
        this.organizerCount = organizerCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.finish_count
     *
     * @return the value of report_match_finish.finish_count
     *
     * @mbggenerated
     */
    public Integer getFinishCount() {
        return finishCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.finish_count
     *
     * @param finishCount the value for report_match_finish.finish_count
     *
     * @mbggenerated
     */
    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.part_time
     *
     * @return the value of report_match_finish.part_time
     *
     * @mbggenerated
     */
    public Date getPartTime() {
        return partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.part_time
     *
     * @param createDate the value for report_match_finish.part_time
     *
     * @mbggenerated
     */
    public void setPartTime(Date partTime) {
        this.partTime = partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.part_time_day
     *
     * @return the value of report_match_finish.part_time_day
     *
     * @mbggenerated
     */
    public Date getPartTimeDay() {
        return partTimeDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.part_time_day
     *
     * @param createDateDay the value for report_match_finish.part_time_day
     *
     * @mbggenerated
     */
    public void setPartTimeDay(Date partTimeDay) {
        this.partTimeDay = partTimeDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.gmt_create
     *
     * @return the value of report_match_finish.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.gmt_create
     *
     * @param gmtCreate the value for report_match_finish.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_match_finish.gmt_modified
     *
     * @return the value of report_match_finish.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_match_finish.gmt_modified
     *
     * @param gmtModified the value for report_match_finish.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getFinishTotalCount() {
        return finishTotalCount;
    }

    public void setFinishTotalCount(Integer finishTotalCount) {
        this.finishTotalCount = finishTotalCount;
    }

}