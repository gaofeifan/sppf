package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 优惠减免类型
 * @author jhb
 * @Date 2019年6月20日 下午7:17:20
 * @Version 1.0
 */
@Getter
public enum  ReductionTypeEnum {

    COUPON(1, "预约"),
    ;
    private Integer code;

    private String message;

    ReductionTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
