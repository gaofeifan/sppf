package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 支付类型
 * @author jhb
 * @Date 2019年6月20日 下午7:17:08
 * @Version 1.0
 */
@Getter
public enum PayTypeEnum {

    /*WE_CHART_PAY(1, "微信支付"),
    ALI_PAY(2, "支付宝支付"),
    UNION_PAY(3, "银联支付"),
    DRAGON_PAY(4, "龙支付(建行)")*/
    
    ACCOUNT(0,"账户支付"),
	ALIPAY( 1,"支付宝支付"),
	WECHAT ( 2,"微信支付"), 
	APPLE (3,"苹果支付"),
	WECHAT_MINI (4,"微信小程序支付"),
	UNION(5,"银联支付"),
	HUAWEI(6,"华为支付"),
	XIAOMI(7,"小米支付"),
	LOONG(8,"建行龙支付");
    
    ;
    private Integer code;

    private String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
