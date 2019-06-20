package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 操作结果类型
 * @author jhb
 * @Date 2019年6月20日 下午7:16:26
 * @Version 1.0
 */
@Getter
public enum OperateStatusEnum {
    FAILURE(0, "失败"),
    SUCCESS(1, "成功");
    private Integer code;

    private String message;

    OperateStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
