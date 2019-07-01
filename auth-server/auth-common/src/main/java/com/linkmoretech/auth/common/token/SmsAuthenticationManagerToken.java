package com.linkmoretech.auth.common.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: alec
 * Description: 基于短信验证码登录验证token 重写
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class SmsAuthenticationManagerToken extends SmsAuthenticationToken {


    private static final long serialVersionUID = 500L;


    public SmsAuthenticationManagerToken(Object principal, String clientId) {
        super(principal, clientId);
    }

    public SmsAuthenticationManagerToken(Object principal, String clientId,
                                         Collection<? extends GrantedAuthority> authorities,
                                         Long userId) {
        super(principal, clientId, authorities, userId);
    }
}
