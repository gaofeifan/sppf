package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("支付[支付宝]")
public class ResPayAlipay { 
	
	@ApiModelProperty(value = "订单信息")
	private String orderInfo;
	
}
