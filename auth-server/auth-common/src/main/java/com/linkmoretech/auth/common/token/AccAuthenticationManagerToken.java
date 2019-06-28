package com.linkmoretech.auth.common.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: alec
 * Description: 基于帐号密码登录Token
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class AccAuthenticationManagerToken extends SmsAuthenticationToken {

    private static final long serialVersionUID = 500L;

    private  Object credentials ;

    public AccAuthenticationManagerToken(Object principal, Object credentials, String clientId) {
        super(principal, clientId);
        this.credentials = credentials;
    }

    public AccAuthenticationManagerToken(Object principal, Object credentials,
                                         String clientId, Long userId,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(principal,clientId, authorities, userId);
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
