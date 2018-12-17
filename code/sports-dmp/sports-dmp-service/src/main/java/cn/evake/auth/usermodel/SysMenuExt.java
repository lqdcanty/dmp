package cn.evake.auth.usermodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import cn.evake.auth.dao.model.SysInfo;
import cn.evake.auth.dao.model.SysMenu;

/**
 * 
 * 系统包括菜单树
 * @author Evance
 * @version $Id: SysMenuExt.java, v 0.1 2018年4月21日 下午10:01:32 Evance Exp $
 */
public class SysMenuExt extends SysInfo {

    /**  */
    private static final long serialVersionUID = -7519322229293036556L;

    private List<MenuExt>     menuExt;

    public SysMenuExt() {
        super();
    }

    public SysMenuExt(List<MenuExt> menuExt) {
        super();
        this.menuExt = menuExt;
    }

    /**
     * 规则系统与菜单目录
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param sysInfos 系统信息
     * @param menus 菜单信息
     * @return
     */
    public static List<SysMenuExt> getSysMenuExt(List<SysInfo> sysInfos, List<SysMenu> menus) {
        List<SysMenuExt> result = new ArrayList<SysMenuExt>();
        for (SysInfo sysinfo : sysInfos) {
            SysMenuExt sysMenuExt = new SysMenuExt();
            BeanUtils.copyProperties(sysinfo, sysMenuExt);
            sysMenuExt.setMenuExt(UserInfoModel.getSysMenuTree(sysinfo.getId(), menus));
            result.add(sysMenuExt);
        }
        return result;
    }

    public List<MenuExt> getChildren() {
        return menuExt;
    }

    public Long getSysId() {
        return getId();
    }

    public void setMenuExt(List<MenuExt> menuExt) {
        this.menuExt = menuExt;
    }

}
