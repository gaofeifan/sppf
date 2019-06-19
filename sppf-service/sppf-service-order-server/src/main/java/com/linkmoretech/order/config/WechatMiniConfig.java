package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * Config - 小程序配置
 * @author liwenlong
 * @version 2.0
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
