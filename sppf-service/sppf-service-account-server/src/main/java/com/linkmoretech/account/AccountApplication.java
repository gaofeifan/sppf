package com.linkmoretech.account;

import com.linkmoretech.common.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author: alec
 * Description: 账户服务启动类
 * @date: 16:52 2019-05-29
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.linkmoretech")
@EnableCircuitBreaker
@ComponentScan(basePackages = "com.linkmoretech")
@Slf4j
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
        log.info("账户服务已启动... {}" , DateTimeUtil.getLocalDateTime(DateTimeUtil.FULL_FORMAT));
    }


}