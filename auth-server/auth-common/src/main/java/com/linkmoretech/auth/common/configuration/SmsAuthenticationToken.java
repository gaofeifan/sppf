package com.linkmoretech.auth.common.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @Author: alec
 * Description:
 * @date: 14:44 2019-06-18
 */
public abstract class SmsAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    protected static final long serialVersionUID = 500L;

    protected final Object principal;

    protected String clientId;

    protected Long userId;

    public SmsAuthenticationToken(Object principal, String clientId) {
        super((Collection)null);
        this.principal = principal;
        this.clientId = clientId;
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal, String clientId, Collection<? extends GrantedAuthority> authorities,
                                  Long userId) {
        super(authorities);
        this.principal = principal;
        this.clientId = clientId;
        this.userId = userId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String getClientId () {
        return this.clientId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
