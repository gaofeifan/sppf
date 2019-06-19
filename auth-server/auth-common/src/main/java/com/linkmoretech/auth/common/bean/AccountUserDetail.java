package com.linkmoretech.auth.common.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @Author: alec
 * Description:
 * @date: 19:07 2019-06-14
 */

public class AccountUserDetail extends User implements UserDetails {

    private Set<Long> dataAuthorities;

    public AccountUserDetail (String username, String password, boolean enable,
                              Collection<? extends GrantedAuthority> authorities,
                              Set<Long> dataAuthorities) {
        super(username, password, enable, true, true, true, authorities);
        this.dataAuthorities = dataAuthorities;
    }

    public Set<Long> getDataAuthorities () {
        return dataAuthorities;
    }

}
