/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.api;

import javax.servlet.http.HttpServletRequest;

import com.efida.sports.dmp.utils.JsonResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efida.sports.dmp.dao.entity.OpenUnitEntity;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.service.unit.UnitService;
import com.efida.sports.dmp.web.vo.UnitVo;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.web.vo.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

/**
 * 数据账户API
 * @author Evance
 * @version $Id: UnitApiController2.java, v 0.1 2018年2月25日 下午9:10:24 Evance Exp $
 */
@RestController
@RequestMapping(value = "dmp/api/unit", produces = "application/json")
@Api(value = "数据账户接口", tags = "数据中心-数据账户管理")
@Authority(value = AuthorityTypeEnum.Validate)
public class UnitApiController {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitService unitService;

    @Authority(permissionCode = "MjAxODA2MjgxMTM5Mzc=")
    @ApiOperation(value = "承办方账户列表", notes = "用于承办方查询账户列表")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "unitName", value = "承办方名称", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "email", value = "联系Email", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "phone", value = "联系电话", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "lock", value = "是否锁定", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "size", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<PagingResult<UnitVo>> getUnitList(@RequestParam(required = false) String unitCode,
                                                           @RequestParam(required = false) String unitName,
                                                           @RequestParam(required = false) String email,
                                                           @RequestParam(required = false) String phone,
                                                           @RequestParam(required = false) String lock,
                                                           @RequestParam(required = false, defaultValue = "1") int page,
                                                           @RequestParam(required = false, defaultValue = "10") int size,
                                                           HttpServletRequest request) {
        ResultWrapper<PagingResult<UnitVo>> resultWrapper = new ResultWrapper<PagingResult<UnitVo>>();
        try {
            PagingResult<OpenUnitEntity> openEntitys = unitService.getPageUnitByKeywords(unitCode,
                unitName, email, phone, lock, page, size);
            resultWrapper.setData(new PagingResult<>(UnitVo.coverVos(openEntitys.getList()),
                openEntitys.getAllRow(), page, size));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA2MjgxMTM5Mzc=")
    @ApiOperation(value = "承办方账户禁用", notes = "用于承办方账户禁用")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = true, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "lock", value = "锁定", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/lock", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<UnitVo> lockUnit(@RequestParam(required = true) String unitCode,
                                          @RequestParam(required = true) boolean lock,
                                          HttpServletRequest request) {
        ResultWrapper<UnitVo> resultWrapper = new ResultWrapper<UnitVo>();
        try {
            unitService.lockUnitByCode(unitCode, lock);
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA2MjgxMTM5Mzc=")
    @ApiOperation(value = "承办方账户详细", notes = "用于调用查询承办方账户信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "unitCode", value = "承办方账号", required = true, dataType = "String", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "/detail", method = { RequestMethod.GET })
    @ResponseBody
    public ResultWrapper<UnitVo> getUnitsDetail(@RequestParam(required = true) String unitCode,
                                                HttpServletRequest request) {
        ResultWrapper<UnitVo> resultWrapper = new ResultWrapper<UnitVo>();
        try {
            OpenUnitEntity openEntity = unitService.getUnitByCode(unitCode);
            resultWrapper.setData(UnitVo.coverVo(openEntity));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception ex) {
            logger.error("系统错误 ", ex);
            resultWrapper.setErrorMsg("系统错误");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA2MjgxMTM5Mzc=")
    @ApiOperation(value = "承办方用户添加", notes = "用于承办方用户添加")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "add", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<UnitVo> unitUserAdd(@ModelAttribute UnitVo openUnit,
                                             HttpServletRequest request) {
        ResultWrapper<UnitVo> resultWrapper = new ResultWrapper<UnitVo>();
        try {
            OpenUnitEntity openUnitEn = UnitVo.toEntity(openUnit);
            OpenUnitEntity newUnit = unitService.addNewOpenUnitEntity(openUnitEn);
            resultWrapper.setData(UnitVo.coverVo(newUnit));
        } catch (DmpBusException ex) {
            resultWrapper.setErrorMsg(ex.getMessage());
            logger.error("业务错误 ", ex);
        } catch (Exception e) {
            logger.error("用户创建失败 ", e);
            resultWrapper.setErrorMsg("用户创建失败");
        }
        return resultWrapper;
    }

    @Authority(permissionCode = "MjAxODA2MjgxMTM5Mzc=")
    @ApiOperation(value = "承办方用户更新", notes = "用于承办方用户更新")
    @ApiImplicitParams({})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public ResultWrapper<UnitVo> unitUserUp(@ModelAttribute UnitVo openUnit,
                                            HttpServletRequest request) {
        ResultWrapper<UnitVo> resultWrapper = new ResultWrapper<UnitVo>();
        try {
            OpenUnitEntity openUnitEn = UnitVo.toEntity(openUnit);
            OpenUnitEntity newUnit = unitService.upOpenUnitEntity(openUnit.getUnitCode(),
                openUnitEn);
            resultWrapper.setData(UnitVo.coverVo(newUnit));
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
    @RequestMapping(value = "selectUnit", method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> selectUnit(@RequestParam(required = false) String lock, HttpServletRequest request) {
        List<OpenUnitEntity> unitList = unitService.getUnitAccountList(lock);
        return JsonResultUtil.getSuccessOpenApiResult("ok", unitList);
    }
}
