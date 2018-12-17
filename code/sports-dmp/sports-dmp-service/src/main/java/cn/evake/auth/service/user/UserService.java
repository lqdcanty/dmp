/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.evake.auth.constants.RoleConstants;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.dao.model.SysRole;
import cn.evake.auth.dao.model.SysRolePermission;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.dao.model.SysUserRole;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.SysRoleMenuExt;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * 用户服务提供用户操作
 * @author Evance
 * @version $Id: UserService.java, v 0.1 2018年2月25日 下午10:37:37 Evance Exp $
 */
public interface UserService {

    /**
     * 创建一个用户
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysUser
     * @param roles
     * @return
     */
    SysUser createUser(SysUser sysUser, Long[] roles);

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    UserInfoModel userLogin(String username, String password);

    /**
     * 获取一个用户
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    Boolean userLoginOut(HttpServletRequest request);

    /**
     * 检查用户是否登录
     * @param request
     * @return
     */
    Boolean checkIsLogin(HttpServletRequest request);

    /**
     * 检查用户是否放行
     * 检查权限优先级
     * 授权码-->访问路径
     * @param uuid 用户唯一标识
     * @param permissionCode 授权码
     * @param relpath 请求相对路径
     * @param userModel 用户model
     * @return
     */
    Boolean checkUserLimit(String uuid, String permissionCode, String relpath,
                           UserInfoModel userModel);

    /**
     * 检查用户是否授权
     * @param request 请求
     * @param permissionCode 授权码
     * @return
     */
    Boolean checkUserLimit(HttpServletRequest request, String permissionCode);

    /**
     * 添加一个角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysRole 角色Model
     * @return
     */
    SysRole addRole(SysRole sysRole);

    /**
     * 删除一个角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId 角色ID
     * @return
     */
    Boolean removeRoleById(Long roleId);

    /**
     * 角色权限添加
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param per 角色权限Model
     * @return
     */
    SysRolePermission addRolePermission(SysRolePermission per);

    /**
     * 角色权限删除
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param permissionId 
     * @return
     */
    Boolean removeRolePermission(Long permissionId);

    /**
     * 添加一个用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysUserRole 用户角色Model
     * @return
     */
    SysUserRole addSysUserRole(SysUserRole sysUserRole);

    /**
     * 删除一个用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userRoleId 用户角色ID
     * @return
     */
    Boolean removeSysUserRoleByid(Long userRoleId);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> getAllRole();

    /**
     * 通过角色ID获取所有角色权限
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId 角色ID
     * @return
     */
    List<SysRolePermission> getRolePermissionByRoleId(Long roleId);

    /**
     * 更新最后一次登录时间
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid 用户唯一标识
     */
    void updateLastLogin(String uuid);

    /**
     * 
     * 获取用户信息并检查用户
     * @param request 请求
     * @return
     */
    UserInfoModel getUserModelChecked(HttpServletRequest request);

    /**
     * 获取用户信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param request
     * @return
     */
    UserInfoModel getUserModelAndExpire(HttpServletRequest request);

    /**
     * 检查验证码是否相同
     * @param sessionCode 内存中的验证码
     * @param authCode 用户输入的验证码
     * @return
     */
    Boolean checkAuthCode(String sessionCode, String authCode);

    /**
     * 获取用户分页数据
     * 
     * @param keyword  支持 用户名 用户真实名 手机号 邮箱 等
     * @param pageNumber 页数
     * @param pageSize 每页数量
     * @return
     */
    PagingResult<SysUser> getAllUserInofs(String keyword, Integer pageNumber, Integer pageSize);

    /**
     * 通过获取用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid 用户唯一标识
     * @return
     */
    List<SysUserRole> getSysUserRoleByUuid(String uuid);

    /**
     * 创建一个用户
     * @param sysUser 用户信息
     * @return
     */
    SysUser addUser(SysUser sysUser);

    /**
     * 检查用户名是否可用
     * @param userName 用户姓名
     * @return
     * Ex: userName:zangshan  return false 表示该用户名不可用
     */
    Boolean checkUserNameIsEnable(String userName);

    /**
     * 更新用户信息
     * @param uuid 用户唯一标示
     * @param sysUser 用户信息
     * @return
     * Ex:该接口只会更新非空数据
     */
    SysUser updateUserByUUid(String uuid, SysUser sysUser);

    /**
     * 锁定/解锁用户
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param userName
     * @param status
     */
    void lockUserByUserName(String userName, boolean status);

    /**
     * 查询用户
     * @param uuid 用户唯一标识
     * @return
     */
    SysUser getUserByUuid(String uuid);

    /**
     * 更新用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param roles
     */
    void checkAndupUserRoleByUuid(String uuid, Long[] roles);

    /**
     * 查询角色拥有菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId 角色ID
     * @return
     */
    List<SysMenu> getSysMenuByRoleId(Long roleId);

    /**
     * 获取所有菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId 角色ID
     * @return
     */
    List<SysRoleMenuExt> getSysRoleMenuByRoleId(Long roleId);

    /**
     * 删除一个角色对应的权限
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId 角色ID
     * @param menuId 菜单ID
     * @return 是否删除成功
     */
    Boolean removeRolePermission(Long roleId, Long menuId);

    /**
     * 角色权限批量添加
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param datas 角色权限数据
     * @return 是否添加成功
     */
    Boolean addRolePermissions(List<SysRolePermission> datas);

    /**
     * 更新角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysRole 角色Model
     * @return 更新后的用户数据
     */
    SysRole updateRole(SysRole sysRole);

    /**
     * 
     * 查询某角色下的用户
     * @param keyword 支持 用户名 用户真实名 手机号 邮箱 等
     * @param roleIds 角色IDs
     * @param pageNumber 页数
     * @param size 每页数量
     * @return 分页后的用户数据
     */
    PagingResult<SysUser> getAllUserInofs(String keyword, List<Long> roleIds, Integer pageNumber,
                                          Integer size);

    /**
     * 查询用户角色
     * @param uuids 用户唯一标识
     * @return
     */
    List<SysUserRole> getSysUserRoleByUuids(List<String> uuids);

    /**
     * 查询用户角色带状态
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid 用户标识
     * @param status  用户状态 @see {@link RoleConstants}
     * @return
     */
    List<SysUserRole> getSysUserRoleByUuidWithStatus(String uuid, String status);

    /**
     * 修改用户密码
     * @param uuid 用户唯一标识
     * @param oldPass 用户旧的密码
     * @param newPass 用户新的密码
     */
    void updateUserPass(String uuid, String oldPass, String newPass);

    /**
     * 修改用户密码
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid 用户唯一标识
     * @param password 用户新的密码
     * @return
     */
    void updateUserPass(String uuid, String password);

    /**
     * 检查用户密码是否有效
     * @param uuid 用户唯一标识
     * @param pass 用户密码
     * @return
     */
    Boolean checkPassIsEnble(String uuid, String pass);

    /**
     * 断言个人信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysUser
     */
    void assertUser(SysUser sysUser);

    /**
     * 获取登录用户信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param authToken
     * @return
     */
    UserInfoModel getLoginUserInfo(String uuid);

    /**
     * 检查登录用户授权码是否可使用
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param permissionCode
     * @return
     */
    boolean checkUserLimit(String uuid, String permissionCode);

    /**
     *添加用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param roles
     */
    void addRolePermissions(String uuid, Long[] roles);

}
