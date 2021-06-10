package com.fh.shop.api.config;

import com.alibaba.fastjson.JSON;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.MailMessage;
import com.fh.shop.po.MsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Configuration
@Slf4j
public class MQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMsgLogMapper msgLogMapper;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //设置回调方法 消息中间件响应给咱们的生产者
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) ->{
            if(ack){
                //投递成功，更新消息的状态



                String msgId = correlationData.getId();
                MsgLog messogeLog = msgLogMapper.selectById(msgId);
                if(messogeLog.getStatus() == SystemConstant.MESSAGE_LOG_STATUS.SENDING){
                    MsgLog msgLog = new MsgLog();
                    msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SEND_SUCCESS);
                    msgLog.setUpdateTime(new Date());
                    msgLog.setMsgId(msgId);
                    msgLogMapper.updateById(msgLog);
//                    msgLogMapper.updateById(msgLog);
                }





            }else{
                //投递失败
                log.info("从P-E投递失败，消息：{}，失败原因：{}",correlationData,cause);
            }
        });
        //设置E-Q的回调,只有E-Q失败的时候才会执行returnCallback
        rabbitTemplate.setMandatory(true);//必须设置，否则只要配置文件中的配置的话 不起作用
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey) ->{
            //E-Q失败了
            log.info("E-Q失败了，消息：{},replyCode:{},replyText:{},交换机:{},路由:{}",
                    message,replyCode,replyText,exchange,routingKey);
            //更新数据库的状态
            byte[] body = message.getBody();
            try {
                String msgJson = new String(body,"utf-8");
                MailMessage mailMessage = JSON.parseObject(msgJson, MailMessage.class);
                String msgId = mailMessage.getMsgId();
                MsgLog msgLog = new MsgLog();
                msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.EXCHANGE_QUEUE_FALL);
                msgLog.setUpdateTime(new Date());
                msgLog.setMsgId(msgId);
                msgLogMapper.updateById(msgLog);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        });
        return rabbitTemplate;
    }




//    队列
    @Bean
    public DirectExchange orderEx(){
        return new DirectExchange(MQConstants.ORDER_EX,true,false);
    }
    @Bean
    public Queue orderQueue(){
        Queue queue = new Queue(MQConstants.ORDER_QUEUE, true);
        queue.addArgument("x-dead-letter-exchange",MQConstants.ORDER_DEAD_EX);
        queue.addArgument("x-dead-letter-routing-key",MQConstants.ORDER_DEAD_ROUTE_KEY);
        return queue;
    }
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderEx()).with(MQConstants.ORDER_ROUTE_KEY);
    }
//    死信队列
    @Bean
    public DirectExchange orderDeadEx(){
    return new DirectExchange(MQConstants.ORDER_DEAD_EX,true,false);
    }
    @Bean
    public Queue orderDeadQueue(){
        return new Queue(MQConstants.ORDER_DEAD_QUEUE,true);
    }
    @Bean
    public Binding orderDeadBinding(){
        return BindingBuilder.bind(orderDeadQueue()).to(orderEx()).with(MQConstants.ORDER_DEAD_ROUTE_KEY);
    }







    @Bean
    public FanoutExchange payExchange(){
        return new FanoutExchange(MQConstants.PAY_EXCHANGE,true,false);
    }
    @Bean
    public Queue scoreQueue(){
        return QueueBuilder.durable(MQConstants.PAY_QUEUE_SCORE).build();
    }
    @Bean
    public Queue sellQueue(){
        return QueueBuilder.durable(MQConstants.PAY_QUEUE_SELL).build();
    }
    @Bean
    public Binding scoreBinding(){
        return BindingBuilder.bind(scoreQueue()).to(payExchange());
    }
    @Bean
    public Binding sellBinding(){
        return BindingBuilder.bind(sellQueue()).to(payExchange());
    }





    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MQConstants.MAIL_EXCHANGE,true,false);
    }

    @Bean
    public Queue mailQueue(){
        return new Queue(MQConstants.MAIL_QUEUE,true);
    }

    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MQConstants.MAIL_ROUTE_KEY);
    }
}

