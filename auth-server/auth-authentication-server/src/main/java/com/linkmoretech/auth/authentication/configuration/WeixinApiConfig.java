package com.linkmoretech.auth.authentication.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * Description:
 * @date: 13:49 2019-07-11
 */
@Configuration
@ConfigurationProperties("weixin-config")
@Data
@ToString
public class WeixinApiConfig {

    private String urlLogin;

    private String appid;

    private String secret;

}
