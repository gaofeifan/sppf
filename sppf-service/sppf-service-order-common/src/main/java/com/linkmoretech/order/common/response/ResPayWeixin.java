package com.linkmoretech.order.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("支付[微信支付]")
public class ResPayWeixin {
	@ApiModelProperty(value = "应用ID")
	private String appid;
	@ApiModelProperty(value = "商户号")
	private String partnerid;
	@ApiModelProperty(value = "会话ID")
	private String prepayid; 
	@ApiModelProperty(value = "随机串")
	private String noncestr;
	@ApiModelProperty(value = "时间戳")
	private String timestamp; 
	@ApiModelProperty(value = "签名")
	private String sign;

}
