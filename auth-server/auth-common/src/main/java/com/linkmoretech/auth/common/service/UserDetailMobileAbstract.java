package com.linkmoretech.auth.common.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: alec
 * Description:
 * @date: 17:30 2019-06-18
 */
public abstract class UserDetailMobileAbstract implements UserDetailsService {

    public abstract UserDetails loadUserBy(String clientId, String username);
}
