package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.service.UserDetailMobileAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 15:29 2019-06-17
 */
@Component
public class SmsAuthenticationPersonalConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Autowired
    UserDetailMobileAbstract userDetailMobileService;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmsAuthenticationPersonalFilter smsAuthenticationFilter = new SmsAuthenticationPersonalFilter();
        smsAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        SmsAuthenticationPersonalProvider smsAuthenticationProvider = new SmsAuthenticationPersonalProvider();
        smsAuthenticationProvider.setUserDetailService(userDetailMobileService);
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
