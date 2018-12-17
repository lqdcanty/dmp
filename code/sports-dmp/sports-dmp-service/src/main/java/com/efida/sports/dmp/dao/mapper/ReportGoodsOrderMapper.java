package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.efida.sports.dmp.dao.entity.ReportGoodsOrder;

public interface ReportGoodsOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    int insert(ReportGoodsOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    int insertSelective(ReportGoodsOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    ReportGoodsOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReportGoodsOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_goods_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ReportGoodsOrder record);

    /**
     * 电子奖章领取人数
     * 
     * @return
     */
    List<Map<String, Object>> countMedalReceive();

    /**
     * 完赛人数统计
     * 
     * @return
     */
    List<Map<String, Object>> countFinishEnrollPerson();

    /**
     * 根据日期查询奖章统计
     * 
     * @param date
     * @return
     */
    ReportGoodsOrder queryReportGoodsByParams(@Param(value = "date") String date);

    /**
     * 根据排序获取报名开始日期 asc
     * 
     * @param sortParam
     * @return
     */
    String queryScoreStartTime();

    /**
     * 根据日期查询奖章统计数据
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    List<ReportGoodsOrder> queryGoodsOrderByParams(@Param(value = "startDate") String startDate,
                                                   @Param(value = "endDate") String endDate);

}