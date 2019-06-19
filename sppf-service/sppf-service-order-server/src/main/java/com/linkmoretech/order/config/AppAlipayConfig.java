package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Config - 支付宝
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "app-alipay")
@Component
@Data
public class AppAlipayConfig {
	private String sellerId;
	private String appId;
	private String serviceUrl;
	
}
