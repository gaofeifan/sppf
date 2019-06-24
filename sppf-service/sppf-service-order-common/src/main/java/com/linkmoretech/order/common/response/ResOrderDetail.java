package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单详情")
public class ResOrderDetail{
	
	@ApiModelProperty(value = "订单主键")
	private String orderId;
	//未使用
	@ApiModelProperty(value = "编号")
	private String orderNo;
	@ApiModelProperty(value = "离开免费时长")
	private Integer leaveTime;
	
	//车区、车位、车牌
	@ApiModelProperty(value = "专区名")
	private String parkName;
	@ApiModelProperty(value = "车位名")
	private String placeName;
	@ApiModelProperty(value = "车牌号")
	private String plateNo;
	
	//订单状态、支付状态
	@ApiModelProperty(value = "订单状态")
	private Short status;
	@ApiModelProperty(value = "支付类型[1免费，2停车券，3账户，4支付宝，5微信，6ApplePay、7微信、8银联云闪付、9华为Pay、10小米Pay、11建行龙支付]")
	private Short payType;
	
	//预约、离开、支付时间
	@ApiModelProperty(value = "预约时间")
	private Date orderTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "停车时长")
	private String parkingTime;
	
	//总金额、支付金额、优惠金额
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "支付金额")
	private BigDecimal payAmount;
	@ApiModelProperty(value = "停车券金额")
	private BigDecimal couponAmount;
	
	@ApiModelProperty(value = "总金额[字符串]")
	private String stotalAmount;
	@ApiModelProperty(value = "停车券金额[字符串]")
	private String scouponAmount;
	@ApiModelProperty(value = "支付金额[字符串]")
	private String spayAmount;
	
	public String getStotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	public String getScouponAmount() {
		if(this.couponAmount==null) {
			this.couponAmount = new BigDecimal(0d);
		}
		return this.couponAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	public String getSpayAmount() {
		if(this.payAmount==null) {
			this.payAmount = new BigDecimal(0d);
		}
		return this.payAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	
}
