package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.mapper.IOrderInfoMapper;
import com.fh.shop.mapper.IOrderItemMapper;
import com.fh.shop.mapper.ISkuMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.PayMessage;
import com.fh.shop.po.OrderItem;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayConsumer {
    @Autowired
    private IMemberMapper memberMapper;
    @Autowired
    private IOrderInfoMapper orderInfoMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private ISkuMapper skuMapper;


    @RabbitListener(queues = MQConstants.PAY_QUEUE_SCORE)
    public void handleScoreMessage(String message){

        //加积分
        PayMessage payMessage = JSON.parseObject(message, PayMessage.class);

        Long memberId = payMessage.getMemberId();
        double totalPrice = Math.floor(Double.parseDouble(payMessage.getTotalAmount()));
        memberMapper.updateIntegral(memberId,totalPrice);
    }

    @RabbitListener(queues = MQConstants.PAY_QUEUE_SELL)
    public void handleSellMessage(String message){
        PayMessage payMessage = JSON.parseObject(message, PayMessage.class);
        String orderInfoId = payMessage.getOrderInfoId();
        //sku销量 次要业务
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderInfoId",orderInfoId);
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
        orderItems.stream().forEach(x -> {

            skuMapper.updateSalesVolume(x.getSkuCount(),x.getSkuId());
        });

    }
}
