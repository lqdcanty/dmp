package com.efida.sports.dmp.dao.entity;

import java.util.Date;

public class ReportUnitEnrollEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.id
     *
     * @mbggenerated
     */
    private Long    id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.unit_code
     *
     * @mbggenerated
     */
    private String  unitCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.unit_name
     *
     * @mbggenerated
     */
    private String  unitName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.enroll_count
     *
     * @mbggenerated
     */
    private Integer enrollCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.enroll_total_count
     *
     * @mbggenerated
     */
    private Integer enrollTotalCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.create_date
     *
     * @mbggenerated
     */
    private Date    applyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.create_date_day
     *
     * @mbggenerated
     */
    private Date    applyTimeDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.gmt_create
     *
     * @mbggenerated
     */
    private Date    gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_enroll.gmt_modified
     *
     * @mbggenerated
     */
    private Date    gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.id
     *
     * @return the value of report_unit_enroll.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.id
     *
     * @param id the value for report_unit_enroll.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.unit_code
     *
     * @return the value of report_unit_enroll.unit_code
     *
     * @mbggenerated
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.unit_code
     *
     * @param unitCode the value for report_unit_enroll.unit_code
     *
     * @mbggenerated
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.unit_name
     *
     * @return the value of report_unit_enroll.unit_name
     *
     * @mbggenerated
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.unit_name
     *
     * @param unitName the value for report_unit_enroll.unit_name
     *
     * @mbggenerated
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.enroll_count
     *
     * @return the value of report_unit_enroll.enroll_count
     *
     * @mbggenerated
     */
    public Integer getEnrollCount() {
        return enrollCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.enroll_count
     *
     * @param enrollCount the value for report_unit_enroll.enroll_count
     *
     * @mbggenerated
     */
    public void setEnrollCount(Integer enrollCount) {
        this.enrollCount = enrollCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.enroll_total_count
     *
     * @return the value of report_unit_enroll.enroll_total_count
     *
     * @mbggenerated
     */
    public Integer getEnrollTotalCount() {
        return enrollTotalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.enroll_total_count
     *
     * @param enrollTotalCount the value for report_unit_enroll.enroll_total_count
     *
     * @mbggenerated
     */
    public void setEnrollTotalCount(Integer enrollTotalCount) {
        this.enrollTotalCount = enrollTotalCount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getApplyTimeDay() {
        return applyTimeDay;
    }

    public void setApplyTimeDay(Date applyTimeDay) {
        this.applyTimeDay = applyTimeDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.gmt_create
     *
     * @return the value of report_unit_enroll.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.gmt_create
     *
     * @param gmtCreate the value for report_unit_enroll.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_enroll.gmt_modified
     *
     * @return the value of report_unit_enroll.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_enroll.gmt_modified
     *
     * @param gmtModified the value for report_unit_enroll.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}