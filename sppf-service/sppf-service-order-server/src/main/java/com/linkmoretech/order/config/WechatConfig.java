package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Config - 微信服务号配置
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WechatConfig { 
	private String appId;
	private String appSecret;
	private String token;
	private String serviceUrl;
	private String noncestr;
	
}
