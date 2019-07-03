package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.authentication.authentication.sms.manager.SmsAuthenticationManagerFilter;
import com.linkmoretech.auth.authentication.authentication.sms.manager.SmsAuthenticationManagerProvider;
import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
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

import javax.annotation.Resource;

/**
 * @Author: alec
 * Description:
 * @date: 11:08 2019-06-28
 */
@Component
public class AppRegisterAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Resource
    UserDetailAccountAbstract userDetailAccountService;

    @Override
    public void configure(HttpSecurity http){

        AppRegisterAuthenticationFilter appRegisterAuthenticationFilter = new AppRegisterAuthenticationFilter();
        appRegisterAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        appRegisterAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        appRegisterAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        AppRegisterAuthenticationProvider appRegisterAuthenticationProvider = new AppRegisterAuthenticationProvider();
        appRegisterAuthenticationProvider.setUserDetailService(userDetailAccountService);
        http.authenticationProvider(appRegisterAuthenticationProvider)
                .addFilterAfter(appRegisterAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
