/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.open.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efida.sport.admin.facade.model.SpProjectTypeModel;
import com.efida.sport.open.OpenException;
import com.efida.sport.open.model.OpenProjectModel;
import com.efida.sports.dmp.biz.open.OpenConfigService;
import com.efida.sports.dmp.biz.open.ProjectService;
import com.efida.sports.dmp.biz.open.SignUtils;
import com.efida.sports.dmp.service.dubbo.intergration.SpProjectTypeFacadeClient;

/**
 * 
 * @author zhiyang
 * @version $Id: ProjectServiceImpl.java, v 0.1 2018年5月28日 下午5:03:16 zhiyang Exp $
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    private Logger                    logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SpProjectTypeFacadeClient projectTypeFacadeClient;

    @Autowired
    private OpenConfigService         openConfigService;

    /**
     * @throws ParseException 
     * @see com.efida.sports.dmp.biz.open.ProjectService#queryProjectList(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<OpenProjectModel> queryProjectList(String unitCode, String timestamp, String sign)
                                                                                                  throws OpenException,
                                                                                                  ParseException {
        SignUtils.checkTimestamp(timestamp);

        String privateKey = openConfigService.getPrivateKey(unitCode);
        String signData = unitCode + privateKey + timestamp;
        SignUtils.assertSignTrue(signData, sign);
        List<SpProjectTypeModel> projects = projectTypeFacadeClient.getAllProjectTypes();
        List<OpenProjectModel> items = convert2ProjectModel(projects);

        return items;
    }

    /**
     * 
     * 
     * @param projects
     * @return
     */
    private List<OpenProjectModel> convert2ProjectModel(List<SpProjectTypeModel> projects) {

        List<OpenProjectModel> items = new ArrayList<OpenProjectModel>();
        if (projects == null) {
            return items;
        }
        for (SpProjectTypeModel project : projects) {

            OpenProjectModel item = new OpenProjectModel();
            item.setId(0L);
            item.setImageUrl(project.getImage());
            item.setProjectCode(project.getGameCode());
            item.setProjectName(project.getGameName());
            item.setStatus(project.getStatus());
            items.add(item);
        }

        return items;
    }

    /**
     *  返回有效的項目
     * @return
     */
    @Override
    public List<OpenProjectModel> selectProjectList() {
        List<SpProjectTypeModel> projects = projectTypeFacadeClient.getProjectTypes();
        List<OpenProjectModel> items = convert2ProjectModel(projects);
        return items;
    }
}
