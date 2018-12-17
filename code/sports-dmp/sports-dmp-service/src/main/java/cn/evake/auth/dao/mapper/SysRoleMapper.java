package cn.evake.auth.dao.mapper;

import java.util.List;

import cn.evake.auth.dao.model.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole selectByRoleName(String roleName);

    List<SysRole> selectAll();

}