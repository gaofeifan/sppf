package com.linkmoretech.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 银联支付
 * @author jhb
 * @Date 2019年6月20日 下午7:13:05
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "union-pay")
@Component
@Data
public class UnionPayConfig { 
	private String unionServiceUrl;
	private String localServiceUrl;
	private String merId;
	private String certDir;
	private String certPath;
	private String certPwd;
	private String certType;
	private String encryptCertPath;
	private String middleCertPath;
	private String rootCertPath;
	private Boolean online; 
	
}
