package com.linkmoretech.parking.enums;

import com.linkmoretech.parking.service.PushService;
import com.linkmoretech.parking.service.impl.DirectPushServiceImpl;
import com.linkmoretech.parking.service.impl.JiGuangPushServiceImpl;
import com.linkmoretech.parking.service.impl.SocketPushServiceImpl;

/**
 * @Author GFF
 * @Description  消息枚举类
 * @Date 2019/6/17
 */
public enum PushType {

	DIRECT(0,new DirectPushServiceImpl()),
	JIGUANG(1,new JiGuangPushServiceImpl()),
	SOCKET(2,new SocketPushServiceImpl());
	
	private int index;
	private PushService pushService;

	private PushType (PushService pushService){
		this.pushService = pushService;
	}
	private PushType (int index , PushService pushService){
		this.index = index;
		this.pushService = pushService;
	}
	public PushService get() {
		return pushService;
	}

	public PushService get(int index) {
		for (PushType v : values()) {
			if(v.index == index) {
				return v.pushService;
			}
		}
		return null;
	}
	
	
}
