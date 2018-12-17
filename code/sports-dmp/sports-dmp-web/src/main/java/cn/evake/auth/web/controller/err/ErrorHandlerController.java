/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.web.controller.err;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;

/**
 * 异常页面处理
 * @author Evance
 * @version $Id: ErrorController.java, v 0.1 2018年5月4日 下午1:01:45 Evance Exp $
 */
@Controller
@Authority(value = AuthorityTypeEnum.NoAuthority)
public class ErrorHandlerController implements ErrorController {
    private final static String ERROR_PATH = "/error";

    private String              ERROR_500  = "system/err/500";

    private String              ERROR_404  = "system/err/404";

    private String              ERROR_403  = "system/err/403";

    /** 
     * Supports the HTML Error View
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = { "text/html" })
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse reponse,
                                  ModelAndView model) {
        if (model == null) {
            model = new ModelAndView();
        }
        if (reponse.getStatus() == 500) {
            model.setViewName(ERROR_500);
        } else if (reponse.getStatus() == 404) {
            model.setViewName(ERROR_404);
        } else if (reponse.getStatus() == 403) {
            model.setViewName(ERROR_403);
        }
        return model;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
