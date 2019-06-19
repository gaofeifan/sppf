package com.linkmoretech.auth.authentication.authentication.sms.mobile;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description:
 * @date: 17:57 2019-06-19
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
