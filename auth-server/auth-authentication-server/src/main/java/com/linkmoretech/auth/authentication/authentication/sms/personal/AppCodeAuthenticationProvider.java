package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AppCodeAuthenticationToken;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description:
 * @date: 11:25 2019-07-11
 */
@Data
public class AppCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailAccountAbstract userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ProviderCommon providerCommon = new ProviderCommon(authentication);
        return providerCommon.getUserDetailForCode(userDetailService);
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return AppCodeAuthenticationToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
