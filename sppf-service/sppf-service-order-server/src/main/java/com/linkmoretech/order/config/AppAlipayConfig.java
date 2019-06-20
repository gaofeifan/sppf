package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 支付宝支付
 * @author jhb
 * @Date 2019年6月20日 下午7:11:02
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "app-alipay")
@Component
@Data
public class AppAlipayConfig {
	private String sellerId;
	private String appId;
	private String serviceUrl;
	
}
