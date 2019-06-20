package com.linkmoretech.auth.common.bean;

import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private Set<Long> dataAuthorities;

    @Getter
    @Setter
    private String clientId;

    public AccountUserDetail (String username, String password, boolean enable,
                              Collection<? extends GrantedAuthority> authorities,
                              Set<Long> dataAuthorities, String clientId) {
        super(username, password, enable, true, true, true, authorities);
        this.dataAuthorities = dataAuthorities;
        this.clientId = clientId;
    }

}
