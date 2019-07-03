package com.linkmoretech.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户服务启动类
 * @author Alec
 * @date 2019/4/4 16:39
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.linkmoretech")
@EnableCircuitBreaker
@ComponentScan(value = "com.linkmoretech")
@Slf4j
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
        log.info("用户服务已启动...");
    }
}
