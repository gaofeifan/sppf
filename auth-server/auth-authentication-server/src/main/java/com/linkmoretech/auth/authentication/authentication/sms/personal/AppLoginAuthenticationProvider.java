package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AppLoginAuthenticationToken;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description: 个人版登录处理器
 * @date: 19:50 2019-06-28
 */
@Slf4j
@Setter
public class AppLoginAuthenticationProvider implements AuthenticationProvider {

    private UserDetailAccountAbstract userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ProviderCommon providerCommon = new ProviderCommon(authentication);
        return providerCommon.getUserDetailForLogin(userDetailService);
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return AppLoginAuthenticationToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
