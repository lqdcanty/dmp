/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api.report;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efida.sport.facade.model.SmtPayOrderDetailModel;
import com.efida.sport.facade.model.SmtPayOrderModel;
import com.efida.sports.dmp.constants.AuthCodeConstants;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.dubbo.intergration.PayOrderFacadeClient;
import com.efida.sports.dmp.service.template.SmtPayOrderTemplate;
import com.efida.sports.dmp.utils.DateUtil;
import com.efida.sports.dmp.web.cover.CommonCover;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.web.vo.ResultWrapper;
import cn.evake.excel.handel.ExcelHanlel;

/**
 * 支付统计报表
 *
 * @author antony
 * @version $Id: SettlementApiController.java, v 0.1 2018年7月21日 下午9:10:24 antony Exp $
 */
@Controller
@RequestMapping(value = "dmp/api/settlementReport", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class SettlementApiController {

    private Logger               logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService          userService;

    @Autowired

    private PayOrderFacadeClient payOrderFacadeClient;

    /**
     * 结算报表模板文件
     */
    private byte[]               smtPayOrderTemplate;
    private int              maxDownLoad = 10000;

    @PostConstruct
    public void intTemplate() throws IOException {
        smtPayOrderTemplate = IOUtils.toByteArray(
            SettlementApiController.class.getResourceAsStream("/excle/支付结算报表-模板.xlsx"));
        logger.info("success load smtPayOrderTemplate.....");

    }

    @Authority(permissionCode = "MjAxODA3MjExNzA2Mjg=")
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<SmtPayOrderModel> getSettlementReport(@RequestParam(required = false) String unitCode,
                                                             @RequestParam(required = false) String gameCode,
                                                             @RequestParam(required = false) String matchCode,
                                                             @RequestParam(required = false) String startTime,
                                                             @RequestParam(required = false) String endTime,
                                                             @RequestParam(required = false, defaultValue = "1") int page,
                                                             @RequestParam(required = false, defaultValue = "20") int limit,
                                                             HttpServletRequest request) {
        LayerPagevo<SmtPayOrderModel> resultWrapper = new LayerPagevo<SmtPayOrderModel>();
        try {
            PagingResult<SmtPayOrderModel> resultModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            if (!allowData && StringUtils.isNotBlank(unitCode)
                && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }
            if (!allowData) {
                unitCode = userInfo.getUserName();
                resultWrapper.setUnitCode(unitCode);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("limit", limit);
            params.put("unitCode", unitCode);
            params.put("gameCode", gameCode);
            params.put("matchCode", matchCode);
            if (StringUtils.isNotBlank(startTime)) {
                params.put("startTime", startTime + " 00:00:00");
            }
            if (StringUtils.isNotBlank(endTime)) {
                params.put("endTime", endTime + " 23:59:59");
            }
            resultModels = payOrderFacadeClient.getSmtPayOrderList(params);
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

    @Authority(permissionCode = "MjAxODA4MDMxNjMxNTI=")
    @RequestMapping(value = "detail", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<SmtPayOrderDetailModel> getSettlementDetailReport(
                                                                         @RequestParam(required = false) String unitCode,
                                                                         @RequestParam(required = false) String gameCode,
                                                                         @RequestParam(required = false) String matchCode,
                                                                         @RequestParam(required = false) String orderCode,
                                                                         @RequestParam(required = false) String registerCode,
                                                                         @RequestParam(required = false) String nickName,
                                                                         @RequestParam(required = false) String selectPayWayCode,
                                                                         @RequestParam(required = false) String orderStatusCode,
                                                                         @RequestParam(required = false) String orderTime,
                                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                                         @RequestParam(required = false, defaultValue = "20") int limit,
                                                                         HttpServletRequest request) {
        LayerPagevo<SmtPayOrderDetailModel> resultWrapper = new LayerPagevo<SmtPayOrderDetailModel>();
        try {
            PagingResult<SmtPayOrderDetailModel> resultModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            /*if (!allowData && StringUtils.isNotBlank(unitCode) && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }*/
            /*if (!allowData) {
                unitCode = userInfo.getUserName();SettlementApiController
                resultWrapper.setUnitCode(unitCode);
            }*/
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("unitCode", unitCode);
            params.put("gameCode", gameCode);
            params.put("matchCode", matchCode);
            params.put("orderCode", orderCode);
            params.put("registerCode", registerCode);
            params.put("nickName", nickName);
            params.put("selectPayWayCode", selectPayWayCode);
            params.put("orderStatusCode", orderStatusCode);
            if (StringUtils.isNotBlank(orderTime)) {
                params.put("startTime", orderTime + " 00:00:00");
                params.put("endTime", orderTime + " 23:59:59");
            }
            params.put("page", page);
            params.put("limit", limit);
            resultModels = payOrderFacadeClient.getSmtPayOrderDetail(params);
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

    @Authority(permissionCode = "MjAxODA3MjExNzA2Mjg=")
    @RequestMapping(value = "export", method = { RequestMethod.GET })
    @ResponseBody
    public void export(@RequestParam(required = false) String unitCode,
                       @RequestParam(required = false) String gameCode,
                       @RequestParam(required = false) String matchCode,
                       @RequestParam(required = false) String startTime,
                       @RequestParam(required = false) String endTime, HttpServletRequest request,
                       HttpServletResponse repson) {

        try {
            PagingResult<SmtPayOrderModel> resultModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            if (!allowData && StringUtils.isNotBlank(unitCode)
                && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }
            if (!allowData) {
                unitCode = userInfo.getUserName();
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("unitCode", unitCode);
            params.put("gameCode", gameCode);
            params.put("matchCode", matchCode);
            params.put("page", 1);
            params.put("limit", maxDownLoad);
            if (StringUtils.isNotBlank(startTime)) {
                params.put("startTime", startTime + " 00:00:00");
            }
            if (StringUtils.isNotBlank(endTime)) {
                params.put("endTime", endTime + " 23:59:59");
            }
             resultModels = payOrderFacadeClient.getSmtPayOrderList(params);
            long queryEnd = System.currentTimeMillis();
            byte[] excel = new ExcelHanlel<>(SmtPayOrderTemplate.class).generTemplateExcel(
                CommonCover.coverSmtPayOrderList(resultModels.getList()), "smtPayOrder", 1,
                smtPayOrderTemplate);
            System.err
                .println("gener data spends " + (System.currentTimeMillis() - queryEnd) + " ms");
            ServletOutputStream outputStream = repson.getOutputStream();
            repson.setContentType("application/vnd.ms-excel;charset=utf-8");
            repson.setCharacterEncoding("utf-8");
            repson.setHeader("Content-Disposition",
                String.format("attachment;filename=%s.xlsx", new Date().getTime()));
            IOUtils.write(excel, outputStream);
        } catch (IOException e) {
            logger.error("文件下载失败!", e);
        }
    }

    @Authority(permissionCode = "MjAxODA3MjExNzA2Mjg=")
    @RequestMapping(value = "getTotal", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<SmtPayOrderModel> getTotal(@RequestParam(required = false) String unitCode,
                                                    @RequestParam(required = false) String gameCode,
                                                    @RequestParam(required = false) String matchCode,
                                                    @RequestParam(required = false) String startTime,
                                                    @RequestParam(required = false) String endTime,
                                                    HttpServletRequest request) {
        ResultWrapper<SmtPayOrderModel> resultWrapper = new ResultWrapper<SmtPayOrderModel>();
        try {
            SmtPayOrderModel resultModels = null;
            UserInfoModel userInfo = userService.getUserModelChecked(request);
            //用户是否可以查所有数据
            Boolean allowData = userService.checkUserLimit(request,
                AuthCodeConstants.chagedata_code);
            //判断用户权限
            if (!allowData && StringUtils.isNotBlank(unitCode)
                && !unitCode.equals(userInfo.getUserName())) {
                throw new DmpBusException("暂无权限查看其它账号数据");
            }
            if (!allowData) {
                unitCode = userInfo.getUserName();
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("unitCode", unitCode);
            params.put("gameCode", gameCode);
            params.put("matchCode", matchCode);
            if (StringUtils.isNotBlank(startTime)) {
                params.put("startTime", startTime + " 00:00:00");
            }
            if (StringUtils.isNotBlank(endTime)) {
                params.put("endTime", endTime + " 23:59:59");
            }
            resultModels = payOrderFacadeClient.getSettlementPayOrderCount(params);
            String date = DateUtil.formatShort(new Date(System.currentTimeMillis()));
            resultModels.setOrderDate(date);
            resultWrapper.setData(resultModels);
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

}
