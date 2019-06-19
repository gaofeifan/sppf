package com.linkmoretech.order.common.response;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("确认支付")
public class ResPayConfirm { 
	@ApiModelProperty(value = "支付金额")
	private Double amount;
	@ApiModelProperty(value = "流水号")
	private String number;
	@ApiModelProperty(value = "支付分类[0账户余额，1支付宝，2微信，3银联Apple Pay,4小程序,5银联云闪付,6华为Pay、7小米Pay、8龙支付]")
	private Short payType;
	@ApiModelProperty(value = "支付宝支付")
	private String alipay;
	@ApiModelProperty(value = "微信支付")
	private ResPayWeixin weixin;
	@ApiModelProperty(value = "苹果支付")
	private String apple;
	@ApiModelProperty(value = "银联支付")
	private String union;
	@ApiModelProperty(value = "小程序微信支付")
	private ResPayWeixinMini weixinMini;
	@ApiModelProperty(value = "建行龙支付")
	private ResLoongPay resLoongPay;
	public Double getAmount() { 
		if(amount==null) {
			amount = new Double(0D);
		}
		BigDecimal b = new BigDecimal(this.amount); 
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}

}
