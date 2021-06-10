package com.fh.shop.api.mq;

import com.fh.shop.api.brand.biz.order.IOrderInfoTwoService;
import com.fh.shop.mq.MQConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderConsumer {

    @Resource(name = "orderInfoServiceTwo")
    private IOrderInfoTwoService orderInfoServiceTwo;

    @RabbitListener(queues = MQConstants.ORDER_DEAD_QUEUE)
    public void handleOrderMsg(String orderId){
        orderInfoServiceTwo.cancelOrder(orderId);
    }
}
