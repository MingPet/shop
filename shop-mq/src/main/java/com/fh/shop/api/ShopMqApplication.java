package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.fh.shop.mapper"})
public class ShopMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMqApplication.class, args);
    }

}
