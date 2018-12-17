package cn.evake.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.auth.dao.model.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    SysMenu selectByMenuNameAndSysId(@Param(value = "sysId") Long sysId,
                                     @Param(value = "menuName") String menuName);

    List<SysMenu> selectByIds(List<Long> menuIds);

    List<SysMenu> selectAll();

    List<SysMenu> selectBySysIds(List<Long> sysIds);

    List<SysMenu> selectMenuByRoleId(@Param(value = "roleId") Long roleId);

    List<SysMenu> selectByIdsWithStatus(@Param(value = "menuIds") List<Long> menuIds,
                                        @Param(value = "status") Boolean status);
}