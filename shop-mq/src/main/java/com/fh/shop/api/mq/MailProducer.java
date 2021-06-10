package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMail(MailMessage mailMessage){
        String mailMessageJson = JSON.toJSONString(mailMessage);
        amqpTemplate.convertAndSend(MQConfig.MAIL_EXCHANGE,MQConfig.MAIL_ROUTE_KEY,mailMessageJson);
    }
}
