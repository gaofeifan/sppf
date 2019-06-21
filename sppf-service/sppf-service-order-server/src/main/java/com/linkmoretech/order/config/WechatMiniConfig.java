package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信小程序配置
 * @author jhb
 * @Date 2019年6月20日 下午7:13:37
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "wechat-mini")
@Component
@Data
public class WechatMiniConfig {
	private String  appId;
	private String  appSecret;  
	private String mchid;
	private String 	key;
	
}
