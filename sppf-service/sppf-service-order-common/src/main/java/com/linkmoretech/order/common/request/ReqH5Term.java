package com.linkmoretech.order.common.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqH5Term {

	private String orderId;

	private String openId;

	private String detail;

	private BigDecimal totalAmount;

	private String appId;

	private String mchId;

	private String appSecret;

	private String mchKey;

	private String priKey;

	private String pubKey;
	
	private String notifyUrl;
	
	private String return_url;

}
