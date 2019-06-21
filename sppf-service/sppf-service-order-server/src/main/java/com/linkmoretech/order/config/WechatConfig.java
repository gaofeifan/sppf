package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信服务号
 * @author jhb
 * @Date 2019年6月20日 下午7:13:23
 * @Version 1.0
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
