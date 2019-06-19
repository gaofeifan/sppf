package com.linkmoretech.auth.authentication.authentication.account;

import com.linkmoretech.auth.common.configuration.SmsAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @Author: alec
 * Description: 基于短信验证码登录验证token 重写
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class AccAuthenticationManagerToken extends SmsAuthenticationToken {

    private static final long serialVersionUID = 500L;

    private  Object credentials ;

    public AccAuthenticationManagerToken(Object principal, Object credentials, String clientId) {
        super(principal, clientId);
        this.credentials = credentials;
    }

    public AccAuthenticationManagerToken(Object principal, Object credentials,
                                         String clientId, Set<Long> dataResources,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(principal,clientId, authorities, dataResources);
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
    /*public AccAuthenticationManagerToken(Object principal, Object credentials, String clientId) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.clientId = clientId;
        this.setAuthenticated(false);
    }

    public AccAuthenticationManagerToken(Object principal, Object credentials,
                                         String clientId, Set<Long> dataResources,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.dataResources = dataResources;
        this.clientId = clientId;
        super.setAuthenticated(true);
    }
*/
   /* @Override
    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public String getClientId () {
        return this.clientId;
    }

    public Set<Long> getDataResources () {
        return this.dataResources;
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
        this.credentials = null;
    }*/

}
