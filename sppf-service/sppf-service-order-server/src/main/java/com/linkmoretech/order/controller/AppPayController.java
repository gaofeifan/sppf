package com.linkmoretech.order.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.order.common.request.ReqPayConfirm;
import com.linkmoretech.order.common.response.ResOrderDetail;
import com.linkmoretech.order.common.response.ResPayCheckout;
import com.linkmoretech.order.common.response.ResPayConfirm;
import com.linkmoretech.order.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 结账支付
 * @author jhb
 * @Date 2019年6月20日 下午7:15:51
 * @Version 1.0
 */
@Api(tags="Pay",description="订单结账")
@RestController
@RequestMapping("app/pay")
@Slf4j
public class AppPayController {
		
	@Autowired
	private PayService payService;
	
	@ApiOperation(value = "生成账单", notes = "生成账单[订单ID不为空]", consumes = "application/json")
	@RequestMapping(value = "checkout", method = RequestMethod.GET)
	@ResponseBody
	public ResPayCheckout checkout(@RequestParam("orderId") String orderId, HttpServletRequest request) {
		log.info("....checkout....orderId:{}", orderId);
		String userId = "322424324126655";
		ResPayCheckout checkout = null;
		try {
			checkout = payService.checkout(orderId,userId);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return checkout;
	}
	@ApiOperation(value = "确认支付", notes = "确认支付", consumes = "application/json")
	@RequestMapping(value = "confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResPayConfirm confirm(@RequestBody ReqPayConfirm roc, HttpServletRequest request) {
		ResPayConfirm response;
		try {
			response = this.payService.confirm(roc,request);
			return response;
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ApiOperation(value = "校验支付", notes = "校验支付[订单ID不为空]", consumes = "application/json")
	@RequestMapping(value = "verify", method = RequestMethod.GET)
	@ResponseBody
	public ResOrderDetail verify(@RequestParam("orderId") String orderId, HttpServletRequest request) {
		String userId = "322424324126655";
		ResOrderDetail detail = null;
		try {
			detail = this.payService.verify(orderId,userId);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return detail;
	}
	
}
