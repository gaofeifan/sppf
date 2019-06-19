package com.linkmoretech.auth.resource.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 10:56 2019-06-17
 */
@Configuration
@ConfigurationProperties("resource")
@Data
public class OauthResourceConfig {

    private List<String> ignores;
}
