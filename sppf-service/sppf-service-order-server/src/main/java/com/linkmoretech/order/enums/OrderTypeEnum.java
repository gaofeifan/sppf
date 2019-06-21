package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 订单类型
 * @author jhb
 * @Date 2019年6月20日 下午7:16:56
 * @Version 1.0
 */
@Getter
public enum OrderTypeEnum {
    SUBSCRIBE(1, "预约"),
    SCAN(2, "扫码"),
    SHARE(3, "分享"),
    ;
    private Integer code;

    private String message;

    OrderTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
