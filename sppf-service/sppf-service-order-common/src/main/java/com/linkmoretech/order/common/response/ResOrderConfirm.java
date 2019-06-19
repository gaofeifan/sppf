package com.linkmoretech.order.common.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResOrderConfirm { 
	private BigDecimal amount; 
	private String number; 
	private Short payType; 
	private String alipay; 
	private ResOrderWeixin weixin; 
	private String apple;
	private String union;
	
}
