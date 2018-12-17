/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.usermodel;

import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import cn.evake.auth.dao.model.SysUser;
import cn.evake.auth.dao.model.SysUserRole;

/**
 * 界面展示用户信息
 * @author Evance
 * @version $Id: DiskUserVo.java, v 0.1 2018年4月21日 下午11:10:20 Evance Exp $
 */
@ApiModel(value = "界面用户展示信息")
public class DiskUserVo extends SysUser {

    /**  */
    private static final long serialVersionUID = -1951530232308766338L;

    private List<SysUserRole> roles;

    public String getPassword() {
        return "*******";
    }

    /**
     * 获取界面展示用户Vos数据
     * @param list
     * @return
     */
    public static List<DiskUserVo> getDiskUserVos(List<SysUser> list) {
        List<DiskUserVo> users = new ArrayList<DiskUserVo>();
        if (CollectionUtils.isEmpty(list)) {
            return users;
        }
        for (SysUser sysUser : list) {
            users.add(getDiskUserVo(sysUser));
        }
        return users;
    }

    /**
     * 规整用户界面显示数据
     * @param users
     * @param userRoles
     * @return
     */
    public static List<DiskUserVo> getDiskUserVo(List<SysUser> users, List<SysUserRole> userRoles) {
        List<DiskUserVo> result = new ArrayList<DiskUserVo>();
        if (CollectionUtils.isEmpty(users)) {
            return result;
        }
        for (SysUser user : users) {
            List<SysUserRole> roles = new ArrayList<SysUserRole>();
            if (!CollectionUtils.isEmpty(userRoles)) {
                for (SysUserRole userRole : userRoles) {
                    if (user.getUuid().equals(userRole.getUuid())) {
                        roles.add(userRole);
                    }
                }
            }
            DiskUserVo diskUserVo = new DiskUserVo();
            BeanUtils.copyProperties(user, diskUserVo);
            diskUserVo.setRoles(roles);
            result.add(diskUserVo);
        }
        return result;
    }

    /**
     * 获取所有用户唯一标识
     * @param list
     * @return
     */
    public static List<String> getUUids(List<SysUser> list) {
        List<String> result = new ArrayList<String>();
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        for (SysUser user : list) {
            result.add(user.getUuid());
        }
        return result;
    }

    /**
     * 获取界面展示用户Vo数据
     * @param sysUser
     * @return
     */
    public static DiskUserVo getDiskUserVo(SysUser sysUser) {
        DiskUserVo diskUserVo = new DiskUserVo();
        if (sysUser == null) {
            return diskUserVo;
        }
        BeanUtils.copyProperties(sysUser, diskUserVo);
        return diskUserVo;
    }

    public List<SysUserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysUserRole> roles) {
        this.roles = roles;
    }

}
