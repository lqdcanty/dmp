package com.efida.sports.dmp.service.dubbo.intergration;

import java.util.List;

import com.efida.sport.admin.facade.model.SpProjectTypeModel;

public interface SpProjectTypeFacadeClient {
    /**
     * 获取有效项目分类
     * 
     * @return
     */
    List<SpProjectTypeModel> getProjectTypes();

    /**
     * 查询所有项目分类。包括无效项目
     * 
     * @return
     */
    List<SpProjectTypeModel> getAllProjectTypes();

}
