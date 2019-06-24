package com.linkmoretech.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 车区服务启动类
 * @author Alec
 * @date 2019/4/4 16:39
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.linkmoretech")
@EnableCircuitBreaker
@ComponentScan(basePackages = "com.linkmoretech")
public class ParkingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingServerApplication.class, args);
    }
}
