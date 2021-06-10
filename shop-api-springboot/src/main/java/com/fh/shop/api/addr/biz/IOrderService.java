package com.fh.shop.api.addr.biz;

import com.fh.shop.api.addr.po.Order;
import com.fh.shop.common.ServerResponse;

public interface IOrderService {
    ServerResponse addConsignee(Long memberId, Order order);

    ServerResponse findAddConsignee(Long memberId);

    ServerResponse updateStatus(Long memberId, Long id);

    ServerResponse delete(Long id);

    ServerResponse findListById(Long id);

    ServerResponse updateOrder(Order order);
}
