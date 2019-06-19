package com.linkmoretech.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResOrderWeixin { 
	private String appid; 
	private String partnerid; 
	private String prepayid;  
	private String noncestr; 
	private String timestamp;  
	private String sign; 
}
