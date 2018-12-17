package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.efida.sports.dmp.dao.entity.ReportUnitFinishEntity;

public interface ReportUnitFinishEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    int insert(ReportUnitFinishEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    int insertSelective(ReportUnitFinishEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    ReportUnitFinishEntity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReportUnitFinishEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_unit_finish
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ReportUnitFinishEntity record);

    /**
     * 根据unitCode 和 applyTimeDay查询承办方完赛成绩
     * 
     * @param map
     * @return
     */
    List<ReportUnitFinishEntity> queryUnitFinishByCode(Map<String, Object> map);

    /**
     * 获取承办方总完赛数
     * 
     * @return
     */
    Map<String, Object> countTotalUnitFinish(Map<String, Object> map);

    /**
     * 根据条件查询承办方完善人数
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> countUnitFinishByParams(Map<String, Object> map);

    /**
     * 根据unitCode获取承办方完赛人数
     * 
     * @param map
     * @return
     */
    Map<String, Object> officialUnitFinishCount(Map<String, Object> map);

    /**
     * 根据unitCode获取承办方完赛人数
     * 
     * @param unitCode
     * @return
     */
    List<Map<String, Object>> queryAllFinishOfficial(@Param(value = "unitCode") String unitCode);

}