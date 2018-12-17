package cn.evake.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.auth.dao.model.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    SysUserRole selectByUuidAndRoleId(@Param(value = "uuid") String uuid,
                                      @Param(value = "roleId") Long roleId);

    List<SysUserRole> selectSysRoleByUuid(@Param(value = "uuid") String uuid);

    List<SysUserRole> selectAll();

    List<SysUserRole> selectSysRoleByUuids(@Param(value = "uuids") List<String> uuids);

    void deleteWithRoleIdAndUuid(@Param("roleId") Long roleId, @Param("uuid") String uuid);

    void deleteWithUuid(@Param("uuid") String uuid);

    List<SysUserRole> selectSysRoleByUuidWithStatus(@Param(value = "uuid") String uuid,
                                                    @Param(value = "status") String status);
}