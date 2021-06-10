package com.fh.shop.api.rabbit.biz;

import com.fh.shop.api.rabbit.param.OrderParam;
import com.fh.shop.common.ServerResponse;
import org.springframework.transaction.annotation.Transactional;

public interface OmsPortalOrderService {
    @Transactional
    ServerResponse generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);
}
