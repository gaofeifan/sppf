package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "base")
@Component
@Data
public class BaseConfig {
	private String serviceUrl;
	private Boolean online;
		
}
