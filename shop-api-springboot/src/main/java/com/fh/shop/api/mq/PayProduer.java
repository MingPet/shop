package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.PayMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayProduer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(PayMessage scoreMessage){

        String scoreMessageJson = JSON.toJSONString(scoreMessage);
        amqpTemplate.convertAndSend(MQConstants.PAY_EXCHANGE,"",scoreMessageJson);
    }
}
