package com.linkmoretech.order.service;

import java.io.IOException;
import org.jdom.JDOMException;
import com.linkmoretech.order.common.request.ReqAppWechatOrder;
import com.linkmoretech.order.common.response.ResAppWechatOrder;
import com.linkmoretech.order.common.response.ResFans;

public interface AppWechatService {

	/**
	 * 根据code得到粉丝
	 * @param code
	 * @return
	 */
	ResFans getWechatFans(String code);

	/**
	 * 生成微信账单
	 * @param wechat
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	ResAppWechatOrder order(ReqAppWechatOrder wechat) throws JDOMException, IOException;

	public Boolean verify(String json);
}
