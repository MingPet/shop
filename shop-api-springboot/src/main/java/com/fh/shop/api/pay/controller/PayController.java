package com.fh.shop.api.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.config.AlipayConfig;
import com.fh.shop.api.pay.biz.IPayService;
import com.fh.shop.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Resource(name = "payService")
    private IPayService payService;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpServletRequest request;

    @Value("${ali.returnUrl.page}")
    private String returnUrl;

    @Value("${ali.returnUrl.fail.page}")
    private String returnFailUrl;

    @Check
    @PostMapping("/aliPay")
    public ServerResponse aliPay(String orderInfoId) {
        return payService.aliPay(orderInfoId);

    }

    @PostMapping("/notifyPay")
    public String notifyPay() {
        Map<String, String[]> requestParams = request.getParameterMap();

        return payService.notifyPay(requestParams);
    }

    @GetMapping("/returnPay")
    public void returnPay() throws IOException {

        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            if (signVerified) {
                String total_amount = params.get("total_amount");//价格

                response.sendRedirect(returnUrl + "?price=" + total_amount);

            } else {
                response.sendRedirect(returnFailUrl);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
