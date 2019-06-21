package com.linkmoretech.order.service;

import com.linkmoretech.order.common.request.ReqOrder;

/**
 * Service接口 - 停车场对接
 * @author jhb
 * @Date 2019年6月20日 下午7:23:26
 * @Version 1.0
 */
public interface DockingService {

	/**
	 * 发通知
	 * @param ro
	 */
	void order(ReqOrder ro);

}
