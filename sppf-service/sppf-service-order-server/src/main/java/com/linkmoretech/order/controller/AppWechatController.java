package com.linkmoretech.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.order.common.response.ResFans;
import com.linkmoretech.order.service.AppWechatService;

/**
 * 微信
 * @author jhb
 * @Date 2019年6月28日 上午10:55:59
 * @Version 1.0
 */
@RestController
@RequestMapping("app-wechat")
public class AppWechatController {
	@Autowired
	private AppWechatService appWechatService;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 对其他服务提供接口
	 * 根据code获取粉丝
	 * @param code 授权码
	 * @return
	 */
	@GetMapping(value = "fans")
	//@ResponseBody
	@IgnoreResponseAdvice
	public ResFans getFans(@RequestParam(value = "code") String code) {
		log.info("code = {}",code);
		return this.appWechatService.getWechatFans(code);
	}
	
}
