package com.linkmoretech.auth.authentication.authentication.sms.manager;

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
public class SmsAuthenticationManagerProvider implements AuthenticationProvider {

    private UserDetailMobileAbstract userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationManagerToken token = (SmsAuthenticationManagerToken) authentication;
        String username = (String)token.getPrincipal();
        String clientId = token.getClientId();
        log.info("username {}, clientId {}", username, clientId);
        AccountUserDetail userDetails = (AccountUserDetail) userDetailService.loadUserBy(clientId, username);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户登录信息");
        }
        SmsAuthenticationManagerToken resultToken = new SmsAuthenticationManagerToken(userDetails,
                clientId,
                userDetails.getAuthorities(),
                userDetails.getDataAuthorities());
        resultToken.setDetails(authentication.getDetails());
        return resultToken;
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return SmsAuthenticationManagerToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
