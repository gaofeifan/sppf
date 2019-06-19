package com.linkmoretech.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResWechatMiniOrder { 
	private String id;
 
	private String nonce;
 
	private String pack;
 
	private String sign;
 
	private String type;
 
	private String stamp;

}
