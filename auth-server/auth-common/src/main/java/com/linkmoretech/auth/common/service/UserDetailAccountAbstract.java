package com.linkmoretech.auth.common.service;

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

    public abstract UserDetails loadUserBy(String clientId, String username);
}
