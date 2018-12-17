package com.efida.sports.dmp.service.dubbo.intergration;

import cn.evake.auth.usermodel.PagingResult;
import com.efida.sport.facade.model.SmtPayOrderDetailModel;
import com.efida.sport.facade.model.SmtPayOrderModel;

import java.util.List;
import java.util.Map;

public interface PayOrderFacadeClient {
    /**
     * 获取支付统计报表
     *
     * @return
     */
    public PagingResult<SmtPayOrderModel> getSmtPayOrderList(Map<String, Object> params);

    /**
     * 获取支付统计-明细报表
     *
     * @return
     */
    public PagingResult<SmtPayOrderDetailModel> getSmtPayOrderDetail(Map<String, Object> params);

    /**
     * 获取支付统计报表(ALL)
     *
     * @return
     */
    List<SmtPayOrderModel> getAllSmtPayOrderModel(Map<String, Object> params);

    /**
     * 获取支付统计总数
     *
     * @return
     */
    SmtPayOrderModel getSettlementPayOrderCount(Map<String, Object> params);

}
