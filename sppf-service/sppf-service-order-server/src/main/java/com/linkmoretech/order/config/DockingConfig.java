package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "docking")
@Component
@Data
public class DockingConfig {
	
	private String orderUrl;

}
