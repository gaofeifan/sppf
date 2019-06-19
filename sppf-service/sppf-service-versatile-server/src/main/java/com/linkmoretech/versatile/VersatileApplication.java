package com.linkmoretech.versatile;

/**
 * @Author: alec
 * @Description:
 * @date: 8:21 PM 2019/4/29
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
/**
 * 基础服务启动类
 * @author Alec
 * @date 2019/4/4 16:39
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@ComponentScan(value = "com.linkmoretech")
@Slf4j
public class VersatileApplication {
    public static void main(String[] args) {
        SpringApplication.run(VersatileApplication.class, args);
        log.info("基础服务已启动...");
    }
}
