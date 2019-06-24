package com.linkmoretech.versatile.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * Description:
 * @date: 15:51 2019-06-21
 */
@Configuration
@ConfigurationProperties("jiujia")
@Data
public class JiujaConfig {

    private String account;

    private String password;

    private String sendUrl;



}
