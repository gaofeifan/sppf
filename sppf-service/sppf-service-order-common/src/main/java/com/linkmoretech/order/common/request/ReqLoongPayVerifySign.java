package com.linkmoretech.order.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLoongPayVerifySign {

	private String sign;
	
	private String srt;
	
	private String pub;
}
