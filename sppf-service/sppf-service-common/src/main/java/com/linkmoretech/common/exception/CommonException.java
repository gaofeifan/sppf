package com.linkmoretech.common.exception;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.Getter;

/**
 * 自定义异常暂不实现特殊
 * @Author: alec
 * @Description:
 * @date: 上午11:31 2019/4/10
 */
@Getter
public class CommonException extends FeignException {

    private ResponseCodeEnum codeEnum;
    private String message;

    public CommonException(ResponseCodeEnum codeEnum) {
        super();
        this.codeEnum = codeEnum;
        this.message = codeEnum.getMessage();
    }
    public CommonException(ResponseCodeEnum codeEnum, String message) {
        super();
        this.codeEnum = codeEnum;
        this.message = message;
    }
}
