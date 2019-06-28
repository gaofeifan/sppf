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
    private Long userId;

    @Getter
    @Setter
    private String clientId;

    @Getter
    private Boolean register;

    public AccountUserDetail (String username, String password, boolean enable,
                              Collection<? extends GrantedAuthority> authorities,
                              Long userId, String clientId) {
        super(username, password, enable, true, true, true, authorities);
        this.userId = userId;
        this.clientId = clientId;
    }

}
