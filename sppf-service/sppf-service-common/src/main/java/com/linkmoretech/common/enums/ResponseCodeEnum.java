package com.linkmoretech.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 响应码枚举类型
 * @author Created by Alec
 * 2019/4/4 14:15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  ResponseCodeEnum {
    SUCCESS(200, "响应成功"),
    UNAUTHORIZED(403, "未授权访问"),
    PARAMS_ERROR(400, "参数不合法"),
    ERROR(401, "操作失败"),
    FAILURE(500, "服务器暂时不可用,请稍后再试"),

    LOGIN_LOSE(405, "未登录或Token失效"),

    ;
    private Integer code;

    private String message;
}
