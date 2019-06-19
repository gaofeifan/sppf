package com.linkmoretech.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkmoretech.common.util.HttpUtil;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqOrder;
import com.linkmoretech.order.config.DockingConfig;
import com.linkmoretech.order.service.DockingService;

@Service
public class DockingServiceImpl implements DockingService {
	@Autowired
	private DockingConfig dockConfig;

	class OrderThread extends Thread{
		private ReqOrder ro;
		public OrderThread(ReqOrder ro) {
			this.ro = ro;
		}
		public void run() {
			HttpUtil.sendJson(dockConfig.getOrderUrl(), JsonUtil.toJson(ro)); 
		} 
	}
	
	@Override 
	public void order(ReqOrder ro) {
		Thread thread = new OrderThread(ro);
		thread.start();
	} 
	
}
