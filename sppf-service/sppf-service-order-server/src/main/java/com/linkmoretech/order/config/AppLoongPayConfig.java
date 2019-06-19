package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 建行龙支付
 * @author   GFF
 * @Date     2018年10月17日
 * @Version  v2.0
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
