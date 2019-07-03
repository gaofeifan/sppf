package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AppAuthenticationToken;
import com.linkmoretech.auth.common.token.AppLoginAuthenticationToken;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;

/**
 * @Author: alec
 * Description:
 * @date: 19:50 2019-06-28
 */
@Slf4j
@Setter
public class AppLoginAuthenticationProvider implements AuthenticationProvider {

    private UserDetailAccountAbstract userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /*AppAuthenticationToken token = (AppAuthenticationToken) authentication;
        String mobile = (String)token.getPrincipal();
        Integer type = token.getType();
        log.info("帐号注册 {} - {}", mobile, type);
        AppUserDetail userDetails = (AppUserDetail) appUserDetailAbstract.login(mobile, type);
        AppAuthenticationToken appAuthenticationToken = new AppAuthenticationToken(userDetails.getUsername(),
                userDetails.getUserId(), userDetails.getRegister());
        appAuthenticationToken.setDetails(token.getDetails());
        return appAuthenticationToken;*/
        ProviderCommon providerCommon = new ProviderCommon(authentication);
        return providerCommon.getUserDetailForLogin(userDetailService);
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return AppLoginAuthenticationToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
