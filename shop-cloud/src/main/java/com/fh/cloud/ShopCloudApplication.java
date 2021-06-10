package com.fh.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShopCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCloudApplication.class, args);
    }

}
