package com.fh.shop.api;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.fh.shop.api.*.mapper","com.fh.shop.mapper"})
@EnableScheduling   //启用定时任务的配置
public class ShopApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiSpringbootApplication.class, args);
    }

}
