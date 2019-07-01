package com.linkmoretech.auth.common.token;
import lombok.Getter;

import java.util.LinkedHashSet;

/**
 * @Author: alec
 * Description: 基于APP短信登录及注册token
 * @date: 10:47 2019-06-28
 */
@Getter
public class AppAuthenticationToken extends SmsAuthenticationToken {

    private static final long serialVersionUID = 500L;

    private Integer type;

    private Boolean register;

    public AppAuthenticationToken(Object principal, Object source) {
        super(principal, null);
        this.type =  (Integer) source;
    }

    public AppAuthenticationToken (Object principal, Long userId, Boolean register){
        super(principal, null, new LinkedHashSet<>(), userId);
        this.register = register;
    }
}
