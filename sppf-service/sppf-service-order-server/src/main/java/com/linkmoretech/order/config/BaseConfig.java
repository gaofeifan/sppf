package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 基础配置
 * @author jhb
 * @Date 2019年6月20日 下午7:11:40
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "base")
@Component
@Data
public class BaseConfig {
	private String serviceUrl;
	private Boolean online;
		
}
