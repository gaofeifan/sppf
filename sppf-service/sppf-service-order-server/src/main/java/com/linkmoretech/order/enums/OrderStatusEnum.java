package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 * @Author: alec
 * @Description:
 * @date: 下午7:58 2019/4/11
 */
@Getter
public enum  OrderStatusEnum {

    BOOKED(1, "已预约"),
    STOPED(2, "已停车"),
    PAID(3, "已支付"),
    DEPARTURE(4, "已离场"),
    CLOSE(5, "已关闭"),
    SUSPENDED(6, "已挂起"),
    CANCEL(7, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
