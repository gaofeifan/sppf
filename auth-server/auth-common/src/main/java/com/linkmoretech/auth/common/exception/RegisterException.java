package com.linkmoretech.auth.common.exception;

import lombok.Getter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description:
 * @date: 14:28 2019-06-28
 */
@Getter
public class RegisterException extends InternalAuthenticationServiceException {

    private Integer code;

    private String message;

    public RegisterException (Integer code, String message) {
        super(message);
        this.code =  code;
        this.message = message;
    }
}
