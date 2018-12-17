package cn.evake.auth.usermodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import cn.evake.auth.dao.model.SysInfo;

/**
 * 
 * 系统包括菜单树
 * @author Evance
 * @version $Id: SysMenuExt.java, v 0.1 2018年4月21日 下午10:01:32 Evance Exp $
 */
public class SysRoleMenuExt extends SysInfo {

    /**  */
    private static final long serialVersionUID = -7519322229293036556L;

    private List<RoleMenuExt> menuExt;

    /**
     * 规整所有系统与菜单
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param allSys 所有系统
     * @param allMenus 所有菜单
     * @return 规整系统与菜单后的数据
     */
    public static List<SysRoleMenuExt> getSysRoleMenuExt(List<SysInfo> sysInfos,
                                                         List<RoleMenuExt> allMenus) {
        List<SysRoleMenuExt> result = new ArrayList<SysRoleMenuExt>();
        for (SysInfo sysinfo : sysInfos) {
            SysRoleMenuExt sysMenuExt = new SysRoleMenuExt();
            BeanUtils.copyProperties(sysinfo, sysMenuExt);
            sysMenuExt.setMenuExt(RoleMenuExt.getRoleMenuExtTree(sysinfo.getId(), allMenus));
            result.add(sysMenuExt);
        }
        return result;
    }

    public SysRoleMenuExt() {
        super();
    }

    public SysRoleMenuExt(List<RoleMenuExt> menuExt) {
        super();
        this.menuExt = menuExt;
    }

    public List<RoleMenuExt> getChildren() {
        return menuExt;
    }

    public Long getSysId() {
        return super.getId();
    }

    public Long getId() {
        return super.getId() + 10001L;
    }

    public Boolean getHasPermis() {
        boolean hasPermis = false;
        if (menuExt == null) {
            return hasPermis;
        }
        for (RoleMenuExt roleMenuExt : menuExt) {
            if (roleMenuExt.getHasPermis()) {
                hasPermis = true;
            }
        }
        return hasPermis;
    }

    public void setMenuExt(List<RoleMenuExt> menuExt) {
        this.menuExt = menuExt;
    }

}
