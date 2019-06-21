package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 建行龙支付
 * @author jhb
 * @Date 2019年6月20日 下午7:10:41
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "loong-pay")
@Component
@Data
public class AppLoongPayConfig {
	
	private String merchantId;
	
	private String posId;
	
	private String branchId;
	
	private String url;

	private String pubKey;
	
}
