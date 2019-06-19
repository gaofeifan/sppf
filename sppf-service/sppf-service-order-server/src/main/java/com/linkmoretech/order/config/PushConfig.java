package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Config - 极光推送
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "push")
@Component
@Data
public class PushConfig {
	
	private String key;
	private String secret;
	
	private String keyAdd;
	private String secretAdd;
	
	private String keyAddM;
	private String secretAddM;
	
}
