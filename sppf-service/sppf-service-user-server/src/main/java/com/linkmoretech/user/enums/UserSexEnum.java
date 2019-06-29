package com.linkmoretech.user.enums;

import lombok.Getter;

/**
 * 用户性别枚举
 * @Author: alec
 * @Description:
 * @date: 下午3:27 2019/4/10
 */
@Getter
public enum UserSexEnum {
    MAN(1, "男"),
    WOMAN(0, "女"),
    ;

    private Integer code;
    private String message;

    UserSexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
