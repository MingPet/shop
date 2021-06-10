package com.fh.shop.api.pay.biz;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.config.AlipayConfig;
import com.fh.shop.api.mq.PayProduer;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.mapper.IOrderInfoMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.mq.PayMessage;
import com.fh.shop.po.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {

    @Autowired
    private IOrderInfoMapper orderInfoMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private IMemberMapper memberMapper;

    @Autowired
    private ISkuMapper skuMapper;

    @Autowired
    private PayProduer payProduer;

    /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
    @Override
    public ServerResponse aliPay(String orderInfoId) {

        //获取订单信息
        OrderInfo orderInfo = orderInfoMapper.selectById(orderInfoId);
        if(orderInfo == null){
            return ServerResponse.error(ResponseEnum.PAY_ORDER_IS_ERROR);
        }
        if(orderInfo.getStatus() != SystemConstant.ORDER_STATUS.WATT_PAY){
            //订单不是未支付状态  不让支付
            return ServerResponse.error(ResponseEnum.PAY_STATUS_IS_ERROR);
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderInfo.getId();
        //付款金额，必填
        String total_amount = orderInfo.getTotalPrice().toString();
        //订单名称，必填
        String subject = "xxx";
        //商品描述，可空
        //String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"), "UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
//                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println(result);
            return ServerResponse.success(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public String notifyPay(Map<String, String[]> requestParams) {

        try {
            Map<String,String> params = new HashMap<String,String>();


            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
            if (signVerified){
                String trade_status = params.get("trade_status");
                    if(trade_status.equals("TRADE_SUCCESS")){
                        //商家的业务处理
                        //查询订单信息
                        String out_trade_no = params.get("out_trade_no");//商户订单号

                        OrderInfo orderInfo = orderInfoMapper.selectById(out_trade_no);

                        //1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
                        if (orderInfo == null){
                            return "fail";
                        }
                        //2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
                        if(!orderInfo.getTotalPrice().toString().equals(params.get("total_amount"))){
                            return "fail";
                        }
                        //3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
                        String seller_id = params.get("seller_id");
                        if(!seller_id.equals(Constants.SELLER_UID)){
                            return "fail";
                        }
                        //4、验证app_id是否为该商户本身。
                        String app_id = params.get("app_id");
                        if(!app_id.equals(AlipayConfig.app_id)){
                            return "fail";
                        }

                        //如果状态不是未支付状态；已经支付成功的不需要再支付
                        if(orderInfo.getStatus() != SystemConstant.ORDER_STATUS.WATT_PAY){
                            return "success";
                        }
                        //更改订单状态  主要业务
                        OrderInfo orderInfo1 = new OrderInfo();
                        orderInfo1.setId(out_trade_no);
                        orderInfo1.setStatus(SystemConstant.ORDER_STATUS.PAY_SUCCESS);//改成已支付
                        orderInfoMapper.updateById(orderInfo1);
                        //会员加积分 次要业务
                        OrderInfo orderInfo2 = orderInfoMapper.selectById(out_trade_no);
                        Long memberId = orderInfo2.getMemberId();
                        String total_amount = params.get("total_amount");//价格
//                        double totalPrice = Math.floor(Double.parseDouble(total_amount));
                        //long longTotalPrice = new Double(totalPrice).longValue();
//                        memberMapper.updateIntegral(memberId,totalPrice);



                        //sku销量 次要业务
//                        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
//                        queryWrapper.eq("orderInfoId",out_trade_no);
//                        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
//                        orderItems.stream().forEach(x -> {
//
//                            skuMapper.updateSalesVolume(x.getSkuCount(),x.getSkuId());
//                        });

                        PayMessage payMessage = new PayMessage();
                        payMessage.setMemberId(memberId);
                        payMessage.setTotalAmount(total_amount);
                        payMessage.setOrderInfoId(out_trade_no);
                        payProduer.sendMessage(payMessage);
                        return "success";
                    }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "fail";




    }


}
