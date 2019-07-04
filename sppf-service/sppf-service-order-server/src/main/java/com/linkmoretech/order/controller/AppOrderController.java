package com.linkmoretech.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.order.common.response.ResOrderDetail;
import com.linkmoretech.order.service.OrdersService;
import com.linkmoretech.order.vo.OrderEditResponse;
import com.linkmoretech.order.vo.OrderOptionRequest;
import com.linkmoretech.order.vo.OrderRequest;
import com.linkmoretech.order.vo.ReqDownLock;
import com.linkmoretech.order.vo.ResCheckedOrder;
import com.linkmoretech.order.vo.ResCurrentOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 车位预约
 * @author jhb
 * @Date 2019年6月20日 下午7:15:17
 * @Version 1.0
 */
@Api(tags = "Order", description = "车位预约")
@RestController
@RequestMapping("app/order")
@Validated
public class AppOrderController {
	
	@Autowired
	private OrdersService ordersService;
	
	@ApiOperation(value = "预约下单", notes = "选位预约下单", consumes = "application/json")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public OrderEditResponse create(Authentication authentication, @RequestBody OrderRequest rb, HttpServletRequest request) throws CommonException {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
    	Long userId = authenticationTokenAnalysis.getUserId();
    	rb.setUserId(userId);
		OrderEditResponse orderEditResponse = this.ordersService.createOrder(rb);
		return orderEditResponse;
	}
	
	@ApiOperation(value = "取消订单", notes = "订单ID不能为空", consumes = "application/json")
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	@ResponseBody
	public OrderEditResponse cancel(Authentication authentication, @RequestBody OrderOptionRequest orderOptionRequest,HttpServletRequest request) {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
    	Long userId = authenticationTokenAnalysis.getUserId();
    	orderOptionRequest.setUserId(userId);
		OrderEditResponse editResponse = null;
		try {
			editResponse = this.ordersService.cancelOrder(orderOptionRequest);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return editResponse;
	}
	
	@ApiOperation(value = "当前订单", notes = "结账离场[组织数据,计算费用，计算时长]", consumes = "application/json")
	@RequestMapping(value = "current", method = RequestMethod.GET)
	@ResponseBody
	public ResCurrentOrder current(Authentication authentication) {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
    	Long userId = authenticationTokenAnalysis.getUserId();
		ResCurrentOrder resOrder = this.ordersService.current(userId);
		return resOrder;
	}
	
	@ApiOperation(value = "用户已完成订单列表", notes = "订单列表[起始请从0开始每页10条记录]", consumes = "application/json")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResCheckedOrder> list(Authentication authentication, @RequestParam("start") Long start) {
    	AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
    	Long userId = authenticationTokenAnalysis.getUserId();
		List<ResCheckedOrder> orders = this.ordersService.list(start, userId);
		return orders;
	}
	@ApiOperation(value = "订单详情", notes = "订单详情[订单ID须为数字]", consumes = "application/json")
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	@ResponseBody
	public ResOrderDetail detail(@Min(value=0,message="订单ID为大于0的长整数") @RequestParam("orderId") String orderId) {
		ResOrderDetail resOrderDetail = this.ordersService.detail(orderId);
		return resOrderDetail;
	}

	@ApiOperation(value = "用户预约下单后降下地锁", notes = "8005092降锁失败，更换其他车位；,8005091降锁失败，请再试一次；", consumes = "application/json")
	@RequestMapping(value = "down", method = RequestMethod.POST)
	@ResponseBody
	public Boolean controlDown(Authentication authentication, @RequestBody ReqDownLock ros) {
		AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(authentication);
    	Long userId = authenticationTokenAnalysis.getUserId();
		Boolean flag = null;
		ros.setUserId(userId);
	    flag = ordersService.controlDown(ros);
		return flag;
	}
	
	/*@ApiOperation(value = "切换车位回调", notes = "切换车位回调校验结果[0失败、1成功、2关闭订单]", consumes = "application/json")
	@RequestMapping(value = "switch/result", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> switchResult(@RequestParam("orderId")Long orderId,HttpServletRequest request) { 
		Integer count = (Integer)this.redisService.get(RedisKey.ORDER_SWITCH_RESULT.key+orderId);
		if(count==null) {
			count = SwitchResult.FAILED.value;
		}
		return  ResponseEntity.success(count, request);
	}
	@ApiOperation(value = "切换车位", notes = "原因ID不能为空，备注可为空", consumes = "application/json")
	@RequestMapping(value = "switch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> switchStall( @RequestBody ReqSwitch rs, HttpServletRequest request) {
		this.ordersService.switchStall(rs, request);
		return ResponseEntity.success(null, request);
	}
	
	//TODO
	@ApiOperation(value = "切换车位", notes = "8005105-无空闲车位,订单已关闭；8005106-切换车位失败，若返回有车位名称则表示切换车位成功", consumes = "application/json")
	@RequestMapping(value = "/v2.1/switch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> switchOrderStall(@RequestParam("orderId")Long orderId, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try { 
			String stallName = this.ordersService.switchOrderStall(orderId, request);
			response = ResponseEntity.success(stallName, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}*/
	
}
