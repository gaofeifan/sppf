package com.linkmoretech.auth.authentication.authentication.account;

import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.token.AccAuthenticationManagerToken;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: alec
 * Description:
 * @date: 15:28 2019-06-17
 */
@Setter
@Slf4j
public class AccAuthenticationManagerProvider implements AuthenticationProvider {

    private UserDetailAccountAbstract userDetailService;

    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AccAuthenticationManagerToken token = (AccAuthenticationManagerToken)authentication;
        String clientId = token.getClientId();
        String username = (String)token.getPrincipal();
        String password = (String) token.getCredentials();
        AccountUserDetail userDetails = (AccountUserDetail)userDetailService.loadUserBy(clientId, username);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InternalAuthenticationServiceException("密码不正确");
        }
        log.info("创建登录token {}", userDetails.getUserId());

        AccAuthenticationManagerToken resultToken = new AccAuthenticationManagerToken(userDetails,
                userDetails.getPassword(),
                clientId,
                userDetails.getUserId(),
                userDetails.getAuthorities());
        resultToken.setDetails(authentication.getDetails());
        return resultToken;
    }

    @Override
    public boolean supports(Class<?> authenticationTokenClass) {
        return AccAuthenticationManagerToken.class.isAssignableFrom(authenticationTokenClass);
    }
}
