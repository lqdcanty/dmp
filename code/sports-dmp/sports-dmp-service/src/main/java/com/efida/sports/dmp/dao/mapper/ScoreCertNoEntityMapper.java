package com.efida.sports.dmp.dao.mapper;

import com.efida.sports.dmp.dao.entity.ScoreCertNoEntity;

public interface ScoreCertNoEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    int insert(ScoreCertNoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    int insertSelective(ScoreCertNoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    ScoreCertNoEntity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ScoreCertNoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table score_cert_no
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ScoreCertNoEntity record);

    /**
     * 通过certSn查询证书占用情况
     * 
     * @param certSn
     * @return
     */
    ScoreCertNoEntity queryByCertSn(String certSn);
}