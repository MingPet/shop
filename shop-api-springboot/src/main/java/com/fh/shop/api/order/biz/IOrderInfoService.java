package com.fh.shop.api.order.biz;

import com.fh.shop.api.order.param.OrderInfoParam;
import com.fh.shop.common.ServerResponse;

public interface IOrderInfoService {

    ServerResponse addOrder(OrderInfoParam orderInfoParam);

    ServerResponse findOrderInfo(Long memberId);

}
