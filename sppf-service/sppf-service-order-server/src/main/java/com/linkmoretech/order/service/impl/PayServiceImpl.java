package com.linkmoretech.order.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.linkmoretech.common.Constants;
import com.linkmoretech.common.Constants.TradePayType;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.enums.StatusEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.order.common.request.ReqAppAlipay;
import com.linkmoretech.order.common.request.ReqAppWechatOrder;
import com.linkmoretech.order.common.request.ReqApplePay;
import com.linkmoretech.order.common.request.ReqLongPay;
import com.linkmoretech.order.common.request.ReqLoongPayVerifySign;
import com.linkmoretech.order.common.request.ReqPayConfirm;
import com.linkmoretech.order.common.request.ReqWechatMiniOrder;
import com.linkmoretech.order.common.response.ResAppWechatOrder;
import com.linkmoretech.order.common.response.ResLoongPay;
import com.linkmoretech.order.common.response.ResOrderConfirm;
import com.linkmoretech.order.common.response.ResOrderDetail;
import com.linkmoretech.order.common.response.ResOrderWeixin;
import com.linkmoretech.order.common.response.ResPayCheckout;
import com.linkmoretech.order.common.response.ResPayConfirm;
import com.linkmoretech.order.common.response.ResPayWeixin;
import com.linkmoretech.order.common.response.ResPayWeixinMini;
import com.linkmoretech.order.common.response.ResWechatMiniOrder;
import com.linkmoretech.order.entity.OrderDetail;
import com.linkmoretech.order.entity.Orders;
import com.linkmoretech.order.entity.RechargeRecord;
import com.linkmoretech.order.enums.OrderStatusEnum;
import com.linkmoretech.order.pay.wxpay.XMLUtil;
import com.linkmoretech.order.resposity.OrderDetailRepository;
import com.linkmoretech.order.resposity.OrdersRepository;
import com.linkmoretech.order.resposity.RechargeRecordRepository;
import com.linkmoretech.order.service.AppAlipayService;
import com.linkmoretech.order.service.AppLoongPayService;
import com.linkmoretech.order.service.AppWechatService;
import com.linkmoretech.order.service.ApplePayService;
import com.linkmoretech.order.service.PayService;
import com.linkmoretech.order.service.WechatMiniService;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付实现
 * @author jhb
 * @Date 2019年6月20日 下午7:19:55
 * @Version 1.0
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
	
	@Autowired
	private AppLoongPayService appLoongPayClient;
	
	@Autowired
	private AppAlipayService appAlipayClient;
	
	@Autowired
	private AppWechatService appWechatClient;

	@Autowired
	private ApplePayService applePayClient;

	@Autowired
	private WechatMiniService wechatMiniClient;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private RechargeRecordRepository rechargeRecordRepository;

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    StringRedisTemplate stringRedisTemplate;
	
	private final static String RESULT_SUCCESS = "success";
	private final static String RESULT_FAILURE = "fail";
	

	@Override
	public ResPayCheckout checkout(String orderId, Long userId) throws CommonException {
		Orders order = ordersRepository.getOne(orderId);
		OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(orderId);
		if(!(order.getStatus() == OrderStatusEnum.BOOKED.getCode() || order.getStatus() == OrderStatusEnum.SUSPENDED.getCode())) {
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_CHECK_EXPIRE.label);
		}
		if (order == null || !order.getUserId().equals(userId)) {
			return null;
		}
		ResPayCheckout roc = new ResPayCheckout();
		roc.setAccountAmount(new BigDecimal(0d));
		roc.setCouponCount(0);
		
		roc.setStartTime(order.getCreateTime());
		roc.setEndTime(new Date());
		if (order.getStatus() == OrderStatusEnum.SUSPENDED.getCode()) {
			roc.setEndTime(order.getStatusTime());
		}
		roc.setParkingTime(
				new Long((roc.getEndTime().getTime() - roc.getStartTime().getTime()) / (1000L * 60)).intValue());
		roc.setOrderId(orderId);
		
		roc.setPlateNumber(orderDetail.getPlateNo());
		roc.setPrefectureName(orderDetail.getParkName());
		roc.setStallName(orderDetail.getPlaceName());
		roc.setPayType((short) Constants.TradePayType.WECHAT.type);
		
		//1、支付类型相关信息，根据登录用户的类型判断 payType
		/*if (cu.getClient().shortValue() == ClientSource.ANDROID.source) {
			roc.setPayType((short) TradePayType.WECHAT.type);
		} else if (cu.getClient().shortValue() == ClientSource.IOS.source) {
			roc.setPayType((short) TradePayType.APPLE.type);
		} else {
			roc.setPayType((short) TradePayType.WECHAT.type);
		}*/
		
		//2、计费相关信息 totalAmount
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("stallId", stall.getId());
		param.put("plateNo", order.getPlateNo());
		param.put("startTime", sdf.format(roc.getStartTime()));
		param.put("endTime", sdf.format(roc.getEndTime()));
		Map<String, Object> map = this.strategyFeeClient.amount(param);
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout param:{}  map:{}", JSON.toJSON(param), JSON.toJSON(map));
		if (map != null) {
			Object object = map.get("chargePrice");
			if (object != null) {
				String totalStr = object.toString();
				String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
				log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout totalAmount:{}", totalAmountStr);
				roc.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr)));
				Map<String,Object> checkParam = new HashMap<String,Object>();
				checkParam.put("plateNo", order.getPlateNo());
				checkParam.put("preId", order.getPreId());
				boolean flag = userPlateClient.exists(checkParam);
				if(flag) {
					roc.setTotalAmount(new BigDecimal(0.00));
				}
			}
		}*/
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>checkout result:{}",JSON.toJSON(roc));
		return roc;
	} 
	
	private ResPayConfirm getConfirmResult(ResOrderConfirm confirm) {
		ResPayConfirm res = null;
		if (confirm != null) {
			res = new ResPayConfirm();
			res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			res.setPayType(confirm.getPayType());

			if (confirm.getPayType().shortValue() == Constants.TradePayType.ALIPAY.type) {
				res.setAlipay(confirm.getAlipay());
				res.setNumber(confirm.getNumber());
			} else if (confirm.getPayType().shortValue() == Constants.TradePayType.WECHAT.type) {
				res.setNumber(confirm.getNumber());
				ResOrderWeixin row = confirm.getWeixin();
				ResPayWeixin weixin = new ResPayWeixin();
				weixin.setAppid(row.getAppid());
				weixin.setNoncestr(row.getNoncestr());
				weixin.setPartnerid(row.getPartnerid());
				weixin.setPrepayid(row.getPrepayid());
				weixin.setSign(row.getSign());
				weixin.setTimestamp(row.getTimestamp());
				res.setWeixin(weixin);
			} else if (confirm.getPayType().shortValue() == Constants.TradePayType.APPLE.type) {
				res.setApple(confirm.getApple());
				res.setNumber(confirm.getNumber());
			} else if (confirm.getPayType().shortValue() == Constants.TradePayType.UNION.type
					|| confirm.getPayType().shortValue() == Constants.TradePayType.HUAWEI.type
					|| confirm.getPayType().shortValue() == Constants.TradePayType.XIAOMI.type) {
				res.setUnion(confirm.getUnion());
				res.setNumber(confirm.getNumber());
			}
		}
		return res;
	}

	@Override
	public ResPayConfirm confirm(ReqPayConfirm roc, HttpServletRequest request) throws CommonException {
		
		ResOrderConfirm confirm = null;
		//1、优惠券信息暂不考虑
		
		/*ResCoupon coupon = null;
		if (roc.getCouponId() != null) {
			coupon = this.couponClient.get(roc.getCouponId());
			log.info("..........confirm coupon:{}", JsonUtil.toJson(coupon));
			if (coupon != null && coupon.getStatus() != CouponStatus.FREE.status) {
				coupon = null;
			} else if (coupon != null && coupon.getUserId().longValue() != cu.getId().longValue()) {
				coupon = null;
			}
		}*/
		Orders orders = ordersRepository.getOne(roc.getOrderId());
		OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(roc.getOrderId());
		//订单已支付
		if(orders.getStatus() == OrderStatusEnum.DEPARTURE.getCode()) {
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_COMPLETED_PAY.label);
		}
		
		//2、支付总金额
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> param = new HashMap<String,Object>(); 
		param.put("stallId", order.getStallId());
		param.put("plateNo", order.getPlateNo());
		param.put("startTime", sdf.format(order.getCreateTime()));
		if (order.getStatus().intValue() == OrderStatus.SUSPENDED.value) {
			param.put("endTime", sdf.format(order.getStatusTime()));
		} else {
			param.put("endTime", sdf.format(new Date()));
		}
		Map<String, Object> map = this.strategyFeeClient.amount(param);
		log.info("..........checkout param:{}  map:{}", JSON.toJSON(param), JSON.toJSON(map));
		if (map != null) {
			Object object = map.get("chargePrice");
			if (object != null) {
				String totalStr = object.toString();
				String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
				log.info("..........checkout totalAmount:{}", totalAmountStr);
				order.setTotalAmount(new BigDecimal(Double.valueOf(totalAmountStr)));
			}
		}*/
		
		// 总金额
		Double amount = orders.getTotalAmount().doubleValue();
		Double faceAmount = 0d;
		//使用优惠券信息
		/*if (coupon != null) {
			faceAmount = coupon.getFaceAmount().doubleValue();
			if (coupon.getType().shortValue() == CouponType.DISCOUNT.type) {
				BigDecimal discountAmount = new BigDecimal(((100 - coupon.getDiscount()) / 100d) * amount);
				discountAmount = discountAmount.setScale(1, BigDecimal.ROUND_DOWN);
				if (coupon.getFaceAmount().doubleValue() > discountAmount.doubleValue()) {
					faceAmount = discountAmount.doubleValue();
				}
			}
		}*/
		// 支付类型1免费2优惠券3账户
		int orderPayType = 3;
		if (amount <= 0) {
			orderPayType = Constants.OrderPayType.FREE.type;
		}else {
			orderPayType = Constants.OrderPayType.ACCOUNT.type;
		}
		log.info(".......... order pay type:{}", orderPayType);
		orders.setPayAmount(new BigDecimal(amount));

		Date endTime = new Date();
		if (orders.getStatusTime() != null) {
			endTime = orders.getStatusTime();
		}
		
		// 判断实际支付金额是否为0 停车费-优惠券金额 为0则直接将订单状态改为已支付 做结账处理
		if ((orders.getPayAmount().doubleValue() <= 0)) {
			// 修改订单状态为已支付并保存
			orders.setTotalAmount(new BigDecimal(0.0d));
			orders.setPayAmount(new BigDecimal(0.0d));
			orders.setPayType(orderPayType);
			orders.setFinishTime(endTime);
			this.updateConfirm(orders);
			// 结账
			this.checkOutOrder(orders, null);
			// 返回app信息
			confirm = new ResOrderConfirm();
			confirm.setAmount(new BigDecimal(0.0D));
			confirm.setNumber(null);
			confirm.setPayType((short) (orderPayType - Constants.OrderPayType.ACCOUNT.type));
			log.info("..........confirm order actualAmount <= 0 confirm:{}",JSON.toJSON(confirm));
			return getConfirmResult(confirm);
		}
		
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setUserId(orders.getUserId());
		rechargeRecord.setStatus((short) 0);
		rechargeRecord.setOrderId(orders.getId());
		rechargeRecord.setPayStatus((short) 0);
		rechargeRecord.setCode(this.getRechargeNumer());
		double recharge = orders.getPayAmount().doubleValue();
		BigDecimal bg = new BigDecimal(recharge);
		recharge = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		rechargeRecord.setPayType((short)roc.getPayType());
		rechargeRecord.setPayAmount(new BigDecimal(recharge));
		rechargeRecord.setCreateTime(new Date());
		rechargeRecord.setUpdateTime(new Date());
		rechargeRecordRepository.save(rechargeRecord);
		
		/**
		 * 1.获取待支付订单 2.生成交易记录 根据订单和交易记录生成移动端请求参数列表
		 */
		try {
			orderPayType = Constants.OrderPayType.ACCOUNT.type + roc.getPayType();
			orders.setPayType(orderPayType);
			orders.setFinishTime(endTime);
			this.updateConfirm(orders);
			//this.putOs(request, order.getUserId());
			
			// 支付宝 支付
			if (roc.getPayType() == Constants.TradePayType.ALIPAY.type) {
				ReqAppAlipay alipay = new ReqAppAlipay();
				alipay.setAmount(rechargeRecord.getPayAmount().doubleValue());
				alipay.setNumber(rechargeRecord.getCode());
				String info = appAlipayClient.order(alipay);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPayAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.ALIPAY.type);
				confirm.setAlipay(info);
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.WECHAT.type) {
				ReqAppWechatOrder reqawo = new ReqAppWechatOrder();
				reqawo.setAddress(request.getLocalAddr());
				reqawo.setAmount(rechargeRecord.getPayAmount().doubleValue());
				reqawo.setNumber(rechargeRecord.getCode());
				ResAppWechatOrder rawo = this.appWechatClient.order(reqawo);
				log.info("get wechat order:{}", JsonUtil.toJson(rawo));
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPayAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.WECHAT.type);
				ResOrderWeixin row = new ResOrderWeixin();
				row.setAppid(rawo.getAppid());
				row.setPartnerid(rawo.getPartnerid());
				row.setPrepayid(rawo.getPrepayid());
				row.setTimestamp(rawo.getTimestamp());
				row.setNoncestr(rawo.getNoncestr());
				row.setSign(rawo.getSign());
				confirm.setWeixin(row);
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.APPLE.type) {
				ReqApplePay rap = new ReqApplePay();
				rap.setTimestramp(new Date().getTime());
				rap.setAmount(rechargeRecord.getPayAmount().doubleValue());
				rap.setNumber(rechargeRecord.getCode());
				String tn = this.applePayClient.order(rap);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPayAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) roc.getPayType());
				confirm.setApple(tn);
				log.info("apple confir :{}", JsonUtil.toJson(confirm));
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.UNION.type || roc.getPayType() == TradePayType.HUAWEI.type
					|| roc.getPayType() == TradePayType.XIAOMI.type) {
				ReqApplePay rap = new ReqApplePay();
				rap.setTimestramp(new Date().getTime());
				rap.setAmount(rechargeRecord.getPayAmount().doubleValue());
				rap.setNumber(rechargeRecord.getCode());
				String tn = this.applePayClient.order(rap);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPayAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) roc.getPayType());
				confirm.setUnion(tn);
				log.info("union confir :{}", JsonUtil.toJson(confirm));
				return getConfirmResult(confirm);
			} else if (roc.getPayType() == TradePayType.WECHAT_MINI.type) {
				ReqWechatMiniOrder wechat = new ReqWechatMiniOrder();
				wechat.setAddress(request.getLocalAddr());
				wechat.setAmount(rechargeRecord.getPayAmount().doubleValue());
				wechat.setNumber(rechargeRecord.getCode());
				//需要微信openId
				//TODO
				wechat.setOpenId("odv_a4pK8HpuIdMrob4iO8X2rnR0");
				ResWechatMiniOrder mini = this.wechatMiniClient.order(wechat);
				confirm = new ResOrderConfirm();
				confirm.setAmount(rechargeRecord.getPayAmount());
				confirm.setNumber(rechargeRecord.getCode());
				confirm.setPayType((short) TradePayType.WECHAT_MINI.type);
				ResPayConfirm res = new ResPayConfirm();
				res.setAmount(confirm.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				res.setPayType(confirm.getPayType());
				res.setNumber(confirm.getNumber());
				ResPayWeixinMini wxMini = new ResPayWeixinMini();
				wxMini.setId(mini.getId());
				wxMini.setNonce(mini.getNonce());
				wxMini.setPack(mini.getPack());
				wxMini.setSign(mini.getSign());
				wxMini.setStamp(mini.getStamp());
				wxMini.setType(mini.getType());
				res.setWeixinMini(wxMini);
				return res;
			} else if(roc.getPayType() == TradePayType.LOONG.type) {
				ReqLongPay longpay = new ReqLongPay();
				longpay.setAmount(rechargeRecord.getPayAmount());
				longpay.setOrderId(rechargeRecord.getCode());
				longpay.setUserId(orders.getUserId());
				ResLoongPay resLoongpay = appLoongPayClient.order(longpay);
				ResPayConfirm res = new ResPayConfirm();
				res.setPayType((short)TradePayType.LOONG.type);
				res.setNumber(rechargeRecord.getCode());
				res.setAmount(rechargeRecord.getPayAmount().doubleValue());
				res.setResLoongPay(new ResLoongPay(resLoongpay.getSign(), resLoongpay.getThirdAppInfo(), null));
				return res;
				
			}else {
				throw new CommonException(ResponseCodeEnum.ERROR,StatusEnum.ORDER_UNKNOW_PAY.label);
			}

		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			throw new CommonException(ResponseCodeEnum.ERROR);
		}
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	private void checkOutOrder(Orders order, RechargeRecord rechargeRecord) {
		if (rechargeRecord != null) {
			// 充值
			rechargeRecord.setPayStatus((short)1);
			rechargeRecord.setStatus((short) 1);
			rechargeRecord.setPayTime(new Date());
			rechargeRecordRepository.saveAndFlush(rechargeRecord);
		}
		// 支付
		//this.payment(order, tradeRecord, wd);

		Date current = new Date();
		Long rechargeTradeId = null;
		short tradeType = (short) Constants.TradeType.ORDER_NONE_PAY.type;
		if (null != rechargeRecord) {
			rechargeTradeId = rechargeRecord.getId();
			tradeType = (short) Constants.TradeType.ORDER_RECHARGE_PAY.type;
		} 

		// 修改车位信息
		/*
		 * 检查车位状态是否为预下线，-->下线 否则 --> 可租用 如果订单为已挂起状态，不修改车位状态
		 */

		// OrdersDetail od = ordersDetailClusterMapper.findByOrderId(order.getId());
		if (order.getStatus() == Constants.OrderStatus.UNPAID.value || order.getStatus() == Constants.OrderStatus.SUSPENDED.value) {
			try {
				log.info(">>>>>>stall checkout thread order = {}",JSON.toJSON(order));
				//new Thread(new StallCheckoutThread(order.getStallId())).start();
			} catch (Exception e) {
				log.info("up lock throw exception = {}",e.getMessage());
			}
		}
		// 更新订单
		order.setStatus(OrderStatusEnum.PAID.getCode());
		order.setUpdateTime(current);
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", order.getId());
		param.put("status", OrderStatusEnum.PAID.getCode());
		param.put("updateTime", current);
		param.put("endTime", current);
		param.put("payTime", current);
		param.put("tradeId", rechargeTradeId);
		this.orderMasterMapper.updatePayment(param);*/
		ordersRepository.finishOrder(order.getId(),OrderStatusEnum.PAID.getCode(),current);
		
		//1、更新用户下单量信息
		// this.userClient.checkout(order.getUserId());
		//2、结账调用新版推送消息
		/*Thread thread = new ProduceCheckBookingThread(order);
		thread.start();
		thread = new PushThread(order.getUserId().toString(), "订单支付通知", "支付成功", PushType.ORDER_COMPLETE_NOTICE, true);
		thread.start();*/
	}
	
	private String getRechargeNumer() {
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//Long increment = stringRedisTemplate.opsForValue().increment(Constants.RedisKey.ORDER_RECHARGE_SERIAL_NUMBER.key + sdf.format(day), 1);
		Long increment = 2L;
		Double t = Math.pow(10, 6);
		StringBuffer number = new StringBuffer();
		number.append(sdf.format(day));
		number.append(t.intValue() + increment);
		return number.toString();
	}
	
	private void updateConfirm(Orders order) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", order.getId());
		param.put("payType", order.getPayType());
		param.put("finishTime", order.getFinishTime());
		param.put("totalAmount", order.getTotalAmount());
		param.put("payAmount", order.getPayAmount());
		this.ordersRepository.updateConfirm(order.getId(), order.getPayType(), order.getFinishTime(),order.getTotalAmount(), order.getPayAmount());
	}
	
	

	@Override
	public ResOrderDetail verify(String orderId, Long userId) throws CommonException {

		Orders orders = ordersRepository.getOne(orderId);
		OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(orderId);
		Boolean flag = false;

		if (orders == null || !orders.getUserId().equals(userId)) {
			flag = false;
		} else if (orders.getStatus() == OrderStatusEnum.PAID.getCode()) {
			flag = true;
		}
		log.info("orderId:{},userId:{},verify:{},order:{}", orderId, userId, flag, JsonUtil.toJson(orders));
		ResOrderDetail resOrderDetail = null;
		if (flag && orders.getUserId().equals(userId)) {
			resOrderDetail = new ResOrderDetail();

        	
        	resOrderDetail = new ResOrderDetail();
    
        	resOrderDetail.setParkName(orderDetail.getParkName());
        	resOrderDetail.setPlaceName(orderDetail.getPlaceName());
        	resOrderDetail.setPlateNo(orderDetail.getPlateNo());
        	
        	resOrderDetail.setOrderTime(orders.getCreateTime());
        	resOrderDetail.setEndTime(orders.getFinishTime());
        	resOrderDetail.setPayTime(orders.getPayTime());
        	
        	resOrderDetail.setStatus(orders.getStatus().shortValue());
        	resOrderDetail.setPayType(orders.getPayType().shortValue());
        	
        	if(orders.getTotalAmount() == null) {
        		orders.setTotalAmount(new BigDecimal(0d));
    		}
    		if(orders.getPayAmount() == null) {
    			orders.setPayAmount(new BigDecimal(0d));
    		}
    		if(orders.getReductionAmount() == null) {
    			orders.setReductionAmount(new BigDecimal(0d));
    		}
    		resOrderDetail.setTotalAmount(orders.getTotalAmount().setScale(2, RoundingMode.HALF_UP));
    		resOrderDetail.setPayAmount(orders.getPayAmount().setScale(2, RoundingMode.HALF_UP));
    		resOrderDetail.setCouponAmount(orders.getReductionAmount().setScale(2, RoundingMode.HALF_UP));
    		
    		long day = 0;
    		long hour = 0;
    		long min = 0;
    		long time = (orders.getFinishTime().getTime()-orders.getCreateTime().getTime())/(60*1000L);
    		day = time / (24*60);
    		hour =( time % (24*60) ) / 60;
    		min = time % 60;
    		StringBuffer parkingTime = new StringBuffer();
    		if(day!=0) {
    			parkingTime.append(day);
    			parkingTime.append("天");
    			parkingTime.append(hour);
    			parkingTime.append("时"); 
    		}else if(hour!=0) {
    			parkingTime.append(hour);
    			parkingTime.append("时");
    		} 
    		parkingTime.append(min);
    		parkingTime.append("分");
    		resOrderDetail.setParkingTime(parkingTime.toString()); 
		}else {
			if(orders!= null && orders.getUserId().equals(userId)) {
				log.info("get the order null reset the value falg :{}, order:{}", flag, JsonUtil.toJson(orders));
			}else {
				log.info("verfiy error");
			}
		}
		return resOrderDetail;
	} 

	@Override
	public void wechatOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(request);
			String json = JsonUtil.toJson(map);
			log.info("wechatOrderNotice:{}", json);
			Boolean flag = this.wechat(json);
			if (flag) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("return_msg", "OK");
				param.put("return_code", "SUCCESS");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<xml>");
				for (Map.Entry<String, String> entry : param.entrySet()) {
					buffer.append("<" + entry.getKey() + ">");
					buffer.append("<![CDATA[" + entry.getValue() + "]]>");
					buffer.append("</" + entry.getKey() + ">");
				}
				buffer.append("</xml>");
				String result = new String(buffer.toString().getBytes(), "utf-8");
				// 微信通知返回业务结果为success 给微信返回成功信息
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(result);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Boolean wechat(String json) {
		Boolean flag = false;
		flag = this.appWechatClient.verify(json);
		log.info("App Wechat Pay verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			if ("SUCCESS".equals(param.get("return_code")) && "SUCCESS".equals(param.get("result_code"))) {
				String number = param.get("out_trade_no");
				RechargeRecord rr = rechargeRecordRepository.findRechargeRecordByCode(number);
				Orders orders = ordersRepository.getOne(rr.getOrderId());
				this.checkOutOrder(orders, rr);
				flag = true;
			}
		}
		return flag;
	}


	@Override
	public void alipayOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> paramMap = new HashMap<>();
			Map<String, String[]> map = request.getParameterMap();
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String param = it.next();
				String[] vals = map.get(param);
				String value = null != vals && vals.length > 0 ? (vals.length == 1 ? vals[0] : null) : null;
				if (null == value) {
					value = "";
					for (String v : vals) {
						value += "," + v;
					}
					value = value.substring(1, value.length());
				}
				paramMap.put(param, value);
			}
			String json = JsonUtil.toJson(paramMap);
			log.info("alipayOrderNotice:{}", json);
			Boolean flag = false;
			try {
				flag = this.alipay(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			PrintWriter pw = response.getWriter();
			pw.print(flag ? RESULT_SUCCESS : RESULT_FAILURE);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Boolean alipay(String json) {
		Boolean flag = false;
		Map<String, String> map = JsonUtil.toObject(json, HashMap.class);
		String number = map.get("out_trade_no");
		RechargeRecord rr = rechargeRecordRepository.findRechargeRecordByCode(number);
		flag = this.appAlipayClient.verify(json, number,
				rr.getPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		log.info("alipay verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Orders orders = ordersRepository.getOne(rr.getOrderId());
			this.checkOutOrder(orders, rr);
			flag = true;
		}
		return flag;
	}

	@Override
	public void appleOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		Enumeration<String> em = request.getParameterNames();
		String name = null;
		Map<String, String> respData = new HashMap<String, String>();
		while (em.hasMoreElements()) {
			name = em.nextElement();
			respData.put(name, request.getParameter(name));
		}
		String json = JsonUtil.toJson(respData);
		this.apple(json);
	}
	
	private Boolean apple(String json) {
		Boolean flag = false;
		flag = this.applePayClient.verify(json);
		log.info("apple verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			String number = param.get("orderId");
			RechargeRecord rr = rechargeRecordRepository.findRechargeRecordByCode(number);
			Orders orders = ordersRepository.getOne(rr.getOrderId());
			this.checkOutOrder(orders, rr);
			flag = true;
		}

		return flag;
	}

	@Override
	public void wechatMiniOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(request);
			String json = JsonUtil.toJson(map);
			log.info("wechatMiniOrderNotice:{}", json);
			Boolean flag = this.wechatMini(json);
			if (flag) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("return_msg", "OK");
				param.put("return_code", "SUCCESS");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<xml>");
				for (Map.Entry<String, String> entry : param.entrySet()) {
					buffer.append("<" + entry.getKey() + ">");
					buffer.append("<![CDATA[" + entry.getValue() + "]]>");
					buffer.append("</" + entry.getKey() + ">");
				}
				buffer.append("</xml>");
				String result = new String(buffer.toString().getBytes(), "utf-8");
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(result);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.info("wechat mini pay callback exception .");
		}
	}
	
	private Boolean wechatMini(String json) {
		Boolean flag = false;
		flag = this.wechatMiniClient.verify(json);
		log.info("Mini Wechat verify :{},result:{}", json, flag);
		if (flag) {
			flag = false;
			Map<String, String> param = JsonUtil.toObject(json, HashMap.class);
			if ("SUCCESS".equals(param.get("return_code")) && "SUCCESS".equals(param.get("result_code"))) {
				String number = param.get("out_trade_no");
				RechargeRecord rr = rechargeRecordRepository.findRechargeRecordByCode(number);
				Orders orders = ordersRepository.getOne(rr.getOrderId());
				this.checkOutOrder(orders, rr);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public void appleLongOrderNotice(HttpServletResponse response, HttpServletRequest request) {
		String POSTID = request.getParameter("POSID");
		String BRANCHID = request.getParameter("BRANCHID");
		String ORDERID = request.getParameter("ORDERID");
		String PAYMENT = request.getParameter("PAYMENT");
		String CURCODE = request.getParameter("CURCODE");
		String REMARK1 = request.getParameter("REMARK1");
		String REMARK2 = request.getParameter("REMARK2");
		String ACC_TYPE = request.getParameter("ACC_TYPE");
		String SUCCESS = request.getParameter("SUCCESS");
		String TYPE = request.getParameter("TYPE");
		String REFERER = request.getParameter("REFERER");
		String CLIENTIP = request.getParameter("CLIENTIP");
		String ACCDATE = request.getParameter("ACCDATE");
		String USRMSG = request.getParameter("USRMSG");
		String INSTALLNUM = request.getParameter("INSTALLNUM");
		String ERRMSG = request.getParameter("ERRMSG");
		String USRINFO = request.getParameter("USRINFO");
		String DISCOUNT = request.getParameter("DISCOUNT");
		String SIGN = request.getParameter("SIGN");
		StringBuilder result = new StringBuilder();
		result.append("POSID=").append(POSTID).append("&").append("BRANCHID=").append(BRANCHID).append("&")
				.append("ORDERID=").append(ORDERID).append("&").append("PAYMENT=").append(PAYMENT).append("&")
				.append("CURCODE=").append(CURCODE).append("&").append("REMARK1=").append(REMARK1).append("&")
				.append("REMARK2=").append(REMARK2).append("&").append("ACC_TYPE=").append(ACC_TYPE).append("&")
				.append("SUCCESS=").append(SUCCESS).append("&").append("TYPE=").append(TYPE).append("&")
				.append("REFERER=").append(REFERER).append("&").append("CLIENTIP=").append(CLIENTIP);
		if (StringUtils.isNotBlank(ACCDATE)) {
			result.append("&").append("ACCDATE=").append(ACCDATE);
		}
		if (StringUtils.isNotBlank(USRMSG)) {
			result.append("&").append("USRMSG=").append(USRMSG);
		}
		if (StringUtils.isNotBlank(INSTALLNUM)) {
			result.append("&").append("INSTALLNUM=").append(INSTALLNUM);
		}
		if (StringUtils.isNotBlank(ERRMSG)) {
			result.append("&").append("ERRMSG=").append(ERRMSG);
		}
		if (StringUtils.isNotBlank(USRINFO)) {
			result.append("&").append("USRINFO=").append(USRINFO);
		}
		if (StringUtils.isNotBlank(DISCOUNT)) {
			result.append("&").append("DISCOUNT=").append(DISCOUNT);
		}
		ReqLoongPayVerifySign sign = new ReqLoongPayVerifySign();
		sign.setSign(SIGN);
		sign.setSrt(result.toString());
		log.info("【龙支付回调】" + JsonUtil.toJson(sign));
		if (SUCCESS.equals("Y")) {
			boolean b = appLoongPayClient.verifySigature(sign);
			log.info("【龙支付回调校验】" + JsonUtil.toJson(b));
			if (b) {
				RechargeRecord rr = rechargeRecordRepository.findRechargeRecordByCode(ORDERID);
				Orders orders = ordersRepository.getOne(rr.getOrderId());
				this.checkOutOrder(orders, rr);
			}
		}
	}
}
