package com.fh.shop.api.rabbit.param;

import lombok.Data;

@Data
public class OrderParam {
    //收货地址id
    private Long orderId;
    //优惠券id
    private Long couponId;
    //使用的积分数
    private Integer useIntegration;
    //支付方式
    private Integer payType;
}
