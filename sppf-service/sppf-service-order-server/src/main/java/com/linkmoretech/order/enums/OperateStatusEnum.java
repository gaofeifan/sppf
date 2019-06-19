package com.linkmoretech.order.enums;

import lombok.Getter;

/**
 * 操作结果类型
 * @Author: alec
 * @Description:
 * @date: 上午11:49 2019/4/12
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
