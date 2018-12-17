package com.efida.sports.dmp.service.dubbo.intergration.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.facade.model.GoodsModel;
import com.efida.sport.facade.model.GoodsOrderModel;
import com.efida.sport.facade.service.ShopFacadeService;
import com.efida.sports.dmp.service.dubbo.cover.PagingResultCover;
import com.efida.sports.dmp.service.dubbo.intergration.ShopFacadeClient;

import cn.evake.auth.usermodel.PagingResult;

@Service("shopFacadeClient")
public class ShopFacadeClientImpl implements ShopFacadeClient {

    @Reference
    private ShopFacadeService shopFacadeService;

    private static Logger     log = LoggerFactory.getLogger(ShopFacadeClientImpl.class);

    @Override
    public void saveGoods(GoodsModel goods) {
        this.shopFacadeService.saveGoods(goods);
    }

    @Override
    public GoodsModel getOneGoodsById(long id) {
        return this.shopFacadeService.getOneGoodsById(id);
    }

    @Override
    public PagingResult<GoodsModel> getGoodsList(Map<String, Object> params) {
        PagingResult<GoodsModel> pagingResult = PagingResultCover
            .convertVO(shopFacadeService.getGoodsListByPage(params));
        return pagingResult;
    }

    @Override
    public PagingResult<GoodsOrderModel> getGoodsOrderList(Map<String, Object> params) {
        PagingResult<GoodsOrderModel> pagingResult = PagingResultCover
            .convertVO(shopFacadeService.getGoodsOrderListByPage(params));
        return pagingResult;
    }

    @Override
    public void sendGoodsOrder(int id) {
        this.shopFacadeService.sendGoodsOrder(id);
    }

    @Override
    public void goodsOrderAutoCompleted() {
        this.shopFacadeService.goodsOrderAutoCompleted();
    }

}
