package com.linkmoretech.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResH5Term {
	
	private Long timeStamp;
	private String pack;
	private String nonceStr;
	private String paySign;
	private String appId;
	private String url;
	
}
