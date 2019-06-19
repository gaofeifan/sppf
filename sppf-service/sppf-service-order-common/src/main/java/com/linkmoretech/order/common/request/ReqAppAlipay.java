package com.linkmoretech.order.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAppAlipay { 
	private String number;
	private Double amount; 
	
}
