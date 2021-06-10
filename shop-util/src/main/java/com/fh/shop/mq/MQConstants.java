package com.fh.shop.mq;

public class MQConstants {


    public static final String MAIL_EXCHANGE = "mail_exchange";//交换机
    public static final String MAIL_QUEUE = "mail_queue";//队列
    public static final String MAIL_ROUTE_KEY = "mail_route_key";//路由key


    public static final String PAY_EXCHANGE = "pay_exchange";
    public static final String PAY_QUEUE_SCORE = "pay_queue_score";//队列 积分
    public static final String PAY_QUEUE_SELL = "pay_queue_sell";//队列 销量


    public static final String  ORDER_EX = "ORDER_EX";
    public static final String  ORDER_QUEUE = "order_queue";
    public static final String  ORDER_ROUTE_KEY = "order_route_key";


    public static final String  ORDER_DEAD_EX = "order_dead_ex";
    public static final String  ORDER_DEAD_QUEUE = "order_dead_queue";
    public static final String  ORDER_DEAD_ROUTE_KEY = "order_dead_route_key";
}
