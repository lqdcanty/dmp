package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wang yi
 * @since 2018-07-28
 */
public interface CompetitionMapper extends BaseMapper<CompetitionEntity> {

    List<CompetitionEntity> selectCompetitionByMatchCode(@Param(value = "matchCode") String matchCode);

    List<String> queryMatchCodes(Map<String, Object> queryParams);

    List<String> queryRegisterMatchCodes(Map<String, Object> queryParams);

    /**
     * start: 
     * limit:
     * 
     * @param map
     * @return
     */
    List<CompetitionEntity> queryNeedRankingCompetition(Map<String, Object> map);

    int updateLastRankingTime(String competitionCode);

    String selectNameByRankCode(@Param(value = "scoreRankCode") String scoreRankCode);

}
