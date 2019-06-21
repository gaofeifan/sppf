package com.linkmoretech.order.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 小程序配置
 * @author jhb
 * @Date 2019年6月20日 下午7:14:20
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "wechat-mini-plus")
@Component
@Data
public class WechatMiniConfigPlus {

	private Map<String, String> miniProps = new HashMap<>();

	public Map<String, String> getMiniProps() {
		return miniProps;
	}

	public void setMiniProps(Map<String, String> miniProps) {
		this.miniProps = miniProps;
	}
	
}
