package com.linkmoretech.cbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import lombok.extern.slf4j.Slf4j;

/**
 * CBD服务启动类
 * @author jhb
 * @Date 2019年7月11日 下午4:43:51
 * @Version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@ComponentScan(value = "com.linkmoretech")
@Slf4j
public class CbdApplication {
    public static void main(String[] args) {
        SpringApplication.run(CbdApplication.class, args);
        log.info("CBD服务已启动...");
    }
}
