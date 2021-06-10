package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.po.MsgLog;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.MailMessage;
import com.fh.shop.util.DateUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MailProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMsgLogMapper msgLogMapper;


    public void sendMail(MailMessage mailMessage){
        String mailMessageJson = JSON.toJSONString(mailMessage);
        amqpTemplate.convertAndSend(MQConstants.MAIL_EXCHANGE, MQConstants.MAIL_ROUTE_KEY,mailMessageJson);
    }

    public void sendMailMessage(MailMessage mailMessage){
        //插入数据库
        String msgId = UUID.randomUUID().toString();
        mailMessage.setMsgId(msgId);

        String mailMessageJson = JSON.toJSONString(mailMessage);
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setExchangeName(MQConstants.MAIL_EXCHANGE);
        msgLog.setRountKey(MQConstants.MAIL_ROUTE_KEY+"ll");
        msgLog.setMessage(mailMessageJson);
        Date date = new Date();
        msgLog.setInsertTime(date);
        msgLog.setUpdateTime(date);
        //下一次重试时间 当前时间+1分钟
        Date retryTime = DateUtil.addMinute(date, 1);
        msgLog.setRetryTime(retryTime);
        msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SENDING);//投递中
        msgLog.setRetryCount(0);
        msgLogMapper.insert(msgLog);
        //发送消息
        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(MQConstants.MAIL_EXCHANGE,MQConstants.MAIL_ROUTE_KEY+"ll",mailMessageJson,correlationData);
    }
}
