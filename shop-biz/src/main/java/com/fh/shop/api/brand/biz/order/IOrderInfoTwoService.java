package com.fh.shop.api.brand.biz.order;


import com.fh.shop.common.ServerResponse;

public interface IOrderInfoTwoService {

    ServerResponse cancelOrder(String id);

}
