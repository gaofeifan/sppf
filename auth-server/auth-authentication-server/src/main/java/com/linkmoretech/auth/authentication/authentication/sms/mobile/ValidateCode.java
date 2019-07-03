package com.linkmoretech.auth.authentication.authentication.sms.mobile;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: alec
 * Description: 短信验证码
 * @date: 16:14 2019-06-19
 */
@Data
public class ValidateCode implements Serializable {

    private String code;

    private Integer expire;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int seconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(seconds);
        this.expire = seconds;
    }
}
