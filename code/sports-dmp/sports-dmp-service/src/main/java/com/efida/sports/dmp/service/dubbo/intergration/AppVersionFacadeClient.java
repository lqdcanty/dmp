package com.efida.sports.dmp.service.dubbo.intergration;

import cn.evake.auth.usermodel.PagingResult;
import com.efida.sport.admin.facade.model.SpAppVersionModel;
import com.efida.sport.facade.model.SmtPayOrderDetailModel;
import com.efida.sport.facade.model.SmtPayOrderModel;

import java.util.Map;

public interface AppVersionFacadeClient {

    /**
     * 获取APP版本list
     * @param params
     */
    public PagingResult<SpAppVersionModel> getAppVersionPageList(Map<String, Object> params);

    /**
     * 保存APP版本
     * @param spAppVersionModel
     */
    public boolean saveAppVersion(SpAppVersionModel spAppVersionModel);

    /**
     * 根据ID查询App版本
     * @param id
     */
    public SpAppVersionModel queryAppVersionById(Long id);

    /**
     * 删除APP版本
     * @param id
     * @return
     */
    public boolean delAppVersion(Long id);
}
