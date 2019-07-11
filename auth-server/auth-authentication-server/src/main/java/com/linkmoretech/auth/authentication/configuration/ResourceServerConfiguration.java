package com.linkmoretech.auth.authentication.configuration;

import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.authentication.ValidateSuccessHandler;
import com.linkmoretech.auth.authentication.authentication.account.AccAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.authentication.sms.mobile.SmsLoginFilter;
import com.linkmoretech.auth.authentication.authentication.sms.personal.AppCodeAuthenticationConfig;
import com.linkmoretech.auth.authentication.authentication.sms.personal.AppLoginAuthenticationConfig;
import com.linkmoretech.auth.authentication.authentication.sms.personal.AppRegisterAuthenticationConfig;
import com.linkmoretech.auth.authentication.authentication.sms.manager.SmsAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.component.ValidateCodeManage;
import com.linkmoretech.auth.common.configuration.OauthResourceConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: alec
 * Description:
 * @date: 15:17 2019-06-13
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    @Autowired
    ValidateSuccessHandler validateSuccessHandler;

    @Autowired
    ValidateFailureHandler validateFailureHandler;

    @Autowired
    SmsAuthenticationManagerConfig smsAuthenticationManagerConfig;

    @Autowired
    AccAuthenticationManagerConfig accAuthenticationManagerConfig;

    @Autowired
    AppLoginAuthenticationConfig appLoginAuthenticationConfig;

    /**
     * 用户注册过滤器
     * */
    @Autowired
    AppRegisterAuthenticationConfig appRegisterAuthenticationConfig;


    @Autowired
    AppCodeAuthenticationConfig appCodeAuthenticationConfig;

    @Autowired
    ValidateCodeManage validateCodeManage;

    @Autowired
    OauthResourceConfig oauthResourceConfig;


    private final String OAUTH_URL = "/oauth/**";


    @Override
    public void configure(HttpSecurity http) throws Exception {

        oauthResourceConfig.getIgnores().add(OAUTH_URL);
        String[] matchers = new String[oauthResourceConfig.getIgnores().size()];
        matchers = oauthResourceConfig.getIgnores().toArray(matchers);
        log.info("对 {} url 不进行拦截 {}", matchers);
        SmsLoginFilter smsCodeFilter = new SmsLoginFilter(validateCodeManage, validateFailureHandler);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin() //采用表单认证方式 -- 身份认证
            .successHandler(validateSuccessHandler)
            .failureHandler(validateFailureHandler);

        http
            .authorizeRequests() // 授权设定
            .antMatchers(matchers).permitAll()    //对此链接不拦截
            .anyRequest() // 所有请求
            .authenticated() //需要身份认证
                .and()
                .apply(accAuthenticationManagerConfig)
                .and()
                .apply(smsAuthenticationManagerConfig)
                .and()
                .apply(appLoginAuthenticationConfig)
                .and()
                .apply(appRegisterAuthenticationConfig)
                .and()
                .apply(appCodeAuthenticationConfig)
                .and()
                .csrf().disable();//关闭csrf
    }
}
