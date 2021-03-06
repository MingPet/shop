package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.api.*.mapper")
public class ShopAdminApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAdminApiApplication.class, args);
    }

}
