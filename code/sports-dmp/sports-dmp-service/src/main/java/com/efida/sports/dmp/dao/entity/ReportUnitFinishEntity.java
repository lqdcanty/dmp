package com.efida.sports.dmp.dao.entity;

import java.util.Date;

public class ReportUnitFinishEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.id
     *
     * @mbggenerated
     */
    private Long    id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.unit_code
     *
     * @mbggenerated
     */
    private String  unitCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.unit_name
     *
     * @mbggenerated
     */
    private String  unitName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.finish_cont
     *
     * @mbggenerated
     */
    private Integer finishCont;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.create_date
     *
     * @mbggenerated
     */
    private Date    partTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.create_date_day
     *
     * @mbggenerated
     */
    private Date    partTimeDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.gmt_create
     *
     * @mbggenerated
     */
    private Date    gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column report_unit_finish.gmt_modified
     *
     * @mbggenerated
     */
    private Date    gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.id
     *
     * @return the value of report_unit_finish.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.id
     *
     * @param id the value for report_unit_finish.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.unit_code
     *
     * @return the value of report_unit_finish.unit_code
     *
     * @mbggenerated
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.unit_code
     *
     * @param unitCode the value for report_unit_finish.unit_code
     *
     * @mbggenerated
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.unit_name
     *
     * @return the value of report_unit_finish.unit_name
     *
     * @mbggenerated
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.unit_name
     *
     * @param unitName the value for report_unit_finish.unit_name
     *
     * @mbggenerated
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.finish_cont
     *
     * @return the value of report_unit_finish.finish_cont
     *
     * @mbggenerated
     */
    public Integer getFinishCont() {
        return finishCont;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.finish_cont
     *
     * @param finishCont the value for report_unit_finish.finish_cont
     *
     * @mbggenerated
     */
    public void setFinishCont(Integer finishCont) {
        this.finishCont = finishCont;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.part_time
     *
     * @return the value of report_unit_finish.part_time
     *
     * @mbggenerated
     */
    public Date getPartTime() {
        return partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.part_time
     *
     * @param createDate the value for report_unit_finish.part_time
     *
     * @mbggenerated
     */
    public void setPartTime(Date partTime) {
        this.partTime = partTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.part_time_day
     *
     * @return the value of report_unit_finish.part_time_day
     *
     * @mbggenerated
     */
    public Date getPartTimeDay() {
        return partTimeDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.part_time_day
     *
     * @param createDateDay the value for report_unit_finish.part_time_day
     *
     * @mbggenerated
     */
    public void setPartTimeDay(Date partTimeDay) {
        this.partTimeDay = partTimeDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.gmt_create
     *
     * @return the value of report_unit_finish.gmt_create
     *
     * @mbggenerated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.gmt_create
     *
     * @param gmtCreate the value for report_unit_finish.gmt_create
     *
     * @mbggenerated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_unit_finish.gmt_modified
     *
     * @return the value of report_unit_finish.gmt_modified
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_unit_finish.gmt_modified
     *
     * @param gmtModified the value for report_unit_finish.gmt_modified
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}