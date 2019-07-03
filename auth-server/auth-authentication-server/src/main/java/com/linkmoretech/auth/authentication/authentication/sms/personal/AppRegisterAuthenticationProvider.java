package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AppAuthenticationToken;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description: 个人版app注册拦截器处理链
 * @date: 10:57 2019-06-28
 */
@Setter
@Slf4j
public class AppRegisterAuthenticationProvider implements AuthenticationProvider {

    private UserDetailAccountAbstract userDetailService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       /* AppAuthenticationToken token = (AppAuthenticationToken) authentication;
        String mobile = (String)token.getPrincipal();
        Integer type = token.getType();
        log.info("帐号注册 {} - {}", mobile, type);
        AppUserDetail userDetails = (AppUserDetail) appUserDetailAbstract.register(mobile, type);
        AppAuthenticationToken appAuthenticationToken = new AppAuthenticationToken(userDetails.getUsername(),
                userDetails.getUserId(), userDetails.getRegister());
        appAuthenticationToken.setDetails(token.getDetails());
        return appAuthenticationToken;*/
        ProviderCommon providerCommon = new ProviderCommon(authentication);
        return providerCommon.getUserDetailForRegister(userDetailService);
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return AppAuthenticationToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
