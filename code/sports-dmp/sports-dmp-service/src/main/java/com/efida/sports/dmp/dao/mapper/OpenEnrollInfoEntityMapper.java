package com.efida.sports.dmp.dao.mapper;

import java.util.List;
import java.util.Map;

import com.efida.sports.dmp.dao.entity.OpenEnrollInfoEntity;

public interface OpenEnrollInfoEntityMapper {

    /**
    /**
     *  String unitCode
     *  String idempotentId
     *  
     * @return
     */
    OpenEnrollInfoEntity selectByIdempotentId(Map<String, Object> map);

    /**
     * map.put("unitCode", qs.getUnitCode());
       map.put("playerCode", qs.getPlayerCode());
       map.put("matchCode", qs.getMatchCode());
       map.put("fieldCode", qs.getFieldCode());
       map.put("matchGroupCode", qs.getMatchGroupCode());
       map.put("eventCode", qs.getEventCode());
       map.put("start", start);
       map.put("limit", qs.getPageSize());
       
    * 
    * @param map
    * @return
    */
    List<OpenEnrollInfoEntity> selectByProp(Map<String, Object> map);

}