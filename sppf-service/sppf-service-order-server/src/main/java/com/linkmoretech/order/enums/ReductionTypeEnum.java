package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 减免类型
 * @Author: alec
 * @Description:
 * @date: 上午11:52 2019/4/12
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
