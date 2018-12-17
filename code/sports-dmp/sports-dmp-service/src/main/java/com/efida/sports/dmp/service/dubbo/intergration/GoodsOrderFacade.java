package com.efida.sports.dmp.service.dubbo.intergration;

import java.util.Date;
import java.util.List;

import com.efida.sport.facade.model.GoodsOrderCountModel;

/**
 * 奖章统计服务
 * 
 * @author zengbo
 * @version $Id: GoodsOrderFacadeService.java, v 0.1 2018年10月10日 上午11:32:31 zengbo Exp $
 */
public interface GoodsOrderFacade {

    /**
     * 奖章领取统计
     * 
     * @param statrTime 查询开始时间
     * @param endTime 查询结束时间
     * @param isRepeat 是否去重（0:不去重　1:去重）
     * @return
     */
    public List<GoodsOrderCountModel> queryGoodsOrderByDay(Date statrTime, Date endTime,
                                                           String isRepeat);

}
