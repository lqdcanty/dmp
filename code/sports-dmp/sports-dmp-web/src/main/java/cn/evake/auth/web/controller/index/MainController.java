/**
 * 
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package cn.evake.auth.web.controller.index;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.dao.model.SysMenu;
import cn.evake.auth.enmus.MenuTypeEnmu;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.MenuExt;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.devices.DeviceUtils;

/**
 * 
 * 系统主页
 * @author Evance
 * @version $Id: MainController.java, v 0.1 2018年1月20日 下午6:12:38 Evance Exp $
 */
@Controller
@RequestMapping("main")
@Authority
public class MainController {

    @Autowired
    private UserService  userService;

    private Logger       logger      = LoggerFactory.getLogger(this.getClass());

    private final String login_index = "system/login/login";

    private final String index_main  = "system/index/index";

    private final String sys_index   = "system/index/main";

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request) {
        logger.info("--进入登陆页面--");
        return login_index;
    }

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String loginOutPage(HttpServletRequest request, HttpServletResponse resp) {
        try {
            UserInfoModel userModel = userService.getUserModelAndExpire(request);
            if (userModel != null) {
                logger.info("--用户:{} 进入退出页面---> 正在用户清除数据...", userModel.getUserName());
                userService.userLoginOut(request);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "redirect:/";
    }

    @RequestMapping("index")
    @Authority(AuthorityTypeEnum.NoAuthority)
    public String sysIndex(HttpServletRequest request, Model model) {
        try {
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            List<MenuExt> sortMenus = UserInfoModel.getSortMenus(UserInfoModel.getSysMenuTree(
                UserInfoModel.getFormatURl(ServletUtil.getCtx(request), userInfo.getSysMenu())));
            //处理权限数据
            ArrayList<MenuExt> newMenus = new ArrayList<MenuExt>();
            for (MenuExt menuExt : sortMenus) {
                if (!menuExt.getType().equals(MenuTypeEnmu.permission.getCode())) {
                    newMenus.add(menuExt);
                }
            }
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("sysMenuTrees", newMenus);
            if (DeviceUtils.isMobileDevice(request)) {
                List<SysMenu> sys = userService.getUserModelAndExpire(request).getSysMenu();
                if (CollectionUtils.isNotEmpty(sys)) {
                    for (int i = 0; i < sys.size(); i++) {
                        SysMenu sysMenu = sys.get(i);
                        if (sysMenu.getSupportMobile() == true && sysMenu.getType().equals("url")) {
                            return "forward://" + sysMenu.getMenuUrl();
                        }
                    }
                }
            }
            //智能体育
            List<SysMenu> sysMenu = userInfo.getSysMenu();
            boolean isShowMain = false;
            for (SysMenu sysmenu : sysMenu) {
                if (sysmenu.getMenuUrl().contains("chartAnalysis")) {
                    isShowMain = true;
                }
            }
            //首页暂时写死赛事分页页面
            if (isShowMain) {
                model.addAttribute("indexMenu", "/pages/open/chart/chartAnalysis");
            } else {
                model.addAttribute("indexMenu", "index/main");
            }

        } catch (Exception e) {
            logger.error("", e);
            logger.info("redirect login index ---->");
            return login_index;
        }
        return index_main;
    }

    @Authority(value = AuthorityTypeEnum.NoValidate)
    @RequestMapping("index/main")
    public String indexPage(Model model) {
        return sys_index;
    }

}
