package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 订单支付类型
 * @Author: alec
 * @Description:
 * @date: 下午8:20 2019/4/11
 */
@Getter
public enum PayTypeEnum {

    WE_CHART_PAY(1, "微信支付"),
    ALI_PAY(2, "支付宝支付"),
    UNION_PAY(3, "银联支付"),
    DRAGON_PAY(4, "龙支付(建行)")
    ;
    private Integer code;

    private String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
