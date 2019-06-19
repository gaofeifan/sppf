package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("支付[微信小程序 微信支付]")
public class ResPayWeixinMini {

	@ApiModelProperty(value = "appId")
	private String id;

	@ApiModelProperty(value = "nonceStr")
	private String nonce;

	@ApiModelProperty(value = "package")
	private String pack;

	@ApiModelProperty(value = "paySign")
	private String sign;

	@ApiModelProperty(value = "signType")
	private String type;

	@ApiModelProperty(value = "timeStamp")
	private String stamp;

}
