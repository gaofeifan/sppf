package com.linkmoretech.auth.authentication.authentication.sms.manager;

import com.linkmoretech.auth.common.configuration.SmsAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

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
                                         Set<Long> dataResourcesId) {
        super(principal, clientId, authorities, dataResourcesId);
    }
}
