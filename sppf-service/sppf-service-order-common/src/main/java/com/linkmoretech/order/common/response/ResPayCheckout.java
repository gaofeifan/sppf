package com.linkmoretech.order.common.response;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("账单信息")
public class ResPayCheckout {
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	@ApiModelProperty(value = "专区名称")
	private String prefectureName;
	@ApiModelProperty(value = "车位名称")
	private String stallName;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "停车时长")
	private Integer parkingTime;
	@ApiModelProperty(value = "停车券数量")
	private Integer couponCount;
	@ApiModelProperty(value = "支付分类[0账户余额，1支付宝，2微信，3银联]")
	private Short payType;
	@ApiModelProperty(value = "订单总额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "账户余额")
	private BigDecimal accountAmount;

	public BigDecimal getTotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getAccountAmount() {
		if(this.accountAmount==null) {
			this.accountAmount = new BigDecimal(0d);
		}
		return this.accountAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
}
