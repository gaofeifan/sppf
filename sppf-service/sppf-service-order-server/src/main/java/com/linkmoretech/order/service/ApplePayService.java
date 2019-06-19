package com.linkmoretech.order.service;

import com.linkmoretech.order.common.request.ReqApplePay;

public interface ApplePayService {

	/**
	 * 统一下单
	 * @param order
	 * @return
	 */
	String order(ReqApplePay order);
	
	public Boolean verify(String json);

}
