package com.linkmoretech.versatile.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * Description: 阿里云短信发送配置
 * @date: 14:32 2019-06-21
 */
@Configuration
@ConfigurationProperties("aliyuncs")
@Data
public class AliyuncsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private String sendUrl;

    private String area;

    private String domain;

    private String product;

    private String defaultConnectTimeout;

    private String defaultReadTimeout;
}
