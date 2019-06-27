package com.linkmoretech.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 10:05 2019-06-21
 */
@ConfigurationProperties(prefix = "swagger")
@Component
@Data
public class SwaggerConfig {

    private String title = "应用接口";
    private String description = "本文档 完全基于REST full接口说明";
    private String version = "1.0";
    private String contact = "凌猫停车";
    private String basePackage = "com.linkmoretech.account.controller";


}
