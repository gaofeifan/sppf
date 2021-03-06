package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 订单状态类型
 * @author jhb
 * @Date 2019年6月20日 下午7:16:41
 * @Version 1.0
 */
@Getter
public enum  OrderStatusEnum {

    BOOKED(1, "已预约"),
    STOPED(2, "已停车"),
    PAID(3, "已支付"),
    CANCEL(4, "已取消"),
    REFUND(5,"已退款"),
    SUSPENDED(6, "已挂起"),
    CLOSE(7, "已关闭"),
    SAILIN(8,"已进场"),
    SAILOUT(9,"已离场"),
    DEPARTURE(10, "已驶离")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
