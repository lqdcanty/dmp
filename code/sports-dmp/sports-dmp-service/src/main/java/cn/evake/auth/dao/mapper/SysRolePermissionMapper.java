package cn.evake.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.auth.dao.model.SysRolePermission;

public interface SysRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

    SysRolePermission selectByRoleIdAndMenuId(@Param(value = "roleId") Long roleId,
                                              @Param(value = "menuId") Long menuId);

    List<SysRolePermission> selectByRoleIds(List<Long> roleIds);

    List<SysRolePermission> selectByRoleId(@Param(value = "roleId") Long roleId);

    void insertBatch(List<SysRolePermission> datas);
}