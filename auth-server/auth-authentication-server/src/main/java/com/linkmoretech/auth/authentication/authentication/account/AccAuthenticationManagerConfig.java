package com.linkmoretech.auth.authentication.authentication.account;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: alec
 * Description:
 * @date: 15:29 2019-06-17
 */
@Component
public class AccAuthenticationManagerConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Resource
    UserDetailAccountAbstract userDetailAccountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) {

        AccAuthenticationManagerFilter smsAuthenticationFilter = new AccAuthenticationManagerFilter();
        smsAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        AccAuthenticationManagerProvider smsAuthenticationProvider = new AccAuthenticationManagerProvider();
        smsAuthenticationProvider.setUserDetailService(userDetailAccountService);
        smsAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
