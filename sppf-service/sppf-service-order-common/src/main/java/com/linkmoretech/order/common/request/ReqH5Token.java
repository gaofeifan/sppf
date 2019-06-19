package com.linkmoretech.order.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqH5Token {

	private String appid;
	
	private String appsecret;
	
	private String code;

	private String privateKey;
	
	private String publicKey;
	
}
