package com.linkmoretech.station;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
/**
 * @Author: alec
 * Description:
 * @date: 15:00 2019-07-15
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.linkmoretech"})
@EnableCircuitBreaker
@ComponentScan(basePackages = "com.linkmoretech")
@Slf4j
public class StationApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationApplication.class, args);
        log.info("中台服务已启动");
    }
}
