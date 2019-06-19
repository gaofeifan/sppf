package com.linkmoretech.user.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 * @Author: alec
 * @Description:
 * @date: 下午3:28 2019/4/10
 */
@Getter
public enum UserStatusEnum {

    NO_REGISTER(0, "未注册"),
    NORMAL(1, "正常"),
    FREEZE(2, "冻结"),
    ;

    private Integer code;

    private String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
