package com.linkmoretech.order.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("确认支付请求")
public class ReqPayConfirm {
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	
	@ApiModelProperty(value = "支付类型[1支付宝、2微信、3ApplePay、5银联云闪付、6华为Pay、7小米Pay、8建行龙支付]")
	private Short payType;
	
	@ApiModelProperty(value = "停车券ID")
	private Long couponId = 0L;

}
