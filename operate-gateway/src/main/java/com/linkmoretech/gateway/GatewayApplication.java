package com.linkmoretech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: alec
 * Description: 网关启动
 * @date: 13:09 2019-05-16
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
@ComponentScan(value = "com.linkmoretech")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
