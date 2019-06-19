package com.linkmoretech.order.service;

import com.linkmoretech.order.common.request.ReqAppAlipay;

public interface AppAlipayService {

	String order(ReqAppAlipay alipay);

	Boolean verify(String json, String number, String string);

}
