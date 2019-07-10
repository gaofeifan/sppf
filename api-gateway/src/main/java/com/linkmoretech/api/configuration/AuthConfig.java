package com.linkmoretech.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:21 2019-07-09
 */
@Configuration
@ConfigurationProperties("resource")
@Data
public class AuthConfig {

    private String clientId;

    private String clientSecure;

    private List<String> ignores;
}
