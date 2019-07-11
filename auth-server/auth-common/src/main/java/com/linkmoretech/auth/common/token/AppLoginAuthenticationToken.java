package com.linkmoretech.auth.common.token;

import lombok.Getter;

/**
 * @Author: alec
 * Description:
 * @date: 20:19 2019-06-28
 */
@Getter
public class AppLoginAuthenticationToken extends AppAuthenticationToken {

    private String validateCode;

    public AppLoginAuthenticationToken(Object principal, Object source, String validateCode) {
        super(principal, source);
        this.validateCode = validateCode;
    }
}
