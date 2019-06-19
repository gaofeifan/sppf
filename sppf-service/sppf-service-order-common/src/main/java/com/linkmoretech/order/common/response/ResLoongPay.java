package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("龙支付")
public class ResLoongPay {
	@ApiModelProperty(value="调用龙支付的参数")
	private String param;

	@ApiModelProperty(value="自定义app字段值")
	private String thirdAppInfo;
	
	private String sign;
}
