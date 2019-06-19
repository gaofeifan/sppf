package com.linkmoretech.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: alec
 * Description:
 * @date: 16:50 2019-06-05
 */
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan(value = "com.linkmoretech")
@Slf4j
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        log.info("网关服务已启动");
    }
}

