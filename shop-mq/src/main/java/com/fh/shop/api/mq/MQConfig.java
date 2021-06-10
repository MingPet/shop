package com.fh.shop.api.mq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

//@Configuration
public class MQConfig {


    public static final String MAIL_EXCHANGE = "mail_exchange";//交换机
    public static final String MAIL_QUEUE = "mail_queue";//队列
    public static final String MAIL_ROUTE_KEY = "mail_route_key";//路由key

    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE,true,false);
    }

    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE,true);
    }

    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTE_KEY);
    }
}
