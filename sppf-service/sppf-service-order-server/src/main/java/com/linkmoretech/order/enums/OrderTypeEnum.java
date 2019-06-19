package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 订单类型
 * @Author: alec
 * @Description:
 * @date: 上午11:49 2019/4/12
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
