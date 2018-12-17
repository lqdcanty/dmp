/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.evake.auth.annotation.LogActionTypeEnum;
import cn.evake.auth.annotation.LoggerAction;
import cn.evake.auth.constants.RoleConstants;
import cn.evake.auth.constants.UserConstants;
import cn.evake.auth.dao.mapper.SysMenuMapper;
import cn.evake.auth.dao.mapper.SysRoleMapper;
import cn.evake.auth.dao.mapper.SysRolePermissionMapper;
import cn.evake.auth.dao.mapper.SysUserMapper;
import cn.evake.auth.dao.mapper.SysUserRoleMapper;
import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.dao.model.SysRole;
import cn.evake.auth.dao.model.SysRolePermission;
import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.dao.model.SysUserRole;
import cn.evake.auth.exception.AuthBusException;
import cn.evake.auth.service.common.CacheService;
import cn.evake.auth.service.system.SysService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.RoleMenuExt;
import cn.evake.auth.usermodel.SysRoleMenuExt;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.SecretUtil;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.user.UserInfoUtil;

/**
 * 用户service
 * @author Evance
 * @version $Id: UserServiceImpl.java, v 0.1 2018年2月25日 下午10:38:47 Evance Exp $
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger                  logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMapper           userMapper;

    @Autowired
    private SysRoleMapper           roleMapper;

    @Autowired
    private SysMenuMapper           menuMapper;

    @Autowired
    private SysService              sysService;

    @Autowired
    private SysRolePermissionMapper rolePermissMapper;

    @Autowired
    private SysUserRoleMapper       userRoleMapper;

    @Autowired
    private CacheService            cacheService;

    @Override
    public UserInfoModel userLogin(String username, String password) {
        UserInfoModel userInfoModel = new UserInfoModel();
        //查询用户
        SysUser user = getUserByUserName(username);
        if (user == null) {
            throw new AuthBusException("用户不存在");
        }
        if (!SecretUtil.getMD5(password).equals(user.getPassword())) {
            throw new AuthBusException("用户密码错误");
        }
        if (StringUtils.isNotBlank(user.getStatus()) && user.getStatus().equalsIgnoreCase("0")) {
            throw new AuthBusException("账户已锁定");
        }
        //用户验证成功
        BeanUtils.copyProperties(user, userInfoModel);
        //查询用户角色
        List<SysUserRole> roles = getSysUserRoleByUuidWithStatus(user.getUuid(),
            RoleConstants.NORMAL);
        //设置用户角色信息
        userInfoModel.setUserRoles(roles);
        //查询角色(可使用的)
        List<SysRolePermission> sysRPer = rolePermissMapper
            .selectByRoleIds(UserInfoModel.getRoleIds(roles));
        userInfoModel.setUserPermisssions(sysRPer);
        //查询系统目录(可使用的)
        List<SysMenu> sysMenus = sysService
            .getSysMenuByMenuIdsWithStatus(UserInfoModel.getMenuIds(sysRPer), true);
        userInfoModel.setSysMenu(sysMenus);
        //查询系统
        userInfoModel.setSys(sysService.getSysBySysId(UserInfoModel.getSysIds(sysMenus)));
        userInfoModel.setAuthToken(UserInfoUtil.generateAutoken(userInfoModel.getUuid()));
        return userInfoModel;
    }

    @Override
    public List<SysUserRole> getSysUserRoleByUuidWithStatus(String uuid, String status) {
        return userRoleMapper.selectSysRoleByUuidWithStatus(uuid, status);
    }

    @Override
    public List<SysUserRole> getSysUserRoleByUuid(String uuid) {
        return userRoleMapper.selectSysRoleByUuid(uuid);
    }

    @Override
    public SysRole addRole(SysRole sysRole) {
        if (sysRole == null) {
            throw new AuthBusException("角色不能为空");
        }
        SysRole sysR = roleMapper.selectByRoleName(sysRole.getRoleName());
        if (sysR != null) {
            throw new AuthBusException("角色已存在");
        }
        roleMapper.insert(sysRole);
        return sysRole;
    }

    @Override
    public Boolean removeRoleById(Long roleId) {
        if (roleId == null) {
            throw new AuthBusException("角色ID不能为空");
        }
        SysRole sysR = roleMapper.selectByPrimaryKey(roleId);
        if (sysR == null) {
            throw new AuthBusException("角色不已存在");
        }
        roleMapper.deleteByPrimaryKey(roleId);
        return true;
    }

    @Override
    public SysRolePermission addRolePermission(SysRolePermission per) {
        //一个角色配置一个权限
        if (per == null) {
            throw new AuthBusException("角色权限不能为空");
        }
        SysRolePermission perp = rolePermissMapper.selectByRoleIdAndMenuId(per.getRoleId(),
            per.getMenuId());
        if (perp != null) {
            throw new AuthBusException("角色权限已存在");
        }
        rolePermissMapper.insert(per);
        return per;
    }

    @Override
    public Boolean removeRolePermission(Long permissionId) {
        SysRolePermission sysRole = rolePermissMapper.selectByPrimaryKey(permissionId);
        if (sysRole == null) {
            throw new AuthBusException("角色权限不存在");
        }
        rolePermissMapper.deleteByPrimaryKey(permissionId);
        return true;
    }

    @Override
    public SysUserRole addSysUserRole(SysUserRole sysUserRole) {
        SysUserRole resule = userRoleMapper.selectByUuidAndRoleId(sysUserRole.getUuid(),
            sysUserRole.getRoleId());
        if (resule != null) {
            throw new AuthBusException("用户存在该角色");
        }
        userRoleMapper.insert(sysUserRole);
        return sysUserRole;
    }

    @Override
    public Boolean removeSysUserRoleByid(Long userRoleId) {
        //
        SysUserRole sysRole = userRoleMapper.selectByPrimaryKey(userRoleId);
        if (sysRole == null) {
            throw new AuthBusException("用戶角色权限不存在");
        }
        userRoleMapper.deleteByPrimaryKey(userRoleId);
        return true;
    }

    @Override
    public List<SysRole> getAllRole() {
        return roleMapper.selectAll();
    }

    @Override
    public List<SysRolePermission> getRolePermissionByRoleId(Long roleId) {
        return rolePermissMapper.selectByRoleId(roleId);
    }

    @Override
    public void updateLastLogin(String uuid) {
        SysUser user = getUserByUuid(uuid);
        if (user != null) {
            SysUser userInfoModel = new SysUser();
            userInfoModel.setId(user.getId());
            userInfoModel.setLastLoginTime(new Date());
            //更新最后一次登录时间
            userMapper.updateByPrimaryKeySelective(userInfoModel);
        } else {
            logger.error("update user last login fail ! user info ：{}", JSON.toJSONString(user));
        }
    }

    @Override
    public UserInfoModel getUserModelAndExpire(HttpServletRequest request) {
        String userkey = UserInfoUtil.getCacheUserKey(request);
        UserInfoModel usermodel = (UserInfoModel) cacheService.getObj(userkey);
        if (userkey == null || usermodel == null) {
            return null;
        }
        //续期用户存留时间
        cacheService.expire(userkey, UserConstants.REDIS_TIMEOUT);
        return usermodel;
    }

    @Override
    public UserInfoModel getUserModelChecked(HttpServletRequest request) {
        UserInfoModel userModel = getUserModelAndExpire(request);
        String userkey = UserInfoUtil.getCacheUserKey(request);
        //授权码
        String authToken = UserInfoUtil.getAuthTokenKey(request);
        if (StringUtils.isBlank(authToken)) {
            throw new AuthBusException("非法访问");
        }
        if (StringUtils.isBlank(userkey)) {
            throw new AuthBusException("登录信息已过期");
        }
        if (userModel == null) {
            throw new AuthBusException("用户未登录");
        }
        if (!userModel.getAuthToken().equals(authToken)) {
            throw new AuthBusException("用户:" + userModel.getUserName() + "在其他设备登陆");
        }
        return userModel;
    }

    @Override
    @LoggerAction(actionType = LogActionTypeEnum.LOGINOUT, description = "用户退出系统")
    public Boolean userLoginOut(HttpServletRequest request) {
        try {
            UserInfoModel userInfoModel = getUserModelAndExpire(request);
            if (userInfoModel != null && userInfoModel.getAuthToken() != null) {
                cacheService.remove(UserInfoUtil.getCacheUserKey(request));
            }
            logger.info("用户退出登陆....用户信息:{}", JSON.toJSONString(userInfoModel));
        } catch (Exception e) {
            logger.error("", e);
        }
        return true;
    }

    @Override
    public Boolean checkAuthCode(String sessionCode, String authCode) {
        if (StringUtils.isBlank(sessionCode)) {
            throw new AuthBusException("系统异常");
        }
        if (StringUtils.isBlank(authCode)) {
            throw new AuthBusException("验证码不能为空");
        }
        //超级验证码
        if (authCode.equals("8888")) {
            return true;
        }
        return StringUtils.equalsIgnoreCase(sessionCode, authCode);
    }

    @Override
    public PagingResult<SysUser> getAllUserInofs(String keyword, Integer pageNumber,
                                                 Integer pageSize) {
        List<SysUser> list = userMapper.selectUserByKeyword(keyword,
            PagingResult.countOffset(pageNumber, pageSize), pageSize);
        int total = userMapper.countUserByKeyword(keyword);
        return new PagingResult<SysUser>(list, total, pageNumber, pageSize);
    }

    @Override
    public Boolean checkUserLimit(String uuid, String permissionCode, String relpath,
                                  UserInfoModel userModel) {
        boolean isPermit = false;
        //续期用户存留时间
        cacheService.expire(UserInfoUtil.generateCacheUserKey(uuid), UserConstants.REDIS_TIMEOUT);
        List<SysMenu> menus = userModel.getSysMenu();
        //校验头参数
        if (StringUtils.isBlank(uuid) || userModel == null || CollectionUtils.isEmpty(menus)) {
            isPermit = false;
        }
        //校验该角色是否用于此方法的权限
        if (StringUtils.isNotBlank(permissionCode)) {
            SysMenu menu = UserInfoModel.getMenuByPermissionCode(menus, permissionCode);
            if (menu != null) {
                isPermit = true;
                return isPermit;
            }
        }
        //校验该角色是否具有该URl权限
        SysMenu menu = UserInfoModel.getMenuByMenuUrl(menus, relpath);
        if (menu != null) {
            isPermit = true;
            return isPermit;
        }
        return isPermit;
    }

    @Override
    public Boolean checkUserLimit(HttpServletRequest request, String permissionCode) {
        UserInfoModel userModel = getUserModelAndExpire(request);
        if (userModel == null) {
            return false;
        }
        String requestURI = ServletUtil.getRequestURIWithOutContextPath(request);
        return checkUserLimit(userModel.getUuid(), permissionCode, requestURI, userModel);
    }

    @Override
    public Boolean checkIsLogin(HttpServletRequest request) {
        UserInfoModel userModel = getUserModelAndExpire(request);
        if (userModel == null) {
            return false;
        }
        return true;
    }

    @Override
    public SysUser addUser(SysUser sysUser) {
        AssertUser(sysUser);
        Boolean stauts = checkUserNameIsEnable(sysUser.getUserName());
        if (stauts == false) {
            throw new AuthBusException("用户名已被占用");
        }
        userMapper.insert(sysUser);
        return sysUser;
    }

    /**
     * 断言User
     * @param sysUser
     */
    private void AssertUser(SysUser sysUser) {
        if (sysUser == null) {
            throw new AuthBusException("用户不能为空");
        }
        if (sysUser.getUuid() == null) {
            throw new AuthBusException("用户uuid不能为空");
        }
        if (StringUtils.isBlank(sysUser.getUserName())) {
            throw new AuthBusException("用户登录名不能为空");
        }
        if (StringUtils.isBlank(sysUser.getPassword())) {
            throw new AuthBusException("用户密码不能为空");
        }
    }

    /**
     * 获取一个用户
     * @param userName 用户名
     * @return
     */
    @Override
    public SysUser getUserByUserName(String userName) {
        return userMapper.selectByName(userName);
    }

    @Override
    public Boolean checkUserNameIsEnable(String userName) {
        SysUser result = getUserByUserName(userName);
        if (result == null) {
            return true;
        }
        return false;
    }

    @Override
    public SysUser updateUserByUUid(String uuid, SysUser sysUser) {
        SysUser userInfo = getUserByUuid(uuid);
        if (userInfo == null) {
            throw new AuthBusException("用户不存在");
        }
        sysUser.setId(userInfo.getId());
        userMapper.updateByPrimaryKeySelective(sysUser);
        return getUserByUuid(uuid);
    }

    @Override
    public SysUser getUserByUuid(String uuid) {
        return userMapper.selectUuid(uuid);
    }

    @Transactional
    @Override
    public SysUser createUser(SysUser sysUser, Long[] roles) {
        SysUser user = addUser(sysUser);
        if (roles == null) {
            throw new AuthBusException("用户角色不能为空");
        }
        for (Long roleid : roles) {
            SysRole roleInfo = getRoleByRoleId(roleid);
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleid);
            sysUserRole.setRoleName(roleInfo.getRoleName());
            sysUserRole.setUuid(user.getUuid());
            sysUserRole.setGmtCreate(new Date());
            addSysUserRole(sysUserRole);
        }
        return user;
    }

    public SysRole getRoleByRoleId(Long roleid) {
        return roleMapper.selectByPrimaryKey(roleid);
    }

    @Transactional
    @Override
    public void checkAndupUserRoleByUuid(String uuid, Long[] roles) {
        removeSysUserRole(uuid);
        addRolePermissions(uuid, roles);
    }

    /**
     * 添加用户角色
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param uuid
     * @param roles
     */
    @Override
    public void addRolePermissions(String uuid, Long[] roles) {
        //更新角色
        for (Long newRoleId : roles) {
            SysRole roleInfo = getRoleByRoleId(newRoleId);
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(newRoleId);
            userRole.setRoleName(roleInfo.getRoleName());
            userRole.setUuid(uuid);
            userRole.setGmtCreate(new Date());
            addSysUserRole(userRole);
        }
    }

    /**
     * 删除一个用户的所有角色
     * @param uuid 用户唯一标识
     */
    public void removeSysUserRole(String uuid) {
        userRoleMapper.deleteWithUuid(uuid);
    }

    /**
     * 
     * 删除一个用户的角色
     * @param roleId 角色编号
     * @param uuid 用户唯一标识
     */
    public void removeSysUserRole(Long roleId, String uuid) {
        userRoleMapper.deleteWithRoleIdAndUuid(roleId, uuid);
    }

    @Override
    public List<SysMenu> getSysMenuByRoleId(Long roleId) {
        List<SysMenu> menus = getMenuByRoleid(roleId);
        return menus;
    }

    /**
     * 查询菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param roleId
     * @return
     */
    public List<SysMenu> getMenuByRoleid(Long roleId) {
        List<SysMenu> menus = menuMapper.selectMenuByRoleId(roleId);
        return menus;
    }

    @Override
    public Boolean removeRolePermission(Long roleId, Long menuId) {
        SysRolePermission data = rolePermissMapper.selectByRoleIdAndMenuId(roleId, menuId);
        if (data == null) {
            throw new AuthBusException("角色权限不存在");
        }
        rolePermissMapper.deleteByPrimaryKey(data.getId());
        return true;
    }

    @Override
    public Boolean addRolePermissions(List<SysRolePermission> datas) {
        List<SysRolePermission> newDatas = new ArrayList<SysRolePermission>();
        for (SysRolePermission sysRolePermission : datas) {
            SysRolePermission data = rolePermissMapper.selectByRoleIdAndMenuId(
                sysRolePermission.getRoleId(), sysRolePermission.getMenuId());
            if (data == null) {
                newDatas.add(sysRolePermission);
            }
        }
        if (CollectionUtils.isNotEmpty(newDatas)) {
            rolePermissMapper.insertBatch(newDatas);
        }
        return true;
    }

    @Override
    public List<SysRoleMenuExt> getSysRoleMenuByRoleId(Long roleId) {
        //获取所有菜单
        List<SysMenu> menus = sysService.getAllSysMenu();
        List<SysMenu> meMenus = getSysMenuByRoleId(roleId);
        List<RoleMenuExt> allMenus = new ArrayList<RoleMenuExt>();
        for (SysMenu menu : menus) {
            boolean hasPermis = false;
            for (SysMenu meMenu : meMenus) {
                if (menu.getId().equals(meMenu.getId())) {
                    hasPermis = true;
                }
            }
            RoleMenuExt roleMenuExt = new RoleMenuExt();
            BeanUtils.copyProperties(menu, roleMenuExt);
            roleMenuExt.setHasPermis(hasPermis);
            allMenus.add(roleMenuExt);
        }

        List<SysInfo> allSys = sysService.getAllSys();
        return SysRoleMenuExt.getSysRoleMenuExt(allSys, allMenus);
    }

    @Override
    public SysRole updateRole(SysRole sysRole) {
        SysRole data = getRoleByRoleId(sysRole.getId());
        if (data == null) {
            throw new AuthBusException("角色不存在");
        }
        roleMapper.updateByPrimaryKeySelective(sysRole);
        return getRoleByRoleId(sysRole.getId());
    }

    @Override
    public PagingResult<SysUser> getAllUserInofs(String keyword, List<Long> roleIds,
                                                 Integer pageNumber, Integer pageSize) {
        List<SysUser> list = userMapper.selectUserByRolesAndKeyword(keyword, roleIds,
            PagingResult.countOffset(pageNumber, pageSize), pageSize);
        int total = userMapper.countUserByRolesAndKeyword(keyword, roleIds);
        return new PagingResult<SysUser>(list, total, pageNumber, pageSize);
    }

    @Override
    public List<SysUserRole> getSysUserRoleByUuids(List<String> uuids) {
        if (CollectionUtils.isEmpty(uuids)) {
            uuids = null;
        }
        return userRoleMapper.selectSysRoleByUuids(uuids);
    }

    @Override
    public void updateUserPass(String uuid, String oldPass, String newPass) {
        if (StringUtils.isBlank(oldPass)) {
            throw new AuthBusException("用户旧密码不能为空");
        }
        if (StringUtils.isBlank(newPass)) {
            throw new AuthBusException("用户新密码不能为空");
        }
        if (!checkPassIsEnble(uuid, oldPass)) {
            throw new AuthBusException("用户密码错误");
        }
        updateUserPass(uuid, newPass);
    }

    @Override
    public void updateUserPass(String uuid, String password) {
        SysUser up = new SysUser();
        up.setUuid(uuid);
        up.setPassword(SecretUtil.getMD5(password));
        updateUserByUUid(uuid, up);
    }

    @Override
    public Boolean checkPassIsEnble(String uuid, String pass) {
        SysUser user = getUserByUuid(uuid);
        if (user == null) {
            throw new AuthBusException("用户信息不存在");
        }
        if (!SecretUtil.getMD5(pass).equals(user.getPassword())) {
            return false;
        }
        return true;
    }

    @Override
    public void lockUserByUserName(String userName, boolean status) {
        SysUser user = getUserByUserName(userName);
        if (user == null) {
            throw new AuthBusException("用户不存在");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setStatus(status == false ? "1" : "0");
        userMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public void assertUser(SysUser sysUser) {
        if (sysUser == null) {
            throw new AuthBusException("用户信息不能为空");
        }
        if (StringUtils.isBlank(sysUser.getUserName()) || sysUser.getUserName().length() > 24) {
            throw new AuthBusException("用户登录名不可为空且必须小于24个字符");
        }
        if (StringUtils.isBlank(sysUser.getRealName()) || sysUser.getRealName().length() > 16) {
            throw new AuthBusException("用户真实名不可为空且必须小于16个字符");
        }
        if (StringUtils.isBlank(sysUser.getPhone())) {
            throw new AuthBusException("用户手机号为必须");
        }
        if (StringUtils.isBlank(sysUser.getGender())) {
            throw new AuthBusException("用户性别为必选");
        }
    }

    @Override
    public UserInfoModel getLoginUserInfo(String uuid) {
        UserInfoModel usermodel = (UserInfoModel) cacheService
            .getObj(UserInfoUtil.generateCacheUserKey(uuid));
        return usermodel;
    }

    @Override
    public boolean checkUserLimit(String uuid, String permissionCode) {
        boolean permit = false;
        UserInfoModel userModel = getLoginUserInfo(uuid);
        //校验该角色是否用于此方法的权限
        if (StringUtils.isNotBlank(permissionCode)) {
            SysMenu menu = UserInfoModel.getMenuByPermissionCode(userModel.getSysMenu(),
                permissionCode);
            if (menu != null) {
                permit = true;
            }
        }
        return permit;
    }

}
