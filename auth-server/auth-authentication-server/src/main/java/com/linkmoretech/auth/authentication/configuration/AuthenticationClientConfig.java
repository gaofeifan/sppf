package com.linkmoretech.auth.authentication.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: alec
 * Description: Oauth2 认证密钥配置
 * @date: 14:32 2019-07-03
 */
@Configuration
@ConfigurationProperties("auth-config")
@Data
@ToString
public class AuthenticationClientConfig {

   private String manageClient;

   private String manageSecret;

   private String personalClient;

   private String personalSecret;

   private String systemClient;

   private String systemSecret;

   private String platformClient;

   private String platformSecret;
}
