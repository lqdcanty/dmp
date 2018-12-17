package com.efida.sports.dmp.biz.score;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.ReportUnitEnrollEntity;

import cn.evake.auth.usermodel.PagingResult;

/**
 * 承办方报名人数
 * 
 * @author zengbo
 * @version $Id: ReportUnitEnrollService.java, v 0.1 2018年8月29日 下午10:59:04 zengbo Exp $
 */
public interface ReportUnitEnrollService {

    /**
     * 添加承办方报名报名人数
     * 
     * @param record
     * @return
     */
    int insert(ReportUnitEnrollEntity record);

    /**
     * 添加承办方报名报名人数
     * 
     * @param record
     * @return
     */
    int insertSelective(ReportUnitEnrollEntity record);

    /**
     * 根据ID查询承办方数据
     * 
     * @param id
     * @return
     */
    ReportUnitEnrollEntity selectByPrimaryKey(Long id);

    /**
     * 修改承办方数据
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ReportUnitEnrollEntity record);

    /**
     * 修改承办方数据
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(ReportUnitEnrollEntity record);

    /**
     * 保存多个承办数据
     * 
     * @param list
     */
    void saveReportUnitList(List<ReportUnitEnrollEntity> list);

    /**
     * 根据承办方unitCode统计报名人数
     * 
     * @param unitCode
     * @param applyTimeDay
     * @return
     */
    int countUnitEnrollByCode(String unitCode, String applyTimeDay);

    /**
     * 根据条件获取承办方报名总人次
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    int countTotalUnitEnroll(String startTime, String endTime);

    /**
     * 分组获取承办方报名人数
     * 
     * @return
     */
    List<Map<String, Object>> countUnitEnrollGroup();

    /**
     * 根据条件查询承办方报名总数据
     * 
     * @param startTime 
     * @param endTime
     * @param likeParams
     * @param inParams
     * @param type @ParamsTypeEnum 根据类型来判断是取中间点还是取详细数据
     * @return
     */
    List<Map<String, Object>> countToealUnitEnrollByParams(String startTime, String endTime,
                                                           String likeParams, String inParams);

    /**
     * 根据条件查询承办方报名数据
     * 
     * @param startTime 
     * @param endTime
     * @param likeParams
     * @param inParams
     * @param type @ParamsTypeEnum 根据类型来判断是取中间点还是取详细数据
     * @return
     */
    List<Map<String, Object>> countUnitEnrollByParams(String startTime, String endTime,
                                                      String likeParams, String inParams);

    /**
     * 分页查询承办方报名数据详情
     * 
     * @param likeParams
     * @param inParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PagingResult<ReportUnitEnrollEntity> queryUnitEnrollByPage(String startTime, String endTime,
                                                               String likeParams, String inParams,
                                                               int pageNumber, int pageSize);

    /**
     * 分页查询承办方报名数据详情
     * 
     * @param likeParams
     * @param inParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PagingResult<ReportUnitEnrollEntity> queryUnitEnrollByPageNew(String date, String inParams,
                                                                  int pageNumber, int pageSize);

    /**
     * 查询承办方报名导出数据
     * 
     * @param likeParams
     * @param inParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ReportUnitEnrollEntity> queryUnitEnrollByExport(String startTime, String endTime,
                                                         String likeParams, String inParams);

    /**
     * 查询承办方报名导出数据
     * 
     * @param date
     * @param inParams
     * @return
     */
    List<ReportUnitEnrollEntity> queryUnitEnrollByExportNew(String date, String inParams);

    /**
     * 模糊查询承办方名称
     * 
     * @param unitName
     * @return
     */
    List<String> queryLikeUnitName(String unitName);

    /**
     * 获取承办方TOP5的数据
     * 
     * @return
     */
    List<String> queryTop5UnitEnroll(String type);

    /**
     * 根据排序获取报名c
     * 
     * @param sortParam （开始日期 asc 结束日期  des）
     * @return
     */
    String queryUnitEnrollTime(String sortParam);

    /**
     * 需求变更新增接口
     * 根据条件查询承办方报名数据
     * 
     * @param date
     * @param inParams
     * @return
     */
    List<Map<String, Object>> countUnitEnrollByParamsNew(String date, String inParams);

    /**
     * 根据unitCode获取承办方报名人数
     * 
     * @param unitCode
     * @return
     */
    List<Map<String, Object>> queryAllUnitEnroll(String unitCode);

    /**
     * 总报名人数 按电话号码和名称去重复
     * 
     * @return
     */
    int getTotalApplyPeople();

    /**
     * 总完赛人数  按电话号码和名称去重
     * 
     * @return
     */
    int getTotalFinishPeople();

    /**
     * 根据条件查询承办方报名总数据
     * 
     * @param startTime 
     * @param endTime
     * @param likeParams
     * @param inParams
     * @param type @ParamsTypeEnum 根据类型来判断是取中间点还是取详细数据
     * @return
     */
    List<Map<String, Object>> countToealUnitEnrollByParams(String likeParams, String inParams);

}
