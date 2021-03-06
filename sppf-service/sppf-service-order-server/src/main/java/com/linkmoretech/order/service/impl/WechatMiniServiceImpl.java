package com.linkmoretech.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmoretech.common.util.HttpUtil;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqWechatMiniOrder;
import com.linkmoretech.order.common.response.ResMiniSession;
import com.linkmoretech.order.common.response.ResWechatMiniOrder;
import com.linkmoretech.order.config.Constans;
import com.linkmoretech.order.config.WechatMiniConfig;
import com.linkmoretech.order.config.WechatMiniConfigPlus;
import com.linkmoretech.order.pay.wxmini.PaySign;
import com.linkmoretech.order.pay.wxmini.WxMiniPay;
import com.linkmoretech.order.service.WechatMiniService;

@Service
public class WechatMiniServiceImpl implements WechatMiniService {

	private static final String SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatMiniConfig WechatMiniConfig;

	@Autowired
	private WechatMiniConfigPlus wechatMiniConfigPlus;

	@Override
	public ResMiniSession getSession(String code) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", WechatMiniConfig.getAppId());
		param.put("secret", WechatMiniConfig.getAppSecret());
		param.put("js_code", code);
		log.info("param:{}", JsonUtil.toJson(param));
		String json = HttpUtil.sendGet(SESSION_URL, param);
		log.info("result:{}", json);
		ResMiniSession rms = JsonUtil.toObject(json, ResMiniSession.class);
		return rms;
	}

	@Override
	public ResMiniSession getSessionPlus(String code, Integer alias) {
		Map<String, String> config = wechatMiniConfigPlus.getMiniProps();
		Map<String, String> param = new HashMap<>();
		String appId = null, appSecret = null;
		switch (alias) {
		case Constans.MINI_1001:
			appId = config.get(Constans.MINI_1001_APPID);
			appSecret = config.get(Constans.MINI_1001_APPSECRET);
			break;
		case Constans.MINI_1002:
			appId = config.get(Constans.MINI_1002_APPID);
			appSecret = config.get(Constans.MINI_1002_APPSECRET);
			break;
		case Constans.MINI_1003:
			appId = config.get(Constans.MINI_1003_APPID);
			appSecret = config.get(Constans.MINI_1003_APPSECRET);
			break;
		}
		param.put("appid", appId);
		param.put("secret", appSecret);
		param.put("js_code", code);
		log.info("param:{}", JsonUtil.toJson(param));
		String json = HttpUtil.sendGet(SESSION_URL, param);
		log.info("result:{}", json);
		ResMiniSession rms = JsonUtil.toObject(json, ResMiniSession.class);
		return rms;
	}

	@Override
	public ResWechatMiniOrder order(ReqWechatMiniOrder wechat) {
		ResWechatMiniOrder order = null;
		try {
			order = WxMiniPay.wxpay(wechat.getAddress(), wechat.getNumber(), wechat.getAmount().toString(),
					wechat.getOpenId());
			log.info("order:{}", JsonUtil.toJson(order));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public Boolean verify(String json) {
		Map<String,String> map = JsonUtil.toObject(json, HashMap.class);
		return PaySign.isValidSign(PaySign.strmapToTreeMap(map));
	}

}
