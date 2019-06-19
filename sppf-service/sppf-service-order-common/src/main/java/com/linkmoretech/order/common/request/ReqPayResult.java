package com.linkmoretech.order.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("支付结果校验")
public class ReqPayResult {
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	@ApiModelProperty(value = "流水号")
	private String number;

}
