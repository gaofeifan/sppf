package com.linkmoretech.order.common.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLongPay {

	private String orderId;
	
	private BigDecimal amount;
	
	private String userId;

}
