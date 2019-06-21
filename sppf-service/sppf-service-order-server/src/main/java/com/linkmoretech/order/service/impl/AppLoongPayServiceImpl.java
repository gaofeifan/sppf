package com.linkmoretech.order.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.linkmoretech.order.common.request.ReqLongPay;
import com.linkmoretech.order.common.request.ReqLoongPayVerifySign;
import com.linkmoretech.order.common.response.ResLoongPay;
import com.linkmoretech.order.config.AppLoongPayConfig;
import com.linkmoretech.order.pay.loong.JianHangLong;
import com.linkmoretech.order.service.AppLoongPayService;

/**
 * 龙支付接口
 * @author jhb
 * @Date 2019年6月20日 下午7:18:51
 * @Version 1.0
 */
@Service
public class AppLoongPayServiceImpl implements AppLoongPayService {

	@Resource
	private AppLoongPayConfig config;
	@Override
	public ResLoongPay order(ReqLongPay longPay) {
		return JianHangLong.create(longPay, config);
	}
	@Override
	public boolean callbackMsg(Map<String, Object> map) {
		JianHangLong.callbackMsg(map);
		return false;
	}
	@Override
	public boolean verifySigature(ReqLoongPayVerifySign verifySign) {
		verifySign.setPub(config.getPubKey());
		return JianHangLong.verifySigature(verifySign);
	}

	
	
}
