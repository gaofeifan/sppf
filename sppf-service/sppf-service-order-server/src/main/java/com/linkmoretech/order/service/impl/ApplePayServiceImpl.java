package com.linkmoretech.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqApplePay;
import com.linkmoretech.order.config.UnionPayConfig;
import com.linkmoretech.order.pay.unionpay.SDKConfig;
import com.linkmoretech.order.pay.unionpay.Unionpay;
import com.linkmoretech.order.service.ApplePayService;

@Service
public class ApplePayServiceImpl implements ApplePayService {
	@Autowired
	private UnionPayConfig unionPayConfig;
	
	@Override
	public String order(ReqApplePay order) {
		return Unionpay.create(order, unionPayConfig);
	}
	
	public Boolean verify(String json) {
		Map<String,String> param = JsonUtil.toObject(json,HashMap.class);
		Boolean flag = false; 
		SDKConfig.init(unionPayConfig);
//		log.info("config:{}",JsonUtil.toJson(unionPayConfig));
//		log.info("json:{}",json);
//		log.info("sdk:{}",JsonUtil.toJson(SDKConfig.getConfig()));
//		AcpService.validate(param,"UTF-8");
		if("00".equals(param.get("respCode"))&&unionPayConfig.getMerId().equals(param.get("merId"))) {
			flag = true;
		}
		return flag;
	}
}
