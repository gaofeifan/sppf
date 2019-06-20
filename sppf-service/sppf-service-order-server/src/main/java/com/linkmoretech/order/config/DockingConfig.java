package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 闸机对接
 * @author jhb
 * @Date 2019年6月20日 下午7:12:15
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "docking")
@Component
@Data
public class DockingConfig {
	
	private String orderUrl;

}
