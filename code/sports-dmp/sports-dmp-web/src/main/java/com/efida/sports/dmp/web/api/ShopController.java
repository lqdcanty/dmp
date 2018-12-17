package com.efida.sports.dmp.web.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.efida.sport.dmp.facade.exception.BusinessException;
import com.efida.sport.facade.model.GoodsModel;
import com.efida.sport.facade.model.GoodsOrderModel;
import com.efida.sports.dmp.exception.DmpBusException;
import com.efida.sports.dmp.open.vo.LayerPagevo;
import com.efida.sports.dmp.service.dubbo.intergration.ShopFacadeClient;
import com.efida.sports.dmp.service.template.GoodsOrderTemplate;
import com.efida.sports.dmp.utils.JsonResultUtil;
import com.efida.sports.dmp.web.cover.CommonCover;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.usermodel.PagingResult;
import cn.evake.excel.handel.ExcelHanlel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 电子奖章管理功能，包含商品管理和订单管理
 * 
 * @author yanglei
 * @version $Id: ShopController.java, v 0.1 2018年10月12日 下午2:30:56 yanglei Exp $
 */
@Controller
@RequestMapping(value = "dmp/api/shop", produces = "application/json")
@Authority(value = AuthorityTypeEnum.Validate)
public class ShopController {

    private Logger           logger      = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopFacadeClient shopFacadeClient;

    private int              maxDownLoad = 10000;

    /**
     * 订单模板文件
     */
    private byte[]           orderTemplate;

    @PostConstruct
    public void intTemplate() throws IOException {
        orderTemplate = IOUtils
            .toByteArray(PlayerApiController.class.getResourceAsStream("/excle/奖章订单-模板.xlsx"));

    }

    @ApiOperation(value = "商品管理")
    @ApiImplicitParams({ @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, dataType = "String", paramType = "query") })
    @Authority(permissionCode = "MjAxODEwMTExNzExNDk=")
    @RequestMapping(value = "goods/list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<GoodsModel> goodsList(@RequestParam(required = false) String goodsName,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int limit,
                                             HttpServletRequest request) {
        LayerPagevo<GoodsModel> resultWrapper = new LayerPagevo<GoodsModel>();
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("limit", limit);
            params.put("goodsName", goodsName);
            PagingResult<GoodsModel> resultModels = shopFacadeClient.getGoodsList(params);
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

    /**
     * 保存商品详情
     * 
     * @param id
     * @param title
     * @param goodsPic
     * @param productPrice
     * @param extraMoney
     * @param description
     * @param request
     * @return
     */
    @Authority(AuthorityTypeEnum.NoAuthority)
    @PostMapping("goods/save")
    @ResponseBody
    public Map<String, Object> saveGoods(@RequestParam(required = false) int id,
                                         @RequestParam(required = true) String title,
                                         @RequestParam(required = true) String goodsPic,
                                         @RequestParam(required = true) int productPrice,
                                         @RequestParam(required = true) int extraMoney,
                                         @RequestParam(required = true) String description,
                                         HttpServletRequest request) {

        GoodsModel goods = new GoodsModel();
        goods.setId(id);
        goods.setDescription(description);
        goods.setExtraMoney(extraMoney);
        goods.setTitle(title);
        goods.setGoodsPic(goodsPic);
        goods.setProductPrice(productPrice);
        this.shopFacadeClient.saveGoods(goods);
        return JsonResultUtil.getSuccessResult("保存成功");
    }

    /**
     * 商品详情
     * 
     * @param addressCode
     * @param model
     * @return
     */
    @ApiOperation(value = "商品详情")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品id", required = false, dataType = "long", paramType = "query") })
    @Authority(AuthorityTypeEnum.NoAuthority)
    @ResponseBody
    @PostMapping("goods/detail")
    public Map<String, Object> detail(HttpSession session,
                                      @RequestParam(value = "id", required = false) long id,
                                      Model model) {
        try {
            GoodsModel goods = this.shopFacadeClient.getOneGoodsById(id);
            if (goods == null)
                throw new DmpBusException("商品不存在");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goods", goods);
            return JsonResultUtil.getSuccessResult(map);
        } catch (DmpBusException e) {
            logger.error("", e);
            return JsonResultUtil.getServerErrorResult(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            return JsonResultUtil.getServerErrorResult("获取商品详情失败");
        }
    }

    @Authority(AuthorityTypeEnum.NoAuthority)
    @ApiOperation(value = "订单管理")
    //    @Authority(permissionCode = "MjAxODEwMTExNzA5MTk=")
    @RequestMapping(value = "order/list", method = { RequestMethod.GET })
    @ResponseBody
    public LayerPagevo<GoodsOrderModel> orderList(@RequestParam(required = false) String startTime,
                                                  @RequestParam(required = false) String endTime,
                                                  @RequestParam(required = false) String orderStatus,
                                                  @RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                  HttpServletRequest request) {
        LayerPagevo<GoodsOrderModel> resultWrapper = new LayerPagevo<GoodsOrderModel>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("limit", limit);
            if (StringUtils.isNotBlank(startTime)) {
                params.put("startTime", startTime + " 00:00:00");
            }
            if (StringUtils.isNotBlank(endTime)) {
                params.put("endTime", endTime + " 23:59:59");
            }
            if (StringUtils.isNotBlank(orderStatus)) {
                params.put("orderStatus", orderStatus);
            }
            PagingResult<GoodsOrderModel> resultModels = shopFacadeClient.getGoodsOrderList(params);
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
    @ApiOperation(value = "订单信息导出", notes = "订单信息导出")
    @ApiImplicitParams({ @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "String", paramType = "query"),
                         @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "int", paramType = "query"),
                         @ApiImplicitParam(name = "limit", value = "每页数", required = false, dataType = "int", paramType = "query"), })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "调用成功"),
                            @ApiResponse(code = 500, message = "接口系统异常") })
    @RequestMapping(value = "order/export", method = { RequestMethod.GET })
    public void exportOrderList(@RequestParam(required = false) Boolean exportAll,
                                @RequestParam(required = false) String startTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(required = false) String orderStatus,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int limit,
                                HttpServletRequest request, HttpServletResponse response) {
        try {
            //导出所有数据
            if (exportAll != null && exportAll == true) {
                page = 1;
                limit = maxDownLoad;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("limit", limit);
            if (StringUtils.isNotBlank(startTime)) {
                params.put("startTime", startTime + " 00:00:00");
            }
            if (StringUtils.isNotBlank(endTime)) {
                params.put("endTime", endTime + " 23:59:59");
            }
            if (StringUtils.isNotBlank(orderStatus)) {
                params.put("orderStatus", orderStatus);
            }
            PagingResult<GoodsOrderModel> resultModels = shopFacadeClient.getGoodsOrderList(params);
            byte[] excel = null;
            excel = new ExcelHanlel<>(GoodsOrderTemplate.class).generTemplateExcel(
                CommonCover.coverToGoodsOrderList(resultModels.getList()), "订单", 2, orderTemplate);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                String.format("attachment;filename=%s.xlsx", new Date().getTime()));
            IOUtils.write(excel, outputStream);
        } catch (IOException e) {
            logger.error("文件下载失败!", e);
        }
    }

    /**
     * 发货
     * 
     * @param id
     * @return
     */
    @Authority(AuthorityTypeEnum.NoAuthority)
    @PostMapping("order/send")
    @ResponseBody
    public Map<String, Object> sendOrder(@RequestParam(required = false) int id,
                                         HttpServletRequest request) {

        try {
            this.shopFacadeClient.sendGoodsOrder(id);
            Map<String, Object> map = new HashMap<String, Object>();
            return JsonResultUtil.getSuccessResult(map);
        } catch (BusinessException e) {
            logger.error("", e);
            return JsonResultUtil.getServerErrorResult(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
            return JsonResultUtil.getServerErrorResult("发货失败");
        }
    }
}
