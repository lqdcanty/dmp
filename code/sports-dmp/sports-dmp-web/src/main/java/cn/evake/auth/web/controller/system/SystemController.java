/**
 * 
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package cn.evake.auth.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.service.user.UserService;

/**
 * 
 * 系统页面
 * @author Evance
 * @version $Id: MainController.java, v 0.1 2018年1月20日 下午6:12:38 Evance Exp $
 */
@Controller
@RequestMapping("system")
@Authority
public class SystemController {

    @Autowired
    private UserService  userService;

    /**
     * logger
     */
    private Logger       logger       = LoggerFactory.getLogger(this.getClass());

    private final String common_pages = "system";

    @RequestMapping(value = "{modules}/{view}", method = RequestMethod.GET)
    public String commonPages(@PathVariable(required = true) String modules,
                              @PathVariable(required = true) String view,
                              HttpServletRequest request) {
        String viewUrl = String.format("%s/%s/%s", common_pages, modules, view);
        logger.debug("user view pages :{}", viewUrl);
        //对应访问码 与访问权限(测试默认为放行)
        boolean perAdd = true;
        boolean perRemove = true;
        boolean PerUpdate = true;
        String permissionCode = "0";
        //增加 修改 删除
        request.setAttribute("perAdd", perAdd);
        request.setAttribute("perRemove", perRemove);
        request.setAttribute("perUpdate", PerUpdate);
        request.setAttribute("permissionCode", permissionCode);
        return viewUrl;
    }

}
