package com.fh.shop.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {



    private RedisPool(){

    }
    //提取成全局变量
    private static JedisPool jedisPool;
    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接池最大连接数量
        jedisPoolConfig.setMaxTotal(1000);
        //闲置连接数量
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setMaxIdle(100);
        //测试连接
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnBorrow(true);

        // 需要设置连接的IP 端口号
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.247.131",6379);
    }
    //静态代码块 一个项目只需要一个连接池即可 所以用到单例得设计模式
    static {
        initPool();
    }

    public static Jedis getResource(){

        return jedisPool.getResource();
    }


}
