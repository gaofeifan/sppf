package com.linkmoretech.auth.common.token;

/**
 * @Author: alec
 * Description:
 * @date: 13:27 2019-07-11
 */
public class AppCodeAuthenticationToken extends AppAuthenticationToken {


    public AppCodeAuthenticationToken(Object principal) {
        super(principal, null);
    }
}
