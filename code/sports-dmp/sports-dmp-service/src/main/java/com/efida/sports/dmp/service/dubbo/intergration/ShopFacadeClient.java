package com.efida.sports.dmp.service.dubbo.intergration;

import java.util.Map;

import com.efida.sport.facade.model.GoodsModel;
import com.efida.sport.facade.model.GoodsOrderModel;

import cn.evake.auth.usermodel.PagingResult;

public interface ShopFacadeClient {

    /**
     * 保存商品信息
     * 
     * @param goods
     */
    void saveGoods(GoodsModel goods);

    /**
     * 获取商品详情
     * 
     * @param id
     * @return
     */
    GoodsModel getOneGoodsById(long id);

    /**
     * 获取商品列表
     * 
     * @param params
     * @return
     */
    PagingResult<GoodsModel> getGoodsList(Map<String, Object> params);

    /**
     * 获取订单列表
     * 
     * @param params
     * @return
     */
    PagingResult<GoodsOrderModel> getGoodsOrderList(Map<String, Object> params);

    /**
     * 发货
     * 
     * @param id
     */
    void sendGoodsOrder(int id);

    /**
     * 自动完成收货
     */
    void goodsOrderAutoCompleted();

}
