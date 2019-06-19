package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("支付[银联ApplePay]")
public class ResPayApple {
	@ApiModelProperty(value = "交易流水号")
	private String tn;

}
