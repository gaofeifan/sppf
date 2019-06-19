package com.linkmoretech.order.service;

import com.linkmoretech.order.common.request.ReqWechatMiniOrder;
import com.linkmoretech.order.common.response.ResMiniSession;
import com.linkmoretech.order.common.response.ResWechatMiniOrder;

public interface WechatMiniService {

	ResMiniSession getSession(String code);
	
	ResMiniSession getSessionPlus(String code,Integer alias);

	ResWechatMiniOrder order(ReqWechatMiniOrder wechat);

	Boolean verify(String json);
 
}
