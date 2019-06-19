package com.linkmoretech.order.common.request;

import java.util.Map;

/**
 * 请求 - 短信
 * @author liwenlong
 * @version 2.0
 */
public class ReqSms {
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 模板
	 */
	private SmsTemplate st;
	
	/**
	 * 短信体参数
	 */
	private Map<String,String> param;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public SmsTemplate getSt() {
		return st;
	}
	public void setSt(SmsTemplate st) {
		this.st = st;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	} 
	
	public enum SmsTemplate {
		USER_APP_LOGIN_CODE(1, "user_app_login_code"), 
		SHARE_COUPON_NOTICE(2,"share_coupon_notice"), 
		ORDER_SUSPEND_NOTICE(3,"order_suspend_notice"),  
		AUTH_RENT_STALL_NOTICE(4, "auth_rent_stall_notice"),
		ORDER_CLOSED_NOTICE(5,"order_closed_coupon_notice"),
		BRAND_USER_INVITE_NOTICE(6,"subed_brand_coupon_notice"),
		UN_BRAND_USER_INVITE_NOTICE(7,"unsub_brand_coupon_notice"),
		NEW_USER_REG_NOTICE(9, "new_user_reg_notice");
		public int type;
		public String id;

		private SmsTemplate(int type, String id) {
			this.id = id;
			this.type = type;
		}
	}
}

	

