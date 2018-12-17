package cn.evake.auth.dao.mapper;

import java.util.List;

import cn.evake.auth.dao.model.SysInfo;

public interface SysInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysInfo record);

    int insertSelective(SysInfo record);

    SysInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysInfo record);

    int updateByPrimaryKey(SysInfo record);

    SysInfo selectByName(String name);

    List<SysInfo> selectAll();

    List<SysInfo> selectSysIds(List<Long> sysIds);
}