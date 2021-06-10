package com.fh.shop.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    //续命 设置生命周期
    public static void expire(String key,int seconds){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    //判断key是否存在
    public static boolean exist(String key){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    //setex
    public static void setEx(String key,String value,int seconds){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.setex(key,seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    //del
    public static Long delete(String key){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }


    //set key
    public static void set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    //hash set
    public static void hset(String key,String field,String value){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.hset(key,field,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
    //hash get
    public static String hget(String key,String field){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            return jedis.hget(key,field);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }

    }

    //get key
    public static String get(String key){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }

    }

    public static void main(String[] args) {
        //set("userName","zhangsan");

        set("userName","李三");
        set("age","18");
        set("sex","男");
        String userName = get("userName");
        String age = get("age");
        String sex = get("sex");
        System.out.println(userName+","+age+","+sex);
    }




}
