package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.service.UserDetailMobileAbstract;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: alec
 * Description:
 * @date: 15:28 2019-06-17
 */
@Setter
@Slf4j
public class SmsAuthenticationPersonalProvider implements AuthenticationProvider {

    private UserDetailMobileAbstract userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationPersonalToken token = (SmsAuthenticationPersonalToken)authentication;
        String username = (String)token.getPrincipal();
        String clientId = token.getClientId();
        AccountUserDetail userDetails = (AccountUserDetail)userDetailService.loadUserBy(clientId, username);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户登录信息");
        }
        SmsAuthenticationPersonalToken resultToken = new SmsAuthenticationPersonalToken(userDetails,
                clientId,
                userDetails.getAuthorities(), userDetails.getDataAuthorities());
        resultToken.setDetails(authentication.getDetails());
        return resultToken;
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return SmsAuthenticationPersonalToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
