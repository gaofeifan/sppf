package com.linkmoretech.auth.resource.configuration;

import com.linkmoretech.auth.common.configuration.OauthResourceConfig;

import com.linkmoretech.auth.common.exception.SecurityAuthenticationEntryPoint;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;



/**
 * @Author: alec
 * Description:
 * @date: 15:17 2019-06-13
 */
@Setter
@Slf4j
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    OauthResourceConfig oauthResourceConfig;

    private final String OAUTH_URL = "/oauth/**";

    @Override
    public void configure(HttpSecurity http)  {

        oauthResourceConfig.getIgnores().add(OAUTH_URL);
        String[] matchers = new String[oauthResourceConfig.getIgnores().size()];
        matchers = oauthResourceConfig.getIgnores().toArray(matchers);

        log.info("过滤URL {}", matchers);
        try {
            http
                .authorizeRequests()
                .antMatchers(matchers)
                .permitAll()
                .anyRequest() // 所有请求
                .authenticated() //需要身份认证
                .and()
                .csrf().disable()//关闭csrf
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                .and()
                .headers().frameOptions().disable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
