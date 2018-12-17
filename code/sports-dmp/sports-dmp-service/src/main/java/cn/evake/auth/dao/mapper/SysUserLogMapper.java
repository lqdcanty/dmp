package cn.evake.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.auth.dao.model.SysUserLog;

public interface SysUserLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserLog record);

    int insertSelective(SysUserLog record);

    SysUserLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserLog record);

    int updateByPrimaryKey(SysUserLog record);

    List<SysUserLog> selectKeyWord(@Param(value = "keyword") String keyword);
}