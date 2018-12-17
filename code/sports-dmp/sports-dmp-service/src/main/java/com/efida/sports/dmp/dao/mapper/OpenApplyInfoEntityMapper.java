package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;

public interface OpenApplyInfoEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    int insert(OpenApplyInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    int insertSelective(OpenApplyInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    OpenApplyInfoEntity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OpenApplyInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_apply_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OpenApplyInfoEntity record);

    /**
     * 通过idempotentId 查询 报名信息
     * 
     *  String unitCode
     *  String idempotentId
     *  
     * @return
     */
    OpenApplyInfoEntity selectByIdempotentId(Map<String, Object> map);

    /**
     *  根据报名编号查询报名信息
     * 
     * @param applyCode
     * @return
     */
    OpenApplyInfoEntity getApplyInfoByApplyCode(String applyCode);

    /**
     * 
     * 
     * @param map
     * @return
     */
    List<OpenApplyInfoEntity> selectUnSyncRecord(Map<String, Object> map);

    /**
     * 
     * 
     * @param map
     * @return
     */
    List<OpenApplyInfoEntity> selectOpenApplyByProp(Map<String, Object> map);

    /**
    * 
    * 
    * @param map
    * @return
    */
    List<OpenApplyInfoEntity> selectOpenApplyByPropHavePlayerPhone(Map<String, Object> map);

    /**
     * 
     * 
     * @return
     */
    List<String> queryUnitEnrollDate();

    /**
     * 承办方报名人数统计
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> queryUnitEnrollCount(Map<String, Object> map);

    /**
     * 赛事报名人数统计
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> queryMatchEnrollCount(Map<String, Object> map);

    /**
     *  统计某一个赛场报名次数
     * 
     * @param fieldCode
     * @return
     */
    long getCountByFieldCode(String fieldCode);

    /**
     * 根据applyCode查询报名信息
     * 
     * @param applyCodes
     * @return
     */
    List<OpenApplyInfoEntity> selectApplyInfosByCodes(List<String> applyCodes);

}