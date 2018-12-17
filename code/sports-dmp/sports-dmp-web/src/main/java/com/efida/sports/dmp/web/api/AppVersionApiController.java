/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import com.efida.sport.admin.facade.model.SpAppVersionModel;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.dubbo.intergration.AppVersionFacadeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * APP版本管理
 *
 * @author antony
 * @version $Id: SettlementApiController.java, v 0.1 2018年7月21日 下午9:10:24 antony Exp $
 */
@Controller
@RequestMapping(value = "dmp/api/appVersion", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class AppVersionApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppVersionFacadeClient appVersionFacadeClient;


    @Authority(permissionCode = "MjAxODA4MDgxNjU5MzQ=")
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public LayerPagevo<SpAppVersionModel> getAppVersion(@RequestParam(required = false) String unitCode,
                                                             @RequestParam(required = false) String appVersionID,
                                                             @RequestParam(required = false) String appVersion,
                                                             @RequestParam(required = false, defaultValue = "1") int page,
                                                             @RequestParam(required = false, defaultValue = "10") int limit,
                                                             HttpServletRequest request) {
        LayerPagevo<SpAppVersionModel> resultWrapper = new LayerPagevo<SpAppVersionModel>();
        try {
            PagingResult<SpAppVersionModel> resultModels = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("limit", limit);
            if(StringUtils.isNotBlank(appVersionID)){
                params.put("appVersionID", appVersionID);
            }
            if(StringUtils.isNotBlank(appVersion)){
                params.put("appVersion", appVersion);
            }
            resultModels = appVersionFacadeClient.getAppVersionPageList(params);
            resultWrapper.setData(resultModels.getList());
            resultWrapper.setCount(resultModels.getAllRow());
        } catch (DmpBusException ex) {
            resultWrapper.setMsg(ex.getMessage());
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public ResultWrapper<SpAppVersionModel> getAppVersionById(
                                                              @RequestParam(required = false) String appVersionID,
                                                              HttpServletRequest request) {
        ResultWrapper<SpAppVersionModel> resultWrapper = new ResultWrapper<SpAppVersionModel>();
        try {
            SpAppVersionModel resultModel = null;
            Map<String, Object> params = new HashMap<String, Object>();
            if(StringUtils.isBlank(appVersionID)){
                throw new DmpBusException("appVersionID不能为空！");
            }
            resultModel = appVersionFacadeClient.queryAppVersionById(Long.valueOf(appVersionID));
            resultWrapper.setData(resultModel);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception e) {
            logger.error("用户更新失败 ", e);
            resultWrapper.setErrorMsg("用户更新失败");
        }
        return resultWrapper;
    }


    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    @ResponseBody
    public ResultWrapper<SpAppVersionModel> saveAppVersionById(@RequestParam(required = false) String id,
                                                                 @RequestParam(required = false) String appVersion,
                                                                 @RequestParam(required = false) String appType,
                                                                 @RequestParam(required = false) String downUrl,
                                                                 @RequestParam(required = false) String appDesc,
                                                                 @RequestParam(required = false) String appdownUrl,
                                                                 @RequestParam(required = false) String isForcedUpgrade,
                                                                 @RequestParam(required = false) String status,
                                                                 HttpServletRequest request) {
        ResultWrapper<SpAppVersionModel> resultWrapper = new ResultWrapper<SpAppVersionModel>();
        try {
            SpAppVersionModel resultModel = new SpAppVersionModel();
            if(StringUtils.isNotBlank(id)){
                resultModel.setId(Long.valueOf(id));
                resultModel.setPublishTime(new Date());
                resultModel.setGmtModified(new Date());
            }else {
                resultModel.setPublishTime(new Date());
                resultModel.setGmtCreate(new Date());
                resultModel.setGmtModified(new Date());
            }
            resultModel.setAppVersion(appVersion);
            resultModel.setAppType(appType);
            resultModel.setAppdownUrl(appdownUrl);
            resultModel.setDownUrl(downUrl);
            resultModel.setAppDesc(appDesc);
            resultModel.setIsForcedUpgrade(Integer.valueOf(isForcedUpgrade));
            resultModel.setStatus(Integer.valueOf(status));
            if(!appVersionFacadeClient.saveAppVersion(resultModel)){
                resultWrapper.setErrorMsg("修改失败");
            }
            resultWrapper.setData(resultModel);
        } catch (DmpBusException ex) {
            logger.error("业务错误 ", ex);
        } catch (Exception e) {
            logger.error("用户更新失败 ", e);
        }
        return resultWrapper;
    }

}
