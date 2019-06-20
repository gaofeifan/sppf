package com.linkmoretech.order.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.order.common.request.ReqPayConfirm;
import com.linkmoretech.order.common.response.ResOrderDetail;
import com.linkmoretech.order.common.response.ResPayCheckout;
import com.linkmoretech.order.common.response.ResPayConfirm;

/**
 * Service接口 - 支付
 * @author jhb
 * @Date 2019年6月20日 下午7:21:51
 * @Version 1.0
 */
public interface PayService {

	/**
	 * 生成账单
	 * @param orderId
	 * @param request
	 * @return
	 */
	ResPayCheckout checkout(String orderId, String userId) throws CommonException;

	/**
	 * 确认支付[生成第三支付订单]
	 * @param roc
	 * @return request
	 */
	ResPayConfirm confirm(ReqPayConfirm roc, HttpServletRequest request) throws CommonException;

	/**
	 * 核验支付结果
	 * @param orderId
	 * @param request
	 * @return
	 */
	ResOrderDetail verify(String orderId, String userId) throws CommonException;; 
	/**
	 * 微信预约结账回调
	 * @param response
	 * @param request
	 */
	void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 支付宝预约结账回调
	 * @param response
	 * @param request
	 */
	void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * Apple Pay预约结账回调
	 * @param response
	 * @param request
	 */
	void appleOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * 微信小程序支付异步回调
	 * @param response
	 * @param request
	 */
	void wechatMiniOrderNotice(HttpServletResponse response, HttpServletRequest request);

	/**
	 * @Description  建行龙支付回调
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void appleLongOrderNotice(HttpServletResponse response, HttpServletRequest request);


}
