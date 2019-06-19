package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Config - 微信支付
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "app-wechat")
@Component
@Data
public class AppWechatConfig {
	private String  appId;
	private String  appSecret;
	private String mchid;
	private String 	key;
	private String serviceUrl;
	
}
