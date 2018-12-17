package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.efida.sports.dmp.dao.entity.OpenPlayerClean;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zoutao
 * @since 2018-09-12
 */
public interface OpenPlayerCleanMapper extends BaseMapper<OpenPlayerClean> {

    List<Map<String, Object>> getAdultStatistics();

    List<Map<String, Object>> getSexStatistics();

    List<Map<String, Object>> getTerminalStatistics();

}
