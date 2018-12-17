package com.efida.sports.dmp.biz.shop.quartz;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.efida.sports.dmp.service.dubbo.intergration.ShopFacadeClient;
import com.efida.sports.dmp.utils.SeqWorkerUtil;

import cn.evake.auth.service.common.CacheService;

/**
 * 
 * 商品订单定时 - 自动完成收货
 * 
 * @author yanglei
 * @version $Id: GoodsOrderTask.java, v 0.1 2018年10月15日 下午5:26:52 yanglei Exp $
 */
@Component
public class GoodsOrderTask {
    private Logger           logger        = LoggerFactory.getLogger(GoodsOrderTask.class);

    private final String     LOCK_TASK_KEY = "dmp_lock_for_GoodsOrderTask";

    @Autowired
    private CacheService     cacheSevcice;

    @Autowired
    private ShopFacadeClient shopFacadeClient;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void execute() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        String status = cacheSevcice.get(LOCK_TASK_KEY);
        if (StringUtils.isNotBlank(status)) {
            return;
        }
        try {
            logger.info("商品订单自动完成收货 start!");
            cacheSevcice.put(LOCK_TASK_KEY, SeqWorkerUtil.buildSingleId(), 600);
            shopFacadeClient.goodsOrderAutoCompleted();
            logger.info("商品订单自动完成收货 end!");
        } catch (Exception ex) {
            logger.error("", ex);
        }
        cacheSevcice.remove(LOCK_TASK_KEY);
    }
}
