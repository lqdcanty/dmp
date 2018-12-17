/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import com.efida.sport.open.model.OpenProjectModel;
import com.efida.sports.dmp.biz.open.ProjectService;
import com.efida.sports.dmp.utils.JsonResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 项目信息获取
 * @author Evance
 * @version $Id: UnitApiController2.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@Controller
@RequestMapping(value = "dmp/api/project", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class ProjectApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectService projectService;

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "selectProject", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> selectUnit(HttpServletRequest request) {
        List<OpenProjectModel> projects = projectService.selectProjectList();
        return JsonResultUtil.getSuccessOpenApiResult("ok", projects);
    }
}
