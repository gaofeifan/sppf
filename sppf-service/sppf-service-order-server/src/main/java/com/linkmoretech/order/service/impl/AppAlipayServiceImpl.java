package com.linkmoretech.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqAppAlipay;
import com.linkmoretech.order.pay.PayConstants;
import com.linkmoretech.order.pay.alipay.Alipay;
import com.linkmoretech.order.pay.alipay.OrderTrade;
import com.linkmoretech.order.service.AppAlipayService;

@Service
public class AppAlipayServiceImpl implements AppAlipayService {

	@Override
	public String order(ReqAppAlipay alipay) {
		Map<String, String> m = Alipay.orderPay(alipay.getNumber(), alipay.getAmount(),
				PayConstants.BODY_ORDER); 
		return m.get("orderInfo"); 
	}

	@Override
	public Boolean verify(String json, String number, String amount) {
		Boolean flag = false;
		OrderTrade or = new OrderTrade();
		or.setOutTradeNo(number);
		or.setTotalAmount(amount);
		Map<String,String> param = JsonUtil.toObject(json, HashMap.class);
		try {
			Map<String, String> orderMap = Alipay.verifyAsync(param, or);
			if(orderMap.get("status").equals("1")) {
				flag = true;
			}
		} catch (AlipayApiException e) { 
			e.printStackTrace();
		}
		return flag;
	}
	
}
