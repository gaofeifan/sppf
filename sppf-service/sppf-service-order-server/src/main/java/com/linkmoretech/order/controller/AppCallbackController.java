package com.linkmoretech.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.order.service.PayService;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller - 第三方回调
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@ApiIgnore
@RestController
@RequestMapping("app/callback")
@Slf4j
public class AppCallbackController {
	
	@Autowired
	private PayService payService;
		
	@RequestMapping(value = "wechat/order", method = RequestMethod.POST)
	public void wechat(HttpServletResponse response, HttpServletRequest request) {
		log.info("wechat async callback");
		this.payService.wechatOrderNotice(response,request);
	}
	@RequestMapping(value = "wechat-mini/order", method = RequestMethod.POST)
	public void wechatMini(HttpServletResponse response, HttpServletRequest request) {
		log.info("wechat mini async callback");
		this.payService.wechatMiniOrderNotice(response,request);
	}

	@RequestMapping(value = "alipay/order", consumes = { "application/x-www-form-urlencoded;charset=utf-8" })
	public void alipay(HttpServletResponse response, HttpServletRequest request) {
		log.info("alipay async callback");
		this.payService.alipayOrderNotice(response,request);
	}
	
	@RequestMapping(value ="apple/order")
	public void apple(HttpServletResponse response,HttpServletRequest request){  
		log.info("apple async callback");
		this.payService.appleOrderNotice(response,request);
	}

	@RequestMapping(value ="loong/order", method = {RequestMethod.POST,RequestMethod.GET})
	public void jianHangLong(HttpServletResponse response,HttpServletRequest request){  
		log.info("jianHangLong async callback");
		this.payService.appleLongOrderNotice(response,request);
	}
}
