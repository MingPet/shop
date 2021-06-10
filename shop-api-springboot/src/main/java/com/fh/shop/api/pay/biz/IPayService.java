package com.fh.shop.api.pay.biz;

import com.fh.shop.common.ServerResponse;

import java.util.Map;

public interface IPayService {

     ServerResponse aliPay(String orderInfoId);

    String notifyPay(Map<String, String[]> requestParams);

}
