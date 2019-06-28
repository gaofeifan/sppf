package com.linkmoretech.auth.common.service;

import com.linkmoretech.auth.common.exception.RegisterException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: alec
 * Description:
 * @date: 11:00 2019-06-28
 */
public abstract class AppUserDetailAbstract implements UserDetailsService {

    public abstract UserDetails register(String mobile, Integer type) throws RegisterException;

}
