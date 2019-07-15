package com.linkmoretech.auth.common.service;

import com.linkmoretech.auth.common.exception.RegisterException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 10:15 2019-06-13
 */
@Data
@Slf4j
@Component
public abstract class UserDetailAccountAbstract implements UserDetailsService {

    /**
     * 通用登录注册
     * */
    public abstract UserDetails loadUserBy(String clientId, String username);

    /**
     * App 注册
     * */
    public abstract UserDetails register(String mobile, Integer type) throws RegisterException;

    /**
     * APP 登录
     * */
    public abstract UserDetails login(String mobile, Integer type) throws RegisterException;


    /**
     * 小程序登录
     * */
    public abstract UserDetails loginForWechat(String code) ;

}
