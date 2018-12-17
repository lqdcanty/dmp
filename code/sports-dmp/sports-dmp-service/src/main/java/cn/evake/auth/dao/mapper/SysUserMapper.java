package cn.evake.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.auth.dao.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByName(String username);

    SysUser selectUuid(@Param(value = "uuid") String uuid);

    List<SysUser> selectUserByKeyword(@Param(value = "keyword") String keyword,
                                      @Param(value = "countOffset") Integer countOffset,
                                      @Param(value = "pageSize") Integer pageSize);

    int countUserByKeyword(@Param(value = "keyword") String keyword);

    List<SysUser> selectUserByRolesAndKeyword(@Param(value = "keyword") String keyword,
                                              @Param(value = "roleIds") List<Long> roleIds,
                                              @Param(value = "countOffset") Integer countOffset,
                                              @Param(value = "pageSize") Integer pageSize);

    int countUserByRolesAndKeyword(@Param(value = "keyword") String keyword,
                                   @Param(value = "roleIds") List<Long> roleIds);
}