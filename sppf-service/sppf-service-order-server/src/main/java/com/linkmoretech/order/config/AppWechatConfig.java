package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信支付
 * @author jhb
 * @Date 2019年6月20日 下午7:11:21
 * @Version 1.0
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
