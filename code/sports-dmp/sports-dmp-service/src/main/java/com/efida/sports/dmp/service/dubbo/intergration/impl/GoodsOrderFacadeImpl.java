package com.efida.sports.dmp.service.dubbo.intergration.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.GoodsOrderCountModel;
import com.efida.sport.facade.service.GoodsOrderFacadeService;
import com.efida.sports.dmp.service.dubbo.intergration.GoodsOrderFacade;

/**
 * 奖章统计
 * 
 * @author zengbo
 * @version $Id: GoodsOrderFacadeImpl.java, v 0.1 2018年10月10日 下午4:47:53 zengbo Exp $
 */
@Service("goodsOrderFacade")
public class GoodsOrderFacadeImpl implements GoodsOrderFacade {

    private static Logger           log = LoggerFactory.getLogger(GoodsOrderFacadeImpl.class);

    @Reference
    private GoodsOrderFacadeService goodsOrderFacadeService;

    @Override
    public List<GoodsOrderCountModel> queryGoodsOrderByDay(Date statrTime, Date endTime,
                                                           String isRepeat) {
        DefaultResult<List<GoodsOrderCountModel>> result = goodsOrderFacadeService
            .queryGoodsOrderByDay(statrTime, endTime, isRepeat);
        if (!result.isSuccess()) {
            log.error(result.getErrorMsg());
            return null;
        }
        return result.getResultObj();
    }

}
